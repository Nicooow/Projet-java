import javax.swing.*;
import java.awt.*;
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
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        JSONParser jsonParser = new JSONParser();
        float ratioHeight = (this.getHeight()-100) / 2527f;
        float ratioWidth = (this.getWidth()-100) / 5712f;
        int y = this.getHeight();
        int x = this.getWidth();
        //Polygon polyC = new Polygon((int[]) new int[]{10, 10, x-10, x-10}, (int[]) new int[]{10, y-10, y-10, 10}, 4);
        //g.setColor(Color.RED);
        //g.drawPolygon(polyC);
        try{
            FileReader reader =  new FileReader(this.getClass().getResource("plans/RDC.json").getPath());
            Object obj = jsonParser.parse(reader);

            JSONArray rooms = (JSONArray) obj;

            for (Object _room : rooms.toArray()){
                ArrayList<ArrayList<Long>> room = (ArrayList<ArrayList<Long>>) _room;
                if(room.size()>3){
                    int[] xPoly = new int[room.size()];
                    int[] yPoly = new int[room.size()];

                    int i = 0;
                    for (Object _wall : room.toArray()){
                        ArrayList<Long> wall = (ArrayList<Long>) _wall;
                        xPoly[i] = Math.round(wall.get(0).intValue() * ratioWidth);
                        yPoly[i] = Math.round(wall.get(1).intValue() * ratioHeight);
                        i++;
                    }

                    Polygon poly = new Polygon(xPoly, yPoly, room.size());
                    g.setColor(Color.BLUE);
                    g.drawPolygon(poly);
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
}