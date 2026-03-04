import Frontend.GUI.MainFrame;
import Frontend.GUI.LogIn.LoginFrame;
import Frontend.Compoent.Theme;

public class App {
    public static void main(String[] args) {
        Theme.setup(false); // Thiết lập FlatLaf/Theme
        java.awt.EventQueue.invokeLater(() -> {
            // Hiển thị màn hình đăng nhập trướcgit 
            new LoginFrame().setVisible(true);
        });
    }
} 