package Frontend.GUI.BanHang;

import Frontend.Compoent.BaseThaoTacDialog;
import net.miginfocom.swing.MigLayout;
import javax.swing.*;
import java.awt.event.ActionListener;

public class KhachHangAddDialog extends BaseThaoTacDialog {
    private JTextField txtMa, txtTen, txtSDT, txtDiaChi;

    public KhachHangAddDialog() {
        super("THÊM KHÁCH HÀNG MỚI", 450, 450);
        initForm();
    }

    @Override
    protected void initForm() {
        pnlContent.setLayout(new MigLayout("wrap 2, fillx, insets 30", "[100!]20[grow]", "[]15[]15[]15[]"));

        pnlContent.add(new JLabel("Mã KH:"));
        txtMa = new JTextField();
        txtMa.setToolTipText("Ví dụ: KH001");
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
        String ma = txtMa.getText().trim();
        String ten = txtTen.getText().trim();
        String sdt = txtSDT.getText().trim();
        String diaChi = txtDiaChi.getText().trim();

        if (ma.isEmpty() || ten.isEmpty() || sdt.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Vui lòng nhập đầy đủ Mã, Tên và SĐT!");
            return;
        }

        System.out.println("Đã lưu KH vào hệ thống: " + ten);
        dispose();
    }
}