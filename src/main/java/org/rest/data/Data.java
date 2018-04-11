package org.rest.data;

import org.rest.model.Course;
import org.rest.model.Grade;
import org.rest.model.Student;

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

    // TODO - Utworzyć interfejs generyczny CRUD

    /**
     * Getting student by index value.
     *
     * @param index unique index value
     * @return student or null if doesn't exist
     */
    public static Student getStudentByIndex(int index)
    {
        Optional<Student> student = getStudents().stream().filter(b -> b.getIndex() != 0 && b.getIndex() == index).findFirst();
        return student.orElse(null);
    }

    /**
     * Adding new student to students list.
     *
     * @param student new student
     * @return true if operation succeeded, false otherwise
     */
    public static boolean addStudent(Student student)
    {
        return students.add(student);
    }

    /**
     * Updating student in array.
     *
     * @param student new student to update
     * @return true if operation succeeded, false otherwise
     */
    public static boolean updateStudent(Student student)
    {
        int index = getStudents().indexOf(getStudentByIndex(student.getIndex()));
        if (index != -1)
        {
            // prevent from adding null list
            if (student.getGrades() == null)
                student.setGrades(new ArrayList<>());
            getStudents().set(index, student);
            return true;
        }

        return false;
    }

    /**
     * Removing student from list by index number.
     *
     * @param index student's index number
     * @return true if operation succeeded, false otherwise
     */
    public static boolean removeStudentByIndex(int index)
    {
        if (getStudentByIndex(index) != null)
        {
            getStudents().remove(getStudentByIndex(index));
            return true;
        } else
            return false;
    }

    /**
     * Getting grade by id.
     *
     * @param id id of looking grade
     * @return grade if exists or null if doesn't
     */
    public static Grade getGradeById(int id)
    {
        Optional<Grade> grade = getGrades().stream().filter(g -> g.getId() == id).findFirst();
        return grade.orElse(null);
    }

    /**
     * Adding new grade to grades list
     *
     * @param grade new grade to add
     * @return true if operation succeeded, false otherwise
     */
    public static boolean addGrade(Grade grade)
    {
        return getGrades().add(grade);
    }

    /**
     * Updating grade.
     *
     * @param grade new grade
     * @return true if operation succeeded, false otherwise
     */
    public static boolean updateGrade(Grade grade)
    {
        int index = getGrades().indexOf(getGradeById(grade.getId()));
        if (index != -1)
        {
            getGrades().set(index, grade);
            return true;
        }

        return false;
    }

    /**
     * Removing grade if exists.
     *
     * @param id id of removing grade
     * @return true if operation succeeded, false otherwise
     */
    public static boolean removeGradeById(int id)
    {
        if (getGradeById(id) != null)
        {
            getGrades().remove(getGradeById(id));
            return true;
        } else
            return false;
    }

    /**
     * Getting course by id.
     *
     * @param id id of looking course
     * @return course if exists or null if doesn't
     */
    public static Course getCourseById(int id)
    {
        Optional<Course> course = getCourses().stream().filter(c -> c.getId() == id).findFirst();
        return course.orElse(null);
    }

    /**
     * Adding new course to courses list
     *
     * @param course new course to add
     * @return true if operation succeeded, false otherwise
     */
    public static boolean addCourse(Course course)
    {
        return getCourses().add(course);
    }

    /**
     * Updating course.
     *
     * @param course new course
     * @param id     id of updating course
     * @return true if operation succeeded, false otherwise
     */
    public static boolean updateCourse(Course course, int id)
    {
        int index = getCourses().indexOf(getCourseById(id));
        if (index != -1)
        {
            getCourses().set(index, course);
            return true;
        }

        return false;
    }

    /**
     * Removing course if exists.
     *
     * @param id id of removing course
     * @return true if operation succeeded, false otherwise
     */
    public static boolean removeCourseById(int id)
    {
        if (getCourseById(id) != null)
        {
            getCourses().remove(getCourseById(id));
            return true;
        } else
            return false;
    }

    /**
     * Removing student grade
     *
     * @param id      id of removing grade
     * @param student student from whom we want delete the grade
     * @return true if operation succeeded, false otherwise
     */
    public static boolean removeStudentGradeByIndex(Student student, int id)
    {
        if (getGradeById(id) != null)
        {
            student.getGrades().remove(getGradeById(id));
            return true;
        } else
            return false;
    }
}
