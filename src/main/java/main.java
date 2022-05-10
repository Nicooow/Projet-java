import ade.Ade;
import net.fortuna.ical4j.data.ParserException;

import java.io.IOException;

public class main {
    public static void main(String[] args) {
        System.out.println("cc");
        //https://plannings.u-bourgogne.fr/jsp/custom/modules/plannings/anonymous_cal.jsp?data=8241fc3873200214f99ef6b1fb1d72f0e0fa50826f0818afe371361a3f1d40e6906f45af276f59ae8fac93f781e86152b11da73a3d6d4343970e5ec90b1e9f90c2973627c2eb073b13ac0cb4a0d643be8d3f4109b6629391

        Ade test = new Ade("https://gauthier-thomas.dev/ADECal.ics");
        try {
            test.loadCal();
        } catch (IOException | ParserException e) {
            e.printStackTrace();
        }
    }
}
