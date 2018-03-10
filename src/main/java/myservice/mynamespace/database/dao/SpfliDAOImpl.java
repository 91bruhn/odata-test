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
import myservice.mynamespace.database.collections.Spfli;
import org.bson.types.ObjectId;
import org.mongodb.morphia.Key;
import org.mongodb.morphia.Morphia;
import org.mongodb.morphia.dao.BasicDAO;
import org.mongodb.morphia.query.FindOptions;
import org.mongodb.morphia.query.Query;

import java.util.List;

import static myservice.mynamespace.service.entities.definitions.EntityNames.DB_CARRIER_ID;
import static myservice.mynamespace.service.entities.definitions.EntityNames.DB_ID;
import static myservice.mynamespace.service.entities.definitions.EntityNames.SCARR;

/**
 *
 */
public class SpfliDAOImpl extends BasicDAO<Spfli, ObjectId> implements SpfliDAO {

    // ------------------------------------------------------------------------
    // constants
    // ------------------------------------------------------------------------

    // ------------------------------------------------------------------------
    // members
    // ------------------------------------------------------------------------

    // ------------------------------------------------------------------------
    // constructors
    // ------------------------------------------------------------------------

    public SpfliDAOImpl(Class<Spfli> entityClass, MongoClient mongoClient, Morphia morphia, String dbName) {
        super(entityClass, mongoClient, morphia, dbName);
    }

    // ------------------------------------------------------------------------
    // methods
    // ------------------------------------------------------------------------

    @Override
    public Spfli getById(String id) {
        return super.get(new ObjectId(id));
    }

    @Override
    public Key<Spfli> save(Spfli spfli) {
        return super.save(spfli);
    }

    @Override
    public WriteResult delete(Spfli spfli) {
        return super.delete(spfli);
    }

    @Override
    public boolean idTaken(String id) {
        return this.getById(id) != null;
    }

    @Override
    public List<Spfli> getAllSpflis() {
        return getDatastore().createQuery(Spfli.class).asList(new FindOptions());
    }

    @Override
    public Spfli findConnectionByConnectionIdAndCarrierId(String connectionId, String carrierId) {
        final Query<Spfli> query = getDatastore().find(Spfli.class).field(DB_ID).equal(connectionId).field(DB_CARRIER_ID).equal(new Key<>(Scarr.class,
                                                                                                                                          SCARR,
                                                                                                                                          carrierId));
        return query.get();
    }

    @Override
    public List<Spfli> findConnectionsByCarrierId(String carrierId) {
        final Query<Spfli> query = getDatastore().find(Spfli.class).field(DB_CARRIER_ID).equal(new Key<>(Scarr.class, SCARR, carrierId));

        return query.asList();
    }

}
