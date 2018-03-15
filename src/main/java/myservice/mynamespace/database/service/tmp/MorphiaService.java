////////////////////////////////////////////////////////////////////////////////
//
// Created by bruhn on 15.03.2018.
//
// Copyright (c) 2006 - 2018 FORCAM GmbH. All rights reserved.
////////////////////////////////////////////////////////////////////////////////

package myservice.mynamespace.database.service.tmp;

import com.mongodb.MongoClient;
import org.mongodb.morphia.Morphia;

/**
 *
 */
public class MorphiaService {//TODO woanders hin?

    // ------------------------------------------------------------------------
    // constants
    // ------------------------------------------------------------------------

    protected MongoClient mMongoClient;
    protected Morphia mMorphia;
    //    protected Datastore mDatastore;

    // ------------------------------------------------------------------------
    // members
    // ------------------------------------------------------------------------

    // ------------------------------------------------------------------------
    // constructors
    // ------------------------------------------------------------------------

    public MorphiaService() {
        mMongoClient = new MongoClient("localhost", 27017);
        mMorphia = new Morphia();
        //        mDatastore = mMorphia.createDatastore(mMongoClient, "Flugdatenverwaltung");
        mMorphia.mapPackage("myservice.mynamespace.database.data");//TODO namen ueberarbeiten
        //        mDatastore.ensureIndexes();
    }

    // ------------------------------------------------------------------------
    // methods
    // ------------------------------------------------------------------------

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
