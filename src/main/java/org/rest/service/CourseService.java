package org.rest.service;

import org.rest.dao.DAOFactory;
import org.rest.dao.MongoCourseDAO;
import org.rest.model.Course;

// TODO - Komentarz
public class CourseService {
    public void addCourse(Course course) {
        IdGeneratorService generator = new IdGeneratorService();
        int newId = generator.generateCourseId();
        course.setId(newId);

        DAOFactory factory = DAOFactory.getDAOFactory();
        course.setId(newId);
        MongoCourseDAO mongoCourseDAO = factory.getMongoCourseDAO();
        mongoCourseDAO.create(course);
    }
}
