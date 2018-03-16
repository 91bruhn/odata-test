package myservice.mynamespace.database.service;

import myservice.mynamespace.database.collections.Saplane;
import myservice.mynamespace.database.collections.Sbook;
import myservice.mynamespace.database.collections.Scarr;
import myservice.mynamespace.database.collections.Sflight;
import myservice.mynamespace.database.collections.Spfli;
import myservice.mynamespace.database.data.enums.UnitOfCurrency;
import myservice.mynamespace.database.data.enums.UnitOfLength;
import myservice.mynamespace.database.data.enums.UnitOfMass;
import myservice.mynamespace.database.data.enums.UnitOfSpeed;
import org.apache.olingo.commons.api.data.Entity;
import org.apache.olingo.commons.api.data.Property;
import org.apache.olingo.commons.api.data.ValueType;
import org.apache.olingo.commons.api.ex.ODataRuntimeException;

import java.net.URI;
import java.net.URISyntaxException;

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
import static myservice.mynamespace.service.entities.definitions.EntityNames.ES_SFLIGHT_NAME;
import static myservice.mynamespace.service.entities.definitions.EntityNames.ES_SPFLI_NAME;
import static myservice.mynamespace.service.entities.definitions.EntityNames.ET_SAPLANE_FQN;
import static myservice.mynamespace.service.entities.definitions.EntityNames.ET_SBOOK_FQN;
import static myservice.mynamespace.service.entities.definitions.EntityNames.ET_SCARR_FQN;
import static myservice.mynamespace.service.entities.definitions.EntityNames.ET_SFLIGHT_FQN;
import static myservice.mynamespace.service.entities.definitions.EntityNames.ET_SPFLI_FQN;
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
import static myservice.mynamespace.service.entities.definitions.EntityNames.SCARR_URL;
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
import static myservice.mynamespace.service.entities.definitions.EntityNames.WEIGHT;
import static myservice.mynamespace.service.entities.definitions.EntityNames.WEIGHT_UNIT;

/**
 *
 */
public class DataTransformator {

    // ------------------------------------------------------------------------
    // constants
    // ------------------------------------------------------------------------

    // ------------------------------------------------------------------------
    // members
    // ------------------------------------------------------------------------

    // ------------------------------------------------------------------------
    // constructors
    // ------------------------------------------------------------------------

    private DataTransformator() {}

    // ------------------------------------------------------------------------
    // methods
    // ------------------------------------------------------------------------

    //TRANSFORM TABLE OBJECT TO ENTITY

    public static Entity transformSflightToEntity(Sflight sflight) {
        Entity flight = new Entity();

        flight.addProperty(new Property(null, CARRIER_ID, ValueType.PRIMITIVE, sflight.getCarrId().getCarrId()));//Todo change name of getter
        flight.addProperty(new Property(null, CONNECTION_ID, ValueType.PRIMITIVE, sflight.getConnId().getConnId()));//Todo change name of getter
        //        flight.addProperty(new Property(null, FLIGHT_DATE, ValueType.PRIMITIVE, sflight.getFlDate().getYear() + 1900));
        flight.addProperty(new Property(null, FLIGHT_DATE, ValueType.PRIMITIVE, sflight.getFlDate()));

        //        flight.addProperty(new Property(null, PLANE_TYPE, ValueType.PRIMITIVE, sflight.getPlaneType().getPlaneType()));

        //        flight.addProperty(new Property(null, PLANE_TYPE, ValueType.COLLECTION_ENTITY, sflight.getPlaneType()));//COLLECTION_COMPLEX

        flight.addProperty(new Property(null, PLANE_TYPE, ValueType.PRIMITIVE, sflight.getPlaneType().getPlaneType()));//COLLECTION_COMPLEX
        //TODO löschen
        flight.addProperty(new Property(null, SPEED, ValueType.PRIMITIVE, sflight.getPlaneType().getOpSpeed()));//COLLECTION_COMPLEX

        flight.addProperty(new Property(null, PRICE, ValueType.PRIMITIVE, sflight.getPrice()));
        flight.addProperty(new Property(null, CURRENCY, ValueType.PRIMITIVE, sflight.getCurrency()));
        flight.addProperty(new Property(null, SEATS_MAX_E, ValueType.PRIMITIVE, sflight.getSeatsMax()));
        flight.addProperty(new Property(null, SEATS_OCC_E, ValueType.PRIMITIVE, sflight.getSeatsOcc()));
        flight.addProperty(new Property(null, SEATS_MAX_B, ValueType.PRIMITIVE, sflight.getSeatsMaxB()));
        flight.addProperty(new Property(null, SEATS_OCC_B, ValueType.PRIMITIVE, sflight.getSeatsOccB()));
        flight.addProperty(new Property(null, SEATS_MAX_F, ValueType.PRIMITIVE, sflight.getSeatsMaxF()));
        flight.addProperty(new Property(null, SEATS_OCC_F, ValueType.PRIMITIVE, sflight.getSeatsOccF()));

        flight.setType(ET_SFLIGHT_FQN.getFullQualifiedNameAsString());
        flight.setId(createId(flight, FLIGHT_DATE));//todo check compound id --> brauche ich das?

        return flight;
    }

    public static Entity transformSpfliToEntity(Spfli spfli) {
        Entity connection = new Entity();
        //TODO CARRIER_ID??
        connection.addProperty(new Property(null, CARRIER_ID, ValueType.PRIMITIVE, spfli.getCarrId().getCarrId()));
        connection.addProperty(new Property(null, CONNECTION_ID, ValueType.PRIMITIVE, spfli.getConnId()));
        connection.addProperty(new Property(null, AIRPORT_FROM, ValueType.PRIMITIVE, spfli.getAirpFrom()));
        connection.addProperty(new Property(null, CITY_FROM, ValueType.PRIMITIVE, spfli.getCityFrom()));
        connection.addProperty(new Property(null, COUNTRY_FROM, ValueType.PRIMITIVE, spfli.getCountryFrom()));
        connection.addProperty(new Property(null, AIRPORT_TO, ValueType.PRIMITIVE, spfli.getAirpTo()));
        connection.addProperty(new Property(null, CITY_TO, ValueType.PRIMITIVE, spfli.getCityTo()));
        connection.addProperty(new Property(null, COUNTRY_TO, ValueType.PRIMITIVE, spfli.getCountryTo()));
        connection.addProperty(new Property(null, FLIGHT_TIME, ValueType.PRIMITIVE, spfli.getFlTime()));
        connection.addProperty(new Property(null, DEPARTURE_TIME, ValueType.PRIMITIVE, spfli.getDepTime()));
        connection.addProperty(new Property(null, ARRIVAL_TIME, ValueType.PRIMITIVE, spfli.getArrTime()));
        connection.addProperty(new Property(null, DISTANCE____, ValueType.PRIMITIVE, spfli.getDistance()));
        connection.addProperty(new Property(null, DISTANCE_UNIT, ValueType.PRIMITIVE, spfli.getDistId().toString()));//TODO richtig so`?
        connection.addProperty(new Property(null, FLIGHT_TYPE, ValueType.PRIMITIVE, spfli.getFlType()));
        connection.addProperty(new Property(null, PERIOD____, ValueType.PRIMITIVE, spfli.getPeriod()));

        connection.setType(ET_SPFLI_FQN.getFullQualifiedNameAsString());
        //        flight.setId(createId(flight, FLIGHT_DATE));//todo check compound id

        return connection;
    }

    public static Entity transformScarrToEntity(Scarr scarr) {
        Entity carrier = new Entity();
        carrier.addProperty(new Property(null, CARRIER_ID, ValueType.PRIMITIVE, scarr.getCarrId()));
        carrier.addProperty(new Property(null, CARRIER_NAME, ValueType.PRIMITIVE, scarr.getCarrName()));
        carrier.addProperty(new Property(null, CURRENCY, ValueType.ENUM, scarr.getCurrCode()));
        carrier.addProperty(new Property(null, SCARR_URL, ValueType.PRIMITIVE, scarr.getUrl()));

        carrier.setType(ET_SCARR_FQN.getFullQualifiedNameAsString());
        //        carrier.setId(createId(carrier, CARRIER_ID));//todo check compound id

        return carrier;
    }

    public static Entity transformSbookToEntity(Sbook sbook) {
        Entity carrier = new Entity();
        //IDs
        carrier.addProperty(new Property(null, BOOKING_ID, ValueType.PRIMITIVE, sbook.getBookId()));
        carrier.addProperty(new Property(null, CARRIER_ID, ValueType.PRIMITIVE, sbook.getCarrId().getCarrId()));
        carrier.addProperty(new Property(null, CONNECTION_ID, ValueType.PRIMITIVE, sbook.getConnId().getConnId()));
        carrier.addProperty(new Property(null, FLIGHT_DATE, ValueType.PRIMITIVE, sbook.getFlDate().getFlDate()));

        carrier.addProperty(new Property(null, CUSTOMER_ID, ValueType.PRIMITIVE, sbook.getCustomId()));
        carrier.addProperty(new Property(null, SEX, ValueType.PRIMITIVE, sbook.getCustType()));
        carrier.addProperty(new Property(null, IS_SMOKER, ValueType.PRIMITIVE, sbook.isSmoker()));
        carrier.addProperty(new Property(null, LUGGAGE_WEIGHT, ValueType.PRIMITIVE, sbook.getLuggWeight()));
        carrier.addProperty(new Property(null, WEIGHT_UNIT, ValueType.ENUM, sbook.getwUnit()));//TODO neuer test mit ENUM
        carrier.addProperty(new Property(null, HAS_INVOICE, ValueType.PRIMITIVE, sbook.hasInvoice()));
        carrier.addProperty(new Property(null, FLIGHT_CLASS, ValueType.PRIMITIVE, sbook.getFlightClass()));
        carrier.addProperty(new Property(null, ORDER_DATE, ValueType.PRIMITIVE, sbook.getOrderDate()));
        carrier.addProperty(new Property(null, IS_CANCELLED, ValueType.PRIMITIVE, sbook.isCancelled()));
        carrier.addProperty(new Property(null, IS_RESERVED, ValueType.PRIMITIVE, sbook.isReserved()));

        carrier.setType(ET_SBOOK_FQN.getFullQualifiedNameAsString());
        //        carrier.setId(createId(carrier, CARRIER_ID));//todo check compound id

        return carrier;
    }

    public static Entity transformSaplaneToEntity(Saplane saplane) {
        Entity carrier = new Entity();
        //IDs --> Key Property
        carrier.addProperty(new Property(null, PLANE_TYPE, ValueType.PRIMITIVE, saplane.getPlaneType()));

        carrier.addProperty(new Property(null, SEATS_MAX, ValueType.PRIMITIVE, saplane.getSeatsMax()));
        carrier.addProperty(new Property(null, SEATS_MAX_B, ValueType.PRIMITIVE, saplane.getSeatsMaxB()));
        carrier.addProperty(new Property(null, SEATS_MAX_F, ValueType.PRIMITIVE, saplane.getSeatsMaxF()));
        carrier.addProperty(new Property(null, CONSUMPTION, ValueType.PRIMITIVE, saplane.getConsum()));
        carrier.addProperty(new Property(null, CONSUM_UNIT, ValueType.PRIMITIVE, saplane.getConUnit()));
        carrier.addProperty(new Property(null, TANK_CAPACITY, ValueType.PRIMITIVE, saplane.getTankCap()));
        carrier.addProperty(new Property(null, TANK_CAP_UNIT, ValueType.PRIMITIVE, saplane.getCapUnit()));
        carrier.addProperty(new Property(null, WEIGHT, ValueType.PRIMITIVE, saplane.getWeight()));
        carrier.addProperty(new Property(null, WEIGHT_UNIT, ValueType.PRIMITIVE, saplane.getWeiUnit()));
        carrier.addProperty(new Property(null, SPAN, ValueType.PRIMITIVE, saplane.getSpan()));
        carrier.addProperty(new Property(null, SPAN_UNIT, ValueType.PRIMITIVE, saplane.getSpanUnit()));
        carrier.addProperty(new Property(null, LENGTH, ValueType.PRIMITIVE, saplane.getLength()));
        carrier.addProperty(new Property(null, LENGTH_UNIT, ValueType.PRIMITIVE, saplane.getLengUnit()));
        carrier.addProperty(new Property(null, SPEED, ValueType.PRIMITIVE, saplane.getOpSpeed()));
        carrier.addProperty(new Property(null, SPEED_UNIT, ValueType.PRIMITIVE, saplane.getSpeedUnit()));
        carrier.addProperty(new Property(null, PRODUCER, ValueType.PRIMITIVE, saplane.getProducer()));

        carrier.setType(ET_SAPLANE_FQN.getFullQualifiedNameAsString());
        //        carrier.setId(createId(carrier, CARRIER_ID));//todo check compound id

        return carrier;
    }

    //TRANSFORM ENTITY TO TABLE OBJECT
    public static Scarr transformEntityToScarr(Entity entity) {//TODO man könnte auch den typ abfragen um safe zu sein
        final String carrierId = getStringValue(entity, CARRIER_ID);
        final String carrierName = getStringValue(entity, CARRIER_NAME);
        final UnitOfCurrency currency = getUnitOfCurrencyValue(entity, CURRENCY);
        final String url = getStringValue(entity, SCARR_URL);

        return new Scarr(carrierId, carrierName, currency, url);
    }

    public static Saplane transformEntityToSaplane(Entity entity) {//TODO man könnte auch den typ abfragen um safe zu sein
        final String planeType = getStringValue(entity, PLANE_TYPE);
        final int seatsMax = getIntegerValue(entity, SEATS_MAX);
        final double consum = getDoubleValue(entity, CONSUMPTION);
        final UnitOfMass conUnit = getUnitOfMassValue(entity, CONSUM_UNIT);
        final double tankCap = getDoubleValue(entity, TANK_CAPACITY);
        final UnitOfMass capUnit = getUnitOfMassValue(entity, TANK_CAP_UNIT);
        final double weight = getDoubleValue(entity, WEIGHT);
        final UnitOfMass weiUnit = getUnitOfMassValue(entity, WEIGHT_UNIT);
        final double span = getDoubleValue(entity, SPAN);
        final UnitOfLength spanUnit = getUnitOfLengthValue(entity, SPAN_UNIT);
        final double length = getDoubleValue(entity, LENGTH);
        final UnitOfLength lengUnit = getUnitOfLengthValue(entity, LENGTH_UNIT);
        final double opSpeed = getDoubleValue(entity, SPEED);
        final UnitOfSpeed speedUnit = getUnitOfSpeedValue(entity, SPEED_UNIT);
        final String producer = getStringValue(entity, PRODUCER);
        final int seatsMaxB = getIntegerValue(entity, SEATS_MAX_B);
        final int seatsMaxF = getIntegerValue(entity, SEATS_MAX_F);

        return new Saplane(planeType,
                           seatsMax,
                           consum,
                           conUnit,
                           tankCap,
                           capUnit,
                           weight,
                           weiUnit,
                           span,
                           spanUnit,
                           length,
                           lengUnit,
                           opSpeed,
                           speedUnit,
                           producer,
                           seatsMaxB,
                           seatsMaxF);
    }

    public static Sflight transformEntityToSflight(Entity entity, Scarr scarr, Spfli spfli, Saplane saplane) {
        final String flDate = getStringValue(entity, FLIGHT_DATE);
        final double price = getDoubleValue(entity, PRICE);
        final UnitOfCurrency currency = getUnitOfCurrencyValue(entity, CURRENCY);
        final int seatsMax = getIntegerValue(entity, SEATS_MAX_E);
        final int seatsOcc = getIntegerValue(entity, SEATS_OCC_E);
        final int seatsMaxB = getIntegerValue(entity, SEATS_MAX_B);
        final int seatsOccB = getIntegerValue(entity, SEATS_OCC_B);
        final int seatsMaxF = getIntegerValue(entity, SEATS_MAX_F);
        final int seatsOccF = getIntegerValue(entity, SEATS_OCC_F);

        return new Sflight(flDate, scarr, spfli, saplane, price, currency, seatsMax, seatsOcc, seatsMaxB, seatsOccB, seatsMaxF, seatsOccF);
    }

    public static Sbook transformEntityToSbook(Entity entity, Scarr scarr, Spfli spfli, Sflight sflight) {
        final Sbook sbook = new Sbook();

        sbook.setBookId(getStringValue(entity, BOOKING_ID));
        sbook.setCarrId(scarr);
        sbook.setConnId(spfli);
        sbook.setFlDate(sflight);
        sbook.setCustomId(getStringValue(entity, CUSTOMER_ID));
        sbook.setCustType(getStringValue(entity, SEX));
        sbook.setSmoker(getBooleanValue(entity, IS_SMOKER));
        sbook.setLuggWeight(getDoubleValue(entity, LUGGAGE_WEIGHT));
        sbook.setwUnit(getUnitOfMassValue(entity, WEIGHT_UNIT));
        sbook.setInvoice(getBooleanValue(entity, HAS_INVOICE));
        sbook.setFlightClass(getStringValue(entity, FLIGHT_CLASS));
        sbook.setOrderDate(getStringValue(entity, ORDER_DATE));
        sbook.setCancelled(getBooleanValue(entity, IS_CANCELLED));
        sbook.setReserved(getBooleanValue(entity, IS_RESERVED));

        return sbook;
    }

    public static Spfli transformEntityToSpfli(Entity entity, Scarr carrier) {
        final String connId = getStringValue(entity, CONNECTION_ID);
        final String airpFrom = getStringValue(entity, AIRPORT_FROM);
        final String airpTo = getStringValue(entity, AIRPORT_TO);
        final String cityFrom = getStringValue(entity, CITY_FROM);
        final String cityTo = getStringValue(entity, CITY_TO);
        final String countryFrom = getStringValue(entity, COUNTRY_FROM);
        final String countryTo = getStringValue(entity, COUNTRY_TO);
        final int flTime = getIntegerValue(entity, FLIGHT_TIME);
        final String depTime = getStringValue(entity, DEPARTURE_TIME);
        final String arrTime = getStringValue(entity, ARRIVAL_TIME);
        final double distance = getDoubleValue(entity, DISTANCE____);
        final UnitOfLength distId = getUnitOfLengthValue(entity, DISTANCE_UNIT);
        final String flType = getStringValue(entity, FLIGHT_TYPE);
        final int period = getIntegerValue(entity, PERIOD____);

        return new Spfli(connId,
                         carrier,
                         airpFrom,
                         airpTo,
                         cityFrom,
                         cityTo,
                         countryFrom,
                         countryTo,
                         flTime,
                         depTime,
                         arrTime,
                         distance,
                         distId,
                         flType,
                         period);
    }

    /* HELPER */ //TODO delete?
    private static URI createId(Entity entity, String idPropertyName) {
        return createId(entity, idPropertyName, null);
    }

    private static URI createId(Entity entity, String idPropertyName, String navigationName) {
        try {
            StringBuilder sb = new StringBuilder(getEntitySetName(entity));//.append("(");
            final Property property = entity.getProperty(idPropertyName);
            sb.append(property.asPrimitive());//.append(")");
            if (navigationName != null) {
                sb.append("/").append(navigationName);
            }
            return new URI(sb.toString());
        } catch (URISyntaxException e) {
            throw new ODataRuntimeException("Unable to create (Atom) id for entity: " + entity, e);
        }
    }

    private static String getEntitySetName(Entity entity) {
        if (ET_SPFLI_FQN.getFullQualifiedNameAsString().equals(entity.getType())) {
            return ES_SPFLI_NAME;
        } else if (ET_SFLIGHT_FQN.getFullQualifiedNameAsString().equals(entity.getType())) {
            return ES_SFLIGHT_NAME;
        }
        return entity.getType();
    }

    private static String getStringValue(Entity entity, String propertyName) {
        final Property property = getProperty(entity, propertyName);
        return property == null ? null : (String) property.getValue();
    }

    private static Double getDoubleValue(Entity entity, String propertyName) {
        final Property property = getProperty(entity, propertyName);
        return property == null ? 0 : (Double) property.getValue();
    }

    private static Integer getIntegerValue(Entity entity, String propertyName) {
        final Property property = getProperty(entity, propertyName);
        return property == null ? 0 : (Integer) property.getValue();
    }

    private static Boolean getBooleanValue(Entity entity, String propertyName) {
        final Property property = getProperty(entity, propertyName);
        //todo fucks
        return property == null ? false : (Boolean) property.getValue();
    }

    private static UnitOfCurrency getUnitOfCurrencyValue(Entity entity, String propertyName) {
        final Property property = getProperty(entity, propertyName);
        if (property != null) {
            String stuff = (String) (property.getValue());
            UnitOfCurrency of = UnitOfCurrency.valueOf(stuff);
            return of;
        } else {
            return null;

        }
        //        return property == null ? null : UnitOfCurrency.valueOf((String) property.getValue());
    }

    private static UnitOfMass getUnitOfMassValue(Entity entity, String propertyName) {
        final Property property = getProperty(entity, propertyName);
        return property == null ? null : UnitOfMass.valueOf((String) property.getValue());
    }

    private static UnitOfSpeed getUnitOfSpeedValue(Entity entity, String propertyName) {
        final Property property = getProperty(entity, propertyName);
        return property == null ? null : UnitOfSpeed.valueOf((String) property.getValue());
    }

    private static UnitOfLength getUnitOfLengthValue(Entity entity, String propertyName) {
        final Property property = getProperty(entity, propertyName);
        return property == null ? null : UnitOfLength.valueOf((String) property.getValue());
    }

    private static Property getProperty(Entity entity, String propertyName) {
        final Property property = entity.getProperty(propertyName);

        if (property != null) {
            if (property.getValue() != null) {
                return property;
            }
        }
        return null;
    }
}
