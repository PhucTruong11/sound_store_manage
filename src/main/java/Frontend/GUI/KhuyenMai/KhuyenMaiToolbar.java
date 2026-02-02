package Frontend.GUI.KhuyenMai;

import java.awt.Color;
import Frontend.Compoent.*;
import net.miginfocom.swing.MigLayout;
import javax.swing.*;
import Backend.BUS.KhuyenMaiBUS;

public class KhuyenMaiToolbar extends JPanel {
    private KhuyenMaiTable table;
    private KhuyenMaiBUS kmBUS = new KhuyenMaiBUS();

    public KhuyenMaiToolbar(KhuyenMaiTable table) {
        this.table = table;
        setLayout(new MigLayout("fillx, insets 10", "[grow]10[]10[]10[]10[]"));
        setBackground(Color.WHITE);
        putClientProperty("FlatLaf.style", "arc: " + Theme.ROUNDING_ARC);

        SearchTextField txtSearch = new SearchTextField("Tìm kiếm khuyến mãi...");
        ButtonAdd btnAdd = new ButtonAdd("Thêm");
        ButtonFix btnFix = new ButtonFix("Sửa");
        ButtonDele btnDele = new ButtonDele("Xóa");
        ButtonXuatExcel btnXuatExcel = new ButtonXuatExcel("Xuất Excel");

        add(txtSearch, "growx, h 35!");
        add(btnAdd, "w 95!, h 35!");
        add(btnFix, "w 95!, h 35!");
        add(btnDele, "w 95!, h 35!");
        add(btnXuatExcel, "w 110!, h 35!");

        // Action Xóa - Y chang NCCToolbar
        btnDele.addActionListener(e -> {
            int selectedRow = table.getTbl().getSelectedRow();
            if (selectedRow == -1) {
                JOptionPane.showMessageDialog(this, "Vui lòng chọn khuyến mãi cần xóa!");
                return;
            }
            String ma = table.getTbl().getValueAt(selectedRow, 1).toString();
            int opt = JOptionPane.showConfirmDialog(this, 
                    "Xóa mã: " + ma + "?", 
                    "Xác nhận", JOptionPane.YES_NO_OPTION);
            if (opt == JOptionPane.YES_OPTION) {
                kmBUS.delete(ma); // Gọi hàm delete vừa thêm ở BUS
                table.loadData();
                // table.loadComboBox(); // Mở ra nếu bạn có dùng combo động
            }
        });
        
        // Bạn có thể thêm action cho btnAdd và btnFix tương tự NCC khi làm xong Dialog
    }
}
