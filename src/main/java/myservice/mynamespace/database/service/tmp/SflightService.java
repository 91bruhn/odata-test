////////////////////////////////////////////////////////////////////////////////
//
// Created by BBruhns on 10.03.2018.
//
// Copyright (c) 2006 - 2018 FORCAM GmbH. All rights reserved.
////////////////////////////////////////////////////////////////////////////////

package myservice.mynamespace.database.service.tmp;

import com.mongodb.WriteResult;
import myservice.mynamespace.database.collections.Sflight;
import myservice.mynamespace.database.dao.SflightDAO;
import myservice.mynamespace.database.dao.SflightDAOImpl;
import myservice.mynamespace.service.entities.definitions.EntityNames;
import org.mongodb.morphia.Key;

import java.util.List;

/**
 *
 */
public class SflightService extends AbstractDBService {

    // ------------------------------------------------------------------------
    // constants
    // ------------------------------------------------------------------------

    // ------------------------------------------------------------------------
    // members
    // ------------------------------------------------------------------------

    SflightDAO mSflightDAO;

    // ------------------------------------------------------------------------
    // constructors
    // ------------------------------------------------------------------------

    public SflightService() {
        mSflightDAO = new SflightDAOImpl(Sflight.class, mMongoClient, mMorphia, EntityNames.DB_NAME);
    }

    // ------------------------------------------------------------------------
    // methods
    // ------------------------------------------------------------------------

    public List<Sflight> getAllSflights() {
        return mSflightDAO.getAllSflights();
    }

    public Sflight getById(String id) {
        return mSflightDAO.getById(id);
    }

    public Key<Sflight> save(Sflight sflight) {
        return mSflightDAO.save(sflight);
    }

    public WriteResult delete(Sflight sflight) {
        return mSflightDAO.delete(sflight);
    }

    public boolean idTaken(String id) {
        return mSflightDAO.idTaken(id);
    }

    public Sflight findFlightByCarrierIdAndConnectionIdAndFlDate(String carrierId, String connectionId, String flDate) {
        return mSflightDAO.findFlightByCarrierIdAndConnectionIdAndFlDate(carrierId, connectionId, flDate);
    }

    public List<Sflight> findFlightsByCarrierId(String carrierCode) {
        return mSflightDAO.findFlightsByCarrierId(carrierCode);
    }

    public List<Sflight> findFlightsByCarrierIdAndConnectionId(String carrierId, String connectionId) {
        return mSflightDAO.findFlightsByCarrierIdAndConnectionId(carrierId, connectionId);
    }

}
