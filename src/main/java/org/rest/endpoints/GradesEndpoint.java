package org.rest.endpoints;

import jersey.repackaged.com.google.common.collect.Lists;
import org.rest.model.Course;
import org.rest.model.Grade;
import org.rest.model.Student;
import org.rest.service.CourseService;
import org.rest.service.IdGeneratorService;
import org.rest.service.StudentService;

import javax.ws.rs.*;
import javax.ws.rs.core.GenericEntity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;
import java.util.stream.Collectors;

@Path("/students/{index}/grades")
public class GradesEndpoint {

    @PathParam("index")
    private int index;

    /**
     * Endpoint which return list of student's grades. It is possible to filter list by grades course's name with parameter
     * and by value of grade.
     *
     * @param courseName    course name parameter which value is used to filtering student's grades list.
     * @param value         grade's value parameter which is used to filtering student's grades list.
     * @param valueRelation value relation to student's grade's value for example "lower", "grater".
     * @return list of all student's grades if courseName or value are't used or filtered by them otherwise.
     */
    @GET
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public Response getStudentGrades(@QueryParam("courseName") String courseName,
                                     @QueryParam("value") String value,
                                     @QueryParam("valueRelation") String valueRelation) {
        // getting student by it's index
        StudentService studentService = new StudentService();
        Student searchedStudent = studentService.getStudent(index);

        // checking if student exists
        if (searchedStudent == null) {
            return Response.status(Response.Status.NOT_FOUND).entity("Student not found").build();
        }

        List<Grade> grades = searchedStudent.getGrades();
        if (grades == null || grades.isEmpty())
            return Response.status(Response.Status.NOT_FOUND).entity("Student's grades not found").build();

        // filtering by course
        if (courseName != null) {
            grades = grades.stream().filter(gr -> gr.getCourse().getName().equals(courseName)).collect(Collectors.toList());
        }

        // filtering by grade's value
        if (value != null && valueRelation != null) {
            switch (valueRelation.toLowerCase()) {
                case "grater":
                    // TODO - is String for value needed, maybe Float?
                    grades = grades.stream().filter(gr -> gr.getValue() > Float.valueOf(value).floatValue()).collect(Collectors.toList());
                    break;
                case "lower":
                    // TODO - is String for value needed, maybe Float?
                    grades = grades.stream().filter(gr -> gr.getValue() < Float.valueOf(value).floatValue()).collect(Collectors.toList());
                    break;
            }
        }

        // creating list of student's grades
        GenericEntity<List<Grade>> entity = new GenericEntity<List<Grade>>(Lists.newArrayList(grades)) {
        };
        // creating xml response
        return Response.status(Response.Status.OK).entity(entity).build();
    }

    /**
     * Endpoint that allows getting grade by its id.
     *
     * @param id unique id of grade.
     * @return id we want to get.
     */
    @GET
    @Path("/{id}")
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public Response getStudentGrade(@PathParam("id") int id) {
        // getting student by it's index
        StudentService studentService = new StudentService();
        Student searchedStudent = studentService.getStudent(index);

        // checking if student exists
        if (searchedStudent == null) {
            return Response.status(Response.Status.NOT_FOUND).entity("Student not found").build();
        }

        // getting student's grade by it's id
        Grade searchedGrade = searchedStudent.getGradeById(id);

        // checking if student's grade exists
        if (searchedGrade == null) {
            return Response.status(Response.Status.NOT_FOUND).entity("Grade not found").build();
        }

        // creating xml response
        return Response.status(Response.Status.OK).entity(searchedGrade).build();
    }

    /**
     * Enpoints that allows adds grade to student in service.
     *
     * @param grade grade we want to add to student grades list.
     * @return response of successful operation.
     */
    @POST
    @Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public Response addStudentGrades(Grade grade) {
        // checking if grade is a null
        if (grade != null) {
            // getting student by it's index
            StudentService studentService = new StudentService();
            Student searchedStudent = studentService.getStudent(index);

            // checking if student exists
            if (searchedStudent == null)
                return Response.status(Response.Status.NOT_FOUND).entity("Student not found").build();

            CourseService courseService = new CourseService();
            Course searchedCourse = courseService.getCourseByParameters(grade.getCourse().getName(), grade.getCourse().getLecturer());

            if (searchedCourse == null)
                return Response.status(Response.Status.NOT_FOUND).entity("Grade's course not found").build();

            IdGeneratorService generator = new IdGeneratorService();
            grade.setId(generator.generateGradeId());
            grade.setStudentIndex(searchedStudent.getIndex());
            grade.setCourse(searchedCourse);
            searchedStudent.addGrade(grade);
            studentService.updateStudent(searchedStudent, false);
            String result = "Student grade " + grade + " added!\n";

            // creating response
            return Response.status(Response.Status.CREATED).header("Location", "students/" + searchedStudent.getIndex() + "/grades/" + grade.getId()).entity(result).build();
        } else
            return Response.status(Response.Status.NO_CONTENT).entity("Grade cannot be null!").build();
    }

    /**
     * Endpoint that allows updating existing student in service. The endpoint checks if student exists in service.
     *
     * @param grade grade we want to be refreshed version.
     * @param id    id of student we want to be updated.
     * @return response of successful operation.
     */
    @PUT
    @Path("/{id}")
    @Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public Response addStudentGrade(Grade grade, @PathParam("id") int id) {
        // checking if grade is a null
        if (grade != null) {
            // getting student by it's index
            StudentService studentService = new StudentService();
            Student searchedStudent = studentService.getStudent(index);

            // checking if student exists
            if (searchedStudent == null) {
                return Response.status(Response.Status.NOT_FOUND).entity("Student not found").build();
            }

            // getting student's grade by it's id
            Grade searchedGrade = searchedStudent.getGradeById(id);

            // checking if student's grade exists
            if (searchedGrade == null) {
                return Response.status(Response.Status.NOT_FOUND).entity("Grade not found").build();
            }

            // checking if grade's course exists
            CourseService courseService = new CourseService();
            Course searchedCourse = courseService.getCourseByParameters(grade.getCourse().getName(), grade.getCourse().getLecturer());
            System.out.println("Course : " + searchedCourse);
            if (searchedCourse == null)
                return Response.status(Response.Status.NOT_FOUND).entity("Grade's course not found").build();

            searchedCourse.setId(grade.getCourse().getId());
            grade.setId(id);
            grade.setStudentIndex(searchedStudent.getIndex());
            searchedStudent.updateStudentGrade(grade);
            studentService.updateStudent(searchedStudent, false);
            String result = "Student grade " + grade + " updated!";

            // creating response
            return Response.status(Response.Status.CREATED).entity(result).build();
        } else
            return Response.status(Response.Status.NOT_FOUND).entity("Grade cannot be null!").build();
    }

    /**
     * Endpoint that allows deleting grade from service. The endpoint checks if student and it's grade we wnt to delete exists in service.
     *
     * @param id unique grade's id we want to delete.
     * @return response of successful operation.
     */
    @DELETE
    @Path("/{id}")
    public Response deleteStudentGrade(@PathParam("id") int id) {
        // getting student by it's index
        StudentService studentService = new StudentService();
        Student searchedStudent = studentService.getStudent(index);

        // checking if student exists
        if (searchedStudent == null)
            return Response.status(Response.Status.NOT_FOUND).entity("Student not found").build();

        Grade searchedGrade = searchedStudent.getGradeById(id);
        // checking if grade exists
        if (searchedGrade == null)
            return Response.status(Response.Status.NOT_FOUND).entity("Grade not found").build();

        // removing student grade
        searchedStudent.removeStudentGradeById(id);
        studentService.updateStudent(searchedStudent, false);
        String result = "Student grade " + searchedGrade + " deleted!";

        // creating response
        return Response.status(Response.Status.OK).entity(result).build();
    }
}
