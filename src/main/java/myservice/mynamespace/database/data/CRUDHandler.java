package myservice.mynamespace.database.data;

import myservice.mynamespace.database.DatabaseHandler;
import myservice.mynamespace.database.DummyDataCreator;
import myservice.mynamespace.database.service.DataTransformator;
import myservice.mynamespace.util.EntityNames;
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
import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;

import static myservice.mynamespace.util.EntityNames.CARRIER_ID;
import static myservice.mynamespace.util.EntityNames.ES_SAPLANE_NAME;
import static myservice.mynamespace.util.EntityNames.ES_SBOOK_NAME;
import static myservice.mynamespace.util.EntityNames.ES_SCARR_NAME;
import static myservice.mynamespace.util.EntityNames.ES_SFLIGHT_NAME;
import static myservice.mynamespace.util.EntityNames.ES_SPFLI_NAME;
import static myservice.mynamespace.util.EntityNames.ET_SCARR_FQN;
import static myservice.mynamespace.util.EntityNames.ET_SFLIGHT_FQN;

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
        final EntityCollection navigationTargetEntityCollection = new EntityCollection();
        final FullQualifiedName relatedEntityFqn = targetEntityType.getFullQualifiedName();
        final String sourceEntityFqn = sourceEntity.getType();

        if (sourceEntityFqn.equals(ET_SFLIGHT_FQN.getFullQualifiedNameAsString()) && relatedEntityFqn.equals(ET_SCARR_FQN)) {
            this.FlightCarrier(sourceEntity, navigationTargetEntityCollection);
        } else if (sourceEntityFqn.equals(ET_SCARR_FQN.getFullQualifiedNameAsString()) && relatedEntityFqn.equals(ET_SFLIGHT_FQN)) {
            this.CarrierFlight(sourceEntity, navigationTargetEntityCollection);
        }

        if (navigationTargetEntityCollection.getEntities().isEmpty()) {
            return null;
        }

        return navigationTargetEntityCollection;
    }

    //TODO implementiere um von flügen den dazugehörigen carrier zu bekommen
    private EntityCollection FlightCarrier(Entity sourceEntity, EntityCollection navigationTargetEntityCollection) {//TODO name
        // relation Products->Category (result all categories) todo überarbeite kommentar
        final String carrierCode = (String) sourceEntity.getProperty(CARRIER_ID).getValue();
        final Scarr scarr = (Scarr) mDatabaseHandler.getById(Scarr.class, carrierCode);
        final Entity carrier = DataTransformator.transformScarrToEntity(scarr);
        navigationTargetEntityCollection.getEntities().add(carrier);

        return navigationTargetEntityCollection;
    }

    //        if (productID == 1 || productID == 2) {
    //            navigationTargetEntityCollection.getEntities().add(categoryList.get(0));
    //        } else if (productID == 3 || productID == 4) {
    //            navigationTargetEntityCollection.getEntities().add(categoryList.get(1));
    //        } else if (productID == 5 || productID == 6) {
    //            navigationTargetEntityCollection.getEntities().add(categoryList.get(2));
    //        }
    private EntityCollection CarrierFlight(Entity sourceEntity, EntityCollection navigationTargetEntityCollection) {//TODO name
        // relation Category->Products (result all products)
        final String carrierCode = (String) sourceEntity.getProperty(CARRIER_ID).getValue();
        final List<Entity> flights = new ArrayList<Entity>();
        final List<Sflight> sflights = mDatabaseHandler.findFlightsForCarrier(carrierCode);

        flights.addAll(sflights.stream().map(DataTransformator::transformSflightToEntity).collect(Collectors.toList()));

        navigationTargetEntityCollection.getEntities().addAll(flights);

        return navigationTargetEntityCollection;
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

    private URI createId(String entitySetName, Object id) {
        try {
            return new URI(entitySetName + "(" + String.valueOf(id) + ")");
        } catch (URISyntaxException e) {
            throw new ODataRuntimeException("Unable to create id for entity: " + entitySetName, e);
        }
    }

    //    private String getEntitySetName(Entity entity) {
    //        if (DemoEdmProvider.ET_SPFLI_FQN.getFullQualifiedNameAsString().equals(entity.getType())) {
    //            return DemoEdmProvider.ES_SPFLI_NAME;
    //        } else if (DemoEdmProvider.ET_SFLIGHT_FQN.getFullQualifiedNameAsString().equals(entity.getType())) {
    //            return DemoEdmProvider.ES_SFLIGHT_NAME;
    //        }
    //        return entity.getType();
    //    }
}
