package Frontend.GUI.NhaCungCap;

import java.awt.Color;

import Frontend.Compoent.ButtonAdd;
import Frontend.Compoent.ButtonDele;
import Frontend.Compoent.ButtonFix;
import Frontend.Compoent.SearchTextField;
import Frontend.Compoent.Theme;
import net.miginfocom.swing.MigLayout;
import javax.swing.*;
import java.awt.*;

public class NCCToolbar extends JPanel{
    private NCCTable table;

    public NCCToolbar(NCCTable table) {
        this.table = table;
        setLayout(new MigLayout("fillx, insets 10", "[grow]10[]10[]10[]"));
        setBackground(Color.WHITE);
        putClientProperty("FlatLaf.style", "arc: " + Theme.ROUNDING_ARC);

        SearchTextField txtSearch = new SearchTextField("Tìm kiếm nhà cung cấp ...");
        ButtonAdd btnAdd = new ButtonAdd("Thêm");
        ButtonFix btnFix = new ButtonFix("Sửa");
        ButtonDele btnDele = new ButtonDele("Xóa");

        add(txtSearch, "growx, h 35!");
        add(btnAdd, "w 120!, h 35!");
        add(btnFix, "w 120!, h 35!");
        add(btnDele, "w 120!, h 35!");

        btnAdd.addActionListener(e -> {
            NCCAddDialog dialog = new NCCAddDialog();
            dialog.setVisible(true);
            // table.loadData(); // Sau khi đóng Dialog, tải lại bảng để thấy dữ liệu mới
        });

    }
}
