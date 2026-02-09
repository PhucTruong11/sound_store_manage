package Backend.DAO;

import Backend.DatabaseHelper;
import Backend.DTO.ChiTietPhieuXuat;
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
                return String.format("PX%02d", num + 1);
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

    public boolean thanhToan(PhieuXuat px, ArrayList<ChiTietPhieuXuat> dsChiTiet) {
        Connection conn = null;
        try {
            conn = DatabaseHelper.getConnection();
            conn.setAutoCommit(false); // Bật chế độ giao dịch (Transaction)

            // 1. Chèn vào bảng PhieuXuat
            String sqlPX = "INSERT INTO PhieuXuat (MaPhieuXuat, NgayXuat, MaNV, MaKH, MaKM, TongTien) VALUES (?, ?, ?, ?, ?, ?)";
            PreparedStatement pstPX = conn.prepareStatement(sqlPX);
            pstPX.setString(1, px.getMaPhieuXuat());
            pstPX.setTimestamp(2, px.getNgayXuat());
            pstPX.setString(3, px.getMaNV());
            pstPX.setString(4, px.getMaKH());
            pstPX.setString(5, px.getMaKM());
            pstPX.setDouble(6, px.getTongTien());
            pstPX.executeUpdate();

            // 2. Chèn danh sách món hàng vào ChiTietPhieuXuat
            String sqlCT = "INSERT INTO ChiTietPhieuXuat (MaPhieuXuat, MaPhienBan, SoLuong, DonGia) VALUES (?, ?, ?, ?)";
            PreparedStatement pstCT = conn.prepareStatement(sqlCT);
            for (ChiTietPhieuXuat ct : dsChiTiet) {
                pstCT.setString(1, ct.getMaPhieuXuat());
                pstCT.setString(2, ct.getMaPhienBan());
                pstCT.setInt(3, ct.getSoLuong());
                pstCT.setDouble(4, ct.getDonGia());
                pstCT.addBatch();
            }
            pstCT.executeBatch();

            conn.commit(); // Hoàn tất giao dịch
            return true;
        } catch (Exception e) {
            if (conn != null)
                try {
                    conn.rollback();
                } catch (SQLException ex) {
                } // Lỗi thì hoàn tác
            e.printStackTrace();
            return false;
        }
    }
}