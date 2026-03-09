package Frontend.GUI.BaoHanh;

import javax.swing.*;
import Backend.DTO.BaoHanh;
import Backend.DTO.ChiTietBaoHanh;
import Backend.BUS.BaoHanhBUS;
import Backend.BUS.ChiTietBaoHanhBUS;
import Frontend.Compoent.BaseThaoTacDialog;
import net.miginfocom.swing.MigLayout;
import java.time.LocalDate;

public class BaoHanhAddDialog extends BaseThaoTacDialog {
    private JTextField txtMaBH, txtImei, txtMaPX;
    private JSpinner spnNgayBD, spnNgayKT;
    private JTextField txtNoiDung;
    private JComboBox<String> cboTinhTrang;
    private BaoHanhBUS bhBUS = new BaoHanhBUS();

    public BaoHanhAddDialog() {
        super("THÊM PHIẾU BẢO HÀNH", 450, 650);
        String newMa = bhBUS.getNewMaBH();
        txtMaBH.setText(newMa);

        txtMaBH.setEditable(false);
        txtMaBH.setFocusable(false);

        SwingUtilities.invokeLater(() -> {
            txtImei.requestFocusInWindow();
        });
    }

    @Override
    protected void initForm() {
        // pnlContent.setLayout(new MigLayout("wrap 2, fillx, insets 30",
        // "[100!]20[grow]", "[]20[]20[]20[]20[]"));
        pnlContent.setLayout(new MigLayout("wrap 2, fillx, insets 30", "[100!]20[grow]", "[]20[]20[]20[]20[]20[]20[]"));

        pnlContent.add(new JLabel("Mã bảo hành:"));
        txtMaBH = new JTextField();
        pnlContent.add(txtMaBH, "growx, h 35!");

        pnlContent.add(new JLabel("Mã Imei:"));
        txtImei = new JTextField();
        pnlContent.add(txtImei, "growx, h 35!");

        pnlContent.add(new JLabel("Mã phiếu xuất:"));
        txtMaPX = new JTextField();
        pnlContent.add(txtMaPX, "growx, h 35!");

        pnlContent.add(new JLabel("Ngày bắt đầu:"));
        spnNgayBD = new JSpinner(new SpinnerDateModel());
        spnNgayBD.setEditor(new JSpinner.DateEditor(spnNgayBD, "dd/MM/yyyy"));
        pnlContent.add(spnNgayBD, "growx, h 35!");

        pnlContent.add(new JLabel("Ngày kết thúc:"));
        spnNgayKT = new JSpinner(new SpinnerDateModel());
        spnNgayKT.setEditor(new JSpinner.DateEditor(spnNgayKT, "dd/MM/yyyy"));
        pnlContent.add(spnNgayKT, "growx, h 35!");

        pnlContent.add(new JLabel("Nội dung lỗi:"));
        txtNoiDung = new JTextField("Tiếp nhận thiết bị");
        pnlContent.add(txtNoiDung, "growx, h 35!");

        pnlContent.add(new JLabel("Tình trạng:"));
        cboTinhTrang = new JComboBox<>(new String[] {
                "Đang sửa chữa", "Hoàn thành", "Đã trả máy"
        });
        pnlContent.add(cboTinhTrang, "growx, h 35!");
    }

    @Override
    protected void logicXacNhan() {
        String maBH = txtMaBH.getText().trim();
        String imei = txtImei.getText().trim();
        String maPX = txtMaPX.getText().trim();

        if (maBH.isEmpty() || imei.isEmpty() || maPX.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Vui lòng nhập đầy đủ thông tin!");
            return;
        }

        if (imei.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Vui lòng nhập Mã IMEI!", "Lỗi nhập liệu", JOptionPane.WARNING_MESSAGE);
            txtImei.requestFocus();
            return;
        }

        if (maPX.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Vui lòng nhập Mã phiếu xuất!", "Lỗi nhập liệu",
                    JOptionPane.WARNING_MESSAGE);
            txtMaPX.requestFocus();
            return;
        }

        if (!imei.matches("^[a-zA-Z0-9]+$")) {
            JOptionPane.showMessageDialog(this, "Mã IMEI không hợp lệ! Chỉ được chứa chữ cái và số.", "Lỗi định dạng",
                    JOptionPane.ERROR_MESSAGE);
            txtImei.requestFocus();
            return;
        }

        if (!maPX.matches("^[a-zA-Z0-9]+$")) {
            JOptionPane.showMessageDialog(this, "Mã Phiếu xuất không hợp lệ! Chỉ được chứa chữ cái và số.",
                    "Lỗi định dạng", JOptionPane.ERROR_MESSAGE);
            txtMaPX.requestFocus();
            return;
        }

        java.util.Date dStart = (java.util.Date) spnNgayBD.getValue();
        java.util.Date dEnd = (java.util.Date) spnNgayKT.getValue();
        LocalDate ngayBD = new java.sql.Date(dStart.getTime()).toLocalDate();
        LocalDate ngayKT = new java.sql.Date(dEnd.getTime()).toLocalDate();

        if (ngayKT.isBefore(ngayBD)) {
            JOptionPane.showMessageDialog(this, "Lỗi: Ngày kết thúc bảo hành không được nhỏ hơn ngày bắt đầu!",
                    "Lỗi logic", JOptionPane.ERROR_MESSAGE);
            return;
        }

        BaoHanh bh = new BaoHanh(maBH, imei, maPX, ngayBD, ngayKT);

        if (bhBUS.add(bh)) {
            ChiTietBaoHanhBUS ctbhBUS = new ChiTietBaoHanhBUS();

            String maCTMoi = ctbhBUS.getNewMaCTBH();

            // ChiTietBaoHanh ctbhMacDinh = new ChiTietBaoHanh(
            // maCTMoi,
            // bh.getMaBH(),
            // null,
            // "Tiếp nhận thiết bị",
            // "Đang sửa chữa");

            String noiDungLoi = txtNoiDung.getText().trim();
            if (noiDungLoi.isEmpty())
                noiDungLoi = "Tiếp nhận thiết bị";

            String tinhTrangChon = cboTinhTrang.getSelectedItem().toString();

            ChiTietBaoHanh ctbhMacDinh = new ChiTietBaoHanh(
                    maCTMoi,
                    bh.getMaBH(),
                    null,
                    noiDungLoi,
                    tinhTrangChon);

            if (ctbhBUS.add(ctbhMacDinh)) {
                JOptionPane.showMessageDialog(this, "Thêm thành công!");
                dispose();
            }
        }
    }
}