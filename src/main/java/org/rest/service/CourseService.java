package org.rest.service;

import org.rest.dao.DAOFactory;
import org.rest.dao.MongoCourseDAO;
import org.rest.model.Course;

public class CourseService {
    public void addCourse(Course course) {
        DAOFactory factory = DAOFactory.getDAOFactory();
        MongoCourseDAO mongoCourseDAO = factory.getMongoCourseDAO();
        mongoCourseDAO.create(course);
    }
}
