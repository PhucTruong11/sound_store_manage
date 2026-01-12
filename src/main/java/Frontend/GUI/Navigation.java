package Frontend.GUI;

import javax.swing.*;
import java.awt.*;
import net.miginfocom.swing.MigLayout;
import Frontend.Compoent.Theme; 

public class Navigation extends JPanel{
    public Navigation(String userName) {
        setBackground(Theme.PRIMARY_COLOR);
        setLayout(new MigLayout("fill, insets 0 40 0 40","[200!][grow, center][300!]", "[65!, center]"));

        setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, new Color(230, 230, 230)));

        // LOGO
        JLabel lblLogo = new JLabel("STORE");
        lblLogo.setFont(new Font("Segoe UI", Font.BOLD, 22));
        lblLogo.setForeground(Color.WHITE);
        add(lblLogo, "left, ay center");

        // THANH TÌM KIẾM
        JTextField txtSearch = new JTextField();
        txtSearch.putClientProperty("JTextField.placeholderText", "Tìm kiếm mọi thứ bạn cần...");
        txtSearch.putClientProperty("JTextField.showClearButton", true);
        txtSearch.setBackground(new Color(255, 255, 255, 230));
        add(txtSearch, "center, w 450, h 38!, ay center");

        // CÀI ĐẶT & TÀI KHOẢN 
        JPanel rightPanel = new JPanel(new MigLayout("insets 0", "[]15[]10[]", "center"));
        rightPanel.setOpaque(false);

        // Thông tin tài khoản
        JLabel lblUser = new JLabel(userName);
        lblUser.setForeground(Color.WHITE);
        lblUser.setFont(new Font("Segoe UI", Font.BOLD, 15));

        JLabel lblCart = new JLabel();
        try {
            ImageIcon cartIcon = new ImageIcon(getClass().getResource("/images/icon/shopping-cart.png"));
            Image scaledCart = cartIcon.getImage().getScaledInstance(16, 16, Image.SCALE_SMOOTH);
            lblCart.setIcon(new ImageIcon(scaledCart));
        } catch (Exception e) {
            System.err.println("Không tìm thấy icon giỏ hàng!");
        }
        lblCart.setCursor(new Cursor(Cursor.HAND_CURSOR));

        // cài đặt
        JButton btnSetting = new JButton("Cài đặt");
        btnSetting.setForeground(Color.WHITE);
        btnSetting.setFont(new Font("Segoe UI", Font.BOLD, 15));
        btnSetting.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnSetting.setBorderPainted(false);
        btnSetting.setContentAreaFilled(false);

        rightPanel.add(lblUser);
        rightPanel.add(lblCart);
        rightPanel.add(btnSetting);

        add(rightPanel, "right, ay center");
    }
}
