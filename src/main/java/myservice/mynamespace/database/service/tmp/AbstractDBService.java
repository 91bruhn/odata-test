////////////////////////////////////////////////////////////////////////////////
//
// Created by BBruhns on 10.03.2018.
//
// Copyright (c) 2006 - 2018 FORCAM GmbH. All rights reserved.
////////////////////////////////////////////////////////////////////////////////

package myservice.mynamespace.database.service.tmp;

import com.mongodb.MongoClient;
import org.mongodb.morphia.Datastore;
import org.mongodb.morphia.Morphia;

/**
 *
 */
public class AbstractDBService {//TODO anders lösen, ich benutze für alle das gleiche...

    // ------------------------------------------------------------------------
    // members
    // ------------------------------------------------------------------------

    protected MongoClient mMongoClient;
    protected Morphia mMorphia;
    protected Datastore mDatastore;

    // ------------------------------------------------------------------------
    // constructors
    // ------------------------------------------------------------------------

    public AbstractDBService() {
        mMongoClient = new MongoClient("localhost", 27017);
        mMorphia = new Morphia();
        mDatastore = mMorphia.createDatastore(mMongoClient, "Flugdatenverwaltung");
        mMorphia.mapPackage("myservice.mynamespace.database.data");
        mDatastore.ensureIndexes();
    }
}
