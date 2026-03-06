package Frontend.GUI.SanPham;

import Frontend.Compoent.Table;
import Frontend.Compoent.Theme;
import Backend.DTO.SanPham;
import net.miginfocom.swing.MigLayout;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;
import javax.swing.table.DefaultTableCellRenderer;
import java.awt.*;
import java.util.ArrayList;

public class SanPhamTable extends JPanel {
    private JTable tbl;
    private DefaultTableModel tblModel;
    private JScrollPane scrollPane;
    private JComboBox<String> cboPhanLoai;
    private TableRowSorter<DefaultTableModel> sorter;

    public SanPhamTable() {
        setLayout(new MigLayout("wrap 1, fill, insets 10", "[grow]", "[]15[grow]"));
        setBackground(Color.WHITE);
        putClientProperty("FlatLaf.style", "arc: " + Theme.ROUNDING_ARC);

        initHeader();
        initTable();
    }

    private void initHeader() {
        JPanel pnlHeader = new JPanel(new MigLayout("insets 10", "[]push[]"));
        pnlHeader.putClientProperty("FlatLaf.style", "arc: " + Theme.ROUNDING_ARC);

        // Tiêu đề
        JLabel lblTitle = new JLabel("Sản Phẩm");
        lblTitle.setFont(new Font("Segoe UI", Font.BOLD, 16));
        // Phân loại
        cboPhanLoai = new JComboBox<>(new String[] { "Tất cả", "Loa", "Tai nghe", "Phụ kiện" });
        cboPhanLoai.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        cboPhanLoai.setBackground(Color.WHITE);
        cboPhanLoai.setFocusable(false);
        cboPhanLoai.setPreferredSize(new Dimension(130, 30));

        pnlHeader.add(lblTitle);
        pnlHeader.add(cboPhanLoai, "w 150!,h 35!");
        add(pnlHeader, "growx");
    }

    private void initTable() {
        String[] columns = { "STT", "Mã SP", "Tên Sản Phẩm", "Số Lượng", "Mã Loại", "Mã Hãng","Mô Tả","Thời Gian Bảo Hành" };

        tblModel = new DefaultTableModel(columns, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
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

    // Hiển thị dữ liệu
    public void showData(ArrayList<SanPham> list) {
        tblModel.setRowCount(0);

        int stt = 1;
        //DecimalFormat formatter = new DecimalFormat("###,###");

        for (SanPham sp : list) {
            tblModel.addRow(new Object[] {
                    stt++, sp.getMaSP(), sp.getTenSP(),sp.getSoLuong(),sp.getMaLoai(),sp.getMaHang(),sp.getMoTa(),sp.getThoiGianBaoHanh()
            });
        }
    }

    public JTable getTable() {return tbl;}

    public JComboBox<String> getComboBox() {return cboPhanLoai;}
}