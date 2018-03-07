////////////////////////////////////////////////////////////////////////////////
//
// Created by bruhn on 30.12.2017.
//
// Copyright (c) 2006 - 2017 FORCAM GmbH. All rights reserved.
////////////////////////////////////////////////////////////////////////////////

package myservice.mynamespace.database.dao;

import myservice.mynamespace.database.data.Sflight;
import org.bson.types.ObjectId;
import org.mongodb.morphia.dao.DAO;

/**
 *
 */
public interface SflightDAO extends DAO<Sflight, ObjectId> {}
