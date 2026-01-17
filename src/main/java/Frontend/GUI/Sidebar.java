package Frontend.GUI;

import javax.swing.*;

import com.formdev.flatlaf.extras.FlatSVGIcon;

import java.awt.*;
import net.miginfocom.swing.MigLayout;
import Frontend.Compoent.Theme;
import Frontend.Compoent.CustomButton;
import Frontend.GUI.HoaDon.HoaDonPanel;
import Frontend.GUI.Nhaphang.FromHienThiNhapHang;
import Frontend.GUI.PhieuNhap.FromHienThiPhieuNhap;
import Frontend.GUI.BanHang.BanHangPanel;
import Frontend.GUI.KhuyenMai.KhuyenMaiPanel;
import Frontend.GUI.BaoHanh.BaoHanhPanel;
import Frontend.GUI.NhaCungCap.FromHienThiNCC;

public class Sidebar extends JPanel {
    private MainFrame parent;

    public Sidebar(MainFrame parent) {
        this.parent = parent;
        setBackground(Theme.SECONDARY_COLOR);
        setLayout(new MigLayout("wrap 1, fillx, insets 20", "[fill]", "[]20[]"));

        String[] menuItems = { "Sản phẩm", "Bán hàng", "Nhập hàng", "Hóa đơn", "Phiếu nhập", "Khuyến mãi",
                "Nhà cung cấp", "Nhân viên", "Khách hàng", "Phân quyền" };
        for (String item : menuItems)
            add(createMenubtn(item));

        FlatSVGIcon logoutIcon = new FlatSVGIcon("images/icon/log-out.svg", 20, 20);
        CustomButton btnLogout = new CustomButton("Đăng xuất", Theme.DANGER_COLOR);
        btnLogout.setIcon(logoutIcon);
        btnLogout.addActionListener(e -> System.exit(0));
        add(btnLogout, "pushy, aligny bottom, h 40!");
    }

    private JButton createMenubtn(String text) {
        String iconPath = "images/icon/" + getIconName(text) + ".svg";
        FlatSVGIcon icon = new FlatSVGIcon(iconPath, 20, 20);
        CustomButton btn = new CustomButton(text, Theme.SECONDARY_COLOR);
        btn.setIcon(icon);
        btn.setFont(new Font("Segoe UI", Font.BOLD, 15));
        btn.setHorizontalAlignment(SwingConstants.LEFT);
        btn.setBorderPainted(false);
        btn.setFocusPainted(false);

        btn.addActionListener(e -> {
            switch (text) {
                case "Sản phẩm":
                    parent.setPage(new QuanlyamthanhPanel());
                    break;
                case "Bán hàng":
                    parent.setPage(new BanHangPanel());
                    break;
                case "Hóa đơn":
                    parent.setPage(new HoaDonPanel());
                    break;
                case "Nhập hàng":
                    parent.setPage(new FromHienThiNhapHang());
                    break;
                case "Phiếu nhập":
                    parent.setPage(new FromHienThiPhieuNhap());
                    break;
                // case "Khuyến mãi":
                // parent.setPage(new KhuyenMaiPanel());
                // break;
                // case "Bảo hành":
                // parent.setPage("BaoHanhPanel()");
                // break;
                case "Nhà cung cấp":
                    parent.setPage(new FromHienThiNCC());
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

    private String getIconName(String text) {
        switch (text) {
            case "Sản phẩm":
                return "shopping-basket";
            case "Bán hàng":
                return "shield-minus";
            case "Nhập hàng":
                return "shield-plus";
            case "Hóa đơn":
                return "ticket";
            case "Phiếu nhập":
                return "tickets";
            case "Khuyến mãi":
                return "ticket-slash";
            case "Nhà cung cấp":
                return "dock";
            case "Nhân viên":
                return "square-user";
            case "Khách hàng":
                return "file-user";
            case "Phân quyền":
                return "user-round-pen";
            default:
                return "help-circle";
        }
    }
}
