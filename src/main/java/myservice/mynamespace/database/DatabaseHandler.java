package myservice.mynamespace.database;

import com.mongodb.MongoClient;
import myservice.mynamespace.database.data.Saplane;
import myservice.mynamespace.database.data.Sbook;
import myservice.mynamespace.database.data.Scarr;
import myservice.mynamespace.database.data.Sflight;
import myservice.mynamespace.database.data.Spfli;
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

    public Object getById(Class clazz, String id){
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

}
