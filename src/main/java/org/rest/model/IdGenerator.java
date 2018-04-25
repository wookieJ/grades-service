package org.rest.model;

import org.mongodb.morphia.annotations.Entity;

@Entity("ids")
public class IdGenerator {
    private int studentId;
    private int courseId;
    private int gradeId;

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

    public int getGradeId() {
        return gradeId;
    }

    public void setGradeId(int gradeId) {
        this.gradeId = gradeId;
    }

    public IdGenerator() {
    }

    public IdGenerator(int studentId, int courseId, int gradeId) {
        this.studentId = studentId;
        this.courseId = courseId;
        this.gradeId = gradeId;
    }

    @Override
    public String toString() {
        return "IdGenerator{" + "studentId=" + studentId + ", courseId=" + courseId + ", gradeId=" + gradeId + '}';
    }
}
