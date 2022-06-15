import ade.Ade;
import ade.Classroom;
import ade.Course;
import net.fortuna.ical4j.data.ParserException;

import java.io.IOException;
import java.net.URL;

public class main {
    public static void main(String[] args) throws IOException {
        /*Ade test = new Ade("https://plannings.u-bourgogne.fr/jsp/custom/modules/plannings/anonymous_cal.jsp?data=8241fc3873200214e2a2f7cd9a0c599314bf1b03930b9e6c52ed9e54d88c5609324cfcf2e9e6b435826b8d54c6bff1eef43b49ed91b3cccdb0db0d7caf18783ae8434ed8dfd957e658ecdc96059f4b9f6e8504f9d2b878bb1aa06c58b82934c1");
        try {
            test.loadCal();
        } catch (IOException | ParserException e) {
            e.printStackTrace();
        }

        for(Classroom classroom : test.getClassrooms().values()){
            Course current = classroom.getCurrentCourse();
            if(current == null){
                System.out.println(classroom.getName() + " : libre");
            }else{
                System.out.println(classroom.getName() + " : " + current.getSummary());
            }
        }

        ADEEsirem.main(args);*/


    }
}
