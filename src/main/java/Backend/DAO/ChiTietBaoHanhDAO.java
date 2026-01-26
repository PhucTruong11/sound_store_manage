package Backend.DAO;

import Backend.DatabaseHelper;
import Backend.DTO.BaoHanh;
import Backend.DTO.ChiTietBaoHanh;
import java.util.ArrayList;
import java.sql.*;

public class ChiTietBaoHanhDAO {
    public ArrayList<ChiTietBaoHanh> getAllChiTietBaoHanh(String maBH) {
        ArrayList<ChiTietBaoHanh> list = new ArrayList<>();
        String sql = "SELECT * FROM ChiTietBaoHanh WHERE MaBH = ?";
        // SỬ DỤNG WHERE ĐỂ KHÔNG KHI BẤM VÀO NÓ KHÔNG HIỆN HẾT THÔNG TIN CỦA MẤY CÁI
        // BẢO HÀNH KHÁC
        try (Connection conn = DatabaseHelper.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, maBH);// cái này dùng để truyền mã bải hành để xem đúng chi tiết cần xem

            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    ChiTietBaoHanh ctbh = new ChiTietBaoHanh();

                    ctbh.setMaCTBH(rs.getString("MaCTBH"));
                    ctbh.setMaBH(rs.getString("MaBH"));
                    ctbh.setNoiDung(rs.getString("NoiDung"));
                    ctbh.setTinhTrang(rs.getString("TinhTrang"));

                    list.add(ctbh);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }
}
