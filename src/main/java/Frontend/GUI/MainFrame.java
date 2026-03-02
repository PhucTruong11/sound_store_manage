package Frontend.GUI;

import javax.swing.*;
import java.awt.*;
import Frontend.Compoent.Theme;

public class MainFrame extends JFrame {
    private JPanel mainContent;

    public MainFrame(String userName) {
        setTitle("Cửa hàng thiết bị âm thanh " + userName);
        setSize(1400, 800);
        setBackground(Theme.BACKGROUND_COLOR);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        setLayout(new BorderLayout());

        add(new Navigation(userName), BorderLayout.NORTH);
        add(new Sidebar(this), BorderLayout.WEST);

        mainContent = new JPanel(new BorderLayout());
        mainContent.setBackground(Theme.BACKGROUND_COLOR);
        add(mainContent, BorderLayout.CENTER);

        JPanel pnlWelcome = new JPanel(new GridBagLayout());
        pnlWelcome.setBackground(Theme.BACKGROUND_COLOR);
        JLabel lblWelcome = new JLabel("Chào mừng " + userName + " trở lại hệ thống!");
        lblWelcome.setFont(new Font("Segoe UI", Font.ITALIC, 20));
        pnlWelcome.add(lblWelcome);
        
        setPage(pnlWelcome);
    }

    public void setPage(JPanel panel) {
        mainContent.removeAll(); // Xóa cái cũ đi
        mainContent.add(panel, BorderLayout.CENTER); // Thêm cái mới vào
        mainContent.revalidate(); // Vẽ lại giao diện
        mainContent.repaint();
    }
}