import ade.Ade;
import net.fortuna.ical4j.data.ParserException;

import javax.swing.*;
import java.io.IOException;

public class ADEEsirem {
    private JPanel root;
    private JPanel mapRDJ;
    private JPanel mapRDC;
    private JPanel map1;
    private JPanel favorites;

    private Ade ade;

    public static void main(String[] args) {
        JFrame frame = new JFrame("ADEEsirem");
        frame.setContentPane(new ADEEsirem().root);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }

    public ADEEsirem() {
        ade = new Ade("https://plannings.u-bourgogne.fr/jsp/custom/modules/plannings/anonymous_cal.jsp?data=8241fc3873200214cb5d743179aedd5bbcb40f7fa3414b5f09e49fce06dff818324cfcf2e9e6b435826b8d54c6bff1eef43b49ed91b3cccdb0db0d7caf18783af6f896fd5b10973b27880fda6f957ee06e8504f9d2b878bb66ed3eb8f721b666");
        try {
            ade.loadCal();
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (ParserException e) {
            throw new RuntimeException(e);
        }

        mapRDJ.setVisible(true);
        mapRDJ.add(new MapPanel(mapRDJ, this));
    }

    public Ade getAde() {
        return ade;
    }

    public void setAde(Ade ade) {
        this.ade = ade;
    }
}
