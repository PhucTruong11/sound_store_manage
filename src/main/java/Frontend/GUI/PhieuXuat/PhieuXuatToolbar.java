package Frontend.GUI.PhieuXuat;

import javax.swing.*;
import java.awt.*;
import net.miginfocom.swing.MigLayout;
import Frontend.Compoent.ButtonXuatExcel;
import Frontend.Compoent.ButtonXuatPdf;
import Frontend.Compoent.SearchTextField;
import Frontend.Compoent.Theme;

public class PhieuXuatToolbar extends JPanel {
    public PhieuXuatToolbar() {
        setLayout(new MigLayout("fillx, insets 10", "[grow]10[]10[]"));
        setBackground(Color.WHITE);
        putClientProperty("FlatLaf.style", "arc: " + Theme.ROUNDING_ARC );

        SearchTextField txtSearch = new SearchTextField("Tìm phiếu nhập hàng ...");
        ButtonXuatPdf btnPdf = new ButtonXuatPdf("Xuất PDF");
        ButtonXuatExcel btnExcel = new ButtonXuatExcel("Xuất EXCEL");
        

        add(txtSearch, "growx, h 35!");
        add(btnPdf, "w 105!, h 35!");
        add(btnExcel, "w 105!, h 35!");
    }
}