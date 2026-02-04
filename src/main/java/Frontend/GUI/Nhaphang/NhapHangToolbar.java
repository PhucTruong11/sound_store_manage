package Frontend.GUI.Nhaphang;

import Frontend.Compoent.SearchTextField;
import Frontend.Compoent.ButtonNhapExcel;
import Frontend.Compoent.ButtonXuatExcel;
import net.miginfocom.swing.MigLayout;
import javax.swing.*;
import java.awt.*;

public class NhapHangToolbar extends JPanel{
    public NhapHangToolbar() {
        setLayout(new MigLayout("insets 10", "[grow]10[]10[]"));
        setBackground(Color.WHITE);
        putClientProperty("FlatLaf.style", "arc: 20");
        setOpaque(false);

        SearchTextField txtSearch = new SearchTextField("Tìm kiếm tên SP, mã SP...");
        add(txtSearch, "growx, h 35!");

        String[] loaiSP = { "Tất cả sản phẩm", "Loa", "Tai nghe", "Phụ kiện" };
        JComboBox<String> cbFilter = new JComboBox<>(loaiSP);
        cbFilter.setPreferredSize(new Dimension(200, 35));
        cbFilter.putClientProperty("FlatLaf.style", "arc: 10");
        add(cbFilter, "w 200!");
    }
}
