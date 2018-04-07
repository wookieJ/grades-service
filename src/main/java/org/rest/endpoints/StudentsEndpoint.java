package org.rest.endpoints;

import org.rest.data.Data;
import org.rest.model.Student;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.util.List;

@Path("/students")
public class StudentsEndpoint
{
    private final List<Student> students = Data.getStudents();

    @GET
    @Path("/all")
    @Produces(MediaType.APPLICATION_JSON)
    public List<Student> getAllStudents()
    {
        return Data.getStudents();
    }

    /*

    @POST
	@Path("/post")
	@Consumes(MediaType.APPLICATION_JSON)
	public Response createTrackInJSON(Track track) {

		String result = "Track saved : " + track;
		return Response.status(201).entity(result).build();

	}

     */
}