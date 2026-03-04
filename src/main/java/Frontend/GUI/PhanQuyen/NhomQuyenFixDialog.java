package Frontend.GUI.PhanQuyen;

import Backend.BUS.NhomQuyenBUS;
import Backend.BUS.ChucNangBUS;
import Backend.DTO.NhomQuyen;
import Backend.DTO.ChucNang;
import Backend.DTO.ChiTietQuyen;
import Frontend.Compoent.CustomButton;
import Frontend.Compoent.Table;
import Frontend.Compoent.Theme;
import net.miginfocom.swing.MigLayout;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.ArrayList;

public class NhomQuyenFixDialog extends JDialog {
      private JTextField txtTen;
      private JTable tblQuyen;
      private DefaultTableModel model;
      private String maNhomQuyen;
      private NhomQuyenBUS nqBUS = new NhomQuyenBUS();
      private ChucNangBUS cnBUS = new ChucNangBUS();
      private ArrayList<ChucNang> dsChucNang;

      public NhomQuyenFixDialog(String ma, String ten, String moTa) {
            setTitle("Chỉnh sửa nhóm quyền: " + ma);
            setModal(true);
            setSize(900, 700);
            setLocationRelativeTo(null);
            
            setLayout(new MigLayout("fill, insets 20", "[grow]", "[]20[]10[grow]20[]"));
            getContentPane().setBackground(Color.WHITE);
            this.maNhomQuyen = ma;

            add(new JLabel("Tên nhóm quyền"), "split 2, w 120!");
            txtTen = new JTextField(ten);
            txtTen.putClientProperty("FlatLaf.style", "arc: 10");
            add(txtTen, "growx, h 35!, wrap");

            JLabel lblTitle = new JLabel("Danh mục chức năng");
            lblTitle.setFont(new Font("Segoe UI", Font.BOLD, 15));
            add(lblTitle, "wrap");

            String[] cols = { "Danh mục chức năng", "Xem", "Tạo mới", "Cập nhật", "Xoá" };
            model = new DefaultTableModel(cols, 0) {
                  @Override
                  public Class<?> getColumnClass(int col) {
                        return col == 0 ? String.class : Boolean.class;
                  }

                  @Override
                  public boolean isCellEditable(int row, int col) {
                        return col != 0;
                  }
            };

            tblQuyen = new Table();
            tblQuyen.setModel(model);
            tblQuyen.setRowHeight(40);
            loadData(); 

            add(new JScrollPane(tblQuyen), "grow, wrap");

            CustomButton btnUpdate = new CustomButton("LƯU THAY ĐỔI", Theme.PRIMARY_COLOR);
            CustomButton btnCancel = new CustomButton("Huỷ bỏ", new Color(217, 83, 79));

            btnCancel.addActionListener(e -> dispose());
            btnUpdate.addActionListener(e -> handleUpdate());

            add(btnCancel, "split 2, center, w 150!, h 40!");
            add(btnUpdate, "w 150!, h 40!");
      }

      private void loadData() {
            dsChucNang = cnBUS.getAll();
            ArrayList<ChiTietQuyen> listQuyenHienTai = nqBUS.getQuyenCuaNhom(maNhomQuyen);

            for (ChucNang cn : dsChucNang) {
                  String maCN = cn.getMaChucNang();
                  model.addRow(new Object[] {
                              "Quản lý " + cn.getTenChucNang().toLowerCase(),
                              checkInList(listQuyenHienTai, maCN, "read"),
                              checkInList(listQuyenHienTai, maCN, "create"),
                              checkInList(listQuyenHienTai, maCN, "update"),
                              checkInList(listQuyenHienTai, maCN, "delete")
                  });
            }
      }

      private boolean checkInList(ArrayList<ChiTietQuyen> list, String maCN, String action) {
            for (ChiTietQuyen q : list) {
                  if (q.getMaChucNang().equalsIgnoreCase(maCN) && q.getHanhDong().equalsIgnoreCase(action)) {
                        return true;
                  }
            }
            return false;
      }

      private void handleUpdate() {
            String tenMoi = txtTen.getText().trim();
            if (tenMoi.isEmpty()) {
                  JOptionPane.showMessageDialog(this, "Tên nhóm quyền không được để trống!");
                  return;
            }

            ArrayList<ChiTietQuyen> dsQuyenMoi = new ArrayList<>();
            String[] actions = { "read", "create", "update", "delete" };

            for (int i = 0; i < model.getRowCount(); i++) {
                  String maCN = dsChucNang.get(i).getMaChucNang();
                  for (int col = 1; col <= 4; col++) {
                        if ((Boolean) model.getValueAt(i, col)) {
                              dsQuyenMoi.add(new ChiTietQuyen(maNhomQuyen, maCN, actions[col - 1]));
                        }
                  }
            }

            NhomQuyen nq = new NhomQuyen(maNhomQuyen, tenMoi, "");
            if (nqBUS.updateRoleWithPermissions(nq, dsQuyenMoi)) {
                  JOptionPane.showMessageDialog(this, "Cập nhật thành công!");
                  dispose();
            } else {
                  JOptionPane.showMessageDialog(this, "Lỗi khi lưu dữ liệu!");
            }
      }
}