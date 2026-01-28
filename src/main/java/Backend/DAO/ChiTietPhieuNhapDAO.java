package Backend.DAO;

import Backend.DatabaseHelper;
import Backend.DTO.ChiTietPhieuNhap;
import java.sql.*;
import java.util.ArrayList;

public class ChiTietPhieuNhapDAO implements ChiTietInterface<ChiTietPhieuNhap> {
    @Override
    public int insert(ArrayList<ChiTietPhieuNhap> list) {
        int count = 0;
        String sql = "INSERT INTO ChiTietPhieuNhap VALUES (?, ?, ?, ?, ?)";
        try (Connection conn = DatabaseHelper.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            for (ChiTietPhieuNhap ct : list) {
                stmt.setString(1, ct.getMaPhieuNhap());
                stmt.setString(2, ct.getMaPhienBan());
                stmt.setInt(3, ct.getSoLuong());
                stmt.setDouble(4, ct.getDonGia());
                stmt.setDouble(5, ct.getThanhTien());
                count += stmt.executeUpdate();
            }
        } catch (Exception e) { e.printStackTrace(); }
        return count;
    }    
    
    @Override
    public ArrayList<ChiTietPhieuNhap> selectAll(String maPN) {
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
                    rs.getString(1),
                    rs.getString(2),
                    rs.getInt(3),
                    rs.getDouble(4),
                    rs.getDouble(5));
                ct.setTenSP(rs.getString("TenSP"));
                list.add(ct);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    @Override
    public int delete(String id) { return 0; }

    @Override
    public int update(ArrayList<ChiTietPhieuNhap> t, String pk) { return 0; }
}
