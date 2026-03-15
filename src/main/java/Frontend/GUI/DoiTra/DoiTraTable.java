package Frontend.GUI.DoiTra;

import java.awt.Color;
import java.awt.Font;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;

import Backend.BUS.DoiTraBUS;
import Backend.DTO.DoiTra;
import Frontend.Compoent.Table;
import Frontend.Compoent.Theme;
import net.miginfocom.swing.MigLayout;

public class DoiTraTable extends JPanel {

    private JTable tbl;
    private DefaultTableModel tblModel;
    private DoiTraBUS doiTraBUS = new DoiTraBUS();
    private JComboBox<String> cboTrangThai;
    private DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd/MM/yyyy");

    public DoiTraTable() {
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
        JLabel lblTitle = new JLabel("Quản Lý Đổi Trả (Hạn 30 ngày)");
        lblTitle.setFont(new Font("Segoe UI", Font.BOLD, 16));

        cboTrangThai = new JComboBox<>(new String[]{"Tất cả", "Còn thời hạn", "Đã hết hạn đổi trả"});
        cboTrangThai.addActionListener(e -> locTrangThai());

        pnlHeader.add(lblTitle);
        pnlHeader.add(cboTrangThai, "w 200!, h 35!");
        add(pnlHeader, "growx");
    }

    private void initTable() {
        String[] header = {"Mã DT", "Mã PX", "Mã KH", "Ngày Mua", "Ngày Hết Hạn", "Số Lượng", "Trạng Thái"};

        tblModel = new DefaultTableModel(header, 0) {
            @Override
            public boolean isCellEditable(int row, int column) { return false; }
        };

        tbl = new Table();
        tbl.setModel(tblModel);

        tbl.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent e) {
                if (e.getClickCount() == 2) {
                    int row = tbl.getSelectedRow();
                    if (row != -1) {
                        String maDT = tbl.getValueAt(row, 0).toString();
                        showDetail(maDT);
                    }
                }
            }
        });

        DefaultTableCellRenderer center = new DefaultTableCellRenderer();
        center.setHorizontalAlignment(JLabel.CENTER);
        tbl.setDefaultRenderer(Object.class, center);

        add(new JScrollPane(tbl), "grow");
    }

    public void loadData() {
        tblModel.setRowCount(0);
        ArrayList<DoiTra> list = doiTraBUS.getAll();
        for (DoiTra dt : list) {
            addRow(dt);
        }
    }

    public void updateTable(ArrayList<DoiTra> newList) {
        tblModel.setRowCount(0);
        for (DoiTra dt : newList) {
            addRow(dt);
        }
    }

    private void addRow(DoiTra dt) {
        tblModel.addRow(new Object[]{
                dt.getMaDoiTra(),
                dt.getMaPhieuXuat(),
                dt.getMaKH(),
                dt.getNgayDoiTra().format(dtf),
                dt.getNgayHetHan().format(dtf),
                dt.getSoLuong(),
                dt.getTrangThaiThoiHan()
        });
    }

    private void locTrangThai() {
        String filter = cboTrangThai.getSelectedItem().toString();
        tblModel.setRowCount(0);
        for (DoiTra dt : doiTraBUS.getAll()) {
            if (filter.equals("Tất cả") || dt.getTrangThaiThoiHan().equals(filter)) {
                addRow(dt);
            }
        }
    }

    private void showDetail(String maDT) {
        DoiTra selected = null;
        for (DoiTra dt : doiTraBUS.getAll()) {
            if (dt.getMaDoiTra().equals(maDT)) {
                selected = dt;
                break;
            }
        }

        if (selected != null) {
            // Sử dụng Dialog chi tiết mới thay vì JOptionPane
            DoiTraDetailDialog detailDialog = new DoiTraDetailDialog(selected);
            detailDialog.setVisible(true);
        }
    }

    public JTable getTable() { return tbl; }
    
    public DefaultTableModel getModel() {
        return tblModel;
    }
}