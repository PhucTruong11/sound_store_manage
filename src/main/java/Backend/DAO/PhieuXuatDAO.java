package Backend.DAO;

import Backend.DatabaseHelper;
import Backend.DTO.PhieuXuat;
import java.util.ArrayList;
import java.sql.*;

public class PhieuXuatDAO {
    public ArrayList<PhieuXuat> selectAll() {
        ArrayList<PhieuXuat> list = new ArrayList<>();
        String sql = "SELECT * FROM PhieuXuat";

        try (Connection conn = DatabaseHelper.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql);
                ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                PhieuXuat px = new PhieuXuat();

                px.setMaPhieuXuat(rs.getString("MaPhieuXuat"));
                px.setNgayXuat(rs.getTimestamp("NgayXuat"));
                px.setMaNV(rs.getString("MaNV"));
                px.setMaKH(rs.getString("MaKH"));
                px.setMaKM(rs.getString("MaKM"));
                px.setTongTien(rs.getDouble("TongTien"));
                px.setTrangThai(rs.getInt("TrangThai"));

                list.add(px);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    public ArrayList<PhieuXuat> search(String keyword) {
        ArrayList<PhieuXuat> list = new ArrayList<>();
        String sql = "SELECT * FROM PhieuXuat WHERE MaPhieuXuat LIKE ? OR MaKH LIKE ?";

        try (Connection conn = DatabaseHelper.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, "%" + keyword + "%");
            stmt.setString(2, "%" + keyword + "%");

            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    PhieuXuat px = new PhieuXuat();
                    px.setMaPhieuXuat(rs.getString("MaPhieuXuat"));
                    px.setNgayXuat(rs.getTimestamp("NgayXuat"));
                    px.setMaNV(rs.getString("MaNV"));
                    px.setMaKH(rs.getString("MaKH"));
                    px.setTongTien(rs.getDouble("TongTien"));
                    list.add(px);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }
}