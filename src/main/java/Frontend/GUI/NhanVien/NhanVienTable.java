package Frontend.GUI.NhanVien;

import Frontend.Compoent.Table;
import Frontend.Compoent.Theme;
import Backend.DatabaseHelper;
import net.miginfocom.swing.MigLayout;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.DefaultTableCellRenderer;
import java.awt.*;
import java.sql.*;

public class NhanVienTable extends JPanel {
    private JTable tbl;
    private DefaultTableModel tblModel;
    private JScrollPane scrollPane;

    public NhanVienTable() {
        setLayout(new MigLayout("wrap 1, fill, insets 10", "[grow]", "[]15[grow]"));
        setBackground(Color.WHITE);
        putClientProperty("FlatLaf.style", "arc: " + Theme.ROUNDING_ARC);

        initHeader();
        initTable();
        loadDataFromMySQL();
    }

    private void initHeader() {
        JPanel pnlHeader = new JPanel(new MigLayout("insets 10", "[]push[]"));
        pnlHeader.putClientProperty("FlatLaf.style", "arc: " + Theme.ROUNDING_ARC);
        JLabel lblTitle = new JLabel("Nhân viên");
        lblTitle.setFont(new Font("Segoe UI", Font.BOLD, 16));
        pnlHeader.add(lblTitle);
        add(pnlHeader, "growx");
    }

    private void initTable() {
        String[] columns = {"STT", "Mã NV", "Tên NV", "Chức vụ", "SĐT", "Địa chỉ"};
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

        scrollPane = new JScrollPane(tbl);
        scrollPane.setBorder(null);
        add(scrollPane, "grow");
    }

    public void loadDataFromMySQL() {
        tblModel.setRowCount(0);
        String sql = "SELECT * FROM nhanvien";

        try (Connection conn = DatabaseHelper.getConnection();
             Statement st = conn.createStatement();
             ResultSet rs = st.executeQuery(sql)) {

            int stt = 1;
            while (rs.next()) {
                tblModel.addRow(new Object[]{
                        stt++,
                        rs.getInt("ma_nv"),
                        rs.getString("ten_nv"),
                        rs.getString("chuc_vu"),
                        rs.getString("sdt"),
                        rs.getString("dia_chi")
                });
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Lỗi load dữ liệu: " + e.getMessage());
        }
    }
}
