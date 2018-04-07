package org.rest.endpoints;

import jersey.repackaged.com.google.common.collect.Lists;
import org.rest.data.Data;
import org.rest.model.Grade;

import javax.ws.rs.*;
import javax.ws.rs.core.GenericEntity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

@Path("/grades")
public class GradesEndpoint
{
    @GET
    @Path("/all")
    @Produces(MediaType.APPLICATION_XML)
    public Response getAllGrades()
    {
        // checking if grades list is empty
        if (Data.getGrades().size() == 0)
            return Response.status(Response.Status.NOT_FOUND).entity("No grades").build();

        GenericEntity<List<Grade>> entity = new GenericEntity<List<Grade>>(Lists.newArrayList(Data.getGrades()))
        {
        };
        // creating xml response
        return Response.status(Response.Status.OK).entity(entity).build();
    }

    @GET
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_XML)
    public Response getGradeById(@PathParam("id") int id)
    {
        // getting grade by id
        Grade gradeByIndex = Data.getGradeById(id);

        // checking if grade exists
        if (gradeByIndex == null)
            return Response.status(Response.Status.NOT_FOUND).entity("Not found").build();

        // creating xml response
        return Response.status(Response.Status.OK).entity(gradeByIndex).build();
    }

    @POST
    @Path("/add")
    @Consumes(MediaType.APPLICATION_XML)
    public Response addGrade(Grade grade)
    {
        // TODO - sprawdzić czy istnieje kurs
        // TODO - czy id trzeba dać czy można dać automatyczną generację przy tworzeniu?
        if (grade != null)
        {
            // adding grade to grades list
            Data.addGrade(grade);
            String result = "Grade " + grade + " added!";

            // creating response
            return Response.status(Response.Status.CREATED).entity(result).build();
        } else
            return Response.status(Response.Status.NO_CONTENT).entity("Grade cannot be null!").build();
    }

    @PUT
    @Path("/update")
    @Consumes(MediaType.APPLICATION_XML)
    public Response updateGrade(Grade grade)
    {
        // getting grade by it's index
        Grade searchedGrade = Data.getGradeById(grade.getId());

        // checking if grade exists
        if (searchedGrade == null)
            return Response.status(Response.Status.NOT_FOUND).entity("Not found").build();

        // updating grade
        Data.updateGrade(grade);
        String result = "Grade " + grade + " updated!";

        // creating response
        return Response.status(Response.Status.OK).entity(result).build();
    }

    @DELETE
    @Path("/delete/{id}")
    public Response deleteGrade(@PathParam("id") int id)
    {
        // getting grade by it's index
        Grade searchedGrade = Data.getGradeById(id);

        // checking if grade exists
        if (searchedGrade == null)
        {
            return Response.status(Response.Status.NOT_FOUND).entity("Not found").build();
        }

        // updating grade
        Data.removeGradeById(id);
        String result = "Grade " + searchedGrade + " deleted!";

        // creating response
        return Response.status(Response.Status.OK).entity(result).build();
    }
}
