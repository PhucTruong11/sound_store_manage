package Frontend.GUI.SanPham;

import Frontend.Compoent.ThaoTacDialog;
import Backend.BUS.SanPhamBUS;
import Backend.DTO.SanPham;

import javax.swing.*;
import java.awt.*;

public class ThemSanPhamDialog extends ThaoTacDialog {

    private SanPhamBUS spBUS = new SanPhamBUS();
    private JTextField txtMaSP, txtTenSP, txtMaLoai, txtMaHang, txtBaoHanh;
    private JTextArea txtMoTa;
    private boolean isSuccess = false;
    private JFrame parentFrame;

    public ThemSanPhamDialog(JFrame parent) {
        super(parent, "THÊM SẢN PHẨM", 550, 480);
        this.parentFrame = parent; 

        txtMaSP.setText(spBUS.getNextID());
        txtMaSP.setEditable(false);
        txtMaSP.setFocusable(false);
    }


    @Override
    protected void initForm() {
        pnlContent.add(new JLabel("Mã sản phẩm: "));
        txtMaSP = new JTextField();
        pnlContent.add(txtMaSP, "growx,h 35!");

        pnlContent.add(new JLabel("Tên sản phẩm: "));
        txtTenSP = new JTextField();
        pnlContent.add(txtTenSP, "growx, h 35!");

        pnlContent.add(new JLabel("Mã loại: "));
        txtMaLoai = new JTextField();
        pnlContent.add(txtMaLoai, "growx, h 35!");

        pnlContent.add(new JLabel("Mã Hãng: "));
        txtMaHang = new JTextField();
        pnlContent.add(txtMaHang, "growx, h 35!");

        pnlContent.add(new JLabel("Bảo hành (tháng): "));
        txtBaoHanh = new JTextField();
        pnlContent.add(txtBaoHanh, "growx, h 35!");

        pnlContent.add(new JLabel("Mô tả sản phẩm: "), "aligny top");
        txtMoTa = new JTextArea(4, 20);
        txtMoTa.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        txtMoTa.setLineWrap(true);
        txtMoTa.setWrapStyleWord(true);
        txtMoTa.setBorder(BorderFactory.createLineBorder(new Color(200, 200, 200)));
        JScrollPane scrollMoTa = new JScrollPane(txtMoTa);
        pnlContent.add(scrollMoTa, "growx, h 100!");
    }

    @Override
    protected void logicXacNhan() {
        if (txtMaSP.getText().trim().isEmpty() || txtTenSP.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Vui lòng nhập Mã và Tên sản phẩm!");
            return;
        }
        try{
            // Tạo đối tượng SanPham
            SanPham sp = new SanPham();
            sp.setMaSP(txtMaSP.getText().trim());
            sp.setTenSP(txtTenSP.getText().trim());
            sp.setMaLoai(txtMaLoai.getText().trim());
            sp.setMaHang(txtMaHang.getText().trim());
            sp.setMoTa(txtMoTa.getText().trim());
            sp.setThoiGianBaoHanh(Integer.parseInt(txtBaoHanh.getText().trim()));
            sp.setTrangThai(true);

            boolean ketQua = spBUS.add(sp);
            if (ketQua) {
                this.isSuccess = true;
                int confirm = JOptionPane.showConfirmDialog(this,"Thêm sản phẩm thành công\nBạn có muốn thiết lập Phiên Bản  ngay không?", "Thông báo",JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
                this.dispose(); // Đóng form Thêm SP
                if (confirm == JOptionPane.YES_OPTION) {
                    // Mở form Quản lý phiên bản
                    PhienBanSPDialog pbDlg = new PhienBanSPDialog(parentFrame, sp.getMaSP(), sp.getTenSP(), true);
                    pbDlg.setVisible(true);
                }

            } else {
                JOptionPane.showMessageDialog(this, "Thêm thất bại");
            }
        }catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Thời gian bảo hành phải là số nguyên!");
        } 

    }

    public boolean isSuccess() {
        return isSuccess;
    }
}