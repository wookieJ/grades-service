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

@Path("/students/{index}/grades")
public class GradesEndpoint
{
    @PathParam("index")
    private int index;

    @GET
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public Response getStudentGrades()
    {
        // getting student by it's index
        Student searchedStudent = Data.getStudentByIndex(index);

        // checking if student exists
        if (searchedStudent == null)
            return Response.status(Response.Status.NOT_FOUND).entity("Not found").build();

        // creating list of student's grades
        GenericEntity<List<Grade>> entity = new GenericEntity<List<Grade>>(Lists.newArrayList(searchedStudent.getGrades()))
        {
        };
        // creating xml response
        return Response.status(Response.Status.OK).entity(entity).build();
    }

    @GET
    @Path("/{id}")
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public Response getStudentGrade(@PathParam("id") int id)
    {
        // getting student by it's index
        Student searchedStudent = Data.getStudentByIndex(index);

        // checking if student exists
        if (searchedStudent == null)
        {
            return Response.status(Response.Status.NOT_FOUND).entity("Student not found").build();
        }

        // getting student's grade by it's id
        Grade searchedGrade = searchedStudent.getGradeById(id);

        // checking if student's grade exists
        if (searchedGrade == null)
        {
            return Response.status(Response.Status.NOT_FOUND).entity("Grade not found").build();
        }

        // creating xml response
        return Response.status(Response.Status.OK).entity(searchedGrade).build();
    }

    @POST
    @Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public Response addStudentGrades(Grade grade)
    {
        // checking if grade is a null
        if (grade != null)
        {
            // getting student by it's index
            Student searchedStudent = Data.getStudentByIndex(index);

            // checking if student exists
            if (searchedStudent == null)
                return Response.status(Response.Status.NOT_FOUND).entity("Student not found").build();

            Grade newGrade = new Grade(grade);
            newGrade.setStudentIndex(searchedStudent.getIndex());
            searchedStudent.addGrade(newGrade);
            String result = "Student grade " + newGrade + " added!\n";

            // creating response
            return Response.status(Response.Status.CREATED).header("Location", "/grades/" + newGrade.getId()).entity(result).build();
        } else
            return Response.status(Response.Status.NO_CONTENT).entity("Grade cannot be null!").build();
    }

    @PUT
    @Path("/{id}")
    @Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public Response addStudentGrade(Grade grade, @PathParam("id") int id)
    {
        // checking if grade is a null
        if (grade != null)
        {
            // getting student by it's index
            Student searchedStudent = Data.getStudentByIndex(index);

            // checking if student exists
            if (searchedStudent == null)
            {
                return Response.status(Response.Status.NOT_FOUND).entity("Student not found").build();
            }

            // getting student's grade by it's id
            Grade searchedGrade = searchedStudent.getGradeById(id);

            // checking if student's grade exists
            if (searchedGrade == null)
            {
                return Response.status(Response.Status.NOT_FOUND).entity("Grade not found").build();
            }

            grade.setId(id);
            grade.setStudentIndex(searchedStudent.getIndex());
            searchedStudent.updateStudentGrade(grade);
            String result = "Student grade " + grade + " updated!";

            // creating response
            return Response.status(Response.Status.CREATED).entity(result).build();
        } else
            return Response.status(Response.Status.NOT_FOUND).entity("Grade cannot be null!").build();
    }

    @DELETE
    @Path("/{id}")
    public Response deleteStudentGrade(@PathParam("id") int id)
    {
        // getting student by it's index
        Student searchedStudent = Data.getStudentByIndex(index);

        // checking if student exists
        if (searchedStudent == null)
            return Response.status(Response.Status.NOT_FOUND).entity("Student not found").build();

        Grade searchedGrade = searchedStudent.getGradeById(id);
        // checking if grade exists
        if (searchedGrade == null)
            return Response.status(Response.Status.NOT_FOUND).entity("Grade not found").build();

        // removing student grade
        searchedStudent.removeStudentGradeById(id);
        String result = "Student grade " + searchedGrade + " deleted!";

        // creating response
        return Response.status(Response.Status.OK).entity(result).build();
    }
}
