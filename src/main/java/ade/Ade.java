package ade;

import net.fortuna.ical4j.data.CalendarBuilder;
import net.fortuna.ical4j.data.ParserException;
import net.fortuna.ical4j.model.Calendar;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.nio.charset.Charset;

public class Ade {
    private String url;

    public Ade(String url) {
        this.url = url;
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

    public void loadCal() throws IOException, ParserException {
        //System.out.println(readFileFromUrl(this.url));
        InputStream is;
        is = new URL(this.url).openStream();
        CalendarBuilder builder = new CalendarBuilder();
        Calendar calendar = builder.build(is);

        System.out.println(calendar);
    }
}
