package Frontend;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import net.miginfocom.swing.MigLayout;

public class MainFrame extends JFrame {

    private JPanel mainContentPanel; // Khu vực hiển thị nội dung bên phải

    public MainFrame() {
        setTitle("Hệ thống Quản lý Cửa hàng Laptop");
        setSize(1200, 700); // Kích thước mặc định
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null); // Căn giữa màn hình

        // Layout: Cột 1 (Menu) rộng 250px, Cột 2 (Content) tự giãn
        setLayout(new MigLayout("fill, insets 0", "[250!][grow]", "[grow]"));

        initMenu();
        initContent();
    }

    private void initMenu() {
        // Tạo Panel cho Menu bên trái
        JPanel menuPanel = new JPanel();
        menuPanel.setBackground(new Color(45, 52, 54));
        // Layout của Menu: Các nút xếp dọc (wrap 1), lấp đầy chiều ngang (fillx)
        menuPanel.setLayout(new MigLayout("wrap 1, fillx, insets 20", "[fill]", "[]20[]"));

        JLabel lblTitle = new JLabel("LAPTOP STORE");
        lblTitle.setForeground(Color.WHITE);
        lblTitle.setFont(new Font("Arial", Font.BOLD, 24));
        lblTitle.setHorizontalAlignment(SwingConstants.CENTER);
        menuPanel.add(lblTitle, "h 50!, gapbottom 30");

        menuPanel.add(createMenuButton("Trang chủ"));
        menuPanel.add(createMenuButton("Quản lý Sản phẩm"));
        menuPanel.add(createMenuButton("Quản lý Nhân viên"));
        menuPanel.add(createMenuButton("Thống kê Doanh thu"));
        
        JButton btnLogout = createMenuButton("Đăng xuất");
        btnLogout.setBackground(new Color(214, 48, 49));
        menuPanel.add(btnLogout, "pushy, aligny bottom");

        add(menuPanel, "growy"); // growy để menu cao full màn hình
    }

    private void initContent() {
        // Tạo Panel chứa nội dung chính bên phải
        mainContentPanel = new JPanel();
        mainContentPanel.setLayout(new BorderLayout());
        mainContentPanel.setBackground(Color.WHITE);

        JLabel lblWelcome = new JLabel("Chào mừng trở lại hệ thống!", SwingConstants.CENTER);
        lblWelcome.setFont(new Font("Segoe UI", Font.ITALIC, 20));
        mainContentPanel.add(lblWelcome, BorderLayout.CENTER);

        add(mainContentPanel, "grow");
    }

    // Hàm tiện ích để tạo nút cho đẹp và đồng bộ
    private JButton createMenuButton(String text) {
        JButton btn = new JButton(text);
        btn.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        btn.setForeground(Color.WHITE);
        btn.setBackground(new Color(99, 110, 114)); // Màu xám nhạt hơn nền menu
        btn.setFocusPainted(false); // Bỏ viền khi click
        btn.setBorderPainted(false); // Bỏ viền nút
        btn.setCursor(new Cursor(Cursor.HAND_CURSOR)); // Đổi chuột thành bàn tay
        
        // Xử lý sự kiện click (Demo chuyển màu nội dung)
        btn.addActionListener(e -> {
            if (text.equals("Quản lý Sản phẩm")) {
                showPanel(new QuanLyLaptopPanel()); // Gọi Panel Laptop hiện lên
            } else {
                JOptionPane.showMessageDialog(this, "Chức năng " + text + " đang phát triển!");
            }
        });
        return btn;
    }

    private void showPanel(JPanel panel) {
        mainContentPanel.removeAll(); // Xóa cái cũ đi
        mainContentPanel.add(panel);  // Thêm cái mới vào
        mainContentPanel.revalidate(); // Vẽ lại giao diện
        mainContentPanel.repaint();
    }
}