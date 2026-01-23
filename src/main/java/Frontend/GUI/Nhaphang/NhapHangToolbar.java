package Frontend.GUI.Nhaphang;

import Frontend.Compoent.SearchTextField;
import Frontend.Compoent.ButtonAdd;
import Frontend.Compoent.ButtonDele;
import Frontend.Compoent.ButtonFix;
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
        ButtonNhapExcel btnNhapExcel = new ButtonNhapExcel("Nhập Excel");
        ButtonXuatExcel btnXuatExcel = new ButtonXuatExcel("Xuất Excel");

        add(txtSearch, "growx, h 35!");
        add(btnNhapExcel, "w 105!, h 35!");
        add(btnXuatExcel, "w 105!, h 35!");
    }
}
