package Backend.DAO;

import Backend.DatabaseHelper;
import Backend.DTO.ChiTietPhieuXuat;
import java.sql.*;
import java.util.ArrayList;

public class ChiTietPhieuXuatDAO {
    public ArrayList<ChiTietPhieuXuat> getAllChiTietPhieuXuat(String maPX) {
        ArrayList<ChiTietPhieuXuat> list = new ArrayList<>();
        String sql = "SELECT ct.*, sp.TenSP, sp.HinhAnh FROM ChiTietPhieuXuat ct " +
                "JOIN PhienBanSP pb ON ct.MaPhienBan = pb.MaPhienBan " +
                "JOIN SanPham sp ON pb.MaSP = sp.MaSP " +
                "WHERE ct.MaPhieuXuat = ?";

        try (Connection conn = DatabaseHelper.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, maPX);

            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    ChiTietPhieuXuat ctpx = new ChiTietPhieuXuat();
                    ctpx.setMaPhieuXuat(rs.getString("MaPhieuXuat"));
                    ctpx.setMaPhienBan(rs.getString("MaPhienBan"));
                    ctpx.setSoLuong(rs.getInt("SoLuong"));
                    ctpx.setDonGia(rs.getDouble("DonGia"));
                    ctpx.setThanhTien(rs.getDouble("ThanhTien"));

                    ctpx.setTenSP(rs.getString("TenSP"));
                    ctpx.setHinhAnh(rs.getString("HinhAnh"));

                    list.add(ctpx);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }
}