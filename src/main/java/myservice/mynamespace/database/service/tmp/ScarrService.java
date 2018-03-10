////////////////////////////////////////////////////////////////////////////////
//
// Created by BBruhns on 10.03.2018.
//
// Copyright (c) 2006 - 2018 FORCAM GmbH. All rights reserved.
////////////////////////////////////////////////////////////////////////////////

package myservice.mynamespace.database.service.tmp;

import com.mongodb.WriteResult;
import myservice.mynamespace.database.collections.Scarr;
import myservice.mynamespace.database.dao.ScarrDAO;
import myservice.mynamespace.database.dao.ScarrDAOImpl;
import myservice.mynamespace.service.entities.definitions.EntityNames;
import org.mongodb.morphia.Key;

import java.util.List;

/**
 *
 */
public class ScarrService extends AbstractDBService {

    // ------------------------------------------------------------------------
    // constants
    // ------------------------------------------------------------------------

    // ------------------------------------------------------------------------
    // members
    // ------------------------------------------------------------------------

    ScarrDAO mScarrDAO;

    // ------------------------------------------------------------------------
    // constructors
    // ------------------------------------------------------------------------

    public ScarrService() {
        mScarrDAO = new ScarrDAOImpl(Scarr.class, mMongoClient, mMorphia, EntityNames.DB_NAME);
    }

    // ------------------------------------------------------------------------
    // methods
    // ------------------------------------------------------------------------

    public List<Scarr> getAllScarrs() {
        return mScarrDAO.getAllScarrs();
    }

    public Scarr getById(String id) {
        return mScarrDAO.getById(id);
    }

    public Key<Scarr> save(Scarr scarr) {
        return mScarrDAO.save(scarr);
    }

    public WriteResult delete(Scarr scarr) {
        return mScarrDAO.delete(scarr);
    }

    public boolean idTaken(String id) {
        return mScarrDAO.idTaken(id);
    }

}
