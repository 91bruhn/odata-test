package myservice.mynamespace.database.service;

import com.mongodb.MongoClient;
import myservice.mynamespace.database.collections.Saplane;
import myservice.mynamespace.database.collections.Sbook;
import myservice.mynamespace.database.collections.Scarr;
import myservice.mynamespace.database.collections.Sflight;
import myservice.mynamespace.database.collections.Spfli;
import org.mongodb.morphia.Datastore;
import org.mongodb.morphia.Key;
import org.mongodb.morphia.Morphia;
import org.mongodb.morphia.query.FindOptions;
import org.mongodb.morphia.query.Query;

import java.util.List;

/**
 *
 */
public class DatabaseHandler {

    // ------------------------------------------------------------------------
    // constants
    // ------------------------------------------------------------------------
    MongoClient mongoClient;
    Morphia morphia;
    Datastore datastore;

    // ------------------------------------------------------------------------
    // members
    // ------------------------------------------------------------------------

    // ------------------------------------------------------------------------
    // constructors
    // ------------------------------------------------------------------------

    public DatabaseHandler() {
        mongoClient = new MongoClient("localhost", 27017);
        morphia = new Morphia();
        datastore = morphia.createDatastore(mongoClient, "Flugdatenverwaltung");
        morphia.mapPackage("myservice.mynamespace.database.data");
        datastore.ensureIndexes();
    }

    // ------------------------------------------------------------------------
    // methods
    // ------------------------------------------------------------------------

    //----LOADING DATA----
    public List<Sflight> getAllSflights() {
        return datastore.createQuery(Sflight.class).asList(new FindOptions());//.asList(new FindOptions().limit(100));
    }

    public List<Spfli> getAllSpflis() {
        return datastore.createQuery(Spfli.class).asList(new FindOptions());
    }

    public List<Scarr> getAllScarrs() {
        return datastore.createQuery(Scarr.class).asList(new FindOptions());
        //        datastore.getByKey(Scarr.class, "")
        //        Scarr s = datastore.get(Scarr.class, "AC");
        //        return datastore.createQuery(Scarr.class).field("_id").equal("AC").asList();
    }

    public List<Sbook> getAllSbooks() {//TODO evtl. doch das limit raus machen, dafür gibt es den $top befehl
        return datastore.createQuery(Sbook.class).asList(new FindOptions());
    }

    public List<Saplane> getAllSaplanes() {//TODO überall saplane statt splane?
        return datastore.createQuery(Saplane.class).asList(new FindOptions());
    }

    // ----SAVE DATA----

    public void saveData(Object object) {
        datastore.save(object);
        //TODO speichere komplexe objekte, also die verschachtelten Sachen.
    }

    // ----DELETE DATA----

    public void deleteData(Object object) {
        datastore.delete(object);
        //        datastore.delet; oder direkt mit id
    }

    // ----FIND DATA----

    public Object getById(Class clazz, String id) {
        return datastore.get(clazz, id);
    }

    public boolean idTaken(Class clazz, String id) {
        return this.getById(clazz, id) != null;
    }

    public boolean idTaken(Class clazz, int id) {
        return datastore.get(clazz, id) != null;
    }

    public List<Sflight> findFlightsForCarrier(String carrierCode) {
        //        List<Sflight> s = datastore.createQuery(Sflight.class).field(CARRIER_ID).equal(carrierCode).asList();
        //        List<Sflight> ss = this.getAllSflights();

        //        Query<Sflight> query = datastore.createQuery(Sflight.class);
        //        query.and(query.criteria("carrId.carrId").equal(carrierCode));

        Query<Sflight> query = datastore.find(Sflight.class).field("carrId").equal(new Key<>(Scarr.class, "SCARR", carrierCode));
        //        query.and(query.criteria("carrId.carrId").equal(carrierCode));

        //        List<Sflight> s = datastore.createQuery(Sflight.class).filter(CARRIER_ID + "=", carrierCode).asList();

        return query.asList();
    }

    public List<Sbook> findBookingsForFlight(String carrierCode, String connId, String fldate) {
        //TODO check querying von meiner thesis
        //Documents.find( { "owner": "123456", "category": "recipes", "date.year": 2015 } );
        //        Documents.find({
        //                           "owner": "123456",
        //            "category": { $in: [ "photos", "announcements" ] },
        //        "date.year": { $lt: 2015 },
        //        "tags": { $in: [ 'baby', 'wedding' ] }
        //        });
        //query.field("price").greaterThanOrEq(1000);

        //        q.and(
        //            q.criteria("width").equal(10),
        //            q.criteria("height").equal(1)
        //        );
        //        Query<Sbook> query = datastore.find(Sbook.class).

        //        Query<Sbook> query = datastore.find(Sbook.class).field("carrId").equal(new Key<>(Scarr.class, "SCARR", carrierCode)).field("connId").equal(new Key<>(
        //            Spfli.class,
        //            "SPFLI",
        //            connId)).field("flDate").equal(new Key<>(Sflight.class, "SFLIGHT", fldate));
        //        List<Sbook> list = query.asList();
        long start = System.currentTimeMillis();

        List<Sbook> list = datastore.find(Sbook.class)
                                    .field("carrId")
                                    .equal(new Key<>(Scarr.class, "SCARR", carrierCode))
                                    .field("connId")
                                    .equal(new Key<>(Spfli.class, "SPFLI", connId))
                                    .field("flDate")
                                    .equal(new Key<>(Sflight.class, "SFLIGHT", fldate))
                                    .asList();
        long stop = System.currentTimeMillis();
        System.out.println(stop - start);
        start = System.currentTimeMillis();
        list = datastore.createQuery(Sbook.class)
                        .filter("carrId = ", new Key<>(Scarr.class, "SCARR", carrierCode))
                        .filter("connId = ", new Key<>(Spfli.class,
                                                       "SPFLI",
                                                       connId))
                        .filter("flDate = ", new Key<>(Sflight.class, "SFLIGHT", fldate))
                        .asList();
        stop = System.currentTimeMillis();
        System.out.println(stop - start);
        return list;
    }

    //    private static Date convertStringAsDateToDate(BSONObject bsonObject, String value) {//2 methhoden
    //        final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");
    ////        LocalDate.parse()
    ////        final LocalDate date = LocalDate.parse(convertToString(bsonObject, value), formatter);
    ////        final LocalDateConverter ldc = new LocalDateConverter();
    ////        return (Date) ldc.encode(date);
    //    }

}
