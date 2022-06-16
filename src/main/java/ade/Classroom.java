package ade;

import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class Classroom {
    private String name;
    private ArrayList<Course> courses;
    private static Date fakeDate;

    public Classroom(String name) {
        this.name = name;
        this.courses = new ArrayList<>();
    }

    public static void setFakeDate(Date fakeDate) {
        Classroom.fakeDate = fakeDate;
    }

    public Course getCurrentCourse(){
        Date date;
        if(fakeDate == null)
            date = new Date();
        else
            date = Classroom.fakeDate;

        for (Course c : courses){
            if(date.after(c.getStartDate()) && date.before(c.getEndDate()))
                return c;
        }

        return null;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ArrayList<Course> getCourses() {
        return courses;
    }

    public void setCourses(ArrayList<Course> courses) {
        this.courses = courses;
    }

    public void addCourse(Course course){
        this.courses.add(course);
    }
}
