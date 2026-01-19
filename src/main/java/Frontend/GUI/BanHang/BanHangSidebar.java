package Frontend.GUI.BanHang;

import javax.swing.*;
import net.miginfocom.swing.MigLayout;
import Frontend.Compoent.Theme;
import java.awt.*;

public class BanHangSidebar extends JPanel {

    public BanHangSidebar() {
        initLayout();
        initDateRange();
        initPriceRange();
    }

    private void initLayout() {
        setLayout(new MigLayout("wrap 1, fillx, insets 20", "[fill]"));
        setPreferredSize(new Dimension(280, 0));
        setBackground(Color.WHITE);
        putClientProperty("FlatLaf.style", "arc: " + Theme.ROUNDING_ARC);
    }

    private void initDateRange() {
        add(new JLabel("Từ ngày:"), "gaptop 10");
        add(new JTextField("2025-01-01"), "h 35!");
        add(new JLabel("Đến ngày:"), "gaptop 10");
        add(new JTextField("2025-12-31"), "h 35!");
    }

    private void initPriceRange() {
        add(new JLabel("Số tiền từ (VNĐ):"), "gaptop 10");
        add(new JTextField(), "h 35!");
        add(new JLabel("Đến số tiền (VNĐ):"), "gaptop 10");
        add(new JTextField(), "h 35!");
    }
}
