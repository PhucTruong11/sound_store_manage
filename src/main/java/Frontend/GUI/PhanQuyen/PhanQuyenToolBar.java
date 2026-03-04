package Frontend.GUI.PhanQuyen;

import java.awt.Color;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import javax.swing.*;
import Frontend.Compoent.ButtonAdd;
import Frontend.Compoent.ButtonDele;
import Frontend.Compoent.ButtonFix;
import Frontend.Compoent.ButtonXuatExcel;
import Frontend.Compoent.SearchTextField;
import Frontend.Compoent.Theme;
import Backend.BUS.NhomQuyenBUS; 
import net.miginfocom.swing.MigLayout;

public class PhanQuyenToolBar extends JPanel {
      private PhanQuyenTable table;
      private NhomQuyenBUS nqBUS = new NhomQuyenBUS();

      public PhanQuyenToolBar(PhanQuyenTable table) {
            this.table = table;
            setLayout(new MigLayout("fillx, insets 10", "[grow]10[]10[]10[]10[]"));
            setBackground(Color.WHITE);
            putClientProperty("FlatLaf.style", "arc: " + Theme.ROUNDING_ARC);

            SearchTextField txtSearch = new SearchTextField("Tìm kiếm nhóm quyền (Mã hoặc tên)...");
            ButtonAdd btnAdd = new ButtonAdd("Thêm");
            ButtonFix btnFix = new ButtonFix("Sửa");
            ButtonDele btnDele = new ButtonDele("Xóa");
            ButtonXuatExcel btnXuatExcel = new ButtonXuatExcel("Xuất Excel");

            add(txtSearch, "growx, h 35!");
            add(btnAdd, "w 95!, h 35!");
            add(btnFix, "w 95!, h 35!");
            add(btnDele, "w 95!, h 35!");
            add(btnXuatExcel, "w 110!, h 35!");

            txtSearch.addKeyListener(new KeyAdapter() {
                  @Override
                  public void keyReleased(KeyEvent e) {
                        String query = txtSearch.getText().toLowerCase().trim();
                        table.loadDataBySearch(query);
                  }
            });

            btnAdd.addActionListener(e -> {
                  NhomQuyenAddDialog dialog = new NhomQuyenAddDialog();
                  dialog.setVisible(true);
                  table.loadData();
            });

            btnFix.addActionListener(e -> {
                  int selectedRow = table.getTbl().getSelectedRow();
                  if (selectedRow == -1) {
                        JOptionPane.showMessageDialog(this, "Vui lòng chọn 1 nhóm quyền để sửa!", "Thông báo",
                                    JOptionPane.WARNING_MESSAGE);
                        return;
                  }
                  String ma = table.getTbl().getValueAt(selectedRow, 1).toString();
                  String ten = table.getTbl().getValueAt(selectedRow, 2).toString();
                  String moTa = table.getTbl().getValueAt(selectedRow, 3).toString();

                  NhomQuyenFixDialog dialog = new NhomQuyenFixDialog(ma, ten, moTa);
                  dialog.setVisible(true);
                  table.loadData();
            });

            btnDele.addActionListener(e -> {
                  int selectedRow = table.getTbl().getSelectedRow();
                  if (selectedRow == -1) {
                        JOptionPane.showMessageDialog(this, "Vui lòng chọn nhóm quyền cần xóa!");
                        return;
                  }

                  String ma = table.getTbl().getValueAt(selectedRow, 1).toString();

                  if (ma.equals("NQ01")) {
                        JOptionPane.showMessageDialog(this, "Không thể xóa nhóm quyền Quản trị tối cao!", "Lỗi",
                                    JOptionPane.ERROR_MESSAGE);
                        return;
                  }

                  int opt = JOptionPane.showConfirmDialog(this,
                              "Xóa nhóm quyền: " + ma + "?\n(Lưu ý: Các tài khoản thuộc nhóm này sẽ bị ảnh hưởng)",
                              "Xác nhận", JOptionPane.YES_NO_OPTION);
                  if (opt == JOptionPane.YES_OPTION) {
                        table.loadData();
                        JOptionPane.showMessageDialog(this, "Đã xóa thành công!");
                  }
            });

            btnXuatExcel.addActionListener(e -> {
                  Frontend.Compoent.XuatExcel.xuat(table.getTbl());
            });
      }
}