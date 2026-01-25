package Backend.DAO;

import Backend.DatabaseHelper;
import Backend.DTO.ChiTietPhieuNhap;
import java.sql.*;
import java.util.ArrayList;

public class ChiTietPhieuNhapDAO {
    public ArrayList<ChiTietPhieuNhap> getByMaPhieu(String maPN) {
        ArrayList<ChiTietPhieuNhap> list = new ArrayList<>();
        String sql = """
                    SELECT ct.*, sp.TenSP 
                    FROM ChiTietPhieuNhap AS ct
                    JOIN PhienBanSP AS pb ON ct.MaPhienBan = pb.MaPhienBan
                    JOIN SanPham AS sp ON pb.MaSP = sp.MaSP
                    WHERE ct.MaPhieuNhap = ?
                    """;                   
        
        try (Connection conn = DatabaseHelper.getConnection();
            PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, maPN);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                ChiTietPhieuNhap ct = new ChiTietPhieuNhap(
                    rs.getString("MaPhieuNhap"),
                    rs.getString("MaPhienBan"),
                    rs.getInt("SoLuong"),
                    rs.getDouble("DonGia"),
                    rs.getDouble("ThanhTien")
                );
                // ct.setTenSP(rs.getString("TenSP"));
                list.add(ct);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }
}
