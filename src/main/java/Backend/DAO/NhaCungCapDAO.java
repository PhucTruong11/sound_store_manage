package Backend.DAO;

import Backend.DatabaseHelper;
import Backend.DTO.NhaCungCap;
import java.sql.*;
import java.util.ArrayList;

public class NhaCungCapDAO {
    public ArrayList<NhaCungCap> getAllNhaCungCap() {
        ArrayList<NhaCungCap> list = new ArrayList<>();
        String sql = "SELECT * FROM NhaCungCap";

        try (Connection conn = DatabaseHelper.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql);
                ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                NhaCungCap ncc = new NhaCungCap(
                        rs.getString("MaNCC"),
                        rs.getString("TenNCC"),
                        rs.getString("DiaChi"),
                        rs.getString("SDT"));
                list.add(ncc);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }
}
