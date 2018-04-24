package org.rest.application;

import org.glassfish.grizzly.http.server.HttpServer;
import org.glassfish.jersey.grizzly2.httpserver.GrizzlyHttpServerFactory;
import org.glassfish.jersey.linking.DeclarativeLinkingFeature;
import org.glassfish.jersey.server.ResourceConfig;
import org.rest.data.Data;
import org.rest.model.Course;
import org.rest.model.Grade;
import org.rest.model.Student;
import org.rest.service.StudentService;

import java.net.URI;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Application {
    /**
     * URI where application started at.
     */
    public static final String BASE_URI = "http://localhost:8080";

    /**
     * Creating new resources where JAX-RS can finds endpoints and returning GrizzlyServer basef on BASE_URI.
     *
     * @return GrizzlyServer from GrizzlyHttpServerFactory based on BASE_URI.
     */
    public static HttpServer startServer() {
        ResourceConfig rc = new ResourceConfig().packages("org.glassfish.jersey.examples.linking").register(DeclarativeLinkingFeature.class).packages("org.rest.endpoints");
        rc.register(org.rest.exceptions.AppExceptionMapper.class);
        return GrizzlyHttpServerFactory.createHttpServer(URI.create(BASE_URI), rc);
    }

    public static void main(String[] args) {
        try {
            // loading students to static list
//            Data.initialize();
//            Data.loadData();

//            StudentService studentService = new StudentService();
//            Course course1 = new Course("Automatyka", "dr Adam Nowicki");
//            Grade grade1 = new Grade(4, new Date(), course1);
//            grade1.setStudentIndex(0);
//            List<Grade> student1Grades = new ArrayList<>();
//            student1Grades.add(grade1);
//            Student newStudent = new Student("Janek", "Nowak", Data.getDate(1995, 11, 14), null);
//            newStudent.setGrades(student1Grades);
//            studentService.addStudent(newStudent);

            // starting server
            startServer();
            System.out.println(String.format("Homework app started at " + BASE_URI));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}