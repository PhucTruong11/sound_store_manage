package Frontend.GUI.HoaDon;

import javax.swing.*;
import net.miginfocom.swing.MigLayout;
import Frontend.Compoent.Theme;
import java.awt.Color;

public class HoaDonToolbar extends JPanel {
    public HoaDonToolbar() {
        setLayout(new MigLayout("fillx, insets 10", "[]10[]20[grow]10[]", "[]"));
        setBackground(Color.WHITE);
        putClientProperty("FlatLaf.style", "arc: " + Theme.ROUNDING_ARC);

        JButton btnDelete = new JButton("XÓA HÓA ĐƠN");
        btnDelete.setBackground(Color.WHITE);
        btnDelete.setForeground(Theme.DANGER_COLOR);

        JButton btnExport = new JButton("XUẤT EXCEL");
        btnExport.setBackground(Color.WHITE);

        JTextField txtSearch = new JTextField();
        txtSearch.putClientProperty("JTextField.placeholderText", "Tìm kiếm hóa đơn...");
        txtSearch.putClientProperty("JTextField.showClearButton", true);

        JButton btnRefresh = new JButton("Làm mới");
        btnRefresh.setBackground(Theme.ACCENT_COLOR);
        btnRefresh.setForeground(Color.WHITE);

        add(btnDelete, "h 40!");
        add(btnExport, "h 40!");
        add(txtSearch, "h 40!, growx");
        add(btnRefresh, "h 40!, align");
    }
}