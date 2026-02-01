package Frontend.GUI.NhaCungCap;

import javax.swing.*;
import Backend.BUS.NhaCungCapBUS;
import Backend.DTO.NhaCungCap;
import Frontend.Compoent.BaseThaoTacDialog;

public class NCCFixDialog extends BaseThaoTacDialog{
    private JTextField txtMa, txtTen, txtDiaChi, txtSDT;
    private NhaCungCapBUS nccBUS = new NhaCungCapBUS();

    public NCCFixDialog(String ma, String ten, String diaChi, String sdt) {
        super("SỬA NHÀ CUNG CẤP", 450, 350);

        txtMa.setText(ma);
        txtTen.setText(ten);
        txtDiaChi.setText(diaChi);
        txtSDT.setText(sdt);

        txtMa.setEditable(false);
        txtMa.setFocusable(false);
    }

    @Override
    protected void initForm() {
        pnlContent.add(new JLabel("Mã NCC:"));
        txtMa = new JTextField();
        pnlContent.add(txtMa, "growx, h 35!");

        pnlContent.add(new JLabel("Tên NCC:"));
        txtTen = new JTextField();
        pnlContent.add(txtTen, "growx, h 35!");

        pnlContent.add(new JLabel("Địa chỉ:"));
        txtDiaChi = new JTextField();
        pnlContent.add(txtDiaChi, "growx, h 35!");

        pnlContent.add(new JLabel("Số điện thoại:"));
        txtSDT = new JTextField();
        pnlContent.add(txtSDT, "growx, h 35!");
    }

    @Override
    protected void logicXacNhan() {
        NhaCungCap ncc = new NhaCungCap(txtMa.getText(), txtTen.getText(), txtDiaChi.getText(), txtSDT.getText());
        if(nccBUS.update(ncc)) {
            JOptionPane.showMessageDialog(this, "Cập nhật thành công!");
            dispose();
        }
    }
}
