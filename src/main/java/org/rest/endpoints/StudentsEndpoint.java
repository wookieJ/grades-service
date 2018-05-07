package org.rest.endpoints;

import jersey.repackaged.com.google.common.collect.Lists;
import org.rest.model.Course;
import org.rest.model.Grade;
import org.rest.model.Student;
import org.rest.service.CourseService;
import org.rest.service.StudentService;

import javax.ws.rs.*;
import javax.ws.rs.core.GenericEntity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Path("/students")
public class StudentsEndpoint {
    /**
     * Endpoint which returns List of all students. It is also possible to filter students by first name, last name and
     * date of birth. Using date as filter it is require to set date relation.
     *
     * @param firstName    student's first name which will we used for filtering students list.
     * @param lastName     student's last name which will we used for filtering students list.
     * @param date         date of birth which is used for filtering students list.
     * @param dateRelation the way we want to compare dates. For example "equal", "grater", "lower".
     * @return students list with all student if no filter is defined or filtered list otherwise.
     */
    @GET
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public Response getAllStudents(@QueryParam("firstName") String firstName,
                                   @QueryParam("lastName") String lastName,
                                   @QueryParam("date") Date date,
                                   @QueryParam("dateRelation") String dateRelation) {
        StudentService studentService = new StudentService();
        List<Student> students = studentService.getAllStudents();

        // checking if student list is empty
        if (students == null || students.size() == 0)
            return Response.status(Response.Status.NOT_FOUND).entity("No students").build();

        // filtering by firstName
        if (firstName != null) {
            students = students.stream().filter(st -> st.getFirstName().equals(firstName)).collect(Collectors.toList());
        }

        // filtering by lastName
        if (lastName != null) {
            students = students.stream().filter(st -> st.getLastName().equals(lastName)).collect(Collectors.toList());
        }

        // filtering by date
        if (date != null && dateRelation != null) {
            switch (dateRelation.toLowerCase()) {
                case "equal":
                    students = students.stream().filter(st -> st.getBirthday().equals(date)).collect(Collectors.toList());
                    break;
                case "after":
                    students = students.stream().filter(st -> st.getBirthday().after(date)).collect(Collectors.toList());
                    break;
                case "before":
                    students = students.stream().filter(st -> st.getBirthday().before(date)).collect(Collectors.toList());
                    break;
            }
        }

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

        // checking if student exists
        if (searchedStudent == null)
            return Response.status(Response.Status.NOT_FOUND).entity("Not found").build();

        // creating xml response
        return Response.status(Response.Status.OK).entity(searchedStudent).build();
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
        if (student != null) {
            // checking if grade's course exists
            CourseService courseService = new CourseService();
            Course searchedCourse;

            for (Grade grade : student.getGrades()) {
                searchedCourse = courseService.getCourseByParameters(grade.getCourse().getName(), grade.getCourse().getLecturer());
                if (searchedCourse == null) {
                    return Response.status(Response.Status.NOT_FOUND).entity("Grade's course not found").build();
                }
            }

            StudentService studentService = new StudentService();
            studentService.addStudent(student);

            String result = "Student " + student + " added!\n";

            // creating response
            Response response = Response.status(Response.Status.CREATED).header("Location", "/students/" + student.getIndex()).entity(result).build();
            return response;
        } else
            return Response.status(Response.Status.NO_CONTENT).entity("Student cannot be null!").build();
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

        // checking if student exists
        if (searchedStudent == null)
            return Response.status(Response.Status.NOT_FOUND).entity("Student not found").build();

        // checking if grade's course exists
        CourseService courseService = new CourseService();
        Course searchedCourse;

        for (Grade grade : student.getGrades()) {
            searchedCourse = courseService.getCourseByParameters(grade.getCourse().getName(), grade.getCourse().getLecturer());
            if (searchedCourse == null) {
                return Response.status(Response.Status.NOT_FOUND).entity("Grade's course not found").build();
            }
        }

        // updating student
        boolean status = studentService.updateStudent(student, false);
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
        // getting student by it's index
        StudentService studentService = new StudentService();
        Student searchedStudent = studentService.getStudent(index);

        // checking if student exists
        if (searchedStudent == null)
            return Response.status(Response.Status.NOT_FOUND).entity("Student not found").build();

        // updating student
        boolean status = studentService.deleteStudent(index);
        if (status == true) {
            String result = "Student " + searchedStudent + " deleted!";

            // creating response
            return Response.status(Response.Status.OK).entity(result).build();
        } else
            return Response.status(Response.Status.CONFLICT).entity("Error, not deleted").build();
    }
}