package Backend.DAO;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import Backend.DTO.DoiTra;
import Backend.DatabaseHelper;

public class DoiTraDAO {

    public ArrayList<DoiTra> selectAll() {
        ArrayList<DoiTra> list = new ArrayList<>();
        String sql = """
            SELECT dt.*, px.NgayXuat, cn.HoTen 
            FROM DoiTra dt
            JOIN PhieuXuat px ON dt.MaPhieuXuat = px.MaPhieuXuat
            JOIN KhachHang kh ON dt.MaKH = kh.ID
            JOIN ConNguoi cn ON kh.ID = cn.ID
            WHERE dt.TrangThai = 1
        """;

        try (Connection conn = DatabaseHelper.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                DoiTra dt = new DoiTra(
                    rs.getString("MaDoiTra"),
                    rs.getString("MaKH"),
                    rs.getString("MaPhieuXuat"),
                    rs.getString("MaImei"),
                    rs.getDate("NgayDoiTra").toLocalDate(),
                    rs.getString("LyDo"),
                    rs.getBoolean("TrangThai")
                );
                dt.setTenKH(rs.getString("HoTen"));
                dt.setNgayMua(rs.getDate("NgayXuat").toLocalDate());
                list.add(dt);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    public int insert(DoiTra dt) {
        String sql = "INSERT INTO DoiTra (MaDoiTra, MaKH, MaPhieuXuat, MaImei, NgayDoiTra, LyDo, TrangThai) VALUES (?, ?, ?, ?, ?, ?, 1)";
        try (Connection conn = DatabaseHelper.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            
            ps.setString(1, dt.getMaDoiTra());
            ps.setString(2, dt.getMaKH());
            ps.setString(3, dt.getMaPhieuXuat());
            ps.setString(4, dt.getMaImei());
            ps.setDate(5, Date.valueOf(dt.getNgayDoiTra()));
            ps.setString(6, dt.getLyDo());
            
            return ps.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }

    public int update(DoiTra dt) {
        String sql = "UPDATE DoiTra SET NgayDoiTra = ?, LyDo = ? WHERE MaDoiTra = ? AND TrangThai = 1";
        try (Connection conn = DatabaseHelper.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            
            ps.setDate(1, Date.valueOf(dt.getNgayDoiTra()));
            ps.setString(2, dt.getLyDo());
            ps.setString(3, dt.getMaDoiTra());
            
            return ps.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }

    public int softDelete(String maDT) {
        String sql = "UPDATE DoiTra SET TrangThai = 0 WHERE MaDoiTra = ?";
        try (Connection conn = DatabaseHelper.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            
            ps.setString(1, maDT);
            return ps.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }

    public boolean updateImeiReturn(String maImei) {
        String sql = """
            UPDATE ChiTietSP 
            SET TinhTrang = 'Trong kho', 
                MaPhieuXuat = NULL 
            WHERE MaImei = ?
        """;
        try (Connection conn = DatabaseHelper.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            
            ps.setString(1, maImei);
            return ps.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public LocalDate getNgayMuaByMaPX(String maPX) {
        String sql = "SELECT NgayXuat FROM PhieuXuat WHERE MaPhieuXuat = ?";
        try (Connection conn = DatabaseHelper.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            
            ps.setString(1, maPX);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return rs.getDate("NgayXuat").toLocalDate();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}