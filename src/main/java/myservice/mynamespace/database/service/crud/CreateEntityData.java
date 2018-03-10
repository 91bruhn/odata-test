package myservice.mynamespace.database.service.crud;

/**
 *
 */
public class CreateEntityData extends CRUDHandler {

    // ------------------------------------------------------------------------
    // constants
    // ------------------------------------------------------------------------

    // ------------------------------------------------------------------------
    // members
    // ------------------------------------------------------------------------

    // ------------------------------------------------------------------------
    // constructors
    // ------------------------------------------------------------------------

    // ------------------------------------------------------------------------
    // methods
    // ------------------------------------------------------------------------

    // ------------------------------------------------------------------------
    // getters/setters
    // ------------------------------------------------------------------------
    //
    //    public Entity createEntityData(EdmEntitySet edmEntitySet, Entity entityToCreate) {
    //        final EdmEntityType edmEntityType = edmEntitySet.getEntityType();
    //
    //        switch (edmEntitySet.getName()) {
    //            case ES_SFLIGHT_NAME:
    //                return createFlight(edmEntityType, entityToCreate);
    //            case ES_SPFLI_NAME:
    //                return createConnection(edmEntityType, entityToCreate);
    //            case ES_SCARR_NAME:
    //                return createCarrier(edmEntityType, entityToCreate);
    //            case ES_SBOOK_NAME:
    //                return createBooking(edmEntityType, entityToCreate);
    //            case ES_SAPLANE_NAME:
    //                return createPlane(edmEntityType, entityToCreate);
    //            default:
    //                return null;
    //        }
    //    }
    //
    //    private Entity createFlight(EdmEntityType edmEntityType, Entity entity) {
    //        final Property idProperty = entity.getProperty(EntityNames.PLANE_TYPE);
    //        final String id;
    //
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
    //        entity.setId(createId("Carriers", id));
    //        mDatabaseHandler.saveData(DataTransformator.transformEntityToScarr(entity));
    //
    //        return entity;
    //    }
    //
    //    //TODO alle verschieben
    //    protected Entity createBooking(EdmEntityType edmEntityType, Entity entity) {
    //        final Property idProperty = entity.getProperty(EntityNames.PLANE_TYPE);
    //        final String id;
    //
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
    //        entity.setId(createId("Carriers", id));
    //        mDatabaseHandler.saveData(DataTransformator.transformEntityToScarr(entity));
    //
    //        return entity;
    //    }
    //
    //    private Entity createConnection(EdmEntityType edmEntityType, Entity entity) {
    //        return null;
    //    }
    //
    //    private Entity createCarrier(EdmEntityType edmEntityType, Entity entity) {//TODO fall das werte die ich brauche null sind
    //        final Property idProperty = entity.getProperty(EntityNames.CARRIER_ID);
    //        final String carrierName = (String) entity.getProperty(EntityNames.CARRIER_NAME).getValue();
    //        final String carrierId;
    //
    //        if (idProperty != null) {
    //            final String carrierCode = (String) idProperty.getValue();
    //
    //            if (idTaken(Scarr.class, carrierCode)) {
    //                carrierId = generateScarrId(carrierName);
    //            } else {
    //                carrierId = carrierCode;
    //            }
    //            idProperty.setValue(ValueType.PRIMITIVE, carrierId);//TODO was macht das?
    //        } else {
    //            //as if OData v4 spec, the key property can be omitted from the POST request body
    //            carrierId = generateScarrId(carrierName);
    //            entity.getProperties().add(new Property(null, EntityNames.CARRIER_ID, ValueType.PRIMITIVE, carrierId));
    //        }
    //        entity.setId(createId("Carriers", carrierId));
    //        mDatabaseHandler.saveData(DataTransformator.transformEntityToScarr(entity));
    //
    //        return entity;
    //    }
    //
    //    //übergebene id muss vorhanden und gültig sein ansonsten null --> service sollte folgenden http
    //    private Entity createPlane(EdmEntityType edmEntityType, Entity entity) {
    //        final Property idProperty = entity.getProperty(EntityNames.PLANE_TYPE);
    //        final String id;
    //
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
    //        entity.setId(createId("Carriers", id));
    //        mDatabaseHandler.saveData(DataTransformator.transformEntityToSaplane(entity));
    //
    //        return entity;
    //    }

}
