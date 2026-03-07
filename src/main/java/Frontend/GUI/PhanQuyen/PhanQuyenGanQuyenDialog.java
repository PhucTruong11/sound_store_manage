package Frontend.GUI.PhanQuyen;

import java.awt.*;
import java.util.ArrayList;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import net.miginfocom.swing.MigLayout;
import Backend.BUS.NhomQuyenBUS;
import Backend.BUS.ChucNangBUS;
import Backend.DTO.ChucNang;
import Backend.DTO.ChiTietQuyen;
import Frontend.Compoent.Table;
import Frontend.Compoent.CustomButton;
import Frontend.Compoent.Theme;

public class PhanQuyenGanQuyenDialog extends JDialog {
      private JTable tblQuyen;
      private DefaultTableModel model;
      private JTextField txtTenNhomQuyen;
      private String maNhomQuyen;
      private NhomQuyenBUS nqBUS = new NhomQuyenBUS();
      private ChucNangBUS cnBUS = new ChucNangBUS();
      private ArrayList<ChucNang> allCN;

      public PhanQuyenGanQuyenDialog(JFrame parent, String maNQ, String tenNQ) {
            super(parent, "Chi tiết nhóm quyền", true);
            this.maNhomQuyen = maNQ;
            setSize(900, 760);
            setLocationRelativeTo(parent);
            setLayout(new MigLayout("fill, insets 20", "[grow]", "[]20[]20[grow]20[]"));
            getContentPane().setBackground(Color.WHITE);

            add(new JLabel("Tên nhóm quyền"), "split 2, w 120!");
            txtTenNhomQuyen = new JTextField(tenNQ);
            txtTenNhomQuyen.putClientProperty("FlatLaf.style", "arc: 10");
            add(txtTenNhomQuyen, "growx, h 35!, wrap");

            JLabel lblTableTitle = new JLabel("Danh mục chức năng");
            lblTableTitle.setFont(new Font("Segoe UI", Font.BOLD, 15));
            add(lblTableTitle, "wrap");

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
            tblQuyen.setShowGrid(false);
            tblQuyen.setIntercellSpacing(new Dimension(0, 0));

            loadData();
            add(new JScrollPane(tblQuyen), "grow, wrap");

            JPanel pnlButtons = new JPanel(new MigLayout("insets 0", "[]10[]", ""));
            pnlButtons.setBackground(Color.WHITE);

            CustomButton btnSave = new CustomButton("LƯU THAY ĐỔI", Theme.PRIMARY_COLOR);
            CustomButton btnCancel = new CustomButton("Huỷ bỏ", new Color(217, 83, 79)); // Màu đỏ giống ảnh

            btnSave.addActionListener(e -> handleSave());
            btnCancel.addActionListener(e -> dispose());

            add(btnCancel, "split 2, center, w 150!, h 40!");
            add(btnSave, "w 150!, h 40!");
      }

      private void loadData() {
            model.setRowCount(0);
            allCN = cnBUS.getAll();
            ArrayList<ChiTietQuyen> listQuyenHienTai = nqBUS.getQuyenCuaNhom(maNhomQuyen);

            for (ChucNang cn : allCN) {
                  String maCN = cn.getMaChucNang();

                  boolean isDefaultAll = maNhomQuyen.equals("NQ01") && listQuyenHienTai.isEmpty();

                  model.addRow(new Object[] {
                              "Quản lý " + cn.getTenChucNang().toLowerCase(),
                              isDefaultAll || checkInList(listQuyenHienTai, maCN, "read"),
                              isDefaultAll || checkInList(listQuyenHienTai, maCN, "create"),
                              isDefaultAll || checkInList(listQuyenHienTai, maCN, "update"),
                              isDefaultAll || checkInList(listQuyenHienTai, maCN, "delete")
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

      private void handleSave() {
            ArrayList<ChiTietQuyen> dsQuyenMoi = new ArrayList<>();
            String[] actions = { "read", "create", "update", "delete" };

            for (int i = 0; i < model.getRowCount(); i++) {
                  String maCN = allCN.get(i).getMaChucNang();
                  for (int col = 1; col <= 4; col++) {
                        if ((Boolean) model.getValueAt(i, col)) {
                              dsQuyenMoi.add(new ChiTietQuyen(maNhomQuyen, maCN, actions[col - 1]));
                        }
                  }
            }

            if (nqBUS.saveQuyen(maNhomQuyen, dsQuyenMoi)) {
                  JOptionPane.showMessageDialog(this, "Cập nhật thành công!");
                  dispose();
            }
      }
}