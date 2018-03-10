package myservice.mynamespace.database.service;

import myservice.mynamespace.database.collections.Saplane;
import myservice.mynamespace.database.collections.Sbook;
import myservice.mynamespace.database.collections.Scarr;
import myservice.mynamespace.database.collections.Sflight;
import myservice.mynamespace.service.entities.definitions.EntityNames;
import myservice.mynamespace.util.Util;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.olingo.commons.api.data.Entity;
import org.apache.olingo.commons.api.data.EntityCollection;
import org.apache.olingo.commons.api.data.Property;
import org.apache.olingo.commons.api.data.ValueType;
import org.apache.olingo.commons.api.edm.EdmEntitySet;
import org.apache.olingo.commons.api.edm.EdmEntityType;
import org.apache.olingo.commons.api.edm.FullQualifiedName;
import org.apache.olingo.commons.api.ex.ODataRuntimeException;
import org.apache.olingo.commons.api.http.HttpMethod;
import org.apache.olingo.commons.api.http.HttpStatusCode;
import org.apache.olingo.server.api.ODataApplicationException;
import org.apache.olingo.server.api.uri.UriParameter;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;

import static myservice.mynamespace.service.entities.definitions.EntityNames.CARRIER_ID;
import static myservice.mynamespace.service.entities.definitions.EntityNames.CONNECTION_ID;
import static myservice.mynamespace.service.entities.definitions.EntityNames.ES_SAPLANE_NAME;
import static myservice.mynamespace.service.entities.definitions.EntityNames.ES_SBOOK_NAME;
import static myservice.mynamespace.service.entities.definitions.EntityNames.ES_SCARR_NAME;
import static myservice.mynamespace.service.entities.definitions.EntityNames.ES_SFLIGHT_NAME;
import static myservice.mynamespace.service.entities.definitions.EntityNames.ES_SPFLI_NAME;
import static myservice.mynamespace.service.entities.definitions.EntityNames.ET_SAPLANE_FQN;
import static myservice.mynamespace.service.entities.definitions.EntityNames.ET_SBOOK_FQN;
import static myservice.mynamespace.service.entities.definitions.EntityNames.ET_SCARR_FQN;
import static myservice.mynamespace.service.entities.definitions.EntityNames.ET_SFLIGHT_FQN;
import static myservice.mynamespace.service.entities.definitions.EntityNames.ET_SPFLI_FQN;
import static myservice.mynamespace.service.entities.definitions.EntityNames.FLIGHT_DATE;

/**
 *
 */
public class CRUDHandler {//Service?

    private DatabaseHandler mDatabaseHandler;

    public CRUDHandler() {
        mDatabaseHandler = new DatabaseHandler();
    }

  /* PUBLIC FACADE */

    public EntityCollection readEntitySetData(EdmEntitySet edmEntitySet) {//TODO USE ABSTRACT CRUD CLASS

        switch (edmEntitySet.getName()) {
            case ES_SFLIGHT_NAME:
                return ReadEntitySetData.getFlights();
            case ES_SPFLI_NAME:
                return ReadEntitySetData.getConnections();
            case ES_SCARR_NAME:
                return ReadEntitySetData.getCarriers();
            case ES_SBOOK_NAME:
                return ReadEntitySetData.getBookings();
            case ES_SAPLANE_NAME:
                return ReadEntitySetData.getPlanes();
            default:
                return null;
        }
    }

    //TODO überall throws? checke andere IMPL.
    public Entity readEntityData(EdmEntitySet edmEntitySet, List<UriParameter> keyParams) throws ODataApplicationException {
        final EdmEntityType edmEntityType = edmEntitySet.getEntityType();

        switch (edmEntitySet.getName()) {
            case ES_SFLIGHT_NAME:
                return ReadEntityData.getFlight(edmEntityType, keyParams);
            case ES_SPFLI_NAME:
                return ReadEntityData.getConnection(edmEntityType, keyParams);
            case ES_SCARR_NAME:
                return ReadEntityData.getCarrier(edmEntityType, keyParams);
            case ES_SBOOK_NAME:
                return ReadEntityData.getBooking(edmEntityType, keyParams);
            case ES_SAPLANE_NAME:
                return ReadEntityData.getPlane(edmEntityType, keyParams);
            default:
                return null;
        }
    }

    public Entity createEntityData(EdmEntitySet edmEntitySet, Entity entityToCreate) {
        final EdmEntityType edmEntityType = edmEntitySet.getEntityType();

        switch (edmEntitySet.getName()) {
            case ES_SFLIGHT_NAME:
                return createFlight(edmEntityType, entityToCreate);
            case ES_SPFLI_NAME:
                return createConnection(edmEntityType, entityToCreate);
            case ES_SCARR_NAME:
                return createCarrier(edmEntityType, entityToCreate);
            case ES_SBOOK_NAME:
                return createBooking(edmEntityType, entityToCreate);
            case ES_SAPLANE_NAME:
                return createPlane(edmEntityType, entityToCreate);
            default:
                return null;
        }
    }

    public void updateEntityData(EdmEntitySet edmEntitySet, List<UriParameter> keyParams, Entity updateEntity, HttpMethod httpMethod)
        throws ODataApplicationException {
        final EdmEntityType edmEntityType = edmEntitySet.getEntityType();

        switch (edmEntitySet.getName()) {
            case ES_SFLIGHT_NAME:
                this.updateFlight(edmEntityType, keyParams, updateEntity, httpMethod);
            case ES_SPFLI_NAME:
                this.updateConnection(edmEntityType, keyParams, updateEntity, httpMethod);
            case ES_SCARR_NAME:
                this.updateCarrier(edmEntityType, keyParams, updateEntity, httpMethod);
            case ES_SBOOK_NAME:
                this.updateBooking(edmEntityType, keyParams, updateEntity, httpMethod);
            case ES_SAPLANE_NAME:
                this.updatePlane(edmEntityType, keyParams, updateEntity, httpMethod);
        }
    }

    public void deleteEntityData(EdmEntitySet edmEntitySet, List<UriParameter> keyParams) throws ODataApplicationException {
        final EdmEntityType edmEntityType = edmEntitySet.getEntityType();

        switch (edmEntitySet.getName()) {
            case ES_SFLIGHT_NAME:
                this.deleteFlight(edmEntityType, keyParams);
            case ES_SPFLI_NAME:
                this.deleteConnection(edmEntityType, keyParams);
            case ES_SCARR_NAME:
                this.deleteCarrier(edmEntityType, keyParams);
            case ES_SBOOK_NAME:
                this.deleteBooking(edmEntityType, keyParams);
            case ES_SAPLANE_NAME:
                this.deletePlane(edmEntityType, keyParams);
        }
    }

    private Entity createCarrier(EdmEntityType edmEntityType, Entity entity) {//TODO fall das werte die ich brauche null sind
        final Property idProperty = entity.getProperty(EntityNames.CARRIER_ID);
        final String carrierName = (String) entity.getProperty(EntityNames.CARRIER_NAME).getValue();
        final String carrierId;

        if (idProperty != null) {
            final String carrierCode = (String) idProperty.getValue();

            if (idTaken(Scarr.class, carrierCode)) {
                carrierId = generateScarrId(carrierName);
            } else {
                carrierId = carrierCode;
            }
            idProperty.setValue(ValueType.PRIMITIVE, carrierId);//TODO was macht das?
        } else {
            //as if OData v4 spec, the key property can be omitted from the POST request body
            carrierId = generateScarrId(carrierName);
            entity.getProperties().add(new Property(null, EntityNames.CARRIER_ID, ValueType.PRIMITIVE, carrierId));
        }
        entity.setId(createId("Carriers", carrierId));
        mDatabaseHandler.saveData(DataTransformator.transformEntityToScarr(entity));

        return entity;
    }

    //    private String id(Property idProperty){
    //        if (idProperty != null) {
    //            final String planeType = (String) idProperty.getValue();
    //
    //            if (idTaken(Saplane.class, planeType)) {
    //                //LOG plane already defined in db
    //                return null;
    //            } else {
    //                id = planeType;
    //            }
    //            idProperty.setValue(ValueType.PRIMITIVE, id);//TODO was macht das?
    //        } else {
    //            return null;
    //        }
    //    }

    //übergebene id muss vorhanden und gültig sein ansonsten null --> service sollte folgenden http
    private Entity createPlane(EdmEntityType edmEntityType, Entity entity) {
        final Property idProperty = entity.getProperty(EntityNames.PLANE_TYPE);
        final String id;

        if (idProperty != null) {
            final String planeType = (String) idProperty.getValue();

            if (idTaken(Saplane.class, planeType)) {
                //LOG plane already defined in db
                return null;
            } else {
                id = planeType;
            }
            idProperty.setValue(ValueType.PRIMITIVE, id);//TODO was macht das?
        } else {
            return null;
        }
        entity.setId(createId("Carriers", id));
        mDatabaseHandler.saveData(DataTransformator.transformEntityToSaplane(entity));

        return entity;
    }

    private Entity createFlight(EdmEntityType edmEntityType, Entity entity) {
        final Property idProperty = entity.getProperty(EntityNames.PLANE_TYPE);
        final String id;

        if (idProperty != null) {
            final String planeType = (String) idProperty.getValue();

            if (idTaken(Saplane.class, planeType)) {
                //LOG plane already defined in db
                return null;
            } else {
                id = planeType;
            }
            idProperty.setValue(ValueType.PRIMITIVE, id);//TODO was macht das?
        } else {
            return null;
        }
        entity.setId(createId("Carriers", id));
        mDatabaseHandler.saveData(DataTransformator.transformEntityToScarr(entity));

        return entity;
    }

    //TODO alle verschieben
    private Entity createBooking(EdmEntityType edmEntityType, Entity entity) {
        final Property idProperty = entity.getProperty(EntityNames.PLANE_TYPE);
        final String id;

        if (idProperty != null) {
            final String planeType = (String) idProperty.getValue();

            if (idTaken(Saplane.class, planeType)) {
                //LOG plane already defined in db
                return null;
            } else {
                id = planeType;
            }
            idProperty.setValue(ValueType.PRIMITIVE, id);//TODO was macht das?
        } else {
            return null;
        }
        entity.setId(createId("Carriers", id));
        mDatabaseHandler.saveData(DataTransformator.transformEntityToScarr(entity));

        return entity;
    }

    private Entity createConnection(EdmEntityType edmEntityType, Entity entity) {
        return null;
    }

    private void updateCarrier(EdmEntityType edmEntityType, List<UriParameter> keyParams, Entity entity, HttpMethod httpMethod)
        throws ODataApplicationException {

        //        Entity productEntity = getProduct(edmEntityType, keyParams);
        //        if (productEntity == null) {
        //            throw new ODataApplicationException("Entity not found", HttpStatusCode.NOT_FOUND.getStatusCode(), Locale.ENGLISH);
    }

    private void updateConnection(EdmEntityType edmEntityType, List<UriParameter> keyParams, Entity entity, HttpMethod httpMethod)
        throws ODataApplicationException {
    }

    private void updatePlane(EdmEntityType edmEntityType, List<UriParameter> keyParams, Entity entity, HttpMethod httpMethod) throws ODataApplicationException {
    }

    private void updateBooking(EdmEntityType edmEntityType, List<UriParameter> keyParams, Entity entity, HttpMethod httpMethod)
        throws ODataApplicationException {
    }

    private void updateFlight(EdmEntityType edmEntityType, List<UriParameter> keyParams, Entity entity, HttpMethod httpMethod)
        throws ODataApplicationException {
    }

    private void deleteCarrier(EdmEntityType edmEntityType, List<UriParameter> keyParams) throws ODataApplicationException {
        final Entity productEntity = ReadEntityData.getCarrier(edmEntityType, keyParams);
        if (productEntity == null) {
            throw new ODataApplicationException("Entity not found", HttpStatusCode.NOT_FOUND.getStatusCode(), Locale.ENGLISH);
        }

        //hole object und dann lösche es?
        //        mDatabaseHandler.deleteData();
        //        this.productList.remove(productEntity);
    }

    private void deleteConnection(EdmEntityType edmEntityType, List<UriParameter> keyParams) throws ODataApplicationException {
        final Entity productEntity = ReadEntityData.getConnection(edmEntityType, keyParams);
        if (productEntity == null) {
            throw new ODataApplicationException("Entity not found", HttpStatusCode.NOT_FOUND.getStatusCode(), Locale.ENGLISH);
        }
    }

    private void deleteFlight(EdmEntityType edmEntityType, List<UriParameter> keyParams) throws ODataApplicationException {
        final Entity productEntity = ReadEntityData.getFlight(edmEntityType, keyParams);
        if (productEntity == null) {
            throw new ODataApplicationException("Entity not found", HttpStatusCode.NOT_FOUND.getStatusCode(), Locale.ENGLISH);
        }
    }

    private void deleteBooking(EdmEntityType edmEntityType, List<UriParameter> keyParams) throws ODataApplicationException {
        final Entity productEntity = ReadEntityData.getBooking(edmEntityType, keyParams);
        if (productEntity == null) {
            throw new ODataApplicationException("Entity not found", HttpStatusCode.NOT_FOUND.getStatusCode(), Locale.ENGLISH);
        }
    }

    private void deletePlane(EdmEntityType edmEntityType, List<UriParameter> keyParams) throws ODataApplicationException {
        final Entity productEntity = ReadEntityData.getPlane(edmEntityType, keyParams);
        if (productEntity == null) {
            throw new ODataApplicationException("Entity not found", HttpStatusCode.NOT_FOUND.getStatusCode(), Locale.ENGLISH);
        }
    }

    /**
     * Generates from the given carrier name a carrier id. If that generated id is taken a random five letter id will be generated.
     *
     * @param carrierName The carrier name of which a carrier id will be tried to generate.
     * @return A not taken carrier id.
     */
    private String generateScarrId(String carrierName) {
        final String carrierId;
        String idToCheckIfTaken = null;

        //try to build id from carrier name
        if (StringUtils.isNotEmpty(carrierName)) {
            final int posOfWhiteSpace = carrierName.indexOf(" ");
            Character secondLetter = null;
            if (posOfWhiteSpace != -1) {
                secondLetter = carrierName.charAt(posOfWhiteSpace + 1);
            }
            if (secondLetter == null) {
                secondLetter = carrierName.charAt(1);
            }
            final Character firstLetter = carrierName.charAt(0);
            idToCheckIfTaken = String.valueOf(firstLetter) + String.valueOf(secondLetter);
        }
        carrierId = generateId(Scarr.class, idToCheckIfTaken, 5, true, false);

        return carrierId;
    }

    private String generateId(Class clazz, String idToCheckIfTaken, int length, boolean useLetters, boolean useNumbers) {//TODO nicht nur String
        while (idTaken(clazz, idToCheckIfTaken)) {
            idToCheckIfTaken = this.generateRandomId(length, useLetters, useNumbers);
        }

        return idToCheckIfTaken;
    }

    private boolean idTaken(Class clazz, String idToCheckIfTaken) {
        //TODO use instance of
        return !StringUtils.isEmpty(idToCheckIfTaken) && mDatabaseHandler.idTaken(clazz, idToCheckIfTaken);
    }

    private String generateRandomId(int length, boolean useLetters, boolean useNumbers) {
        return RandomStringUtils.random(length, useLetters, useNumbers);
    }

    //create id, incrementing...
    //TODO vorübergehend auskommentiert
    //    private boolean productIdExists(int id) {
    //        for (Entity entity : this.productList) {
    //            Integer existingId = (Integer) entity.getProperty("ID").getValue();
    //            if (existingId.intValue() == id) {
    //                return true;
    //            }
    //        }
    //        return false;
    //    }

      /* INTERNAL */

    ////////////////////////Navigation////////////////////////
    //nur ein ergebnis kann zurückkomen vom aufruf
    public Entity getRelatedEntity(Entity entity, EdmEntityType relatedEntityType) {
        final EntityCollection collection = getRelatedEntityCollection(entity, relatedEntityType);

        if (collection.getEntities().isEmpty()) {
            return null;
        }

        return collection.getEntities().get(0);//TODO check, ists immer das 1.?
    }

    public Entity getRelatedEntity(Entity entity, EdmEntityType relatedEntityType, List<UriParameter> keyPredicates) throws ODataApplicationException {
        final EntityCollection relatedEntities = getRelatedEntityCollection(entity, relatedEntityType);

        return Util.findEntity(relatedEntityType, relatedEntities, keyPredicates);
    }

    public EntityCollection getRelatedEntityCollection(Entity sourceEntity, EdmEntityType targetEntityType) {
        final String sourceEntityFqn = sourceEntity.getType();
        final boolean navFromFlights = sourceEntityFqn.equals(ET_SFLIGHT_FQN.getFullQualifiedNameAsString());
        final boolean navFromCarriers = sourceEntityFqn.equals(ET_SCARR_FQN.getFullQualifiedNameAsString());
        final boolean navFromConnections = sourceEntityFqn.equals(ET_SPFLI_FQN.getFullQualifiedNameAsString());
        final boolean navFromPlanes = sourceEntityFqn.equals(ET_SAPLANE_FQN.getFullQualifiedNameAsString());
        final boolean navFromBookings = sourceEntityFqn.equals(ET_SBOOK_FQN.getFullQualifiedNameAsString());
        EntityCollection navigationTargetEntityCollection = new EntityCollection();

        if (navFromFlights) {
            navigationTargetEntityCollection = this.getFlightssssss(sourceEntity, targetEntityType, navigationTargetEntityCollection);
        } else if (navFromCarriers) {
            navigationTargetEntityCollection = this.getCarreirsssssss(sourceEntity, targetEntityType, navigationTargetEntityCollection);
        } else if (navFromConnections) {
            navigationTargetEntityCollection = this.getConnectionsssssss(sourceEntity, targetEntityType, navigationTargetEntityCollection);
        } else if (navFromPlanes) {
            navigationTargetEntityCollection = this.getPlanessssssss(sourceEntity, targetEntityType, navigationTargetEntityCollection);
        } else if (navFromBookings) {
            navigationTargetEntityCollection = this.getBookingssssss(sourceEntity, targetEntityType, navigationTargetEntityCollection);
        }

        if (navigationTargetEntityCollection == null) {
            //TODO LOG
            return null;
        }

        return navigationTargetEntityCollection;
    }

    private EntityCollection getFlightssssss(Entity sourceEntity, EdmEntityType targetEntityType, EntityCollection navigationTargetEntityCollection) {
        final FullQualifiedName relatedEntityFqn = targetEntityType.getFullQualifiedName();
        final boolean navToCarrier = relatedEntityFqn.equals(ET_SCARR_FQN);
        final boolean navToConnection = relatedEntityFqn.equals(ET_SPFLI_FQN);
        final boolean navToPlane = relatedEntityFqn.equals(ET_SAPLANE_FQN);
        final boolean navToBookings = relatedEntityFqn.equals(ET_SBOOK_FQN);

        if (navToCarrier) {
            return this.getCarrierforFlight(sourceEntity, navigationTargetEntityCollection);
        } else if (navToConnection) {
            return this.getConnectionForFlight(sourceEntity, navigationTargetEntityCollection);
        } else if (navToPlane) {
            return this.getPlaneForFlight(sourceEntity, navigationTargetEntityCollection);
        } else if (navToBookings) {
            return this.getBookingsForFlight(sourceEntity, navigationTargetEntityCollection);
        } else {
            return null;
        }
    }

    private EntityCollection getBookingsForFlight(Entity sourceEntity, EntityCollection navigationTargetEntityCollection) {
        //TODO implement
        //von flight - carrierId, connid, fldate

        final String carrierCode = (String) sourceEntity.getProperty(CARRIER_ID).getValue();
        final String connectionId = (String) sourceEntity.getProperty(CONNECTION_ID).getValue();
        final String fldate = (String) sourceEntity.getProperty(FLIGHT_DATE).getValue();
        final List<Sbook> sbookings = mDatabaseHandler.findBookingsForFlight(carrierCode, connectionId, fldate);
        //        final List<Entity> bookings = DataTransformator.
        return null;
    }

    private EntityCollection getPlaneForFlight(Entity sourceEntity, EntityCollection navigationTargetEntityCollection) {
        //TODO implement
        return null;
    }

    private EntityCollection getConnectionForFlight(Entity sourceEntity, EntityCollection navigationTargetEntityCollection) {
        //TODO implement
        return null;
    }

    private EntityCollection getCarreirsssssss(Entity sourceEntity, EdmEntityType targetEntityType, EntityCollection navigationTargetEntityCollection) {
        final FullQualifiedName relatedEntityFqn = targetEntityType.getFullQualifiedName();
        final boolean navToFlights = relatedEntityFqn.equals(ET_SFLIGHT_FQN);
        final boolean navToConnections = relatedEntityFqn.equals(ET_SPFLI_FQN);
        final boolean navToBookings = relatedEntityFqn.equals(ET_SBOOK_FQN);
        //TODO implement
        final boolean navToPlane = relatedEntityFqn.equals(ET_SAPLANE_FQN);

        if (navToFlights) {
            return this.getFlightsForCarrier(sourceEntity, navigationTargetEntityCollection);
        } else if (navToConnections) {
            return this.getConnectionsForCarrier(sourceEntity, navigationTargetEntityCollection);
        } else if (navToPlane) {
            return this.getPlaneForCarrier(sourceEntity, navigationTargetEntityCollection);
        } else if (navToBookings) {
            return this.getBookingsForCarrier(sourceEntity, navigationTargetEntityCollection);
        } else {
            return null;
        }
    }

    private EntityCollection getBookingsForCarrier(Entity sourceEntity, EntityCollection navigationTargetEntityCollection) {
        //TODO implement
        return null;
    }

    private EntityCollection getPlaneForCarrier(Entity sourceEntity, EntityCollection navigationTargetEntityCollection) {
        //TODO implement
        return null;
    }

    private EntityCollection getConnectionsForCarrier(Entity sourceEntity, EntityCollection navigationTargetEntityCollection) {
        //TODO implement
        return null;
    }

    private EntityCollection getConnectionsssssss(Entity sourceEntity, EdmEntityType targetEntityType, EntityCollection navigationTargetEntityCollection) {
        final FullQualifiedName relatedEntityFqn = targetEntityType.getFullQualifiedName();
        final boolean navToCarrier = relatedEntityFqn.equals(ET_SCARR_FQN);
        final boolean navToFlights = relatedEntityFqn.equals(ET_SFLIGHT_FQN);
        final boolean navToBookings = relatedEntityFqn.equals(ET_SBOOK_FQN);
        //TODO implement
        final boolean navToPlane = relatedEntityFqn.equals(ET_SAPLANE_FQN);

        if (navToFlights) {
            return this.getFlightsForConnection(sourceEntity, navigationTargetEntityCollection);
        } else if (navToCarrier) {
            return this.getCarrierForConnection(sourceEntity, navigationTargetEntityCollection);
        } else if (navToPlane) {
            return this.getPlaneForConnection(sourceEntity, navigationTargetEntityCollection);
        } else if (navToBookings) {
            return this.getBookingsForConnection(sourceEntity, navigationTargetEntityCollection);
        } else {
            return null;
        }
    }

    private EntityCollection getBookingsForConnection(Entity sourceEntity, EntityCollection navigationTargetEntityCollection) {
        //TODO implement
        return null;
    }

    private EntityCollection getPlaneForConnection(Entity sourceEntity, EntityCollection navigationTargetEntityCollection) {
        //TODO implement
        return null;
    }

    private EntityCollection getCarrierForConnection(Entity sourceEntity, EntityCollection navigationTargetEntityCollection) {
        //TODO implement
        return null;
    }

    private EntityCollection getFlightsForConnection(Entity sourceEntity, EntityCollection navigationTargetEntityCollection) {
        //TODO implement
        return null;
    }

    private EntityCollection getPlanessssssss(Entity sourceEntity, EdmEntityType targetEntityType, EntityCollection navigationTargetEntityCollection) {
        final FullQualifiedName relatedEntityFqn = targetEntityType.getFullQualifiedName();
        final boolean navToFlights = relatedEntityFqn.equals(ET_SFLIGHT_FQN);
        //TODO implement
        final boolean navToCarrier = relatedEntityFqn.equals(ET_SAPLANE_FQN);
        final boolean navToConnection = relatedEntityFqn.equals(ET_SPFLI_FQN);
        final boolean navToBookings = relatedEntityFqn.equals(ET_SBOOK_FQN);

        if (navToFlights) {
            return this.getFlightsForPlane(sourceEntity, navigationTargetEntityCollection);
        } else if (navToConnection) {
            return this.getConnectionForPlane(sourceEntity, navigationTargetEntityCollection);
        } else if (navToCarrier) {
            return this.getCarrierForPlane(sourceEntity, navigationTargetEntityCollection);
        } else if (navToBookings) {
            return this.getBookingsForPlane(sourceEntity, navigationTargetEntityCollection);
        } else {
            return null;
        }
    }

    private EntityCollection getBookingsForPlane(Entity sourceEntity, EntityCollection navigationTargetEntityCollection) {
        //TODO implement
        return null;
    }

    private EntityCollection getCarrierForPlane(Entity sourceEntity, EntityCollection navigationTargetEntityCollection) {
        //TODO implement
        return null;
    }

    private EntityCollection getConnectionForPlane(Entity sourceEntity, EntityCollection navigationTargetEntityCollection) {
        //TODO implement
        return null;
    }

    private EntityCollection getFlightsForPlane(Entity sourceEntity, EntityCollection navigationTargetEntityCollection) {
        //TODO implement
        return null;
    }

    private EntityCollection getBookingssssss(Entity sourceEntity, EdmEntityType targetEntityType, EntityCollection navigationTargetEntityCollection) {
        final FullQualifiedName relatedEntityFqn = targetEntityType.getFullQualifiedName();
        final boolean navToCarrier = relatedEntityFqn.equals(ET_SAPLANE_FQN);
        final boolean navToFlight = relatedEntityFqn.equals(ET_SFLIGHT_FQN);
        final boolean navToConnection = relatedEntityFqn.equals(ET_SPFLI_FQN);
        //TODO implement
        final boolean navToPlane = relatedEntityFqn.equals(ET_SAPLANE_FQN);

        if (navToFlight) {
            return this.getFlightForBooking(sourceEntity, navigationTargetEntityCollection);
        } else if (navToConnection) {
            return this.getConnectionForBooking(sourceEntity, navigationTargetEntityCollection);
        } else if (navToCarrier) {
            return this.getCarrierForBooking(sourceEntity, navigationTargetEntityCollection);
        } else if (navToPlane) {
            return this.getPlaneForBooking(sourceEntity, navigationTargetEntityCollection);
        } else {
            return null;
        }
    }

    private EntityCollection getPlaneForBooking(Entity sourceEntity, EntityCollection navigationTargetEntityCollection) {
        //TODO implement
        return null;
    }

    private EntityCollection getCarrierForBooking(Entity sourceEntity, EntityCollection navigationTargetEntityCollection) {
        //TODO implement
        return null;
    }

    private EntityCollection getConnectionForBooking(Entity sourceEntity, EntityCollection navigationTargetEntityCollection) {
        //TODO implement
        return null;
    }

    private EntityCollection getFlightForBooking(Entity sourceEntity, EntityCollection navigationTargetEntityCollection) {
        //TODO implement
        return null;
    }

    //TODO implementiere um von flügen den dazugehörigen carrier zu bekommen
    private EntityCollection getCarrierforFlight(Entity sourceEntity, EntityCollection navigationTargetEntityCollection) {//TODO name
        // relation Products->Category (result all categories) todo überarbeite kommentar
        final String carrierCode = (String) sourceEntity.getProperty(CARRIER_ID).getValue();
        final Scarr scarr = (Scarr) mDatabaseHandler.getById(Scarr.class, carrierCode);
        final Entity carrier = DataTransformator.transformScarrToEntity(scarr);
        navigationTargetEntityCollection.getEntities().add(carrier);

        return navigationTargetEntityCollection;
    }

    private EntityCollection getFlightsForCarrier(Entity sourceEntity, EntityCollection navigationTargetEntityCollection) {//TODO name
        final String carrierCode = (String) sourceEntity.getProperty(CARRIER_ID).getValue();
        final List<Entity> flights = new ArrayList<>();
        final List<Sflight> sflights = mDatabaseHandler.findFlightsForCarrier(carrierCode);

        flights.addAll(sflights.stream().map(DataTransformator::transformSflightToEntity).collect(Collectors.toList()));

        navigationTargetEntityCollection.getEntities().addAll(flights);

        return navigationTargetEntityCollection;
    }

    private URI createId(String entitySetName, Object id) {
        try {
            return new URI(entitySetName + "(" + String.valueOf(id) + ")");
        } catch (URISyntaxException e) {
            throw new ODataRuntimeException("Unable to create id for entity: " + entitySetName, e);
        }
    }
    //
    //    /* HELPER */
    //    private URI createId(Entity entity, String idPropertyName) {
    //        return createId(entity, idPropertyName, null);
    //    }
    //
    //        private URI createId(Entity entity, String idPropertyName, String navigationName) {
    //            try {
    //                StringBuilder sb = new StringBuilder(getEntitySetName(entity)).append("(");
    //                final Property property = entity.getProperty(idPropertyName);
    //                sb.append(property.asPrimitive()).append(")");
    //                if (navigationName != null) {
    //                    sb.append("/").append(navigationName);
    //                }
    //                return new URI(sb.toString());
    //            } catch (URISyntaxException e) {
    //                throw new ODataRuntimeException("Unable to create (Atom) id for entity: " + entity, e);
    //            }
    //        }

    //    private String getEntitySetName(Entity entity) {
    //        if (FlightDataEdmProvider.ET_SPFLI_FQN.getFullQualifiedNameAsString().equals(entity.getType())) {
    //            return FlightDataEdmProvider.ES_SPFLI_NAME;
    //        } else if (FlightDataEdmProvider.ET_SFLIGHT_FQN.getFullQualifiedNameAsString().equals(entity.getType())) {
    //            return FlightDataEdmProvider.ES_SFLIGHT_NAME;
    //        }
    //        return entity.getType();
    //    }
}
