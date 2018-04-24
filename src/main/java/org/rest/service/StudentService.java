package org.rest.service;

import org.rest.dao.DAOFactory;
import org.rest.dao.MongoIdGeneratorDAO;
import org.rest.dao.MongoStudentDAO;
import org.rest.model.Student;

import java.util.List;

// TODO - komentarze
public class StudentService {
    public void addStudent(Student student) {
        DAOFactory factory = DAOFactory.getDAOFactory();
        MongoIdGeneratorDAO mongoIdGeneratorDAO = factory.getMongoIdGeneratorDAO();
        int newId = mongoIdGeneratorDAO.incrementStudentId();
        student.setIndex(newId);
        MongoStudentDAO mongoStudentDAO = factory.getMongoStudentDAO();
        mongoStudentDAO.create(student);
    }

    public List<Student> getAllStudents(){
        DAOFactory factory = DAOFactory.getDAOFactory();
        MongoStudentDAO mongoStudentDAO = factory.getMongoStudentDAO();
        return mongoStudentDAO.getAll();
    }
}
