package Frontend.GUI;

import javax.swing.*;
import java.awt.*;
import net.miginfocom.swing.MigLayout;
import Frontend.Compoent.Theme;
import Frontend.Compoent.Button;
import Frontend.GUI.HoaDon.HoaDonPanel;
import Frontend.GUI.Nhaphang.FromHienThi;

public class Sidebar extends JPanel {
    private MainFrame parent;

    public Sidebar(MainFrame parent) {
        this.parent = parent;
        setBackground(Theme.SECONDARY_COLOR);
        setLayout(new MigLayout("wrap 1, fillx, insets 20", "[fill]", "[]20[]"));

        String[] menuItems = { "Sản phẩm", "Bán hàng", "Nhập hàng", "Hóa đơn", "Phiếu nhập", "Khuyến mãi",
                "Nhà cung cấp", "Nhân viên", "Khách hàng" };
        for (String item : menuItems)
            add(createMenubtn(item));

        Button btnLogout = new Button("Đăng xuất", Theme.DANGER_COLOR);
        btnLogout.addActionListener(e -> System.exit(0));
        add(btnLogout, "pushy, aligny bottom, h 40!");
    }

    private JButton createMenubtn(String text) {
        Button btn = new Button(text, Theme.SECONDARY_COLOR);
        btn.setFont(new Font("Sagoe UI", Font.BOLD, 15));
        btn.setHorizontalAlignment(SwingConstants.CENTER);
        btn.setBorderPainted(false);
        btn.setFocusPainted(false);

        btn.addActionListener(e -> {
            switch (text) {
                case "Sản phẩm":
                    parent.setPage(new QuanlyamthanhPanel());
                    break;
                case "Hóa đơn":
                    parent.setPage(new HoaDonPanel());
                    break;
                case "Nhập hàng":
                    parent.setPage(new FromHienThi());
                    break;
                case "Đăng xuất":
                    System.exit(0);
                default:
                    JOptionPane.showMessageDialog(parent, "Chức năng " + text + " đang phát triển!");
                    break;
            }
        });
        return btn;
    }
}
