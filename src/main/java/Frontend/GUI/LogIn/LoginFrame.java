package Frontend.GUI.LogIn;

import Frontend.Compoent.Theme;
import Frontend.Compoent.CustomButton;
import Backend.BUS.TaiKhoanBUS;
import Backend.DTO.TaiKhoan;
import net.miginfocom.swing.MigLayout;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class LoginFrame extends JFrame {

    private JTextField txtUsername;
    private JPasswordField txtPassword;
    private CustomButton btnLogin;
    private JLabel lblForgotPassword;
    private TaiKhoanBUS taiKhoanBUS = new TaiKhoanBUS();

    public LoginFrame() {
        initUI();
    }

    private void initUI() {
        setTitle("Đăng nhập hệ thống - Sound Store");
        setSize(500, 500);
        setLocationRelativeTo(null); 
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);

        JPanel mainPanel = new JPanel(new MigLayout("fill, insets 20", "[center]", "[center]"));
        mainPanel.setBackground(Color.WHITE);

        JPanel formPanel = new JPanel(new MigLayout("wrap 1, fillx, insets 40 40 40 40", "[grow]"));
        formPanel.setOpaque(false);

        JLabel lblTitle = new JLabel("ĐĂNG NHẬP");
        lblTitle.setFont(new Font("Segoe UI", Font.BOLD, 32));
        lblTitle.setForeground(Theme.PRIMARY_COLOR);
        formPanel.add(lblTitle, "center, gapbottom 20");

        JLabel lblUsername = new JLabel("Tên đăng nhập:");
        lblUsername.setFont(new Font("Segoe UI", Font.BOLD, 13));
        lblUsername.setForeground(new Color(100, 100, 100));
        formPanel.add(lblUsername, "gaptop 10");

        txtUsername = new JTextField();
        txtUsername.putClientProperty("FlatLaf.style", "margin: 5,10,5,10; arc: 10"); // Padding bên trong ô text
        formPanel.add(txtUsername, "growx, h 45!");

        JLabel lblPassword = new JLabel("Mật khẩu:");
        lblPassword.setFont(new Font("Segoe UI", Font.BOLD, 13));
        lblPassword.setForeground(new Color(100, 100, 100));
        formPanel.add(lblPassword, "gaptop 15");

        txtPassword = new JPasswordField();
        txtPassword.putClientProperty("FlatLaf.style", "margin: 5,10,5,10; arc: 10");
        formPanel.add(txtPassword, "growx, h 45!");

        btnLogin = new CustomButton("ĐĂNG NHẬP", Theme.PRIMARY_COLOR);
        formPanel.add(btnLogin, "growx, h 45!, gaptop 30");

        lblForgotPassword = new JLabel("<html><u>Quên mật khẩu?</u></html>");
        lblForgotPassword.setForeground(Theme.PRIMARY_COLOR);
        lblForgotPassword.setCursor(new Cursor(Cursor.HAND_CURSOR));
        formPanel.add(lblForgotPassword, "center, gaptop 15");

        mainPanel.add(formPanel, "w 340!"); 
        setContentPane(mainPanel);

        btnLogin.addActionListener(e -> doLogin());

        // Hỗ trợ bấm Enter ở cả 2 ô text để đăng nhập nhanh
        KeyAdapter enterSubmit = new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                    doLogin();
                }
            }
        };
        txtUsername.addKeyListener(enterSubmit);
        txtPassword.addKeyListener(enterSubmit);
    }

    private void doLogin() {
        String username = txtUsername.getText().trim();
        String password = new String(txtPassword.getPassword());

        if (username.isEmpty() || password.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Vui lòng nhập đầy đủ thông tin!", "Cảnh báo", JOptionPane.WARNING_MESSAGE);
            return;
        }

        TaiKhoan tk = taiKhoanBUS.login(username, password);

        if (tk != null) {
            // Truyền tên đăng nhập (hoặc tên nhân viên nếu bạn join bảng) sang MainFrame
            new Frontend.GUI.MainFrame(tk.getUsername()).setVisible(true);
            dispose(); // Đóng form Login
        } else {
            JOptionPane.showMessageDialog(this, "Sai tài khoản hoặc mật khẩu, hoặc tài khoản đã bị khóa!", "Lỗi đăng nhập", JOptionPane.ERROR_MESSAGE);
        }
    }
}