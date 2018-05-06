package org.rest.dao;

import com.mongodb.MongoClient;
import org.mongodb.morphia.Datastore;
import org.mongodb.morphia.Morphia;

import java.io.Serializable;
import java.util.List;

/**
 * MongoDB database abstract class with primary CRUD operations definitions
 *
 * @param <T>  Class that we want to operate on with CRUD operations
 * @param <PK> Primary key that identify object in the database
 */
public abstract class MongoGenericDAO<T, PK extends Serializable> {
    // TODO - Singleton pattern implement
    private MongoClient mongoClient = new MongoClient("localhost", 8004);
    private Morphia morphia = new Morphia();
    protected Datastore datastore = morphia.createDatastore(mongoClient, "students-grades");

    public abstract List<T> getAll();

    public abstract T read(PK primaryKey);

    public abstract boolean update(T updateObject);

    public T create(T newObject) {
        datastore.save(newObject);
        return newObject;
    }

    public boolean delete(PK primaryKey) {
        datastore.delete(read(primaryKey));
        if (read(primaryKey) == null)
            return true;
        else
            return false;
    }

    @Override
    protected void finalize() throws Throwable {
        mongoClient.close();
    }
}