package Frontend.GUI.PhieuNhap;

import Frontend.Compoent.SearchTextField;
import Frontend.Compoent.Theme;
import Frontend.Compoent.ButtonXuatExcel;
import Frontend.Compoent.ButtonXuatPdf;
import net.miginfocom.swing.MigLayout;
import javax.swing.*;
import java.awt.*;

public class PhieuNhapToolbar extends JPanel{
    public PhieuNhapToolbar() {
        setLayout(new MigLayout("fillx, insets 10", "[grow]10[]10[]"));
        setBackground(Color.WHITE);
        putClientProperty("FlatLaf.style", "arc: " + Theme.ROUNDING_ARC );

        SearchTextField txtSearch = new SearchTextField("Tìm phiếu nhập hàng ...");
        ButtonXuatPdf btnPdf = new ButtonXuatPdf("XUẤT PDF");
        ButtonXuatExcel btnExcel = new ButtonXuatExcel("XUẤT EXCEL");

        add(txtSearch, "growx, h 35!");
        add(btnPdf, "w 120!, h 35!");
        add(btnExcel, "w 120!, h 35!");
    }
}
