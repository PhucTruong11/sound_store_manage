package Frontend.GUI.SanPham;

import Backend.BUS.PhienBanSanPhamBUS;
import Backend.DTO.PhienBanSanPham;
import Frontend.Compoent.CustomButton;
import Frontend.Compoent.Table;
import Frontend.Compoent.Theme;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.text.DecimalFormat;
import java.util.ArrayList;

public class PhienBanSPDialog extends JDialog {
        private JTable tblPB;
        private DefaultTableModel model;
        private PhienBanSanPhamBUS pbBUS = new PhienBanSanPhamBUS();
        private String maSPHT, tenSPHT;
        private boolean isEditMode;

        public PhienBanSPDialog(JFrame parent, String maSP, String tenSP, boolean isEditMode) {
                super(parent, isEditMode ? "Danh sách phiên bản - " + tenSP : "Danh sách phiên bản - " + tenSP, true);
                this.maSPHT = maSP;
                this.tenSPHT = tenSP;
                this.isEditMode = isEditMode;
                setSize(900, 500);
                setLocationRelativeTo(null);
                setLayout(new BorderLayout());
                getContentPane().setBackground(Color.WHITE);

                initComponents();
                loadData();
        }

        public void initComponents() {
                // Header
                JPanel pnlHeader = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 15));
                pnlHeader.setBackground(Theme.PRIMARY_COLOR);
                JLabel lblHeader = new JLabel(isEditMode ? "DANH SÁCH PHIÊN BẢN - " + tenSPHT.toUpperCase(): "DANH SÁCH PHIÊN BẢN - " + tenSPHT.toUpperCase());
                lblHeader.setFont(new Font("Segoe UI", Font.BOLD, 16));
                lblHeader.setForeground(Color.WHITE);
                pnlHeader.add(lblHeader);
                add(pnlHeader, BorderLayout.NORTH);

                String[] columns = { "Mã PB", "Màu Sắc", "Công Suất", "Pin", "Kết Nối", "Giá Nhập", "Giá Bán","Tồn Kho" };
                model = new DefaultTableModel(columns, 0) {
                        @Override
                        public boolean isCellEditable(int row, int column) {
                                return false;
                        }
                };
                tblPB = new Table();
                tblPB.setModel(model);

                JScrollPane scroll = new JScrollPane(tblPB);
                scroll.getViewport().setBackground(Color.WHITE);
                scroll.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
                add(scroll, BorderLayout.CENTER);

                // Footer
                JPanel pnlFooter = new JPanel(new FlowLayout(FlowLayout.RIGHT));
                pnlFooter.setBackground(Color.WHITE);
                if (isEditMode) {
                        CustomButton btnThem = new CustomButton("Thêm", Theme.ACCENT_COLOR);
                        CustomButton btnSua = new CustomButton("Sửa", Theme.WARNING_COLOR);
                        CustomButton btnQLCT = new CustomButton("Quản Lý Chi Tiết", Theme.WARNING_COLOR);
                        CustomButton btnXoa = new CustomButton("Xóa", Theme.DANGER_COLOR);

                        btnThem.setPreferredSize(new Dimension(80, 40));
                        btnSua.setPreferredSize(new Dimension(80, 40));
                        btnQLCT.setPreferredSize(new Dimension(150, 40));
                        btnXoa.setPreferredSize(new Dimension(80, 40));

                        pnlFooter.add(btnThem);
                        pnlFooter.add(btnSua);
                        pnlFooter.add(btnXoa);
                        pnlFooter.add(btnQLCT);

                        addEditEvents(btnThem, btnSua, btnQLCT, btnXoa);
                } else {
                        CustomButton btnChiTiet = new CustomButton("Xem Chi Tiết", Theme.WARNING_COLOR);
                        btnChiTiet.setPreferredSize(new Dimension(120, 40));
                        pnlFooter.add(btnChiTiet);
                        btnChiTiet.addActionListener(e -> {
                                int selectedRow = tblPB.getSelectedRow();
                                if (selectedRow == -1) {
                                        JOptionPane.showMessageDialog(this, "Vui lòng chọn 1 phiên bàn để xem chi tiết");
                                        return;
                                }
                                String maPB = tblPB.getValueAt(selectedRow, 0).toString();
                                JFrame frameCha = (JFrame) SwingUtilities.getWindowAncestor(this);
                                ChiTietSPDialog dialog = new ChiTietSPDialog(frameCha, maPB, false);
                                dialog.setVisible(true);
                        });
                }

                CustomButton btnDong = new CustomButton("Thoát", new Color(149, 165, 166));
                btnDong.setPreferredSize(new Dimension(80, 40));
                btnDong.addActionListener(e -> dispose());
                pnlFooter.add(btnDong);
                add(pnlFooter, BorderLayout.SOUTH);
        }

        private void addEditEvents(JButton btnThem, JButton btnSua, JButton btnQLCT, JButton btnXoa) {
                btnThem.addActionListener(e -> {
                        InputPhienBanDialog inputDlg = new InputPhienBanDialog((JFrame) SwingUtilities.getWindowAncestor(this), maSPHT, null);
                        inputDlg.setVisible(true);
                        if (inputDlg.isSuccess())
                        loadData();
                });
                btnSua.addActionListener(e -> {
                        int row = tblPB.getSelectedRow();
                        if (row == -1) {
                                JOptionPane.showMessageDialog(this, "Vui lòng chọn phiên bản để sửa!", "Thông báo",JOptionPane.WARNING_MESSAGE);
                                return;
                        }
                        PhienBanSanPham pb = new PhienBanSanPham();
                        pb.setMaPhienBan(tblPB.getValueAt(row, 0).toString());
                        pb.setMaSP(this.maSPHT);
                        pb.setMauSac(tblPB.getValueAt(row, 1).toString());
                        pb.setCongSuat(tblPB.getValueAt(row, 2).toString());
                        pb.setPin(tblPB.getValueAt(row, 3).toString());
                        pb.setKetNoi(tblPB.getValueAt(row, 4).toString());
                        try {
                                String giaNhapStr = tblPB.getValueAt(row, 5).toString().replaceAll("[^0-9]", "");
                                String giaBanStr = tblPB.getValueAt(row, 6).toString().replaceAll("[^0-9]", "");
                                String tonKhoStr = tblPB.getValueAt(row, 7).toString().replaceAll("[^0-9]", "");

                                pb.setGiaNhap(Double.parseDouble(giaNhapStr));
                                pb.setGiaBan(Double.parseDouble(giaBanStr));
                                pb.setSoLuongTon(Integer.parseInt(tonKhoStr));
                        } catch (Exception ex) {
                                JOptionPane.showMessageDialog(this, "Lỗi chuyển đổi dữ liệu số!");
                                return;
                        }
                        InputPhienBanDialog inputDlg = new InputPhienBanDialog((JFrame) SwingUtilities.getWindowAncestor(this), maSPHT, pb);
                        inputDlg.setVisible(true);
                        if (inputDlg.isSuccess()) {
                                loadData();
                        }
                });

                btnQLCT.addActionListener(e -> {
                        int row = tblPB.getSelectedRow();
                        if (row == -1) {
                                JOptionPane.showMessageDialog(this, "Vui lòng chọn phiên bản để quản lý");
                                return;
                        }
                        String maPB = tblPB.getValueAt(row, 0).toString();
                        ChiTietSPDialog dialog = new ChiTietSPDialog((JFrame) SwingUtilities.getWindowAncestor(this),maPB, true);
                        dialog.setVisible(true);
                        loadData();
                });

                btnXoa.addActionListener(e -> {
                       int row=tblPB.getSelectedRow();
                       if(row==-1){
                        JOptionPane.showMessageDialog(this,"Vui lòng chọn phiên bản để xóa","Thông báo",JOptionPane.WARNING_MESSAGE);
                        return;
                       } 
                       String maPB=tblPB.getValueAt(row,0).toString();
                       int op=JOptionPane.showConfirmDialog(this,"Bạn có muốn xóa phiên bản "+maPB+" không?\n","Xác nhận",JOptionPane.YES_NO_OPTION,JOptionPane.WARNING_MESSAGE);

                       if(op==JOptionPane.YES_OPTION){
                        if(pbBUS.delete(maPB)){
                                //JOptionPane.showMessageDialog(this,"Xóa thành công");
                                loadData();
                        }
                        else JOptionPane.showMessageDialog(this,"Xóa thất bại");
                       }
                });
        }

        private void loadData() {
                ArrayList<PhienBanSanPham> list = pbBUS.getByMaSP(maSPHT);
                model.setRowCount(0);
                DecimalFormat formatter = new DecimalFormat("###,###");

                for (PhienBanSanPham pb : list) {
                        model.addRow(new Object[] {
                                pb.getMaPhienBan(),pb.getMauSac(),pb.getCongSuat(),pb.getPin(),pb.getKetNoi(),formatter.format(pb.getGiaNhap()),formatter.format(pb.getGiaBan()),pb.getSoLuongTon()
                        });
                }
        }
}
