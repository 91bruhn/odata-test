package myservice.mynamespace.database.service;

import com.mongodb.MongoClient;
import com.mongodb.util.JSON;
import myservice.mynamespace.database.collections.Saplane;
import myservice.mynamespace.database.collections.Sbook;
import myservice.mynamespace.database.collections.Scarr;
import myservice.mynamespace.database.collections.Sflight;
import myservice.mynamespace.database.collections.Spfli;
import myservice.mynamespace.database.data.enums.UnitOfCurrency;
import myservice.mynamespace.database.data.enums.UnitOfLength;
import myservice.mynamespace.database.data.enums.UnitOfMass;
import myservice.mynamespace.database.data.enums.UnitOfSpeed;
import org.apache.commons.collections.CollectionUtils;
import org.bson.BSONObject;
import org.mongodb.morphia.Datastore;
import org.mongodb.morphia.Morphia;
import org.mongodb.morphia.converters.LocalDateConverter;
import org.mongodb.morphia.converters.LocalDateTimeConverter;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.text.DecimalFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

import static myservice.mynamespace.service.entities.definitions.EntityNames.COUNTRY_FROM;
import static myservice.mynamespace.service.entities.definitions.EntityNames.COUNTRY_TO;
import static myservice.mynamespace.service.entities.definitions.EntityNames.SAPLANE_CAP_UNIT;
import static myservice.mynamespace.service.entities.definitions.EntityNames.SAPLANE_CONSUM;
import static myservice.mynamespace.service.entities.definitions.EntityNames.SAPLANE_CON_UNIT;
import static myservice.mynamespace.service.entities.definitions.EntityNames.SAPLANE_LENG;
import static myservice.mynamespace.service.entities.definitions.EntityNames.SAPLANE_LENG_UNIT;
import static myservice.mynamespace.service.entities.definitions.EntityNames.SAPLANE_OP_SPEED;
import static myservice.mynamespace.service.entities.definitions.EntityNames.SAPLANE_PLANETYPE;
import static myservice.mynamespace.service.entities.definitions.EntityNames.SAPLANE_PRODUCER;
import static myservice.mynamespace.service.entities.definitions.EntityNames.SAPLANE_SEATSMAX;
import static myservice.mynamespace.service.entities.definitions.EntityNames.SAPLANE_SEATSMAX_B;
import static myservice.mynamespace.service.entities.definitions.EntityNames.SAPLANE_SEATSMAX_F;
import static myservice.mynamespace.service.entities.definitions.EntityNames.SAPLANE_SPAN;
import static myservice.mynamespace.service.entities.definitions.EntityNames.SAPLANE_SPAN_UNIT;
import static myservice.mynamespace.service.entities.definitions.EntityNames.SAPLANE_SPEED_UNIT;
import static myservice.mynamespace.service.entities.definitions.EntityNames.SAPLANE_TANKCAP;
import static myservice.mynamespace.service.entities.definitions.EntityNames.SAPLANE_WEIGHT;
import static myservice.mynamespace.service.entities.definitions.EntityNames.SAPLANE_WEI_UNIT;
import static myservice.mynamespace.service.entities.definitions.EntityNames.SBOOK_BOOKID;
import static myservice.mynamespace.service.entities.definitions.EntityNames.SBOOK_CANCELLED;
import static myservice.mynamespace.service.entities.definitions.EntityNames.SBOOK_CARRID;
import static myservice.mynamespace.service.entities.definitions.EntityNames.SBOOK_CLASS;
import static myservice.mynamespace.service.entities.definitions.EntityNames.SBOOK_CONNID;
import static myservice.mynamespace.service.entities.definitions.EntityNames.SBOOK_CUSTOMID;
import static myservice.mynamespace.service.entities.definitions.EntityNames.SBOOK_CUSTTYPE;
import static myservice.mynamespace.service.entities.definitions.EntityNames.SBOOK_INVOICE;
import static myservice.mynamespace.service.entities.definitions.EntityNames.SBOOK_LUGGWEIGHT;
import static myservice.mynamespace.service.entities.definitions.EntityNames.SBOOK_ORDER_DATE;
import static myservice.mynamespace.service.entities.definitions.EntityNames.SBOOK_RESERVED;
import static myservice.mynamespace.service.entities.definitions.EntityNames.SBOOK_SMOKER;
import static myservice.mynamespace.service.entities.definitions.EntityNames.SBOOK_WUNIT;
import static myservice.mynamespace.service.entities.definitions.EntityNames.SCARR_CARRID;
import static myservice.mynamespace.service.entities.definitions.EntityNames.SCARR_CARRNAME;
import static myservice.mynamespace.service.entities.definitions.EntityNames.SCARR_CURRCODE;
import static myservice.mynamespace.service.entities.definitions.EntityNames.SCARR_URL;
import static myservice.mynamespace.service.entities.definitions.EntityNames.SFLIGHT_CARRID;
import static myservice.mynamespace.service.entities.definitions.EntityNames.SFLIGHT_CONNID;
import static myservice.mynamespace.service.entities.definitions.EntityNames.SFLIGHT_FLDATE;
import static myservice.mynamespace.service.entities.definitions.EntityNames.SFLIGHT_PLANETYPE;
import static myservice.mynamespace.service.entities.definitions.EntityNames.SPFLI_AIRPFROM;
import static myservice.mynamespace.service.entities.definitions.EntityNames.SPFLI_AIRPTO;
import static myservice.mynamespace.service.entities.definitions.EntityNames.SPFLI_ARRTIME;
import static myservice.mynamespace.service.entities.definitions.EntityNames.SPFLI_CARRID;
import static myservice.mynamespace.service.entities.definitions.EntityNames.SPFLI_CITYFROM;
import static myservice.mynamespace.service.entities.definitions.EntityNames.SPFLI_CITYTO;
import static myservice.mynamespace.service.entities.definitions.EntityNames.SPFLI_CONNID;
import static myservice.mynamespace.service.entities.definitions.EntityNames.SPFLI_DEPTIME;
import static myservice.mynamespace.service.entities.definitions.EntityNames.SPFLI_DISTANCE;
import static myservice.mynamespace.service.entities.definitions.EntityNames.SPFLI_DISTID;
import static myservice.mynamespace.service.entities.definitions.EntityNames.SPFLI_FLTIME;
import static myservice.mynamespace.service.entities.definitions.EntityNames.SPFLI_FLTYPE;
import static myservice.mynamespace.service.entities.definitions.EntityNames.SPFLI_PERIOD;

/**
 *
 */
public class DummyDataCreator {

    // ------------------------------------------------------------------------
    // constructors
    // ------------------------------------------------------------------------

    private DummyDataCreator() {}

    // ------------------------------------------------------------------------
    // methods
    // ------------------------------------------------------------------------

    public static void createTestData() {
        final MongoClient mongoClient = new MongoClient("localhost", 27017);
        final Morphia morphia = new Morphia();
        final Datastore datastore = morphia.createDatastore(mongoClient, "Flugdatenverwaltung");//TODO checke ob man vorher checkt ob die db nicht leer ist
        morphia.mapPackage("myservice.mynamespace.database.data");
        datastore.ensureIndexes();

        ////////////////////////SCARR////////////////////////
        BSONObject jsonArray = readJson("/dummyData/dummyDataScarr.json", DummyDataCreator.class);
        List<Scarr> carriers = transformScarr(jsonArray);
        datastore.save(carriers);
        ////////////////////////SAPLANE////////////////////////
        jsonArray = readJson("/dummyData/dummyDataSaplane.json", DummyDataCreator.class);
        List<Saplane> planes = transformSaplane(jsonArray);
        datastore.save(planes);
        ////////////////////////SPFLI////////////////////////
        jsonArray = readJson("/dummyData/dummyDataSpfli.json", DummyDataCreator.class);
        List<Spfli> connections = transformSpfli(jsonArray, carriers);
        datastore.save(connections);
        ////////////////////////SFLIGHTS////////////////////////
        jsonArray = readJson("/dummyData/dummyDataSflight.json", DummyDataCreator.class);
        List<Sflight> flights = transformSflight(jsonArray, carriers, connections, planes);
        datastore.save(flights);
        ////////////////////////SBOOKS////////////////////////
        jsonArray = readJson("/dummyData/dummyDataSbook.json", DummyDataCreator.class);
        List<Sbook> bookings = transformSbooking(jsonArray, carriers, connections, flights);//mehrzahl
        datastore.save(bookings);
    }

    private static BSONObject readJson(String path, Class clazz) {
        final InputStream inputStream = clazz.getResourceAsStream(path);
        final String json = readFromInputStream(inputStream);
        return (BSONObject) JSON.parse(json);
    }

    public static String readFromInputStream(InputStream inputStream) {
        StringBuilder resultStringBuilder = new StringBuilder();
        try {
            BufferedReader br = new BufferedReader(new InputStreamReader(inputStream));
            String line;
            while ((line = br.readLine()) != null) {
                resultStringBuilder.append(line).append("\n");
            }
        } catch (Exception e) {
            System.out.print("");
        }
        return resultStringBuilder.toString();
    }

    private static List<Scarr> transformScarr(BSONObject jsonArray) {
        final List<Scarr> carriers = new ArrayList<>(jsonArray.keySet().size());

        for (String key : jsonArray.keySet()) {
            final BSONObject jsonObject = (BSONObject) jsonArray.get(key);

            final Scarr carrier = new Scarr();
            carrier.setCarrId(convertToString(jsonObject, SCARR_CARRID));
            carrier.setCarrName(convertToString(jsonObject, SCARR_CARRNAME));
            carrier.setCurrCode(UnitOfCurrency.valueOf(convertToString(jsonObject, SCARR_CURRCODE)));
            carrier.setUrl(convertToString(jsonObject, SCARR_URL));

            carriers.add(carrier);
        }

        return carriers;
    }

    private static List<Saplane> transformSaplane(BSONObject jsonArray) {
        final List<Saplane> planes = new ArrayList<>(jsonArray.keySet().size());

        for (String key : jsonArray.keySet()) {
            final BSONObject json = (BSONObject) jsonArray.get(key);

            final Saplane plane = new Saplane();
            plane.setPlaneType(convertToString(json, SAPLANE_PLANETYPE));
            plane.setSeatsMax(convertToInteger(json, SAPLANE_SEATSMAX));
            plane.setConsum(convertStringToDouble(json, SAPLANE_CONSUM));
            plane.setConUnit(UnitOfMass.valueOf(convertToString(json, SAPLANE_CON_UNIT)));
            plane.setTankCap(convertStringToDouble(json, SAPLANE_TANKCAP));
            plane.setCapUnit(UnitOfMass.valueOf(convertToString(json, SAPLANE_CAP_UNIT)));
            plane.setWeight(convertStringToDouble(json, SAPLANE_WEIGHT));
            plane.setWeiUnit(UnitOfMass.valueOf(convertToString(json, SAPLANE_WEI_UNIT)));
            plane.setSpan(convertStringToDouble(json, SAPLANE_SPAN));
            plane.setSpanUnit(UnitOfLength.valueOf(convertToString(json, SAPLANE_SPAN_UNIT)));
            plane.setLength(convertStringToDouble(json, SAPLANE_LENG));
            plane.setLengUnit(UnitOfLength.valueOf(convertToString(json, SAPLANE_LENG_UNIT)));
            plane.setOpSpeed(convertStringToDouble(json, SAPLANE_OP_SPEED));
            plane.setSpeedUnit(UnitOfSpeed.valueOf(convertToString(json, SAPLANE_SPEED_UNIT)));
            plane.setProducer(convertToString(json, SAPLANE_PRODUCER));
            plane.setSeatsMaxB(convertToInteger(json, SAPLANE_SEATSMAX_B));
            plane.setSeatsMaxF(convertToInteger(json, SAPLANE_SEATSMAX_F));

            planes.add(plane);
        }

        return planes;
    }

    private static List<Spfli> transformSpfli(BSONObject jsonArray, List<Scarr> carriers) {
        final List<Spfli> connections = new ArrayList<>(jsonArray.keySet().size());

        for (String key : jsonArray.keySet()) {
            final BSONObject json = (BSONObject) jsonArray.get(key);
            final String carrId = convertToString(json, SPFLI_CARRID);
            final Scarr carrierFK = findForeignKeyScarr(carrId, carriers);

            if (carrierFK != null) {
                final Spfli connection = new Spfli();
                connection.setConnId(String.valueOf(convertToInteger(json, SPFLI_CONNID)));
                connection.setCarrId(carrierFK);
                connection.setAirpFrom(convertToString(json, SPFLI_AIRPFROM));
                connection.setAirpTo(convertToString(json, SPFLI_AIRPTO));
                connection.setCityFrom(convertToString(json, SPFLI_CITYFROM));
                connection.setCityTo(convertToString(json, SPFLI_CITYTO));
                connection.setCountryFrom(convertToString(json, COUNTRY_FROM));
                connection.setCountryTo(convertToString(json, COUNTRY_TO));
                connection.setFlTime(convertToInteger(json, SPFLI_FLTIME));
                connection.setDepTime(convertAndValidateStringAsTIMS(json, SPFLI_DEPTIME));
                connection.setArrTime(convertAndValidateStringAsTIMS(json, SPFLI_ARRTIME));
                connection.setDistance(convertToDouble(json, SPFLI_DISTANCE));
                connection.setDistId(UnitOfLength.valueOf(convertToString(json, SPFLI_DISTID)));
                connection.setFlType(convertToString(json, SPFLI_FLTYPE));
                connection.setPeriod(convertToInteger(json, SPFLI_PERIOD));

                connections.add(connection);
            }
        }

        return connections;
    }

    private static Scarr findForeignKeyScarr(String carrId, List<Scarr> carriers) {
        if (CollectionUtils.isNotEmpty(carriers)) {
            for (Scarr carrier : carriers) {
                if (carrId.equals(carrier.getCarrId())) {
                    return carrier;
                }
            }
        }
        return null;
    }

    private static Spfli findForeignKeySpfli(String connId, List<Spfli> connections) {
        if (CollectionUtils.isNotEmpty(connections)) {
            for (Spfli connection : connections) {
                if (connId.equals(connection.getConnId())) {
                    return connection;
                }
            }
        }
        return null;
    }

    private static Saplane findForeignKeySaplane(String planeType, List<Saplane> planes) {
        if (CollectionUtils.isNotEmpty(planes)) {
            for (Saplane plane : planes) {
                if (planeType.equals(plane.getPlaneType())) {
                    return plane;
                }
            }
        }
        return null;
    }

    private static List<Sflight> transformSflight(BSONObject jsonArray, List<Scarr> carriers, List<Spfli> connections, List<Saplane> planes) {
        final List<Sflight> flights = new ArrayList<>(jsonArray.keySet().size());

        for (String key : jsonArray.keySet()) {
            final BSONObject json = (BSONObject) jsonArray.get(key);

            final String carrId = convertToString(json, SFLIGHT_CARRID);
            final Scarr carrierFK = findForeignKeyScarr(carrId, carriers);

            final String connId = String.valueOf(convertToInteger(json, SFLIGHT_CONNID));
            final Spfli connectionFK = findForeignKeySpfli(connId, connections);

            final String planeType = convertToString(json, SFLIGHT_PLANETYPE);
            final Saplane planeFK = findForeignKeySaplane(planeType, planes);

            if (carrierFK != null && connectionFK != null && planeFK != null) {
                final int maxSeats = planeFK.getSeatsMax();
                final int maxSeatsB = planeFK.getSeatsMaxB();
                final int maxSeatsF = planeFK.getSeatsMaxF();

                final Sflight flight = new Sflight();
                flight.setFlDate(convertToString(json, SFLIGHT_FLDATE));//SAP's DATS format
                flight.setCarrId(carrierFK);
                flight.setConnId(connectionFK);
                flight.setPlaneType(planeFK);

                flight.setCurrency(carrierFK.getCurrCode());
                flight.setPrice(calculateFlightPrice(connectionFK.getDistance(), connectionFK.getDistId(), flight.getCurrency()));

                flight.setSeatsMax(maxSeats);
                flight.setSeatsMaxB(maxSeatsB);
                flight.setSeatsMaxF(maxSeatsF);
                flight.setSeatsOcc(calculateOccupiedSeats(maxSeats));
                flight.setSeatsOccB(calculateOccupiedSeats(maxSeatsB));
                flight.setSeatsOccF(calculateOccupiedSeats(maxSeatsF));

                flights.add(flight);
            }
        }

        return flights;
    }

    private static List<Sbook> transformSbooking(BSONObject jsonArray, List<Scarr> carriers, List<Spfli> connections, List<Sflight> flights) {
        final List<Sbook> bookings = new ArrayList<>(jsonArray.keySet().size());
        int counter = 0;

        for (String key : jsonArray.keySet()) {
            final BSONObject json = (BSONObject) jsonArray.get(key);

            final String carrId = convertToString(json, SBOOK_CARRID);
            final Scarr carrierFK = findForeignKeyScarr(carrId, carriers);

            final String connId = String.valueOf(convertToInteger(json, SBOOK_CONNID));
            final Spfli connectionFK = findForeignKeySpfli(connId, connections);

            //randomly connect a flight to a booking
            final Sflight flightFK = flights.get(counter++);
            if (counter == flights.size()) {
                counter = 0;
            }

            if (carrierFK != null && connectionFK != null) {
                final Sbook booking = new Sbook();
                //TODO maybe add value price use standard price of booking and multiply by class
                booking.setBookId(convertIntegerToString(json, SBOOK_BOOKID));
                booking.setCarrId(carrierFK);
                booking.setConnId(connectionFK);
                booking.setFlDate(flightFK);
                booking.setCustomId(convertIntegerToString(json, SBOOK_CUSTOMID));
                booking.setCustType(convertToCharacter(json, SBOOK_CUSTTYPE));
                booking.setSmoker(convertToCharacter(json, SBOOK_SMOKER));
                booking.setLuggWeight(convertToInteger(json, SBOOK_LUGGWEIGHT));
                booking.setwUnit(UnitOfMass.valueOf(convertToString(json, SBOOK_WUNIT)));
                booking.setInvoice(convertToCharacter(json, SBOOK_INVOICE));
                booking.setFlightClass(convertToCharacter(json, SBOOK_CLASS));
                booking.setOrderDate(convertToString(json, SBOOK_ORDER_DATE));//SAP's DATS format
                booking.setCancelled(convertToCharacter(json, SBOOK_CANCELLED));
                booking.setReserved(convertToCharacter(json, SBOOK_RESERVED));

                bookings.add(booking);
            }
        }

        return bookings;
    }//TODO andere die noch Date sind zurück auf String

    //todo consider moving to utils and using in concrete entity class
    private static String convertToString(BSONObject bsonObject, String value) {
        return (String) bsonObject.get(value);
    }

    private static Integer convertToInteger(BSONObject bsonObject, String value) {
        return (Integer) bsonObject.get(value);
    }

    private static Double convertToDouble(BSONObject bsonObject, String value) {
        return (Double) bsonObject.get(value);
    }

    private static Character convertToCharacter(BSONObject bsonObject, String value) {
        return ((String) bsonObject.get(value)).charAt(0);
    }

    private static String convertIntegerToString(BSONObject bsonObject, String value) {
        return String.valueOf(convertToInteger(bsonObject, value));
    }

    private static Double convertStringToDouble(BSONObject bsonObject, String value) {
        return Double.parseDouble((convertToString(bsonObject, value)).replace(".", ""));
    }

    //TODO erklärung, ich nehme die abflugszeit von der verbindung hier mit rein
    //    private static Date conv(BSONObject bsonObject, String value, LocalTime departureTime) {
    //        final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm:ss.SSSSSS");
    //        LocalDateTime localDateTime = LocalDateTime.parse(date, formatter);
    //
    //    }

    private static Date convertStringAsDateToDate(BSONObject bsonObject, String value) {//2 methhoden
        final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm:ss");
        final LocalDate date = LocalDate.parse(convertToString(bsonObject, value), formatter);
        final LocalDateConverter ldc = new LocalDateConverter();
        return (Date) ldc.encode(date);
    }

    private static Date convertToDateTEMP2(BSONObject bsonObject, String value) {//todo delete
        final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm");
        final LocalDate date = LocalDate.parse(convertToString(bsonObject, value), formatter);
        final LocalTime time = LocalTime.of(createRandomNumber(0, 24), createRandomNumber(0, 60), 0);
        final LocalDateTime ldt = LocalDateTime.parse(convertToString(bsonObject, value), formatter);
        final LocalDateTime ldt2 = LocalDateTime.of(date, time);
        final LocalDateTimeConverter ldtc = new LocalDateTimeConverter();
        final LocalDateConverter ldc = new LocalDateConverter();
        return (Date) ldtc.encode(ldt);

    }

    /**
     * Validates a String that is supposed to be in SAP's {@code TIMS}-format.
     * <p>
     * Note: If the value for seconds is invalid, the actual time is still considered valid.
     * Therefore, seconds may be a 0 in that case.
     *
     * @return Returns String as valid {@code TIMS} time format or an empty String if the parsing failed.
     */
    private static String convertAndValidateStringAsTIMS(BSONObject bsonObject, String value) {
        String result = "";
        final String colon = ":";
        final String tims = convertToString(bsonObject, value);
        final String[] timeValues = tims.split(colon);

        final int invalidValue = -1;
        int hour = invalidValue;
        int min = invalidValue;
        int sec = invalidValue;

        if (timeValues.length > 0 && timeValues.length == 3) {
            hour = Integer.parseInt(timeValues[0]);
            if (hour >= 0 && hour < 24) {
                min = Integer.parseInt(timeValues[1]);
                if (min >= 0 && min < 60) {
                    String secondsValue = timeValues[2];
                    if (secondsValue.length() == 2) {
                        sec = Integer.parseInt(secondsValue);
                    } else if ((secondsValue.length() > 2)) {
                        sec = Integer.parseInt(secondsValue.substring(0, 1));
                    }
                    //fallback if seconds is invalid
                    if (sec < 0 || sec > 60) {
                        sec = 0;
                    }
                }
            }
        }

        if (hour != invalidValue && min != invalidValue) {
            final String zero = "0";
            String hourValue = String.valueOf(hour);
            String minValue = String.valueOf(min);
            String secValue = String.valueOf(sec);
            if (hour < 10) {
                hourValue = zero + hourValue;
            }
            if (min < 10) {
                minValue = zero + minValue;
            }
            if (sec < 10) {
                secValue = zero + secValue;
            }
            result = hourValue + colon + minValue + colon + secValue;
        }

        return result;
    }

    private static int createRandomNumber(int numberFrom, int NumberTo) {
        return ThreadLocalRandom.current().nextInt(numberFrom, NumberTo);
    }

    private static int calculateOccupiedSeats(int maxSeatsInPlane) {
        if (maxSeatsInPlane <= 1) {
            maxSeatsInPlane = 2;
        }
        return createRandomNumber(0, maxSeatsInPlane);
    }

    //preis berechnet sich aus der distanz, die business class ist hier nicht bekannt und auch egal, es handelt sich um einen standard preis
    //der preis kann nochmal in sbook extra berechnet werden, indem die klasse miteinbezogen wird.
    private static double calculateFlightPrice(double distance, UnitOfLength distanceUnit, UnitOfCurrency currency) {
        double price;

        //calculate base price depending on flight distance
        if (distance < 1000) {
            price = createRandomNumber(25, 500);
        } else if (distance < 3000) {
            price = createRandomNumber(50, 1500);
        } else if (distance < 7500) {
            price = createRandomNumber(500, 2500);
        } else {
            price = createRandomNumber(750, 3500);
        }

        //is unit of distance in imperial miles, then adjust price
        if (distanceUnit.equals(UnitOfLength.MI)) {
            price = price * 1.62;
        }

        //if price is not in euros then adjust price
        switch (currency) {
            case EUR:
                break;
            case USD:
                price = price * 0.846302926;
                break;
            case CAD:
                price = price * 0.660759472;
                break;
            case GBP:
                price = price * 1.13498532;
                break;
            case JPY:
                price = price * 0.00751516998;
                break;
            case AUD:
                price = price * 0.645898393;
                break;
            case ZAR:
                price = price * 0.0628887704;
                break;
            case SGD:
                price = price * 0.628227588;
                break;
            case CHF:
                price = price * 0.859039785;
                break;
        }
        final DecimalFormat format = new DecimalFormat("#.00");

        return Double.parseDouble(format.format(price).replace(",", "."));
    }
}