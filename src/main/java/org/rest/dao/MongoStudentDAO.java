package org.rest.dao;

import org.mongodb.morphia.query.Query;
import org.rest.model.Student;

import java.util.List;

/**
 * Student DAO in MongoDB database with Student class and Integer as primary Key
 */
public class MongoStudentDAO extends MongoGenericDAO<Student, Integer> {
    @Override
    public Student read(Integer index) {
        return datastore.createQuery(Student.class).field("index").equal(index).get();
    }

    @Override
    public List<Student> getAll() {
        final Query<Student> query = datastore.createQuery(Student.class);
        final List<Student> students = query.asList();
        return students;
    }
}
