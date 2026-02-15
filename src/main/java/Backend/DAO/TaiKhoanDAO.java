package Backend.DAO;

import Backend.DTO.TaiKhoan;
import Backend.DatabaseHelper;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class TaiKhoanDAO {

    public TaiKhoan login(String username, String password) {
        TaiKhoan tk = null;

        // Câu lệnh SQL khớp chính xác với bảng TaiKhoan trong file SQL của bạn
        String sql = """
            SELECT TenDangNhap, MatKhau, MaNV, MaNhomQuyen, TrangThai
            FROM TaiKhoan
            WHERE TenDangNhap = ? AND MatKhau = ? AND TrangThai = 1
        """;

        try (
            Connection con = DatabaseHelper.getConnection();
            PreparedStatement ps = con.prepareStatement(sql)
        ) {
            ps.setString(1, username);
            ps.setString(2, password); // Nếu sau này dùng BCrypt, logic này sẽ thay đổi một chút

            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                tk = new TaiKhoan();
                // Gán dữ liệu dựa trên tên cột trong Database của bạn
                tk.setUsername(rs.getString("TenDangNhap"));
                tk.setPassword(rs.getString("MatKhau"));
                tk.setMaNV(rs.getString("MaNV"));
                tk.setMaNhomQuyen(rs.getString("MaNhomQuyen"));
                // Chuyển đổi Boolean từ DB sang int/boolean tùy vào DTO của bạn
                tk.setStatus(rs.getBoolean("TrangThai") ? 1 : 0);
            }

        } catch (Exception e) {
            System.err.println("Lỗi đăng nhập: " + e.getMessage());
            e.printStackTrace();
        }

        return tk;
    }
}
