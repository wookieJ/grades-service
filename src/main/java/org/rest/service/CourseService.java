package org.rest.service;

import org.rest.dao.DAOFactory;
import org.rest.dao.MongoCourseDAO;
import org.rest.model.Course;

import java.util.List;

public class CourseService {
    /**
     * Getting all courses available in service.
     *
     * @return list of all courses in service.
     */
    public List<Course> getAllCourses() {
        DAOFactory factory = DAOFactory.getDAOFactory();
        MongoCourseDAO mongoCourseDAO = factory.getMongoCourseDAO();
        return mongoCourseDAO.getAll();
    }

    /**
     * Adding new course to service.
     *
     * @param course new course.
     * @return course we added to service.
     */
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

    /**
     * Getting course from service by id.
     *
     * @param id unique id of course we want to get.
     * @return course we want to get.
     */
    public Course getCourse(int id) {
        DAOFactory factory = DAOFactory.getDAOFactory();
        MongoCourseDAO mongoCourseDAO = factory.getMongoCourseDAO();
        return mongoCourseDAO.read(id);
    }

    /**
     * Getting course by parameters.
     *
     * @param name     course name.
     * @param lecturer lecturer name.
     * @return course we want to get.
     */
    public Course getCourseByParameters(String name, String lecturer) {
        DAOFactory factory = DAOFactory.getDAOFactory();
        MongoCourseDAO mongoCourseDAO = factory.getMongoCourseDAO();
        return mongoCourseDAO.readByParameters(name, lecturer);
    }

    /**
     * Updating existing course in service.
     * @param course new course version.
     * @return true if succe
     */
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
