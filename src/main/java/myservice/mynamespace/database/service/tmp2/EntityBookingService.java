////////////////////////////////////////////////////////////////////////////////
//
// Created by BBruhns on 10.03.2018.
//
// Copyright (c) 2006 - 2018 FORCAM GmbH. All rights reserved.
////////////////////////////////////////////////////////////////////////////////

package myservice.mynamespace.database.service.tmp2;

import myservice.mynamespace.database.collections.Saplane;
import myservice.mynamespace.database.collections.Sbook;
import myservice.mynamespace.database.service.DataTransformator;
import myservice.mynamespace.database.service.tmp.SbookService;
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

/**
 *
 */
public class EntityBookingService extends AbstractEntityService {

    // ------------------------------------------------------------------------
    // constants
    // ------------------------------------------------------------------------

    // ------------------------------------------------------------------------
    // members
    // ------------------------------------------------------------------------

    private SbookService mSbookService;

    // ------------------------------------------------------------------------
    // constructors
    // ------------------------------------------------------------------------

    public EntityBookingService() {
        mSbookService = new SbookService();
    }

    // ------------------------------------------------------------------------
    // methods
    // ------------------------------------------------------------------------

    public EntityCollection getBookings() {
        final List<Sbook> sbooks = mSbookService.getAllSbooks();
        final List<Entity> bookings = sbooks.stream().map(DataTransformator::transformSbookToEntity).collect(Collectors.toList());
        final EntityCollection entitySet = new EntityCollection();
        entitySet.getEntities().addAll(bookings);

        return entitySet;
    }

    public Entity getBooking(EdmEntityType edmEntityType, List<UriParameter> keyParams) throws ODataApplicationException {
        // the list of entities at runtime
        final EntityCollection entitySet = this.getBookings();

    /* generic approach to find the requested entity */
        return Util.findEntity(edmEntityType, entitySet, keyParams);
    }

    //TODO alle verschieben
    public Entity createBooking(EdmEntityType edmEntityType, Entity entity) {
        final Property idProperty = entity.getProperty(EntityNames.PLANE_TYPE);
        final String id;

        if (idProperty != null) {
            final String bookId = (String) idProperty.getValue();

            if (this.idTaken(bookId)) {
                //TODO LOG plane already defined in db
                return null;
            } else {
                id = bookId;
            }
            idProperty.setValue(ValueType.PRIMITIVE, id);//TODO was macht das?
        } else {
            return null;
        }
        entity.setId(super.createId("Carriers", id));
        //        mSbookService.save(DataTransformator.transformEntityToSaplane(entity)); TODO implement

        return entity;
    }

    public void updateBooking(EdmEntityType edmEntityType, List<UriParameter> keyParams, Entity entity, HttpMethod httpMethod)
        throws ODataApplicationException {
    }

    public void deleteBooking(EdmEntityType edmEntityType, List<UriParameter> keyParams) throws ODataApplicationException {
        final Entity productEntity = this.getBooking(edmEntityType, keyParams);
        if (productEntity == null) {
            throw new ODataApplicationException("Entity not found", HttpStatusCode.NOT_FOUND.getStatusCode(), Locale.ENGLISH);
        }
    }

    public boolean idTaken(String idToCheckIfTaken) {//TODO VErschieben?
        //TODO use instance of
        return !StringUtils.isEmpty(idToCheckIfTaken) && mSbookService.idTaken(idToCheckIfTaken);
    }

    //NAVIGATION
    public EntityCollection getBookingsForFlight(Entity sourceEntity, EntityCollection navigationTargetEntityCollection) {
        final String carrierId = (String) sourceEntity.getProperty(CARRIER_ID).getValue();
        final String connectionId = (String) sourceEntity.getProperty(CONNECTION_ID).getValue();
        final String fldate = (String) sourceEntity.getProperty(FLIGHT_DATE).getValue();
        final List<Sbook> sbooks = mSbookService.findBookingsByCarrierIdAndConnectionIdAndFlDate(carrierId, connectionId, fldate);
        final List<Entity> bookings = sbooks.stream().map(DataTransformator::transformSbookToEntity).collect(Collectors.toList());

        navigationTargetEntityCollection.getEntities().addAll(bookings);

        return navigationTargetEntityCollection;
    }

    public EntityCollection getBookingsForCarrier(Entity sourceEntity, EntityCollection navigationTargetEntityCollection) {
        final String carrierId = (String) sourceEntity.getProperty(CARRIER_ID).getValue();
        final List<Sbook> sbooks = mSbookService.findBookingsByCarrierId(carrierId);
        final List<Entity> bookings = sbooks.stream().map(DataTransformator::transformSbookToEntity).collect(Collectors.toList());

        navigationTargetEntityCollection.getEntities().addAll(bookings);

        return navigationTargetEntityCollection;

    }

    public EntityCollection getBookingsForConnection(Entity sourceEntity, EntityCollection navigationTargetEntityCollection) {
        final String connectionId = (String) sourceEntity.getProperty(CONNECTION_ID).getValue();
        final List<Sbook> sbooks = mSbookService.findBookingsByConnectionId(connectionId);
        final List<Entity> bookings = sbooks.stream().map(DataTransformator::transformSbookToEntity).collect(Collectors.toList());

        navigationTargetEntityCollection.getEntities().addAll(bookings);

        return navigationTargetEntityCollection;
    }
}
