package org.rest.dao;

import com.mongodb.MongoClient;
import org.mongodb.morphia.Datastore;
import org.mongodb.morphia.Morphia;
import org.rest.model.Student;

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

    public T create(T newObject) {
        datastore.save(newObject);
        return newObject;
    }

    public abstract T read(PK primaryKey);

    public boolean update(T updateObject) {
//        return datastore.createQuery(Student.class).field("index").equal(index).get();
//        datastore.update();
        return false;
    }

    public boolean delete(PK primaryKey) {
        return false;
    }

    public abstract List<T> getAll();
}