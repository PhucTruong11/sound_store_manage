package Frontend.GUI.HoaDon;

import javax.swing.*;
import net.miginfocom.swing.MigLayout;
import Frontend.Compoent.Theme;
import java.awt.*;

public class HoaDonSidebar extends JPanel {

    public HoaDonSidebar() {
        initLayout();
        initFilter();
        initDateRange();
        initPriceRange();
    }

    private void initLayout() {
        setLayout(new MigLayout("wrap 1, fillx, insets 20", "[fill]"));
        setPreferredSize(new Dimension(280, 0));
        setBackground(Color.WHITE);
        putClientProperty("FlatLaf.style", "arc: " + Theme.ROUNDING_ARC);
    }

    private void initFilter() {
        add(new JLabel("Nhân viên bán"), "gaptop 10");
        add(new JComboBox<>(new String[] { "Tất cả", "Van Nam" }), "h 35!");
    }

    private void initDateRange() {
        add(new JLabel("Từ ngày:"), "gaptop 10");
        add(new JTextField("2024-01-01"), "h 35!");
        add(new JLabel("Đến ngày:"), "gaptop 10");
        add(new JTextField("2024-12-31"), "h 35!");
    }

    private void initPriceRange() {
        add(new JLabel("Số tiền từ (VNĐ):"), "gaptop 10");
        add(new JTextField(), "h 35!");
        add(new JLabel("Đến số tiền (VNĐ):"), "gaptop 10");
        add(new JTextField(), "h 35!");
    }

    public void updateInfo(String maHD, String khachHang, String tongTien) {
    }
    
}