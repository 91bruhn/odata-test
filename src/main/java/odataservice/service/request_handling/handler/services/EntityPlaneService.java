////////////////////////////////////////////////////////////////////////////////
//
// Created by BBruhns on 10.03.2018.
//
// Copyright (c) 2006 - 2018 FORCAM GmbH. All rights reserved.
////////////////////////////////////////////////////////////////////////////////

package odataservice.service.request_handling.handler.services;

import odataservice.database.collections.Saplane;
import odataservice.database.service.DataTransformator;
import odataservice.database.service.SaplaneService;
import odataservice.service.entities.definitions.EntityNames;
import odataservice.service.util.Util;
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

/**
 *
 */
public class EntityPlaneService {

    // ------------------------------------------------------------------------
    // members
    // ------------------------------------------------------------------------

    private SaplaneService mSaplaneService;

    // ------------------------------------------------------------------------
    // constructors
    // ------------------------------------------------------------------------

    public EntityPlaneService() {
        mSaplaneService = new SaplaneService();
    }

    // ------------------------------------------------------------------------
    // methods
    // ------------------------------------------------------------------------

    public EntityCollection getPlanes() {
        final List<Saplane> saplanes = mSaplaneService.getAllSaplanes();
        final List<Entity> planes = saplanes.stream().map(DataTransformator::transformSaplaneToEntity).collect(Collectors.toList());
        final EntityCollection entitySet = new EntityCollection();

        entitySet.getEntities().addAll(planes);

        return entitySet;
    }

    public Entity getPlane(EdmEntityType edmEntityType, List<UriParameter> keyParams) throws ODataApplicationException {
        final Saplane saplane = mSaplaneService.getById(Util.getPlaneTypeKey(keyParams));
        return DataTransformator.transformSaplaneToEntity(saplane);
    }

    //übergebene id muss vorhanden und gültig sein ansonsten null --> service sollte folgenden http
    public Entity createPlane(EdmEntityType edmEntityType, Entity entity) {
        final Property idProperty = entity.getProperty(EntityNames.PLANE_TYPE);
        final String id;

        if (idProperty != null) {
            final String planeType = (String) idProperty.getValue();

            if (this.idTaken(planeType)) {
                //TODO LOG plane already defined in db
                return null;
            } else {
                id = planeType;
            }
            idProperty.setValue(ValueType.PRIMITIVE, id);//TODO was macht das?
        } else {
            return null;
        }
        entity.setId(Util.createId(EntityNames.ES_SAPLANE_NAME, id));
        mSaplaneService.save(DataTransformator.transformEntityToSaplane(entity));

        return entity;
    }

    public void updatePlane(EdmEntityType edmEntityType, List<UriParameter> keyParams, Entity entityUpdated, HttpMethod httpMethod)
        throws ODataApplicationException {
        final Entity entityFromDB = getPlane(edmEntityType, keyParams);//TODO wird das auch in die db gespeichert?
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
        //TODO namen von object anpassen
        //actual update - morphias uses upsert todo weiter erklären
        mSaplaneService.save(DataTransformator.transformEntityToSaplane(entityFromDB));
    }

    public void deletePlane(EdmEntityType edmEntityType, List<UriParameter> keyParams) throws ODataApplicationException {
        final Entity entity = this.getPlane(edmEntityType, keyParams);

        if (entity == null) {
            throw new ODataApplicationException("Entity not found", HttpStatusCode.NOT_FOUND.getStatusCode(), Locale.ENGLISH);
        }

        mSaplaneService.delete(DataTransformator.transformEntityToSaplane(entity));
    }

    public boolean idTaken(String idToCheckIfTaken) {//TODO VErschieben?
        //TODO use instance of
        return !StringUtils.isEmpty(idToCheckIfTaken) && mSaplaneService.idTaken(idToCheckIfTaken);
    }

    // ========================================================================
    //                           NAVIGATION
    // ========================================================================

    public EntityCollection getPlaneForFlight(Entity sourceEntity, EntityCollection navigationTargetEntityCollection) {
        final String planeType = (String) sourceEntity.getProperty(EntityNames.PLANE_TYPE).getValue();
        //        final Saplane saplane = mDatabaseHandler.findPlaneForFlight(planeType);
        //        final Saplane saplane = mSaplaneService.getById(planeType);
        final Saplane saplane = mSaplaneService.findPlaneByPlaneType(planeType);
        final Entity plane = DataTransformator.transformSaplaneToEntity(saplane);

        navigationTargetEntityCollection.getEntities().add(plane);

        return navigationTargetEntityCollection;
    }

}
