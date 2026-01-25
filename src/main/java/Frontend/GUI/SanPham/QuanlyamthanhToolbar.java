package Frontend.GUI.SanPham;

import Frontend.Compoent.ButtonAdd;
import Frontend.Compoent.ButtonXuatExcel;
import Frontend.Compoent.ButtonRefresh;
import Frontend.Compoent.SearchTextField;
import Frontend.GUI.SanPham.ThemSanPhamDialog;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import net.miginfocom.swing.MigLayout;

public class QuanlyamthanhToolbar extends JPanel {

    private QuanlyamthanhPanel parentPanel;
    private SearchTextField txtSearch;
    private JComboBox<String> cboPhanLoai;

    public QuanlyamthanhToolbar(QuanlyamthanhPanel parentPanel) {
        this.parentPanel = parentPanel;
        
        setLayout(new MigLayout("insets 0", "[grow]10[]10[]10[]10[]")); 
        setOpaque(false);
        
        initComponents();
    }

    private void initComponents() {
        txtSearch = new SearchTextField("Tìm kiếm theo tên sản phẩm...");
        txtSearch.addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent evt) {
                // parentPanel.loadData();
            }
        });
        // Các thao tác
        String[] items = {"Tất cả", "Loa", "Tai nghe", "Phụ kiện"};
        cboPhanLoai = new JComboBox<>(items);
        cboPhanLoai.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        cboPhanLoai.setBackground(Color.WHITE);
        cboPhanLoai.setFocusable(false);
        cboPhanLoai.addActionListener(e -> parentPanel.loadData());

        ButtonAdd btnAdd = new ButtonAdd("Thêm");
        ButtonRefresh btnRefresh = new ButtonRefresh("Làm Mới");
        ButtonXuatExcel btnXuatExcel = new ButtonXuatExcel("Xuất Excel");

        add(txtSearch, "growx, h 35!");       
        add(cboPhanLoai, "w 105!, h 35!");    
        add(btnAdd, "w 95!, h 35!");           
        add(btnRefresh, "w 110!, h 35!");
        add(btnXuatExcel, "w 105!, h 35!");       

        btnAdd.addActionListener(e -> {
            JFrame frameCha = (JFrame) SwingUtilities.getWindowAncestor(this);
            ThemSanPhamDialog dialog = new ThemSanPhamDialog(frameCha);
            dialog.setVisible(true);
            // parentPanel.loadData();
        });

        btnXuatExcel.addActionListener(e -> {
            JOptionPane.showMessageDialog(this, "Chức năng xuất Excel...");
        });

        btnRefresh.addActionListener(e -> {
            txtSearch.setText("");
            cboPhanLoai.setSelectedIndex(0);
            parentPanel.loadData();
        });
    }

}