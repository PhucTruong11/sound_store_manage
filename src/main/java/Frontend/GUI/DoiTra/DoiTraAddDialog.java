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

public class DoiTraAddDialog extends BaseThaoTacDialog {

    private JTextField txtMa, txtMaPX, txtMaKH, txtMaImei, txtNgayBan, txtHanCuoi, txtLyDo;
    private JButton btnChonPX, btnChonImei;
    private JDateChooser jdNgayDoi;
    private DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd/MM/yyyy");
    private JTextField txtMaImeiMoi;
    private JButton btnChonImeiMoi;

    private DoiTraBUS doiTraBUS = new DoiTraBUS();

    public DoiTraAddDialog() {
        super("THÊM PHIẾU ĐỔI TRẢ MỚI", 500, 700);

        String newMa = doiTraBUS.generateMaDoiTra();
        txtMa.setText(newMa);
        
        jdNgayDoi.setDate(new Date());
    }

    @Override
    protected void initForm() {
        pnlContent.add(new JLabel("Mã phiếu đổi trả:"));
        txtMa = new JTextField();
        txtMa.setEditable(false);
        txtMa.setBackground(new Color(240, 240, 240));
        pnlContent.add(txtMa, "growx, h 35!");

        pnlContent.add(new JLabel("Chọn Phiếu Xuất:"));
        txtMaPX = new JTextField();
        txtMaPX.setEditable(false);
        btnChonPX = new JButton("...");
        btnChonPX.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnChonPX.addActionListener(e -> openSelectPhieuXuat());
        pnlContent.add(txtMaPX, "split 2, growx, h 35!");
        pnlContent.add(btnChonPX, "w 40!, h 35!");

        pnlContent.add(new JLabel("Mã khách hàng:"));
        txtMaKH = new JTextField();
        txtMaKH.setEditable(false);
        txtMaKH.setBackground(new Color(240, 240, 240));
        pnlContent.add(txtMaKH, "growx, h 35!");

        pnlContent.add(new JLabel("Ngày bán:"));
        txtNgayBan = new JTextField();
        txtNgayBan.setEditable(false);
        txtNgayBan.setBackground(new Color(240, 240, 240));
        pnlContent.add(txtNgayBan, "growx, h 35!");

        pnlContent.add(new JLabel("Hạn cuối đổi trả:"));
        txtHanCuoi = new JTextField();
        txtHanCuoi.setEditable(false);
        txtHanCuoi.setBackground(new Color(240, 240, 240));
        pnlContent.add(txtHanCuoi, "growx, h 35!");

        pnlContent.add(new JLabel("Mã IMEI máy trả:"));
        txtMaImei = new JTextField();
        txtMaImei.setEditable(false);
        btnChonImei = new JButton("...");
        btnChonImei.setEnabled(false);
        btnChonImei.addActionListener(e -> openSelectImei());
        pnlContent.add(txtMaImei, "split 2, growx, h 35!");
        pnlContent.add(btnChonImei, "w 40!, h 35!");

        pnlContent.add(new JLabel("Mã IMEI thay thế:"));
        txtMaImeiMoi = new JTextField();
        txtMaImeiMoi.setEditable(false);
        btnChonImeiMoi = new JButton("...");
        btnChonImeiMoi.setEnabled(false);
        btnChonImeiMoi.addActionListener(e -> openSelectImeiMoi()); 
        pnlContent.add(txtMaImeiMoi, "split 2, growx, h 35!");
        pnlContent.add(btnChonImeiMoi, "w 40!, h 35!");

        pnlContent.add(new JLabel("Ngày đổi trả:"));
        jdNgayDoi = new JDateChooser();
        jdNgayDoi.setDateFormatString("dd/MM/yyyy");
        pnlContent.add(jdNgayDoi, "growx, h 35!");

        pnlContent.add(new JLabel("Lý do: "));
        txtLyDo = new JTextField();
        pnlContent.add(txtLyDo, "growx, h 35!");
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
            
            btnChonImei.setEnabled(true);
            btnChonImeiMoi.setEnabled(true);
            txtMaImei.setText("");
        }
    }

    private void openSelectImei() {
        String maPX = txtMaPX.getText();
        SelectImeiDialog dialog = new SelectImeiDialog(maPX);
        dialog.setVisible(true);
        
        if (dialog.getSelectedImei() != null) {
            txtMaImei.setText(dialog.getSelectedImei());
        }
    }

    private void openSelectImeiMoi() {
        SelectImeiTrongKhoDialog dialog = new SelectImeiTrongKhoDialog();
        dialog.setVisible(true);
        if (dialog.getSelectedImei() != null) {
            txtMaImeiMoi.setText(dialog.getSelectedImei());
        }
    }

    @Override
    protected void logicXacNhan() {
        if (txtMaPX.getText().isEmpty() || txtMaImei.getText().isEmpty() || txtMaImeiMoi.getText().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn Phiếu xuất, máy trả và máy mới thay thế!");
            return;
        }

        if (txtLyDo.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Vui lòng nhập lý do đổi trả!");
            return;
        }

        Date dateDoi = jdNgayDoi.getDate();
        if (dateDoi == null) {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn ngày đổi trả!");
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
        dt.setTrangThai(true);

        String imeiMoi = txtMaImeiMoi.getText();
        String res = doiTraBUS.add(dt, imeiMoi);

        if (res.equals("OK")) {
            JOptionPane.showMessageDialog(this, "Đổi trả thành công!\n- Máy cũ đã thu hồi.\n- Máy mới " + imeiMoi + " đã giao cho khách.");
            dispose();
        } else {
            JOptionPane.showMessageDialog(this, res, "Lỗi", JOptionPane.ERROR_MESSAGE);
        }
    }
}