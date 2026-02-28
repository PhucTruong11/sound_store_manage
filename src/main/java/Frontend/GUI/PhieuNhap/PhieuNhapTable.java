package Frontend.GUI.PhieuNhap;

import Backend.BUS.PhieuNhapBUS;
import Backend.DTO.PhieuNhap;
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
import java.util.Date;

public class PhieuNhapTable extends JScrollPane {
    private JTable tbl;     
    private DefaultTableModel tblModel;
    private PhieuNhapBUS phieuNhapBUS;

    public PhieuNhapTable() {
        phieuNhapBUS = new PhieuNhapBUS();
        initTable();
        loadData();
        addTableEvents();
    }

    private void initTable() {
        String[] columns = { "STT", "Mã HĐN", "Ngày Lập", "Mã NV", "Tổng Tiền" };
        tblModel = new DefaultTableModel(columns, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        tbl = new Table();
        tbl.setModel(tblModel);

        // Căn giữa các cột
        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(JLabel.CENTER);
        tbl.setDefaultRenderer(Object.class, centerRenderer);

        setViewportView(tbl);
        setBorder(BorderFactory.createLineBorder(new Color(230, 230, 230), 1));
    }

    public void filter(String maNCC, Date from, Date to, String minStr, String maxStr) {
        long min = (minStr == null || minStr.trim().isEmpty()) ? 0 : Long.parseLong(minStr.replaceAll("[^0-9]", ""));
        long max = (maxStr == null || maxStr.trim().isEmpty()) ? 9999999999L : Long.parseLong(maxStr.replaceAll("[^0-9]", ""));

        tblModel.setRowCount(0);
        ArrayList<PhieuNhap> list = phieuNhapBUS.getFilteredPhieuNhap(maNCC, from, to, min, max);

        DecimalFormat df = new DecimalFormat("#,###");
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm");

        int stt = 1;
        for (PhieuNhap pn : list) {
            tblModel.addRow(new Object[]{stt++, pn.getmaPhieuNhap(), sdf.format(pn.getngayNhap()), 
                                        pn.getmaNV(), df.format(pn.getTongTien())});
        }
    }

    public void loadDataBySearch(String query) {
        tblModel.setRowCount(0);
        ArrayList<PhieuNhap> list = phieuNhapBUS.getAllPhieuNhap();
        DecimalFormat df = new DecimalFormat("#,###");
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm");
        int stt = 1;
        for (PhieuNhap pn : list) {
            if (pn.getmaPhieuNhap().toLowerCase().contains(query.toLowerCase())) {
                tblModel.addRow(new Object[]{stt++, pn.getmaPhieuNhap(), sdf.format(pn.getngayNhap()), 
                                            pn.getmaNV(), df.format(pn.getTongTien())});
            }
        }
    }

    public void loadData() {
        // tblModel.setRowCount(0);
        // ArrayList<PhieuNhap> listPN = phieuNhapBUS.getAllPhieuNhap();

        // DecimalFormat df = new DecimalFormat("#, ###");
        // SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm");

        // int stt = 1;
        // for(PhieuNhap pn :listPN) {
        //     Object[] row = {
        //         stt++,
        //         pn.getmaPhieuNhap(),
        //         sdf.format(pn.getngayNhap()),
        //         pn.getmaNV(),
        //         df.format(pn.getTongTien())
        //     };
        //     tblModel.addRow(row);
        // }
        filter("All", null, null, "", "");
    }

    // public void loadDataByNCC(String maPN) {
    //     tblModel.setRowCount(0);
    //     ArrayList<PhieuNhap> list;

    //     if(maPN.equals("All")) {
    //         list = phieuNhapBUS.getAllPhieuNhap();
    //     } else {
    //         list = phieuNhapBUS.getByNCC(maPN);
    //     }

    //     DecimalFormat df = new DecimalFormat("#, ###");
    //     SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm");

    //     int stt = 1;
    //     for(PhieuNhap pn : list) {
    //         Object[] row = {
    //              stt++,
    //             pn.getmaPhieuNhap(),
    //             sdf.format(pn.getngayNhap()),
    //             pn.getmaNV(),
    //             df.format(pn.getTongTien())
    //         };
    //         tblModel.addRow(row);
    //     }
    // }

    private void addTableEvents() {
        tbl.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() == 2) {
                    int row = tbl.getSelectedRow();
                    if (row != -1) {
                        String maPN = tblModel.getValueAt(row, 1).toString();
                        openDetailDialog(maPN);
                    }
                }
            }
        });
    }

    private void openDetailDialog(String maPN) {
        JFrame parentFrame = (JFrame) SwingUtilities.getWindowAncestor(this);
        ChiTietHoaDonNhapDialog dialog = new ChiTietHoaDonNhapDialog(parentFrame, maPN);
        dialog.setVisible(true);
    }

    public JTable getTable() {
        return tbl;
    }
}
