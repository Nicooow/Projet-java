import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.Array;
import java.util.ArrayList;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class MapPanel extends JPanel {
    public MapPanel(JPanel parent) {
        setPreferredSize(parent.getPreferredSize());
        setBackground(Color.white);
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        JSONParser jsonParser = new JSONParser();
        Graphics2D g2 = (Graphics2D) g;

        float ratioHeight = (this.getHeight()-100) / 2527f;
        float ratioWidth = (this.getWidth()-100) / 5712f;

        int fontSize = Math.round(14*this.getWidth()/(5712f/4f));

        try{
           /* BufferedImage image = ImageIO.read(new File(this.getClass().getResource("plans/RDC.png").getPath()));
            g.drawImage(image, 0, 0, null);*/

            FileReader reader =  new FileReader(this.getClass().getResource("plans/RDC.json").getPath());
            Object obj = jsonParser.parse(reader);

            JSONArray rooms = (JSONArray) obj;

            for (Object _room : rooms.toArray()){
                ArrayList<Object> roomInfo = (ArrayList<Object>) _room;
                String roomName = (String) roomInfo.get(0);
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
                    if(roomName.equals("$corridor")){
                        g.setColor(new Color(0xececec));
                        g.fillPolygon(poly);
                    }else {
                        g.setColor(new Color(0xe0e0e0));
                        g.fillPolygon(poly);
                        g.setColor(Color.BLUE);
                        g2.setStroke(new BasicStroke(2));
                        g.drawPolygon(poly);
                        this.drawCenteredString(g2, roomName, new Rectangle(sumX / roomPoints.size() - 100, sumY / roomPoints.size() - 100, 200, 200), new Font("Arial", Font.BOLD, fontSize));
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
}