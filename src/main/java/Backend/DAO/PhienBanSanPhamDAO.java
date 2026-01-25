package Backend.DAO;

import Backend.DatabaseHelper;
import Backend.DTO.PhienBanSanPham;
import java.sql.*;
import java.util.ArrayList;

public class PhienBanSanPhamDAO {
    public ArrayList<PhienBanSanPham> getAllPhienBan() {
        ArrayList<PhienBanSanPham> list = new ArrayList<>();
        String sql = "SELECT * FROM PhienBanSP";
        
        try (Connection conn = DatabaseHelper.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            
            while (rs.next()) {
                PhienBanSanPham pb = new PhienBanSanPham(
                    rs.getString("MaPhienBan"),
                    rs.getString("MaSP"),
                    rs.getString("MauSac"),
                    rs.getString("CongSuat"),
                    rs.getString("Pin"),
                    rs.getString("KetNoi"),
                    rs.getDouble("GiaNhap"),
                    rs.getDouble("GiaBan"),
                    rs.getInt("SoLuongTon")
                );
                list.add(pb);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    public PhienBanSanPham getPhienBanByMa(String maPhienBan) {
        String sql = "SELECT * FROM PhienBanSP WHERE MaPhienBan = ?";
        
        try (Connection conn = DatabaseHelper.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setString(1, maPhienBan);
            ResultSet rs = stmt.executeQuery();
            
            if (rs.next()) {
                return new PhienBanSanPham(
                    rs.getString("MaPhienBan"),
                    rs.getString("MaSP"),
                    rs.getString("MauSac"),
                    rs.getString("CongSuat"),
                    rs.getString("Pin"),
                    rs.getString("KetNoi"),
                    rs.getDouble("GiaNhap"),
                    rs.getDouble("GiaBan"),
                    rs.getInt("SoLuongTon")
                );
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
