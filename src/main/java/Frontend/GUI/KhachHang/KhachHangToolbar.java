package Frontend.GUI.KhachHang;

import Frontend.Compoent.SearchTextField;
import Frontend.Compoent.Theme;
import Frontend.Compoent.ButtonAdd;
import Frontend.Compoent.ButtonFix;
import Frontend.Compoent.ButtonXuatExcel;
import Frontend.Compoent.ButtonXuatPdf;
import Frontend.Compoent.ButtonDele;
import net.miginfocom.swing.MigLayout;
import javax.swing.*;
import java.awt.*;

public class KhachHangToolbar extends JPanel {
    public KhachHangToolbar() {
        setLayout(new MigLayout("fillx, insets 10", "[grow]10[]10[]10[]"));
        setBackground(Color.WHITE);
        putClientProperty("FlatLaf.style", "arc: " + Theme.ROUNDING_ARC);

        SearchTextField txtSearch = new SearchTextField("Tìm khách hàng ...");
        ButtonAdd btnAdd = new ButtonAdd("Thêm");
        ButtonFix btnFix = new ButtonFix("Sửa");
        ButtonDele btnDele = new ButtonDele("Xóa");
        ButtonXuatPdf btnXuatPdf = new ButtonXuatPdf("Xuất PDF");
        ButtonXuatExcel btnXuatExcel = new ButtonXuatExcel("Xuất Excel");

        add(txtSearch, "growx, h 35!");
        add(btnAdd, "w 120!, h 35!");
        add(btnFix, "w 120!, h 35!");
        add(btnDele, "w 120!, h 35!");
        add(btnXuatPdf, "w 120!, h 35!");
        add(btnXuatExcel, "w 120!, h 35!");

        btnAdd.addActionListener(e -> {
            KhachHangAddDialog dialog = new KhachHangAddDialog();
            dialog.setVisible(true);
        });
    }
}
