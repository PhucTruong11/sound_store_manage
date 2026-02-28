package Frontend.GUI.NhanVien;

import javax.swing.*;
import Backend.BUS.NhanVienBUS;
import Backend.DTO.NhanVien;
import Frontend.Compoent.BaseThaoTacDialog;

public class NhanVienFixDialog extends BaseThaoTacDialog {
    private JTextField txtMa, txtTen, txtSDT, txtDiaChi, txtChucVu, txtEmail, txtLuong;
    private NhanVienBUS nvBUS = new NhanVienBUS();

    public NhanVienFixDialog(String ma, String ten, String sdt, String diaChi,
                             String chucVu, String email, double luong) {
        super("SỬA NHÂN VIÊN", 500, 450);

        txtMa.setText(ma);
        txtTen.setText(ten);
        txtSDT.setText(sdt);
        txtDiaChi.setText(diaChi);
        txtChucVu.setText(chucVu);
        txtEmail.setText(email);
        txtLuong.setText(String.valueOf(luong));

        txtMa.setEditable(false);
        txtMa.setFocusable(false);
    }

    @Override
    protected void initForm() {
        pnlContent.add(new JLabel("Mã NV:"));
        txtMa = new JTextField();
        pnlContent.add(txtMa, "growx, h 35!");

        pnlContent.add(new JLabel("Tên NV:"));
        txtTen = new JTextField();
        pnlContent.add(txtTen, "growx, h 35!");

        pnlContent.add(new JLabel("SĐT:"));
        txtSDT = new JTextField();
        pnlContent.add(txtSDT, "growx, h 35!");

        pnlContent.add(new JLabel("Địa chỉ:"));
        txtDiaChi = new JTextField();
        pnlContent.add(txtDiaChi, "growx, h 35!");

        pnlContent.add(new JLabel("Chức vụ:"));
        txtChucVu = new JTextField();
        pnlContent.add(txtChucVu, "growx, h 35!");

        pnlContent.add(new JLabel("Email:"));
        txtEmail = new JTextField();
        pnlContent.add(txtEmail, "growx, h 35!");

        pnlContent.add(new JLabel("Lương:"));
        txtLuong = new JTextField();
        pnlContent.add(txtLuong, "growx, h 35!");
    }

    @Override
    protected void logicXacNhan() {
        try {
            double luong = Double.parseDouble(txtLuong.getText());
            NhanVien nv = new NhanVien(
                    txtMa.getText(),
                    txtTen.getText(),
                    txtSDT.getText(),
                    txtDiaChi.getText(),
                    txtChucVu.getText(),
                    txtEmail.getText(),
                    luong,
                    true
            );
            if (nvBUS.update(nv)) {
                JOptionPane.showMessageDialog(this, "Cập nhật nhân viên thành công!");
                dispose();
            } else {
                JOptionPane.showMessageDialog(this, "Cập nhật thất bại!");
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Lương phải là số!");
        }
    }
}
