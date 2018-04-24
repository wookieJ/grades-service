package org.rest.dao;

import org.rest.model.Course;

import java.util.List;

/**
 * Course DAO in MongoDB database with Course class and Integer as primary Key
 */
public class MongoCourseDAO extends MongoGenericDAO<Course, Integer> {
    @Override
    public List<Course> getAll() {
        return null;
    }
}