package org.rest.model;

import org.bson.types.ObjectId;
import org.glassfish.jersey.linking.InjectLink;
import org.glassfish.jersey.linking.InjectLinks;
import org.mongodb.morphia.annotations.Embedded;
import org.mongodb.morphia.annotations.Entity;
import org.mongodb.morphia.annotations.Id;
import org.mongodb.morphia.annotations.Reference;

import javax.ws.rs.core.Link;
import javax.xml.bind.annotation.*;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import java.util.Date;
import java.util.List;

@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
@Embedded
@Entity("grades")
public class Grade {
    @InjectLinks({@InjectLink(value = "/students/{studentIndex}/grades/{id}", rel = "self"), @InjectLink(value = "/students/{studentIndex}/grades", rel = "parent")})
    @XmlElement(name = "link")
    @XmlElementWrapper(name = "links")
    @XmlJavaTypeAdapter(Link.JaxbAdapter.class)
    List<Link> links;

    @XmlTransient
    private int studentIndex;

    @Id
    @XmlJavaTypeAdapter(ObjectIdJaxbAdapter.class)
    ObjectId _id;

    private int id;
    private float value;
    // @JsonFormat(shape=JsonFormat.Shape.STRING, pattern="yyyy-MM-dd", timezone="CET")
    private Date date;

    private Course course;
    private static int idNumber = 0;

    @XmlTransient
    public ObjectId get_id() {
        return _id;
    }

    public void set_id(ObjectId _id) {
        this._id = _id;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getStudentIndex() {
        return studentIndex;
    }

    public void setStudentIndex(int studentIndex) {
        this.studentIndex = studentIndex;
    }

    public float getValue() {
        return value;
    }

    public void setValue(float value) {
        this.value = (float) (0.5 * Math.round(value * 2));
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Course getCourse() {
        return course;
    }

    public void setCourse(Course course) {
        this.course = course;
    }

    public Grade(float value, Date date, Course course) {
        this.id = idNumber++;
        this.value = value;
        this.date = date;
        this.course = course;
    }

    public Grade(Grade grade) {
        this.studentIndex = grade.studentIndex;
        this.id = idNumber++;
        this.value = grade.getValue();
        this.date = grade.getDate();
        this.course = grade.getCourse();
    }

    public Grade() {
    }

    @Override
    public String toString() {
        return "Grade{" + "id=" + id + ", studentIndex=" + studentIndex + ", value=" + value + ", date=" + date + ", course=" + "" + '}';
    }
}