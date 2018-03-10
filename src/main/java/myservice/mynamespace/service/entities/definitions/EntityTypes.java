package myservice.mynamespace.service.entities.definitions;

import org.apache.olingo.commons.api.edm.EdmPrimitiveTypeKind;
import org.apache.olingo.commons.api.edm.provider.CsdlEntityType;
import org.apache.olingo.commons.api.edm.provider.CsdlNavigationProperty;
import org.apache.olingo.commons.api.edm.provider.CsdlProperty;
import org.apache.olingo.commons.api.edm.provider.CsdlPropertyRef;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static myservice.mynamespace.service.entities.definitions.EntityNames.AIRPORT_FROM;
import static myservice.mynamespace.service.entities.definitions.EntityNames.AIRPORT_TO;
import static myservice.mynamespace.service.entities.definitions.EntityNames.ARRIVAL_TIME;
import static myservice.mynamespace.service.entities.definitions.EntityNames.BOOKING_ID;
import static myservice.mynamespace.service.entities.definitions.EntityNames.CARRIER_ID;
import static myservice.mynamespace.service.entities.definitions.EntityNames.CARRIER_NAME;
import static myservice.mynamespace.service.entities.definitions.EntityNames.CITY_FROM;
import static myservice.mynamespace.service.entities.definitions.EntityNames.CITY_TO;
import static myservice.mynamespace.service.entities.definitions.EntityNames.CONNECTION_ID;
import static myservice.mynamespace.service.entities.definitions.EntityNames.CONSUMPTION;
import static myservice.mynamespace.service.entities.definitions.EntityNames.CONSUM_UNIT;
import static myservice.mynamespace.service.entities.definitions.EntityNames.COUNTRY_FROM;
import static myservice.mynamespace.service.entities.definitions.EntityNames.COUNTRY_TO;
import static myservice.mynamespace.service.entities.definitions.EntityNames.CURRENCY;
import static myservice.mynamespace.service.entities.definitions.EntityNames.CUSTOMER_ID;
import static myservice.mynamespace.service.entities.definitions.EntityNames.DEPARTURE_TIME;
import static myservice.mynamespace.service.entities.definitions.EntityNames.DISTANCE_UNIT;
import static myservice.mynamespace.service.entities.definitions.EntityNames.DISTANCE____;
import static myservice.mynamespace.service.entities.definitions.EntityNames.ES_SBOOK_NAME;
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
import static myservice.mynamespace.service.entities.definitions.EntityNames.FLIGHT_CLASS;
import static myservice.mynamespace.service.entities.definitions.EntityNames.FLIGHT_DATE;
import static myservice.mynamespace.service.entities.definitions.EntityNames.FLIGHT_TIME;
import static myservice.mynamespace.service.entities.definitions.EntityNames.FLIGHT_TYPE;
import static myservice.mynamespace.service.entities.definitions.EntityNames.HAS_INVOICE;
import static myservice.mynamespace.service.entities.definitions.EntityNames.IS_CANCELLED;
import static myservice.mynamespace.service.entities.definitions.EntityNames.IS_RESERVED;
import static myservice.mynamespace.service.entities.definitions.EntityNames.IS_SMOKER;
import static myservice.mynamespace.service.entities.definitions.EntityNames.LENGTH;
import static myservice.mynamespace.service.entities.definitions.EntityNames.LENGTH_UNIT;
import static myservice.mynamespace.service.entities.definitions.EntityNames.LUGGAGE_WEIGHT;
import static myservice.mynamespace.service.entities.definitions.EntityNames.ORDER_DATE;
import static myservice.mynamespace.service.entities.definitions.EntityNames.PERIOD____;
import static myservice.mynamespace.service.entities.definitions.EntityNames.PLANE_TYPE;
import static myservice.mynamespace.service.entities.definitions.EntityNames.PRICE;
import static myservice.mynamespace.service.entities.definitions.EntityNames.PRODUCER;
import static myservice.mynamespace.service.entities.definitions.EntityNames.SEATS_MAX;
import static myservice.mynamespace.service.entities.definitions.EntityNames.SEATS_MAX_B;
import static myservice.mynamespace.service.entities.definitions.EntityNames.SEATS_MAX_E;
import static myservice.mynamespace.service.entities.definitions.EntityNames.SEATS_MAX_F;
import static myservice.mynamespace.service.entities.definitions.EntityNames.SEATS_OCC_B;
import static myservice.mynamespace.service.entities.definitions.EntityNames.SEATS_OCC_E;
import static myservice.mynamespace.service.entities.definitions.EntityNames.SEATS_OCC_F;
import static myservice.mynamespace.service.entities.definitions.EntityNames.SEX;
import static myservice.mynamespace.service.entities.definitions.EntityNames.SPAN;
import static myservice.mynamespace.service.entities.definitions.EntityNames.SPAN_UNIT;
import static myservice.mynamespace.service.entities.definitions.EntityNames.SPEED;
import static myservice.mynamespace.service.entities.definitions.EntityNames.SPEED_UNIT;
import static myservice.mynamespace.service.entities.definitions.EntityNames.TANK_CAPACITY;
import static myservice.mynamespace.service.entities.definitions.EntityNames.TANK_CAP_UNIT;
import static myservice.mynamespace.service.entities.definitions.EntityNames.URL;
import static myservice.mynamespace.service.entities.definitions.EntityNames.WEIGHT;
import static myservice.mynamespace.service.entities.definitions.EntityNames.WEIGHT_UNIT;

/**
 *
 */
public class EntityTypes {

    // ------------------------------------------------------------------------
    // constructors
    // ------------------------------------------------------------------------

    // ------------------------------------------------------------------------
    // methods
    // ------------------------------------------------------------------------

    public static CsdlEntityType getFlightEntityType() {
        final CsdlEntityType entityType;

        // create EntityType properties
        final CsdlProperty carrierId = new CsdlProperty().setName(CARRIER_ID).setType(EdmPrimitiveTypeKind.String.getFullQualifiedName());//TODO doppelt

        final CsdlProperty connectionId = new CsdlProperty().setName(CONNECTION_ID).setType(EdmPrimitiveTypeKind.String.getFullQualifiedName());//TODO doppelt
        final CsdlProperty flightDate = new CsdlProperty().setName(FLIGHT_DATE)
                                                          .setType(EdmPrimitiveTypeKind.String.getFullQualifiedName());//TODO anpassen für konsis?

        //        CsdlProperty planeType = new CsdlProperty().setName(PLANE_TYPE).setType(EdmCollection);
        //        new CsdlEnumType().set
        //                CsdlProperty planeType = new CsdlProperty().setName(PLANE_TYPE).setType(EdmStructuredType);

        //        CsdlProperty planeType = new CsdlProperty().setName(PLANE_TYPE).setCollection(true).setMapping()setType(ET_SAPLANE_FQN);
        //        EdmProperty dm = new EdmPropertyImpl(null, planeType);

        //        CsdlProperty planeType = new CsdlProperty().setName(PLANE_TYPE).setType(EdmPrimitiveTypeKind.String.getFullQualifiedName()).setCollection(true);

        final CsdlProperty planeType = new CsdlProperty().setName(PLANE_TYPE).setType(EdmPrimitiveTypeKind.String.getFullQualifiedName());
        final CsdlProperty speed = new CsdlProperty().setName(SPEED).setType(EdmPrimitiveTypeKind.Double.getFullQualifiedName());

        final CsdlProperty price = new CsdlProperty().setName(PRICE).setType(EdmPrimitiveTypeKind.Double.getFullQualifiedName());
        final CsdlProperty currency = new CsdlProperty().setName(CURRENCY).setType(EdmPrimitiveTypeKind.String.getFullQualifiedName());
        final CsdlProperty seatsMax = new CsdlProperty().setName(SEATS_MAX_E).setType(EdmPrimitiveTypeKind.Int32.getFullQualifiedName());
        final CsdlProperty seatsOcc = new CsdlProperty().setName(SEATS_OCC_E).setType(EdmPrimitiveTypeKind.String.getFullQualifiedName());
        final CsdlProperty seatsMaxB = new CsdlProperty().setName(SEATS_MAX_B).setType(EdmPrimitiveTypeKind.Int32.getFullQualifiedName());
        final CsdlProperty seatsOccB = new CsdlProperty().setName(SEATS_OCC_B).setType(EdmPrimitiveTypeKind.String.getFullQualifiedName());
        final CsdlProperty seatsMaxF = new CsdlProperty().setName(SEATS_MAX_F).setType(EdmPrimitiveTypeKind.Int32.getFullQualifiedName());
        final CsdlProperty seatsOccF = new CsdlProperty().setName(SEATS_OCC_F).setType(EdmPrimitiveTypeKind.String.getFullQualifiedName());

        // create PropertyRef for Key element
        final CsdlPropertyRef propRefCarrierId = new CsdlPropertyRef().setName(CARRIER_ID);
        final CsdlPropertyRef propRefConnectionId = new CsdlPropertyRef().setName(CONNECTION_ID);
        final CsdlPropertyRef propRefFlightDate = new CsdlPropertyRef().setName(FLIGHT_DATE);

        // navigation properties
        final CsdlNavigationProperty navPropCarrier = new CsdlNavigationProperty().setName(ET_SCARR_NAME)
                                                                                  .setType(ET_SCARR_FQN)
                                                                                  .setPartner(ES_SFLIGHT_NAME)
                                                                                  .setCollection(false)
                                                                                  .setNullable(false);
        final CsdlNavigationProperty navPropConnection = new CsdlNavigationProperty().setName(ET_SPFLI_NAME)
                                                                                     .setType(ET_SPFLI_FQN)
                                                                                     .setPartner(ES_SFLIGHT_NAME)
                                                                                     .setCollection(false)
                                                                                     .setNullable(false);
        final CsdlNavigationProperty navPropPlane = new CsdlNavigationProperty().setName(ET_SAPLANE_NAME)
                                                                                .setType(ET_SAPLANE_FQN)
                                                                                .setPartner(ES_SFLIGHT_NAME)
                                                                                .setCollection(false)
                                                                                .setNullable(false);
        final CsdlNavigationProperty navPropBookings = new CsdlNavigationProperty().setName(ES_SBOOK_NAME)
                                                                                   .setType(ET_SBOOK_FQN)
                                                                                   .setPartner(ET_SFLIGHT_NAME)
                                                                                   .setCollection(true)
                                                                                   .setNullable(false);
        final List<CsdlNavigationProperty> navPropList = new ArrayList<>(Arrays.asList(navPropCarrier, navPropConnection, navPropPlane, navPropBookings));

        // configure EntityType
        entityType = new CsdlEntityType();
        entityType.setName(ET_SFLIGHT_NAME);
        entityType.setProperties(Arrays.asList(carrierId,
                                               connectionId,
                                               flightDate,
                                               planeType,
                                               speed,
                                               price,
                                               currency,
                                               seatsMax,
                                               seatsOcc,
                                               seatsMaxB,
                                               seatsOccB,
                                               seatsMaxF,
                                               seatsOccF));
        entityType.setKey(Arrays.asList(propRefCarrierId, propRefConnectionId, propRefFlightDate));
        entityType.setNavigationProperties(navPropList);

        return entityType;
    }

    public static CsdlEntityType getConnectionEntityType() {
        final CsdlEntityType entityType;
        // create EntityType properties
        final CsdlProperty carrierId = new CsdlProperty().setName(CARRIER_ID).setType(EdmPrimitiveTypeKind.String.getFullQualifiedName());//TODO doppelt
        final CsdlProperty connectionId = new CsdlProperty().setName(CONNECTION_ID).setType(EdmPrimitiveTypeKind.String.getFullQualifiedName());//TODO doppelt

        final CsdlProperty airpFrom = new CsdlProperty().setName(AIRPORT_FROM).setType(EdmPrimitiveTypeKind.String.getFullQualifiedName());
        final CsdlProperty cityFrom = new CsdlProperty().setName(CITY_FROM).setType(EdmPrimitiveTypeKind.String.getFullQualifiedName());
        final CsdlProperty countryFr = new CsdlProperty().setName(COUNTRY_FROM)
                                                         .setType(EdmPrimitiveTypeKind.String.getFullQualifiedName());//TODO change String values
        final CsdlProperty airpTo = new CsdlProperty().setName(AIRPORT_TO).setType(EdmPrimitiveTypeKind.String.getFullQualifiedName());
        final CsdlProperty cityTo = new CsdlProperty().setName(CITY_TO).setType(EdmPrimitiveTypeKind.String.getFullQualifiedName());
        final CsdlProperty countryTo = new CsdlProperty().setName(COUNTRY_TO).setType(EdmPrimitiveTypeKind.String.getFullQualifiedName());
        final CsdlProperty flTime = new CsdlProperty().setName(FLIGHT_TIME).setType(EdmPrimitiveTypeKind.Int32.getFullQualifiedName());//TODO korrekter typ?
        final CsdlProperty depTime = new CsdlProperty().setName(DEPARTURE_TIME).setType(EdmPrimitiveTypeKind.String.getFullQualifiedName());
        final CsdlProperty arrTime = new CsdlProperty().setName(ARRIVAL_TIME).setType(EdmPrimitiveTypeKind.String.getFullQualifiedName());
        final CsdlProperty distance = new CsdlProperty().setName(DISTANCE____).setType(EdmPrimitiveTypeKind.Double.getFullQualifiedName());
        final CsdlProperty distanceId = new CsdlProperty().setName(DISTANCE_UNIT).setType(EdmPrimitiveTypeKind.String.getFullQualifiedName());
        final CsdlProperty flyType = new CsdlProperty().setName(FLIGHT_TYPE).setType(EdmPrimitiveTypeKind.String.getFullQualifiedName());
        final CsdlProperty period = new CsdlProperty().setName(PERIOD____).setType(EdmPrimitiveTypeKind.Int32.getFullQualifiedName());

        // create PropertyRef for Key element
        final CsdlPropertyRef propRefCarrierId = new CsdlPropertyRef().setName(CARRIER_ID);
        final CsdlPropertyRef propRefConnectionId = new CsdlPropertyRef().setName(CONNECTION_ID);

        // navigation properties
        final CsdlNavigationProperty navPropFlights = new CsdlNavigationProperty().setName(ES_SFLIGHT_NAME)
                                                                                  .setType(ET_SFLIGHT_FQN)
                                                                                  .setPartner(ET_SPFLI_NAME)
                                                                                  .setCollection(true)
                                                                                  .setNullable(false);
        final CsdlNavigationProperty navPropBookings = new CsdlNavigationProperty().setName(ES_SBOOK_NAME)
                                                                                   .setType(ET_SBOOK_FQN)
                                                                                   .setPartner(ET_SPFLI_NAME)
                                                                                   .setCollection(true)
                                                                                   .setNullable(false);
        final CsdlNavigationProperty navPropCarrier = new CsdlNavigationProperty().setName(ET_SCARR_NAME)
                                                                                  .setType(ET_SCARR_FQN)
                                                                                  .setPartner(ES_SPFLI_NAME)
                                                                                  .setCollection(false)
                                                                                  .setNullable(false);
        //TODO implement navigation to plane
        final List<CsdlNavigationProperty> navPropList = new ArrayList<>(Arrays.asList(navPropFlights, navPropBookings, navPropCarrier));

        // configure EntityType
        entityType = new CsdlEntityType();
        entityType.setName(ET_SPFLI_NAME);
        entityType.setProperties(Arrays.asList(carrierId,
                                               connectionId,
                                               countryFr,
                                               cityFrom,
                                               airpFrom,
                                               countryTo,
                                               cityTo,
                                               airpTo,
                                               flTime,
                                               depTime,
                                               arrTime,
                                               distance,
                                               distanceId,
                                               flyType,
                                               period));
        entityType.setKey(Arrays.asList(propRefCarrierId, propRefConnectionId));
        entityType.setNavigationProperties(navPropList);
        return entityType;
    }

    public static CsdlEntityType getCarrierEntityType() {
        final CsdlEntityType entityType;

        final CsdlProperty carrierId = new CsdlProperty().setName(CARRIER_ID).setType(EdmPrimitiveTypeKind.String.getFullQualifiedName());
        final CsdlProperty carrierName = new CsdlProperty().setName(CARRIER_NAME).setType(EdmPrimitiveTypeKind.String.getFullQualifiedName());
        final CsdlProperty currency = new CsdlProperty().setName(CURRENCY)
                                                        .setType(EdmPrimitiveTypeKind.String.getFullQualifiedName());//TODO anpassen type enum und
        final CsdlProperty url = new CsdlProperty().setName(URL).setType(EdmPrimitiveTypeKind.String.getFullQualifiedName());

        final CsdlPropertyRef propRefCarrierId = new CsdlPropertyRef().setName(CARRIER_ID);
        entityType = new CsdlEntityType();
        entityType.setName(ET_SCARR_NAME);
        entityType.setProperties(Arrays.asList(carrierId, carrierName, currency, url));
        entityType.setKey(Collections.singletonList(propRefCarrierId));

        // navigation properties
        final CsdlNavigationProperty navPropFlights = new CsdlNavigationProperty().setName(ES_SFLIGHT_NAME)
                                                                                  .setType(ET_SFLIGHT_FQN)
                                                                                  .setPartner(ET_SCARR_NAME)
                                                                                  .setCollection(true)
                                                                                  .setNullable(false);
        final CsdlNavigationProperty navPropConnections = new CsdlNavigationProperty().setName(ES_SPFLI_NAME)
                                                                                      .setType(ET_SPFLI_FQN)
                                                                                      .setPartner(ET_SCARR_NAME)
                                                                                      .setCollection(true)
                                                                                      .setNullable(false);
        final CsdlNavigationProperty navPropBookings = new CsdlNavigationProperty().setName(ES_SBOOK_NAME)
                                                                                   .setType(ET_SBOOK_FQN)
                                                                                   .setPartner(ET_SCARR_NAME)
                                                                                   .setCollection(true)
                                                                                   .setNullable(false);
        //TODO implement navigation to plane

        final List<CsdlNavigationProperty> navPropList = new ArrayList<>(Arrays.asList(navPropConnections, navPropFlights, navPropBookings));
        entityType.setNavigationProperties(navPropList);

        return entityType;
    }

    public static CsdlEntityType getBookingEntityType() {
        final CsdlEntityType entityType;
        final CsdlProperty bookingId = new CsdlProperty().setName(BOOKING_ID).setType(EdmPrimitiveTypeKind.String.getFullQualifiedName());
        final CsdlProperty carrierId = new CsdlProperty().setName(CARRIER_ID).setType(EdmPrimitiveTypeKind.String.getFullQualifiedName());
        final CsdlProperty connectionId = new CsdlProperty().setName(CONNECTION_ID).setType(EdmPrimitiveTypeKind.String.getFullQualifiedName());
        final CsdlProperty flightDate = new CsdlProperty().setName(FLIGHT_DATE).setType(EdmPrimitiveTypeKind.String.getFullQualifiedName());//TODO check

        final CsdlProperty customerId = new CsdlProperty().setName(CUSTOMER_ID).setType(EdmPrimitiveTypeKind.String.getFullQualifiedName());
        final CsdlProperty sex = new CsdlProperty().setName(SEX).setType(EdmPrimitiveTypeKind.String.getFullQualifiedName());
        final CsdlProperty isSmoker = new CsdlProperty().setName(IS_SMOKER).setType(EdmPrimitiveTypeKind.Boolean.getFullQualifiedName());
        final CsdlProperty luggageWeight = new CsdlProperty().setName(LUGGAGE_WEIGHT).setType(EdmPrimitiveTypeKind.Double.getFullQualifiedName());

        final CsdlProperty weightUnit = new CsdlProperty().setName(WEIGHT_UNIT).setType(EdmPrimitiveTypeKind.String.getFullQualifiedName());
        final CsdlProperty hasInvoice = new CsdlProperty().setName(HAS_INVOICE).setType(EdmPrimitiveTypeKind.Boolean.getFullQualifiedName());
        final CsdlProperty flightClass = new CsdlProperty().setName(FLIGHT_CLASS).setType(EdmPrimitiveTypeKind.String.getFullQualifiedName());
        final CsdlProperty orderDate = new CsdlProperty().setName(ORDER_DATE).setType(EdmPrimitiveTypeKind.String.getFullQualifiedName());
        final CsdlProperty isCancelled = new CsdlProperty().setName(IS_CANCELLED).setType(EdmPrimitiveTypeKind.Boolean.getFullQualifiedName());
        final CsdlProperty isReserved = new CsdlProperty().setName(IS_RESERVED).setType(EdmPrimitiveTypeKind.Boolean.getFullQualifiedName());

        final CsdlPropertyRef propRefBookingId = new CsdlPropertyRef().setName(BOOKING_ID);
        final CsdlPropertyRef propRefCarrierId = new CsdlPropertyRef().setName(BOOKING_ID);
        final CsdlPropertyRef propRefConnectionId = new CsdlPropertyRef().setName(BOOKING_ID);
        final CsdlPropertyRef propRefFlightDate = new CsdlPropertyRef().setName(BOOKING_ID);

        // navigation properties
        final CsdlNavigationProperty navPropFlight = new CsdlNavigationProperty().setName(ET_SFLIGHT_NAME)
                                                                                 .setType(ET_SFLIGHT_FQN)
                                                                                 .setPartner(ES_SBOOK_NAME)
                                                                                 .setCollection(false)
                                                                                 .setNullable(false);
        final CsdlNavigationProperty navPropConnection = new CsdlNavigationProperty().setName(ET_SPFLI_NAME)
                                                                                     .setType(ET_SPFLI_FQN)
                                                                                     .setPartner(ES_SBOOK_NAME)
                                                                                     .setCollection(false)
                                                                                     .setNullable(false);
        final CsdlNavigationProperty navPropCarrier = new CsdlNavigationProperty().setName(ET_SCARR_NAME)
                                                                                  .setType(ET_SCARR_FQN)
                                                                                  .setPartner(ES_SBOOK_NAME)
                                                                                  .setCollection(false)
                                                                                  .setNullable(false);
        //TODO implement flight

        entityType = new CsdlEntityType();
        entityType.setName(ET_SPFLI_NAME);
        entityType.setProperties(Arrays.asList(bookingId,
                                               carrierId,
                                               connectionId,
                                               flightDate,
                                               customerId,
                                               sex,
                                               isSmoker,
                                               luggageWeight,
                                               weightUnit,
                                               hasInvoice,
                                               flightClass,
                                               orderDate,
                                               isCancelled,
                                               isReserved));
        entityType.setKey(Arrays.asList(propRefBookingId, propRefCarrierId, propRefConnectionId, propRefFlightDate));

        final List<CsdlNavigationProperty> navPropList = new ArrayList<>(Arrays.asList(navPropFlight, navPropConnection, navPropCarrier));

        entityType.setNavigationProperties(navPropList);

        return entityType;
    }

    public static CsdlEntityType getPlaneEntityType() {
        CsdlEntityType entityType;
        CsdlProperty planeType = new CsdlProperty().setName(PLANE_TYPE).setType(EdmPrimitiveTypeKind.String.getFullQualifiedName());

        final CsdlProperty seatsMaxE = new CsdlProperty().setName(SEATS_MAX).setType(EdmPrimitiveTypeKind.Int32.getFullQualifiedName());//TODO evlt zu MAX_E?
        final CsdlProperty seatsMaxB = new CsdlProperty().setName(SEATS_MAX_B).setType(EdmPrimitiveTypeKind.Int32.getFullQualifiedName());
        final CsdlProperty seatsMaxF = new CsdlProperty().setName(SEATS_MAX_F).setType(EdmPrimitiveTypeKind.Int32.getFullQualifiedName());
        final CsdlProperty consumption = new CsdlProperty().setName(CONSUMPTION).setType(EdmPrimitiveTypeKind.Double.getFullQualifiedName());
        final CsdlProperty conUnit = new CsdlProperty().setName(CONSUM_UNIT).setType(EdmPrimitiveTypeKind.String.getFullQualifiedName());
        final CsdlProperty tankCapacity = new CsdlProperty().setName(TANK_CAPACITY).setType(EdmPrimitiveTypeKind.Double.getFullQualifiedName());
        final CsdlProperty tankCapUnit = new CsdlProperty().setName(TANK_CAP_UNIT).setType(EdmPrimitiveTypeKind.String.getFullQualifiedName());
        final CsdlProperty weight = new CsdlProperty().setName(WEIGHT).setType(EdmPrimitiveTypeKind.Double.getFullQualifiedName());
        final CsdlProperty weightUnit = new CsdlProperty().setName(WEIGHT_UNIT).setType(EdmPrimitiveTypeKind.String.getFullQualifiedName());
        final CsdlProperty span = new CsdlProperty().setName(SPAN).setType(EdmPrimitiveTypeKind.Double.getFullQualifiedName());
        final CsdlProperty spanUnit = new CsdlProperty().setName(SPAN_UNIT).setType(EdmPrimitiveTypeKind.String.getFullQualifiedName());
        final CsdlProperty length = new CsdlProperty().setName(LENGTH).setType(EdmPrimitiveTypeKind.Double.getFullQualifiedName());
        final CsdlProperty lengthUnit = new CsdlProperty().setName(LENGTH_UNIT).setType(EdmPrimitiveTypeKind.String.getFullQualifiedName());
        final CsdlProperty speed = new CsdlProperty().setName(SPEED).setType(EdmPrimitiveTypeKind.Double.getFullQualifiedName());
        final CsdlProperty speedUnit = new CsdlProperty().setName(SPEED_UNIT).setType(EdmPrimitiveTypeKind.String.getFullQualifiedName());
        final CsdlProperty producer = new CsdlProperty().setName(PRODUCER).setType(EdmPrimitiveTypeKind.String.getFullQualifiedName());

        final CsdlPropertyRef propRefBookingId = new CsdlPropertyRef().setName(BOOKING_ID);
        final CsdlPropertyRef propRefCarrierId = new CsdlPropertyRef().setName(BOOKING_ID);
        final CsdlPropertyRef propRefConnectionId = new CsdlPropertyRef().setName(BOOKING_ID);
        final CsdlPropertyRef propRefFlightDate = new CsdlPropertyRef().setName(BOOKING_ID);

        // navigation properties
        final CsdlNavigationProperty navPropFlights = new CsdlNavigationProperty().setName(ES_SFLIGHT_NAME)
                                                                                  .setType(ET_SFLIGHT_FQN)
                                                                                  .setPartner(ET_SAPLANE_NAME)
                                                                                  .setCollection(true)
                                                                                  .setNullable(false);
        //TODO implement navigation to connection, booking, carrier

        final List<CsdlNavigationProperty> navPropList = new ArrayList<>(Arrays.asList(navPropFlights));

        entityType = new CsdlEntityType();
        entityType.setName(ET_SPFLI_NAME);
        entityType.setProperties(Arrays.asList(planeType,
                                               seatsMaxE,
                                               seatsMaxB,
                                               seatsMaxF,
                                               consumption,
                                               conUnit,
                                               tankCapacity,
                                               tankCapUnit,
                                               weight,
                                               weightUnit,
                                               span,
                                               spanUnit,
                                               length,
                                               lengthUnit,
                                               speed,
                                               speedUnit,
                                               producer));
        entityType.setKey(Arrays.asList(propRefBookingId, propRefCarrierId, propRefConnectionId, propRefFlightDate));

        entityType.setNavigationProperties(navPropList);

        return entityType;
    }

}
