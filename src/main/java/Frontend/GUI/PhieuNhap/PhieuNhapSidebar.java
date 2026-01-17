package Frontend.GUI.PhieuNhap;

import Frontend.Compoent.Theme;
import Frontend.Compoent.CustomButton;
import net.miginfocom.swing.MigLayout;
import javax.swing.*;
import java.awt.*;

public class PhieuNhapSidebar extends JPanel{
    public PhieuNhapSidebar() {
        setLayout(new MigLayout("wrap 1, fillx, insets 20", "[fill]"));
        setPreferredSize(new Dimension(280, 0));
        setBackground(Color.WHITE);
        putClientProperty("FlatLaf.style", "arc: " + Theme.ROUNDING_ARC);

        initNhanVienNhap();
        initNhaCungCap();
        initDate();
        initPrice();
        initConfirmButton();
    }

    private void initNhanVienNhap() {
        add(new JLabel("Nhân viên nhập"), "gaptop 10");
        add(new JComboBox<>(new String[] {"Tất cả", "Phuc truong", "Van Nam"}), "h 35!");
    }

    private void initNhaCungCap() {
        add(new JLabel("Nhà cung cấp"), "gaptop 10");
        add(new JComboBox<>(new String[] {"Tất cả", "Sony Electronics", "JBL Official", "Marshall VN"}), "h 35!");
    }

    private void initDate() {
        add(new JLabel("Từ ngày:"), "gaptop 10");
        add(new JTextField("2025-01-01"), "h 35!");
        add(new JLabel("Đến ngày:"), "gaptop 10");
        add(new JTextField("2026-12-01"), "h 35!");
    }

    private void initPrice() {
        add(new JLabel("Số tiền từ (VNĐ):"), "gaptop 10");
        add(new JTextField(), "h 35!");
        add(new JLabel("Đến số tiền (VNĐ):"), "gaptop 10");
        add(new JTextField(), "h 35!");
    }

    private void initConfirmButton() {
        CustomButton btnLoc = new CustomButton("LỌC", Theme.ACCENT_COLOR);
        add(btnLoc, "pushy, aligny bottom, growx, h 40!");
    }
}
