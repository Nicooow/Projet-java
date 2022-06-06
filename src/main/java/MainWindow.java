import javax.swing.*;

public class MainWindow {
    private JPanel root;
    private JPanel map;

    public static void main(String[] args) {
        JFrame frame = new JFrame("MainWindow");
        frame.setContentPane(new MainWindow().root);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }

    public MainWindow() {
        map.setVisible(true);
        map.add(new MapPanel(map));
    }
}
