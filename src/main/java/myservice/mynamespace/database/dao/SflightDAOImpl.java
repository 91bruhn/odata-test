package myservice.mynamespace.database.dao;

import myservice.mynamespace.database.collections.Sflight;
import org.bson.types.ObjectId;
import org.mongodb.morphia.Datastore;
import org.mongodb.morphia.dao.BasicDAO;

/**
 *
 */
public class SflightDAOImpl extends BasicDAO<Sflight, ObjectId> implements SflightDAO {

    // ------------------------------------------------------------------------
    // constants
    // ------------------------------------------------------------------------

    // ------------------------------------------------------------------------
    // members
    // ------------------------------------------------------------------------

    // ------------------------------------------------------------------------
    // constructors
    // ------------------------------------------------------------------------

    public SflightDAOImpl(final Class<Sflight> entityClass, final Datastore ds) {
        super(entityClass, ds);
    }

    // ------------------------------------------------------------------------
    // methods
    // ------------------------------------------------------------------------

    // ------------------------------------------------------------------------
    // getters/setters
    // ------------------------------------------------------------------------

}
