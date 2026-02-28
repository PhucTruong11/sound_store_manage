package Frontend.GUI.KhuyenMai;

import Backend.DTO.KhuyenMai;
import com.toedter.calendar.JDateChooser;
import net.miginfocom.swing.MigLayout;
import javax.swing.*;
import java.awt.*;
import java.util.Date;

public class KhuyenMaiDialog extends JDialog {
    private JTextField txtMa, txtTen, txtGiam;
    private JDateChooser dateBD, dateKT;
    private JButton btnSave, btnCancel;
    private boolean confirmed = false;

    public KhuyenMaiDialog(Frame owner, String title, KhuyenMai data) {
super(owner, title, true);
    
    // 1. Ép kích thước ngay từ đầu
    Dimension size = new Dimension(550, 600);
    this.setSize(size);
    this.setPreferredSize(size);
    this.setMinimumSize(size);
    this.setResizable(false); 

    // 2. Panel chính
    JPanel container = new JPanel(new MigLayout("fillx, insets 35", "[right]20[grow, fill]"));
    container.setBackground(Color.WHITE);
    // Ép kích thước cho container để XWayland không bóp nhỏ
    container.setPreferredSize(size); 
    setContentPane(container);

    // Header
    JLabel lblHeader = new JLabel(title.toUpperCase());
    lblHeader.setFont(new Font("Segoe UI", Font.BOLD, 22));
    lblHeader.setForeground(new Color(52, 73, 94));
    container.add(lblHeader, "span 2, center, gapbottom 20");

    // fieldStyle: dùng h 45! để ép chiều cao dòng
    String fieldStyle = "h 45!, w 300!"; 

    container.add(new JLabel("Mã KM:")); 
    txtMa = new JTextField();
    container.add(txtMa, fieldStyle);

    container.add(new JLabel("Tên chương trình:")); 
    txtTen = new JTextField();
    container.add(txtTen, fieldStyle);

    container.add(new JLabel("% Giảm:")); 
    txtGiam = new JTextField();
    container.add(txtGiam, fieldStyle);

    container.add(new JLabel("Ngày bắt đầu:")); 
    dateBD = new JDateChooser(new Date());
    container.add(dateBD, fieldStyle);

    container.add(new JLabel("Ngày kết thúc:")); 
    dateKT = new JDateChooser(new Date());
    container.add(dateKT, fieldStyle);

    // Nút bấm
    btnSave = new JButton("Xác nhận");
    btnCancel = new JButton("Hủy bỏ");
    // ... (Giữ nguyên phần màu sắc nút bấm của bạn)

    container.add(btnSave, "split 2, center, gaptop 30, w 140!, h 45!");
    container.add(btnCancel, "w 140!, h 45!");

    // Quan trọng nhất cho Niri:
    this.pack(); // Để MigLayout tính toán
    this.setSize(size); // Ép lại kích thước sau khi pack
    this.setLocationRelativeTo(owner);
        if (data != null) {
            txtMa.setText(data.getMaKM());
            txtMa.setEditable(false);
            txtTen.setText(data.getTenKM());
            txtGiam.setText(String.valueOf(data.getPhanTramGiam()));
            dateBD.setDate(data.getNgayBD());
            dateKT.setDate(data.getNgayKT());
        }

        btnSave.addActionListener(e -> { confirmed = true; dispose(); });
        btnCancel.addActionListener(e -> dispose());
    }

    public KhuyenMai getData() {
        if (!confirmed) return null;
        try {
            return new KhuyenMai(txtMa.getText(), txtTen.getText(), 
                Double.parseDouble(txtGiam.getText()), dateBD.getDate(), dateKT.getDate(), 1);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Dữ liệu nhập vào không hợp lệ!");
            return null;
        }
    }
}
