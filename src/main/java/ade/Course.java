package ade;

import java.util.Date;

public class Course {
    private Classroom location;
    private String summary;
    private String description;
    private Date startDate;
    private Date endDate;

    public Course(Classroom location, String summary, String description, Date startDate, Date endDate) {
        this.location = location;
        this.summary = summary;
        this.description = description;
        this.startDate = startDate;
        this.endDate = endDate;
    }

    public Classroom getLocation() {
        return location;
    }

    public void setLocation(Classroom location) {
        this.location = location;
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }
}
