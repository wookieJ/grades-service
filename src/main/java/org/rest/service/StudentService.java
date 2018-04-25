package org.rest.service;

import org.rest.dao.DAOFactory;
import org.rest.dao.MongoStudentDAO;
import org.rest.model.Student;

import java.util.List;

/**
 * Service which provides operations on student collection in database
 */
public class StudentService {
    /**
     * Getting all students from database
     *
     * @return list of students from database
     */
    public List<Student> getAllStudents() {
        DAOFactory factory = DAOFactory.getDAOFactory();
        MongoStudentDAO mongoStudentDAO = factory.getMongoStudentDAO();
        return mongoStudentDAO.getAll();
    }

    /**
     * Adding new student to database
     *
     * @param student student we want to add to database
     * @return student we added to database
     */
    public Student addStudent(Student student) {
        IdGeneratorService generator = new IdGeneratorService();
        int newId = generator.generateStudentIndex();
        student.setIndex(newId);

        DAOFactory factory = DAOFactory.getDAOFactory();
        MongoStudentDAO mongoStudentDAO = factory.getMongoStudentDAO();
        mongoStudentDAO.create(student);

        return student;
    }

    /**
     * Getting student from database with specified index
     *
     * @param index student's index we want to get
     * @return student we want to get
     */
    public Student getStudent(int index) {
        DAOFactory factory = DAOFactory.getDAOFactory();
        MongoStudentDAO mongoStudentDAO = factory.getMongoStudentDAO();
        return mongoStudentDAO.read(index);
    }
}