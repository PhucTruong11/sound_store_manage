package Backend.DAO;

import Backend.DatabaseHelper;
import Backend.DTO.SanPham;
import java.sql.*;
import java.util.ArrayList;

public class SanPhamDAO {
    public ArrayList<SanPham> getAll() {
        ArrayList<SanPham> list = new ArrayList<>();
        String sql = "SELECT * FROM SanPham WHERE TrangThai = TRUE";
        try (Connection conn = DatabaseHelper.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                list.add(new SanPham(
                    rs.getString("MaSP"),
                    rs.getString("TenSP"),
                    rs.getString("MaLoai"),
                    rs.getString("MaHang"),
                    rs.getString("HinhAnh"),
                    rs.getString("MoTa"),
                    rs.getInt("ThoiGianBaoHanh"),
                    rs.getBoolean("TrangThai")
                ));
            }
        } catch (Exception e) { e.printStackTrace(); }
        return list;
    }
}
