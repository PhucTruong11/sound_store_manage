package Frontend.GUI.Nhaphang;

import Frontend.Compoent.SearchTextField;
import Frontend.Compoent.ButtonAdd;
import Frontend.Compoent.ButtonDele;
import Frontend.Compoent.ButtonFix;
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
        
        ButtonAdd btnAdd = new ButtonAdd("Thêm");
        ButtonFix btnFix = new ButtonFix("Sửa");
        ButtonDele btnDele = new ButtonDele("Xóa");

        add(txtSearch, "growx, h 35!");
        add(btnAdd, "w 100!, h 35!");
        add(btnFix, "w 100!, h 35!");
        add(btnDele, "w 100!, h 35!");
    }
}
