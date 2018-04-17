package org.rest.endpoints;

import jersey.repackaged.com.google.common.collect.Lists;
import org.rest.data.Data;
import org.rest.model.Grade;
import org.rest.model.Student;

import javax.ws.rs.*;
import javax.ws.rs.core.GenericEntity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

@Path("/students")
public class StudentsEndpoint
{
    @GET
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public Response getAllStudents()
    {
        // checking if student list is empty
        if (Data.getStudents().size() == 0)
            return Response.status(Response.Status.NOT_FOUND).entity("No students").build();

        List<Student> studentList = Data.getStudents();

        GenericEntity<List<Student>> entity = new GenericEntity<List<Student>>(Lists.newArrayList(studentList))
        {
        };
        // creating response
        return Response.status(Response.Status.OK).entity(entity).build();
    }

    @GET
    @Path("/{index}")
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public Response getStudentByIndex(@PathParam("index") int index)
    {
        // getting student by it's index
        Student searchedStudent = Data.getStudentByIndex(index);

        // checking if student exists
        if (searchedStudent == null)
            return Response.status(Response.Status.NOT_FOUND).entity("Not found").build();

        // creating xml response
        return Response.status(Response.Status.OK).entity(searchedStudent).build();
    }



    @POST
    @Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public Response addStudents(Student students)
    {
        if (students != null)
        {
            Student newStudent = new Student(students);
            Data.addStudent(newStudent);
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
    public Response updateStudent(Student student, @PathParam("index") int index)
    {
        // TODO - Szukam na liście czy istnieje student z danym indeksem, jak tak to robię studentsList.set(index, student) i zwracam 204, a jak jeszcze nie istnieje to robię .add(student) i zwracam 201

        // getting student by it's index
        Student searchedStudent = Data.getStudentByIndex(index);

        // checking if student exists
        if (searchedStudent == null)
            return Response.status(Response.Status.NOT_FOUND).entity("Student not found").build();

        // updating student
        student.setIndex(index);
        Data.updateStudent(student);
        String result = "Student " + student + " updated!";

        // creating response
        return Response.status(Response.Status.OK).entity(result).build();
    }

    @DELETE
    @Path("/{index}")
    public Response deleteStudent(@PathParam("index") int index)
    {
        // getting student by it's index
        Student searchedStudent = Data.getStudentByIndex(index);

        // checking if student exists
        if (searchedStudent == null)
            return Response.status(Response.Status.NOT_FOUND).entity("Student not found").build();

        // updating student
        Data.removeStudentByIndex(index);
        String result = "Student " + searchedStudent + " deleted!";

        // creating response
        return Response.status(Response.Status.OK).entity(result).build();
    }
}