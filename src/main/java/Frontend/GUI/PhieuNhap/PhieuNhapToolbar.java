package Frontend.GUI.PhieuNhap;

import Frontend.Compoent.SearchTextField;
import Frontend.Compoent.Theme;
import Frontend.Compoent.ButtonXuatExcel;
import Frontend.Compoent.ButtonXuatPdf;
import net.miginfocom.swing.MigLayout;
import javax.swing.*;
import java.awt.*;

public class PhieuNhapToolbar extends JPanel{
    public PhieuNhapToolbar(PhieuNhapTable table) {
        setLayout(new MigLayout("fillx, insets 10", "[grow]10[]10[]"));
        setBackground(Color.WHITE);
        putClientProperty("FlatLaf.style", "arc: " + Theme.ROUNDING_ARC );

        SearchTextField txtSearch = new SearchTextField("Tìm phiếu nhập hàng ...");
        ButtonXuatPdf btnPdf = new ButtonXuatPdf("Xuất PDF");
        ButtonXuatExcel btnExcel = new ButtonXuatExcel("Xuất EXCEL");
        

        add(txtSearch, "growx, h 35!");
        add(btnPdf, "w 105!, h 35!");
        add(btnExcel, "w 105!, h 35!");

        btnPdf.addActionListener(e -> {
            Frontend.Compoent.XuatPDF.xuat(table.getTable(), "DANH SÁCH PHIẾU NHẬP HÀNG");
        });

        btnExcel.addActionListener(e -> {
            Frontend.Compoent.XuatExcel.xuat(table.getTable());
        });
    }
}
