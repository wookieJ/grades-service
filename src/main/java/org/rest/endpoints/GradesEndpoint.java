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

    @DELETE
    @Path("/{id}")
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
