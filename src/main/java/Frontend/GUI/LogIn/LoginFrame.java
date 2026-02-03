package Frontend.GUI.LogIn;

import Frontend.Compoent.Theme;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class LoginFrame extends JFrame {

    private JTextField txtUsername;
    private JPasswordField txtPassword;
    private JButton btnLogin;
    private JLabel lblForgotPassword;

    public LoginFrame() {
        initUI();
    }

    private void initUI() {
        setTitle("Đăng nhập hệ thống");
        setSize(400, 250);
        setLocationRelativeTo(null); // căn giữa màn hình
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);

        // Panel chính
        JPanel panel = new JPanel();
        panel.setLayout(null);
        panel.setBackground(Color.WHITE);
        add(panel);

        // Tiêu đề
        JLabel lblTitle = new JLabel("ĐĂNG NHẬP");
        lblTitle.setFont(new Font("Arial", Font.BOLD, 24));
        lblTitle.setBounds(130, 20, 200, 30);
        panel.add(lblTitle);

        // Username
        JLabel lblUsername = new JLabel("Username:");
        lblUsername.setBounds(50, 70, 80, 25);
        panel.add(lblUsername);

        txtUsername = new JTextField();
        txtUsername.setBounds(140, 70, 200, 25);
        panel.add(txtUsername);

        // Password
        JLabel lblPassword = new JLabel("Password:");
        lblPassword.setBounds(50, 110, 80, 25);
        panel.add(lblPassword);

        txtPassword = new JPasswordField();
        txtPassword.setBounds(140, 110, 200, 25);
        panel.add(txtPassword);

        // Nút Login
        btnLogin = new JButton("Đăng nhập");
        btnLogin.setBounds(140, 150, 200, 30);
        btnLogin.setBackground(new Color(0, 123, 255));
        btnLogin.setForeground(Color.WHITE);
        btnLogin.setFocusPainted(false);
        panel.add(btnLogin);

        // Quên mật khẩu
        lblForgotPassword = new JLabel("<HTML><U>Quên mật khẩu?</U></HTML>");
        lblForgotPassword.setBounds(140, 190, 200, 25);
        lblForgotPassword.setForeground(Color.BLUE);
        lblForgotPassword.setCursor(new Cursor(Cursor.HAND_CURSOR));
        panel.add(lblForgotPassword);

        // Action listener cho login
        btnLogin.addActionListener(e -> doLogin());

        // Click quên mật khẩu
        lblForgotPassword.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                JOptionPane.showMessageDialog(LoginFrame.this,
                        "Đang phát triển chức năng Quên mật khẩu",
                        "Quên mật khẩu",
                        JOptionPane.INFORMATION_MESSAGE);
            }
        });

        // Enter trên password cũng login
        txtPassword.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                    doLogin();
                }
            }
        });
    }

    private void doLogin() {
        String username = txtUsername.getText().trim();
        String password = new String(txtPassword.getPassword());

        if (username.isEmpty() || password.isEmpty()) {
            JOptionPane.showMessageDialog(this,
                    "Vui lòng nhập đầy đủ thông tin",
                    "Lỗi",
                    JOptionPane.ERROR_MESSAGE);
            return;
        }

        // MOCK: đăng nhập thành công nếu username = admin / password = 123
        if (username.equals("admin") && password.equals("123456")) {
            JOptionPane.showMessageDialog(this,
                    "Đăng nhập thành công");

            // Mở MainFrame (chưa cần truyền TaiKhoan thật)
            new Frontend.GUI.MainFrame().setVisible(true);
            dispose();
        } else {
            JOptionPane.showMessageDialog(this,
                    "Sai tài khoản hoặc mật khẩu",
                    "Lỗi",
                    JOptionPane.ERROR_MESSAGE);
        }
    }
}
