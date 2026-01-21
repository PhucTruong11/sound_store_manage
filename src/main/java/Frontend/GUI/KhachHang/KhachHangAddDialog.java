package Frontend.GUI.KhachHang;

import Frontend.Compoent.BaseThaoTacDialog;
import net.miginfocom.swing.MigLayout;

import javax.swing.*;

public class KhachHangAddDialog extends BaseThaoTacDialog {
    private JTextField txtMa, txtTen, txtSDT, txtDiaChi;

    public KhachHangAddDialog() {
        super("THÊM KHÁCH HÀNG", 450, 400);

        initForm();
        logicXacNhan();
    }

    @Override
    protected void initForm() {
        pnlContent.setLayout(new MigLayout("wrap 2, fillx, insets 30", "[100!]20[grow]", "[]20[]20[]20[]20[]"));

        pnlContent.add(new JLabel("Mã KH:"));
        txtMa = new JTextField();
        pnlContent.add(txtMa, "growx, h 35!");

        pnlContent.add(new JLabel("Tên KH:"));
        txtTen = new JTextField();
        pnlContent.add(txtTen, "growx, h 35!");

        pnlContent.add(new JLabel("SĐT:"));
        txtSDT = new JTextField();
        pnlContent.add(txtSDT, "growx, h 35!");

        pnlContent.add(new JLabel("Địa chỉ:"));
        txtDiaChi = new JTextField();
        pnlContent.add(txtDiaChi, "growx, h 35!");
    }

    @Override
    protected void logicXacNhan() {
        System.out.println("Đã lưu KH: " + txtTen.getText());
        dispose();
    }
}
