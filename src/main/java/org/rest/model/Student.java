package org.rest.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.bson.types.ObjectId;
import org.glassfish.jersey.linking.InjectLink;
import org.glassfish.jersey.linking.InjectLinks;
import org.mongodb.morphia.annotations.Entity;
import org.mongodb.morphia.annotations.Id;
import org.mongodb.morphia.annotations.Indexed;

import javax.ws.rs.core.Link;
import javax.xml.bind.annotation.*;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@XmlRootElement
@Entity("students")
public class Student {
    @Id
    @XmlTransient
//    @XmlJavaTypeAdapter(ObjectIdJaxbAdapter.class)
    private ObjectId id;

    @Indexed(name = "index", unique = true)
    private int index;
    private String firstName;
    private String lastName;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd", timezone = "CET")
    private Date birthday;

    private List<Grade> grades;

    @InjectLinks({@InjectLink(value = "/students/{index}", rel = "self"), @InjectLink(resource = org.rest.endpoints.StudentsEndpoint.class, rel = "parent"), @InjectLink(resource = org.rest.endpoints.GradesEndpoint.class, rel = "grades")})
    @XmlElement(name = "link")
    @XmlElementWrapper(name = "links")
    @XmlJavaTypeAdapter(Link.JaxbAdapter.class)
    List<Link> links;

    @XmlTransient
    public ObjectId getId() {
        return id;
    }

    public void setId(ObjectId id) {
        this.id = id;
    }

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
        for(Grade g: grades){
            g.setStudentIndex(index);
        }
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public Date getBirthday() {
        return birthday;
    }

    public void setBirthday(Date birthday) {
        this.birthday = birthday;
    }

    @XmlElement(name = "grade")
    @XmlElementWrapper(name = "grades")
    @JsonProperty("grades")
    public List<Grade> getGrades() {
        return grades;
    }

    public void setGrades(List<Grade> grades) {
        this.grades = grades;
    }

    public Student(String firstName, String lastName, Date birthday, List<Grade> grades) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.birthday = birthday;
        this.grades = grades;
    }

    // TODO - dodanie id oceny
    public Student(Student student) {
        this.firstName = student.getFirstName();
        this.lastName = student.getLastName();
        this.birthday = student.getBirthday();
        this.grades = student.getGrades();
    }

    public Student() {
        this.grades = new ArrayList<>();
    }

    @Override
    public String toString() {
        return "Student{" + "index=" + index + ", firstName='" + firstName + '\'' + ", lastName='" + lastName + '\'' + ", birthday=" + birthday + ", grades=" + grades + '}';
    }

    /**
     * Adding new grade with new id based on posted grade
     *
     * @param grade grade we want to add to student grades list
     */
    public void addGrade(Grade grade) {
        getGrades().add(grade);
    }

    /**
     * Getting student grade by id
     *
     * @param id grade's id
     * @return grade with searching id
     */
    public Grade getGradeById(int id) {
        Optional<Grade> grade = getGrades().stream().filter(c -> c.getId() == id).findFirst();
        return grade.orElse(null);
    }

    /**
     * Updating student's grade if exists
     *
     * @param grade new grade
     * @return true if operation succeeded, false otherwise
     */
    public boolean updateStudentGrade(Grade grade) {
        int idx = getGrades().indexOf(getGradeById(grade.getId()));
        if (idx != -1) {
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
    public boolean removeStudentGradeById(int id) {
        System.out.println("Removing: " + id);
        return getGrades().remove(getGradeById(id));
    }
}
