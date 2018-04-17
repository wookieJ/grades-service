package org.rest.model;

import org.glassfish.jersey.linking.InjectLink;
import org.glassfish.jersey.linking.InjectLinks;
import org.rest.data.Data;

import javax.ws.rs.core.Link;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@XmlRootElement
public class Student
{
    @InjectLinks({
            @InjectLink(value = "/students/{index}", rel = "self"),
            @InjectLink(resource = org.rest.endpoints.StudentsEndpoint.class, rel = "parent")})
    @XmlElement(name = "link")
    @XmlElementWrapper(name = "links")
    @XmlJavaTypeAdapter(Link.JaxbAdapter.class)
    List<Link> links;

    private int index;

    private String firstName;
    private String lastName;
    private Date birthday;
    private List<Grade> grades;
    private static int idNumber = 0;

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

    public Student(String firstName, String lastName, Date birthday, List<Grade> grades)
    {
        this.index = idNumber++;
        this.firstName = firstName;
        this.lastName = lastName;
        this.birthday = birthday;
        this.grades = grades;
    }

    public Student(Student student)
    {
        this.index = idNumber++;
        this.firstName = student.getFirstName();
        this.lastName = student.getLastName();
        this.birthday = student.getBirthday();
        this.grades = student.getGrades();
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
     *
     * @param grade grade we want to add to student grades list
     */
    public void addGrade(Grade grade)
    {
        getGrades().add(grade);
    }

    /**
     * Getting student grade by id
     *
     * @param id grade's id
     * @return grade with searching id
     */
    public Grade getGradeById(int id)
    {
        Optional<Grade> grade = getGrades().stream().filter(c -> c.getId() == id).findFirst();
        return grade.orElse(null);
    }

    /**
     * Updating student's grade if exists
     *
     * @param grade new grade
     * @return true if operation succeeded, false otherwise
     */
    public boolean updateStudentGrade(Grade grade)
    {
        int idx = getGrades().indexOf(getGradeById(grade.getId()));
        if (idx != -1)
        {
            getGrades().set(idx, grade);
            return true;
        } else
            return false;
    }

    /**
     * Removing student's grade
     *
     * @param id id of removing grade
     * @return true if operation succeeded, false otherwise
     */
    public boolean removeStudentGradeById(int id)
    {
        return getGrades().remove(getGradeById(id));
    }
}
