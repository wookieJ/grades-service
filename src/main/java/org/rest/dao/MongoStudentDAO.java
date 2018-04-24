package org.rest.dao;

import org.mongodb.morphia.query.Query;
import org.rest.model.Student;

import java.util.List;

/**
 * Student DAO in MongoDB database with Student class and Integer as primary Key
 */
public class MongoStudentDAO extends MongoGenericDAO<Student, Integer> {
    @Override
    public List<Student> getAll() {
        final Query<Student> query = super.datastore.createQuery(Student.class);
        final List<Student> students = query.asList();
        return students;
    }
}
