////////////////////////////////////////////////////////////////////////////////
//
// Created by BBruhns on 10.03.2018.
//
// Copyright (c) 2006 - 2018 FORCAM GmbH. All rights reserved.
////////////////////////////////////////////////////////////////////////////////

package myservice.mynamespace.database.dao;

/**
 *
 */
public class AbstractDAO {//TODO delete

    // ------------------------------------------------------------------------
    // constants
    // ------------------------------------------------------------------------

    //    protected MongoClient mongoClient;
    //    protected Morphia morphia;
    //    protected Datastore datastore;

    // ------------------------------------------------------------------------
    // members
    // ------------------------------------------------------------------------

    // ------------------------------------------------------------------------
    // constructors
    // ------------------------------------------------------------------------

    public AbstractDAO() {
        //        mongoClient = new MongoClient("localhost", 27017);
        //        morphia = new Morphia();
        //        morphia.mapPackage("myservice.mynamespace.database.data");
        //        datastore = morphia.createDatastore(mongoClient, "Flugdatenverwaltung");
        //        datastore.ensureIndexes();
    }
    //    public SpfliDAOImpl(Class<Sflight> entityClass, MongoClient mMongoClient, Morphia mMorphia, String dbName) {
    //        super(entityClass, mMongoClient, mMorphia, dbName);
    //    }

    // ------------------------------------------------------------------------
    // methods
    // ------------------------------------------------------------------------

    //    public void saveData(Object object) {
    //        datastore.save(object);
    //        //TODO speichere komplexe objekte, also die verschachtelten Sachen.
    //    }
    //
    //    public void deleteData(Object object) {
    //        datastore.delete(object);
    //        //        mDatastore.delet; oder direkt mit id
    //    }

    // ----FIND DATA----

    //    public Object getById(Class clazz, String id) {
    //        return datastore.get(clazz, id);
    //    }
    //
    //    public boolean idTaken(Class clazz, String id) {
    //        return this.getById(clazz, id) != null;
    //    }
    //
    //    public boolean idTaken(Class clazz, int id) {
    //        return datastore.get(clazz, id) != null;
    //    }

}
