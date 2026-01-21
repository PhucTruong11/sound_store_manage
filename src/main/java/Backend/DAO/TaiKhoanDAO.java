package Backend.DAO;

import Backend.DTO.TaiKhoan;
import Backend.DatabaseHelper;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class TaiKhoanDAO {

    public TaiKhoan login(String username, String passwordHash) {

        TaiKhoan tk = null;

        String sql = """
            SELECT u.id, u.username, u.password_hash, u.role, u.status, u.connguoi_id
            FROM Users u
            WHERE u.username = ? AND u.password_hash = ? AND u.status = 1
        """;

        try (
                Connection con = DatabaseHelper.getConnection();
                PreparedStatement ps = con.prepareStatement(sql)
        ) {
            ps.setString(1, username);
            ps.setString(2, passwordHash);

            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                tk = new TaiKhoan();
                tk.setId(rs.getInt("id"));
                tk.setUsername(rs.getString("username"));
                tk.setPasswordHash(rs.getString("password_hash"));
                tk.setRole(rs.getString("role"));
                tk.setStatus(rs.getInt("status"));
                tk.setConNguoiId(rs.getString("connguoi_id"));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return tk;
    }
}
