package ade;

import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class Classroom {
    private String name;
    private ArrayList<Course> courses;

    public Classroom(String name) {
        this.name = name;
        this.courses = new ArrayList<>();
    }

    public Course getCurrentCourse(){
        Date now = new Date(1655284514l*1000);

        for (Course c : courses){
            if(now.after(c.getStartDate()) && now.before(c.getEndDate()))
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
