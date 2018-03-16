////////////////////////////////////////////////////////////////////////////////
//
// Created by BBruhns on 10.03.2018.
//
// Copyright (c) 2006 - 2018 FORCAM GmbH. All rights reserved.
////////////////////////////////////////////////////////////////////////////////

package myservice.mynamespace.database.service.tmp2;

import myservice.mynamespace.database.collections.Scarr;
import myservice.mynamespace.database.collections.Spfli;
import myservice.mynamespace.database.service.DataTransformator;
import myservice.mynamespace.database.service.tmp.SpfliService;
import myservice.mynamespace.util.Util;
import org.apache.commons.lang3.StringUtils;
import org.apache.olingo.commons.api.data.Entity;
import org.apache.olingo.commons.api.data.EntityCollection;
import org.apache.olingo.commons.api.data.Property;
import org.apache.olingo.commons.api.data.ValueType;
import org.apache.olingo.commons.api.edm.EdmEntityType;
import org.apache.olingo.commons.api.http.HttpMethod;
import org.apache.olingo.commons.api.http.HttpStatusCode;
import org.apache.olingo.server.api.ODataApplicationException;
import org.apache.olingo.server.api.uri.UriParameter;

import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;

import static myservice.mynamespace.service.entities.definitions.EntityNames.CARRIER_ID;
import static myservice.mynamespace.service.entities.definitions.EntityNames.CONNECTION_ID;
import static myservice.mynamespace.service.entities.definitions.EntityNames.ES_SPFLI_NAME;

/**
 *
 */
public class EntityConnectionService extends AbstractEntityService {

    // ------------------------------------------------------------------------
    // constants
    // ------------------------------------------------------------------------

    // ------------------------------------------------------------------------
    // members
    // ------------------------------------------------------------------------

    private SpfliService mSpfliService;

    // ------------------------------------------------------------------------
    // constructors
    // ------------------------------------------------------------------------

    public EntityConnectionService() {
        mSpfliService = new SpfliService();
    }

    // ------------------------------------------------------------------------
    // methods
    // ------------------------------------------------------------------------

    public EntityCollection getConnections() {//todo für jede tabelle beide methoden fast identisch
        final List<Spfli> spflis = mSpfliService.getAllSpflis();
        final List<Entity> connections = spflis.stream().map(DataTransformator::transformSpfliToEntity).collect(Collectors.toList());
        final EntityCollection entitySet = new EntityCollection();

        entitySet.getEntities().addAll(connections);

        return entitySet;
    }

    public Entity getConnection(EdmEntityType edmEntityType, List<UriParameter> keyParams) throws ODataApplicationException {
        // the list of entities at runtime
        final EntityCollection entitySet = this.getConnections();
    /* generic approach to find the requested entity */
        return Util.findEntity(edmEntityType, entitySet, keyParams);
    }

    public Entity createConnection(EdmEntityType edmEntityType, Entity entity) {
        final Property idProperty = entity.getProperty(CONNECTION_ID);
        final String id;

        if (idProperty != null) {
            final String givenBookId = (String) idProperty.getValue();

            if (Util.idTaken(givenBookId, mSpfliService)) {
                id = Util.generateId(givenBookId, 10, true, true, mSpfliService);
            } else {
                id = givenBookId;
            }
            idProperty.setValue(ValueType.PRIMITIVE, id);
        } else {
            id = Util.generateId(StringUtils.EMPTY, 10, true, true, mSpfliService);
            entity.getProperties().add(new Property(null, CONNECTION_ID, ValueType.PRIMITIVE, id));
        }
        entity.setId(Util.createId(ES_SPFLI_NAME, id));

        final String carrierId = (String) entity.getProperty(CARRIER_ID).getValue();
        final Scarr scarr = Util.loadAssociatedCarrier(carrierId);

        mSpfliService.save(DataTransformator.transformEntityToSpfli(entity, scarr));

        return entity;
    }

    public void updateConnection(EdmEntityType edmEntityType, List<UriParameter> keyParams, Entity entityUpdated, HttpMethod httpMethod)
        throws ODataApplicationException {

        final Entity entityFromDB = getConnection(edmEntityType, keyParams);//TODO wird das auch in die db gespeichert?
        if (entityFromDB == null) {
            throw new ODataApplicationException("Entity not found", HttpStatusCode.NOT_FOUND.getStatusCode(), Locale.ENGLISH);
        }

        final List<Property> existingProperties = entityFromDB.getProperties();
        for (Property existingProperty : existingProperties) {
            final String propName = existingProperty.getName();

            if (Util.isKey(edmEntityType, propName)) {
                continue;
            }

            final Property updateProperty = entityUpdated.getProperty(propName);

            if (updateProperty == null) {
                //if a property has not been added to the request payload
                //depending on the HttpMethod the behaviour should be different
                if (httpMethod.equals(HttpMethod.PUT)) {
                    // in case of PUT, the existing property is set to null
                    existingProperty.setValue(existingProperty.getValueType(), null);
                    // in case of PATCH, the existing property is not touched
                }
            } else {
                if (updateProperty.getValue() != null) {
                    existingProperty.setValue(existingProperty.getValueType(), updateProperty.getValue());
                } else {
                    //TODO log das was schiefgegangen ist
                }
            }
        }
        //TODO checke obs sich bis hierhin verändert hat
        final Scarr scarr = Util.loadAssociatedCarrier((String) entityUpdated.getProperty(CARRIER_ID).getValue());
        //TODO namen von object anpassen
        //actual update - morphias uses upsert todo weiter erklären
        mSpfliService.save(DataTransformator.transformEntityToSpfli(entityFromDB, scarr));
    }

    public void deleteConnection(EdmEntityType edmEntityType, List<UriParameter> keyParams) throws ODataApplicationException {
        final Entity entity = this.getConnection(edmEntityType, keyParams);

        if (entity == null) {
            throw new ODataApplicationException("Entity not found", HttpStatusCode.NOT_FOUND.getStatusCode(), Locale.ENGLISH);
        }

        final Scarr scarr = Util.loadAssociatedCarrier((String) entity.getProperty(CARRIER_ID).getValue());

        mSpfliService.delete(DataTransformator.transformEntityToSpfli(entity, scarr));
    }

    // NAVIGATION TODO COOOL
    public EntityCollection getConnectionForFlight(Entity sourceEntity, EntityCollection navigationTargetEntityCollection) {
        final String carrierId = (String) sourceEntity.getProperty(CARRIER_ID).getValue();
        final String connectionId = (String) sourceEntity.getProperty(CONNECTION_ID).getValue();
        final Spfli spfli = mSpfliService.findConnectionByConnectionIdAndCarrierId(connectionId, carrierId);
        final Entity connection = DataTransformator.transformSpfliToEntity(spfli);

        navigationTargetEntityCollection.getEntities().add(connection);

        return navigationTargetEntityCollection;
    }

    public EntityCollection getConnectionsForCarrier(Entity sourceEntity, EntityCollection navigationTargetEntityCollection) {
        final String carrierId = (String) sourceEntity.getProperty(CARRIER_ID).getValue();
        final List<Spfli> spflis = mSpfliService.findConnectionsByCarrierId(carrierId);
        final List<Entity> connections = spflis.stream().map(DataTransformator::transformSpfliToEntity).collect(Collectors.toList());

        navigationTargetEntityCollection.getEntities().addAll(connections);

        return navigationTargetEntityCollection;
    }

    public EntityCollection getConnectionForBooking(Entity sourceEntity, EntityCollection navigationTargetEntityCollection) {
        final String carrierId = (String) sourceEntity.getProperty(CARRIER_ID).getValue();
        final String connectionId = (String) sourceEntity.getProperty(CONNECTION_ID).getValue();
        final Spfli spfli = mSpfliService.findConnectionByConnectionIdAndCarrierId(connectionId, carrierId);
        final Entity connection = DataTransformator.transformSpfliToEntity(spfli);

        navigationTargetEntityCollection.getEntities().add(connection);

        return navigationTargetEntityCollection;
    }

}
