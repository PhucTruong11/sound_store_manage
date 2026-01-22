package Frontend.GUI.SanPham;

import Backend.BUS.AmthanhBUS;
import Backend.DTO.Amthanh;
import Frontend.Compoent.Table; 
import Frontend.GUI.SanPham.ChiTietSanPhamDialog; 

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import net.miginfocom.swing.MigLayout;

public class QuanlyamthanhPanel extends JPanel {
    private AmthanhBUS amthanhBUS = new AmthanhBUS();
    private JTable table;
    private DefaultTableModel tableModel;
    private QuanlyamthanhToolbar toolbar;

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

        toolbar = new QuanlyamthanhToolbar(this); 
        add(toolbar, "growx, wrap");

        String[] columns = {"Mã SP", "Tên Sản Phẩm", "Đơn Giá (VNĐ)"};
        tableModel = new DefaultTableModel(columns, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
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
                if (e.getClickCount() == 2) {
                    int row = table.getSelectedRow();
                    if(row == -1) return;
                    String ma = table.getValueAt(row, 0).toString();
                    String ten = table.getValueAt(row, 1).toString();
                    String gia = table.getValueAt(row, 2).toString();

                    JFrame parent = (JFrame) SwingUtilities.getWindowAncestor(QuanlyamthanhPanel.this);
                    ChiTietSanPhamDialog dialog = new ChiTietSanPhamDialog(parent, ma, ten, gia);
                    dialog.setVisible(true);
                    if (dialog.getHienThi() == false) {
                        tableModel.removeRow(row);
                    } 
                }
            }
        });
    }
    // Load dữ liệu 
    public void loadData() {
    ArrayList<Amthanh> list = amthanhBUS.getAllAmthanh();
    tableModel.setRowCount(0);
    for (Amthanh sp : list) {
        String formattedPrice = String.format("%,.0f", sp.getGiaBan());
        Object[] row = {
            sp.getMaMay(), 
            sp.getTenMay(), 
            formattedPrice
        };
        tableModel.addRow(row);
    }
}
}