package Frontend.GUI.KhachHang;

import Backend.BUS.KhachHangBUS;
import Backend.DTO.KhachHang;
import Frontend.Compoent.Table;
import Frontend.Compoent.Theme;
import net.miginfocom.swing.MigLayout;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.DefaultTableCellRenderer;
import java.awt.*;
import java.util.ArrayList;

public class KhachHangTable extends JPanel {
    private JTable tbl;
    private DefaultTableModel tblModel;
    private JScrollPane scrollPane;
    private KhachHangBUS khBUS;
    private JComboBox<KhachHang> cboKH;

    public KhachHangTable() {
        khBUS = new KhachHangBUS();
        setLayout(new MigLayout("wrap 1, fill, insets 10", "[grow]", "[]15[grow]"));
        setBackground(Color.WHITE);
        putClientProperty("FlatLaf.style", "arc: 20");

        initFilterHeader();
        initTable();
        loadData();
    }

    private void initFilterHeader() {
        JPanel pnlHeader = new JPanel(new MigLayout("insets 10", "[]push[]"));
        pnlHeader.putClientProperty("FlatLaf.style", "arc: " + Theme.ROUNDING_ARC);
        JLabel lblTitle = new JLabel("Khách hàng");
        lblTitle.setFont(new Font("Segoe UI", Font.BOLD, 16));

        cboKH = new JComboBox<>();
        loadComboBox();

        cboKH.addActionListener(e -> {
            KhachHang selected = (KhachHang) cboKH.getSelectedItem();
            if (selected != null) {
                if (selected.getId().equals("All")) {
                    loadData();
                } else {
                    loadDataByFilter(selected.getId());
                }
            }
        });

        cboKH.putClientProperty("FlatLaf.style", "arc: " + Theme.ROUNDING_ARC);
        pnlHeader.add(lblTitle);
        pnlHeader.add(cboKH, "w 150!, h 35!");
        add(pnlHeader, "growx");
    }

    private void initTable() {
        String[] columns = { "STT", "Mã KH", "Tên KH", "SĐT", "Địa chỉ" };
        tblModel = new DefaultTableModel(columns, 0) {
            @Override
            public boolean isCellEditable(int row, int column) { return false; }
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

    public void loadData() {
        tblModel.setRowCount(0);
        ArrayList<KhachHang> listKH = khBUS.getAllKhachHang();

        int stt = 1;
        for (KhachHang kh : listKH) {
            Object[] row = {
                    stt++,
                    kh.getId(),
                    kh.getHoTen(),
                    kh.getSdt(),
                    kh.getDiaChi()
            };
            tblModel.addRow(row);
        }
    }

    public void loadDataByFilter(String maKH) {
        tblModel.setRowCount(0);
        ArrayList<KhachHang> listKH = khBUS.getAllKhachHang();

        int stt = 1;
        for (KhachHang kh : listKH) {
            if (kh.getId().equals(maKH)) {
                Object[] row = {
                        stt++,
                        kh.getId(),
                        kh.getHoTen(),
                        kh.getSdt(),
                        kh.getDiaChi()
                };
                tblModel.addRow(row);
            }
        }
    }

    public JTable getTbl() { return tbl; }

    public void loadComboBox() {
        if (cboKH == null) return;
        Object selected = cboKH.getSelectedItem();
        cboKH.removeAllItems();
        cboKH.addItem(new KhachHang("All", "Tất cả", "", "", true));
        ArrayList<KhachHang> list = khBUS.getAllKhachHang();
        for (KhachHang kh : list) {
            cboKH.addItem(kh);
        }
        if (selected != null) cboKH.setSelectedItem(selected);
    }

    public void loadDataBySearch(String query) {
        tblModel.setRowCount(0);
        ArrayList<KhachHang> list = khBUS.getAllKhachHang();

        int stt = 1;
        for (KhachHang kh : list) {
            boolean matchMa = kh.getId().toLowerCase().contains(query.toLowerCase());
            boolean matchTen = kh.getHoTen().toLowerCase().contains(query.toLowerCase());
            if (matchMa || matchTen) {
                Object[] row = {
                        stt++,
                        kh.getId(),
                        kh.getHoTen(),
                        kh.getSdt(),
                        kh.getDiaChi()
                };
                tblModel.addRow(row);
            }
        }
        if (query.isEmpty()) cboKH.setSelectedIndex(0);
    }
}
