package Backend.DAO;

import Backend.DatabaseHelper;
import Backend.DTO.PhieuXuat;
import java.util.ArrayList;
import java.sql.*;

public class PhieuXuatDAO implements DAOInterface<PhieuXuat> {
    @Override
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
                px.setTrangThai(rs.getBoolean("TrangThai"));

                list.add(px);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    @Override
    public int insert(PhieuXuat px) {
        String sql = "INSERT INTO PhieuXuat (MaPhieuXuat, NgayXuat, MaNV, MaKH, MaKM, TongTien, TrangThai) VALUES (?, ?, ?, ?, ?, ?, ?)";
        try (Connection conn = DatabaseHelper.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, px.getMaPhieuXuat());
            stmt.setTimestamp(2, px.getNgayXuat());
            stmt.setString(3, px.getMaNV());
            stmt.setString(4, px.getMaKH());
            stmt.setString(5, px.getMaKM());
            stmt.setDouble(6, px.getTongTien());
            stmt.setBoolean(7, px.getTrangThai());
            return stmt.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }

    @Override
    public PhieuXuat selectById(String id) {
        String sql = "SELECT * FROM PhieuXuat WHERE MaPhieuXuat = ?";
        try (Connection conn = DatabaseHelper.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, id);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                return new PhieuXuat(
                        rs.getString("MaPhieuXuat"),
                        rs.getTimestamp("NgayXuat"),
                        rs.getString("MaNV"),
                        rs.getString("MaKH"),
                        rs.getString("MaKM"),
                        rs.getDouble("TongTien"),
                        rs.getBoolean("TrangThai"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public int update(PhieuXuat px) {
        return 0;
    }

    @Override
    public int delete(String id) {
        throw new UnsupportedOperationException("Không được xóa phiếu!");
    }

    public String generateMaPhieuXuat() {
        String sql = "SELECT MaPhieuXuat FROM PhieuXuat ORDER BY CAST(SUBSTRING(MaPhieuXuat, 3) AS UNSIGNED) DESC LIMIT 1";
        try (Connection conn = DatabaseHelper.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql);
                ResultSet rs = stmt.executeQuery()) {
            if (rs.next()) {
                String lastMa = rs.getString("MaPhieuXuat");
                int num = Integer.parseInt(lastMa.substring(2));
                return String.format("PX%03d", num + 1);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "PX000";
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
                    px.setMaKM(rs.getString("MaKM"));
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