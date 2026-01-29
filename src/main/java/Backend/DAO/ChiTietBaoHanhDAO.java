package Backend.DAO;

import Backend.DatabaseHelper;
import Backend.DTO.ChiTietBaoHanh;
import java.util.ArrayList;
import java.sql.*;

public class ChiTietBaoHanhDAO {

    public int insert(ChiTietBaoHanh ctbh) {
        String sql = "INSERT INTO ChiTietBaoHanh (MaCTBH, MaBH, NoiDung, TinhTrang) VALUES (?, ?, ?, ?)";
        try (Connection conn = DatabaseHelper.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, ctbh.getMaCTBH());
            stmt.setString(2, ctbh.getMaBH());
            stmt.setString(3, ctbh.getNoiDung());
            stmt.setString(4, ctbh.getTinhTrang());

            System.out.println("Inserting ChiTietBaoHanh: " + ctbh.getMaCTBH());
            return stmt.executeUpdate();
        } catch (Exception e) {
            System.err.println("Lỗi insert ChiTietBaoHanh:");
            e.printStackTrace();
            return 0;
        }
    }

    public ArrayList<ChiTietBaoHanh> selectByMaBH(String maBH) {
        ArrayList<ChiTietBaoHanh> list = new ArrayList<>();
        String sql = "SELECT c.*, sp.TenSP " +
                "FROM ChiTietBaoHanh c " +
                "JOIN BaoHanh bh ON c.MaBH = bh.MaBH " +
                "LEFT JOIN ChiTietSP ctsp ON bh.MaImei = ctsp.MaImei " +
                "LEFT JOIN PhienBanSP pb ON ctsp.MaPhienBan = pb.MaPhienBan " +
                "LEFT JOIN SanPham sp ON pb.MaSP = sp.MaSP " +
                "WHERE c.MaBH = ? " +
                "ORDER BY c.MaCTBH";

        try (Connection conn = DatabaseHelper.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, maBH);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                ChiTietBaoHanh ctbh = new ChiTietBaoHanh();
                ctbh.setMaCTBH(rs.getString("MaCTBH"));
                ctbh.setMaBH(rs.getString("MaBH"));
                ctbh.setTenSP(rs.getString("TenSP")); // Lấy từ JOIN
                ctbh.setNoiDung(rs.getString("NoiDung"));
                ctbh.setTinhTrang(rs.getString("TinhTrang"));
                list.add(ctbh);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    public int update(ChiTietBaoHanh ctbh) {
        String sql = "UPDATE ChiTietBaoHanh SET NoiDung=?, TinhTrang=? WHERE MaCTBH=?";
        try (Connection conn = DatabaseHelper.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, ctbh.getNoiDung());
            stmt.setString(2, ctbh.getTinhTrang());
            stmt.setString(3, ctbh.getMaCTBH());
            return stmt.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }

    public int delete(String maCTBH) {
        String sql = "DELETE FROM ChiTietBaoHanh WHERE MaCTBH = ?";
        try (Connection conn = DatabaseHelper.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, maCTBH);
            return stmt.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }

    public int deleteAllByMaBH(String maBH) {
        String sql = "DELETE FROM ChiTietBaoHanh WHERE MaBH = ?";
        try (Connection conn = DatabaseHelper.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, maBH);
            return stmt.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }
}