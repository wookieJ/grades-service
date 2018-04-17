package org.rest.model;

import javax.xml.bind.annotation.XmlRootElement;
import java.util.Date;

@XmlRootElement
public class Grade
{
    private int id;
    private float value;
    private Date date;
    private Course course;

    private static int idNumber = 0;

    public int getId()
    {
        return id;
    }

    public void setId(int id)
    {
        this.id = id;
    }

    public float getValue()
    {
        return value;
    }

    public void setValue(float value)
    {
        this.value = (float) (0.5 * Math.round(value * 2));
    }

    public Date getDate()
    {
        return date;
    }

    public void setDate(Date date)
    {
        this.date = date;
    }

    public Course getCourse()
    {
        return course;
    }

    public void setCourse(Course course)
    {
        this.course = course;
    }

    public Grade(float value, Date date, Course course)
    {
        this.id = idNumber++;
        this.value = value;
        this.date = date;
        this.course = course;
    }

    public Grade(Grade grade)
    {
        this.id = idNumber++;
        this.value = grade.getValue();
        this.date = grade.getDate();
        this.course = grade.getCourse();
    }

    public Grade()
    {
    }

    @Override
    public String toString()
    {
        return "Grade{" + "id=" + id + ", value=" + value + ", date=" + date + ", course=" + course + '}';
    }
}