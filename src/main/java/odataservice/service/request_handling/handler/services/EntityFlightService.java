////////////////////////////////////////////////////////////////////////////////
//
// Created by BBruhns on 10.03.2018.
//
// Copyright (c) 2006 - 2018 FORCAM GmbH. All rights reserved.
////////////////////////////////////////////////////////////////////////////////

package odataservice.service.request_handling.handler.services;

import odataservice.database.collections.Saplane;
import odataservice.database.collections.Scarr;
import odataservice.database.collections.Sflight;
import odataservice.database.collections.Spfli;
import odataservice.database.service.DataTransformator;
import odataservice.database.service.SflightService;
import odataservice.service.entities.definitions.EntityNames;
import odataservice.service.util.Util;
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
public class EntityFlightService {

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

    public Entity getFlight(EdmEntityType edmEntityType, List<UriParameter> keyParams) throws ODataApplicationException {
        final String carrierId = Util.getCarrierIdKey(keyParams);
        final String connectionId = Util.getConnectionIdKey(keyParams);
        final String flDate = Util.getFlightDateKey(keyParams);

        final Sflight sflight = mSflightService.findFlightByCarrierIdAndConnectionIdAndFlDate(carrierId, connectionId, flDate);

        return DataTransformator.transformSflightToEntity(sflight);
    }

    public Entity createFlight(EdmEntityType edmEntityType, Entity entity) {
        final Property idProperty = entity.getProperty(EntityNames.FLIGHT_DATE);
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
        entity.setId(Util.createId(EntityNames.ES_SFLIGHT_NAME, id));

        final String carrierId = (String) entity.getProperty(EntityNames.CARRIER_ID).getValue();
        final String connectionId = (String) entity.getProperty(EntityNames.CONNECTION_ID).getValue();
        final String planeType = (String) entity.getProperty(EntityNames.PLANE_TYPE).getValue();

        final Scarr scarr = Util.loadAssociatedCarrier(carrierId);
        final Spfli spfli = Util.loadAssociatedConnection(connectionId);
        final Saplane saplane = Util.loadAssociatedPlane(planeType);

        mSflightService.save(DataTransformator.transformEntityToSflight(entity, scarr, spfli, saplane));

        return entity;
    }

    public void updateFlight(EdmEntityType edmEntityType, List<UriParameter> keyParams, Entity entityUpdated, HttpMethod httpMethod)
        throws ODataApplicationException {

        final Entity entityFromDB = getFlight(edmEntityType, keyParams);//TODO wird das auch in die db gespeichert?
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
        final Scarr scarr = Util.loadAssociatedCarrier((String) entityUpdated.getProperty(EntityNames.CARRIER_ID).getValue());
        final Spfli spfli = Util.loadAssociatedConnection((String) entityUpdated.getProperty(EntityNames.CONNECTION_ID).getValue());
        final Saplane saplane = Util.loadAssociatedPlane((String) entityUpdated.getProperty(EntityNames.PLANE_TYPE).getValue());
        //TODO namen von object anpassen
        //actual update - morphias uses upsert todo weiter erklären
        mSflightService.save(DataTransformator.transformEntityToSflight(entityFromDB, scarr, spfli, saplane));
    }

    public void deleteFlight(EdmEntityType edmEntityType, List<UriParameter> keyParams) throws ODataApplicationException {
        final Entity entity = this.getFlight(edmEntityType, keyParams);

        if (entity == null) {
            throw new ODataApplicationException("Entity not found", HttpStatusCode.NOT_FOUND.getStatusCode(), Locale.ENGLISH);
        }

        final Scarr scarr = Util.loadAssociatedCarrier((String) entity.getProperty(EntityNames.CARRIER_ID).getValue());
        final Spfli spfli = Util.loadAssociatedConnection((String) entity.getProperty(EntityNames.CONNECTION_ID).getValue());
        final Saplane saplane = Util.loadAssociatedPlane((String) entity.getProperty(EntityNames.PLANE_TYPE).getValue());

        mSflightService.delete(DataTransformator.transformEntityToSflight(entity, scarr, spfli, saplane));
    }

    // ========================================================================
    //                           NAVIGATION
    // ========================================================================

    public EntityCollection getFlightsForCarrier(Entity sourceEntity, EntityCollection navigationTargetEntityCollection) {//TODO name
        final String carrierCode = (String) sourceEntity.getProperty(EntityNames.CARRIER_ID).getValue();
        final List<Sflight> sflights = mSflightService.findFlightsByCarrierId(carrierCode);
        final List<Entity> flights = (sflights.stream().map(DataTransformator::transformSflightToEntity).collect(Collectors.toList()));

        navigationTargetEntityCollection.getEntities().addAll(flights);

        return navigationTargetEntityCollection;
    }

    public EntityCollection getFlightsForConnection(Entity sourceEntity, EntityCollection navigationTargetEntityCollection) {
        final String carrierId = (String) sourceEntity.getProperty(EntityNames.CARRIER_ID).getValue();
        final String connectionId = (String) sourceEntity.getProperty(EntityNames.CONNECTION_ID).getValue();
        final List<Sflight> sflights = mSflightService.findFlightsByCarrierIdAndConnectionId(carrierId, connectionId);
        final List<Entity> flights = sflights.stream().map(DataTransformator::transformSflightToEntity).collect(Collectors.toList());

        navigationTargetEntityCollection.getEntities().addAll(flights);

        return navigationTargetEntityCollection;
    }

    public EntityCollection getFlightForBooking(Entity sourceEntity, EntityCollection navigationTargetEntityCollection) {
        final String carrierId = (String) sourceEntity.getProperty(EntityNames.CARRIER_ID).getValue();
        final String connectionId = (String) sourceEntity.getProperty(EntityNames.CONNECTION_ID).getValue();
        final String flDate = (String) sourceEntity.getProperty(EntityNames.FLIGHT_DATE).getValue();
        final Sflight sflight = mSflightService.findFlightByCarrierIdAndConnectionIdAndFlDate(carrierId, connectionId, flDate);
        final Entity flight = DataTransformator.transformSflightToEntity(sflight);

        navigationTargetEntityCollection.getEntities().add(flight);

        return navigationTargetEntityCollection;
    }

}
