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

    public void updateConnection(EdmEntityType edmEntityType, List<UriParameter> keyParams, Entity entity, HttpMethod httpMethod)
        throws ODataApplicationException {
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
