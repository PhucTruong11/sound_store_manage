package Frontend.GUI.KhuyenMai;

import Backend.BUS.KhuyenMaiBUS;
import Backend.DTO.KhuyenMai;
import Frontend.Compoent.Table;
import Frontend.Compoent.Theme;
import net.miginfocom.swing.MigLayout;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.ArrayList;

public class KhuyenMaiTable extends JPanel {
    private JTable tbl;
    private DefaultTableModel tblModel;
    private JScrollPane scrollPane;
    private KhuyenMaiBUS kmBUS;
    private JComboBox<String> cboKM; // Lọc trạng thái

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

        cboKM = new JComboBox<>(new String[]{"Tất cả", "Đang diễn ra", "Sắp diễn ra", "Đã kết thúc"});
        cboKM.putClientProperty("FlatLaf.style", "arc: 10");

        pnlHeader.add(lblTitle);
        pnlHeader.add(new JLabel("Trạng thái: "), "split 2");
        pnlHeader.add(cboKM, "w 150!, h 30!");

        add(pnlHeader, "growx");
    }

    private void initTable() {
        String[] header = {"STT", "Mã KM", "Tên chương trình", "% Giảm", "Ngày bắt đầu", "Ngày kết thúc"};
        tblModel = new DefaultTableModel(header, 0);
        tbl = new Table();
        tbl.setModel(tblModel);
        
        scrollPane = new JScrollPane(tbl);
        add(scrollPane, "grow");
    }

    public void loadData() {
        tblModel.setRowCount(0);
        ArrayList<KhuyenMai> list = kmBUS.getAllKhuyenMai(); 
        int stt = 1;
        for (KhuyenMai km : list) {
            Object[] row = {
                stt++,
                km.getMaKM(),
                km.getTenKM(),
                km.getPhanTramGiam() + "%",
                km.getNgayBD(),
                km.getNgayKT()
            };
            tblModel.addRow(row);
        }
    }

    public JTable getTbl() { return tbl; }
    
    // Thêm hàm này để giống NCC (nếu sau này cần load danh sách vào combo)
    public void loadComboBox() {
        // Logic tương tự NCCTable nếu cần
    }
}
