////////////////////////////////////////////////////////////////////////////////
//
// Created by BBruhns on 10.03.2018.
//
// Copyright (c) 2006 - 2018 FORCAM GmbH. All rights reserved.
////////////////////////////////////////////////////////////////////////////////

package myservice.mynamespace.database.service.tmp2;

import myservice.mynamespace.database.collections.Saplane;
import myservice.mynamespace.database.collections.Scarr;
import myservice.mynamespace.database.collections.Sflight;
import myservice.mynamespace.database.collections.Spfli;
import myservice.mynamespace.database.service.DataTransformator;
import myservice.mynamespace.database.service.tmp.SflightService;
import myservice.mynamespace.util.Util;
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
import static myservice.mynamespace.service.entities.definitions.EntityNames.ES_SFLIGHT_NAME;
import static myservice.mynamespace.service.entities.definitions.EntityNames.FLIGHT_DATE;
import static myservice.mynamespace.service.entities.definitions.EntityNames.PLANE_TYPE;

/**
 *
 */
public class EntityFlightService extends AbstractEntityService {

    // ------------------------------------------------------------------------
    // constants
    // ------------------------------------------------------------------------

    // ------------------------------------------------------------------------
    // members
    // ------------------------------------------------------------------------

    private SflightService mSflightService;

    // ------------------------------------------------------------------------
    // constructors
    // ------------------------------------------------------------------------

    public EntityFlightService() {
        mSflightService = new SflightService();
    }

    // ------------------------------------------------------------------------
    // methods
    // ------------------------------------------------------------------------

    public EntityCollection getFlights() {//TODO UMWANDLUNG AN DIESER STELLE
        final List<Sflight> sflights = mSflightService.getAllSflights();
        final List<Entity> flights = sflights.stream().map(DataTransformator::transformSflightToEntity).collect(Collectors.toList());
        final EntityCollection entitySet = new EntityCollection();

        entitySet.getEntities().addAll(flights);

        return entitySet;
    }

    public Entity getFlight(EdmEntityType edmEntityType, List<UriParameter> keyParams) throws ODataApplicationException {//TODO rename
        // the list of entities at runtime
        final EntityCollection entityCollection = this.getFlights();
        /* generic approach to find the requested entity */
        return Util.findEntity(edmEntityType, entityCollection, keyParams);
    }

    public Entity createFlight(EdmEntityType edmEntityType, Entity entity) {
        final Property idProperty = entity.getProperty(FLIGHT_DATE);
        final String id;

        if (idProperty != null) {
            final String flDate = (String) idProperty.getValue();

            if (Util.idTaken(flDate, mSflightService)) {//todo tag ist nicht so eindeutig sollte vllt zu normaler var.
                //TODO LOG plane already defined in db
                return null;
            } else {
                id = flDate;
            }
            idProperty.setValue(ValueType.PRIMITIVE, id);
        } else {
            return null;
        }
        entity.setId(Util.createId(ES_SFLIGHT_NAME, id));//TODO load corresponding connectoin und carrier und plane

        final String carrierId = (String) entity.getProperty(CARRIER_ID).getValue();
        final String connectionId = (String) entity.getProperty(CONNECTION_ID).getValue();
        final String planeType = (String) entity.getProperty(PLANE_TYPE).getValue();

        final Scarr scarr = Util.loadAssociatedCarrier(carrierId);
        final Spfli spfli = Util.loadAssociatedConnection(connectionId);
        final Saplane saplane = Util.loadAssociatedPlane(planeType);

        mSflightService.save(DataTransformator.transformEntityToSflight(entity, scarr, spfli, saplane));

        return entity;
    }

    public void updateFlight(EdmEntityType edmEntityType, List<UriParameter> keyParams, Entity entity, HttpMethod httpMethod) throws ODataApplicationException {
    }

    public void deleteFlight(EdmEntityType edmEntityType, List<UriParameter> keyParams) throws ODataApplicationException {
        final Entity entity = this.getFlight(edmEntityType, keyParams);

        if (entity == null) {
            throw new ODataApplicationException("Entity not found", HttpStatusCode.NOT_FOUND.getStatusCode(), Locale.ENGLISH);
        }

        final Scarr scarr = Util.loadAssociatedCarrier((String) entity.getProperty(CARRIER_ID).getValue());
        final Spfli spfli = Util.loadAssociatedConnection((String) entity.getProperty(CONNECTION_ID).getValue());
        final Saplane saplane = Util.loadAssociatedPlane((String) entity.getProperty(PLANE_TYPE).getValue());

        mSflightService.delete(DataTransformator.transformEntityToSflight(entity, scarr, spfli, saplane));
    }

    //NAVIGATION
    public EntityCollection getFlightsForCarrier(Entity sourceEntity, EntityCollection navigationTargetEntityCollection) {//TODO name
        final String carrierCode = (String) sourceEntity.getProperty(CARRIER_ID).getValue();
        final List<Sflight> sflights = mSflightService.findFlightsByCarrierId(carrierCode);
        final List<Entity> flights = (sflights.stream().map(DataTransformator::transformSflightToEntity).collect(Collectors.toList()));

        navigationTargetEntityCollection.getEntities().addAll(flights);

        return navigationTargetEntityCollection;
    }

    public EntityCollection getFlightsForConnection(Entity sourceEntity, EntityCollection navigationTargetEntityCollection) {
        final String carrierId = (String) sourceEntity.getProperty(CARRIER_ID).getValue();
        final String connectionId = (String) sourceEntity.getProperty(CONNECTION_ID).getValue();
        final List<Sflight> sflights = mSflightService.findFlightsByCarrierIdAndConnectionId(carrierId, connectionId);
        final List<Entity> flights = sflights.stream().map(DataTransformator::transformSflightToEntity).collect(Collectors.toList());

        navigationTargetEntityCollection.getEntities().addAll(flights);

        return navigationTargetEntityCollection;
    }

    public EntityCollection getFlightForBooking(Entity sourceEntity, EntityCollection navigationTargetEntityCollection) {
        final String carrierId = (String) sourceEntity.getProperty(CARRIER_ID).getValue();
        final String connectionId = (String) sourceEntity.getProperty(CONNECTION_ID).getValue();
        final String flDate = (String) sourceEntity.getProperty(FLIGHT_DATE).getValue();
        final Sflight sflight = mSflightService.findFlightByCarrierIdAndConnectionIdAndFlDate(carrierId, connectionId, flDate);
        final Entity flight = DataTransformator.transformSflightToEntity(sflight);

        navigationTargetEntityCollection.getEntities().add(flight);

        return navigationTargetEntityCollection;
    }

}
