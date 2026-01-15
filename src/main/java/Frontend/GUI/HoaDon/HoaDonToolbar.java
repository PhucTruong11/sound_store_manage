package Frontend.GUI.HoaDon;

import javax.swing.*;
import net.miginfocom.swing.MigLayout;
import Frontend.Compoent.Theme;
import java.awt.Color;
import Frontend.Compoent.ButtonXuatExcel;
import Frontend.Compoent.ButtonXuatPdf;
import Frontend.Compoent.SearchTextField;


public class HoaDonToolbar extends JPanel {
    public HoaDonToolbar() {
        setLayout(new MigLayout("fillx, insets 10", "[grow]10[]10[]"));
        setBackground(Color.WHITE);
        putClientProperty("FlatLaf.style", "arc: " + Theme.ROUNDING_ARC);

        SearchTextField txtSearch = new SearchTextField("Tìm kiếm hóa đơn...");
        ButtonXuatPdf btnPdf = new ButtonXuatPdf("XUẤT PDF");
        ButtonXuatExcel btnExport = new ButtonXuatExcel("XUẤT EXCEL");

        add(txtSearch, "growx, h 35!");
        add(btnPdf, "w 120!, h 35!");
        add(btnExport, "w 120!, h 35!");
    }
}