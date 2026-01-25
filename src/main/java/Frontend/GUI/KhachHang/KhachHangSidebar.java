package Frontend.GUI.KhachHang;

import Frontend.Compoent.Theme;
import Frontend.Compoent.CustomButton;
import net.miginfocom.swing.MigLayout;
import javax.swing.*;
import java.awt.*;

public class KhachHangSidebar extends JPanel {
    public KhachHangSidebar() {
        setLayout(new MigLayout("wrap 1, fillx, insets 20", "[fill]"));
        setPreferredSize(new Dimension(280, 0));
        setBackground(Color.WHITE);
        putClientProperty("FlatLaf.style", "arc: " + Theme.ROUNDING_ARC);

        initFilter();
    }

    private void initFilter() {
        add(new JLabel("Theo Mã"), "gaptop 10");
        add(new JTextField(), "h 35!");

        add(new JLabel("Theo Tên"), "gaptop 10");
        add(new JTextField(), "h 35!");

        add(new JLabel("Theo địa chỉ"), "gaptop 10");
        add(new JTextField(), "h 35!");

        add(new JLabel("Theo SĐT"), "gaptop 10");
        add(new JTextField(), "h 35!");

        CustomButton btnLoc = new CustomButton("LỌC", Theme.ACCENT_COLOR);
        add(btnLoc, "pushy, aligny bottom, growx, h 40!");
    }
}
