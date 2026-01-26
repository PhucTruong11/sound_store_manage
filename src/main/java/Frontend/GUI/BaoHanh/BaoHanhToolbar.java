package Frontend.GUI.BaoHanh;

import java.awt.Color;

import Frontend.Compoent.ButtonAdd;
import Frontend.Compoent.ButtonDele;
import Frontend.Compoent.ButtonFix;
import Frontend.Compoent.ButtonXuatExcel;
import Frontend.Compoent.SearchTextField;
import Frontend.Compoent.Theme;
import net.miginfocom.swing.MigLayout;
import javax.swing.*;
import java.awt.*;

public class BaoHanhToolbar extends JPanel {
    private BaoHanhTable table;

    public BaoHanhToolbar(BaoHanhTable table) {
        this.table = table;
        setLayout(new MigLayout("fillx, insets 10", "[grow]10[]10[]10[]"));
        setBackground(Color.WHITE);
        putClientProperty("FlatLaf.style", "arc: " + Theme.ROUNDING_ARC);

        SearchTextField txtSearch = new SearchTextField("Tìm kiếm thiết bị đã bão hành ...");
        ButtonAdd btnAdd = new ButtonAdd("Thêm");
        ButtonFix btnFix = new ButtonFix("Sửa");
        ButtonDele btnDele = new ButtonDele("Xóa");
        ButtonXuatExcel btnXuatExcel = new ButtonXuatExcel("Xuất Excel");

        add(txtSearch, "growx, h 35!");
        add(btnAdd, "w 95!, h 35!");
        add(btnFix, "w 95!, h 35!");
        add(btnDele, "w 95!, h 35!");
        add(btnXuatExcel, "w 105!, h 35!");

        btnAdd.addActionListener(e -> {
            BaoHanhAddDialog dialog = new BaoHanhAddDialog();
            dialog.setVisible(true);
        });

        btnFix.addActionListener(e -> {
            int selectedRow = table.getTbl().getSelectedRow();

            if (selectedRow == -1) {
                JOptionPane.showMessageDialog(this, "Vui lòng chọn 1 hàng để sửa!", "Thông báo",
                        JOptionPane.WARNING_MESSAGE);
                return;
            }

            // Lấy dữ liệu từ các cột
            String ma = table.getTbl().getValueAt(selectedRow, 0).toString();
            String ten = table.getTbl().getValueAt(selectedRow, 1).toString();
            String phanTram = table.getTbl().getValueAt(selectedRow, 2).toString();

            // Mở Dialog, truyền dữ liệu qua
            BaoHanhFixDialog dialog = new BaoHanhFixDialog(ma, ten, phanTram);
            dialog.setVisible(true);
        });

        btnDele.addActionListener(e -> {
            int selectedRow = table.getTbl().getSelectedRow();

            if (selectedRow == -1) {
                JOptionPane.showMessageDialog(this, "Vui lòng chọn hàng cần xóa!");
                return;
            }

            String tenBaoHanh = table.getTbl().getValueAt(selectedRow, 1).toString();
            int opt = JOptionPane.showConfirmDialog(this,
                    "Bạn có chắc muốn xóa nhà cung cấp: " + tenBaoHanh + "?",
                    "Xác nhận xóa", JOptionPane.YES_NO_OPTION);

            if (opt == JOptionPane.YES_OPTION) {
                System.out.println("Đã xóa hàng thứ: " + selectedRow);
                // table.loadData(); // Cập nhật lại bảng
            }
        });
    }
}
