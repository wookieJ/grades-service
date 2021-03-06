package org.rest.endpoints;

import jersey.repackaged.com.google.common.collect.Lists;
import org.rest.model.Course;
import org.rest.model.Student;
import org.rest.service.CourseService;
import org.rest.service.StudentService;

import javax.ws.rs.*;
import javax.ws.rs.core.GenericEntity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Path("/students")
public class StudentsEndpoint {
    /**
     * Endpoint which returns List of all students. It is also possible to filter students by first name, last name and
     * date of birth. Using date as filter it is require to set date relation.
     *
     * @param firstName    student's first name which will we used for filtering students list.
     * @param lastName     student's last name which will we used for filtering students list.
     * @param birthday     date of birth which is used for filtering students list.
     * @param dateRelation the way we want to compare dates. For example "equal", "grater", "lower".
     * @return students list with all student if no filter is defined or filtered list otherwise.
     */
    @GET
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public Response getAllStudents(@QueryParam("firstName") String firstName, @QueryParam("lastName") String lastName, @QueryParam("birthday") Date birthday, @QueryParam("dateRelation") String dateRelation) throws ParseException {
        System.out.println("B:" + birthday);

        StudentService studentService = new StudentService();
        List<Student> students = studentService.getStudentsByFilters(firstName, lastName, birthday, dateRelation);
        System.out.println(students);
        GenericEntity<List<Student>> entity = new GenericEntity<List<Student>>(Lists.newArrayList(students)) {
        };

        // creating response
        return Response.status(Response.Status.OK).entity(entity).build();
    }

    /**
     * Endpoint which returns student by unique index from service.
     *
     * @param index unique student index.
     * @return student with unique index we want to get.
     */
    @GET
    @Path("/{index}")
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public Response getStudentByIndex(@PathParam("index") int index) {
        // getting student by it's index
        StudentService studentService = new StudentService();
        Student searchedStudent = studentService.getStudent(index);

        List<Student> students = new ArrayList<>();
        students.add(searchedStudent);

        GenericEntity<List<Student>> entity = new GenericEntity<List<Student>>(Lists.newArrayList(students)) {
        };
        // creating xml response
        return Response.status(Response.Status.OK).entity(entity).build();
    }

    /**
     * Endpoint which adds student to service which user send by POST method.
     *
     * @param student student we want to add.
     * @return response of successful operation. 204 response if created new Student.
     */
    @POST
    @Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public Response addStudents(Student student) {
        System.out.print("POST ");
        System.out.println(student);

        StudentService studentService = new StudentService();
        studentService.addStudent(student);

        // creating response
        return Response.status(Response.Status.CREATED).header("Location", "/students/" + student.getIndex()).entity(student).build();
    }

    /**
     * Endpoint that allows update existing student in service.
     *
     * @param student updated version of student.
     * @param index   index of student we want to update.
     * @return response of successful operation.
     */
    @PUT
    @Path("/{index}")
    @Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public Response updateStudent(Student student, @PathParam("index") int index) {
        // getting student by it's index
        StudentService studentService = new StudentService();
        Student searchedStudent = studentService.getStudent(index);

//        // checking if student exists
//        if (searchedStudent == null)
//            return Response.status(Response.Status.NOT_FOUND).entity("Student not found").build();

        // checking if grade's course exists
        CourseService courseService = new CourseService();
        Course searchedCourse;

//        for (Grade grade : student.getGrades()) {
//            searchedCourse = courseService.getCourseById(grade.getCourse().getId());
//            if (searchedCourse == null) {
//                return Response.status(Response.Status.NOT_FOUND).entity("Grade's course not found").build();
//            }
//        }

        // updating student
        studentService.updateStudent(student);
        String result = "Student " + student + " updated!";

        // creating response
        return Response.status(Response.Status.OK).entity(result).build();
    }

    /**
     * Endpoint that allows deleting student from service.
     *
     * @param index index of student we want to delete from service.
     * @return response of successful operation.
     */
    @DELETE
    @Path("/{index}")
    public Response deleteStudent(@PathParam("index") int index) {
        System.out.println("DELETE");
        System.out.println("index = " + index);
        // getting student by it's index
        StudentService studentService = new StudentService();
        Student searchedStudent = studentService.getStudent(index);
        System.out.println(searchedStudent);
        // checking if student exists
        if (searchedStudent == null)
            return Response.status(Response.Status.NOT_FOUND).entity("Student not found").build();

        // updating student
        boolean status = studentService.deleteStudent(index);
        if (status) {
            String result = "Student " + searchedStudent + " deleted!";

            // creating response
            return Response.status(Response.Status.OK).entity(result).build();
        } else
            return Response.status(Response.Status.CONFLICT).entity("Error, not deleted").build();
    }
}