package Frontend.GUI.PhieuNhap;

import Frontend.Compoent.Table;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.DefaultTableCellRenderer;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

public class PhieuNhapTable extends JScrollPane {
    private JTable tbl;
    private DefaultTableModel tblModel;
    private PhieuNhapSidebar sidebar;

    public PhieuNhapTable(PhieuNhapSidebar sidebar) {
        this.sidebar = sidebar;
        initTable();
        loadDummyData();
        addTableEvents();
    }

    private void initTable() {
        String[] columns = { "STT", "Mã HĐN", "Ngày Lập", "Nhân Viên", "Số Lượng", "Đơn Giá (VNĐ)" };
        tblModel = new DefaultTableModel(columns, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        tbl = new Table();
        tbl.setModel(tblModel);

        // Căn giữa các cột
        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(JLabel.CENTER);
        tbl.setDefaultRenderer(Object.class, centerRenderer);

        setViewportView(tbl);
        setBorder(BorderFactory.createLineBorder(new Color(230, 230, 230), 1));
    }

    private void loadDummyData() {
        tblModel.addRow(new Object[]{"1", "PN001", "2024-05-20", "Phuc truong", "2", "18,700,000"});
        tblModel.addRow(new Object[]{"2", "PN002", "2024-05-21", "Van Nam", "1", "10,200,000"});
        tblModel.addRow(new Object[]{"3", "PN003", "2024-05-22", "Phuc truong", "5", "42,500,000"});
    }

    private void addTableEvents() {
        tbl.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() == 2) {
                    int row = tbl.getSelectedRow();
                    if (row != -1) {
                        String maPN = tblModel.getValueAt(row, 1).toString();
                        openDetailDialog(maPN);
                    }
                }
            }
        });
    }

    private void openDetailDialog(String maPN) {
        JFrame parentFrame = (JFrame) SwingUtilities.getWindowAncestor(this);
        ChiTietHoaDonNhapDialog dialog = new ChiTietHoaDonNhapDialog(parentFrame, maPN);
        dialog.setVisible(true);
    }
}
