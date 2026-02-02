package Frontend.GUI.NhanVien;

import Backend.BUS.NhanVienBUS;
import Backend.DTO.NhanVien;
import Frontend.Compoent.Table;
import Frontend.Compoent.Theme;
import net.miginfocom.swing.MigLayout;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.DefaultTableCellRenderer;
import java.awt.*;
import java.util.ArrayList;

public class NhanVienTable extends JPanel {
    private JTable tbl;
    private DefaultTableModel tblModel;
    private JScrollPane scrollPane;
    private NhanVienBUS nvBUS;
    private JComboBox<NhanVien> cboNV;

    public NhanVienTable() {
        nvBUS = new NhanVienBUS();
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
        JLabel lblTitle = new JLabel("Nhân viên");
        lblTitle.setFont(new Font("Segoe UI", Font.BOLD, 16));

        cboNV = new JComboBox<>();
        loadComboBox();

        cboNV.addActionListener(e -> {
            NhanVien selected = (NhanVien) cboNV.getSelectedItem();
            if (selected != null) {
                if (selected.getId().equals("All")) {
                    loadData();
                } else {
                    loadDataByFilter(selected.getId());
                }
            }
        });

        cboNV.putClientProperty("FlatLaf.style", "arc: " + Theme.ROUNDING_ARC);
        pnlHeader.add(lblTitle);
        pnlHeader.add(cboNV, "w 150!, h 35!");
        add(pnlHeader, "growx");
    }

    private void initTable() {
        String[] columns = { "STT", "Mã NV", "Tên NV", "SĐT", "Địa chỉ", "Chức vụ", "Email", "Lương" };
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
        ArrayList<NhanVien> listNV = nvBUS.getAllNhanVien();

        int stt = 1;
        for (NhanVien nv : listNV) {
            Object[] row = {
                    stt++,
                    nv.getId(),
                    nv.getHoTen(),
                    nv.getSdt(),
                    nv.getDiaChi(),
                    nv.getChucVu(),
                    nv.getEmail(),
                    nv.getLuong()
            };
            tblModel.addRow(row);
        }
    }

    public void loadDataByFilter(String maNV) {
        tblModel.setRowCount(0);
        ArrayList<NhanVien> listNV = nvBUS.getAllNhanVien();

        int stt = 1;
        for (NhanVien nv : listNV) {
            if (nv.getId().equals(maNV)) {
                Object[] row = {
                        stt++,
                        nv.getId(),
                        nv.getHoTen(),
                        nv.getSdt(),
                        nv.getDiaChi(),
                        nv.getChucVu(),
                        nv.getEmail(),
                        nv.getLuong()
                };
                tblModel.addRow(row);
            }
        }
    }

    public JTable getTbl() { return tbl; }

    public void loadComboBox() {
        if (cboNV == null) return;
        Object selected = cboNV.getSelectedItem();
        cboNV.removeAllItems();
        cboNV.addItem(new NhanVien("All", "Tất cả", "", "", "", "", 0, true));
        ArrayList<NhanVien> list = nvBUS.getAllNhanVien();
        for (NhanVien nv : list) {
            cboNV.addItem(nv);
        }
        if (selected != null) cboNV.setSelectedItem(selected);
    }

    public void loadDataBySearch(String query) {
        tblModel.setRowCount(0);
        ArrayList<NhanVien> list = nvBUS.getAllNhanVien();

        int stt = 1;
        for (NhanVien nv : list) {
            boolean matchMa = nv.getId().toLowerCase().contains(query.toLowerCase());
            boolean matchTen = nv.getHoTen().toLowerCase().contains(query.toLowerCase());
            if (matchMa || matchTen) {
                Object[] row = {
                        stt++,
                        nv.getId(),
                        nv.getHoTen(),
                        nv.getSdt(),
                        nv.getDiaChi(),
                        nv.getChucVu(),
                        nv.getEmail(),
                        nv.getLuong()
                };
                tblModel.addRow(row);
            }
        }
        if (query.isEmpty()) cboNV.setSelectedIndex(0);
    }
}
