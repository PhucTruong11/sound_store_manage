package Backend.DAO;

import Backend.DatabaseHelper;
import Backend.DTO.PhieuNhap;
import java.sql.*;
import java.util.ArrayList;

public class PhieuNhapDAO implements DAOInterface<PhieuNhap> {

    @Override
    public int insert(PhieuNhap pn) {
        String sql ="INSERT INTO PhieuNhap (MaPhieuNhap, MaNV, MaNCC, TongTien) VALUES (?, ?, ?, ?)";
        try (Connection conn = DatabaseHelper.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, pn.getmaPhieuNhap());
            stmt.setString(2, pn.getmaNV());
            stmt.setString(3, pn.getmaNCC());
            stmt.setDouble(4, pn.getTongTien());
            return stmt.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace(); return 0;
        }
    }

    @Override
    public ArrayList<PhieuNhap> selectAll() {
        ArrayList<PhieuNhap> list = new ArrayList<>();
        String sql = "SELECT * FROM PhieuNhap WHERE TrangThai = TRUE ORDER BY NgayNhap DESC";
        try (Connection conn = DatabaseHelper.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                list.add(new PhieuNhap(
                    rs.getString(1),
                    rs.getTimestamp(2),
                    rs.getString(3),
                    rs.getString(4),
                    rs.getDouble(5),
                    rs.getBoolean(6)));
            }
        } catch (Exception e) { e.printStackTrace(); }
        return list;
    }

    @Override
    public PhieuNhap selectById(String id) { 
        String sql = "SELECT * FROM PhieuNhap WHERE MaPhieuNhap = ?";
        try (Connection conn = DatabaseHelper.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, id);
            ResultSet rs = stmt.executeQuery();
            
            if (rs.next()) {
                return new PhieuNhap(
                    rs.getString("MaPhieuNhap"),
                    rs.getTimestamp("NgayNhap"),
                    rs.getString("MaNV"),
                    rs.getString("MaNCC"),
                    rs.getDouble("TongTien"),
                    rs.getBoolean("TrangThai")
                );
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null; 
    }

    @Override
    public int update(PhieuNhap pn) { return 0; }

    @Override
    public int delete(String id) { throw new UnsupportedOperationException("Không được xóa phiếu!"); }

    public String generateMaPhieuNhap() {
        String sql = "SELECT MaPhieuNhap FROM PhieuNhap ORDER BY CAST(SUBSTRING(MaPhieuNhap, 3) AS UNSIGNED) DESC LIMIT 1";
        try (Connection conn = DatabaseHelper.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            if (rs.next()) {
                String lastMa = rs.getString("MaPhieuNhap");
                int num = Integer.parseInt(lastMa.substring(2)); 
                return String.format("PN%03d", num + 1);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "PN000";
    }
}
