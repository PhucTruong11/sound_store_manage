package Frontend.GUI.SanPham;

import Backend.BUS.AmthanhBUS;
import Backend.DTO.Amthanh;
import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import net.miginfocom.swing.MigLayout;

public class QuanlyamthanhPanel extends JPanel {
    
    private AmthanhBUS amthanhBUS = new AmthanhBUS();
    private SanPhamToolbar toolbar; 
    private SanPhamTable table;

    public QuanlyamthanhPanel() {
        setLayout(new MigLayout("fill, insets 10, gap 10", "[grow]", "[]10[grow]"));
        setBackground(new Color(237, 241, 245));
        initComponents();
        loadData(); 
    }

    private void initComponents() {
        toolbar = new SanPhamToolbar(this);
        toolbar.putClientProperty("FlatLaf.style", "arc: 15"); 
        add(toolbar, "growx, wrap");

        table = new SanPhamTable();
        toolbar.putClientProperty("FlatLaf.style", "arc: 15");
        add(table, "grow"); 
    }
    // Load dữ liệu 
    public void loadData() {
        // String tuKhoa = toolbar.getKeyword();
        // String phanLoai = table.getComboBox().getSelectedItem().toString();

        ArrayList<Amthanh> list = amthanhBUS.getAllAmthanh();

       // ArrayList<Amthanh> list = amthanhBUS.timKiemSanPham(tuKhoa, phanLoai);

        table.showData(list);
    }
    public SanPhamTable getTable(){
        return table;
    }
}