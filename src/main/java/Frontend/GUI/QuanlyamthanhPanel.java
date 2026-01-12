package Frontend.GUI;

import Backend.BUS.AmthanhBUS;
import Backend.DTO.Amthanh;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.DefaultTableCellRenderer;
import java.awt.*;
import java.util.ArrayList;
import net.miginfocom.swing.MigLayout;

public class QuanlyamthanhPanel extends JPanel {
    private AmthanhBUS laptopBUS = new AmthanhBUS();
    private JTable table;
    private DefaultTableModel tableModel;

    public QuanlyamthanhPanel() {
        setLayout(new MigLayout("fill, insets 20", "[grow]", "[]20[]10[grow]"));
        setBackground(Color.WHITE);

        initComponents();
        loadData();
    }

    private void initComponents() {
        // Tiêu đề Panel
        JLabel lblTitle = new JLabel("QUẢN LÝ DANH SÁCH SẢN PHẨM");
        lblTitle.setFont(new Font("Segoe UI", Font.BOLD, 24));
        lblTitle.setForeground(new Color(45, 52, 54));
        add(lblTitle, "wrap");

        // Thanh công cụ (Nút bấm bo tròn)
        JPanel toolBar = new JPanel(new MigLayout("insets 0", "[grow]10[]10[]"));
        toolBar.setOpaque(false);

        JTextField txtSearch = new JTextField();
        txtSearch.putClientProperty("JTextField.placeholderText", "Tìm kiếm theo tên máy...");
        
        JButton btnAdd = new JButton("Thêm Mới");
        btnAdd.setBackground(new Color(46, 204, 113));
        btnAdd.setForeground(Color.WHITE);
        btnAdd.setFont(new Font("Segoe UI", Font.BOLD, 14));
        btnAdd.putClientProperty("JButton.buttonType", "roundRect");

        JButton btnReload = new JButton("Làm Mới");
        btnReload.addActionListener(e -> loadData());

        toolBar.add(txtSearch, "growx");
        toolBar.add(btnAdd, "w 120!, h 35!");
        toolBar.add(btnReload, "w 120!, h 35!");
        
        add(toolBar, "growx, wrap");

        // Bảng dữ liệu (Table)
        // Lấy danh sách cột từ sơ đồ ERD của bạn [cite: 51, 56]
        String[] columns = {"Mã SP", "Tên Laptop", "Đơn Giá (VNĐ)"};
        tableModel = new DefaultTableModel(columns, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false; // Không cho sửa trực tiếp trên bảng
            }
        };
        
        table = new JTable(tableModel);
        table.setRowHeight(30);
        table.setShowGrid(false);
        
        // Căn giữa nội dung các cột
        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(JLabel.CENTER);
        table.setDefaultRenderer(Object.class, centerRenderer);

        JScrollPane scrollPane = new JScrollPane(table);
        // Bo tròn viền của ScrollPane
        scrollPane.setBorder(BorderFactory.createLineBorder(new Color(230, 230, 230), 1));
        add(scrollPane, "grow");
    }

    public void loadData() {
        // Gọi BUS lấy danh sách từ Database [cite: 44, 156]
        ArrayList<Amthanh> list = laptopBUS.getAllLaptops();
        
        // Xóa dữ liệu cũ
        tableModel.setRowCount(0);
        
        // Đổ dữ liệu mới vào bảng
        for (Amthanh lap : list) {
            // Định dạng tiền tệ đơn giản
            String formattedPrice = String.format("%,.0f", lap.getGiaBan());
            Object[] row = {
                lap.getMaMay(), 
                lap.getTenMay(), 
                formattedPrice
            };
            tableModel.addRow(row);
        }
    }
}