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
    private KhuyenMai resultData = null;

    // Constructor cho Thêm mới/Sửa
    public KhuyenMaiDialog(Frame owner, String title, KhuyenMai data) {
        super(title, 450, 450);
        
        if (data != null) {
            // Trường hợp SỬA: Đổ dữ liệu cũ vào
            fillData(data);
            txtMaKM.setEditable(false);
            txtMaKM.setFocusable(false);
        } else {
            // Trường hợp THÊM MỚI: Tự động lấy mã mới
            String newMa = kmBUS.getNewMaKM(); // Đảm bảo BUS đã có hàm này
            txtMaKM.setText(newMa);
            
            // Khóa lại không cho user sửa mã tự tăng
            txtMaKM.setEditable(false);
            txtMaKM.setFocusable(false);
            
            // Tự động nhảy con trỏ xuống ô Tên chương trình
            SwingUtilities.invokeLater(() -> txtTenKM.requestFocusInWindow());
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

    // Các hàm helper giữ nguyên
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

            if (tenKM.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Tên chương trình không được để trống!");
                return;
            }

            resultData = new KhuyenMai(maKM, tenKM, phanTram, ngayBD, ngayKT, 1);
            
            // Logic lưu xuống DB thông qua BUS
            String msg = kmBUS.validate(resultData, txtMaKM.isEditable()); // Giả định hàm validate của bạn
            if (!msg.equals("OK") && !msg.isEmpty()) {
                 // Nếu BUS có hàm validate thì check ở đây
            }

            // Gọi hàm add/update của BUS tùy theo logic của bạn
            dispose();
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Vui lòng nhập số hợp lệ cho phần giảm giá!");
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Lỗi: " + e.getMessage());
        }
    }
}
