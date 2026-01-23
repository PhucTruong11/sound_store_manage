package Frontend.GUI.SanPham;

import Frontend.Compoent.BaseThaoTacDialog;
import net.miginfocom.swing.MigLayout;

import javax.swing.*;

public class ThemSanPhamDialog extends BaseThaoTacDialog {

    private JTextField txtMa;
    private JTextField txtTen;
    private JTextField txtGia;

    public ThemSanPhamDialog(JFrame owner) {
        super("THÊM SẢN PHẨM MỚI", 500, 300);
    }

    @Override
    public void initForm() {
        pnlContent.setLayout(new MigLayout("wrap 2, fillx, insets 20", "[100!]10[grow]", "[]20[]20[]"));

        pnlContent.add(new JLabel("Mã sản phẩm:"));
        txtMa = new JTextField();
        pnlContent.add(txtMa, "growx, h 35!"); 

        pnlContent.add(new JLabel("Tên sản phẩm:"));
        txtTen = new JTextField();
        pnlContent.add(txtTen, "growx, h 35!");

        pnlContent.add(new JLabel("Đơn giá (VNĐ):"));
        txtGia = new JTextField();
        pnlContent.add(txtGia, "growx, h 35!");
    }

    @Override
    public void logicXacNhan() {
        System.out.println("Thêm sản phẩm thành công");
        dispose(); 
    }
}