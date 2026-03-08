package Frontend.GUI.PhanQuyen;

import Backend.DatabaseHelper;
import Frontend.Compoent.CustomButton;
import Frontend.Compoent.Theme;
import net.miginfocom.swing.MigLayout;

import javax.swing.*;
import java.awt.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.HashMap;

public class TaiKhoanAddDialog extends JDialog {
      private JComboBox<String> cboNhanVien;
      private JTextField txtUsername;
      private JPasswordField txtPassword;
      private JComboBox<String> cboNhomQuyen;

      // HashMap dùng để ghi nhớ: Khi chọn tên "Trương Phúc", hệ thống tự biết mã là
      // "NV001"
      private HashMap<String, String> mapNhanVien = new HashMap<>();

      public TaiKhoanAddDialog(JFrame parent) {
            super(parent, "Cấp tài khoản cho nhân viên", true);
            setSize(450, 350);
            setLocationRelativeTo(parent);
            setLayout(new MigLayout("wrap 2, fillx, insets 20", "[120!][grow]"));
            getContentPane().setBackground(Color.WHITE);

            initUI();
            loadNhanVienChuaCoTaiKhoan();
      }

      private void initUI() {
            add(new JLabel("Chọn nhân viên:"));
            cboNhanVien = new JComboBox<>();
            add(cboNhanVien, "growx, h 30!");

            add(new JLabel("Tên đăng nhập:"));
            txtUsername = new JTextField();
            add(txtUsername, "growx, h 30!");

            add(new JLabel("Mật khẩu:"));
            txtPassword = new JPasswordField();
            add(txtPassword, "growx, h 30!");

            add(new JLabel("Cấp nhóm quyền:"));
            // Cắt chuỗi để lấy mã NQ khi lưu vào DB
            cboNhomQuyen = new JComboBox<>(new String[] {
                        "NQ01 - Quản lý cửa hàng",
                        "NQ02 - Nhân viên bán hàng",
                        "NQ03 - Nhân viên kho"
            });
            add(cboNhomQuyen, "growx, h 30!");

            add(new JSeparator(), "span 2, growx, gaptop 15, gapbottom 15");

            CustomButton btnHuy = new CustomButton("Hủy bỏ", new Color(217, 83, 79));
            CustomButton btnLuu = new CustomButton("TẠO TÀI KHOẢN", Theme.PRIMARY_COLOR);

            btnHuy.addActionListener(e -> dispose());
            btnLuu.addActionListener(e -> saveTaiKhoan());

            add(btnHuy, "split 2, span 2, center, w 120!, h 40!");
            add(btnLuu, "w 150!, h 40!");
      }

      // 🌟 PHÉP THUẬT NẰM Ở ĐÂY: Chỉ load những người chưa có tài khoản
      private void loadNhanVienChuaCoTaiKhoan() {
            String sql = "SELECT c.ID, c.HoTen FROM ConNguoi c JOIN NhanVien n ON c.ID = n.ID " +
                        "WHERE n.TrangThai = TRUE AND c.ID NOT IN (SELECT MaNV FROM TaiKhoan)";
            try (Connection conn = DatabaseHelper.getConnection();
                        PreparedStatement pst = conn.prepareStatement(sql);
                        ResultSet rs = pst.executeQuery()) {

                  while (rs.next()) {
                        String maNV = rs.getString("ID");
                        String tenNV = rs.getString("HoTen");
                        String display = maNV + " - " + tenNV; // Hiện lên UI: "NV004 - Lê Văn Test"

                        cboNhanVien.addItem(display);
                        mapNhanVien.put(display, maNV); // Lưu ngầm mã NV để lát insert
                  }
            } catch (Exception e) {
                  e.printStackTrace();
            }
      }

      private void saveTaiKhoan() {
            if (cboNhanVien.getItemCount() == 0) {
                  JOptionPane.showMessageDialog(this, "Tất cả nhân viên đều đã có tài khoản!", "Thông báo",
                              JOptionPane.INFORMATION_MESSAGE);
                  return;
            }

            String user = txtUsername.getText().trim();
            String pass = new String(txtPassword.getPassword());

            if (user.isEmpty() || pass.isEmpty()) {
                  JOptionPane.showMessageDialog(this, "Vui lòng nhập đủ Tài khoản và Mật khẩu!", "Lỗi",
                              JOptionPane.ERROR_MESSAGE);
                  return;
            }

            // Bóc tách dữ liệu để lưu vào DB
            String displaySelected = cboNhanVien.getSelectedItem().toString();
            String maNV = mapNhanVien.get(displaySelected);
            String maNQ = cboNhomQuyen.getSelectedItem().toString().split(" - ")[0];

            String sql = "INSERT INTO TaiKhoan (TenDangNhap, MatKhau, MaNV, MaNhomQuyen, TrangThai) VALUES (?, ?, ?, ?, 1)";
            try (Connection conn = DatabaseHelper.getConnection();
                        PreparedStatement pst = conn.prepareStatement(sql)) {

                  pst.setString(1, user);
                  pst.setString(2, pass);
                  pst.setString(3, maNV);
                  pst.setString(4, maNQ);

                  pst.executeUpdate();
                  JOptionPane.showMessageDialog(this, "Cấp tài khoản thành công!");
                  dispose(); // Tắt form

            } catch (Exception e) {
                  e.printStackTrace();
                  JOptionPane.showMessageDialog(this, "Lỗi! Tên đăng nhập này có thể đã có người sử dụng.", "Lỗi DB",
                              JOptionPane.ERROR_MESSAGE);
            }
      }
}