package org.rest.endpoints;

import jersey.repackaged.com.google.common.collect.Lists;
import org.rest.data.Data;
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
    @Path("/all")
    @Produces(MediaType.APPLICATION_XML)
    public Response getAllStudents()
    {
        // checking if student list is empty
        if (Data.getStudents().size() == 0)
            return Response.status(Response.Status.NOT_FOUND).entity("No students").build();

        GenericEntity<List<Student>> entity = new GenericEntity<List<Student>>(Lists.newArrayList(Data.getStudents()))
        {
        };
        // creating xml response
        return Response.status(Response.Status.OK).entity(entity).build();
    }

    @GET
    @Path("/{index}")
    @Produces(MediaType.APPLICATION_XML)
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
    @Path("/add")
    @Consumes(MediaType.APPLICATION_XML)
    public Response addStudent(Student student)
    {
        // adding student to students list
        Data.addStudent(student);
        String result = "Student " + student + " added!";

        // creating response
        return Response.status(Response.Status.CREATED).entity(result).build();
    }

    @PUT
    @Path("/update")
    @Consumes(MediaType.APPLICATION_XML)
    public Response updateStudent(Student student)
    {
        // getting student by it's index
        Student searchedStudent = Data.getStudentByIndex(student.getIndex());

        // checking if student exists
        if (searchedStudent == null)
            return Response.status(Response.Status.NOT_FOUND).entity("Not found").build();

        // updating student
        Data.updateStudent(student);
        String result = "Student " + student + " updated!";

        // creating response
        return Response.status(Response.Status.OK).entity(result).build();
    }

    @DELETE
    @Path("/delete/{index}")
    public Response updateStudent(@PathParam("index") int index)
    {
        // getting student by it's index
        Student searchedStudent = Data.getStudentByIndex(index);

        // checking if student exists
        if (searchedStudent == null)
            return Response.status(Response.Status.NOT_FOUND).entity("Not found").build();

        // updating student
        Data.removeStudentByIndex(index);
        String result = "Student " + searchedStudent + " deleted!";

        // creating response
        return Response.status(Response.Status.OK).entity(result).build();
    }
}