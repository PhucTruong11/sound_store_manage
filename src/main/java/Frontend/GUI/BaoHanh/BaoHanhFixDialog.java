package Frontend.GUI.BaoHanh;

import javax.swing.*;
import Frontend.Compoent.BaseThaoTacDialog;

public class BaoHanhFixDialog extends BaseThaoTacDialog {
    private JTextField txtMa, txtTen, txtPhanTramGiam;

    public BaoHanhFixDialog(String ma, String ten, String phanTram) {
        // super đã tự gọi initForm() rồi, không cần gọi lại nữa
        super("SỬA BẢO HÀNH", 450, 500);

        // Đổ dữ liệu cũ vào các ô text
        txtMa.setText(ma);
        txtTen.setText(ten);
        txtPhanTramGiam.setText(phanTram);

        // Không cho sửa mã
        txtMa.setEditable(false);
        txtMa.setFocusable(false);

        // Focus vào ô Tên và đưa con trỏ về cuối để ko bị bôi xanh
        SwingUtilities.invokeLater(() -> {
            txtTen.requestFocusInWindow();
            txtTen.setCaretPosition(txtTen.getText().length());
        });
    }

    @Override
    protected void initForm() {
        pnlContent.add(new JLabel("Mã bảo hành:"));
        txtMa = new JTextField();
        pnlContent.add(txtMa, "growx, h 35!");

        pnlContent.add(new JLabel("Tên bảo hành:"));
        txtTen = new JTextField();
        pnlContent.add(txtTen, "growx, h 35!");

        pnlContent.add(new JLabel("Phần trăm giảm:"));
        txtPhanTramGiam = new JTextField();
        pnlContent.add(txtPhanTramGiam, "growx, h 35!");
    }

    @Override
    protected void logicXacNhan() {
        // TƯƠNG LAI: Kết nối BUS tại đây
        // nccBUS.update(new NCCDTO(txtMa.getText(), txtTen.getText(), ...));
        System.out.println("Đã cập nhật: " + txtTen.getText());
        dispose();
    }
}
