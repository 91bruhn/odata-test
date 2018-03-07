package myservice.mynamespace.delete;

/**
 * Created by bruhn on 10.12.2017.
 */
public class Test {

//    public static void main(String[] args) {
//        MongoClient mongoClient = new MongoClient("localhost", 27017);
//        Morphia morphia = new Morphia();
//        Datastore datastore = morphia.createDatastore(mongoClient, "morphia_example");
//        morphia.mapPackage("myservice.mynamespace.data2");
//        datastore.ensureIndexes();
//
//        ////////////////////////SCARR////////////////////////
//        BSONObject jsonArray = readJson("/dummyData/dummyDataScarr.json", EnterTestData.class);//todo this.getClass()
//        List<Scarr> carriers = transformScarr(jsonArray);
//        datastore.save(carriers);
//        ////////////////////////SAPLANE////////////////////////
//        jsonArray = readJson("/dummyData/dummyDataSaplane.json", EnterTestData.class);
//        List<Saplane> planes = transformSaplane(jsonArray);
//        datastore.save(planes);
//        ////////////////////////SPFLI////////////////////////
//        jsonArray = readJson("/dummyData/dummyDataSpfli.json", EnterTestData.class);
//        List<Spfli> connections = transformSpfli(jsonArray, carriers);
//        datastore.save(connections);
//        ////////////////////////SFLIGHTS////////////////////////
//        jsonArray = readJson("/dummyData/dummyDataSflight.json", EnterTestData.class);
//        List<Sflight> flights = transformSflight(jsonArray, carriers, connections, planes);
//        datastore.save(flights);
//        ////////////////////////SBOOKS////////////////////////
//        jsonArray = readJson("/dummyData/dummyDataSbook.json", EnterTestData.class);
//        List<Sbook> bookings = transformSbooking(jsonArray, carriers, connections, flights);//mehrzahl
//        datastore.save(bookings);
//    }
//
//    private static BSONObject readJson(String path, Class clazz) {
//        InputStream inputStream = clazz.getResourceAsStream(path);
//        String json = EnterTestData.readFromInputStream(inputStream);
//        return (BSONObject) JSON.parse(json);
//    }
//
//    private static List<Scarr> transformScarr(BSONObject jsonArray) {
//        List<Scarr> carriers = new ArrayList<Scarr>(jsonArray.keySet().size());
//
//        for (String key : jsonArray.keySet()) {
//            BSONObject jsonObject = (BSONObject) jsonArray.get(key);
//
//            Scarr carrier = new Scarr();
//            carrier.setCarrId(convertToString(jsonObject, SCARR_CARRID));
//            carrier.setCarrName(convertToString(jsonObject, SCARR_CARRNAME));
//            carrier.setCurrCode(Currency.valueOf(convertToString(jsonObject, SCARR_CURRCODE)));
//            carrier.setUrl(convertToString(jsonObject, SCARR_URL));
//
//            carriers.add(carrier);
//        }
//
//        return carriers;
//    }
//
//    private static List<Saplane> transformSaplane(BSONObject jsonArray) {
//        List<Saplane> planes = new ArrayList<Saplane>(jsonArray.keySet().size());
//
//        for (String key : jsonArray.keySet()) {
//            BSONObject json = (BSONObject) jsonArray.get(key);
//
//            Saplane plane = new Saplane();
//            plane.setPlaneType(convertToString(json, SAPLANE_PLANETYPE));
//            plane.setSeatsMax(convertToInteger(json, SAPLANE_SEATSMAX));
//            plane.setConsum(convertStringToDouble(json, SAPLANE_CONSUM));
//            plane.setConUnit(UnitOfMass.valueOf(convertToString(json, SAPLANE_CON_UNIT)));
//            plane.setTankCap(convertStringToDouble(json, SAPLANE_TANKCAP));
//            plane.setCapUnit(UnitOfMass.valueOf(convertToString(json, SAPLANE_CAP_UNIT)));
//            plane.setWeight(convertStringToDouble(json, SAPLANE_WEIGHT));
//            plane.setWeiUnit(UnitOfMass.valueOf(convertToString(json, SAPLANE_WEI_UNIT)));
//            plane.setSpan(convertStringToDouble(json, SAPLANE_SPAN));
//            plane.setSpanUnit(UnitOfLength.valueOf(convertToString(json, SAPLANE_SPAN_UNIT)));
//            plane.setLength(convertStringToDouble(json, SAPLANE_LENG));
//            plane.setLengUnit(UnitOfLength.valueOf(convertToString(json, SAPLANE_LENG_UNIT)));
//            plane.setOpSpeed(convertStringToDouble(json, SAPLANE_OP_SPEED));
//            plane.setSpeedUnit(UnitOfSpeed.valueOf(convertToString(json, SAPLANE_SPEED_UNIT)));
//            plane.setProducer(convertToString(json, SAPLANE_PRODUCER));
//            plane.setSeatsMaxB(convertToInteger(json, SAPLANE_SEATSMAX_B));
//            plane.setSeatsMaxF(convertToInteger(json, SAPLANE_SEATSMAX_F));
//
//            planes.add(plane);
//        }
//
//        return planes;
//    }
//
//    private static List<Spfli> transformSpfli(BSONObject jsonArray, List<Scarr> carriers) {
//        List<Spfli> connections = new ArrayList<Spfli>(jsonArray.keySet().size());
//
//        for (String key : jsonArray.keySet()) {
//            BSONObject json = (BSONObject) jsonArray.get(key);
//            String carrId = convertToString(json, SPFLI_CARRID);
//            Scarr carrierFK = findForeignKeyScarr(carrId, carriers);
//
//            if (carrierFK != null) {
//                Spfli connection = new Spfli();
//                connection.setConnId(String.valueOf(convertToInteger(json, SPFLI_CONNID)));
//                connection.setCarrId(carrierFK);
//                connection.setAirpFrom(convertToString(json, SPFLI_AIRPFROM));
//                connection.setAirpTo(convertToString(json, SPFLI_AIRPTO));
//                connection.setCityFrom(convertToString(json, SPFLI_CITYFROM));
//                connection.setCityTo(convertToString(json, SPFLI_CITYTO));
//                connection.setCountryFrom(convertToString(json, COUNTRY_FROM));
//                connection.setCountryTo(convertToString(json, COUNTRY_TO));
//                connection.setFlTime(convertToInteger(json, SPFLI_FLTIME));
//                connection.setDepTime(convertAndValidateStringAsTIMS(json, SPFLI_DEPTIME));
//                connection.setArrTime(convertAndValidateStringAsTIMS(json, SPFLI_ARRTIME));
//                connection.setDistance(convertToDouble(json, SPFLI_DISTANCE));
//                connection.setDistId(UnitOfLength.valueOf(convertToString(json, SPFLI_DISTID)));
//                connection.setFlType(convertToString(json, SPFLI_FLTYPE));
//                connection.setPeriod(convertToInteger(json, SPFLI_PERIOD));
//
//                connections.add(connection);
//            }
//        }
//
//        return connections;
//    }
//
//    private static Scarr findForeignKeyScarr(String carrId, List<Scarr> carriers) {
//        if (CollectionUtils.isNotEmpty(carriers)) {
//            for (Scarr carrier : carriers) {
//                if (carrId.equals(carrier.getCarrId())) {
//                    return carrier;
//                }
//            }
//        }
//        return null;
//    }
//
//    private static Spfli findForeignKeySpfli(String connId, List<Spfli> connections) {
//        if (CollectionUtils.isNotEmpty(connections)) {
//            for (Spfli connection : connections) {
//                if (connId.equals(connection.getConnId())) {
//                    return connection;
//                }
//            }
//        }
//        return null;
//    }
//
//    private static Saplane findForeignKeySaplane(String planeType, List<Saplane> planes) {
//        if (CollectionUtils.isNotEmpty(planes)) {
//            for (Saplane plane : planes) {
//                if (planeType.equals(plane.getPlaneType())) {
//                    return plane;
//                }
//            }
//        }
//        return null;
//    }
//
//    private static List<Sflight> transformSflight(BSONObject jsonArray, List<Scarr> carriers, List<Spfli> connections, List<Saplane> planes) {
//        List<Sflight> flights = new ArrayList<Sflight>(jsonArray.keySet().size());
//
//        for (String key : jsonArray.keySet()) {
//            BSONObject json = (BSONObject) jsonArray.get(key);
//
//            String carrId = convertToString(json, SFLIGHT_CARRID);
//            Scarr carrierFK = findForeignKeyScarr(carrId, carriers);
//
//            String connId = String.valueOf(convertToInteger(json, SFLIGHT_CONNID));
//            Spfli connectionFK = findForeignKeySpfli(connId, connections);
//
//            String planeType = convertToString(json, SFLIGHT_PLANETYPE);
//            Saplane planeFK = findForeignKeySaplane(planeType, planes);
//
//            if (carrierFK != null && connectionFK != null && planeFK != null) {
//                final int maxSeats = planeFK.getSeatsMax();
//                final int maxSeatsB = planeFK.getSeatsMaxB();
//                final int maxSeatsF = planeFK.getSeatsMaxF();
//
//                final Sflight flight = new Sflight();
//                flight.setFlDate(convertStringAsDateToDate(json, SFLIGHT_FLDATE));
//                flight.setCarrId(carrierFK);
//                flight.setConnId(connectionFK);
//                flight.setPlaneType(planeFK);
//
//                flight.setCurrency(carrierFK.getCurrCode());
//                flight.setPrice(calculateFlightPrice(connectionFK.getDistance(), connectionFK.getDistId(), flight.getCurrency()));
//
//                flight.setSeatsMax(maxSeats);
//                flight.setSeatsMaxB(maxSeatsB);
//                flight.setSeatsMaxF(maxSeatsF);
//                flight.setSeatsOcc(calculateOccupiedSeats(maxSeats));
//                flight.setSeatsOccB(calculateOccupiedSeats(maxSeatsB));
//                flight.setSeatsOccF(calculateOccupiedSeats(maxSeatsF));
//
//                flights.add(flight);
//            }
//        }
//
//        return flights;
//    }
//
//    private static List<Sbook> transformSbooking(BSONObject jsonArray, List<Scarr> carriers, List<Spfli> connections, List<Sflight> flights) {
//        List<Sbook> bookings = new ArrayList<Sbook>(jsonArray.keySet().size());
//        int counter = 0;
//
//        for (String key : jsonArray.keySet()) {
//            BSONObject json = (BSONObject) jsonArray.get(key);
//
//            String carrId = convertToString(json, SBOOK_CARRID);
//            Scarr carrierFK = findForeignKeyScarr(carrId, carriers);
//
//            String connId = String.valueOf(convertToInteger(json, SBOOK_CONNID));
//            Spfli connectionFK = findForeignKeySpfli(connId, connections);
//
//            //randomly connect a flight to a booking
//            Sflight flightFK = flights.get(counter++);
//            if (counter == flights.size()) {
//                counter = 0;
//            }
//
//            if (carrierFK != null && connectionFK != null) {
//                final Sbook booking = new Sbook();
//                //TODO maybe add value price use standard price of booking and multiply by class
//                booking.setBookId(convertIntegerToString(json, SBOOK_BOOKID));
//                booking.setCarrId(carrierFK);
//                booking.setConnId(connectionFK);
//                booking.setFlDate(flightFK);
//                booking.setCustomId(convertIntegerToString(json, SBOOK_CUSTOMID));
//                booking.setCustType(convertToCharacter(json, SBOOK_CUSTTYPE));
//                booking.setSmoker(convertToCharacter(json, SBOOK_SMOKER));
//                booking.setLuggWeight(convertToInteger(json, SBOOK_LUGGWEIGHT));
//                booking.setwUnit(UnitOfMass.valueOf(convertToString(json, SBOOK_WUNIT)));
//                booking.setInvoice(convertToCharacter(json, SBOOK_INVOICE));
//                booking.setFlightClass(convertToCharacter(json, SBOOK_CLASS));
//                booking.setOrderDate(convertStringAsDateToDate(json, SBOOK_ORDER_DATE));
//                booking.setCancelled(convertToCharacter(json, SBOOK_CANCELLED));
//                booking.setReserved(convertToCharacter(json, SBOOK_RESERVED));
//
//                bookings.add(booking);
//            }
//        }
//
//        return bookings;
//    }
//
//    //todo consider moving to utils and using in concrete entity class
//    private static String convertToString(BSONObject bsonObject, String value) {
//        return (String) bsonObject.get(value);
//    }
//
//    private static Integer convertToInteger(BSONObject bsonObject, String value) {
//        return (Integer) bsonObject.get(value);
//    }
//
//    private static Double convertToDouble(BSONObject bsonObject, String value) {
//        return (Double) bsonObject.get(value);
//    }
//
//    private static Character convertToCharacter(BSONObject bsonObject, String value) {
//        return ((String) bsonObject.get(value)).charAt(0);
//    }
//
//    private static String convertIntegerToString(BSONObject bsonObject, String value) {
//        return String.valueOf(convertToInteger(bsonObject, value));
//    }
//
//    private static Double convertStringToDouble(BSONObject bsonObject, String value) {
//        return Double.parseDouble((convertToString(bsonObject, value)).replace(".", ""));
//    }
//
//    private static Date convertStringAsDateToDate(BSONObject bsonObject, String value) {//2 methhoden
//        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");
//        LocalDate date = LocalDate.parse(convertToString(bsonObject, value), formatter);
//        LocalDateConverter ldc = new LocalDateConverter();
//        return (Date) ldc.encode(date);
//    }
//
//    private static Date convertToDateTEMP2(BSONObject bsonObject, String value) {//todo delete
//        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm");
//        LocalDate date = LocalDate.parse(convertToString(bsonObject, value), formatter);
//        LocalTime time = LocalTime.of(createRandomNumber(0, 24), createRandomNumber(0, 60), 0);
//        LocalDateTime ldt = LocalDateTime.parse(convertToString(bsonObject, value), formatter);
//        LocalDateTime ldt2 = LocalDateTime.of(date, time);
//        LocalDateTimeConverter ldtc = new LocalDateTimeConverter();
//        LocalDateConverter ldc = new LocalDateConverter();
//        return (Date) ldtc.encode(ldt);
//
//    }
//
//    /**
//     * Validates a String that is supposed to be in SAP's {@code TIMS}-format.
//     * <p/>
//     * Note: If the value for seconds is invalid, the actual time is still considered valid.
//     * Therefore, seconds may be a 0 in that case.
//     *
//     * @return Returns String as valid {@code TIMS} time format or an empty String if the parsing failed.
//     */
//    private static String convertAndValidateStringAsTIMS(BSONObject bsonObject, String value) {
//        String result = "";
//        String colon = ":";
//        String tims = convertToString(bsonObject, value);
//        String[] timeValues = tims.split(colon);
//
//        int invalidValue = -1;
//        int hour = invalidValue;
//        int min = invalidValue;
//        int sec = invalidValue;
//
//        if (timeValues.length > 0 && timeValues.length == 3) {
//            hour = Integer.parseInt(timeValues[0]);
//            if (hour >= 0 && hour < 24) {
//                min = Integer.parseInt(timeValues[1]);
//                if (min >= 0 && min < 60) {
//                    String secondsValue = timeValues[2];
//                    if (secondsValue.length() == 2) {
//                        sec = Integer.parseInt(secondsValue);
//                    } else if ((secondsValue.length() > 2)) {
//                        sec = Integer.parseInt(secondsValue.substring(0, 1));
//                    }
//                    //fallback if seconds is invalid
//                    if (sec < 0 || sec > 60) {
//                        sec = 0;
//                    }
//                }
//            }
//        }
//
//        if (hour != invalidValue && min != invalidValue) {
//            String zero = "0";
//            String hourValue = String.valueOf(hour);
//            String minValue = String.valueOf(min);
//            String secValue = String.valueOf(sec);
//            if (hour < 10) {
//                hourValue = zero + hourValue;
//            }
//            if (min < 10) {
//                minValue = zero + minValue;
//            }
//            if (sec < 10) {
//                secValue = zero + secValue;
//            }
//            result = hourValue + colon + minValue + colon + secValue;
//        }
//
//        return result;
//    }
//
//    private static int createRandomNumber(int numberFrom, int NumberTo) {
//        return ThreadLocalRandom.current().nextInt(numberFrom, NumberTo);
//    }
//
//    private static int calculateOccupiedSeats(int maxSeatsInPlane) {
//        if (maxSeatsInPlane <= 1) {
//            maxSeatsInPlane = 2;
//        }
//        return createRandomNumber(0, maxSeatsInPlane);
//    }
//
//    //preis berechnet sich aus der distanz, die business class ist hier nicht bekannt und auch egal, es handelt sich um einen standard preis
//    //der preis kann nochmal in sbook extra berechnet werden, indem die klasse miteinbezogen wird.
//    private static double calculateFlightPrice(double distance, UnitOfLength distanceUnit, Currency currency) {
//        double price;
//
//        //calculate base price depending on flight distance
//        if (distance < 1000) {
//            price = createRandomNumber(25, 500);
//        } else if (distance < 3000) {
//            price = createRandomNumber(50, 1500);
//        } else if (distance < 7500) {
//            price = createRandomNumber(500, 2500);
//        } else {
//            price = createRandomNumber(750, 3500);
//        }
//
//        //is unit of distance in imperial miles, then adjust price
//        if (distanceUnit.equals(UnitOfLength.MI)) {
//            price = price * 1.62;
//        }
//
//        //if price is not in euros then adjust price
//        switch (currency) {
//            case EUR:
//                break;
//            case USD:
//                price = price * 0.846302926;
//                break;
//            case CAD:
//                price = price * 0.660759472;
//                break;
//            case GBP:
//                price = price * 1.13498532;
//                break;
//            case JPY:
//                price = price * 0.00751516998;
//                break;
//            case AUD:
//                price = price * 0.645898393;
//                break;
//            case ZAR:
//                price = price * 0.0628887704;
//                break;
//            case SGD:
//                price = price * 0.628227588;
//                break;
//            case CHF:
//                price = price * 0.859039785;
//                break;
//        }
//
//        DecimalFormat format = new DecimalFormat("#.00");
//
//        return Double.parseDouble(format.format(price).replace(",", "."));
//    }
}

//    MongoDatabase database;
//        MongoCollection<Document> table = db.getCollection("user");
//        Document document = new Document();
//        document.put("name", "mkyong");
//        document.put("age", 30);
//        document.put("createdDate", new Date());

//For this simple example, using asList() is fine but in practice fetch() is usually the more appropriate choice.
//        final Query<Scarr> query = datastore.createQuery(Scarr.class);
//        final List<Scarr> scarrs = query.asList();
//        for (Scarr sc : scarrs){
//            System.out.println(sc.toString());
//        }
//
//        final Query<Sbook> query1 = datastore.createQuery(Sbook.class);
//        final List<Sbook> sbooks = query1.asList();
//        for (Sbook sc : sbooks){
//            System.out.println(sc.toString());
//        }

//        underpaid = datastore.createQuery(Employee.class)
//                .field("salary").lessThanOrEq(30000)
//                .asList();
//
//        List<Employee> underpaid = datastore.createQuery(Employee.class)
//                .filter("salary <=", 30000)
//                .asList();
