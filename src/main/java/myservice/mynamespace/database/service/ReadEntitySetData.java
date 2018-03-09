package myservice.mynamespace.database.service;

import myservice.mynamespace.database.service.TransformStuffFromDb;
import org.apache.olingo.commons.api.data.EntityCollection;

/**
 *
 */
public class ReadEntitySetData {

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

    public static EntityCollection getFlights() {
        final EntityCollection entitySet = new EntityCollection();
        final TransformStuffFromDb sd = new TransformStuffFromDb();//todo rename
        entitySet.getEntities().addAll(sd.getFlightEntities());

        return entitySet;
    }

    public static EntityCollection getConnections() {//todo für jede tabelle beide methoden fast identisch
        final EntityCollection entitySet = new EntityCollection();
        final TransformStuffFromDb sd = new TransformStuffFromDb();
        //        for (Entity categoryEntity : this.categoryList) {
        //            entitySet.getEntities().add(categoryEntity);
        //        }
        entitySet.getEntities().addAll(sd.getConnectionEntities());

        return entitySet;
    }

    public static EntityCollection getCarriers() {
        final EntityCollection entitySet = new EntityCollection();
        final TransformStuffFromDb sd = new TransformStuffFromDb();
        entitySet.getEntities().addAll(sd.getCarrierEntities());

        return entitySet;
    }

    public static EntityCollection getBookings() {
        final EntityCollection entitySet = new EntityCollection();
        final TransformStuffFromDb sd = new TransformStuffFromDb();
        entitySet.getEntities().addAll(sd.getBookingEntities());

        return entitySet;
    }

    public static EntityCollection getPlanes() {
        final EntityCollection entitySet = new EntityCollection();
        final TransformStuffFromDb sd = new TransformStuffFromDb();
        entitySet.getEntities().addAll(sd.getPlaneEntities());

        return entitySet;
    }

}
