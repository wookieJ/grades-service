package org.rest.endpoints;

import jersey.repackaged.com.google.common.collect.Lists;
import org.rest.model.Student;
import org.rest.service.StudentService;

import javax.ws.rs.*;
import javax.ws.rs.core.GenericEntity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

@Path("/students")
public class StudentsEndpoint {
    @GET
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public Response getAllStudents() {
        StudentService studentService = new StudentService();
        List<Student> students = studentService.getAllStudents();

        // checking if student list is empty
        if (students == null || students.size() == 0)
            return Response.status(Response.Status.NOT_FOUND).entity("No students").build();

        GenericEntity<List<Student>> entity = new GenericEntity<List<Student>>(Lists.newArrayList(students)) {
        };

        // creating response
        return Response.status(Response.Status.OK).entity(entity).build();
    }

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

    // TODO - Błąd gdy chcemy dodac studenta bez pola <grades>
    @POST
    @Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public Response addStudents(Student student) {
        if (student != null) {
            // TODO - czy potrzebne?
            Student newStudent = new Student(student);

            StudentService studentService = new StudentService();
            studentService.addStudent(newStudent);

            String result = "Student " + newStudent + " added!\n";

            // creating response
            Response response = Response.status(Response.Status.CREATED).header("Location", "/students/" + newStudent.getIndex()).entity(result).build();
            return response;
        } else
            return Response.status(Response.Status.NO_CONTENT).entity("Students cannot be null!").build();
    }


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

        // updating student
        boolean status = studentService.updateStudent(student);
        String result = "Student " + student + " updated!";

        // creating response
        return Response.status(Response.Status.OK).entity(result).build();
    }

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