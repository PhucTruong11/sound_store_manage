package Frontend.GUI.BanHang;

import javax.swing.*;
import java.awt.*;
import Frontend.Compoent.Theme;
import net.miginfocom.swing.MigLayout;

public class BanHangPanel extends JPanel {
    private ProductGrid productGrid;
    private BanHangSidebar sidebar;
    private BanHangToolbar toolbar;
    private PaginationPanel pagination;

    public BanHangPanel() {
        setLayout(new MigLayout("fill, insets 15", "[280!]15[grow, fill]", "[][grow][]"));
        setBackground(Theme.BACKGROUND_COLOR);

        toolbar = new BanHangToolbar();
        sidebar = new BanHangSidebar();
        productGrid = new ProductGrid(sidebar);

        pagination = new PaginationPanel(5, n -> {
            productGrid.loadData(n, sidebar);
        });

        add(toolbar, "span 2, growx, wrap, gapbottom 10");
        add(sidebar, "w 280!, growy, span 1 2");
        add(productGrid, "grow, wrap");
        add(pagination, "center, center");
        // JPanel pnlSouth = new JPanel(new BorderLayout());
        // pnlSouth.setBackground(Theme.BACKGROUND_COLOR);
        // pnlSouth.add(pagination, BorderLayout.CENTER);

        // add(pnlSouth, BorderLayout.SOUTH);
        add(pagination, BorderLayout.SOUTH);
    }
}