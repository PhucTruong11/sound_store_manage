package Frontend.GUI.Nhaphang;

import Frontend.Compoent.Theme;
import Frontend.Compoent.CustomButton;
import Frontend.Compoent.Table;
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
        JLabel lblTitle = new JLabel("KIỂM TRA THÔNG TIN NHẬP HÀNG");
        lblTitle.setFont(new Font("Segoe UI", Font.BOLD, 18));
        lblTitle.setForeground(Theme.PRIMARY_COLOR);
        add(lblTitle, "center, wrap, gapbottom 15");

        String[] cols = {"STT", "Mã SP", "Tên Sản Phẩm", "SL", "Đơn Giá", "Thành Tiền"};
        modelReview = new DefaultTableModel(cols, 0);
        // tblReview = new JTable(modelReview);
        // tblReview.setRowHeight(30);

        Table tblReview = new Table();
        tblReview.setModel(modelReview);
        
        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(JLabel.CENTER);
        tblReview.setDefaultRenderer(Object.class, centerRenderer);

        // Copy dữ liệu từ Sidebar sang
        for (int i = 0; i < sourceModel.getRowCount(); i++) {
            // Cột 0: Mã, Cột 1: Tên, Cột 2: SL, Cột 3: Giá
            String ma = sourceModel.getValueAt(i, 0).toString();
            String ten = sourceModel.getValueAt(i, 1).toString();
            int sl = Integer.parseInt(sourceModel.getValueAt(i, 2).toString());
            String giaStr = sourceModel.getValueAt(i, 3).toString().replaceAll("[^0-9]", "");
            double gia = Double.parseDouble(giaStr);
            
            double thanhTien = sl * gia;

            modelReview.addRow(new Object[]{
                (i + 1),
                ma,
                ten, 
                sl, 
                String.format("%,.0f", gia), 
                String.format("%,.0f", thanhTien)
            });
        }

        add(new JScrollPane(tblReview), "grow, wrap");

        JPanel pnlTotal = new JPanel(new MigLayout("fillx", "[grow][right]"));
        pnlTotal.setBackground(new Color(245, 245, 245));
        pnlTotal.putClientProperty("FlatLaf.style", "arc: 15"); 
        
        lblTongSL = new JLabel("Tổng số lượng: 0");
        lblTongSL.setFont(new Font("Segoe UI", Font.BOLD, 16));
        lblTongSL.setForeground(Color.RED);
        
        lblTongTien = new JLabel("Tổng tiền thanh toán: 0 VNĐ");
        lblTongTien.setFont(new Font("Segoe UI", Font.BOLD, 16));
        lblTongTien.setForeground(Color.RED);

        pnlTotal.add(lblTongSL);
        pnlTotal.add(lblTongTien);
        add(pnlTotal, "growx, wrap, gaptop 10");

        JPanel pnlButtons = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        pnlButtons.setBackground(Color.WHITE);
        
        CustomButton btnHuy = new CustomButton("HỦY BỎ", new Color(149, 165, 166));
        btnHuy.addActionListener(e -> dispose());
        
        CustomButton btnXacNhan = new CustomButton("XÁC NHẬN & XUẤT HÓA ĐƠN", Theme.ACCENT_COLOR);
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
            totalSL += Integer.parseInt(modelReview.getValueAt(i, 3).toString());
            String thanhTienStr = modelReview.getValueAt(i, 5).toString().replaceAll("[^0-9]", "");
            totalMoney += Double.parseDouble(thanhTienStr);
        }
        lblTongSL.setText("Tổng số lượng: " + totalSL);
        lblTongTien.setText("TỔNG TIỀN THANH TOÁN: " + String.format("%,.0f", totalMoney) + " VNĐ");
    }
}
