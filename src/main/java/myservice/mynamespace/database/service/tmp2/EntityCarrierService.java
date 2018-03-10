////////////////////////////////////////////////////////////////////////////////
//
// Created by BBruhns on 10.03.2018.
//
// Copyright (c) 2006 - 2018 FORCAM GmbH. All rights reserved.
////////////////////////////////////////////////////////////////////////////////

package myservice.mynamespace.database.service.tmp2;

import myservice.mynamespace.database.collections.Scarr;
import myservice.mynamespace.database.service.DataTransformator;
import myservice.mynamespace.database.service.tmp.ScarrService;
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

/**
 *
 */
public class EntityCarrierService extends AbstractEntityService {

    // ------------------------------------------------------------------------
    // constants
    // ------------------------------------------------------------------------

    // ------------------------------------------------------------------------
    // members
    // ------------------------------------------------------------------------

    private ScarrService mScarrService;

    // ------------------------------------------------------------------------
    // constructors
    // ------------------------------------------------------------------------

    public EntityCarrierService() {
        mScarrService = new ScarrService();
    }

    // ------------------------------------------------------------------------
    // methods
    // ------------------------------------------------------------------------

    public EntityCollection getCarriers() {
        final List<Scarr> scarrs = mScarrService.getAllScarrs();
        final List<Entity> carriers = scarrs.stream().map(DataTransformator::transformScarrToEntity).collect(Collectors.toList());
        final EntityCollection entitySet = new EntityCollection();
        entitySet.getEntities().addAll(carriers);

        return entitySet;
    }

    public Entity getCarrier(EdmEntityType edmEntityType, List<UriParameter> keyParams) throws ODataApplicationException {
        final EntityCollection entitySet = this.getCarriers();

        return Util.findEntity(edmEntityType, entitySet, keyParams);
    }

    public Entity createCarrier(EdmEntityType edmEntityType, Entity entity) {//TODO fall das werte die ich brauche null sind
        final Property idProperty = entity.getProperty(EntityNames.CARRIER_ID);
        final String carrierName = (String) entity.getProperty(EntityNames.CARRIER_NAME).getValue();
        final String carrierId;

        if (idProperty != null) {
            final String carrierCode = (String) idProperty.getValue();

            if (this.idTaken(Scarr.class, carrierCode)) {
                carrierId = this.generateScarrId(carrierName);
            } else {
                carrierId = carrierCode;
            }
            idProperty.setValue(ValueType.PRIMITIVE, carrierId);//TODO was macht das?
        } else {
            //as if OData v4 spec, the key property can be omitted from the POST request body
            carrierId = this.generateScarrId(carrierName);
            entity.getProperties().add(new Property(null, EntityNames.CARRIER_ID, ValueType.PRIMITIVE, carrierId));
        }
        entity.setId(super.createId("Carriers", carrierId));
        mScarrService.save(DataTransformator.transformEntityToScarr(entity));

        return entity;
    }

    public void updateCarrier(EdmEntityType edmEntityType, List<UriParameter> keyParams, Entity entity, HttpMethod httpMethod)
        throws ODataApplicationException {

        //        Entity productEntity = getProduct(edmEntityType, keyParams);
        //        if (productEntity == null) {
        //            throw new ODataApplicationException("Entity not found", HttpStatusCode.NOT_FOUND.getStatusCode(), Locale.ENGLISH);
    }

    public void deleteCarrier(EdmEntityType edmEntityType, List<UriParameter> keyParams) throws ODataApplicationException {
        final Entity productEntity = this.getCarrier(edmEntityType, keyParams);
        if (productEntity == null) {
            throw new ODataApplicationException("Entity not found", HttpStatusCode.NOT_FOUND.getStatusCode(), Locale.ENGLISH);
        }

        //hole object und dann lösche es?
        //        mDatabaseHandler.deleteData();
        //        this.productList.remove(productEntity);
    }

    /**
     * Generates from the given carrier name a carrier id. If that generated id is taken a random five letter id will be generated.
     *
     * @param carrierName The carrier name of which a carrier id will be tried to generate.
     * @return A not taken carrier id.
     */
    public String generateScarrId(String carrierName) {
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
        carrierId = this.generateId(Scarr.class, idToCheckIfTaken, 5, true, false);

        return carrierId;
    }

    public String generateId(Class clazz, String idToCheckIfTaken, int length, boolean useLetters, boolean useNumbers) {//TODO nicht nur String
        while (idTaken(clazz, idToCheckIfTaken)) {
            idToCheckIfTaken = this.generateRandomId(length, useLetters, useNumbers);
        }

        return idToCheckIfTaken;
    }

    public boolean idTaken(Class clazz, String idToCheckIfTaken) {//TODO VErschieben?
        //TODO use instance of
        return !StringUtils.isEmpty(idToCheckIfTaken) && mScarrService.idTaken(idToCheckIfTaken);
    }

    //NAVIGATION
    //TODO implementiere um von flügen den dazugehörigen carrier zu bekommen
    public EntityCollection getCarrierforFlight(Entity sourceEntity, EntityCollection navigationTargetEntityCollection) {//TODO name
        // relation Products->Category (result all categories) todo überarbeite kommentar
        final String carrierCode = (String) sourceEntity.getProperty(CARRIER_ID).getValue();
        final Scarr scarr = (Scarr) mScarrService.getById(carrierCode);
        final Entity carrier = DataTransformator.transformScarrToEntity(scarr);

        navigationTargetEntityCollection.getEntities().add(carrier);

        return navigationTargetEntityCollection;
    }

    public EntityCollection getCarrierForConnection(Entity sourceEntity, EntityCollection navigationTargetEntityCollection) {
        final String carrierId = (String) sourceEntity.getProperty(CARRIER_ID).getValue();
        final Scarr scarr = (Scarr) mScarrService.getById(carrierId);
        final Entity carrier = DataTransformator.transformScarrToEntity(scarr);

        navigationTargetEntityCollection.getEntities().add(carrier);

        return navigationTargetEntityCollection;
    }

    public EntityCollection getCarrierForBooking(Entity sourceEntity, EntityCollection navigationTargetEntityCollection) {
        final String carrierId = (String) sourceEntity.getProperty(CARRIER_ID).getValue();
        final Scarr scarr = (Scarr) mScarrService.getById(carrierId);
        final Entity carrier = DataTransformator.transformScarrToEntity(scarr);

        navigationTargetEntityCollection.getEntities().add(carrier);

        return navigationTargetEntityCollection;
    }

}
