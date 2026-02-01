package Frontend.GUI.KhuyenMai;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.DefaultTableCellRenderer;
import java.awt.*;
import net.miginfocom.swing.MigLayout;

public class KhuyenMaiPanel extends JPanel {
    private JTable table;
    private DefaultTableModel model;

    public KhuyenMaiPanel() {
        // [250!] éo cột trái rộng 250px, [fill] cột phải tự giãn. gapx 10 giúp chúng sát nhau.
        setLayout(new MigLayout("fill, insets 10, gapx 10", "[250!]0[fill]", "[fill]"));
        setBackground(new Color(242, 244, 246));

        // --- BÊN TRÁI: BỘ LỌC ---
        JPanel pnlFilter = new JPanel(new MigLayout("wrap 1, fillx, insets 15", "[fill]", "[]15[]5[]15[]5[]"));
        pnlFilter.setBackground(Color.WHITE);
        pnlFilter.setBorder(BorderFactory.createLineBorder(new Color(210, 210, 210)));

        JLabel lblTitle = new JLabel("BỘ LỌC");
        lblTitle.setFont(new Font("Segoe UI", Font.BOLD, 16));
        pnlFilter.add(lblTitle);
        
        pnlFilter.add(new JLabel("Loại khuyến mãi:"), "gaptop 10");
        pnlFilter.add(new JComboBox<>(new String[]{"Tất cả", "Giảm theo %", "Giảm theo tiền"}), "h 35!");

        pnlFilter.add(new JLabel("Trạng thái:"), "gaptop 10");
        pnlFilter.add(new JComboBox<>(new String[]{"Tất cả", "Đang diễn ra", "Đã kết thúc"}), "h 35!");

        JButton btnReset = new JButton("Làm mới bộ lọc");
        btnReset.setFocusPainted(false);
        pnlFilter.add(btnReset, "gaptop 20, h 35!");

        add(pnlFilter, "growy");

        // --- BÊN PHẢI: NỘI DUNG CHÍNH ---
        JPanel pnlRight = new JPanel(new MigLayout("fill, wrap 1, insets 0", "[fill]", "[]10[fill]"));
        pnlRight.setOpaque(false);

        // Thanh công cụ: ép thanh search 300px, push để đẩy các nút về bên phải
        JPanel pnlToolbar = new JPanel(new MigLayout("insets 0", "[300!]10[]push[]", "[]"));
        pnlToolbar.setOpaque(false);

        JTextField txtSearch = new JTextField(" Tìm kiếm mã...");
        JButton btnExport = new JButton("Xuất Excel");
        JButton btnAdd = new JButton("+ TẠO MỚI");
        btnAdd.setBackground(new Color(40, 167, 69));
        btnAdd.setForeground(Color.WHITE);
        btnAdd.setFont(new Font("Segoe UI", Font.BOLD, 13));

        pnlToolbar.add(txtSearch, "h 40!");
        pnlToolbar.add(btnExport, "h 40!");
        pnlToolbar.add(btnAdd, "h 40!");

        // Bảng dữ liệu
        String[] cols = {"STT", "Mã KM", "Tên Chương Trình", "Giá Trị", "Bắt Đầu", "Kết Thúc", "Trạng Thái"};
        model = new DefaultTableModel(new Object[][]{
            {"1", "KM01", "Khai Xuân", "10%", "01/01", "15/01", "Đang chạy"},
            {"2", "KM02", "Hè Về", "20%", "01/06", "30/06", "Sắp tới"}
        }, cols);
        
        table = new JTable(model);
        styleTable();
        
        JScrollPane sp = new JScrollPane(table);
        sp.setBorder(BorderFactory.createLineBorder(new Color(220, 220, 220)));

        pnlRight.add(pnlToolbar);
        pnlRight.add(sp);

        add(pnlRight);
    }

    private void styleTable() {
        table.setRowHeight(35);
        table.getTableHeader().setBackground(new Color(52, 58, 64));
        table.getTableHeader().setForeground(Color.WHITE);
        table.getTableHeader().setPreferredSize(new Dimension(0, 35));
        
        // Căn giữa STT và Trạng thái
        DefaultTableCellRenderer center = new DefaultTableCellRenderer();
        center.setHorizontalAlignment(JLabel.CENTER);
        table.getColumnModel().getColumn(0).setCellRenderer(center);
        table.getColumnModel().getColumn(6).setCellRenderer(center);
    }
}
