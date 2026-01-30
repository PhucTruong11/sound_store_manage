package Frontend.GUI.PhieuXuat;

import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Cell;

import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Font;

import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.FileOutputStream;
import java.io.File;
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
import java.util.Date;

public class PhieuXuatTable extends JScrollPane {
    private JTable tbl;
    private DefaultTableModel tblModel;
    private PhieuXuatBUS phieuXuatBUS;

    public PhieuXuatTable() {
        phieuXuatBUS = new PhieuXuatBUS();
        initTable();
        loadData("");
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

    // Thêm hàm này vào lớp PhieuXuatTable của bạn
    public void loadData(String keyword) {
        tblModel.setRowCount(0);
        ArrayList<PhieuXuat> listPX = phieuXuatBUS.search(keyword);

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

    public void filterData(Date start, Date end, String nv, String ncc, double minPrice, double maxPrice) {
        tblModel.setRowCount(0);
        ArrayList<PhieuXuat> listPX = phieuXuatBUS.getAllPhieuXuat();

        DecimalFormat df = new DecimalFormat("#,### VNĐ");
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm");
        
        int stt = 1;
        for (PhieuXuat px : listPX) {
            boolean matchDate = (px.getNgayXuat().after(start) || px.getNgayXuat().equals(start))
                    && (px.getNgayXuat().before(end) || px.getNgayXuat().equals(end));

            boolean matchNV = nv.equals("Tất cả") || px.getMaNV().equalsIgnoreCase(nv);
            boolean matchNCC = ncc.equals("Tất cả") || px.getMaKH().equalsIgnoreCase(ncc);

            boolean matchPrice = px.getTongTien() >= minPrice && px.getTongTien() <= maxPrice;
            
            if (matchDate && matchNV && matchNCC && matchPrice) {
                Object[] row = {
                        stt++,
                        px.getMaPhieuXuat(),
                        sdf.format(px.getNgayXuat()),
                        px.getMaNV(),
                        px.getMaKH(),
                        df.format(px.getTongTien())
                };
                tblModel.addRow(row);
            }
        }
    }

    public void xuatExcel() {
        JFileChooser chooser = new JFileChooser();
        chooser.setDialogTitle("Chọn đường dẫn lưu file Excel");

        if (chooser.showSaveDialog(this) == JFileChooser.APPROVE_OPTION) {
            String filePath = chooser.getSelectedFile().getAbsolutePath();
            if (!filePath.toLowerCase().endsWith(".xlsx"))
                filePath += ".xlsx";

            try (Workbook workbook = new XSSFWorkbook()) {
                Sheet sheet = workbook.createSheet("PhieuXuat");

                try (FileOutputStream out = new FileOutputStream(new File(filePath))) {
                    workbook.write(out);
                    JOptionPane.showMessageDialog(this, "Xuất file Excel thành công!");
                }
            } catch (Exception e) {
                e.printStackTrace();
                JOptionPane.showMessageDialog(this, "Lỗi: " + e.getMessage());
            }
        }
    }

    public JTable getTable() {
        return tbl;
    }
}