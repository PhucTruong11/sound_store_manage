package Frontend.GUI.DoiTra;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

import com.toedter.calendar.JDateChooser;

import Backend.BUS.DoiTraBUS;
import Backend.DTO.DoiTra;
import Frontend.Compoent.BaseThaoTacDialog;

public class DoiTraFixDialog extends BaseThaoTacDialog {

    private JTextField txtMa, txtMaPX, txtMaKH, txtMaPhienBan, txtLyDo , txtSoLuong, txtTinhTrang;
    private JDateChooser jdNgay;
    private DoiTraBUS doiTraBUS = new DoiTraBUS();

    public DoiTraFixDialog(String ma, String maPX, String maKH, String maPhienBan,
                           String ngay, String LyDo ,String soLuong, String tinhTrang) {

        super("SỬA ĐỔI TRẢ", 450, 500);

        txtMa.setText(ma);
        txtMaPX.setText(maPX);
        txtMaKH.setText(maKH);
        txtMaPhienBan.setText(maPhienBan);
        txtLyDo.setText(LyDo);
        txtSoLuong.setText(soLuong);
        txtTinhTrang.setText(tinhTrang);

        txtMa.setEditable(false);
        txtMa.setFocusable(false);

        
        try {
            LocalDate localDate = LocalDate.parse(ngay);
            Date date = Date.from(localDate.atStartOfDay(ZoneId.systemDefault()).toInstant());
            jdNgay.setDate(date);
        } catch (Exception e) {
            jdNgay.setDate(new Date()); // Nếu lỗi thì để ngày hiện tại
        }
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
        jdNgay.setDateFormatString("dd/MM/yyyy");
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
        // Lấy ngày từ JDateChooser (Không lo bị lỗi gõ chữ "sdg" nữa)
        Date selectedDate = jdNgay.getDate();
        if (selectedDate == null) {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn ngày hợp lệ!");
            return;
        }
        
        // Chuyển từ Date sang LocalDate
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

            String validationMsg = doiTraBUS.validate(dt, false);

            if (!validationMsg.equals("OK")) {
                JOptionPane.showMessageDialog(this, validationMsg, "Lỗi dữ liệu", JOptionPane.WARNING_MESSAGE);
                return;
            }

            if (doiTraBUS.update(dt)) {
                JOptionPane.showMessageDialog(this, "Cập nhật thành công!");
                dispose();
            } else {
                JOptionPane.showMessageDialog(this, "Cập nhật thất bại!");
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Số lượng phải là con số!");
        }
    }
}