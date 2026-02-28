package Frontend.GUI.SanPham;

import Frontend.Compoent.CustomButton;
import Frontend.Compoent.Theme;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import net.miginfocom.swing.MigLayout;

public class SuaSanPhamDialog extends JDialog {

    private JTextField txtMaSP, txtTenSP, txtSoLuong, txtMaLoai, txtMaHang, txtBaoHanh;
    private JTextArea txtMoTa;
    private CustomButton btnLuu, btnSuaPhienBan, btnHuy;
    private boolean isSuccess = false;

    public SuaSanPhamDialog(JFrame parent, String ma, String ten, String sl, String maLoai, String maHang, String mota, String baohanh) {
        super(parent, "Chỉnh sửa sản phẩm", true);
        setSize(600, 620); 
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());
        getContentPane().setBackground(Color.WHITE);

        initComponents(ma, ten, sl, maLoai, maHang, mota, baohanh);
    }

    private void initComponents(String ma, String ten, String sl, String maLoaiStr, String maHangStr, String mota, String baohanh) {
        // --- 1. HEADER ---
        JPanel pnlHeader = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
        pnlHeader.setBackground(Theme.PRIMARY_COLOR);
        JLabel lblHeader = new JLabel("CHỈNH SỬA SẢN PHẨM");
        lblHeader.setFont(new Font("Segoe UI", Font.BOLD, 16));
        lblHeader.setForeground(Color.WHITE);
        pnlHeader.add(lblHeader);
        add(pnlHeader, BorderLayout.NORTH);

        // --- 2. BODY ---
        JPanel pnlBody = new JPanel(new MigLayout("wrap 2, fillx, insets 20 40 20 40", "[130px!][grow]", "[]15[]"));
        pnlBody.setBackground(Color.WHITE);

        pnlBody.add(new JLabel("Mã sản phẩm:"));
        txtMaSP = createTextField(ma, false); 
        pnlBody.add(txtMaSP, "growx");

        pnlBody.add(new JLabel("Tên sản phẩm:"));
        txtTenSP = createTextField(ten, true);
        pnlBody.add(txtTenSP, "growx");

        pnlBody.add(new JLabel("Số lượng:"));
        txtSoLuong = createTextField(sl, false);
        pnlBody.add(txtSoLuong, "growx");

        pnlBody.add(new JLabel("Mã loại:"));
        txtMaLoai = createTextField(maLoaiStr, true);
        pnlBody.add(txtMaLoai, "growx");

        pnlBody.add(new JLabel("Mã hãng:"));
        txtMaHang = createTextField(maHangStr, true);
        pnlBody.add(txtMaHang, "growx");

        pnlBody.add(new JLabel("Bảo hành (tháng):"));
        txtBaoHanh = createTextField(baohanh, true);
        pnlBody.add(txtBaoHanh, "growx");

        pnlBody.add(new JLabel("Mô tả sản phẩm:"), "aligny top");
        
        txtMoTa = new JTextArea(4, 20);
        txtMoTa.setText(mota);
        txtMoTa.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        txtMoTa.setLineWrap(true);
        txtMoTa.setWrapStyleWord(true);
        txtMoTa.setBorder(BorderFactory.createLineBorder(new Color(200, 200, 200)));
        JScrollPane scrollMoTa = new JScrollPane(txtMoTa);
        pnlBody.add(scrollMoTa, "growx, h 100!");

        add(pnlBody, BorderLayout.CENTER);

        // --- 3. FOOTER (BUTTONS) ---
        JPanel pnlFooter = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 20));
        pnlFooter.setBackground(Color.WHITE);

        btnLuu = new CustomButton("Lưu thông tin", new Color(52, 152, 219)); 
        btnLuu.setPreferredSize(new Dimension(140, 40));
        
        btnSuaPhienBan = new CustomButton("Sửa phiên bản", new Color(243, 156, 18));
        btnSuaPhienBan.setPreferredSize(new Dimension(140, 40));
        
        btnHuy = new CustomButton("Huỷ bỏ", new Color(231, 76, 60));
        btnHuy.setPreferredSize(new Dimension(140, 40));

        pnlFooter.add(btnLuu);
        pnlFooter.add(btnSuaPhienBan);
        pnlFooter.add(btnHuy);
        add(pnlFooter, BorderLayout.SOUTH);

        btnHuy.addActionListener(e -> dispose());
        
        btnLuu.addActionListener(e -> {
            JOptionPane.showMessageDialog(this, "Đã lưu thông tin sản phẩm!");
            isSuccess = true;
            dispose();
        });

        btnSuaPhienBan.addActionListener(e -> {
            JFrame frameCha = (JFrame) SwingUtilities.getWindowAncestor(this);
            PhienBanSPDialog dialog = new PhienBanSPDialog(frameCha, txtMaSP.getText(), txtTenSP.getText(), true);
            dialog.setVisible(true);
        });
    }

    private JTextField createTextField(String text, boolean isEditable) {
        JTextField txt = new JTextField();
        txt.setText(text);
        txt.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        txt.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createLineBorder(new Color(200, 200, 200)),new EmptyBorder(5, 10, 5, 10)));
        
        if (!isEditable) {
            txt.setEditable(false);
            txt.setFocusable(false);
            txt.setBackground(new Color(240, 240, 240)); 
            txt.setForeground(Color.GRAY);
        }
        return txt;
    }

    public boolean isSuccess() {
        return isSuccess;
    }
}