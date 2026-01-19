package Frontend.GUI.BanHang;

import javax.swing.*;
import java.awt.*;
import Frontend.Compoent.Theme;
import Frontend.GUI.BanHang.PaginationPanel;

public class BanHangPanel extends JPanel {
    private ProductGrid productGrid;
    private BanHangSidebar sidebar;
    private BanHangToolbar toolbar;
    private PaginationPanel pagination;

    public BanHangPanel() {
        setLayout(new BorderLayout(10, 10)); // Dùng BorderLayout làm chuẩn
        setBackground(Theme.BACKGROUND_COLOR);

        toolbar = new BanHangToolbar();
        add(toolbar, BorderLayout.NORTH);

        sidebar = new BanHangSidebar();
        add(sidebar, BorderLayout.WEST);

        productGrid = new ProductGrid(sidebar);
        add(productGrid, BorderLayout.CENTER);

        pagination = new PaginationPanel(5, n -> {
            productGrid.loadData(n, sidebar);
        });

        add(pagination, BorderLayout.SOUTH);
    }
}