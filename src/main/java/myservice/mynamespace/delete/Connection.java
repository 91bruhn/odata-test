package myservice.mynamespace.delete;

import com.mongodb.MongoClient;
import org.mongodb.morphia.Datastore;
import org.mongodb.morphia.Morphia;

/**
 * Created by bruhn on 10.12.2017.
 */
public class Connection {

    MongoClient mongoClient;
//    MongoDatabase database;
    Morphia morphia;
    Datastore datastore;

    public Connection() {
        morphia = new Morphia();
        // tell Morphia where to find your classes
        morphia.mapPackage(getMorphiaDataPackageName());
        mongoClient = new MongoClient("localhost", 27017);
        // create the Datastore connecting to the default port on the local host
        datastore = morphia.createDatastore(mongoClient, "morphia_example");
        datastore.ensureIndexes();

//        database = mongoClient.getDatabase("testFlightStuff");


//        MongoCollection<Document> table = db.getCollection("user");
//        Document document = new Document();
//        document.put("name", "mkyong");
//        document.put("age", 30);
//        document.put("createdDate", new Date());


        datastore.ensureIndexes();


    }

    public String getMorphiaDataPackageName() {
        return "myservice.mynamespace.database.data";
    }

    //todo transform json.txt ... to start with dummy data
}
