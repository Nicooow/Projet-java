package ade;

import net.fortuna.ical4j.data.CalendarBuilder;
import net.fortuna.ical4j.data.ParserException;
import net.fortuna.ical4j.model.Calendar;
import net.fortuna.ical4j.model.Component;
import net.fortuna.ical4j.model.component.VEvent;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

/**
 * Class to manage ADE calendar
 */
public class Ade {
    private String url;
    private HashMap<String, Classroom> classrooms;

    /**
     * Constructor of the class
     * @param url url of the calendar to parse
     */
    public Ade(String url) {
        this.url = url;
        this.classrooms = new HashMap<>();
    }

    private static String readAll(BufferedReader rd) throws IOException {
        StringBuilder sb = new StringBuilder();
        int cp;
        while ((cp = rd.read()) != -1) {
            sb.append((char) cp);
        }
        return sb.toString();
    }

    public static String readFileFromUrl(String url) throws IOException {
        InputStream is = new URL(url).openStream();
        try {
            BufferedReader rd = new BufferedReader(new InputStreamReader(is, Charset.forName("UTF-8")));
            String text = readAll(rd);
            return text;
        } finally {
            is.close();
        }
    }

    /**
     * Load the URL and parse the file
     * @throws IOException
     * @throws ParserException
     */
    public void loadCal() throws IOException, ParserException {
        InputStream is;
        is = new URL(this.url).openStream();
        CalendarBuilder builder = new CalendarBuilder();
        Calendar calendar = builder.build(is);

        for (Iterator i = calendar.getComponents(Component.VEVENT).iterator(); i.hasNext();) {
            VEvent event = (VEvent) i.next();

            String[] classroomsNames = event.getLocation().getValue().split(",");
            for (String classroomName : classroomsNames){
                if(classroomName.contains("("))
                    classroomName = classroomName.substring(0, classroomName.indexOf("(")-1);

                if(!classrooms.containsKey(classroomName)){
                    classrooms.put(classroomName, new Classroom(classroomName));
                }

                Course course = new Course(classrooms.get(classroomName), event.getSummary().getValue(), event.getDescription().getValue(), event.getStartDate().getDate(), event.getEndDate().getDate());
                classrooms.get(classroomName).addCourse(course);
            }
        }

    }

    public HashMap<String, Classroom> getClassrooms() {
        return classrooms;
    }

    public void setClassrooms(HashMap<String, Classroom> classrooms) {
        this.classrooms = classrooms;
    }
}
