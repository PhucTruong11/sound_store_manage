package Backend.DAO;

import Backend.DatabaseHelper;
import Backend.DTO.BaoHanh;
import java.util.ArrayList;
import java.sql.*;

public class BaoHanhDAO implements DAOInterface<BaoHanh> {

    @Override
    public int insert(BaoHanh bh) {
        String sql = "INSERT INTO BaoHanh (MaBH, MaImei, MaPhieuXuat, NgayBatDau, NgayKetThuc) VALUES (?, ?, ?, ?, ?)";
        try (Connection conn = DatabaseHelper.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, bh.getMaBH());
            stmt.setString(2, bh.getMaImei());
            stmt.setString(3, bh.getMaPhieuXuat());
            stmt.setDate(4, Date.valueOf(bh.getNgayBatDau()));
            stmt.setDate(5, Date.valueOf(bh.getNgayKetThuc()));

            System.out.println("Inserting: " + bh.getMaBH() + ", " + bh.getMaImei());
            return stmt.executeUpdate();
        } catch (Exception e) {
            System.err.println("Lỗi insert BaoHanh:");
            e.printStackTrace();
            return 0;
        }
    }

    @Override
    public ArrayList<BaoHanh> selectAll() {
        ArrayList<BaoHanh> list = new ArrayList<>();
        String sql = "SELECT bh.*, " +
                "(SELECT TinhTrang FROM ChiTietBaoHanh WHERE MaBH = bh.MaBH ORDER BY MaCTBH DESC LIMIT 1) AS TinhTrang "
                +
                "FROM BaoHanh bh ORDER BY bh.NgayBatDau DESC";

        try (Connection conn = DatabaseHelper.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql);
                ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                BaoHanh bh = new BaoHanh();
                bh.setMaBH(rs.getString("MaBH"));
                bh.setMaImei(rs.getString("MaImei"));
                bh.setMaPhieuXuat(rs.getString("MaPhieuXuat"));
                bh.setNgayBatDau(rs.getDate("NgayBatDau").toLocalDate());
                bh.setNgayKetThuc(rs.getDate("NgayKetThuc").toLocalDate());
                bh.setTinhTrang(rs.getString("TinhTrang"));

                list.add(bh);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    @Override
    public int update(BaoHanh bh) {
        String sql = "UPDATE BaoHanh SET MaImei=?, MaPhieuXuat=?, NgayBatDau=?, NgayKetThuc=? WHERE MaBH=?";
        try (Connection conn = DatabaseHelper.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, bh.getMaImei());
            stmt.setString(2, bh.getMaPhieuXuat());
            stmt.setDate(3, Date.valueOf(bh.getNgayBatDau()));
            stmt.setDate(4, Date.valueOf(bh.getNgayKetThuc()));
            stmt.setString(5, bh.getMaBH());
            return stmt.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }

    @Override
    public int delete(String id) {
        String sql = "DELETE FROM BaoHanh WHERE MaBH = ?";
        try (Connection conn = DatabaseHelper.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, id);
            return stmt.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }

    @Override
    public BaoHanh selectById(String id) {
        String sql = "SELECT * FROM BaoHanh WHERE MaBH = ?";
        try (Connection conn = DatabaseHelper.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, id);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                BaoHanh bh = new BaoHanh();
                bh.setMaBH(rs.getString("MaBH"));
                bh.setMaImei(rs.getString("MaImei"));
                bh.setMaPhieuXuat(rs.getString("MaPhieuXuat"));
                bh.setNgayBatDau(rs.getDate("NgayBatDau").toLocalDate());
                bh.setNgayKetThuc(rs.getDate("NgayKetThuc").toLocalDate());
                return bh;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public ArrayList<BaoHanh> selectAllWithDetails() {
        ArrayList<BaoHanh> list = new ArrayList<>();
        String sql = "SELECT bh.*, sp.TenSP, " +
                "(SELECT TinhTrang FROM ChiTietBaoHanh WHERE MaBH = bh.MaBH ORDER BY MaCTBH DESC LIMIT 1) AS TinhTrang "
                +
                "FROM BaoHanh bh " +
                "LEFT JOIN ChiTietSP ctsp ON bh.MaImei = ctsp.MaImei " +
                "LEFT JOIN PhienBanSP pb ON ctsp.MaPhienBan = pb.MaPhienBan " +
                "LEFT JOIN SanPham sp ON pb.MaSP = sp.MaSP " +
                "ORDER BY bh.NgayBatDau DESC";

        try (Connection conn = DatabaseHelper.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql);
                ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                BaoHanh bh = new BaoHanh();
                bh.setMaBH(rs.getString("MaBH"));
                bh.setMaImei(rs.getString("MaImei"));
                bh.setMaPhieuXuat(rs.getString("MaPhieuXuat"));

                Date bd = rs.getDate("NgayBatDau");
                if (bd != null)
                    bh.setNgayBatDau(bd.toLocalDate());

                Date kt = rs.getDate("NgayKetThuc");
                if (kt != null)
                    bh.setNgayKetThuc(kt.toLocalDate());

                bh.setTenSP(rs.getString("TenSP"));
                bh.setTinhTrang(rs.getString("TinhTrang"));

                list.add(bh);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    public boolean checkImeiExists(String maImei) {
        String sql = "SELECT COUNT(*) FROM ChiTietSP WHERE MaImei = ?";
        try (Connection conn = DatabaseHelper.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, maImei);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return rs.getInt(1) > 0;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }
}