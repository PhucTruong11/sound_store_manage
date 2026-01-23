package Frontend.GUI.SanPham;

import Frontend.Compoent.BaseThaoTacDialog;
import javax.swing.*;

public class SuaSanPhamDialog extends BaseThaoTacDialog {
        private JTextField txtMa, txtTen, txtGia, txtSoLuong;
        private boolean isSuccess = false;

        public SuaSanPhamDialog(JFrame parent, String ma, String ten, String gia, String soLuong) {
                super("Sửa Sản Phẩm", 450, 400);
                txtMa.setText(ma);
                txtTen.setText(ten);

                if (gia != null) {
                        String giaClean = gia.replace(",", "").replace(".", "").replace("VNĐ", "").trim();
                        txtGia.setText(giaClean);
                }
                txtSoLuong.setText(soLuong);

                // Không cho sửa mã và số lượng
                txtMa.setEditable(false);
                txtMa.setFocusable(false);
                txtSoLuong.setEditable(false);
                txtSoLuong.setFocusable(false);
        }

        @Override
        protected void initForm() {

                pnlContent.add(new JLabel("Mã sản phẩm:"));
                txtMa = new JTextField();
                pnlContent.add(txtMa, "growx, h 35!");

                pnlContent.add(new JLabel("Tên sản phẩm:"));
                txtTen = new JTextField();
                pnlContent.add(txtTen, "growx, h 35!");

                pnlContent.add(new JLabel("Đơn giá (VNĐ):"));
                txtGia = new JTextField();
                pnlContent.add(txtGia, "growx, h 35!");

                pnlContent.add(new JLabel("Số lượng:"));
                txtSoLuong = new JTextField();
                pnlContent.add(txtSoLuong, "growx, h 35!");
        }

        @Override
        protected void logicXacNhan() {
                this.isSuccess = true;
                this.dispose();
        }

        public boolean isSuccess() {
                return isSuccess;
        }
}