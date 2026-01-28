package Frontend.GUI.Nhaphang;

import Backend.BUS.PhienBanSanPhamBUS;
import Backend.DTO.PhienBanSanPham;
import Frontend.Compoent.Table;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.DefaultTableCellRenderer;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

public class NhapHangTable extends JScrollPane {
    private JTable tbl;
    private DefaultTableModel tblModel;
    private PhienBanSanPhamBUS phienbansanphamBUS;
    private NhapHangSidebar sidebar; // Để truyền dữ liệu sang Sidebar khi click

    public NhapHangTable(NhapHangSidebar sidebar) {
        phienbansanphamBUS = new PhienBanSanPhamBUS();
        this.sidebar = sidebar;
        initTable();
        loadData();
    }

    private void initTable() {
        String[] columns = {"STT", "Mã Phiên Bản", "Mã Sản Phẩm", "Màu Sắc", "Giá Nhập", "Số Lượng Tồn"};
        tblModel = new DefaultTableModel(columns, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false; 
            }
        };
        
        tbl = new Table();
        tbl.setModel(tblModel);

        // tbl.getColumnModel().getColumn(0).setPreferredWidth(40);
        // tbl.getColumnModel().getColumn(0).setMaxWidth(50);
        // tbl.getColumnModel().getColumn(1).setPreferredWidth(80);
        // tbl.getColumnModel().getColumn(1).setMaxWidth(90);
        // tbl.getColumnModel().getColumn(3).setPreferredWidth(100);
        // tbl.getColumnModel().getColumn(3).setMaxWidth(110);
        // tbl.getColumnModel().getColumn(5).setPreferredWidth(100);
        // tbl.getColumnModel().getColumn(6).setPreferredWidth(70);
        // tbl.getColumnModel().getColumn(6).setMaxWidth(80);

        tbl.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int row = tbl.getSelectedRow();
                if (row == -1) return;

                String ma = tbl.getValueAt(row, 0).toString();
                String ten = tbl.getValueAt(row, 1).toString();
                String loai = tbl.getValueAt(row, 2).toString();
                String hang = tbl.getValueAt(row, 3).toString();
                String gia = tbl.getValueAt(row, 4).toString();

                // Đẩy dữ liệu sang Sidebar thông qua hàm public
                sidebar.updateInfo(ma, ten, gia);

                if (e.getClickCount() == 2) {
                    new ChiTietSanPhamDialog(null, ma, ten, gia).setVisible(true);
                }
            }
        });

        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(JLabel.CENTER);
        tbl.setDefaultRenderer(Object.class, centerRenderer);

        setViewportView(tbl);
        setBorder(BorderFactory.createLineBorder(new Color(230, 230, 230), 1));
    }

    public void loadData() {
        tblModel.setRowCount(0);
        ArrayList<PhienBanSanPham> list = phienbansanphamBUS.getAllPhienBanSanPham();
        
        int stt = 1;
        for (PhienBanSanPham pbsp : list) {
           Object[] row = {
            stt++,
            pbsp.getMaPhienBan(),
            pbsp.getMaSP(),
            pbsp.getMauSac(),
            pbsp.getGiaNhap(),
            pbsp.getSoLuongTon(),
           };
           tblModel.addRow(row);
        }
    }
}