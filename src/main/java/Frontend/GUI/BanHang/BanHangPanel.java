package Frontend.GUI.BanHang;

import javax.swing.*;
import Frontend.Compoent.SearchTextField;
import java.awt.*;
import net.miginfocom.swing.MigLayout;
import Frontend.Compoent.Theme;

public class BanHangPanel extends JPanel {
    private ProductGrid productGrid;
    private BanHangSidebar sidebar;
    private BanHangToolbar toolbar;

    public BanHangPanel() {
        setLayout(new BorderLayout(15, 15));
        setBackground(Theme.BACKGROUND_COLOR);
        this.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));

        toolbar = new BanHangToolbar();
        add(toolbar, BorderLayout.NORTH);

        sidebar = new BanHangSidebar();
        add(sidebar, BorderLayout.WEST);

        productGrid = new ProductGrid();
        add(productGrid, BorderLayout.CENTER);

        JPanel pnlPagination = new JPanel(new FlowLayout(FlowLayout.CENTER));
        JButton btnPrev = new JButton("<");
        JButton btnNext = new JButton(">");
        JLabel lblPageInfo = new JLabel("Trang 1 / 5");

        pnlPagination.add(btnPrev);
        pnlPagination.add(lblPageInfo);
        pnlPagination.add(btnNext);

        add(pnlPagination, BorderLayout.SOUTH);
    }
}