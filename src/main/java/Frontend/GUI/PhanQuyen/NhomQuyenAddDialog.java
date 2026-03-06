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

public class NhomQuyenAddDialog extends JDialog {
      private JTextField txtTen;
      private JTable tblQuyen;
      private DefaultTableModel model;
      private NhomQuyenBUS nqBUS = new NhomQuyenBUS();
      private ChucNangBUS cnBUS = new ChucNangBUS();
      private ArrayList<ChucNang> dsChucNang;

      public NhomQuyenAddDialog() {
            setTitle("Thêm nhóm quyền mới");
            setModal(true);
            setSize(900, 700);
            setLocationRelativeTo(null);

            setLayout(new MigLayout("fill, insets 20", "[grow]", "[]20[]10[grow]20[]"));
            getContentPane().setBackground(Color.WHITE);

            add(new JLabel("Tên nhóm quyền"), "split 2, w 120!");
            txtTen = new JTextField();
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
            loadChucNang();

            JScrollPane scroll = new JScrollPane(tblQuyen);
            scroll.setBorder(BorderFactory.createLineBorder(new Color(230, 230, 230)));
            add(scroll, "grow, wrap");

            CustomButton btnSave = new CustomButton("THÊM MỚI", Theme.PRIMARY_COLOR);
            CustomButton btnCancel = new CustomButton("Huỷ bỏ", new Color(217, 83, 79));

            btnCancel.addActionListener(e -> dispose());
            btnSave.addActionListener(e -> handleSave());

            add(btnCancel, "split 2, center, w 150!, h 40!");
            add(btnSave, "w 150!, h 40!");
      }

      private void loadChucNang() {
            dsChucNang = cnBUS.getAll();
            for (ChucNang cn : dsChucNang) {
                  model.addRow(new Object[] { "Quản lý " + cn.getTenChucNang().toLowerCase(), false, false, false,
                              false });
            }
      }

      private void handleSave() {
            String tenNQ = txtTen.getText().trim();
            if (tenNQ.isEmpty()) {
                  JOptionPane.showMessageDialog(this, "Vui lòng nhập tên nhóm quyền!");
                  return;
            }

            String maNQ = nqBUS.getNextMa(); 
            NhomQuyen nq = new NhomQuyen(maNQ, tenNQ, "");

            ArrayList<ChiTietQuyen> dsQuyen = new ArrayList<>();
            String[] hanhDongs = { "read", "create", "update", "delete" };

            for (int i = 0; i < model.getRowCount(); i++) {
                  String maCN = dsChucNang.get(i).getMaChucNang();
                  for (int col = 1; col <= 4; col++) {
                        Boolean isSelected = (Boolean) model.getValueAt(i, col);
                        if (isSelected != null && isSelected) {
                              dsQuyen.add(new ChiTietQuyen(maNQ, maCN, hanhDongs[col - 1]));
                        }
                  }
            }

            if (nqBUS.addNewRoleWithPermissions(nq, dsQuyen)) {
                  JOptionPane.showMessageDialog(this, "Thêm nhóm quyền thành công!");
                  dispose();
            } else {
                  JOptionPane.showMessageDialog(this, "Lỗi khi lưu dữ liệu!");
            }
      }
}