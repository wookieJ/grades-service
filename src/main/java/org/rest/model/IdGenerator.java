package org.rest.model;

import org.mongodb.morphia.annotations.Entity;

@Entity("ids")
public class IdGenerator {
    private int studentId;
    private int courseId;

    public int getStudentId() {
        return studentId;
    }

    public void setStudentId(int studentId) {
        this.studentId = studentId;
    }

    public int getCourseId() {
        return courseId;
    }

    public void setCourseId(int courseId) {
        this.courseId = courseId;
    }

    public IdGenerator() {
    }

    public IdGenerator(int studentId, int courseId) {
        this.studentId = studentId;
        this.courseId = courseId;
    }

    @Override
    public String toString() {
        return "IdGenerator{" + "studentId=" + studentId + ", courseId=" + courseId + '}';
    }
}
