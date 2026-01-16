package Frontend.GUI.Nhaphang;

import Frontend.Compoent.Theme;
import Frontend.Compoent.Button;
import net.miginfocom.swing.MigLayout;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.DefaultTableCellRenderer;
import java.awt.*;

public class XacNhanNhapHangDialog extends JDialog{
    private JTable tblReview;
    private DefaultTableModel modelReview;
    private JLabel lblTongSL, lblTongTien;
    
    public XacNhanNhapHangDialog(JFrame parent, DefaultTableModel sourceModel) {
        super(parent, "Xác nhận phiếu nhập hàng", true);
        setLayout(new MigLayout("fill, insets 20", "[grow]", "[][grow][]"));
        setSize(800, 500);
        setLocationRelativeTo(parent);
        getContentPane().setBackground(Color.WHITE);

        initComponents(sourceModel);
        calculateTotal();
    }

    private void initComponents(DefaultTableModel sourceModel) {
        // --- Header ---
        JLabel lblTitle = new JLabel("KIỂM TRA THÔNG TIN NHẬP HÀNG");
        lblTitle.setFont(new Font("Segoe UI", Font.BOLD, 18));
        lblTitle.setForeground(Theme.PRIMARY_COLOR);
        add(lblTitle, "center, wrap, gapbottom 15");

        // --- Bảng hiển thị ---
        String[] cols = {"STT", "Mã SP", "Tên Sản Phẩm", "SL", "Đơn Giá", "Thành Tiền"};
        modelReview = new DefaultTableModel(cols, 0);
        tblReview = new JTable(modelReview);
        tblReview.setRowHeight(30);
        
        // Căn giữa dữ liệu
        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(JLabel.CENTER);
        tblReview.setDefaultRenderer(Object.class, centerRenderer);

        // Copy dữ liệu từ Sidebar sang
        for (int i = 0; i < sourceModel.getRowCount(); i++) {
            // Cột 0: Mã, Cột 1: Tên, Cột 2: SL, Cột 3: Giá
            String ma = sourceModel.getValueAt(i, 0).toString();
            String ten = sourceModel.getValueAt(i, 1).toString();
            
            // Lấy Số lượng từ cột 2 (Thay vì cột 1 như cũ)
            int sl = Integer.parseInt(sourceModel.getValueAt(i, 2).toString());
            
            // Lấy Đơn giá từ cột 3 (Thay vì cột 2 như cũ)
            // Dùng regex để xóa các ký tự không phải số (phòng trường hợp có dấu , hoặc .)
            String giaStr = sourceModel.getValueAt(i, 3).toString().replaceAll("[^0-9]", "");
            double gia = Double.parseDouble(giaStr);
            
            double thanhTien = sl * gia;

            // Thêm vào bảng Review của Dialog (Bảng này có thể giữ 5 hoặc 6 cột tùy bạn)
            modelReview.addRow(new Object[]{
                (i + 1), // STT
                ma,      // Thêm mã vào đây cho chuyên nghiệp
                ten, 
                sl, 
                String.format("%,.0f", gia), 
                String.format("%,.0f", thanhTien)
            });
        }

        add(new JScrollPane(tblReview), "grow, wrap");

        // --- Footer Info ---
        JPanel pnlTotal = new JPanel(new MigLayout("fillx", "[grow][right]"));
        pnlTotal.setBackground(new Color(245, 245, 245));
        
        lblTongSL = new JLabel("Tổng số lượng: 0");
        lblTongSL.setFont(new Font("Segoe UI", Font.BOLD, 14));
        
        lblTongTien = new JLabel("TỔNG TIỀN THANH TOÁN: 0 VNĐ");
        lblTongTien.setFont(new Font("Segoe UI", Font.BOLD, 16));
        lblTongTien.setForeground(Color.RED);

        pnlTotal.add(lblTongSL);
        pnlTotal.add(lblTongTien);
        add(pnlTotal, "growx, wrap, gaptop 10");

        // --- Buttons ---
        JPanel pnlButtons = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        pnlButtons.setBackground(Color.WHITE);
        
        Button btnHuy = new Button("HỦY BỎ", new Color(149, 165, 166));
        btnHuy.addActionListener(e -> dispose());
        
        Button btnXacNhan = new Button("XÁC NHẬN & LƯU", Theme.ACCENT_COLOR);
        btnXacNhan.addActionListener(e -> {
            JOptionPane.showMessageDialog(this, "Nhập hàng thành công!");
            dispose();
        });

        pnlButtons.add(btnHuy);
        pnlButtons.add(btnXacNhan);
        add(pnlButtons, "right");
    }

    private void calculateTotal() {
        int totalSL = 0;
        double totalMoney = 0;
        for (int i = 0; i < modelReview.getRowCount(); i++) {
            // Lấy từ modelReview (Bảng hiện tại của Dialog)
            // Cột 3 là SL, Cột 5 là Thành Tiền
            totalSL += Integer.parseInt(modelReview.getValueAt(i, 3).toString());
            
            String thanhTienStr = modelReview.getValueAt(i, 5).toString().replaceAll("[^0-9]", "");
            totalMoney += Double.parseDouble(thanhTienStr);
        }
        lblTongSL.setText("Tổng số lượng: " + totalSL);
        lblTongTien.setText("TỔNG TIỀN THANH TOÁN: " + String.format("%,.0f", totalMoney) + " VNĐ");
    }
}
