package Frontend.GUI.NhanVien;

import java.awt.Color;
import Frontend.Compoent.ButtonAdd;
import Frontend.Compoent.ButtonDele;
import Frontend.Compoent.ButtonFix;
import Frontend.Compoent.ButtonRefresh;
import Frontend.Compoent.ButtonXuatExcel;
import Frontend.Compoent.ButtonXuatPdf;
import Frontend.Compoent.SearchTextField;
import Frontend.Compoent.Theme;
import net.miginfocom.swing.MigLayout;
import javax.swing.*;

public class NhanVienToolbar extends JPanel {
    private NhanVienTable table;

    public NhanVienToolbar(NhanVienTable table) {
        this.table = table;
        setLayout(new MigLayout("fillx, insets 10", "[grow]10[]10[]10[]10[]"));
        setBackground(Color.WHITE);
        putClientProperty("FlatLaf.style", "arc: " + Theme.ROUNDING_ARC);

        SearchTextField txtSearch = new SearchTextField("Tìm kiếm nhân viên ...");
        ButtonAdd btnAdd = new ButtonAdd("Thêm");
        ButtonFix btnFix = new ButtonFix("Sửa");
        ButtonDele btnDele = new ButtonDele("Xóa");
        //ButtonRefresh btnRefresh = new ButtonRefresh("Làm mới");
        ButtonXuatPdf btnXuatPdf = new ButtonXuatPdf("Xuất PDF");
        ButtonXuatExcel btnXuatExcel = new ButtonXuatExcel("Xuất Excel");

        add(txtSearch, "growx, h 35!");
        add(btnAdd, "w 120!, h 35!");
        add(btnFix, "w 120!, h 35!");
        add(btnDele, "w 120!, h 35!");
        //add(btnRefresh, "w 120!, h 35!");
        add(btnXuatPdf, "w 120!, h 35!");
        add(btnXuatExcel, "w 120!, h 35!");

        btnAdd.addActionListener(e -> {
            NhanVienAddDialog dialog = new NhanVienAddDialog();
            dialog.setVisible(true);
            table.loadDataFromMySQL();
        });

        //btnRefresh.addActionListener(e -> table.loadDataFromMySQL());
    }
}
