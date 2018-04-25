package org.rest.dao;

import org.mongodb.morphia.query.Query;
import org.mongodb.morphia.query.UpdateOperations;
import org.rest.model.Course;
import org.rest.model.Student;

import java.util.List;

/**
 * Course DAO in MongoDB database with Course class and Integer as primary Key
 */
public class MongoCourseDAO extends MongoGenericDAO<Course, Integer> {
    @Override
    public Course read(Integer id) {
        return datastore.createQuery(Course.class).field("id").equal(id).get();
    }

    @Override
    public boolean update(Course updateObject) {
        final Query<Course> courseToUpdate = datastore.createQuery(Course.class).field("id").equal(updateObject.getId());
        final UpdateOperations<Course> updateOperations = datastore.createUpdateOperations(Course.class)
                .set("name", updateObject.getName())
                .set("lecturer", updateObject.getLecturer());

        datastore.update(courseToUpdate, updateOperations);
        // TODO - check if succeeded
        return true;
    }

    @Override
    public List<Course> getAll() {
        final Query<Course> query = datastore.createQuery(Course.class);
        final List<Course> courses = query.asList();
        return courses;
    }

    public Course readByParameters(String name, String lecturer) {
        return datastore.createQuery(Course.class).field("name").equal(name)
                .field("lecturer").equal(lecturer).get();
    }
}