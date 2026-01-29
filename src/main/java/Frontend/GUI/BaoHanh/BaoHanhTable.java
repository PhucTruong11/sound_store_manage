package Frontend.GUI.BaoHanh;

import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Cell;

import org.apache.poi.ss.usermodel.CellStyle;
// import org.apache.poi.ss.usermodel.Font;

import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.FileOutputStream;
import java.io.File;
import Backend.DTO.BaoHanh;
import Backend.BUS.BaoHanhBUS;
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

public class BaoHanhTable extends JPanel {
    private JTable tbl;
    private DefaultTableModel tblModel;
    private JScrollPane scrollPane;
    private BaoHanhBUS baoHanhBUS = new BaoHanhBUS();

    public BaoHanhTable() {
        setLayout(new MigLayout("wrap 1, fill, insets 10", "[grow]", "[]15[grow]"));
        setBackground(Color.WHITE);
        putClientProperty("FlatLaf.style", "arc: 20");

        initFilterHeader();
        initTable();
        loadData();
        addTableEvents(); // Sự kiện double-click để xem chi tiết
    }

    private void initFilterHeader() {
        JPanel pnlHeader = new JPanel(new MigLayout("insets 10", "[]push[]"));
        pnlHeader.putClientProperty("FlatLaf.style", "arc: " + Theme.ROUNDING_ARC);
        JLabel lblTitle = new JLabel("Danh sách Bảo hành");
        lblTitle.setFont(new Font("Segoe UI", Font.BOLD, 16));

        pnlHeader.add(lblTitle);
        add(pnlHeader, "growx");
    }

    private void initTable() {
        String[] colums = { "STT", "Mã bảo hành", "Mã Imei", "Mã phiếu xuất", "Ngày bắt đầu", "Ngày kết thúc" };
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

    public void loadData() {
        renderTable(baoHanhBUS.getAllBaoHanh());
    }

    private void renderTable(ArrayList<BaoHanh> list) {
        tblModel.setRowCount(0);
        int stt = 1;
        for (BaoHanh bh : list) {
            Object[] row = {
                    stt++,
                    bh.getMaBH(),
                    bh.getMaImei(),
                    bh.getMaPhieuXuat(),
                    bh.getNgayBatDau(),
                    bh.getNgayKetThuc()
            };
            tblModel.addRow(row);
        }
    }

    private void addTableEvents() {
        tbl.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() == 2) { // Nhấn đúp chuột để xem chi tiết
                    int row = tbl.getSelectedRow();
                    if (row != -1) {
                        String maBH = tbl.getValueAt(row, 1).toString();
                        JFrame parent = (JFrame) SwingUtilities.getWindowAncestor(BaoHanhTable.this);
                        ChiTietBaoHanhDialog dialog = new ChiTietBaoHanhDialog(parent, maBH);
                        dialog.setVisible(true);
                    }
                }
            }
        });
    }

    public JTable getTbl() {
        return tbl;
    }

    public void loadDataBySearch(String query) {
        ArrayList<BaoHanh> list = baoHanhBUS.getAllBaoHanh();
        ArrayList<BaoHanh> result = new ArrayList<>();
        String k = query.toLowerCase();
        for (BaoHanh bh : list) {
            if (bh.getMaBH().toLowerCase().contains(k) || bh.getMaImei().contains(k)) {
                result.add(bh);
            }
        }
        renderTable(result);
    }
}