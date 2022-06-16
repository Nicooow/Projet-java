package ADEEsiremModels;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import ADEEsirem.ADEEsirem;
import ade.Classroom;
import ade.Course;
import org.json.simple.JSONArray;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class MapPanel extends JPanel implements MouseListener{
    HashMap<Polygon, String> polys;
    String level;
    ADEEsirem app;

    public MapPanel(JPanel parent, ADEEsirem app, String level) {
        super();
        setPreferredSize(parent.getPreferredSize());
        setBackground(Color.white);
        addMouseListener(this);
        this.level = level;
        this.app = app;
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        JSONParser jsonParser = new JSONParser();
        Graphics2D g2 = (Graphics2D) g;
        polys = new HashMap();

        float ratioHeight = (this.getHeight()-100) / 2527f;
        float ratioWidth = (this.getWidth()-100) / 5712f;

        int fontSize = Math.round(14*this.getWidth()/(5712f/4f));
        Font font = new Font("Arial", Font.BOLD, fontSize);
        Font fontHeart = new Font("Arial", Font.BOLD, Math.round(fontSize*1.4f));
        Font subFont = new Font("Arial", Font.PLAIN, Math.round(fontSize*0.75f));

        try{
            FileReader reader =  new FileReader(app.getClass().getResource("../plans/"+this.level+".json").getPath());
            Object obj = jsonParser.parse(reader);

            JSONArray rooms = (JSONArray) obj;

            for (Object _room : rooms.toArray()){
                ArrayList<Object> roomInfo = (ArrayList<Object>) _room;
                String roomName = (String) roomInfo.get(0);
                Course currentCourse = null;
                if(app.getAde().getClassrooms().containsKey(roomName)){
                    Classroom classroom = app.getAde().getClassrooms().get(roomName);
                    currentCourse = classroom.getCurrentCourse();
                }
                ArrayList<ArrayList<Long>> roomPoints = (ArrayList<ArrayList<Long>>) roomInfo.get(1);
                if(roomPoints.size()>3){
                    int[] xPoly = new int[roomPoints.size()];
                    int[] yPoly = new int[roomPoints.size()];
                    int sumX = 0;
                    int sumY = 0;

                    int i = 0;
                    for (Object _wall : roomPoints.toArray()){
                        ArrayList<Long> wall = (ArrayList<Long>) _wall;
                        xPoly[i] = Math.round(wall.get(0).intValue() * ratioWidth);
                        yPoly[i] = Math.round(wall.get(1).intValue() * ratioHeight);
                        sumX += xPoly[i];
                        sumY += yPoly[i];
                        i++;
                    }

                    Polygon poly = new Polygon(xPoly, yPoly, roomPoints.size());
                    polys.put(poly, roomName);

                    if(roomName.equals("$corridor")){
                        g.setColor(new Color(0xececec));
                        g.fillPolygon(poly);
                    }else {
                        g.setColor(new Color(0xe0e0e0));
                        g.fillPolygon(poly);
                        g2.setStroke(new BasicStroke(2));
                        if(currentCourse != null){
                            g.setColor(Color.RED);
                            g.drawPolygon(poly);
                            this.drawCenteredString(g2, roomName, new Rectangle(sumX / roomPoints.size() - 100, sumY / roomPoints.size() - 110, 200, 200), font);
                            this.drawCenteredString(g2, currentCourse.getSummary(), new Rectangle(sumX / roomPoints.size() - 100, (sumY / roomPoints.size() - 110) + g2.getFontMetrics(font).getHeight(), 200, 200), subFont);
                        }else{
                            g.setColor(Color.BLUE);
                            g.drawPolygon(poly);
                            this.drawCenteredString(g2, roomName, new Rectangle(sumX / roomPoints.size() - 100, sumY / roomPoints.size() - 100, 200, 200), font);
                        }
                        if(app.getFavoriteClassroom().containsClassroom(roomName)){
                            g.setColor(Color.MAGENTA);
                            g.setFont(fontHeart);
                            if(currentCourse != null)
                                g2.drawString("♥", (sumX / roomPoints.size())-6, (sumY / roomPoints.size())-20);
                            else
                                g2.drawString("♥", (sumX / roomPoints.size())-6, (sumY / roomPoints.size())-11);
                        }
                    }

                }
            }

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    public void classroomClicked(String classroom){
        FavoritesClassroom f = app.getFavoriteClassroom();
        if(f.containsClassroom(classroom))
            f.removeClassroom(classroom);
        else
            f.addClassroom(classroom);
        this.updateUI();
    }

    /**
     * Draw a String centered in the middle of a Rectangle.
     *
     * @param g The Graphics instance.
     * @param text The String to draw.
     * @param rect The Rectangle to center the text in.
     */
    public void drawCenteredString(Graphics g, String text, Rectangle rect, Font font) {
        // Get the FontMetrics
        FontMetrics metrics = g.getFontMetrics(font);
        // Determine the X coordinate for the text
        int x = rect.x + (rect.width - metrics.stringWidth(text)) / 2;
        // Determine the Y coordinate for the text (note we add the ascent, as in java 2d 0 is top of the screen)
        int y = rect.y + ((rect.height - metrics.getHeight()) / 2) + metrics.getAscent();
        // Set the font
        g.setFont(font);
        // Draw the String
        g.drawString(text, x, y);
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        for (Polygon poly : polys.keySet()){
            if(poly.contains(new Point(e.getX(), e.getY()))){
                String classroom = polys.get(poly);
                if(!(classroom == "" || classroom.startsWith("$")))
                    this.classroomClicked(classroom);
            }
        }
    }

    @Override
    public void mousePressed(MouseEvent e) {}

    @Override
    public void mouseReleased(MouseEvent e) {}

    @Override
    public void mouseEntered(MouseEvent e) {}

    @Override
    public void mouseExited(MouseEvent e) {}
}