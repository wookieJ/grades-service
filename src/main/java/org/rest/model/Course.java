package org.rest.model;

import org.glassfish.jersey.linking.InjectLink;
import org.glassfish.jersey.linking.InjectLinks;
import org.rest.data.Data;

import javax.ws.rs.core.Link;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import java.util.List;

@XmlRootElement
public class Course
{
    @InjectLinks({
            @InjectLink(value = "/courses/{id}", rel = "self"),
            @InjectLink(resource = org.rest.endpoints.CoursesEndpoint.class, rel = "parent")}
    )
    @XmlElement(name = "link")
    @XmlElementWrapper(name = "links")
    @XmlJavaTypeAdapter(Link.JaxbAdapter.class)
    List<Link> links;

    private int id;
    private String name;
    private String lecturer;
    private static int idNumber = 0;

    public int getId()
    {
        return id;
    }

    public void setId(int id)
    {
        this.id = id;
    }

    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public String getLecturer()
    {
        return lecturer;
    }

    public void setLecturer(String lecturer)
    {
        this.lecturer = lecturer;
    }

    public Course(String name, String lecturer)
    {
        // generating new not busy id
        while (Data.getCourseById(idNumber) != null)
        {
            idNumber++;
        }
        this.id = idNumber++;
        this.name = name;
        this.lecturer = lecturer;
    }

    public Course(Course course)
    {
        // checing if course id is not busy
        if (Data.getCourseById(course.getId()) == null)
            this.id = course.getId();
        else
        {
            // generating new not busy id
            while (Data.getCourseById(idNumber) != null)
            {
                idNumber++;
            }
            this.id = idNumber++;
        }

        this.name = course.getName();
        this.lecturer = course.getLecturer();
    }

    public Course()
    {
    }

    @Override
    public String toString()
    {
        return "Course{" + "id=" + id + ", name='" + name + '\'' + ", lecturer='" + lecturer + '\'' + '}';
    }
}
