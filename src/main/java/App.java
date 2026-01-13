import Frontend.GUI.MainFrame;
import Frontend.Compoent.Theme;

public class App {
    public static void main(String[] args) {
        // Mặc định chạy bản Sáng (false) hoặc Tối (true)
        Theme.setup(false);
        java.awt.EventQueue.invokeLater(() -> {
            new MainFrame().setVisible(true);
        });
    }
}