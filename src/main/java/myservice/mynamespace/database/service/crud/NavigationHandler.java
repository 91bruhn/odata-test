////////////////////////////////////////////////////////////////////////////////
//
// Created by BBruhns on 10.03.2018.
//
// Copyright (c) 2006 - 2018 FORCAM GmbH. All rights reserved.
////////////////////////////////////////////////////////////////////////////////

package myservice.mynamespace.database.service.crud;

import myservice.mynamespace.database.service.tmp2.EntityBookingService;
import myservice.mynamespace.database.service.tmp2.EntityCarrierService;
import myservice.mynamespace.database.service.tmp2.EntityConnectionService;
import myservice.mynamespace.database.service.tmp2.EntityFlightService;
import myservice.mynamespace.database.service.tmp2.EntityPlaneService;
import myservice.mynamespace.util.Util;
import org.apache.olingo.commons.api.data.Entity;
import org.apache.olingo.commons.api.data.EntityCollection;
import org.apache.olingo.commons.api.edm.EdmEntityType;
import org.apache.olingo.commons.api.edm.FullQualifiedName;
import org.apache.olingo.server.api.ODataApplicationException;
import org.apache.olingo.server.api.uri.UriParameter;

import java.util.List;

import static myservice.mynamespace.service.entities.definitions.EntityNames.ET_SAPLANE_FQN;
import static myservice.mynamespace.service.entities.definitions.EntityNames.ET_SBOOK_FQN;
import static myservice.mynamespace.service.entities.definitions.EntityNames.ET_SCARR_FQN;
import static myservice.mynamespace.service.entities.definitions.EntityNames.ET_SFLIGHT_FQN;
import static myservice.mynamespace.service.entities.definitions.EntityNames.ET_SPFLI_FQN;

/**
 *
 */
public class NavigationHandler {

    // ------------------------------------------------------------------------
    // constants
    // ------------------------------------------------------------------------

    // ------------------------------------------------------------------------
    // members
    // ------------------------------------------------------------------------

    private EntityFlightService mEntityFlightService;
    private EntityBookingService mEntityBookingService;
    private EntityConnectionService mEntityConnectionService;
    private EntityPlaneService mEntityPlaneService;
    private EntityCarrierService mEntityCarrierService;

    // ------------------------------------------------------------------------
    // constructors
    // ------------------------------------------------------------------------

    public NavigationHandler() {
        mEntityFlightService = new EntityFlightService();
        mEntityBookingService = new EntityBookingService();
        mEntityConnectionService = new EntityConnectionService();
        mEntityPlaneService = new EntityPlaneService();
        mEntityCarrierService = new EntityCarrierService();

    }

    // ------------------------------------------------------------------------
    // methods
    // ------------------------------------------------------------------------

    ////////////////////////Navigation/////////////////////////TODO extra Klasse für NavigationService??
    //nur ein ergebnis kann zurückkomen vom aufruf
    public Entity getRelatedEntity(Entity entity, EdmEntityType relatedEntityType) {
        final EntityCollection collection = this.getRelatedEntityCollection(entity, relatedEntityType);

        if (collection.getEntities().isEmpty()) {
            return null;
        }

        return collection.getEntities().get(0);//TODO check, ists immer das 1.?
    }

    public Entity getRelatedEntity(Entity entity, EdmEntityType relatedEntityType, List<UriParameter> keyPredicates) throws ODataApplicationException {
        final EntityCollection relatedEntities = this.getRelatedEntityCollection(entity, relatedEntityType);

        return Util.findEntity(relatedEntityType, relatedEntities, keyPredicates);
    }

    public EntityCollection getRelatedEntityCollection(Entity sourceEntity, EdmEntityType targetEntityType) {
        final String sourceEntityFqn = sourceEntity.getType();
        final boolean navFromFlights = sourceEntityFqn.equals(ET_SFLIGHT_FQN.getFullQualifiedNameAsString());
        final boolean navFromCarriers = sourceEntityFqn.equals(ET_SCARR_FQN.getFullQualifiedNameAsString());
        final boolean navFromConnections = sourceEntityFqn.equals(ET_SPFLI_FQN.getFullQualifiedNameAsString());
        final boolean navFromBookings = sourceEntityFqn.equals(ET_SBOOK_FQN.getFullQualifiedNameAsString());
        EntityCollection navigationTargetEntityCollection = new EntityCollection();
        //TODO Delete
        final boolean navFromPlanes = sourceEntityFqn.equals(ET_SAPLANE_FQN.getFullQualifiedNameAsString());

        if (navFromFlights) {
            navigationTargetEntityCollection = this.getFlightssssss(sourceEntity, targetEntityType, navigationTargetEntityCollection);
        } else if (navFromCarriers) {
            navigationTargetEntityCollection = this.getCarreirsssssss(sourceEntity, targetEntityType, navigationTargetEntityCollection);
        } else if (navFromConnections) {
            navigationTargetEntityCollection = this.getConnectionsssssss(sourceEntity, targetEntityType, navigationTargetEntityCollection);
        } else if (navFromBookings) {
            navigationTargetEntityCollection = this.getBookingssssss(sourceEntity, targetEntityType, navigationTargetEntityCollection);
        }

        if (navigationTargetEntityCollection == null) {
            //TODO LOG
            return null;
        }

        return navigationTargetEntityCollection;
    }

    private EntityCollection getFlightssssss(Entity sourceEntity, EdmEntityType targetEntityType, EntityCollection navigationTargetEntityCollection) {//TODO name
        final FullQualifiedName relatedEntityFqn = targetEntityType.getFullQualifiedName();
        final boolean navToCarrier = relatedEntityFqn.equals(ET_SCARR_FQN);
        final boolean navToConnection = relatedEntityFqn.equals(ET_SPFLI_FQN);
        final boolean navToPlane = relatedEntityFqn.equals(ET_SAPLANE_FQN);
        final boolean navToBookings = relatedEntityFqn.equals(ET_SBOOK_FQN);

        if (navToCarrier) {
            return mEntityCarrierService.getCarrierforFlight(sourceEntity, navigationTargetEntityCollection);
        } else if (navToConnection) {
            return mEntityConnectionService.getConnectionForFlight(sourceEntity, navigationTargetEntityCollection);
        } else if (navToPlane) {
            return mEntityPlaneService.getPlaneForFlight(sourceEntity, navigationTargetEntityCollection);
        } else if (navToBookings) {
            return mEntityBookingService.getBookingsForFlight(sourceEntity, navigationTargetEntityCollection);
        } else {
            return null;
        }
    }

    private EntityCollection getCarreirsssssss(Entity sourceEntity, EdmEntityType targetEntityType, EntityCollection navigationTargetEntityCollection) {
        final FullQualifiedName relatedEntityFqn = targetEntityType.getFullQualifiedName();
        final boolean navToFlights = relatedEntityFqn.equals(ET_SFLIGHT_FQN);
        final boolean navToConnections = relatedEntityFqn.equals(ET_SPFLI_FQN);
        final boolean navToBookings = relatedEntityFqn.equals(ET_SBOOK_FQN);
        //TODO implement
        final boolean navToPlane = relatedEntityFqn.equals(ET_SAPLANE_FQN);

        if (navToFlights) {
            return mEntityFlightService.getFlightsForCarrier(sourceEntity, navigationTargetEntityCollection);
        } else if (navToConnections) {
            return mEntityConnectionService.getConnectionsForCarrier(sourceEntity, navigationTargetEntityCollection);
        } else if (navToBookings) {
            return mEntityBookingService.getBookingsForCarrier(sourceEntity, navigationTargetEntityCollection);
        } else {
            return null;
        }
    }

    private EntityCollection getConnectionsssssss(Entity sourceEntity, EdmEntityType targetEntityType, EntityCollection navigationTargetEntityCollection) {
        final FullQualifiedName relatedEntityFqn = targetEntityType.getFullQualifiedName();
        final boolean navToCarrier = relatedEntityFqn.equals(ET_SCARR_FQN);
        final boolean navToFlights = relatedEntityFqn.equals(ET_SFLIGHT_FQN);
        final boolean navToBookings = relatedEntityFqn.equals(ET_SBOOK_FQN);
        //TODO implement
        final boolean navToPlane = relatedEntityFqn.equals(ET_SAPLANE_FQN);

        if (navToFlights) {
            return mEntityFlightService.getFlightsForConnection(sourceEntity, navigationTargetEntityCollection);
        } else if (navToCarrier) {
            return mEntityCarrierService.getCarrierForConnection(sourceEntity, navigationTargetEntityCollection);
        } else if (navToPlane) {
            return mEntityPlaneService.getPlaneForConnection(sourceEntity, navigationTargetEntityCollection);
        } else if (navToBookings) {
            return mEntityBookingService.getBookingsForConnection(sourceEntity, navigationTargetEntityCollection);
        } else {
            return null;
        }
    }

    private EntityCollection getBookingssssss(Entity sourceEntity, EdmEntityType targetEntityType, EntityCollection navigationTargetEntityCollection) {
        final FullQualifiedName relatedEntityFqn = targetEntityType.getFullQualifiedName();
        final boolean navToCarrier = relatedEntityFqn.equals(ET_SAPLANE_FQN);
        final boolean navToFlight = relatedEntityFqn.equals(ET_SFLIGHT_FQN);
        final boolean navToConnection = relatedEntityFqn.equals(ET_SPFLI_FQN);
        //TODO implement
        final boolean navToPlane = relatedEntityFqn.equals(ET_SAPLANE_FQN);

        if (navToFlight) {
            return mEntityFlightService.getFlightForBooking(sourceEntity, navigationTargetEntityCollection);
        } else if (navToConnection) {
            return mEntityConnectionService.getConnectionForBooking(sourceEntity, navigationTargetEntityCollection);
        } else if (navToCarrier) {
            return mEntityCarrierService.getCarrierForBooking(sourceEntity, navigationTargetEntityCollection);
        } else if (navToPlane) {
            return mEntityPlaneService.getPlaneForBooking(sourceEntity, navigationTargetEntityCollection);
        } else {
            return null;
        }
    }
}
