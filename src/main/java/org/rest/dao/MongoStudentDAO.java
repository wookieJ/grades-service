package org.rest.dao;

import org.mongodb.morphia.query.Query;
import org.mongodb.morphia.query.UpdateOperations;
import org.rest.model.Student;

import java.util.Date;
import java.util.List;

/**
 * Student DAO in MongoDB database with Student class and Integer as primary Key
 */
public class MongoStudentDAO extends MongoGenericDAO<Student, Integer> {
    /**
     * Getting student by index
     *
     * @param index index of student
     * @return student with specified index
     */
    @Override
    public Student read(Integer index) {
        return datastore.createQuery(Student.class).field("index").equal(index).get();
    }

    /**
     * Getting all students from database
     *
     * @return list of all students
     */
    @Override
    public List<Student> getAll() {
        final Query<Student> query = datastore.createQuery(Student.class);
        final List<Student> students = query.asList();
        return students;
    }

    /**
     * Updating student
     *
     * @param updateObject
     * @return true if operation succeeded
     */
    public boolean update(Student updateObject) {
        final Query<Student> studentToUpdate = datastore.createQuery(Student.class).field("index").equal(updateObject.getIndex());
        final UpdateOperations<Student> updateOperations = datastore.createUpdateOperations(Student.class).set("firstName", updateObject.getFirstName()).set("lastName", updateObject.getLastName()).set("birthday", updateObject.getBirthday());
        updateOperations.set("grades", updateObject.getGrades());
        datastore.update(studentToUpdate, updateOperations);
        // TODO - check if succeeded
        return true;
    }

    /**
     * Getting all students that contain specified filters
     *
     * @param firstName first name of student
     * @return list of filtered by first name students
     */
    public List<Student> getStudentsByFilters(String firstName, String lastName, Date birthday, String dateRelation) {
        System.out.println("B: " + birthday);
        System.out.println("Rel: " + dateRelation);
        final Query<Student> query = datastore.createQuery(Student.class);
        if(firstName != null)
            query.field("firstName").containsIgnoreCase(firstName);
        if(lastName != null)
            query.field("lastName").containsIgnoreCase(lastName);
        if(birthday != null && dateRelation != null) {
            System.out.println("birthday != null ? " + birthday != null);
            if(dateRelation.equals("equal")) {
                System.out.println("Equal!");
                query.field("birthday").equal(birthday);
            }
            else if(dateRelation.equals("grater"))
                query.field("birthday").greaterThan(birthday);
            else if(dateRelation.equals("lower"))
                query.field("birthday").lessThan(birthday);
        }
        List<Student> filteredStudents = query.asList();
        return filteredStudents;
    }
}
