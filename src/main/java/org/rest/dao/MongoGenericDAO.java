package org.rest.dao;

import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;
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
//    private MongoClient mongoClient = new MongoClient("localhost", 8004);
    private MongoClient mongoClient = new MongoClient(
            new MongoClientURI( "mongodb://admin:password123@ds253840.mlab.com:53840/grades-service" )
    );
    private Morphia morphia = new Morphia();
//    protected Datastore datastore = morphia.createDatastore(mongoClient, "StudentGrades");
    protected Datastore datastore = morphia.createDatastore(mongoClient, "grades-service");

    public abstract List<T> getAll();

    public abstract T read(PK primaryKey);

    public abstract boolean update(T updateObject);

    /**
     * creating new generic object
     *
     * @param newObject new object
     * @return generic added to service object
     */
    public T create(T newObject) {
        datastore.save(newObject);
        return newObject;
    }

    /**
     * deleting object from service by generic primary key object
     *
     * @param primaryKey generic primary key object
     * @return true if succeeded
     */
    public boolean delete(PK primaryKey) {
        datastore.delete(read(primaryKey));
        if (read(primaryKey) == null)
            return true;
        else
            return false;
    }

    /**
     * Closing database connection
     *
     * @throws Throwable closing database exception
     */
    @Override
    protected void finalize() throws Throwable {
        mongoClient.close();
    }
}