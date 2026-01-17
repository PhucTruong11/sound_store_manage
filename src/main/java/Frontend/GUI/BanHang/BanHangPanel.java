package Frontend.GUI.BanHang;

import javax.swing.*;
import Frontend.Compoent.SearchTextField;
import java.awt.*;
import net.miginfocom.swing.MigLayout;
import Frontend.Compoent.Theme;

public class BanHangPanel extends JPanel {
    private ProductGrid productGrid;

    public BanHangPanel() {
        setLayout(new BorderLayout(10, 10));
        setBackground(Color.WHITE);

        JPanel pnlHeader = new JPanel(new MigLayout("insets 10 15 10 15, fillx", "[grow]", "[]"));
        pnlHeader.setBackground(Theme.BACKGROUND_COLOR);

        SearchTextField txtSearch = new SearchTextField("Tìm kiếm sản phẩm...");

        pnlHeader.add(txtSearch, "w 700!, h 35!");

        add(pnlHeader, BorderLayout.NORTH);

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