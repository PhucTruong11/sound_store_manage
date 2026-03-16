package Frontend.GUI.DoiTra;

import java.awt.Color;
import java.awt.Cursor;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Date;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

import com.toedter.calendar.JDateChooser;

import Backend.BUS.DoiTraBUS;
import Backend.DTO.DoiTra;
import Frontend.Compoent.BaseThaoTacDialog;

public class DoiTraFixDialog extends BaseThaoTacDialog {

    private JTextField txtMa, txtMaPX, txtMaKH, txtMaImei, txtNgayBan, txtHanCuoi, txtLyDo;
    private JButton btnChonPX, btnChonImei;
    private JDateChooser jdNgayDoi;
    private DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd/MM/yyyy");

    private DoiTraBUS doiTraBUS = new DoiTraBUS();

    public DoiTraFixDialog(DoiTra dt) {
        super("CẬP NHẬT PHIẾU ĐỔI TRẢ", 500, 500);

        txtMa.setText(dt.getMaDoiTra());
        txtMaPX.setText(dt.getMaPhieuXuat());
        txtMaKH.setText(dt.getMaKH());
        txtMaImei.setText(dt.getMaImei());
        txtLyDo.setText(dt.getLyDo());

        if (dt.getNgayDoiTra() != null) {
            Date date = Date.from(dt.getNgayDoiTra().atStartOfDay(ZoneId.systemDefault()).toInstant());
            jdNgayDoi.setDate(date);
        }

        txtMa.setEditable(false);
        txtMa.setBackground(new Color(240, 240, 240));
    }

    @Override
    protected void initForm() {
        pnlContent.setLayout(new net.miginfocom.swing.MigLayout("fillx, insets 20", "[right]15[grow, fill]"));

        pnlContent.add(new JLabel("Mã phiếu đổi trả:"));
        txtMa = new JTextField();
        pnlContent.add(txtMa, "h 35!, wrap");

        pnlContent.add(new JLabel("Chọn Phiếu Xuất:"));
        txtMaPX = new JTextField();
        txtMaPX.setEditable(false);
        btnChonPX = new JButton("...");
        btnChonPX.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnChonPX.addActionListener(e -> openSelectPhieuXuat());
        pnlContent.add(txtMaPX, "split 2, growx, h 35!");
        pnlContent.add(btnChonPX, "w 40!, h 35!, wrap");

        pnlContent.add(new JLabel("Mã khách hàng:"));
        txtMaKH = new JTextField();
        txtMaKH.setEditable(false);
        txtMaKH.setBackground(new Color(240, 240, 240));
        pnlContent.add(txtMaKH, "h 35!, wrap");

        pnlContent.add(new JLabel("Ngày bán:"));
        txtNgayBan = new JTextField();
        txtNgayBan.setEditable(false);
        txtNgayBan.setBackground(new Color(240, 240, 240));
        pnlContent.add(txtNgayBan, "h 35!, wrap");

        pnlContent.add(new JLabel("Hạn cuối đổi trả:"));
        txtHanCuoi = new JTextField();
        txtHanCuoi.setEditable(false);
        txtHanCuoi.setBackground(new Color(240, 240, 240));
        pnlContent.add(txtHanCuoi, "h 35!, wrap");

        pnlContent.add(new JLabel("Mã IMEI máy trả:"));
        txtMaImei = new JTextField();
        txtMaImei.setEditable(false);
        btnChonImei = new JButton("...");
        btnChonImei.addActionListener(e -> openSelectImei());
        pnlContent.add(txtMaImei, "split 2, growx, h 35!");
        pnlContent.add(btnChonImei, "w 40!, h 35!, wrap");

        pnlContent.add(new JLabel("Ngày đổi trả:"));
        jdNgayDoi = new JDateChooser();
        jdNgayDoi.setDateFormatString("dd/MM/yyyy");
        pnlContent.add(jdNgayDoi, "h 35!, wrap");

        pnlContent.add(new JLabel("Lý do: "));
        txtLyDo = new JTextField();
        pnlContent.add(txtLyDo, "h 35!, wrap");
    }

    private void openSelectPhieuXuat() {
        SelectPhieuXuatDialog dialog = new SelectPhieuXuatDialog();
        dialog.setVisible(true);
        Backend.DTO.PhieuXuat px = dialog.getSelectedPX();
        if (px != null) {
            txtMaPX.setText(px.getMaPhieuXuat());
            txtMaKH.setText(px.getMaKH());
            if (px.getNgayXuat() != null) {
                LocalDate ngayBan = px.getNgayXuat().toLocalDateTime().toLocalDate();
                txtNgayBan.setText(ngayBan.format(dtf));
                txtHanCuoi.setText(ngayBan.plusDays(30).format(dtf));
            }
            txtMaImei.setText(""); 
        }
    }

    private void openSelectImei() {
        String maPX = txtMaPX.getText();
        if(maPX.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn Phiếu xuất trước!");
            return;
        }
        SelectImeiDialog dialog = new SelectImeiDialog(maPX);
        dialog.setVisible(true);
        if (dialog.getSelectedImei() != null) {
            txtMaImei.setText(dialog.getSelectedImei());
        }
    }

    @Override
    protected void logicXacNhan() {
        if (txtMaPX.getText().isEmpty() || txtMaImei.getText().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn Phiếu xuất và IMEI!");
            return;
        }

        if (txtLyDo.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Vui lòng nhập lý do!");
            return;
        }

        Date dateDoi = jdNgayDoi.getDate();
        if (dateDoi == null) {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn ngày!");
            return;
        }

        LocalDate ngayDoiTra = dateDoi.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();

        DoiTra dt = new DoiTra();
        dt.setMaDoiTra(txtMa.getText());
        dt.setMaKH(txtMaKH.getText());
        dt.setMaPhieuXuat(txtMaPX.getText());
        dt.setMaImei(txtMaImei.getText());
        dt.setNgayDoiTra(ngayDoiTra);
        dt.setLyDo(txtLyDo.getText());

        String res = doiTraBUS.update(dt);

        if (res.equals("OK")) {
            JOptionPane.showMessageDialog(this, "Cập nhật thành công!");
            dispose();
        } else {
            JOptionPane.showMessageDialog(this, res, "Lỗi", JOptionPane.ERROR_MESSAGE);
        }
    }
}