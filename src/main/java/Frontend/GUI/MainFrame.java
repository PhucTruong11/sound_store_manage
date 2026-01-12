package Frontend.GUI;

import javax.swing.*;

import java.awt.*;
// import java.awt.event.ActionEvent;
// import java.awt.event.ActionListener;
// import net.miginfocom.swing.MigLayout;
import Frontend.Compoent.Theme;

public class MainFrame extends JFrame {

    private JPanel mainContent; // Nơi chứa các trang (Panel) chuyển đổi

    public MainFrame() {
        setTitle("Cửa hàng thiết bị âm thanh");
        setSize(1200, 800);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        setLayout(new BorderLayout());

        add(new Navigation("Phuc Truong"), BorderLayout.NORTH);
        add(new Sidebar(this), BorderLayout.WEST);

        mainContent = new JPanel(new BorderLayout());
        mainContent.setBackground(Theme.BACKGROUND_COLOR);
        add(mainContent, BorderLayout.CENTER);

        setPage(new JPanel() {{
            add(new JLabel("Chào mừng trở lại"));
        }});
    }

    public void setPage(JPanel panel) {
        mainContent.removeAll(); // Xóa cái cũ đi
        mainContent.add(panel, BorderLayout.CENTER);  // Thêm cái mới vào
        mainContent.revalidate(); // Vẽ lại giao diện
        mainContent.repaint();
    }
}