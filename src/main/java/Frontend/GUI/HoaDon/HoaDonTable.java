package Frontend.GUI.HoaDon;

import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import Frontend.Compoent.Theme;
import Frontend.Compoent.Table;


public class HoaDonTable extends JScrollPane {
    private JTable table;
    private DefaultTableModel model;
    private HoaDonSidebar sidebar;

    public HoaDonTable(HoaDonSidebar sidebar) {
        this.sidebar = sidebar;
        initTable();
        initStyle();
        initEvent();
        loadData();
    }

    private void initTable() {
        String[] columns = { "STT", "Mã HĐ", "Ngày Lập", "Nhân Viên", "Khách Hàng", "Thời gian", "Tổng Tiền" };
        model = new DefaultTableModel(columns, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        // table = new JTable(model);
        table = new Table();
        table.setModel(model);
        this.setViewportView(table);
    }

    private void initStyle() {
        // table.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 13));
        // table.getTableHeader().setPreferredSize(new Dimension(0, 40));
        // table.setRowHeight(35);

        // Bo góc và nền cho JScrollPane
        // this.setBorder(BorderFactory.createLineBorder(new Color(230, 230, 230), 1));
        // this.putClientProperty("FlatLaf.style", "arc: " + Theme.ROUNDING_ARC);
        // this.getViewport().setBackground(Color.WHITE);

        // Căn lề các cột
        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(JLabel.CENTER);

        DefaultTableCellRenderer rightRenderer = new DefaultTableCellRenderer();
        rightRenderer.setHorizontalAlignment(JLabel.RIGHT);

        for (int i = 0; i < table.getColumnCount(); i++) {
            if (i == 6) {
                table.getColumnModel().getColumn(i).setCellRenderer(rightRenderer);
            } else {
                table.getColumnModel().getColumn(i).setCellRenderer(centerRenderer);
            }
        }
    }

    private void initEvent() {
        table.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int row = table.getSelectedRow();
                if (row == -1)
                    return;

                // Lấy dữ liệu
                String ma = model.getValueAt(row, 1).toString();
                String khach = model.getValueAt(row, 4).toString();
                String tongTien = model.getValueAt(row, 6).toString();

                // Cập nhật Sidebar khi click đơn
                if (sidebar != null) {
                    sidebar.updateInfo(ma, khach, tongTien);
                }

                // Mở Frame chi tiết khi Double Click
                if (e.getClickCount() == 2) {
                    showDetailDialog(ma);
                }
            }
        });
    }

    private void showDetailDialog(String maHD) {
        Window ancestor = SwingUtilities.getWindowAncestor(this);// WindowAncestor trả về cửa sổ cha của thành phần giao
                                                                 // diện người dùng
        if (ancestor instanceof JFrame) {
            ChiTietHoaDonDialog dialog = new ChiTietHoaDonDialog((JFrame) ancestor, maHD);
            dialog.setVisible(true);
        }
    }

    public void loadData() {
        model.setRowCount(0);
        // Ví dụ dữ liệu
        model.addRow(new Object[] { "1", "HD001", "2024-10-27", "Van Nam", "Khách lẻ", "09:30", "1.500.000" });
        model.addRow(new Object[] { "2", "HD002", "2024-10-28", "Van Nam", "Nguyễn Văn A", "14:15", "2.300.000" });
    }
}