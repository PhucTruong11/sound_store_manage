package Frontend.GUI.KhachHang;

import Frontend.Compoent.Table;
import Backend.DatabaseHelper;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.DefaultTableCellRenderer;
import java.awt.*;
import java.sql.*;

public class KhachHangTable extends JScrollPane {
    private JTable tbl;
    private DefaultTableModel tblModel;
    private KhachHangSidebar sidebar;

    public KhachHangTable(KhachHangSidebar sidebar) {
        this.sidebar = sidebar;
        initTable();
        loadDataFromMySQL();
    }

    private void initTable() {
        String[] columns = { "STT", "Mã KH", "Tên KH", "SĐT", "Địa chỉ" };
        tblModel = new DefaultTableModel(columns, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        tbl = new Table();
        tbl.setModel(tblModel);

        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(JLabel.CENTER);
        tbl.setDefaultRenderer(Object.class, centerRenderer);

        setViewportView(tbl);
        setBorder(BorderFactory.createLineBorder(new Color(230, 230, 230), 1));
    }

    public void loadDataFromMySQL() {
        tblModel.setRowCount(0);
        String sql = "SELECT * FROM khachhang";

        try (Connection conn = DatabaseHelper.getConnection();
             Statement st = conn.createStatement();
             ResultSet rs = st.executeQuery(sql)) {

            int stt = 1;
            while (rs.next()) {
                tblModel.addRow(new Object[]{
                        stt++,
                        rs.getInt("ma_kh"),
                        rs.getString("ten_kh"),
                        rs.getString("sdt"),
                        rs.getString("dia_chi")
                });
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Lỗi load dữ liệu: " + e.getMessage());
        }
    }
}
