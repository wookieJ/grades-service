package org.rest.data;

import org.rest.model.Course;
import org.rest.model.Grade;
import org.rest.model.Student;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Data
{
    private static List<Student> students = new ArrayList<>();
    private static List<Grade> grades = new ArrayList<>();
    private static List<Course> courses = new ArrayList<>();

    public static List<Student> getStudents()
    {
        return students;
    }

    public static List<Grade> getGrades()
    {
        return grades;
    }

    public static List<Course> getCourses()
    {
        return courses;
    }

    public static void loadData()
    {
        Course course1 = new Course("Automatyka", "dr Adam Nowicki");
        Course course2 = new Course("Elektronika", "dr Piotr Zieliński");
        Course course3 = new Course("WF", "mgr Alina Buk");

        courses.add(course1);
        courses.add(course2);
        courses.add(course3);

        Grade grade1 = new Grade(4, new Date(), course1);
        Grade grade2 = new Grade(3.5f, new Date(), course1);
        Grade grade3 = new Grade(5, new Date(), course2);

        grades.add(grade1);
        grades.add(grade2);
        grades.add(grade3);

        List<Grade> student1Grades = new ArrayList<>();
        List<Grade> student2Grades = new ArrayList<>();
        List<Grade> student3Grades = new ArrayList<>();

        student1Grades.add(grade1);
        student1Grades.add(grade2);
        student2Grades.add(grade3);

        students.add(new Student(123456, "Jan", "Kowalski", new Date(), student1Grades));
        students.add(new Student(654321, "Mateusz", "Nowak", new Date(), student2Grades));
        students.add(new Student(346845, "Robert", "Kot", new Date(), student3Grades));
    }
}
