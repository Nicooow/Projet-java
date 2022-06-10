import javax.swing.*;

public class MainWindow {
    private JPanel root;
    private JPanel mapRDJ;
    private JPanel mapRDC;
    private JPanel map1;
    private JPanel favorites;

    public static void main(String[] args) {
        JFrame frame = new JFrame("MainWindow");
        frame.setContentPane(new MainWindow().root);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }

    public MainWindow() {
        mapRDJ.setVisible(true);
        mapRDJ.add(new MapPanel(mapRDJ));
    }
}
