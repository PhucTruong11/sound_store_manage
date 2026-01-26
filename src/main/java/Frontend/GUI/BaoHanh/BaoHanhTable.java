package Frontend.GUI.BaoHanh;

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
    private BaoHanhBUS baoHanhBUS;

    public BaoHanhTable() {
        baoHanhBUS = new BaoHanhBUS();
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
        JLabel lblTitle = new JLabel("Bảo hành");
        lblTitle.setFont(new Font("Segoe UI", Font.BOLD, 16));

        JComboBox<String> cboLoc = new JComboBox<>(
                new String[] { "Tất cả", "Sony Electronics", "JBL Official", "Marshall VN" });
        cboLoc.putClientProperty("FlatLaf.style", "arc: " + Theme.ROUNDING_ARC);

        pnlHeader.add(lblTitle);
        pnlHeader.add(cboLoc, "w 150!, h 35!");

        add(pnlHeader, "growx");
    }

    private void initTable() {
        String[] colums = { "STT", "Mã bão hành", "Mã Imei", "Mã phiếu xuất", "Ngày BĐ", "Ngày KT" };
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

    private void loadData() {
        tblModel.setRowCount(0);
        ArrayList<BaoHanh> listBaoHanh = baoHanhBUS.getAllBaoHanh();

        int STT = 1;
        for (BaoHanh bh : listBaoHanh) {
            Object[] row = {
                    STT++,
                    bh.getMaBH(),
                    bh.getMaImei(),
                    bh.getMaPhieuXuat(),
                    bh.getNgayBatDau(),
                    bh.getNgayKetThuc(),
            };
            tblModel.addRow(row);
        }

        tbl.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() == 2) { // Double click
                    int row = tbl.getSelectedRow();
                    if (row != -1) {
                        // Lấy Mã bảo hành ở cột thứ 1 (Index 1)
                        String maBH = tbl.getValueAt(row, 1).toString();

                        // Mở Dialog
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
}
