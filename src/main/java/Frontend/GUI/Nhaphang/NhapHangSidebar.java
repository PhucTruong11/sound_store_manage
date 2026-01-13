package Frontend.GUI.Nhaphang;

import Frontend.Compoent.Theme;
import Frontend.Compoent.Button;
import net.miginfocom.swing.MigLayout;
import javax.swing.*;
import java.awt.*;

public class NhapHangSidebar extends JPanel{
    private JLabel lblTenSP, lblMaSP, lblTonKho;
    private JSpinner spnSoLuongNhap;

    public NhapHangSidebar() {
        setLayout(new MigLayout("wrap 1, fillx, insets 5", "[grow]", "[]10[]10[]"));
        setBackground(Color.WHITE);
        putClientProperty("FlatLaf.style", "arc: 15");

        initFilter();
        initInfo();
        initConfirmButton();
    }

    private void initFilter() {
        JComboBox<String> cboNhaCungCap = new JComboBox<>(new String[]{"Tất cả", "Sony Electronics", "JBL Official", "Marshall VN"});
        add(new JLabel("Nhà cung cấp:"), "gapbottom 5");
        add(cboNhaCungCap, "growx, h 35!");
    }

    private void initInfo() {
        lblTenSP = new JLabel("Chọn sản phẩm");
        lblTenSP.setFont(new Font("Segoe UI", Font.BOLD, 14));
        lblTenSP.setForeground(Theme.PRIMARY_COLOR);

        lblMaSP = new JLabel("Mã: -");
        lblTonKho = new JLabel("Tồn kho hiện tại: 0");

        add(lblTenSP, "gaptop 10");
        add(lblMaSP, "split 2, growx");
        add(lblTonKho);
        add(new JSeparator(), "growx, gaptop 5, gapbottom 5");

        add(new JLabel("Số lượng nhập:"));
        spnSoLuongNhap = new JSpinner(new SpinnerNumberModel(1, 1, 1000, 1));
        add(spnSoLuongNhap, "growx, h 35!");
    }

    private void initConfirmButton() {
        Button btnXacNhan = new Button("XÁC NHẬN NHẬP", Theme.ACCENT_COLOR);
        add(btnXacNhan, "pushy, aligny bottom, growx, h 40!");
    }

    // Các hàm cập nhật thông tin từ bên ngoài
    public void updateInfo(String ma, String ten, String gia) {
        lblMaSP.setText("Mã: " + ma);
        lblTenSP.setText(ten);
        lblTonKho.setText("Giá: " + gia);
    }
    
}
