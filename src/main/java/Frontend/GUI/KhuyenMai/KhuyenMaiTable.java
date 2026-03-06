package Frontend.GUI.KhuyenMai;

import Backend.BUS.KhuyenMaiBUS;
import Backend.DTO.KhuyenMai;
import Frontend.Compoent.Table;
import Frontend.Compoent.Theme;
import net.miginfocom.swing.MigLayout;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;

import java.awt.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

public class KhuyenMaiTable extends JPanel {
    private JTable tbl;
    private DefaultTableModel tblModel;
    private JScrollPane scrollPane;
    private KhuyenMaiBUS kmBUS;
    private JComboBox<String> cboKM;
    private SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
    private TableRowSorter<DefaultTableModel> sorter;

    public KhuyenMaiTable() {
        kmBUS = new KhuyenMaiBUS();
        setLayout(new MigLayout("wrap 1, fill, insets 10", "[grow]", "[]15[grow]"));
        setBackground(Color.WHITE);
        putClientProperty("FlatLaf.style", "arc: 20");

        initFilterHeader();
        initTable();
        loadData();
    }

    private void initFilterHeader() {
        JPanel pnlHeader = new JPanel(new MigLayout("insets 10", "[]push[]"));
        pnlHeader.setBackground(Color.WHITE);
        pnlHeader.putClientProperty("FlatLaf.style", "arc: " + Theme.ROUNDING_ARC);
        
        JLabel lblTitle = new JLabel("Chương trình khuyến mãi");
        lblTitle.setFont(new Font("Segoe UI", Font.BOLD, 16));

        cboKM = new JComboBox<>(new String[]{"Tất cả", "Đang hoạt động", "Ngừng hoạt động"});
        cboKM.putClientProperty("FlatLaf.style", "arc: 10");

        pnlHeader.add(lblTitle);
        pnlHeader.add(cboKM, "w 150!, h 30!");

        add(pnlHeader, "growx");
    }

    private void initTable() {
        // Đã xóa cột Điều kiện tối thiểu
        String[] header = {"STT", "Mã KM", "Tên chương trình", "% Giảm", "Ngày bắt đầu", "Ngày kết thúc", "Trạng thái"};
        tblModel = new DefaultTableModel(header, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        tbl = new Table();
        tbl.setModel(tblModel);
        sorter = new TableRowSorter<>(tblModel);
        tbl.setRowSorter(sorter);
        
        scrollPane = new JScrollPane(tbl);
        add(scrollPane, "grow");
    }

    public void loadData() {
        kmBUS.refreshData();
        displayData(kmBUS.getAllKhuyenMai());
    }

    public void displayData(ArrayList<KhuyenMai> list) {
        tblModel.setRowCount(0);
        int stt = 1;
        for (KhuyenMai km : list) {
            Object[] row = {
                stt++,
                km.getMaKM(),
                km.getTenKM(),
                km.getPhanTramGiam() + "%",
                sdf.format(km.getNgayBD()),
                sdf.format(km.getNgayKT()),
                (km.getTrangThai() == 1) ? "Đang hoạt động" : "Ngừng hoạt động"
            };
            tblModel.addRow(row);
        }
    }

    public JTable getTbl() {
        return tbl;
    }
}
