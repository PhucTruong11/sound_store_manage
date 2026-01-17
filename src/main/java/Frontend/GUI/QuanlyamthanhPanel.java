package Frontend.GUI;

import Backend.BUS.AmthanhBUS;
import Backend.DTO.Amthanh;
import Frontend.Compoent.SearchTextField;
import Frontend.Compoent.Table;
import Frontend.Compoent.Theme;
import Frontend.GUI.Nhaphang.ChiTietSanPhamDialog;
import Frontend.Compoent.CustomButton;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import net.miginfocom.swing.MigLayout;
import java.util.ArrayList;

public class QuanlyamthanhPanel extends JPanel {
    private AmthanhBUS amthanhBUS = new AmthanhBUS();
    private JTable table;
    private DefaultTableModel tableModel;

    public QuanlyamthanhPanel() {
        setLayout(new MigLayout("fill, insets 20", "[grow]", "[]20[]10[grow]"));
        setBackground(Color.WHITE);

        initComponents();
        loadData();
    }

    private void initComponents() {
        JLabel lblTitle = new JLabel("QUẢN LÝ DANH SÁCH SẢN PHẨM");
        lblTitle.setFont(new Font("Segoe UI", Font.BOLD, 24));
        lblTitle.setForeground(new Color(45, 52, 54));
        add(lblTitle, "wrap");

        JPanel toolBar = new JPanel(new MigLayout("insets 0", "[grow]10[]10[]"));
        toolBar.setOpaque(false);

        SearchTextField txtSearch = new SearchTextField("Tìm kiếm theo tên máy...");

        CustomButton btnAdd = new CustomButton("Thêm Mới", Theme.ACCENT_COLOR);

        JButton btnReload = new JButton("Làm Mới");
        btnReload.addActionListener(e -> loadData());

        toolBar.add(txtSearch, "growx, h 38!, ay center");
        toolBar.add(btnAdd, "w 120!, h 35!");
        toolBar.add(btnReload, "w 120!, h 35!");
        
        add(toolBar, "growx, wrap");

        String[] columns = {"Mã SP", "Tên Laptop", "Đơn Giá (VNĐ)"};
        tableModel = new DefaultTableModel(columns, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false; // Không cho sửa trực tiếp trên bảng
            }
        };

        table = new Table();
        table.setModel(tableModel);

        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setBorder(BorderFactory.createLineBorder(new Color(230, 230, 230), 1));
        add(scrollPane, "grow");

        table.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int row = table.getSelectedRow();
                if(row == - 1) return;

                String ma = table.getValueAt(row, 0).toString();
                String ten = table.getValueAt(row, 1).toString();
                String gia = table.getValueAt(row, 2).toString();

                if(e.getClickCount() == 2) {
                    new ChiTietSanPhamDialog(null, ma, ten, gia).setVisible(true);
                }
            }
        });
    }


    public void loadData() {
        ArrayList<Amthanh> list = amthanhBUS.getAllAmthanh();
        tableModel.setRowCount(0);
        for (Amthanh lap : list) {
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