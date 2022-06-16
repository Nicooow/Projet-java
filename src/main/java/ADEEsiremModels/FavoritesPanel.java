package ADEEsiremModels;

import ADEEsirem.ADEEsirem;
import ade.Course;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Locale;

/**
 * Class generating the favorites page
 */
public class FavoritesPanel extends JPanel{
    ADEEsirem app;
    JTable table;
    JScrollPane sp;
    DefaultTableModel model;

    public FavoritesPanel(JPanel parent, ADEEsirem app) {
        super();
        setPreferredSize(parent.getPreferredSize());
        setBackground(Color.white);
        this.app = app;

        model = new DefaultTableModel() {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        JTable table = new JTable(model);
        sp = new JScrollPane(table);

        // resize table when window resize
        table.addComponentListener(new ComponentAdapter()
        {
            @Override
            public void componentResized(ComponentEvent e)
            {
                table.setRowHeight(16);
                Dimension p = table.getPreferredSize();
                Dimension v = sp.getViewportBorderBounds().getSize();
                if (v.height > p.height)
                {
                    int available = v.height -
                            table.getRowCount() * table.getRowMargin();
                    int perRow = available / table.getRowCount();
                    table.setRowHeight(perRow);
                }
            }
        });

        model.addColumn("Salle");
        model.addColumn("Cours actuel");
        model.addColumn("Disponible");
        model.addColumn("Prochain cours");

        this.addRows();
        this.add(sp);
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        // set the size of the scrollpane when window resize
        sp.setPreferredSize(new Dimension((int) (this.getSize().getWidth()*0.75), (int) (this.getSize().getHeight()*0.7)));
    }

    /**
     * Update the table
     */
    public void updateTable() {
        while(model.getRowCount()!=0){
            model.removeRow(0);
        }
        addRows();
    }

    /**
     * Add a row on the table
     */
    private void addRows(){
        Locale locale = new Locale("fr", "FR");
        String pattern = "EEEE dd/MM/yyyy HH:mm";
        DateFormat df = new SimpleDateFormat(pattern, locale);
        for(String classroom : app.getFavoriteClassroom().getClassrooms()){
            String actual = "";
            String next = "";
            String disponible = "Oui";
            if(app.getAde().getClassrooms().containsKey(classroom)) {
                Course current = app.getAde().getClassrooms().get(classroom).getCurrentCourse();
                if (current != null) {
                    actual = current.getSummary();
                    disponible = "le " + df.format(current.getEndDate());
                }

                Course nextCourse = app.getAde().getClassrooms().get(classroom).getNextCourse();
                if (nextCourse != null) {
                    next = nextCourse.getSummary() + " le " + df.format(nextCourse.getStartDate());;
                }

            }else{
                actual = "?";
                next = "?";
                disponible = "?";
            }
            model.addRow(new Object[]{classroom, actual, disponible, next});
        }
    }
}
