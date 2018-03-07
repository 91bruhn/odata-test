
package myservice.mynamespace.database.service;

import myservice.mynamespace.database.data.Saplane;
import myservice.mynamespace.database.data.Sbook;
import myservice.mynamespace.database.data.Scarr;
import myservice.mynamespace.database.data.Sflight;
import myservice.mynamespace.database.data.Spfli;
import myservice.mynamespace.database.data.enums.Currency;
import myservice.mynamespace.database.data.enums.UnitOfLength;
import myservice.mynamespace.database.data.enums.UnitOfMass;
import myservice.mynamespace.database.data.enums.UnitOfSpeed;
import myservice.mynamespace.util.EntityNames;
import org.apache.olingo.commons.api.data.Entity;
import org.apache.olingo.commons.api.data.Property;
import org.apache.olingo.commons.api.data.ValueType;
import org.apache.olingo.commons.api.ex.ODataRuntimeException;

import java.net.URI;
import java.net.URISyntaxException;

import static myservice.mynamespace.util.EntityNames.AIRPORT_FROM;
import static myservice.mynamespace.util.EntityNames.AIRPORT_TO;
import static myservice.mynamespace.util.EntityNames.ARRIVAL_TIME;
import static myservice.mynamespace.util.EntityNames.BOOKING_ID;
import static myservice.mynamespace.util.EntityNames.CARRIER_ID;
import static myservice.mynamespace.util.EntityNames.CARRIER_NAME;
import static myservice.mynamespace.util.EntityNames.CITY_FROM;
import static myservice.mynamespace.util.EntityNames.CITY_TO;
import static myservice.mynamespace.util.EntityNames.CONNECTION_ID;
import static myservice.mynamespace.util.EntityNames.CONSUMPTION;
import static myservice.mynamespace.util.EntityNames.CONSUM_UNIT;
import static myservice.mynamespace.util.EntityNames.COUNTRY_FROM;
import static myservice.mynamespace.util.EntityNames.COUNTRY_TO;
import static myservice.mynamespace.util.EntityNames.CURRENCY;
import static myservice.mynamespace.util.EntityNames.CUSTOMER_ID;
import static myservice.mynamespace.util.EntityNames.DEPARTURE_TIME;
import static myservice.mynamespace.util.EntityNames.DISTANCE_UNIT;
import static myservice.mynamespace.util.EntityNames.DISTANCE____;
import static myservice.mynamespace.util.EntityNames.ES_SFLIGHT_NAME;
import static myservice.mynamespace.util.EntityNames.ES_SPFLI_NAME;
import static myservice.mynamespace.util.EntityNames.ET_SAPLANE_FQN;
import static myservice.mynamespace.util.EntityNames.ET_SBOOK_FQN;
import static myservice.mynamespace.util.EntityNames.ET_SCARR_FQN;
import static myservice.mynamespace.util.EntityNames.ET_SFLIGHT_FQN;
import static myservice.mynamespace.util.EntityNames.ET_SPFLI_FQN;
import static myservice.mynamespace.util.EntityNames.FLIGHT_CLASS;
import static myservice.mynamespace.util.EntityNames.FLIGHT_DATE;
import static myservice.mynamespace.util.EntityNames.FLIGHT_TIME;
import static myservice.mynamespace.util.EntityNames.FLIGHT_TYPE;
import static myservice.mynamespace.util.EntityNames.HAS_INVOICE;
import static myservice.mynamespace.util.EntityNames.IS_CANCELLED;
import static myservice.mynamespace.util.EntityNames.IS_RESERVED;
import static myservice.mynamespace.util.EntityNames.IS_SMOKER;
import static myservice.mynamespace.util.EntityNames.LENGTH;
import static myservice.mynamespace.util.EntityNames.LENGTH_UNIT;
import static myservice.mynamespace.util.EntityNames.LUGGAGE_WEIGHT;
import static myservice.mynamespace.util.EntityNames.ORDER_DATE;
import static myservice.mynamespace.util.EntityNames.PERIOD____;
import static myservice.mynamespace.util.EntityNames.PLANE_TYPE;
import static myservice.mynamespace.util.EntityNames.PRICE;
import static myservice.mynamespace.util.EntityNames.PRODUCER;
import static myservice.mynamespace.util.EntityNames.SCARR_URL;
import static myservice.mynamespace.util.EntityNames.SEATS_MAX;
import static myservice.mynamespace.util.EntityNames.SEATS_MAX_B;
import static myservice.mynamespace.util.EntityNames.SEATS_MAX_E;
import static myservice.mynamespace.util.EntityNames.SEATS_MAX_F;
import static myservice.mynamespace.util.EntityNames.SEATS_OCC_B;
import static myservice.mynamespace.util.EntityNames.SEATS_OCC_E;
import static myservice.mynamespace.util.EntityNames.SEATS_OCC_F;
import static myservice.mynamespace.util.EntityNames.SEX;
import static myservice.mynamespace.util.EntityNames.SPAN;
import static myservice.mynamespace.util.EntityNames.SPAN_UNIT;
import static myservice.mynamespace.util.EntityNames.SPEED;
import static myservice.mynamespace.util.EntityNames.SPEED_UNIT;
import static myservice.mynamespace.util.EntityNames.TANK_CAPACITY;
import static myservice.mynamespace.util.EntityNames.TANK_CAP_UNIT;
import static myservice.mynamespace.util.EntityNames.WEIGHT;
import static myservice.mynamespace.util.EntityNames.WEIGHT_UNIT;

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
        flight.addProperty(new Property(null, FLIGHT_DATE, ValueType.PRIMITIVE, sflight.getFlDate().getYear() + 1900));
        //        flight.addProperty(new Property(null, PLANE_TYPE, ValueType.PRIMITIVE, sflight.getPlaneType().getPlaneType()));

        //        flight.addProperty(new Property(null, PLANE_TYPE, ValueType.COLLECTION_ENTITY, sflight.getPlaneType()));//COLLECTION_COMPLEX

        flight.addProperty(new Property(null, PLANE_TYPE, ValueType.PRIMITIVE, sflight.getPlaneType().getPlaneType()));//COLLECTION_COMPLEX
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
        carrier.addProperty(new Property(null, CURRENCY, ValueType.PRIMITIVE, scarr.getCurrCode()));
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
        carrier.addProperty(new Property(null, WEIGHT_UNIT, ValueType.PRIMITIVE, sbook.getwUnit()));
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
        //IDs
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
        final String carrierId = (String) entity.getProperty(EntityNames.CARRIER_ID).getValue();
        final String carrierName = (String) entity.getProperty(EntityNames.CARRIER_NAME).getValue();
        final Currency currency = Currency.valueOf((String) entity.getProperty(EntityNames.CURRENCY).getValue());
        final String url = (String) entity.getProperty(EntityNames.SCARR_URL).getValue();

        return new Scarr(carrierId, carrierName, currency, url);
    }

    public static Saplane transformEntityToSaplane(Entity entity) {//TODO man könnte auch den typ abfragen um safe zu sein
        final String planeType = (String) entity.getProperty(EntityNames.PLANE_TYPE).getValue();
        final int seatsMax = (Integer) entity.getProperty(EntityNames.SEATS_MAX).getValue();
        final double consum = (Double) entity.getProperty(EntityNames.CONSUMPTION).getValue();
        final UnitOfMass conUnit = UnitOfMass.valueOf((String) entity.getProperty(EntityNames.CONSUM_UNIT).getValue());
        final double tankCap = (Double) entity.getProperty(EntityNames.TANK_CAPACITY).getValue();
        final UnitOfMass capUnit = UnitOfMass.valueOf((String) entity.getProperty(EntityNames.TANK_CAP_UNIT).getValue());
        final double weight = (Double) entity.getProperty(EntityNames.WEIGHT).getValue();
        final UnitOfMass weiUnit = UnitOfMass.valueOf((String) entity.getProperty(EntityNames.WEIGHT_UNIT).getValue());
        final double span = (Double) entity.getProperty(EntityNames.SPAN).getValue();
        final UnitOfLength spanUnit = UnitOfLength.valueOf((String) entity.getProperty(EntityNames.SPAN_UNIT).getValue());
        final double length = (Double) entity.getProperty(EntityNames.LENGTH).getValue();
        final UnitOfLength lengUnit = UnitOfLength.valueOf((String) entity.getProperty(EntityNames.LENGTH_UNIT).getValue());
        final double opSpeed = (Double) entity.getProperty(EntityNames.SPEED).getValue();
        final UnitOfSpeed speedUnit = UnitOfSpeed.valueOf((String) entity.getProperty(EntityNames.SPEED_UNIT).getValue());
        final String producer = (String) entity.getProperty(EntityNames.PRODUCER).getValue();
        final int seatsMaxB = (Integer) entity.getProperty(EntityNames.SEATS_MAX_B).getValue();
        final int seatsMaxF = (Integer) entity.getProperty(EntityNames.SEATS_MAX_F).getValue();

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

    private static Sflight transformEntityToSflight(Entity entity) {
        //        final String flDate = (String) entity.getProperty(EntityNames.PRODUCER).getValue();
        //        final String carrId = (String) entity.getProperty(EntityNames.PRODUCER).getValue();
        //        final String connId = (String) entity.getProperty(EntityNames.PRODUCER).getValue();
        //        final String planeType = (String) entity.getProperty(EntityNames.PRODUCER).getValue();
        //        final String price = (String) entity.getProperty(EntityNames.PRODUCER).getValue();
        //        final String currency = (String) entity.getProperty(EntityNames.PRODUCER).getValue();
        //        final String seatsMax = (String) entity.getProperty(EntityNames.PRODUCER).getValue();
        //        final String seatsOcc = (String) entity.getProperty(EntityNames.PRODUCER).getValue();
        //        final String seatsMaxB = (String) entity.getProperty(EntityNames.PRODUCER).getValue();
        //        final String seatsOccB = (String) entity.getProperty(EntityNames.PRODUCER).getValue();
        //        final String seatsMaxF = (String) entity.getProperty(EntityNames.PRODUCER).getValue();
        //        final String seatsOccF = (String) entity.getProperty(EntityNames.PRODUCER).getValue();
        //
        //        return new Sflight(flDate, carrId, connId, planeType, price, currency, seatsMax, seatsOcc, seatsMaxB, seatsOccB, seatsMaxF, seatsOccF);
        return null;
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
}
