package myservice.mynamespace.database.service;

import myservice.mynamespace.database.DatabaseHandler;
import myservice.mynamespace.database.data.Saplane;
import myservice.mynamespace.database.data.Sbook;
import myservice.mynamespace.database.data.Scarr;
import myservice.mynamespace.database.data.Sflight;
import myservice.mynamespace.database.data.Spfli;
import org.apache.olingo.commons.api.data.Entity;

import java.util.List;
import java.util.stream.Collectors;

/**
 *
 */
public class TransformStuffFromDb {

    // ------------------------------------------------------------------------
    // constants
    // ------------------------------------------------------------------------

    // ------------------------------------------------------------------------
    // members
    // ------------------------------------------------------------------------

    private DatabaseHandler mDatabaseHandler;
    // ------------------------------------------------------------------------

    // constructors
    // ------------------------------------------------------------------------

    public TransformStuffFromDb() {
        mDatabaseHandler = new DatabaseHandler();
    }

    // ------------------------------------------------------------------------
    // methods
    // ------------------------------------------------------------------------

    public List<Entity> getFlightEntities() {
        final List<Sflight> sflights = mDatabaseHandler.getAllSflights();
        return sflights.stream().map(DataTransformator::transformSflightToEntity).collect(Collectors.toList());
    }

    public List<Entity> getConnectionEntities() {
        final List<Spfli> spflis = mDatabaseHandler.getAllSpflis();
        return spflis.stream().map(DataTransformator::transformSpfliToEntity).collect(Collectors.toList());
    }

    public List<Entity> getCarrierEntities() {
        final List<Scarr> scarrs = mDatabaseHandler.getAllScarrs();
        return scarrs.stream().map(DataTransformator::transformScarrToEntity).collect(Collectors.toList());
    }

    public List<Entity> getBookingEntities() {
        final List<Sbook> sbooks = mDatabaseHandler.getAllSbooks();
        return sbooks.stream().map(DataTransformator::transformSbookToEntity).collect(Collectors.toList());
    }

    public List<Entity> getPlaneEntities() {
        final List<Saplane> saplanes = mDatabaseHandler.getAllSaplanes();
        return saplanes.stream().map(DataTransformator::transformSaplaneToEntity).collect(Collectors.toList());
    }
}
