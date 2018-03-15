package myservice.mynamespace.service.entities.definitions;

import org.apache.olingo.commons.api.edm.FullQualifiedName;

/**
 *
 */
public class EntityNames {

    // Morphia TODO vllt woanders hin damit
    public static final String DB_NAME = "Flugdatenverwaltung";
    public static final String PACKAGE_NAME = "myservice.mynamespace.database.data";

    // Service Namespace
    public static final String NAMESPACE = "OData.Flugdatenverwaltung";//TODO flugdatenverwaltung?? --> flight data management

    // EDM Container
    public static final String CONTAINER_NAME = "Container";//TODO flugdaten??
    public static final FullQualifiedName CONTAINER = new FullQualifiedName(NAMESPACE, CONTAINER_NAME);

    // Entity Types Names
    public static final String ET_SAPLANE_NAME = "Airplane";
    public static final FullQualifiedName ET_SAPLANE_FQN = new FullQualifiedName(NAMESPACE, ET_SAPLANE_NAME);

    public static final String ET_SCARR_NAME = "Carrier";
    public static final FullQualifiedName ET_SCARR_FQN = new FullQualifiedName(NAMESPACE, ET_SCARR_NAME);

    public static final String ET_SPFLI_NAME = "Connection";
    public static final FullQualifiedName ET_SPFLI_FQN = new FullQualifiedName(NAMESPACE, ET_SPFLI_NAME);

    public static final String ET_SFLIGHT_NAME = "Flight";
    public static final FullQualifiedName ET_SFLIGHT_FQN = new FullQualifiedName(NAMESPACE, ET_SFLIGHT_NAME);

    public static final String ET_SBOOK_NAME = "Booking";
    public static final FullQualifiedName ET_SBOOK_FQN = new FullQualifiedName(NAMESPACE, ET_SBOOK_NAME);

    // Entity Set Names
    public static final String ES_SCARR_NAME = "Carriers";
    public static final String ES_SAPLANE_NAME = "Airplanes";
    public static final String ES_SPFLI_NAME = "Connections";
    public static final String ES_SFLIGHT_NAME = "Flights";
    public static final String ES_SBOOK_NAME = "Bookings";

    // Mongo DB ID Naming
    public static final String DB_ID = "_id";
    public static final String DB_CARRIER_ID = "carrId";
    public static final String DB_CONNECTION_ID = "connId";
    public static final String DB_FLIGHT_DATE = "flDate";


    //COMMON --> URL-PATH-NAMES //TODO Umwandlung möglich? URL = PRICE; Anzeige = Airfare
    public static final String CARRIER_ID = "CarrierCode";
    public static final String CONNECTION_ID = "FlightConnectionNumber";
    public static final String FLIGHT_DATE = "FlightDate";
    public static final String PLANE_TYPE = "PlaneType";
    public static final String BOOKING_ID = "BookingId";

    public static final String CARRIER_NAME = "CarrierName";
    public static final String URL = "URL";
    public static final String PRICE = "Airfare";
    public static final String CURRENCY = "LocalCurrencyOfAirline";
    public static final String SEATS_MAX_E = "MaxCapacityOfSeatsInEconomyClass";
    public static final String SEATS_OCC_E = "OccupiedSeatsInEconomyClass";
    public static final String SEATS_MAX_B = "Max. capacity of Seats in Business Class";
    public static final String SEATS_OCC_B = "Occupied Seats in Business Class";
    public static final String SEATS_MAX_F = "Max. capacity of Seats in First Class";
    public static final String SEATS_OCC_F = "Occupied Seats in First Class";

    //entity spfli
    public static final String COUNTRY_FROM = "Departure Country (Key)";//TODO adde zu wessen das jew. der key ist
    public static final String CITY_FROM = "Departure City";
    public static final String AIRPORT_FROM = "Departure Airport";
    public static final String DEPARTURE_TIME = "Departure Time";
    public static final String COUNTRY_TO = "Arrival Country (Key)";
    public static final String CITY_TO = "Arrival City";
    public static final String AIRPORT_TO = "Arrival Airport";
    public static final String ARRIVAL_TIME = "Arrival Time";
    public static final String FLIGHT_TIME = "Flight Time";
    public static final String DISTANCE____ = "Distance";//TODO RENAME
    public static final String DISTANCE_UNIT = "Mass Unit of Distance";
    public static final String FLIGHT_TYPE = "Flight Type";
    public static final String PERIOD____ = "Arrival in Day(s) later";

    //SBOOK
    public static final String CUSTOMER_ID = "CustomerId";
    public static final String SEX = "Sex";
    public static final String IS_SMOKER = "IsSmoker";
    public static final String LUGGAGE_WEIGHT = "LuggageWeight";
    public static final String WEIGHT_UNIT = "WeightUnit"; //TODO  SSSSSSSSSSSSSSSSS
    public static final String HAS_INVOICE = "InvoiceAvailable";
    public static final String FLIGHT_CLASS = "FlightClass";
    public static final String ORDER_DATE = "OrderDate";
    public static final String IS_CANCELLED = "IsCancelled";
    public static final String IS_RESERVED = "IsReserved";

    //SAPLANE
    public static final String SEATS_MAX = "MaxSeats";
    public static final String CONSUMPTION = "Consumption";
    public static final String CONSUM_UNIT = "MassUnitOfConsumption";
    public static final String TANK_CAPACITY = "TankCapacity";
    public static final String TANK_CAP_UNIT = "UnitOfTankCapacity";
    public static final String WEIGHT = "Weight";
    public static final String SPAN = "WingSpan";
    public static final String SPAN_UNIT = "UnitOfSpan";
    public static final String LENGTH = "Length";
    public static final String LENGTH_UNIT = "UnitOfLength";
    public static final String SPEED = "Speed";
    public static final String SPEED_UNIT = "UnitOfSpeed";
    public static final String PRODUCER = " Producer";

    //SCARR
    public static final String SCARR = "SCARR";
    public static final String SCARR_CARRID = "CARRID";
    public static final String SCARR_CARRNAME = "CARRNAME";
    public static final String SCARR_CURRCODE = "CURRCODE";
    public static final String SCARR_URL = "URL";

    //SAPLANE
    public static final String SAPLANE = "SAPLANE";
    public static final String SAPLANE_PLANETYPE = "PLANETYPE";//TODO rename check oben
    public static final String SAPLANE_SEATSMAX = "SEATSMAX";
    public static final String SAPLANE_CONSUM = "CONSUM";
    public static final String SAPLANE_CON_UNIT = "CON_UNIT";
    public static final String SAPLANE_TANKCAP = "TANKCAP";
    public static final String SAPLANE_CAP_UNIT = "CAP_UNIT";
    public static final String SAPLANE_WEIGHT = "WEIGHT";
    public static final String SAPLANE_WEI_UNIT = "WEI_UNIT";
    public static final String SAPLANE_SPAN = "SPAN";
    public static final String SAPLANE_SPAN_UNIT = "SPAN_UNIT";
    public static final String SAPLANE_LENG = "LENG";
    public static final String SAPLANE_LENG_UNIT = "LENG_UNIT";
    public static final String SAPLANE_OP_SPEED = "OP_SPEED";
    public static final String SAPLANE_SPEED_UNIT = "SPEED_UNIT";
    public static final String SAPLANE_PRODUCER = "PRODUCER";
    public static final String SAPLANE_SEATSMAX_B = "SEATSMAX_B";
    public static final String SAPLANE_SEATSMAX_F = "SEATSMAX_F";

    //SPFLI
    public static final String SPFLI = "SPFLI";
    public static final String SPFLI_CONNID = "CONNID";
    public static final String SPFLI_CARRID = "CARRID";//common --> zum ursprung
    public static final String SPFLI_AIRPFROM = "AIRPFROM";
    public static final String SPFLI_AIRPTO = "AIRPTO";
    public static final String SPFLI_CITYFROM = "CITYFROM";
    public static final String SPFLI_CITYTO = "CITYTO";
    public static final String SPFLI_COUNTRYFR = "COUNTRYFR";
    public static final String SPFLI_COUNTRYTO = "COUNTRYTO";
    public static final String SPFLI_FLTIME = "FLTIME";
    public static final String SPFLI_DEPTIME = "DEPTIME";
    public static final String SPFLI_ARRTIME = "ARRTIME";
    public static final String SPFLI_DISTANCE = "DISTANCE";
    public static final String SPFLI_DISTID = "DISTID";
    public static final String SPFLI_FLTYPE = "FLTYPE";
    public static final String SPFLI_PERIOD = "PERIOD";

    //SFLIGHT
    public static final String SFLIGHT = "SFLIGHT";
    public static final String SFLIGHT_FLDATE = "FLDATE"; //--> spezifische unabhängig
    public static final String SFLIGHT_CARRID = "CARRID";
    public static final String SFLIGHT_CONNID = "CONNID";
    public static final String SFLIGHT_PLANETYPE = "PLANETYPE";
    public static final String SFLIGHT_PRICE = "PRICE";
    public static final String SFLIGHT_CURRENCY = "CURRENCY";
    public static final String SFLIGHT_SEATSMAX = "SEATSMAX";
    public static final String SFLIGHT_SEATSOCC = "SEATSOCC";
    public static final String SFLIGHT_PAYMENTSUM = "PAYMENTSUM";
    public static final String SFLIGHT_SEATSMAX_B = "SEATSMAX_B";
    public static final String SFLIGHT_SEATSOCC_B = "SEATSOCC_B";
    public static final String SFLIGHT_SEATSMAX_F = "SEATSMAX_F";
    public static final String SFLIGHT_SEATSOCC_F = "SEATSOCC_F";

    //SBOOK
    public static final String SBOOK = "SBOOK";
    public static final String SBOOK_BOOKID = "BOOKID";
    public static final String SBOOK_CARRID = "CARRID";
    public static final String SBOOK_CONNID = "CONNID";
    public static final String SBOOK_FLDATE = "FLDATE";
    public static final String SBOOK_CUSTOMID = "CUSTOMID";
    public static final String SBOOK_CUSTTYPE = "CUSTTYPE";
    public static final String SBOOK_SMOKER = "SMOKER";
    public static final String SBOOK_LUGGWEIGHT = "LUGGWEIGHT";
    public static final String SBOOK_WUNIT = "WUNIT";
    public static final String SBOOK_INVOICE = "INVOICE";
    public static final String SBOOK_CLASS = "CLASS";
    public static final String SBOOK_ORDER_DATE = "ORDER_DATE";
    public static final String SBOOK_CANCELLED = "CANCELLED";
    public static final String SBOOK_RESERVED = "RESERVED";

}
