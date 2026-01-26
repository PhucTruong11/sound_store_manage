package Frontend.GUI.NhaCungCap;

import javax.swing.*;
import Frontend.Compoent.BaseThaoTacDialog;

public class NCCAddDialog extends BaseThaoTacDialog{
    private JTextField txtMa, txtTen, txtDiaChi, txtSDT;

    public NCCAddDialog() {
        // super đã tự gọi initForm() rồi, không cần gọi lại nữa
        super("THÊM NHÀ CUNG CẤP", 450, 350);
    }

    @Override
    protected void initForm() {
        // pnlContent.setLayout(new MigLayout("wrap 2, fillx, insets 30", "[100!]20[grow]", "[]20[]"));
        pnlContent.add(new JLabel("Mã NCC:"));
        txtMa = new JTextField();
        pnlContent.add(txtMa, "growx, h 35!");

        pnlContent.add(new JLabel("Tên NCC:"));
        txtTen = new JTextField();
        pnlContent.add(txtTen, "growx, h 35!");

        pnlContent.add(new JLabel("Địa chỉ:"));
        txtDiaChi = new JTextField();
        pnlContent.add(txtDiaChi, "growx, h 35!");

        pnlContent.add(new JLabel("Số điẹn thoại:"));
        txtSDT = new JTextField();
        pnlContent.add(txtSDT, "growx, h 35!");
    }

    @Override
    protected void logicXacNhan() {
        // TƯƠNG LAI: Kết nối BUS tại đây
        System.out.println("Đã lưu NCC: " + txtTen.getText());
        dispose();
    }
}
