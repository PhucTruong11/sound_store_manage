package Frontend.GUI.NhaCungCap;

import Frontend.Compoent.Table;
import Frontend.Compoent.Theme;
import net.miginfocom.swing.MigLayout;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.DefaultTableCellRenderer;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

public class NCCTable extends JPanel{
    private JTable tbl;
    private DefaultTableModel tblModel;
    private JScrollPane scrollPane;

    public NCCTable() {
        setLayout(new MigLayout("wrap 1, fill, insets 10", "[grow]", "[]15[grow]"));
        setBackground(Color.WHITE);
        putClientProperty("FlatLaf.style", "arc: 20");

        initFilterHeader();
        initTable();
        loadDummyData();
    }

    private void initFilterHeader() {
        JPanel pnlHeader = new JPanel(new MigLayout("insets 10", "[]push[]"));
        pnlHeader.putClientProperty("FlatLaf.style", "arc: " + Theme.ROUNDING_ARC);
        JLabel lblTitle = new JLabel("Nhà cung cấp");
        lblTitle.setFont(new Font("Segoe UI", Font.BOLD, 16));

        JComboBox<String> cboLoc = new JComboBox<>(new String[] {"Tất cả", "Sony Electronics", "JBL Official", "Marshall VN"});
        cboLoc.putClientProperty("FlatLaf.style", "arc: " + Theme.ROUNDING_ARC);
        
        pnlHeader.add(lblTitle);
        pnlHeader.add(cboLoc, "w 150!, h 35!");
        
        add(pnlHeader, "growx");
    }

    private void initTable() {
        String[] colums = {"STT","Mã Nhà cung cấp", "Tên Nhà cung cấp", "Địa chỉ", "SĐT"};
        tblModel = new DefaultTableModel(colums, 0) {
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

     private void loadDummyData() {
        tblModel.addRow(new Object[]{"1","NCC01", "Stek VN", "Hà Nội", "0961253086"});
        tblModel.addRow(new Object[]{"2","NCC02", "Pro Tech", "Hồ Chí Minh", "0879910893"});
    }


}
