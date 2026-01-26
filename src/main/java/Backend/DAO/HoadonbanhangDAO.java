package Backend.DAO;

import Backend.DatabaseHelper;
import Backend.DTO.BaoHanh;
import Backend.DTO.HoaDonBanHang;
import java.util.ArrayList;
import java.sql.*;

public class HoadonbanhangDAO {
    public ArrayList<HoaDonBanHang> getAllHoaDonBanHang() {
        ArrayList<HoaDonBanHang> list = new ArrayList<>();
        String sql = "SELECT * FROM HoaDonBanHang";

        try (Connection conn = DatabaseHelper.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql);
                ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                HoaDonBanHang hdbh = new HoaDonBanHang();

                hdbh.setMaHD(rs.getString(""));

                Date nl = rs.getDate("NgayLap");
                if (nl != null) {
                    hdbh.setNgayLap(nl.toLocalDate());
                }
                hdbh.setMaHD(rs.getString(""));
                hdbh.setMaHD(rs.getString(""));
                hdbh.setMaHD(rs.getString(""));
                hdbh.setMaHD(rs.getString(""));

                list.add(hdbh);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }
}
