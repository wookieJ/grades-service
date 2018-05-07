package org.rest.endpoints;

import jersey.repackaged.com.google.common.collect.Lists;
import org.rest.model.Course;
import org.rest.model.Student;
import org.rest.service.CourseService;
import org.rest.service.StudentService;

import javax.ws.rs.*;
import javax.ws.rs.core.GenericEntity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;
import java.util.stream.Collectors;

@Path("/courses")
public class CoursesEndpoint {

    /**
     * Endpoint which returns list of courses. It is possible to filter list by lecturer name.
     *
     * @param lecturer lecturer name we want to filter list by.
     * @return list of all courses if lecturer parameter is not set and filtered list otherwise.
     */
    @GET
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public Response getAllCourses(@QueryParam("lecturer") String lecturer) {
        CourseService courseService = new CourseService();
        List<Course> courses = courseService.getAllCourses();

//        // checking if courses list is empty
//        if (courses == null || courses.size() == 0)
//            return Response.status(Response.Status.NOT_FOUND).entity("No courses").build();

        // filtering by lecturer name
        if (lecturer != null) {
            courses = courses.stream().filter(cr -> cr.getLecturer().equals(lecturer)).collect(Collectors.toList());
        }

        GenericEntity<List<Course>> entity = new GenericEntity<List<Course>>(Lists.newArrayList(courses)) {
        };
        // creating xml response
        return Response.status(Response.Status.OK).entity(entity).build();
    }

    /**
     * Endpoint that allows getting course by id.
     *
     * @param id unique value of course id we want to get.
     * @return response of successful operation.
     */
    @GET
    @Path("/{id}")
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public Response getCourseById(@PathParam("id") int id) {
        // getting course by id
        CourseService courseService = new CourseService();
        Course course = courseService.getCourse(id);

        // checking if course exists
        if (course == null)
            return Response.status(Response.Status.NOT_FOUND).entity("Not found").build();

        // creating xml response
        return Response.status(Response.Status.OK).entity(course).build();
    }

    /**
     * Endpoint that allows adding course to service.
     *
     * @param course new course we want to add.
     * @return response of successful operation
     */
    @POST
    @Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public Response addCourse(Course course) {
        if (course != null) {
            CourseService courseService = new CourseService();
            course = courseService.addCourse(course);

            String result = "Course " + course + " added!\n";

            // creating response
            return Response.status(Response.Status.CREATED).header("Location", "/courses/" + course.getId()).entity(result).build();
        } else
            return Response.status(Response.Status.NO_CONTENT).entity("Courses cannot be null!").build();
    }

    /**
     * Endpoint that allows updating course which exists in service.
     *
     * @param course course we want to be refreshed version of existing course in service.
     * @param id     unique course's id.
     * @return response of successful operation.
     */
    @PUT
    @Path("/{id}")
    @Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public Response updateCourse(Course course, @PathParam("id") int id) {
        // getting course by it's index
        CourseService courseService = new CourseService();
        Course searchedCourse = courseService.getCourse(id);

        // checking if course exists
        if (searchedCourse == null)
            return Response.status(Response.Status.NOT_FOUND).entity("Not found").build();

        course.setId(id);
        // updating course
        boolean status = courseService.updateCourse(course);
        if (status == true) {
            String result = "Course " + course + " updated!";

            // creating response
            return Response.status(Response.Status.OK).entity(result).build();
        } else
            return Response.status(Response.Status.CONFLICT).entity("Error, not updated").build();
    }

    /**
     * Endpoint that allows deleting existing course from service.
     *
     * @param id unique course's id we want to delete.
     * @return response of successful operation.
     */
    @DELETE
    @Path("/{id}")
    public Response deleteCourse(@PathParam("id") int id) {
        // getting course by it's index
        CourseService courseService = new CourseService();
        Course searchedCourse = courseService.getCourse(id);

        // checking if course exists
        if (searchedCourse == null) {
            return Response.status(Response.Status.NOT_FOUND).entity("Not found").build();
        }

        // updating course
        StudentService studentService = new StudentService();
        List<Student> students = studentService.getAllStudents();

        // TODO - Zrobić na streamach ładniej lub zapytanie do każdej oceny na query!
        // Deleting grades with deleting course
        if (students != null && !students.isEmpty()) {
//            List<Student> filteredStudent = new ArrayList<>();
            for (Student st : students) {
                for (int i = 0; i < st.getGrades().size(); i++) {//Grade gr: st.getGrades()) {
                    if (st.getGrades().get(i).getCourse().getId() == id) {
//                        filteredStudent.add(st);
                        System.out.println(st.getGrades().get(i));
                        st.removeStudentGradeById(st.getGrades().get(i).getId());
                    }
                }
                studentService.updateStudent(st, true);
            }
        }

        boolean status = courseService.deleteCourse(id);
        if (status == true) {
            String result = "Course " + searchedCourse + " deleted!";

            // creating response
            return Response.status(Response.Status.OK).entity(result).build();
        } else
            return Response.status(Response.Status.CONFLICT).entity("Error, not deleted").build();
    }
}
