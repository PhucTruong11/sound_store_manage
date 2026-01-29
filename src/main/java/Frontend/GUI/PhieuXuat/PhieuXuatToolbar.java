package Frontend.GUI.PhieuXuat;

import javax.swing.*;
import java.awt.*;
import net.miginfocom.swing.MigLayout;
import Frontend.Compoent.ButtonXuatExcel;
import Frontend.Compoent.ButtonXuatPdf;
import Frontend.Compoent.SearchTextField;
import Frontend.Compoent.Theme;
import Frontend.Compoent.XuatExcel;

public class PhieuXuatToolbar extends JPanel {
    public PhieuXuatToolbar(PhieuXuatTable table) {
        setLayout(new MigLayout("fillx, insets 10", "[grow]10[]10[]"));
        setBackground(Color.WHITE);

        SearchTextField txtSearch = new SearchTextField("Tìm phiếu xuất hàng..."); // Sửa lại text cho đúng

        txtSearch.addKeyListener(new java.awt.event.KeyAdapter() {
            @Override
            public void keyReleased(java.awt.event.KeyEvent e) {
                String keyword = txtSearch.getText().trim();
                table.loadData(keyword); // Gọi hàm loadData có tham số của bảng
            }
        });
        ButtonXuatPdf btnPdf = new ButtonXuatPdf("Xuất PDF");
        ButtonXuatExcel btnExcel = new ButtonXuatExcel("Xuất EXCEL");

        btnExcel.addActionListener(e -> {
            XuatExcel.xuat(table.getTable());
        });

        add(txtSearch, "growx, h 35!");
        add(btnPdf, "w 105!, h 35!");
        add(btnExcel, "w 105!, h 35!");
    }
}