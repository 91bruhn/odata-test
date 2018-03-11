////////////////////////////////////////////////////////////////////////////////
//
// Created by BBruhns on 10.03.2018.
//
// Copyright (c) 2006 - 2018 FORCAM GmbH. All rights reserved.
////////////////////////////////////////////////////////////////////////////////

package myservice.mynamespace.database.dao;

import com.mongodb.MongoClient;
import com.mongodb.WriteResult;
import myservice.mynamespace.database.collections.Saplane;
import myservice.mynamespace.database.collections.Scarr;
import myservice.mynamespace.database.collections.Sflight;
import myservice.mynamespace.database.collections.Spfli;
import org.bson.types.ObjectId;
import org.mongodb.morphia.Datastore;
import org.mongodb.morphia.Key;
import org.mongodb.morphia.Morphia;
import org.mongodb.morphia.dao.BasicDAO;
import org.mongodb.morphia.query.Query;

import java.util.List;

import static myservice.mynamespace.service.entities.definitions.EntityNames.DB_CARRIER_ID;
import static myservice.mynamespace.service.entities.definitions.EntityNames.DB_CONNECTION_ID;
import static myservice.mynamespace.service.entities.definitions.EntityNames.DB_ID;
import static myservice.mynamespace.service.entities.definitions.EntityNames.PLANE_TYPE;
import static myservice.mynamespace.service.entities.definitions.EntityNames.SCARR;
import static myservice.mynamespace.service.entities.definitions.EntityNames.SPFLI;

/**
 *
 */
public class SaplaneDAOImpl extends BasicDAO<Saplane, ObjectId> implements SaplaneDAO {

    // ------------------------------------------------------------------------
    // constants
    // ------------------------------------------------------------------------

    // ------------------------------------------------------------------------
    // members
    // ------------------------------------------------------------------------

    Datastore mDatastore;

    // ------------------------------------------------------------------------
    // constructors
    // ------------------------------------------------------------------------

    public SaplaneDAOImpl(Class<Saplane> entityClass, MongoClient mongoClient, Morphia morphia, String dbName) {
        super(entityClass, mongoClient, morphia, dbName);
    }

    // ------------------------------------------------------------------------
    // methods
    // ------------------------------------------------------------------------

    @Override
    public Saplane getById(String id) {
        return super.get(new ObjectId(id));
    }

    @Override
    public Key<Saplane> save(Saplane saplane) {
        return super.save(saplane);
    }

    @Override
    public WriteResult delete(Saplane saplane) {
        return super.delete(saplane);
    }

    @Override
    public boolean idTaken(String id) {
        return this.getById(id) != null;
    }

    @Override
    public Saplane findPlaneByPlaneType(String planeType) {
        return createQuery().field(PLANE_TYPE).equal(planeType).get();
    }

    @Override
    public List<Saplane> getAllSaplanes() {//TODO überall saplane statt splane?
        return super.getDatastore().createQuery(Saplane.class).asList();//new FindOptions()
    }

    @Override
    public Saplane findPlaneByCarrierIdAndConnectionIdAndFlDate(String carrierId, String connectionId, String flDate) {
        //        Sflight sflight = this.findFlightByCarrierIdAndConnectionIdAndFlDate(carrierId, connectionId, flDate);
        final Query<Sflight> query = getDatastore().find(Sflight.class).field(DB_ID).equal(flDate).field(DB_CARRIER_ID).equal(new Key<>(Scarr.class,
                                                                                                                                        SCARR,
                                                                                                                                        carrierId)).field(
            DB_CONNECTION_ID).equal(new Key<>(Spfli.class, SPFLI, connectionId));
        final Sflight sflight = query.get();

        return this.getById(sflight.getPlaneType().getPlaneType());
    }

}
