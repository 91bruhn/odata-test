
package myservice.mynamespace.database.data;

import myservice.mynamespace.util.Util;
import org.apache.olingo.commons.api.data.Entity;
import org.apache.olingo.commons.api.data.EntityCollection;
import org.apache.olingo.commons.api.edm.EdmEntityType;
import org.apache.olingo.server.api.ODataApplicationException;
import org.apache.olingo.server.api.uri.UriParameter;

import java.util.List;

/**
 *
 */
public class ReadEntityData {

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

    public static Entity getFlight(EdmEntityType edmEntityType, List<UriParameter> keyParams) throws ODataApplicationException {//TODO rename

        // the list of entities at runtime
        final EntityCollection entityCollection = ReadEntitySetData.getFlights();

        /* generic approach to find the requested entity */
        return Util.findEntity(edmEntityType, entityCollection, keyParams);
    }

    public static Entity getConnection(EdmEntityType edmEntityType, List<UriParameter> keyParams) throws ODataApplicationException {

        // the list of entities at runtime
        final EntityCollection entitySet = ReadEntitySetData.getConnections();

    /* generic approach to find the requested entity */
        return Util.findEntity(edmEntityType, entitySet, keyParams);
    }

    public static Entity getCarrier(EdmEntityType edmEntityType, List<UriParameter> keyParams) throws ODataApplicationException {
        final EntityCollection entitySet = ReadEntitySetData.getCarriers();

        return Util.findEntity(edmEntityType, entitySet, keyParams);
    }

    public static Entity getBooking(EdmEntityType edmEntityType, List<UriParameter> keyParams) throws ODataApplicationException {
        // the list of entities at runtime
        final EntityCollection entitySet = ReadEntitySetData.getBookings();

    /* generic approach to find the requested entity */
        return Util.findEntity(edmEntityType, entitySet, keyParams);
    }

    public static Entity getPlane(EdmEntityType edmEntityType, List<UriParameter> keyParams) throws ODataApplicationException {

        // the list of entities at runtime
        final EntityCollection entitySet = ReadEntitySetData.getPlanes();

    /* generic approach to find the requested entity */
        return Util.findEntity(edmEntityType, entitySet, keyParams);
    }

}
