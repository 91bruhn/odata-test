////////////////////////////////////////////////////////////////////////////////
//
// Created by bruhn on 15.03.2018.
//
// Copyright (c) 2006 - 2018 FORCAM GmbH. All rights reserved.
////////////////////////////////////////////////////////////////////////////////

package myservice.mynamespace.database.connection;

import com.mongodb.MongoClient;
import org.mongodb.morphia.Morphia;

/**
 *
 */
public class MorphiaService {//TODO woanders hin?

    // ------------------------------------------------------------------------
    // members
    // ------------------------------------------------------------------------

    protected MongoClient mMongoClient;
    protected Morphia mMorphia;

    // ------------------------------------------------------------------------
    // constructors
    // ------------------------------------------------------------------------

    public MorphiaService() {
        mMongoClient = new MongoClient("localhost", 27017);
        mMorphia = new Morphia();
        //        mDatastore = mMorphia.createDatastore(mMongoClient, "Flugdatenverwaltung");
        //        mMorphia.mapPackage("myservice.mynamespace.database.data");//TODO namen ueberarbeiten
        mMorphia.mapPackage("myservice.mynamespace.database.collections");
        //        mDatastore.ensureIndexes();
    }

    // ------------------------------------------------------------------------
    // getters/setters
    // ------------------------------------------------------------------------

    public MongoClient getMongoClient() {
        return mMongoClient;
    }

    public Morphia getMorphia() {
        return mMorphia;
    }
}
