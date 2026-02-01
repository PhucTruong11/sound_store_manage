package Backend.DAO;

import Backend.DatabaseHelper;
import Backend.DTO.PhienBanSanPham;
import java.sql.*;
import java.util.ArrayList;

public class PhienBanSanPhamDAO implements DAOInterface<PhienBanSanPham> {
    @Override
    public ArrayList<PhienBanSanPham> selectAll() {
        ArrayList<PhienBanSanPham> list = new ArrayList<>();
        // JOIN để lấy TenSP từ bảng SanPham dựa trên MaSP
        String sql = "SELECT pb.*, sp.TenSP FROM PhienBanSP pb " +
                     "JOIN SanPham sp ON pb.MaSP = sp.MaSP";
        try (Connection conn = DatabaseHelper.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            
            while (rs.next()) {
                PhienBanSanPham pbsp = new PhienBanSanPham(
                    rs.getString("MaPhienBan"),
                    rs.getString("MaSP"),
                    rs.getString("MauSac"),
                    rs.getString("CongSuat"),
                    rs.getString("Pin"),
                    rs.getString("KetNoi"),
                    rs.getDouble("GiaNhap"),
                    rs.getDouble("GiaBan"),
                    rs.getInt("SoLuongTon"));

                    pbsp.setTenSP(rs.getString("TenSP"));
                    list.add(pbsp);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    @Override
    public PhienBanSanPham selectById(String maPhienBan) {
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

    @Override
    public int insert(PhienBanSanPham pbsp) {
        return 0;
    }

    @Override
    public int update(PhienBanSanPham pbsp) {
        return 0;
    }

    @Override
    public int delete(String id) {
        return 0;
    }

    // Chọn theo nhà cung cấp
    public ArrayList<PhienBanSanPham> selectByNCC(String maNCC) {
        ArrayList<PhienBanSanPham> list = new ArrayList<>();
        String sql =  "SELECT pb.*, sp.TenSP FROM PhienBanSP pb " +
                 "JOIN SanPham sp ON pb.MaSP = sp.MaSP " +
                 "JOIN NCC_SanPham ncc_sp ON sp.MaSP = ncc_sp.MaSP " +
                 "WHERE ncc_sp.MaNCC = ?";

        try (Connection conn = DatabaseHelper.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, maNCC);
            ResultSet rs = stmt.executeQuery();
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
                    rs.getInt("SoLuongTon"));
                    pb.setTenSP(rs.getString("TenSP"));
                    list.add(pb);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }
}
