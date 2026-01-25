package Frontend.GUI.PhieuXuat;

import Backend.BUS.PhieuXuatBUS;
import Backend.DTO.PhieuXuat;
import Frontend.Compoent.Table;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.DefaultTableCellRenderer;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

public class PhieuXuatTable extends JScrollPane {
    private JTable tbl;
    private DefaultTableModel tblModel;
    private PhieuXuatBUS phieuXuatBUS;

    public PhieuXuatTable() {
        phieuXuatBUS = new PhieuXuatBUS();
        initTable();
        loadData();
        addTableEvents();
    }

    private void initTable() {
        String[] columns = { "STT", "Mã HĐX", "Ngày Bán", "Mã NV", "Khách Hàng", "Tổng Tiền" };
        tblModel = new DefaultTableModel(columns, 0) {
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

        setViewportView(tbl);
        setBorder(BorderFactory.createLineBorder(new Color(230, 230, 230), 1));
    }

    public void loadData() {
        tblModel.setRowCount(0);
        ArrayList<PhieuXuat> listPX = phieuXuatBUS.getAllPhieuXuat();

        DecimalFormat df = new DecimalFormat("#,### VNĐ");
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm");

        int stt = 1;
        for (PhieuXuat px : listPX) {
            Object[] row = {
                    stt++,
                    px.getMaPhieuXuat(),
                    px.getNgayXuat() != null ? sdf.format(px.getNgayXuat()) : "N/A",
                    px.getMaNV(),
                    px.getMaKH(),
                    df.format(px.getTongTien())
            };
            tblModel.addRow(row);
        }
    }

    private void addTableEvents() {
        tbl.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() == 2) {
                    int row = tbl.getSelectedRow();
                    if (row != -1) {
                        String maPX = tblModel.getValueAt(row, 1).toString();
                        openDetailDialog(maPX);
                    }
                }
            }
        });
    }

    private void openDetailDialog(String maPX) {
        JFrame parentFrame = (JFrame) SwingUtilities.getWindowAncestor(this);
        ChiTietPhieuXuatDialog dialog = new ChiTietPhieuXuatDialog(parentFrame, maPX);
        dialog.setVisible(true);
    }
}