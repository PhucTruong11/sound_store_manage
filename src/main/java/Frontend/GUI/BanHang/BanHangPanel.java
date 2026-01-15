package Frontend.GUI.BanHang;

import javax.swing.*;
import Frontend.Compoent.SearchTextField;
import java.awt.*;
import net.miginfocom.swing.MigLayout;

public class BanHangPanel extends JPanel {
    private ProductGrid productGrid;
    private JPanel pnlCart;

    public BanHangPanel() {
        setLayout(new BorderLayout(10, 10));
        setBackground(Color.WHITE);

        JPanel pnlHeader = new JPanel(new FlowLayout(FlowLayout.LEFT, 15, 10));
        pnlHeader.setBackground(Color.WHITE);

        SearchTextField txtSearch = new SearchTextField("Tìm kiếm sản phẩm...");
        txtSearch.setPreferredSize(new Dimension(400, 40));

        pnlHeader.add(txtSearch);
        add(pnlHeader, BorderLayout.NORTH);

        productGrid = new ProductGrid();
        add(productGrid, BorderLayout.CENTER);

        pnlCart = new JPanel(new MigLayout("wrap 1, fillx, insets 10", "[fill]", "[][grow][]"));
        pnlCart.setPreferredSize(new Dimension(350, 0));
        pnlCart.setBackground(Color.WHITE);
        pnlCart.setBorder(BorderFactory.createLineBorder(Color.BLACK));

        add(pnlCart, BorderLayout.EAST);
    }
}