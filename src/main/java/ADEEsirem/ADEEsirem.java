package ADEEsirem;

import ade.Ade;
import ade.Classroom;
import ADEEsiremModels.FavoritesClassroom;
import ADEEsiremModels.MapPanel;
import ADEEsiremModels.FavoritesPanel;
import net.fortuna.ical4j.data.ParserException;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class ADEEsirem {
    private JPanel root;
    private JPanel mapRDJ;
    private JPanel mapRDC;
    private JPanel map1;
    private JPanel favorites;
    private JSlider sliderDate;
    private JLabel dateLabel;
    private JTabbedPane pane;
    private Date now = new Date();
    public static final long TWO_WEEKS_IN_SECONDS = 1210000l;

    private Ade ade;
    private FavoritesClassroom favoriteClassroom;
    private FavoritesPanel favoritesPanel;

    public static void main(String[] args) {
        JFrame frame = new JFrame("ADE Esirem");
        frame.setContentPane(new ADEEsirem().root);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }

    /**
     * Constructor of the application
     */
    public ADEEsirem() {
        ade = new Ade("https://plannings.u-bourgogne.fr/jsp/custom/modules/plannings/anonymous_cal.jsp?data=8241fc3873200214cb5d743179aedd5bbcb40f7fa3414b5f09e49fce06dff818324cfcf2e9e6b435826b8d54c6bff1eef43b49ed91b3cccdb0db0d7caf18783af6f896fd5b10973b27880fda6f957ee06e8504f9d2b878bb66ed3eb8f721b666");
        try {
            ade.loadCal();
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (ParserException e) {
            throw new RuntimeException(e);
        }

        this.favoriteClassroom = new FavoritesClassroom();

        // create the differents tabs
        mapRDJ.setVisible(true);
        mapRDJ.add(new MapPanel(mapRDJ, this, "RDJ"));
        mapRDC.setVisible(false);
        mapRDC.add(new MapPanel(mapRDC, this, "RDC"));
        map1.setVisible(false);
        map1.add(new MapPanel(map1, this, "1"));

        favorites.setVisible(false);
        FavoritesPanel favoritesPanel = new FavoritesPanel(favorites, this);
        favorites.add(favoritesPanel, BorderLayout.CENTER);

        this.setDateLabel(now);

        // when slider move
        sliderDate.addChangeListener(e -> {
            int value = sliderDate.getValue() + 1; // to avoid zero
            long shift = Math.round((TWO_WEEKS_IN_SECONDS*((float)value/1000f) - TWO_WEEKS_IN_SECONDS/2)*1000);

            Date newDate = new Date(now.getTime()+shift);
            Classroom.setFakeDate(newDate);
            this.setDateLabel(newDate);
            this.mapRDJ.updateUI();
            this.mapRDC.updateUI();
            this.map1.updateUI();
            if(pane.getSelectedIndex() == 3)
                favoritesPanel.updateTable();
        });

        // when tab selected change, hide the others tabs
        pane.addChangeListener(e -> {
            mapRDJ.setVisible(pane.getSelectedIndex() == 0);
            mapRDC.setVisible(pane.getSelectedIndex() == 1);
            map1.setVisible(pane.getSelectedIndex() == 2);
            if(pane.getSelectedIndex() == 3)
                favoritesPanel.updateTable();
            favorites.setVisible(pane.getSelectedIndex() == 3);
        });
    }

    /**
     * Show the date formatted on the label
     * @param date date to show
     */
    private void setDateLabel(Date date){
        Locale locale = new Locale("fr", "FR");
        String pattern = "EEEE dd/MM/yyyy HH:mm";
        DateFormat df = new SimpleDateFormat(pattern, locale);
        String dateString = df.format(date);
        dateLabel.setText(dateString);
    }

    public Ade getAde() {
        return ade;
    }

    public void setAde(Ade ade) {
        this.ade = ade;
    }

    public FavoritesClassroom getFavoriteClassroom() {
        return favoriteClassroom;
    }

    public JPanel getRoot() {
        return root;
    }
}
