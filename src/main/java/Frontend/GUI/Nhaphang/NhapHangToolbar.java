package Frontend.GUI.Nhaphang;

import Frontend.Compoent.SearchTextField;
import Frontend.Compoent.Theme;
import Frontend.Compoent.Button;
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
        
        Button btnAdd = new Button("Thêm", Theme.ACCENT_COLOR);
        Button btnDele = new Button("Xóa", Theme.DANGER_COLOR);

        add(txtSearch, "growx, h 35!");
        add(btnAdd, "w 120!, h 35!");
        add(btnDele, "w 120!, h 35!");
    }
}
