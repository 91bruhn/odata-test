////////////////////////////////////////////////////////////////////////////////
//
// Created by BBruhns on 10.03.2018.
//
// Copyright (c) 2006 - 2018 FORCAM GmbH. All rights reserved.
////////////////////////////////////////////////////////////////////////////////

package myservice.mynamespace.database.service.tmp;

import com.mongodb.WriteResult;
import myservice.mynamespace.database.connection.MorphiaService;
import myservice.mynamespace.database.collections.Sbook;
import myservice.mynamespace.database.dao.SbookDAO;
import myservice.mynamespace.database.dao.SbookDAOImpl;
import myservice.mynamespace.service.entities.definitions.EntityNames;
import org.mongodb.morphia.Key;

import java.util.List;

/**
 *
 */
public class SbookService implements IDBService {

    // ------------------------------------------------------------------------
    // constants
    // ------------------------------------------------------------------------

    // ------------------------------------------------------------------------
    // members
    // ------------------------------------------------------------------------

    SbookDAO mSbookDAO;

    // ------------------------------------------------------------------------
    // constructors
    // ------------------------------------------------------------------------

    public SbookService() {
        final MorphiaService morphiaService = new MorphiaService();
        mSbookDAO = new SbookDAOImpl(Sbook.class, morphiaService.getMongoClient(), morphiaService.getMorphia(), EntityNames.DB_NAME);
    }

    // ------------------------------------------------------------------------
    // methods
    // ------------------------------------------------------------------------

    public List<Sbook> getAllSbooks() {
        return mSbookDAO.getAllSbooks();
    }

    public Sbook getById(String id) {
        return mSbookDAO.getById(id);
    }

    public Key<Sbook> save(Sbook sbook) {
        return mSbookDAO.save(sbook);
    }

    public WriteResult delete(Sbook sbook) {
        return mSbookDAO.delete(sbook);
    }

    @Override
    public boolean idTaken(String id) {
        return mSbookDAO.idTaken(id);
    }

    public List<Sbook> findBookingsByCarrierId(String carrierId) {
        return mSbookDAO.findBookingsByCarrierId(carrierId);
    }

    public List<Sbook> findBookingsByCarrierIdAndConnectionIdAndFlDate(String carrierCode, String connId, String fldate) {
        return mSbookDAO.findBookingsByCarrierIdAndConnectionIdAndFlDate(carrierCode, connId, fldate);
    }

    public List<Sbook> findBookingsByConnectionId(String connectionId) {
        return mSbookDAO.findBookingsByConnectionId(connectionId);
    }

}
