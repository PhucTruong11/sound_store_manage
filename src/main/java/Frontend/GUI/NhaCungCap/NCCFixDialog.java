package Frontend.GUI.NhaCungCap;

import javax.swing.*;

import Backend.BUS.NhaCungCapBUS;
import Backend.DTO.NhaCungCap;
import Frontend.Compoent.BaseThaoTacDialog;

public class NCCFixDialog extends BaseThaoTacDialog{
    private JTextField txtMa, txtTen, txtDiaChi, txtSDT;
    private NhaCungCapBUS nccBUS = new NhaCungCapBUS();

    public NCCFixDialog(String ma, String ten, String diaChi, String sdt) {
        // super đã tự gọi initForm() rồi, không cần gọi lại nữa
        super("SỬA NHÀ CUNG CẤP", 450, 350);

        // Đổ dữ liệu cũ vào các ô text
        txtMa.setText(ma);
        txtTen.setText(ten);
        txtDiaChi.setText(diaChi);
        txtSDT.setText(sdt);

        // Không cho sửa mã
        txtMa.setEditable(false);
        txtMa.setFocusable(false);

        // Focus vào ô Tên và đưa con trỏ về cuối để ko bị bôi xanh
        // SwingUtilities.invokeLater(() -> {
        //     txtTen.requestFocusInWindow();
        //     txtTen.setCaretPosition(txtTen.getText().length());
        // });
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
