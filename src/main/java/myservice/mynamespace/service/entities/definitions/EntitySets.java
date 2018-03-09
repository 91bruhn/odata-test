package myservice.mynamespace.service.entities.definitions;

import org.apache.olingo.commons.api.edm.provider.CsdlEntitySet;
import org.apache.olingo.commons.api.edm.provider.CsdlNavigationPropertyBinding;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static myservice.mynamespace.service.entities.definitions.EntityNames.ES_SAPLANE_NAME;
import static myservice.mynamespace.service.entities.definitions.EntityNames.ES_SBOOK_NAME;
import static myservice.mynamespace.service.entities.definitions.EntityNames.ES_SCARR_NAME;
import static myservice.mynamespace.service.entities.definitions.EntityNames.ES_SFLIGHT_NAME;
import static myservice.mynamespace.service.entities.definitions.EntityNames.ES_SPFLI_NAME;
import static myservice.mynamespace.service.entities.definitions.EntityNames.ET_SAPLANE_FQN;
import static myservice.mynamespace.service.entities.definitions.EntityNames.ET_SAPLANE_NAME;
import static myservice.mynamespace.service.entities.definitions.EntityNames.ET_SBOOK_FQN;
import static myservice.mynamespace.service.entities.definitions.EntityNames.ET_SCARR_FQN;
import static myservice.mynamespace.service.entities.definitions.EntityNames.ET_SCARR_NAME;
import static myservice.mynamespace.service.entities.definitions.EntityNames.ET_SFLIGHT_FQN;
import static myservice.mynamespace.service.entities.definitions.EntityNames.ET_SFLIGHT_NAME;
import static myservice.mynamespace.service.entities.definitions.EntityNames.ET_SPFLI_FQN;
import static myservice.mynamespace.service.entities.definitions.EntityNames.ET_SPFLI_NAME;

/**
 *
 */
public class EntitySets {

    // ------------------------------------------------------------------------
    // methods
    // ------------------------------------------------------------------------

    public static CsdlEntitySet getFlightsEntitySet() {
        // navigation property binding
        final CsdlNavigationPropertyBinding navBindingToCarrier = buildNavigationPropertyBinding(ES_SCARR_NAME, ET_SCARR_NAME);
        final CsdlNavigationPropertyBinding navBindingToConnection = buildNavigationPropertyBinding(ES_SPFLI_NAME, ET_SPFLI_NAME);
        final CsdlNavigationPropertyBinding navBindingToPlane = buildNavigationPropertyBinding(ES_SAPLANE_NAME, ET_SAPLANE_NAME);
        final CsdlNavigationPropertyBinding navBindingToBookings = buildNavigationPropertyBinding(ES_SBOOK_NAME, ES_SBOOK_NAME);

        final List<CsdlNavigationPropertyBinding> navPropBindingList = new ArrayList<>(Arrays.asList(navBindingToCarrier,
                                                                                                     navBindingToConnection,
                                                                                                     navBindingToPlane,
                                                                                                     navBindingToBookings));
        return new CsdlEntitySet().setName(ES_SFLIGHT_NAME).setType(ET_SFLIGHT_FQN).setNavigationPropertyBindings(navPropBindingList);
    }

    public static CsdlEntitySet getConnectionsEntitySet() {
        // navigation property binding
        final CsdlNavigationPropertyBinding navBindingToFlights = buildNavigationPropertyBinding(ES_SFLIGHT_NAME, ES_SFLIGHT_NAME);
        final CsdlNavigationPropertyBinding navBindingToBookings = buildNavigationPropertyBinding(ES_SBOOK_NAME, ES_SBOOK_NAME);
        final CsdlNavigationPropertyBinding navBindingToCarrier = buildNavigationPropertyBinding(ES_SCARR_NAME, ET_SCARR_NAME);
        //        final CsdlNavigationPropertyBinding navBindingToPlane = new CsdlNavigationPropertyBinding().setTarget(ES_SAPLANE_NAME).setPath(ET_SAPLANE_NAME);

        final List<CsdlNavigationPropertyBinding> navPropBindingList = new ArrayList<>(Arrays.asList(navBindingToFlights,
                                                                                                     navBindingToCarrier,
                                                                                                     navBindingToBookings));
        return new CsdlEntitySet().setName(ES_SPFLI_NAME).setType(ET_SPFLI_FQN).setNavigationPropertyBindings(navPropBindingList);
    }

    public static CsdlEntitySet getCarrierEntitySet() {
        // navigation property binding
        final CsdlNavigationPropertyBinding navPropBindingToFlights = buildNavigationPropertyBinding(ES_SFLIGHT_NAME, ES_SFLIGHT_NAME);
        final CsdlNavigationPropertyBinding navPropBindingToConnections = buildNavigationPropertyBinding(ES_SPFLI_NAME, ES_SPFLI_NAME);
        final CsdlNavigationPropertyBinding navPropBindingToBookings = buildNavigationPropertyBinding(ES_SBOOK_NAME, ES_SBOOK_NAME);
        //        final CsdlNavigationPropertyBinding navPropBindingToPlanes = new CsdlNavigationPropertyBinding().setTarget(ES_SAPLANE_NAME).setPath(ES_SAPLANE_NAME);

        final List<CsdlNavigationPropertyBinding> navPropBindingList = new ArrayList<>(Arrays.asList(navPropBindingToFlights,
                                                                                                     navPropBindingToConnections,
                                                                                                     navPropBindingToBookings));
        return new CsdlEntitySet().setName(ES_SCARR_NAME).setType(ET_SCARR_FQN).setNavigationPropertyBindings(navPropBindingList);
    }

    public static CsdlEntitySet getBookingEntitySet() {
        // navigation property binding
        final CsdlNavigationPropertyBinding navBindingToFlight = buildNavigationPropertyBinding(ES_SFLIGHT_NAME, ET_SFLIGHT_NAME);
        final CsdlNavigationPropertyBinding navBindingToConnection = buildNavigationPropertyBinding(ES_SPFLI_NAME, ET_SPFLI_NAME);
        final CsdlNavigationPropertyBinding navBindingToCarrier = buildNavigationPropertyBinding(ES_SCARR_NAME, ET_SCARR_NAME);
        //        final CsdlNavigationPropertyBinding navBindingToPlane = new CsdlNavigationPropertyBinding().setTarget(ES_SAPLANE_NAME).setPath(ET_SAPLANE_NAME);

        final List<CsdlNavigationPropertyBinding> navPropBindingList = new ArrayList<>(Arrays.asList(navBindingToFlight,
                                                                                                     navBindingToConnection,
                                                                                                     navBindingToCarrier));
        return new CsdlEntitySet().setName(ES_SBOOK_NAME).setType(ET_SBOOK_FQN).setNavigationPropertyBindings(navPropBindingList);
    }

    public static CsdlEntitySet getPlaneEntitySet() {
        // navigation property binding
        final CsdlNavigationPropertyBinding navBindingToFlights = buildNavigationPropertyBinding(ES_SFLIGHT_NAME, ES_SFLIGHT_NAME);
        //        final CsdlNavigationPropertyBinding navBindingToConnections = new CsdlNavigationPropertyBinding().setTarget(ES_SPFLI_NAME).setPath(ES_SPFLI_NAME);
        //        final CsdlNavigationPropertyBinding navBindingToCarrier = new CsdlNavigationPropertyBinding().setTarget(ES_SCARR_NAME).setPath(ET_SCARR_NAME);
        //        final CsdlNavigationPropertyBinding navBindingToBookings = new CsdlNavigationPropertyBinding().setTarget(ES_SBOOK_NAME).setPath(ES_SBOOK_NAME);

        final List<CsdlNavigationPropertyBinding> navPropBindingList = new ArrayList<>(Arrays.asList(navBindingToFlights));

        return new CsdlEntitySet().setName(ES_SAPLANE_NAME).setType(ET_SAPLANE_FQN).setNavigationPropertyBindings(navPropBindingList);
    }

    // the path from entity type to navigation property
    // the target entity set, where the navigation property points to
    private static CsdlNavigationPropertyBinding buildNavigationPropertyBinding(String target, String path) {
        return new CsdlNavigationPropertyBinding().setTarget(target).setPath(path);
    }
}
//TODO muss der pfad tatsächlich dann Einzahl sein?
//TODO bei Einzahl: Cannot find Singleton Fligh//TODO bei Einzahl: "Not supported."
