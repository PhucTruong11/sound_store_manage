package Frontend.GUI.BaoHanh;

import javax.swing.*;
import Frontend.Compoent.BaseThaoTacDialog;

public class BaoHanhAddDialog extends BaseThaoTacDialog {
    private JTextField txtMa, txtTen, txtPhanTramGiam;

    public BaoHanhAddDialog() {
        // super đã tự gọi initForm() rồi, không cần gọi lại nữa
        super("THÊM BẢO HÀNH", 450, 400);
    }

    @Override
    protected void initForm() {
        // pnlContent.setLayout(new MigLayout("wrap 2, fillx, insets 30",
        // "[100!]20[grow]", "[]20[]"));
        pnlContent.add(new JLabel("Mã NCC:"));
        txtMa = new JTextField();
        pnlContent.add(txtMa, "growx, h 35!");

        pnlContent.add(new JLabel("Tên NCC:"));
        txtTen = new JTextField();
        pnlContent.add(txtTen, "growx, h 35!");

        pnlContent.add(new JLabel("Phần trăm giảm:"));
        txtPhanTramGiam = new JTextField();
        pnlContent.add(txtPhanTramGiam, "growx, h 35!");
    }

    @Override
    protected void logicXacNhan() {
        // TƯƠNG LAI: Kết nối BUS tại đây
        System.out.println("Đã lưu Bảo hành: " + txtTen.getText());
        dispose();
    }
}
