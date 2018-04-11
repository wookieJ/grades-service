package org.rest.model;

import javax.xml.bind.annotation.XmlRootElement;
import java.util.Date;
import java.util.List;

@XmlRootElement
public class Student
{
    private int index;
    private String firstName;
    private String lastName;
    private Date birthday;
    private List<Grade> grades;

    public int getIndex()
    {
        return index;
    }

    public void setIndex(int index)
    {
        this.index = index;
    }

    public String getFirstName()
    {
        return firstName;
    }

    public void setFirstName(String firstName)
    {
        this.firstName = firstName;
    }

    public String getLastName()
    {
        return lastName;
    }

    public void setLastName(String lastName)
    {
        this.lastName = lastName;
    }

    public Date getBirthday()
    {
        return birthday;
    }

    public void setBirthday(Date birthday)
    {
        this.birthday = birthday;
    }

    public List<Grade> getGrades()
    {
        return grades;
    }

    public void setGrades(List<Grade> grades)
    {
        this.grades = grades;
    }

    public Student(int index, String firstName, String lastName, Date birthday, List<Grade> grades)
    {
        this.index = index;
        this.firstName = firstName;
        this.lastName = lastName;
        this.birthday = birthday;
        this.grades = grades;
    }

    public Student()
    {
    }

    @Override
    public String toString()
    {
        return "Student{" + "index=" + index + ", firstName='" + firstName + '\'' + ", lastName='" + lastName + '\'' + ", birthday=" + birthday + ", grades=" + grades + '}';
    }

    /**
     * Adding new grade with new id based on posted grade
     * @param grade grade we want to add to student grades list
     */
    public void addGrade(Grade grade)
    {
        getGrades().add(grade);
    }
}
