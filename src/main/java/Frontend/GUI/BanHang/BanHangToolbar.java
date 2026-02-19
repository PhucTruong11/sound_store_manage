package Frontend.GUI.BanHang;

import javax.swing.*;
import net.miginfocom.swing.MigLayout;
import Frontend.Compoent.Theme;
import Frontend.Compoent.SearchTextField;
import java.awt.*;

public class BanHangToolbar extends JPanel {
    private ProductGrid productGrid;
    private BanHangSidebar sidebar;

    public BanHangToolbar(ProductGrid productGrid, BanHangSidebar sidebar) {
        this.productGrid = productGrid;
        this.sidebar = sidebar;

        setLayout(new MigLayout("fillx, insets 10", "[grow]10[]10[]"));
        setBackground(Color.WHITE);
        putClientProperty("FlatLaf.style", "arc: " + Theme.ROUNDING_ARC);

        SearchTextField txtSearch = new SearchTextField("Tìm kiếm sản phẩm...");
        txtSearch.addKeyListener(new java.awt.event.KeyAdapter() {
            @Override
            public void keyReleased(java.awt.event.KeyEvent e) {
                String text = txtSearch.getText().trim();
                productGrid.loadSearchData(text);
            }
        });
        add(txtSearch, "growx, h 35!");

        String[] loaiSP = { "Tất cả sản phẩm", "Loa", "Tai nghe", "Phụ kiện" };
        JComboBox<String> cbFilter = new JComboBox<>(loaiSP);

        cbFilter.setPreferredSize(new Dimension(200, 35));
        cbFilter.putClientProperty("FlatLaf.style", "arc: 10");

        add(cbFilter, "w 200!");
    }
}
