package Frontend.GUI.DoiTra;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;

import com.toedter.calendar.JDateChooser;

import Backend.BUS.DoiTraBUS;
import Backend.DTO.DoiTra;
import Frontend.Compoent.BaseThaoTacDialog;

public class DoiTraAddDialog extends BaseThaoTacDialog {

    private JTextField txtMa, txtMaPX, txtMaKH, txtMaPhienBan, txtSoLuong, txtLyDo;
    private JDateChooser jdNgay;
    private JTextField txtTinhTrang;

    private DoiTraBUS doiTraBUS = new DoiTraBUS();

    public DoiTraAddDialog() {

        super("THÊM ĐỔI TRẢ", 450, 500);

        String newMa = doiTraBUS.generateMaDoiTra();
        txtMa.setText(newMa);

        txtMa.setEditable(false);
        txtMa.setFocusable(false);

        jdNgay.setDate(new Date());
        jdNgay.setDateFormatString("dd/MM/yyyy");

        SwingUtilities.invokeLater(() -> {
            txtMaPX.requestFocusInWindow();
        });
    }

    @Override
    protected void initForm() {

        pnlContent.add(new JLabel("Mã đổi trả:"));
        txtMa = new JTextField();
        pnlContent.add(txtMa, "growx, h 35!");

        pnlContent.add(new JLabel("Mã phiếu xuất:"));
        txtMaPX = new JTextField();
        pnlContent.add(txtMaPX, "growx, h 35!");

        pnlContent.add(new JLabel("Mã khách hàng:"));
        txtMaKH = new JTextField();
        pnlContent.add(txtMaKH, "growx, h 35!");

        pnlContent.add(new JLabel("Mã phiên bản:"));
        txtMaPhienBan = new JTextField();
        pnlContent.add(txtMaPhienBan, "growx, h 35!");

        pnlContent.add(new JLabel("Ngày đổi trả:"));
        jdNgay = new JDateChooser();
        pnlContent.add(jdNgay, "growx, h 35!");

        pnlContent.add(new JLabel("Lý do:"));
        txtLyDo = new JTextField();
        pnlContent.add(txtLyDo, "growx, h 35!");

        pnlContent.add(new JLabel("Số lượng:"));
        txtSoLuong = new JTextField();
        pnlContent.add(txtSoLuong, "growx, h 35!");

        pnlContent.add(new JLabel("Tình trạng:"));
        txtTinhTrang = new JTextField();
        pnlContent.add(txtTinhTrang, "growx, h 35!");
    }

    @Override
    protected void logicXacNhan() {
        Date selectedDate = jdNgay.getDate();
        if (selectedDate == null) {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn ngày hợp lệ!", "Lỗi", JOptionPane.ERROR_MESSAGE);
            return;
        }
        LocalDate ngay = selectedDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();

        try {
            DoiTra dt = new DoiTra(
                txtMa.getText(),
                txtMaPX.getText(),
                txtMaKH.getText(),
                txtMaPhienBan.getText(),
                ngay,
                Integer.parseInt(txtSoLuong.getText().trim()),
                txtLyDo.getText(),
                txtTinhTrang.getText()
            );

            String validationMsg = doiTraBUS.validate(dt, true);

            if (!validationMsg.equals("OK")) {
                JOptionPane.showMessageDialog(this, validationMsg, "Lỗi dữ liệu", JOptionPane.WARNING_MESSAGE);
                return;
            }

            if (doiTraBUS.add(dt)) {
                JOptionPane.showMessageDialog(this, "Thêm thành công!");
                dispose();
            } else {
                JOptionPane.showMessageDialog(this, "Thêm thất bại!");
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Số lượng phải là một con số!");
        }
    }
}