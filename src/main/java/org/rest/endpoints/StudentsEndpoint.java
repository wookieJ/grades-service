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

    @GET
    @Path("/{index}/grades")
    @Produces(MediaType.APPLICATION_XML)
    public Response getStudentGrades(@PathParam("index") int index)
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
    @Path("/{index}/grades/{id}")
    @Produces(MediaType.APPLICATION_XML)
    public Response getStudentGrade(@PathParam("index") int index, @PathParam("id") int id)
    {
        // getting student by it's index
        Student searchedStudent = Data.getStudentByIndex(index);

        // getting grade by id
        Grade gradeByIndex = Data.getGradeById(id);

        // checking if student exists and has this grade
        if (searchedStudent == null || gradeByIndex == null || !searchedStudent.getGrades().contains(gradeByIndex))
        {
            return Response.status(Response.Status.NOT_FOUND).entity("Not found").build();
        }

        // creating xml response
        return Response.status(Response.Status.OK).entity(gradeByIndex).build();
    }

    @POST
    @Consumes(MediaType.APPLICATION_XML)
    public Response addStudents(Student[] students)
    {
        // TODO - sprawdzić czy czasem nie istnieje już taki - index musi być unikalny
        // TODO - sprawdzić czy obiekt oceny(a) jest ok
        if (students != null)
        {
            String result = "";

            // adding student to students list
            for (Student student : students)
            {
                Data.addStudent(student);
                result += "Student " + student + " added!\n";
            }
            // creating response
            return Response.status(Response.Status.CREATED).entity(result).build();
        } else
            return Response.status(Response.Status.NO_CONTENT).entity("Students cannot be null!").build();
    }

    @POST
    @Path("/{index}/grades")
    @Consumes(MediaType.APPLICATION_XML)
    public Response addStudentGrades(Grade[] grades, @PathParam("index") int index)
    {
        if (grades != null)
        {
            // getting student by it's index
            Student searchedStudent = Data.getStudentByIndex(index);

            // checking if student exists
            if (searchedStudent == null)
                return Response.status(Response.Status.NOT_FOUND).entity("Not found").build();

            String result = "";
            // adding grades list to student
            for (Grade grade : grades)
            {
                Grade newGrade = new Grade(grade);

                Data.addGrade(newGrade);
                searchedStudent.addGrade(newGrade);
                result += "Student grade " + newGrade + " added!\n";
            }
            // creating response
            return Response.status(Response.Status.CREATED).entity(result).build();
        } else
            return Response.status(Response.Status.NO_CONTENT).entity("Grades cannot be null!").build();
    }

    @POST
    @Path("/{index}/grades/{id}")
    @Consumes(MediaType.APPLICATION_XML)
    public Response addStudentGrade(Grade grade, @PathParam("index") int index, @PathParam("id") int id)
    {
        if (Data.getGradeById(id) == null)
        {
            // getting student by it's index
            Student searchedStudent = Data.getStudentByIndex(index);

            // checking if student exists and has this grade
            if (searchedStudent == null || grade == null)
            {
                return Response.status(Response.Status.NOT_FOUND).entity("Not found").build();
            }

            grade.setId(id);

            Data.addGrade(grade);
            searchedStudent.addGrade(grade);
            String result = "Student grade " + grade + " added!";

            // creating response
            return Response.status(Response.Status.CREATED).entity(result).build();
        }
        else
            return Response.status(Response.Status.METHOD_NOT_ALLOWED).entity("Grade with this id exists!").build();
    }

    @PUT
    @Path("/{index}")
    @Consumes(MediaType.APPLICATION_XML)
    public Response updateStudent(Student student, @PathParam("index") int index)
    {
        // getting student by it's index
        Student searchedStudent = Data.getStudentByIndex(index);

        // checking if student exists
        if (searchedStudent == null)
            return Response.status(Response.Status.NOT_FOUND).entity("Not found").build();

        // checking if we sent empty index number
        if(student.getIndex() == 0)
            student.setIndex(index);

        // updating student
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
            return Response.status(Response.Status.NOT_FOUND).entity("Not found").build();

        // updating student
        Data.removeStudentByIndex(index);
        String result = "Student " + searchedStudent + " deleted!";

        // creating response
        return Response.status(Response.Status.OK).entity(result).build();
    }

    @DELETE
    @Path("/{index}/grades/{id}")
    public Response deleteStudentGrade(@PathParam("index") int index, @PathParam("id") int id)
    {
        // getting student by it's index
        Student searchedStudent = Data.getStudentByIndex(index);
        Grade searchedGrade = Data.getGradeById(id);
        // checking if student exists
        if (searchedStudent == null || searchedGrade == null || !searchedStudent.getGrades().contains(searchedGrade))
            return Response.status(Response.Status.NOT_FOUND).entity("Not found").build();

        // removing student grade
        Data.removeStudentGradeByIndex(searchedStudent, id);
        String result = "Student grade " + searchedStudent + " deleted!";

        // creating response
        return Response.status(Response.Status.OK).entity(result).build();
    }
}