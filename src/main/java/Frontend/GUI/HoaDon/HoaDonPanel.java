package Frontend.GUI.HoaDon;

import javax.swing.*;
import java.awt.*;
import Frontend.Compoent.Theme;
import Frontend.GUI.HoaDon.*;

public class HoaDonPanel extends JPanel {
    private HoaDonToolbar toolbar;
    private HoaDonSidebar sidebar;
    private HoaDonTable table;

    public HoaDonPanel() {
        initLayout();
        initComponents();
    }

    private void initLayout() {
        this.setLayout(new BorderLayout(15, 15));
        this.setBackground(Theme.BACKGROUND_COLOR);
        this.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
    }

    private void initComponents() {
        toolbar = new HoaDonToolbar();
        sidebar = new HoaDonSidebar();
        table = new HoaDonTable(sidebar);

        add(toolbar, BorderLayout.NORTH);
        add(sidebar, BorderLayout.WEST);
        add(table, BorderLayout.CENTER);
    }
}