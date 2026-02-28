package Frontend.GUI.NhanVien;

import Backend.BUS.NhanVienBUS;
import Backend.DTO.NhanVien;
import Frontend.Compoent.Table;
import Frontend.Compoent.Theme;
import net.miginfocom.swing.MigLayout;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;
import javax.swing.table.DefaultTableCellRenderer;
import java.awt.*;
import java.util.ArrayList;

public class NhanVienTable extends JPanel {
    private JTable tbl;
    private DefaultTableModel tblModel;
    private JScrollPane scrollPane;
    private NhanVienBUS nvBUS;
    private JComboBox<String> cboSort;
    private JComboBox<String> cboSortLuong;
    private JComboBox<String> cboChucVu;
    private TableRowSorter<DefaultTableModel> sorter;
    private String currentKeyword = "";

    // private JComboBox<NhanVien> cboNV;

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
        JPanel pnlHeader = new JPanel(new MigLayout("insets 10, gapx 5", "[]push[][][]"));
        pnlHeader.putClientProperty("FlatLaf.style", "arc: " + Theme.ROUNDING_ARC);
        JLabel lblTitle = new JLabel("Nhân viên");
        lblTitle.setFont(new Font("Segoe UI", Font.BOLD, 16));

        // cboNV = new JComboBox<>();
        // loadComboBox();

        // cboNV.addActionListener(e -> {
        //     NhanVien selected = (NhanVien) cboNV.getSelectedItem();
        //     if (selected != null) {
        //         if (selected.getId().equals("All")) {
        //             loadData();
        //         } else {
        //             loadDataByFilter(selected.getId());
        //         }
        //     }
        // });

        // cboNV.putClientProperty("FlatLaf.style", "arc: " + Theme.ROUNDING_ARC);
        // pnlHeader.add(lblTitle);
        // pnlHeader.add(cboNV, "w 150!, h 35!");
        // add(pnlHeader, "growx");
        cboSort = new JComboBox<>(new String[]{
                "Tên mặc định",
                "Tên A - Z",
                "Tên Z - A"
        });

        cboSortLuong = new JComboBox<>(new String[]{
                "Lương mặc định",
                "Lương thấp → cao",
                "Lương cao → thấp"
        });

        cboChucVu = new JComboBox<>();
        loadComboBoxChucVu();


        cboSort.addActionListener(e -> {
        int colTen = 2; // cột "Tên NV"

        switch (cboSort.getSelectedIndex()) {
            case 0: // Mặc định
                sorter.setSortKeys(null);
                break;

            case 1: // A - Z
                sorter.setSortKeys(
                    java.util.List.of(
                        new RowSorter.SortKey(colTen, SortOrder.ASCENDING)
                    )
                );
                break;

            case 2: // Z - A
                sorter.setSortKeys(
                    java.util.List.of(
                        new RowSorter.SortKey(colTen, SortOrder.DESCENDING)
                    )
                );
                break;
        }
    });
            cboSortLuong.addActionListener(e -> {
            int colLuong = 7; // cột Lương

            switch (cboSortLuong.getSelectedIndex()) {
                case 0:
                    sorter.setSortKeys(null);
                    break;

                case 1:
                    sorter.setSortKeys(
                        java.util.List.of(
                            new RowSorter.SortKey(colLuong, SortOrder.ASCENDING)
                        )
                    );
                    break;

                case 2:
                    sorter.setSortKeys(
                        java.util.List.of(
                            new RowSorter.SortKey(colLuong, SortOrder.DESCENDING)
                        )
                    );
                    break;
            }
        });

        cboChucVu.addActionListener(e -> applyFilters());

        cboSort.putClientProperty("FlatLaf.style", "arc: " + Theme.ROUNDING_ARC);

        pnlHeader.add(lblTitle);
        pnlHeader.add(cboSort, "w 150!, h 35!");
        pnlHeader.add(cboChucVu, "w 160!, h 35!");
        pnlHeader.add(cboSortLuong, "w 170!, h 35!");
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

        sorter = new TableRowSorter<>(tblModel);
        tbl.setRowSorter(sorter);

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
public void filterByKeyword(String keyword) {
    currentKeyword = (keyword == null) ? "" : keyword.toLowerCase().trim();
    applyFilters();
}


    public void applyFilters() {
    String chucVu = cboChucVu.getSelectedItem().toString();
    String keyword = currentKeyword; // từ search

    sorter.setRowFilter(new RowFilter<DefaultTableModel, Integer>() {
        @Override
        public boolean include(Entry<? extends DefaultTableModel, ? extends Integer> entry) {

            String ma = entry.getStringValue(1).toLowerCase();
            String ten = entry.getStringValue(2).toLowerCase();
            String sdt = entry.getStringValue(3).toLowerCase();
            String cv  = entry.getStringValue(5);

            boolean matchSearch =
                    keyword.isEmpty()
                    || ma.contains(keyword)
                    || ten.contains(keyword)
                    || sdt.contains(keyword);

            boolean matchChucVu =
                    chucVu.equals("Tất cả")
                    || cv.equalsIgnoreCase(chucVu);

            return matchSearch && matchChucVu;
        }
    });
}


    // public void loadComboBox() {
    //     if (cboNV == null) return;
    //     Object selected = cboNV.getSelectedItem();
    //     cboNV.removeAllItems();
    //     cboNV.addItem(new NhanVien("All", "Tất cả", "", "", "", "", 0, true));
    //     ArrayList<NhanVien> list = nvBUS.getAllNhanVien();
    //     for (NhanVien nv : list) {
    //         cboNV.addItem(nv);
    //     }
    //     if (selected != null) cboNV.setSelectedItem(selected);
    // }

    // public void loadDataBySearch(String query) {
    //     tblModel.setRowCount(0);
    //     ArrayList<NhanVien> list = nvBUS.getAllNhanVien();

    //     int stt = 1;
    //     for (NhanVien nv : list) {
    //         boolean matchMa = nv.getId().toLowerCase().contains(query.toLowerCase());
    //         boolean matchTen = nv.getHoTen().toLowerCase().contains(query.toLowerCase());
    //         if (matchMa || matchTen) {
    //             Object[] row = {
    //                     stt++,
    //                     nv.getId(),
    //                     nv.getHoTen(),
    //                     nv.getSdt(),
    //                     nv.getDiaChi(),
    //                     nv.getChucVu(),
    //                     nv.getEmail(),
    //                     nv.getLuong()
    //             };
    //             tblModel.addRow(row);
    //         }
    //     }
    //     if (query.isEmpty()) cboNV.setSelectedIndex(0);
    // }
    private void loadComboBoxChucVu() {
            cboChucVu.removeAllItems();
            cboChucVu.addItem("Tất cả chức vụ");

            ArrayList<NhanVien> list = nvBUS.getAllNhanVien();
            java.util.Set<String> set = new java.util.HashSet<>();

            for (NhanVien nv : list) {
                set.add(nv.getChucVu());
            }

            for (String cv : set) {
                cboChucVu.addItem(cv);
            }
        }
}
