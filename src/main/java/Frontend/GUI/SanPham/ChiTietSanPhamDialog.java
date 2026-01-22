package Frontend.GUI.SanPham;

import Frontend.Compoent.CustomButton;
import Frontend.Compoent.Theme;
import javax.swing.*;
import java.awt.*;
import net.miginfocom.swing.MigLayout;

public class ChiTietSanPhamDialog extends JDialog {

    private JTextField txtMaSP;
    private JTextField txtTenSP;
    private JTextField txtDonGia;
    
    private CustomButton btnUpdate;
    private CustomButton btnDelete; 
    private CustomButton btnCancel;

    private boolean hienThi = true; 

    public ChiTietSanPhamDialog(Frame owner, String ma, String ten, String gia) {
        super(owner, "Chi Tiết Sản Phẩm", true);
        setSize(450, 280);
        setLocationRelativeTo(owner);
        setLayout(new MigLayout("fillx, insets 15", "[label, 100!]10[grow]", "[]10[]10[]15[]"));
        getContentPane().setBackground(Color.WHITE);

        initComponents();
        fillData(ma, ten, gia);
    }

    private void initComponents() {
        JLabel lblHeader = new JLabel("THÔNG TIN CHI TIẾT");
        lblHeader.setFont(new Font("Segoe UI", Font.BOLD, 18));
        lblHeader.setForeground(Theme.PRIMARY_COLOR);
        add(lblHeader, "span, center, wrap");

        // Form nhập liệu
        add(new JLabel("Mã Sản Phẩm:"));
        txtMaSP = new JTextField();
        txtMaSP.setEnabled(false); 
        txtMaSP.setBackground(new Color(245, 245, 245));
        add(txtMaSP, "growx, h 30!, wrap");

        add(new JLabel("Tên Sản Phẩm:"));
        txtTenSP = new JTextField();
        add(txtTenSP, "growx, h 30!, wrap"); 

        add(new JLabel("Đơn Giá (VNĐ):"));
        txtDonGia = new JTextField();
        add(txtDonGia, "growx, h 30!, wrap"); 

        JPanel pnlActions = new JPanel(new MigLayout("insets 0, fill", "[grow][grow][grow]"));
        pnlActions.setOpaque(false);
        btnUpdate = new CustomButton("Lưu", Theme.ACCENT_COLOR); 
        btnCancel = new CustomButton("Hủy", new Color(149, 165, 166));
        btnDelete = new CustomButton("Xóa", new Color(231, 76, 60)); 

        btnDelete.addActionListener(e -> {
             int confirm = JOptionPane.showConfirmDialog(this, 
                "Bạn chắc chắn muốn xóa sản phẩm này khỏi danh sách?", 
                "Xác nhận xóa", JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);
             
             if (confirm == JOptionPane.YES_OPTION) {
                this.hienThi = false;
                this.dispose();
             }
        });
        btnCancel.addActionListener(e -> dispose());
        btnUpdate.addActionListener(e -> {
            JOptionPane.showMessageDialog(this, "Đã lưu thông tin!");
            dispose();
        });
        pnlActions.add(btnUpdate, "w 100!, h 35!"); 
        pnlActions.add(btnCancel, "w 100!, h 35!");
        pnlActions.add(btnDelete, "w 100!, h 35!");
        add(pnlActions, "span, growx, pushy, bottom");
    }
    
    private void fillData(String ma, String ten, String gia) {
        txtMaSP.setText(ma);
        txtTenSP.setText(ten);
        txtDonGia.setText(gia);
    }

    public boolean getHienThi() { return hienThi; }
}