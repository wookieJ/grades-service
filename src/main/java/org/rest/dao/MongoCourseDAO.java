package org.rest.dao;

import org.rest.model.Course;

import java.util.List;

/**
 * Course DAO in MongoDB database with Course class and Integer as primary Key
 */
public class MongoCourseDAO extends MongoGenericDAO<Course, Integer> {
    @Override
    public Course read(Integer primaryKey) {
        // TODO - napisać logikę
        return null;
    }

    @Override
    public boolean update(Course updateObject) {
        // TODO - uzupełnić
        return false;
    }

    @Override
    public List<Course> getAll() {
        return null;
    }
}