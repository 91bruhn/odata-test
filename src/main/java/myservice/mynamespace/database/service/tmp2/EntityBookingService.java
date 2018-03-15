////////////////////////////////////////////////////////////////////////////////
//
// Created by BBruhns on 10.03.2018.
//
// Copyright (c) 2006 - 2018 FORCAM GmbH. All rights reserved.
////////////////////////////////////////////////////////////////////////////////

package myservice.mynamespace.database.service.tmp2;

import myservice.mynamespace.database.collections.Sbook;
import myservice.mynamespace.database.collections.Scarr;
import myservice.mynamespace.database.collections.Sflight;
import myservice.mynamespace.database.collections.Spfli;
import myservice.mynamespace.database.service.DataTransformator;
import myservice.mynamespace.database.service.tmp.SbookService;
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

import static myservice.mynamespace.service.entities.definitions.EntityNames.BOOKING_ID;
import static myservice.mynamespace.service.entities.definitions.EntityNames.CARRIER_ID;
import static myservice.mynamespace.service.entities.definitions.EntityNames.CONNECTION_ID;
import static myservice.mynamespace.service.entities.definitions.EntityNames.ES_SBOOK_NAME;
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
        final List<Sbook> sbooks = mSbookService.getAllSbooks();//TODO whs bei allen variablen falsch konf? da kein type bei enum wert fuer property
        final List<Entity> bookings = sbooks.stream().map(DataTransformator::transformSbookToEntity).collect(Collectors.toList());
        final EntityCollection entitySet = new EntityCollection();
        entitySet.getEntities().addAll(bookings);

        return entitySet;
    }

    public Entity getBooking(EdmEntityType edmEntityType, List<UriParameter> keyParams) throws ODataApplicationException {
        // the list of entities at runtime
        //        final EntityCollection entitySet = this.getBookings();


    /* generic approach to find the requested entity */
        //        return Util.findEntity(edmEntityType, entitySet, keyParams);//TODO make use of real DB stuff

        Sbook sbook = mSbookService.getById("dfd");

        return DataTransformator.transformSbookToEntity(sbook);
    }

    //TODO alle verschieben
    public Entity createBooking(EdmEntityType edmEntityType, Entity entity) {
        final Property idProperty = entity.getProperty(BOOKING_ID);
        final String id;

        if (idProperty != null) {
            final String givenBookId = (String) idProperty.getValue();

            if (Util.idTaken(givenBookId, mSbookService)) {
                id = Util.generateId(givenBookId, 10, true, true, mSbookService);
            } else {
                id = givenBookId;
            }
            idProperty.setValue(ValueType.PRIMITIVE, id);
        } else {
            id = Util.generateId(StringUtils.EMPTY, 10, true, true, mSbookService);
            entity.getProperties().add(new Property(null, BOOKING_ID, ValueType.PRIMITIVE, id));
        }
        entity.setId(Util.createId(ES_SBOOK_NAME, id));

        final String carrierId = (String) entity.getProperty(CARRIER_ID).getValue();
        final String connectionId = (String) entity.getProperty(CONNECTION_ID).getValue();
        final String flDate = (String) entity.getProperty(FLIGHT_DATE).getValue();

        final Scarr scarr = Util.loadAssociatedCarrier(carrierId);
        final Spfli spfli = Util.loadAssociatedConnection(connectionId);
        final Sflight sflight = Util.loadAssociatedFlight(flDate);

        mSbookService.save(DataTransformator.transformEntityToSbook(entity, scarr, spfli, sflight));

        return entity;
    }

    public void updateBooking(EdmEntityType edmEntityType, List<UriParameter> keyParams, Entity entity, HttpMethod httpMethod)
        throws ODataApplicationException {
    }

    public void deleteBooking(EdmEntityType edmEntityType, List<UriParameter> keyParams) throws ODataApplicationException {
        final Entity entity = this.getBooking(edmEntityType, keyParams);

        if (entity == null) {
            throw new ODataApplicationException("Entity not found", HttpStatusCode.NOT_FOUND.getStatusCode(), Locale.ENGLISH);
        }

        final String carrierId = (String) entity.getProperty(CARRIER_ID).getValue();
        final String connectionId = (String) entity.getProperty(CONNECTION_ID).getValue();
        final String flDate = (String) entity.getProperty(FLIGHT_DATE).getValue();

        final Scarr scarr = Util.loadAssociatedCarrier(carrierId);
        final Spfli spfli = Util.loadAssociatedConnection(connectionId);
        final Sflight sflight = Util.loadAssociatedFlight(flDate);

        mSbookService.delete(DataTransformator.transformEntityToSbook(entity, scarr, spfli, sflight));
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
