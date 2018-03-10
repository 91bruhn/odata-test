////////////////////////////////////////////////////////////////////////////////
//
// Created by BBruhns on 10.03.2018.
//
// Copyright (c) 2006 - 2018 FORCAM GmbH. All rights reserved.
////////////////////////////////////////////////////////////////////////////////

package myservice.mynamespace.database.service.tmp2;

import myservice.mynamespace.database.collections.Saplane;
import myservice.mynamespace.database.service.DataTransformator;
import myservice.mynamespace.database.service.tmp.SaplaneService;
import myservice.mynamespace.service.entities.definitions.EntityNames;
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
import static myservice.mynamespace.service.entities.definitions.EntityNames.FLIGHT_DATE;
import static myservice.mynamespace.service.entities.definitions.EntityNames.PLANE_TYPE;

/**
 *
 */
public class EntityPlaneService extends AbstractEntityService {

    // ------------------------------------------------------------------------
    // constants
    // ------------------------------------------------------------------------

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

        // the list of entities at runtime
        final EntityCollection entitySet = this.getPlanes();

    /* generic approach to find the requested entity */
        return Util.findEntity(edmEntityType, entitySet, keyParams);
    }

    //übergebene id muss vorhanden und gültig sein ansonsten null --> service sollte folgenden http
    public Entity createPlane(EdmEntityType edmEntityType, Entity entity) {
        final Property idProperty = entity.getProperty(EntityNames.PLANE_TYPE);
        final String id;

        if (idProperty != null) {
            final String planeType = (String) idProperty.getValue();

            if (this.idTaken(Saplane.class, planeType)) {
                //LOG plane already defined in db
                return null;
            } else {
                id = planeType;
            }
            idProperty.setValue(ValueType.PRIMITIVE, id);//TODO was macht das?
        } else {
            return null;
        }
        entity.setId(super.createId("Carriers", id));
        mSaplaneService.save(DataTransformator.transformEntityToSaplane(entity));

        return entity;
    }

    public void updatePlane(EdmEntityType edmEntityType, List<UriParameter> keyParams, Entity entity, HttpMethod httpMethod) throws ODataApplicationException {
    }

    public void deletePlane(EdmEntityType edmEntityType, List<UriParameter> keyParams) throws ODataApplicationException {
        final Entity productEntity = this.getPlane(edmEntityType, keyParams);
        if (productEntity == null) {
            throw new ODataApplicationException("Entity not found", HttpStatusCode.NOT_FOUND.getStatusCode(), Locale.ENGLISH);
        }
    }

    public boolean idTaken(Class clazz, String idToCheckIfTaken) {//TODO VErschieben?
        //TODO use instance of
        return !StringUtils.isEmpty(idToCheckIfTaken) && mSaplaneService.idTaken(idToCheckIfTaken);
    }

    //NAVIGATION
    public EntityCollection getPlaneForFlight(Entity sourceEntity, EntityCollection navigationTargetEntityCollection) {
        final String planeType = (String) sourceEntity.getProperty(PLANE_TYPE).getValue();
        //        final Saplane saplane = mDatabaseHandler.findPlaneForFlight(planeType);
        final Saplane saplane = (Saplane) mSaplaneService.getById(planeType);
        final Entity plane = DataTransformator.transformSaplaneToEntity(saplane);

        navigationTargetEntityCollection.getEntities().add(plane);

        return navigationTargetEntityCollection;
    }

    public EntityCollection getPlaneForConnection(Entity sourceEntity, EntityCollection navigationTargetEntityCollection) {
        final String connectionId = (String) sourceEntity.getProperty(CONNECTION_ID).getValue();
        final Saplane saplane = (Saplane) mSaplaneService.getById(connectionId);
        final Entity plane = DataTransformator.transformSaplaneToEntity(saplane);

        navigationTargetEntityCollection.getEntities().add(plane);

        return navigationTargetEntityCollection;
    }

    public EntityCollection getPlaneForBooking(Entity sourceEntity, EntityCollection navigationTargetEntityCollection) {
        final String carrierId = (String) sourceEntity.getProperty(CARRIER_ID).getValue();
        final String connectionId = (String) sourceEntity.getProperty(CONNECTION_ID).getValue();
        final String flDate = (String) sourceEntity.getProperty(FLIGHT_DATE).getValue();
        final Saplane saplane = mSaplaneService.findPlaneByCarrierIdAndConnectionIdAndFlDate(carrierId, connectionId, flDate);
        final Entity plane = DataTransformator.transformSaplaneToEntity(saplane);

        navigationTargetEntityCollection.getEntities().add(plane);

        return navigationTargetEntityCollection;
    }

}
