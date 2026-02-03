package Frontend.GUI.BanHang;

import javax.swing.*;
import net.miginfocom.swing.MigLayout;
import Frontend.Compoent.Theme;
import java.awt.Color;
import Frontend.Compoent.SearchTextField;
import java.awt.*;

public class BanHangToolbar extends JPanel {
    public BanHangToolbar() {
        setLayout(new MigLayout("fillx, insets 10", "[grow]10[]10[]"));
        setBackground(Color.WHITE);
        putClientProperty("FlatLaf.style", "arc: " + Theme.ROUNDING_ARC);

        SearchTextField txtSearch = new SearchTextField("Tìm kiếm sản phẩm...");
        add(txtSearch, "growx, h 35!");

        String[] loaiSP = { "Tất cả sản phẩm", "Loa", "Tai nghe", "Phụ kiện" };
        JComboBox<String> cbFilter = new JComboBox<>(loaiSP);
        cbFilter.setPreferredSize(new Dimension(200, 35));
        cbFilter.putClientProperty("FlatLaf.style", "arc: 10");

        add(cbFilter, "w 200!");
    }
}
