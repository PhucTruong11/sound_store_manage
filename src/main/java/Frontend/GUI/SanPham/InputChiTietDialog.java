package Frontend.GUI.SanPham;

import Backend.BUS.ChiTietSPBUS;
import Backend.DTO.ChiTietSP;
import Frontend.Compoent.ThaoTacDialog;
//import Frontend.Compoent.Theme;
import javax.swing.*;
import java.awt.*;

public class InputChiTietDialog extends ThaoTacDialog  {

    private JTextField txtIMEI, txtMaPB, txtMaPN, txtMaPX;
    private JComboBox<String> cboTinhTrang;
    private ChiTietSPBUS ctBUS = new ChiTietSPBUS();
    private boolean isUpdate = false;
    private boolean isSuccess = false;
    private String maPB;

    public InputChiTietDialog(JFrame parent, String maPB, ChiTietSP ct) {
        super(parent, ct == null ? "THÊM IMEI MỚI" : "CẬP NHẬT THÔNG TIN", 450,500 );
        this.maPB = maPB;
        this.isUpdate = (ct != null);
        
        // Điền dữ liệu nếu là form sửa
        if (isUpdate) {
            fillData(ct);
            txtMaPB.setEditable(false);
            txtMaPB.setFocusable(false);
            txtMaPN.setEditable(false);
            txtMaPN.setFocusable(false);
            txtMaPX.setEditable(false);
            txtMaPX.setFocusable(false);
        } else {
            txtMaPB.setText(maPB);
            txtMaPN.setText("N/A"); 
            txtMaPX.setText("N/A"); 
            cboTinhTrang.setSelectedItem("Tồn kho");
        }
    }

    @Override
    protected void initForm(){
    
        pnlContent.add(new JLabel("Mã IMEI:")); 
        txtIMEI = new JTextField();
        txtIMEI.setEditable(!isUpdate);
        pnlContent.add(txtIMEI, "growx,h 35!");
        
        pnlContent.add(new JLabel("Mã Phiên Bản:"));
        txtMaPB = new JTextField();
        pnlContent.add(txtMaPB, "growx,h 35!");

        pnlContent.add(new JLabel("Mã Phiếu Nhập:"));
        txtMaPN = new JTextField();
        pnlContent.add(txtMaPN, "growx,h 35!");

        pnlContent.add(new JLabel("Mã Phiếu Xuất:"));
        txtMaPX = new JTextField();
        pnlContent.add(txtMaPX, "growx,h 35!");

        pnlContent.add(new JLabel("Tình Trạng:"));
        String[] tinhTrang = {"Tồn kho", "Đã bán", "Lỗi"};
        cboTinhTrang = new JComboBox<>(tinhTrang);
        cboTinhTrang.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        cboTinhTrang.setBackground(Color.WHITE);
        pnlContent.add(cboTinhTrang, "growx, h 35!");
    }

    private void fillData(ChiTietSP ct) {
        txtMaPB.setText(ct.getMaPhienBan());
        txtIMEI.setText(ct.getMaImei());
        txtMaPN.setText(ct.getMaPhieuNhap() == null ? "N/A" : ct.getMaPhieuNhap());
        txtMaPX.setText(ct.getMaPhieuXuat() == null ? "N/A" : ct.getMaPhieuXuat());
        cboTinhTrang.setSelectedItem(ct.getTinhTrang());
    }

    @Override
    protected void logicXacNhan() {
        if (txtIMEI.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Vui lòng nhập mã IMEI!");
            return;
        }
        ChiTietSP ct = new ChiTietSP();
        ct.setMaImei(txtIMEI.getText().trim());
        ct.setMaPhienBan(maPB);
        ct.setTinhTrang(cboTinhTrang.getSelectedItem().toString());
        ct.setMaPhieuNhap(null); 
        ct.setMaPhieuXuat(null);
        boolean ketQua;

        if (isUpdate) {
            if (ctBUS.update(ct)) {
                JOptionPane.showMessageDialog(this, "Cập nhật trạng thái thành công!");
                ketQua = true;
            } else {
                JOptionPane.showMessageDialog(this, "Cập nhật thất bại!");
                ketQua = false;
            }
        } else {
            if (ctBUS.add(ct)) {
                JOptionPane.showMessageDialog(this, "Thêm IMEI thành công!");
                ketQua = true;
            } else {
                JOptionPane.showMessageDialog(this, "Thêm thất bại (IMEI đã tồn tại)!");
                ketQua = false;
            }
        }

        if (ketQua) {
            isSuccess = true;
            dispose();
        }
    }
    
    public boolean isSuccess() {
        return isSuccess;
    }
}