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
        
        // 1. Định nghĩa kích thước chuẩn (đã tính toán cho Scale 2.1)
        Dimension size = new Dimension(550, 600);
        this.setSize(size);
        this.setPreferredSize(size);
        this.setMinimumSize(size);
        this.setResizable(false);
        this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);

        // 2. Tạo Panel chính với MigLayout quản lý lưới 2 cột
        // "fillx" trải rộng ngang, "insets 35" tạo lề an toàn
        // "[right]20[grow, fill]" cột 1 căn phải, cột 2 tự giãn và lấp đầy
        JPanel container = new JPanel(new MigLayout("fillx, insets 35", "[right]20[grow, fill]"));
        container.setBackground(Color.WHITE);
        container.setPreferredSize(size);
        setContentPane(container);

        // Header - Tiêu đề lớn căn giữa
        JLabel lblHeader = new JLabel(title.toUpperCase());
        lblHeader.setFont(new Font("Segoe UI", Font.BOLD, 22));
        lblHeader.setForeground(new Color(52, 73, 94));
        container.add(lblHeader, "span 2, center, gapbottom 25, wrap");

        // 3. Khởi tạo và Style linh kiện
        // "h 45!" ép chiều cao, "w 300!" ép chiều rộng, "wrap" ép xuống dòng
        String fieldStyle = "h 45!, w 300!, wrap";

        txtMa = new JTextField();
        txtTen = new JTextField();
        txtGiam = new JTextField();
        dateBD = new JDateChooser(new Date());
        dateKT = new JDateChooser(new Date());

        // Áp dụng định dạng hiển thị cho JDateChooser
        dateBD.setDateFormatString("dd/MM/yyyy");
        dateKT.setDateFormatString("dd/MM/yyyy");

        // Thêm linh kiện vào lưới
        container.add(new JLabel("Mã KM:")); 
        container.add(txtMa, fieldStyle);

        container.add(new JLabel("Tên chương trình:")); 
        container.add(txtTen, fieldStyle);

        container.add(new JLabel("% Giảm:")); 
        container.add(txtGiam, fieldStyle);

        container.add(new JLabel("Ngày bắt đầu:")); 
        container.add(dateBD, fieldStyle);

        container.add(new JLabel("Ngày kết thúc:")); 
        container.add(dateKT, fieldStyle);

        // 4. Các nút bấm hành động
        btnSave = new JButton("Xác nhận");
        btnSave.setBackground(new Color(26, 188, 156)); // Màu Teal hiện đại
        btnSave.setForeground(Color.WHITE);
        btnSave.setFont(new Font("Segoe UI", Font.BOLD, 14));
        btnSave.setFocusPainted(false);

        btnCancel = new JButton("Hủy bỏ");
        btnCancel.setBackground(new Color(189, 195, 199)); // Màu xám nhẹ
        btnCancel.setFont(new Font("Segoe UI", Font.BOLD, 14));
        btnCancel.setFocusPainted(false);

        // Bố trí nút bấm: "split 2" gộp 2 nút vào 1 hàng, "center" căn giữa hàng đó
        container.add(btnSave, "split 2, center, gaptop 40, w 140!, h 45!");
        container.add(btnCancel, "w 140!, h 45!");

        // 5. Đổ dữ liệu nếu ở chế độ Chỉnh sửa
        if (data != null) {
            txtMa.setText(data.getMaKM());
            txtMa.setEditable(false);
            txtTen.setText(data.getTenKM());
            txtGiam.setText(String.valueOf(data.getPhanTramGiam()));
            dateBD.setDate(data.getNgayBD());
            dateKT.setDate(data.getNgayKT());
        }

        // 6. Xử lý sự kiện
        btnSave.addActionListener(e -> {
            if (validateInput()) {
                confirmed = true;
                dispose();
            }
        });
        
        btnCancel.addActionListener(e -> dispose());

        // 7. CÚ CHỐT: Ép vẽ lại toàn bộ để fix lỗi trắng xóa trên Wayland
        this.pack();
        this.setSize(size);
        this.setLocationRelativeTo(owner);
        
        // Buộc layout manager tính toán lại và vẽ lại linh kiện ngay lập tức
        container.revalidate();
        container.repaint();
    }

    // Kiểm tra dữ liệu nhập vào cơ bản
    private boolean validateInput() {
        if (txtMa.getText().trim().isEmpty() || txtTen.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Vui lòng nhập đầy đủ Mã và Tên KM!");
            return false;
        }
        try {
            double giam = Double.parseDouble(txtGiam.getText());
            if (giam < 0 || giam > 100) throw new NumberFormatException();
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "% giảm giá phải là số từ 0 - 100!");
            return false;
        }
        return true;
    }

    public KhuyenMai getData() {
        if (!confirmed) return null;
        try {
            return new KhuyenMai(
                txtMa.getText().trim(),
                txtTen.getText().trim(),
                Double.parseDouble(txtGiam.getText()),
                dateBD.getDate(),
                dateKT.getDate(),
                1 // Trạng thái mặc định
            );
        } catch (Exception e) {
            return null;
        }
    }
}
