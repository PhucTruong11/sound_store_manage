package Backend.DAO;

import Backend.DatabaseHelper;
import Backend.DTO.BaoHanh;
import java.util.ArrayList;
import java.sql.*;

public class BaoHanhDAO {
    public ArrayList<BaoHanh> getAllBaoHanh() {
        ArrayList<BaoHanh> list = new ArrayList<>();
        String sql = "SELECT * FROM BaoHanh";

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

                list.add(bh);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }
}
