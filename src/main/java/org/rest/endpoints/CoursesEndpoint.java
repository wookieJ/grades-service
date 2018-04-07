package org.rest.endpoints;

import jersey.repackaged.com.google.common.collect.Lists;
import org.rest.data.Data;
import org.rest.model.Course;
import org.rest.model.Grade;

import javax.ws.rs.*;
import javax.ws.rs.core.GenericEntity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

@Path("/courses")
public class CoursesEndpoint
{
    @GET
    @Path("/all")
    @Produces(MediaType.APPLICATION_XML)
    public Response getAllCourses()
    {
        // checking if courses list is empty
        if (Data.getCourses().size() == 0)
            return Response.status(Response.Status.NOT_FOUND).entity("No courses").build();

        GenericEntity<List<Course>> entity = new GenericEntity<List<Course>>(Lists.newArrayList(Data.getCourses()))
        {
        };
        // creating xml response
        return Response.status(Response.Status.OK).entity(entity).build();
    }

    @GET
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_XML)
    public Response getCourseById(@PathParam("id") int id)
    {
        // getting course by id
        Course courseByIndex = Data.getCourseById(id);

        // checking if course exists
        if (courseByIndex == null)
            return Response.status(Response.Status.NOT_FOUND).entity("Not found").build();

        // creating xml response
        return Response.status(Response.Status.OK).entity(courseByIndex).build();
    }

    @POST
    @Path("/add")
    @Consumes(MediaType.APPLICATION_XML)
    public Response addCourse(Course course)
    {
        // TODO - sprawdzić czy istnieje kurs
        // TODO - czy id trzeba dać czy można dać automatyczną generację przy tworzeniu?
        if (course != null)
        {
            // adding course to courses list
            Data.addCourse(course);
            String result = "Course " + course + " added!";

            // creating response
            return Response.status(Response.Status.CREATED).entity(result).build();
        } else
            return Response.status(Response.Status.NO_CONTENT).entity("Course cannot be null!").build();
    }

    @PUT
    @Path("/update")
    @Consumes(MediaType.APPLICATION_XML)
    public Response updateCourse(Course course)
    {
        // getting course by it's index
        Course searchedCourse = Data.getCourseById(course.getId());

        // checking if course exists
        if (searchedCourse == null)
            return Response.status(Response.Status.NOT_FOUND).entity("Not found").build();

        // updating course
        Data.updateCourse(course);
        String result = "Course " + course + " updated!";

        // creating response
        return Response.status(Response.Status.OK).entity(result).build();
    }

    @DELETE
    @Path("/delete/{id}")
    public Response deleteGrade(@PathParam("id") int id)
    {
        // getting course by it's index
        Course searchedCourse = Data.getCourseById(id);

        // checking if course exists
        if (searchedCourse == null)
        {
            return Response.status(Response.Status.NOT_FOUND).entity("Not found").build();
        }

        // updating course
        Data.removeCourseById(id);
        String result = "Course " + searchedCourse + " deleted!";

        // creating response
        return Response.status(Response.Status.OK).entity(result).build();
    }
}
