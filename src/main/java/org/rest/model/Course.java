package org.rest.model;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class Course {
    private long id;
    private String name;
    private String lecturer;

    private static long idNumber = 0;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLecturer() {
        return lecturer;
    }

    public void setLecturer(String lecturer) {
        this.lecturer = lecturer;
    }

    public Course(String name, String lecturer) {
        this.id = idNumber++;
        this.name = name;
        this.lecturer = lecturer;
    }

    public Course(){}

    @Override
    public String toString() {
        return "Course{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", lecturer='" + lecturer + '\'' +
                '}';
    }
}
