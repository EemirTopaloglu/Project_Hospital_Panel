import ui.MainFrame;
import ui.LoginFrame;
import javax.swing.*;

public class HospitalApp {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new LoginFrame().setVisible(true);
        });
    }
}