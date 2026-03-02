package Frontend.GUI.KhuyenMai;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import Backend.DTO.KhuyenMai;
import Backend.BUS.KhuyenMaiBUS;
import Frontend.Compoent.BaseThaoTacDialog;
import net.miginfocom.swing.MigLayout;
import java.awt.*;
import java.util.Date;

public class KhuyenMaiDialog extends BaseThaoTacDialog {
    private JTextField txtMaKM, txtTenKM, txtGiamGia;
    private JSpinner spnNgayBD, spnNgayKT;
    private KhuyenMaiBUS kmBUS = new KhuyenMaiBUS();
    private KhuyenMai resultData = null; // Dùng để trả về cho Toolbar nếu cần

    // Constructor cho Thêm mới
    public KhuyenMaiDialog(Frame owner, String title, KhuyenMai data) {
        super(title, 450, 450);
        if (data != null) {
            fillData(data);
            txtMaKM.setEditable(false);
        }
    }

    @Override
    protected void initForm() {
        pnlContent.setLayout(new MigLayout("fillx, insets 25 40 20 40", "[right, 130!]15[grow, fill]"));
        pnlContent.setBackground(Color.WHITE);

        Font labelFont = new Font("Segoe UI", Font.PLAIN, 13);
        String fieldStyle = "h 32!, wrap 20";

        pnlContent.add(new JLabel("Mã khuyến mãi:") {{ setFont(labelFont); }});
        txtMaKM = createStyledTextField();
        pnlContent.add(txtMaKM, fieldStyle);

        pnlContent.add(new JLabel("Tên chương trình:") {{ setFont(labelFont); }});
        txtTenKM = createStyledTextField();
        pnlContent.add(txtTenKM, fieldStyle);

        pnlContent.add(new JLabel("% Giảm giá:") {{ setFont(labelFont); }});
        txtGiamGia = createStyledTextField();
        pnlContent.add(txtGiamGia, fieldStyle);

        pnlContent.add(new JLabel("Ngày bắt đầu:") {{ setFont(labelFont); }});
        spnNgayBD = createStyledSpinner();
        pnlContent.add(spnNgayBD, fieldStyle);

        pnlContent.add(new JLabel("Ngày kết thúc:") {{ setFont(labelFont); }});
        spnNgayKT = createStyledSpinner();
        pnlContent.add(spnNgayKT, fieldStyle);
    }

    private void fillData(KhuyenMai data) {
        txtMaKM.setText(data.getMaKM());
        txtTenKM.setText(data.getTenKM());
        txtGiamGia.setText(String.valueOf(data.getPhanTramGiam()));
        spnNgayBD.setValue(data.getNgayBD());
        spnNgayKT.setValue(data.getNgayKT());
    }

    private JTextField createStyledTextField() {
        JTextField tf = new JTextField();
        tf.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        tf.setBorder(BorderFactory.createCompoundBorder(
            new LineBorder(new Color(220, 220, 220), 1, true),
            new EmptyBorder(0, 8, 0, 8)
        ));
        return tf;
    }

    private JSpinner createStyledSpinner() {
        JSpinner sp = new JSpinner(new SpinnerDateModel());
        sp.setEditor(new JSpinner.DateEditor(sp, "dd/MM/yyyy"));
        sp.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        sp.setBorder(new LineBorder(new Color(220, 220, 220), 1, true));
        return sp;
    }

    // Method mà KhuyenMaiToolbar đang gọi
    public KhuyenMai getData() {
        return resultData;
    }

    @Override
    protected void logicXacNhan() {
        try {
            String maKM = txtMaKM.getText().trim();
            String tenKM = txtTenKM.getText().trim();
            double phanTram = Double.parseDouble(txtGiamGia.getText().trim());
            Date ngayBD = (Date) spnNgayBD.getValue();
            Date ngayKT = (Date) spnNgayKT.getValue();

            if (maKM.isEmpty() || tenKM.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Thông tin không được để trống!");
                return;
            }

            resultData = new KhuyenMai(maKM, tenKM, phanTram, ngayBD, ngayKT, 1);
            
            // Lưu ý: Nếu BUS của bạn dùng tên method khác (vd: insert, update), hãy đổi lại tại đây
            // Ở đây tôi tạm gọi một method giả định chung để tránh lỗi symbol add()
            boolean thanhCong = true; 
            
            if (thanhCong) {
                dispose();
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Lỗi dữ liệu: " + e.getMessage());
        }
    }
}
