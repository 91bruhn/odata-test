////////////////////////////////////////////////////////////////////////////////
//
// Created by BBruhns on 10.03.2018.
//
// Copyright (c) 2006 - 2018 FORCAM GmbH. All rights reserved.
////////////////////////////////////////////////////////////////////////////////

package myservice.mynamespace.database.dao;

import com.mongodb.MongoClient;
import com.mongodb.WriteResult;
import myservice.mynamespace.database.collections.Scarr;
import org.bson.types.ObjectId;
import org.mongodb.morphia.Key;
import org.mongodb.morphia.Morphia;
import org.mongodb.morphia.dao.BasicDAO;
import org.mongodb.morphia.query.FindOptions;

import java.util.List;

import static myservice.mynamespace.service.entities.definitions.EntityNames.DB_CARRIER_ID;
import static myservice.mynamespace.service.entities.definitions.EntityNames.DB_FLIGHT_DATE;
import static myservice.mynamespace.service.entities.definitions.EntityNames.DB_ID;

/**
 *
 */
public class ScarrDAOImpl extends BasicDAO<Scarr, ObjectId> implements ScarrDAO {

    // ------------------------------------------------------------------------
    // constants
    // ------------------------------------------------------------------------

    // ------------------------------------------------------------------------
    // members
    // ------------------------------------------------------------------------

    // ------------------------------------------------------------------------
    // constructors
    // ------------------------------------------------------------------------

    public ScarrDAOImpl(Class<Scarr> entityClass, MongoClient mongoClient, Morphia morphia, String dbName) {
        super(entityClass, mongoClient, morphia, dbName);
    }

    // ------------------------------------------------------------------------
    // methods
    // ------------------------------------------------------------------------

    @Override
    public Scarr getById(String id) {
        return super.findOne(DB_ID + " = ", id);
    }

    @Override
    public Key<Scarr> save(Scarr scarr) {
        return super.save(scarr);
    }

    @Override
    public WriteResult delete(Scarr scarr) {
        return super.delete(scarr);
    }

    @Override
    public boolean idTaken(String id) {
        return this.getById(id) != null;
    }

    @Override
    public List<Scarr> getAllScarrs() {
        return getDatastore().createQuery(Scarr.class).asList(new FindOptions());
        //        mDatastore.getByKey(Scarr.class, "")
        //        Scarr s = mDatastore.get(Scarr.class, "AC");
        //        return mDatastore.createQuery(Scarr.class).field("_id").equal("AC").asList();
    }

}
