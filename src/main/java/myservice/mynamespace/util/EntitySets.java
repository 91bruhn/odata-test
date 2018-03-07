
package myservice.mynamespace.util;

import org.apache.olingo.commons.api.edm.provider.CsdlEntitySet;
import org.apache.olingo.commons.api.edm.provider.CsdlNavigationPropertyBinding;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static myservice.mynamespace.util.EntityNames.ES_SAPLANE_NAME;
import static myservice.mynamespace.util.EntityNames.ES_SBOOK_NAME;
import static myservice.mynamespace.util.EntityNames.ES_SCARR_NAME;
import static myservice.mynamespace.util.EntityNames.ES_SFLIGHT_NAME;
import static myservice.mynamespace.util.EntityNames.ES_SPFLI_NAME;
import static myservice.mynamespace.util.EntityNames.ET_SAPLANE_FQN;
import static myservice.mynamespace.util.EntityNames.ET_SAPLANE_NAME;
import static myservice.mynamespace.util.EntityNames.ET_SBOOK_FQN;
import static myservice.mynamespace.util.EntityNames.ET_SCARR_FQN;
import static myservice.mynamespace.util.EntityNames.ET_SCARR_NAME;
import static myservice.mynamespace.util.EntityNames.ET_SFLIGHT_FQN;
import static myservice.mynamespace.util.EntityNames.ET_SFLIGHT_NAME;
import static myservice.mynamespace.util.EntityNames.ET_SPFLI_FQN;
import static myservice.mynamespace.util.EntityNames.ET_SPFLI_NAME;

/**
 *
 */
public class EntitySets {

    // ------------------------------------------------------------------------
    // methods
    // ------------------------------------------------------------------------

    public static CsdlEntitySet getFlightsEntitySet() {
        final CsdlEntitySet entitySet;
        entitySet = new CsdlEntitySet();
        entitySet.setName(ES_SFLIGHT_NAME);
        entitySet.setType(ET_SFLIGHT_FQN);

        // navigation
        final CsdlNavigationPropertyBinding navBindingToCarrier = new CsdlNavigationPropertyBinding().setTarget(ES_SCARR_NAME).setPath(ET_SCARR_NAME);
        final CsdlNavigationPropertyBinding navBindingToConnection = new CsdlNavigationPropertyBinding().setTarget(ES_SPFLI_NAME).setPath(ET_SPFLI_NAME);
        final CsdlNavigationPropertyBinding navBindingToPlane = new CsdlNavigationPropertyBinding().setTarget(ES_SAPLANE_NAME).setPath(ET_SAPLANE_NAME);
        final CsdlNavigationPropertyBinding navBindingToBookings = new CsdlNavigationPropertyBinding().setTarget(ES_SBOOK_NAME).setPath(ES_SBOOK_NAME);

        final List<CsdlNavigationPropertyBinding> navPropBindingList = new ArrayList<>(Arrays.asList(navBindingToCarrier,
                                                                                                     navBindingToConnection,
                                                                                                     navBindingToPlane,
                                                                                                     navBindingToBookings));
        entitySet.setNavigationPropertyBindings(navPropBindingList);

        return entitySet;
    }

    public static CsdlEntitySet getConnectionsEntitySet() {
        final CsdlEntitySet entitySet;
        entitySet = new CsdlEntitySet();
        entitySet.setName(ES_SPFLI_NAME);
        entitySet.setType(ET_SPFLI_FQN);

        // navigation
        final CsdlNavigationPropertyBinding navBindingToFlights = new CsdlNavigationPropertyBinding().setTarget(ES_SFLIGHT_NAME).setPath(ES_SFLIGHT_NAME);
        final CsdlNavigationPropertyBinding navBindingToCarrier = new CsdlNavigationPropertyBinding().setTarget(ES_SCARR_NAME).setPath(ET_SCARR_NAME);
        final CsdlNavigationPropertyBinding navBindingToBookings = new CsdlNavigationPropertyBinding().setTarget(ES_SBOOK_NAME).setPath(ES_SBOOK_NAME);
        final CsdlNavigationPropertyBinding navBindingToPlane = new CsdlNavigationPropertyBinding().setTarget(ES_SAPLANE_NAME).setPath(ET_SAPLANE_NAME);

        final List<CsdlNavigationPropertyBinding> navPropBindingList = new ArrayList<>(Arrays.asList(navBindingToFlights,
                                                                                                     navBindingToCarrier,
                                                                                                     navBindingToBookings,
                                                                                                     navBindingToPlane));
        entitySet.setNavigationPropertyBindings(navPropBindingList);

        return entitySet;
    }

    public static CsdlEntitySet getCarrierEntitySet() {
        final CsdlEntitySet entitySet;
        entitySet = new CsdlEntitySet();
        entitySet.setName(ES_SCARR_NAME);
        entitySet.setType(ET_SCARR_FQN);

        // navigation
        final CsdlNavigationPropertyBinding navPropBindingToFlights = new CsdlNavigationPropertyBinding().setTarget(ES_SFLIGHT_NAME).setPath(ES_SFLIGHT_NAME);
        final CsdlNavigationPropertyBinding navPropBindingToConnections = new CsdlNavigationPropertyBinding().setTarget(ES_SPFLI_NAME).setPath(ES_SPFLI_NAME);
        final CsdlNavigationPropertyBinding navPropBindingToPlanes = new CsdlNavigationPropertyBinding().setTarget(ES_SAPLANE_NAME).setPath(ES_SAPLANE_NAME);
        final CsdlNavigationPropertyBinding navPropBindingToBookings = new CsdlNavigationPropertyBinding().setTarget(ES_SBOOK_NAME).setPath(ES_SBOOK_NAME);

        final List<CsdlNavigationPropertyBinding> navPropBindingList = new ArrayList<>(Arrays.asList(navPropBindingToFlights,
                                                                                                     navPropBindingToConnections,
                                                                                                     navPropBindingToPlanes,
                                                                                                     navPropBindingToBookings));
        entitySet.setNavigationPropertyBindings(navPropBindingList);//TODO bei Einzahl: Cannot find Singleton Fligh//TODO bei Einzahl: "Not supported."

        return entitySet;
    }

    public static CsdlEntitySet getBookingEntitySet() {
        final CsdlEntitySet entitySet;
        entitySet = new CsdlEntitySet();
        entitySet.setName(ES_SBOOK_NAME);
        entitySet.setType(ET_SBOOK_FQN);//TODO es könnten auch mehrere connections zu einer buchung? und somit auch mehrere planes

        // navigation
        final CsdlNavigationPropertyBinding navBindingToFlight = new CsdlNavigationPropertyBinding().setTarget(ES_SFLIGHT_NAME).setPath(ET_SFLIGHT_NAME);
        final CsdlNavigationPropertyBinding navBindingToConnection = new CsdlNavigationPropertyBinding().setTarget(ES_SPFLI_NAME).setPath(ET_SPFLI_NAME);
        final CsdlNavigationPropertyBinding navBindingToCarrier = new CsdlNavigationPropertyBinding().setTarget(ES_SCARR_NAME).setPath(ET_SCARR_NAME);
        final CsdlNavigationPropertyBinding navBindingToPlane = new CsdlNavigationPropertyBinding().setTarget(ES_SAPLANE_NAME).setPath(ET_SAPLANE_NAME);

        final List<CsdlNavigationPropertyBinding> navPropBindingList = new ArrayList<>(Arrays.asList(navBindingToFlight,
                                                                                                     navBindingToConnection,
                                                                                                     navBindingToCarrier,
                                                                                                     navBindingToPlane));
        entitySet.setNavigationPropertyBindings(navPropBindingList);

        return entitySet;
    }

    public static CsdlEntitySet getPlaneEntitySet() {
        // navigation
        final CsdlNavigationPropertyBinding navBindingToFlight = buildNavigationPropertyBinding(ES_SPFLI_NAME, ET_SPFLI_NAME);
        final CsdlNavigationPropertyBinding navBindingToConnections = new CsdlNavigationPropertyBinding().setTarget(ES_SPFLI_NAME).setPath(ES_SPFLI_NAME);
        final CsdlNavigationPropertyBinding navBindingToCarrier = new CsdlNavigationPropertyBinding().setTarget(ES_SCARR_NAME).setPath(ET_SCARR_NAME);
        final CsdlNavigationPropertyBinding navBindingToBookings = new CsdlNavigationPropertyBinding().setTarget(ES_SBOOK_NAME).setPath(ES_SBOOK_NAME);

        final List<CsdlNavigationPropertyBinding> navPropBindingList = new ArrayList<>(Arrays.asList(navBindingToFlight,
                                                                                                     navBindingToConnections,
                                                                                                     navBindingToCarrier,
                                                                                                     navBindingToBookings));

        return new CsdlEntitySet().setName(ES_SAPLANE_NAME).setType(ET_SAPLANE_FQN).setNavigationPropertyBindings(navPropBindingList);//TODO make it like that?
    }

    // the path from entity type to navigation property
    // the target entity set, where the navigation property points to
    private static CsdlNavigationPropertyBinding buildNavigationPropertyBinding(String target, String path) {//TODO use?
        return new CsdlNavigationPropertyBinding().setTarget(target).setPath(path);
    }
}
