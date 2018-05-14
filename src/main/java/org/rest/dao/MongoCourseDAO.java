package org.rest.dao;

import org.mongodb.morphia.query.Query;
import org.mongodb.morphia.query.UpdateOperations;
import org.rest.model.Course;

import java.util.List;

/**
 * Course DAO in MongoDB database with Course class and Integer as primary Key
 */
public class MongoCourseDAO extends MongoGenericDAO<Course, Integer> {
    /**
     * Getting course by id
     *
     * @param id id of course
     * @return course by id from service
     */
    @Override
    public Course read(Integer id) {
        return datastore.createQuery(Course.class).field("id").equal(id).get();
    }

    /**
     * Updating course in service
     *
     * @param updateObject refreshed version of course
     * @return true if operation succeeded
     */
    @Override
    public boolean update(Course updateObject) {
        final Query<Course> courseToUpdate = datastore.createQuery(Course.class).field("id").equal(updateObject.getId());
        final UpdateOperations<Course> updateOperations = datastore.createUpdateOperations(Course.class).set("name", updateObject.getName()).set("lecturer", updateObject.getLecturer());

        datastore.update(courseToUpdate, updateOperations);
        // TODO - check if succeeded
        return true;
    }

    /**
     * Getting all courses from service
     *
     * @return list of all courses
     */
    @Override
    public List<Course> getAll() {
        final Query<Course> query = datastore.createQuery(Course.class);
        final List<Course> courses = query.asList();
        return courses;
    }

    /**
     * Getting Course by it's name and lecturer name
     *
     * @param name     name of course
     * @param lecturer name of lecturer
     * @return course filtered by it's name and lecturer name
     */
    public Course readByParameters(String name, String lecturer) {
        return datastore.createQuery(Course.class).field("name").equal(name).field("lecturer").equal(lecturer).get();
    }

    /**
     * Getting courses by it's lecturer name
     *
     * @param lecturer lecturer name
     * @return list of courses filtered by lecturer name
     */
    public List<Course> getByLecturer(String lecturer) {
        final Query<Course> query = datastore.createQuery(Course.class);
        if (lecturer != null)
            query.field("lecturer").containsIgnoreCase(lecturer);
        List<Course> courses = query.asList();
        return courses;
    }
}