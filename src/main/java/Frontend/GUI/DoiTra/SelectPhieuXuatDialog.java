package Frontend.GUI.DoiTra;

import java.awt.BorderLayout;
import java.util.ArrayList;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import Backend.DTO.PhieuXuat;
import Backend.DAO.DoiTraDAO;
import Frontend.Compoent.CustomButton;
import Frontend.Compoent.Table;
import Frontend.Compoent.Theme;

public class SelectPhieuXuatDialog extends JDialog {
    private JTable tbl;
    private DefaultTableModel tblModel;
    private PhieuXuat selectedPX = null;
    private CustomButton btnChon;
    private ArrayList<PhieuXuat> listPX = new ArrayList<>();

    public SelectPhieuXuatDialog() {
        setTitle("DANH SÁCH HÓA ĐƠN ĐÃ XUẤT");
        setSize(700, 450);
        setLocationRelativeTo(null);
        setModal(true);
        setLayout(new BorderLayout());

        String[] header = {"Mã Phiếu Xuất", "Mã Khách Hàng", "Ngày Xuất", "Tổng Tiền"};
        tblModel = new DefaultTableModel(header, 0) {
            @Override
            public boolean isCellEditable(int row, int column) { return false; }
        };
        tbl = new Table();
        tbl.setModel(tblModel);
        add(new JScrollPane(tbl), BorderLayout.CENTER);

        btnChon = new CustomButton("Chọn phiếu xuất",Theme.ACCENT_COLOR);

        btnChon.addActionListener(e -> {
            int row = tbl.getSelectedRow();
            if (row != -1) {
                selectedPX = listPX.get(row);
                dispose();
            } else {
                JOptionPane.showMessageDialog(this, "Vui lòng chọn một hóa đơn!");
            }
        });
        
        JPanel pnlBottom = new JPanel();
        pnlBottom.add(btnChon);
        add(pnlBottom, BorderLayout.SOUTH);

        loadData();
    }

    private void loadData() {
        tblModel.setRowCount(0);
        listPX.clear();
        
        String sql = "SELECT * FROM PhieuXuat WHERE TrangThai = 1 ORDER BY NgayXuat DESC";
        
        try (java.sql.Connection conn = Backend.DatabaseHelper.getConnection();
             java.sql.PreparedStatement ps = conn.prepareStatement(sql);
             java.sql.ResultSet rs = ps.executeQuery()) {
            
            while (rs.next()) {
                PhieuXuat px = new PhieuXuat(
                    rs.getString("MaPhieuXuat"),
                    rs.getTimestamp("NgayXuat"),
                    rs.getString("MaNV"),
                    rs.getString("MaKH"),
                    rs.getString("MaKM"),
                    rs.getDouble("TongTien"),
                    rs.getBoolean("TrangThai")
                );
                listPX.add(px);
                
                tblModel.addRow(new Object[]{
                    px.getMaPhieuXuat(),
                    px.getMaKH(),
                    px.getNgayXuat(),
                    String.format("%,.0f VNĐ", px.getTongTien())
                });
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public PhieuXuat getSelectedPX() {
        return selectedPX;
    }
}