package myservice.mynamespace.database.dao;

import myservice.mynamespace.database.data.Sflight;
import org.bson.types.ObjectId;
import org.mongodb.morphia.dao.DAO;

/**
 *
 */
public interface SflightDAO extends DAO<Sflight, ObjectId> {}