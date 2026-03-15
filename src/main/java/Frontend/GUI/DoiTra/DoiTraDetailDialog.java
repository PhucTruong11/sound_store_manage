package Frontend.GUI.DoiTra;

import java.awt.Color;
import java.time.format.DateTimeFormatter;

import javax.swing.JLabel;
import javax.swing.JSeparator;
import javax.swing.JTextField;

import Backend.DTO.DoiTra;
import Frontend.Compoent.BaseThaoTacDialog;

public class DoiTraDetailDialog extends BaseThaoTacDialog {

    private DoiTra dt;
    private DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd/MM/yyyy");

    public DoiTraDetailDialog(DoiTra dt) {
        super("CHI TIẾT PHIẾU ĐỔI TRẢ", 500, 600);
        this.dt = dt;
        fillData();
        // Ẩn nút xác nhận vì đây là form chỉ xem
        btnXacNhan.setVisible(false);
        btnHuy.setText("Đóng");
    }

    @Override
    protected void initForm() {
        // Form này dùng để hiển thị nên mình sẽ thiết kế nhãn và ô text khóa
        addInfoField("Mã đổi trả:", txtMa = new JTextField());
        addInfoField("Mã phiếu xuất:", txtMaPX = new JTextField());
        
        pnlContent.add(new JSeparator(), "growx, span, gaptop 10, gapbottom 10");
        
        addInfoField("Khách hàng:", txtTenKH = new JTextField());
        addInfoField("Sản phẩm:", txtTenSP = new JTextField());
        
        pnlContent.add(new JSeparator(), "growx, span, gaptop 10, gapbottom 10");

        addInfoField("Ngày mua:", txtNgayMua = new JTextField());
        addInfoField("Hạn đổi trả:", txtHanDoi = new JTextField());
        addInfoField("Tình trạng hạn:", txtTrangThai = new JTextField());
        
        addInfoField("Lý do:", txtLyDo = new JTextField());
        addInfoField("Số lượng:", txtSL = new JTextField());
    }

    private void addInfoField(String label, JTextField txt) {
        pnlContent.add(new JLabel(label));
        txt.setEditable(false);
        txt.setFocusable(false);
        txt.setBackground(new Color(245, 245, 245)); // Màu xám nhẹ cho ô bị khóa
        pnlContent.add(txt, "growx, h 35!");
    }

    private void fillData() {
        txtMa.setText(dt.getMaDoiTra());
        txtMaPX.setText(dt.getMaPhieuXuat());
        txtTenKH.setText(dt.getTenKH() + " (" + dt.getMaKH() + ")");
        txtTenSP.setText(dt.getTenSP());
        txtNgayMua.setText(dt.getNgayDoiTra().format(dtf));
        txtHanDoi.setText(dt.getNgayHetHan().format(dtf));
        
        txtTrangThai.setText(dt.getTrangThaiThoiHan());
        // Highlight màu nếu hết hạn
        if(dt.getTrangThaiThoiHan().equals("Đã hết hạn đổi trả")) {
            txtTrangThai.setForeground(Color.RED);
        } else {
            txtTrangThai.setForeground(new Color(0, 150, 0)); // Màu xanh lá
        }
        
        txtLyDo.setText(dt.getLyDo());
        txtSL.setText(String.valueOf(dt.getSoLuong()));
    }

    // Khai báo các biến UI
    private JTextField txtMa, txtMaPX, txtTenKH, txtTenSP, txtNgayMua, txtHanDoi, txtTrangThai, txtLyDo, txtSL;

    @Override
    protected void logicXacNhan() {
        dispose();
    }
}