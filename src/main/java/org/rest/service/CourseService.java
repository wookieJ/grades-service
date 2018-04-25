package org.rest.service;

import org.rest.dao.DAOFactory;
import org.rest.dao.MongoCourseDAO;
import org.rest.model.Course;

import java.util.List;

// TODO - Komentarze
public class CourseService {
    public List<Course> getAllCourses() {
        DAOFactory factory = DAOFactory.getDAOFactory();
        MongoCourseDAO mongoCourseDAO = factory.getMongoCourseDAO();
        return mongoCourseDAO.getAll();
    }

    public Course addCourse(Course course) {
        IdGeneratorService generator = new IdGeneratorService();
        int newId = generator.generateCourseId();
        course.setId(newId);

        DAOFactory factory = DAOFactory.getDAOFactory();
        course.setId(newId);
        MongoCourseDAO mongoCourseDAO = factory.getMongoCourseDAO();
        mongoCourseDAO.create(course);

        return course;
    }

    public Course getCourse(int id) {
        DAOFactory factory = DAOFactory.getDAOFactory();
        MongoCourseDAO mongoCourseDAO = factory.getMongoCourseDAO();
        return mongoCourseDAO.read(id);
    }

    public boolean updateCourse(Course course) {
        DAOFactory factory = DAOFactory.getDAOFactory();
        MongoCourseDAO mongoCourseDAO = factory.getMongoCourseDAO();
        return mongoCourseDAO.update(course);
    }

    public boolean deleteCourse(int id) {
        DAOFactory factory = DAOFactory.getDAOFactory();
        MongoCourseDAO mongoCourseDAO = factory.getMongoCourseDAO();
        return mongoCourseDAO.delete(id);
    }
}
