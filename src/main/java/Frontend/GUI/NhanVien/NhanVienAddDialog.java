package Frontend.GUI.NhanVien;

import javax.swing.*;
import Frontend.Compoent.BaseThaoTacDialog;
import net.miginfocom.swing.MigLayout;

public class NhanVienAddDialog extends BaseThaoTacDialog {
    private JTextField txtMa, txtTen, txtChucVu, txtSDT, txtDiaChi;

    public NhanVienAddDialog() {
        super("THÊM NHÂN VIÊN", 450, 450);

        initForm();
        logicXacNhan();
    }

    @Override
    protected void initForm() {
        pnlContent.setLayout(new MigLayout("wrap 2, fillx, insets 30", "[100!]20[grow]", "[]20[]20[]20[]20[]"));

        pnlContent.add(new JLabel("Mã NV:"));
        txtMa = new JTextField();
        pnlContent.add(txtMa, "growx, h 35!");

        pnlContent.add(new JLabel("Tên NV:"));
        txtTen = new JTextField();
        pnlContent.add(txtTen, "growx, h 35!");

        pnlContent.add(new JLabel("Chức vụ:"));
        txtChucVu = new JTextField();
        pnlContent.add(txtChucVu, "growx, h 35!");

        pnlContent.add(new JLabel("SĐT:"));
        txtSDT = new JTextField();
        pnlContent.add(txtSDT, "growx, h 35!");

        pnlContent.add(new JLabel("Địa chỉ:"));
        txtDiaChi = new JTextField();
        pnlContent.add(txtDiaChi, "growx, h 35!");
    }

    @Override
    protected void logicXacNhan() {
        System.out.println("Đã lưu NV: " + txtTen.getText());
        dispose();
    }
}
