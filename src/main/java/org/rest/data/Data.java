package org.rest.data;

import org.rest.model.Course;
import org.rest.model.Grade;
import org.rest.model.Student;

import java.awt.print.Book;
import java.util.*;

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

    /**
     * Adding new student to students list
     *
     * @param student new student
     * @return true if operation succeeded, false otherwise
     */
    public static boolean addStudent(Student student)
    {
        return students.add(student);
    }

    /**
     * Loading example data to lists
     */
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

        students.add(new Student(123456, "Jan", "Kowalski", getDate(1995, 01, 03), student1Grades));
        students.add(new Student(654321, "Mateusz", "Nowak", getDate(1995, 02, 17), student2Grades));
        students.add(new Student(346845, "Robert", "Kot", getDate(1995, 12, 01), student3Grades));
    }

    private static Date getDate(int year, int month, int day)
    {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.YEAR, year);
        calendar.set(Calendar.MONTH, month);
        calendar.set(Calendar.DAY_OF_MONTH, day);
        Date date = calendar.getTime();

        return date;
    }

    /**
     * Getting student by index value
     * @param index unique index value
     * @return student or null if doesn't exist
     */
    public static Student getStudentByIndex(int index)
    {
        Optional<Student> student = getStudents().stream().filter(b -> b.getIndex() != 0 && b.getIndex() == index).findFirst();
        return student.orElse(null);
    }

    /**
     * Updating student in array
     *
     * @param student new student to update
     */
    public static void updateStudent(Student student)
    {
        int index = getStudents().indexOf(getStudentByIndex(student.getIndex()));
        getStudents().set(index, student);
    }

    /**
     * Removing student from list by index number
     * @param index student's index number
     * @return true if operation succeeded, false otherwise
     */
    public static boolean removeStudentByIndex(int index)
    {
        if(getStudentByIndex(index) != null)
        {
            getStudents().remove(getStudentByIndex(index));
            return true;
        }
        else
            return false;
    }
}
