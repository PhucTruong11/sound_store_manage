package Frontend.GUI.DoiTra;

import java.awt.Color;
import java.util.ArrayList;

import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import Backend.BUS.DoiTraBUS;
import Backend.DTO.DoiTra;
import Frontend.Compoent.ButtonAdd;
import Frontend.Compoent.ButtonDele;
import Frontend.Compoent.ButtonFix;
import Frontend.Compoent.ButtonXuatExcel;
import Frontend.Compoent.SearchTextField;
import Frontend.Compoent.Theme;
import net.miginfocom.swing.MigLayout;

public class DoiTraToolbar extends JPanel {

    private DoiTraTable table;
    private DoiTraBUS doiTraBUS = new DoiTraBUS();

    private ButtonAdd btnAdd;
    private ButtonFix btnFix;
    private ButtonDele btnDele;
    private ButtonXuatExcel btnXuatExcel;
    private SearchTextField txtSearch;

    public DoiTraToolbar(DoiTraTable table) {

        this.table = table;
        setLayout(new MigLayout("fillx, insets 10", "[grow]10[]10[]10[]"));
        setBackground(Color.WHITE);
        putClientProperty("FlatLaf.style", "arc: " + Theme.ROUNDING_ARC);

        txtSearch = new SearchTextField("Tìm kiếm đổi trả ...");
        btnAdd = new ButtonAdd("Thêm");
        btnFix = new ButtonFix("Sửa");
        btnDele = new ButtonDele("Xóa");
        btnXuatExcel = new ButtonXuatExcel("Xuất Excel");

        add(txtSearch, "growx, h 35!");
        add(btnAdd, "w 95!, h 35!");
        add(btnFix, "w 95!, h 35!");
        add(btnDele, "w 95!, h 35!");
        add(btnXuatExcel, "w 105!, h 35!");
        initEvent();
    }
    private void initEvent() {

        btnAdd.addActionListener(e -> {

            DoiTraAddDialog dialog = new DoiTraAddDialog();
            dialog.setVisible(true);

            table.loadData();
        });

        btnFix.addActionListener(e -> {
            JTable tbl = table.getTable();
            int row = tbl.getSelectedRow();

            if (row == -1) {
                JOptionPane.showMessageDialog(this, "Vui lòng chọn dòng cần sửa");
                return;
            }

            DefaultTableModel model = table.getModel();

            String ma = model.getValueAt(row, 0).toString();
            String maPX = model.getValueAt(row, 1).toString();   
            String maKH = model.getValueAt(row, 2).toString();
            String ngay = model.getValueAt(row, 3).toString();    
            String sl = model.getValueAt(row, 5).toString(); 
            String tinhTrang = model.getValueAt(row, 6).toString(); 
            String maPB = ""; 

            DoiTraFixDialog dialog = new DoiTraFixDialog(ma, maPX, maKH, maPB, ngay, "Lý do", sl, tinhTrang);
            dialog.setVisible(true);
            table.loadData();
        });

        btnDele.addActionListener(e -> {

            JTable tbl = table.getTable();
            int row = tbl.getSelectedRow();

            if (row == -1) {
                JOptionPane.showMessageDialog(this, "Vui lòng chọn dòng cần xóa");
                return;
            }

            String ma = table.getModel().getValueAt(row, 0).toString();

            int confirm = JOptionPane.showConfirmDialog(
                    this,
                    "Bạn có chắc muốn xóa?",
                    "Xác nhận",
                    JOptionPane.YES_NO_OPTION
            );

            if (confirm == JOptionPane.YES_OPTION) {

                if (doiTraBUS.delete(ma)) {
                    JOptionPane.showMessageDialog(this, "Xóa thành công");
                    table.loadData();
                } else {
                    JOptionPane.showMessageDialog(this, "Xóa thất bại");
                }
            }
        });

        txtSearch.addKeyListener(new java.awt.event.KeyAdapter() {
            @Override
            public void keyReleased(java.awt.event.KeyEvent e) {
                String keyword = txtSearch.getText().trim();
                ArrayList<DoiTra> result = doiTraBUS.search(keyword);
                table.updateTable(result); 
            }
        });
    }
}