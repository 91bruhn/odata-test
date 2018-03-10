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








    // ----SAVE DATA----

    // ----DELETE DATA----






    //    public Saplane findPlaneForFlight(String planeType) {
    //        final Query<Saplane> query = mDatastore.find(Saplane.class).field("_id").equal(planeType);
    //
    //        return query.get();
    //    }


}
