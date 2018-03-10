////////////////////////////////////////////////////////////////////////////////
//
// Created by BBruhns on 10.03.2018.
//
// Copyright (c) 2006 - 2018 FORCAM GmbH. All rights reserved.
////////////////////////////////////////////////////////////////////////////////

package myservice.mynamespace.database.service.tmp;

import com.mongodb.WriteResult;
import myservice.mynamespace.database.collections.Spfli;
import myservice.mynamespace.database.dao.SpfliDAO;
import myservice.mynamespace.database.dao.SpfliDAOImpl;
import myservice.mynamespace.service.entities.definitions.EntityNames;
import org.mongodb.morphia.Key;

import java.util.List;

/**
 *
 */
public class SpfliService extends AbstractDBService {

    // ------------------------------------------------------------------------
    // constants
    // ------------------------------------------------------------------------

    // ------------------------------------------------------------------------
    // members
    // ------------------------------------------------------------------------

    SpfliDAO mSpfliDAO;

    // ------------------------------------------------------------------------
    // constructors
    // ------------------------------------------------------------------------

    public SpfliService() {
        mSpfliDAO = new SpfliDAOImpl(Spfli.class, mMongoClient, mMorphia, EntityNames.DB_NAME);
    }

    // ------------------------------------------------------------------------
    // methods
    // ------------------------------------------------------------------------

    public List<Spfli> getAllSpflis() {
        return mSpfliDAO.getAllSpflis();
    }

    public Spfli getById(String id) {
        return mSpfliDAO.getById(id);
    }

    public Key<Spfli> save(Spfli spfli) {
        return mSpfliDAO.save(spfli);
    }

    public WriteResult delete(Spfli spfli) {
        return mSpfliDAO.delete(spfli);
    }

    public boolean idTaken(String id) {
        return mSpfliDAO.idTaken(id);
    }

    public Spfli findConnectionByConnectionIdAndCarrierId(String connectionId, String carrierId) {
        return mSpfliDAO.findConnectionByConnectionIdAndCarrierId(connectionId, carrierId);
    }

    public List<Spfli> findConnectionsByCarrierId(String carrierId) {
        return mSpfliDAO.findConnectionsByCarrierId(carrierId);
    }

}
