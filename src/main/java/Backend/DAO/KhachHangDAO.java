package Backend.DAO;

import Backend.DatabaseHelper;
import Backend.DTO.KhachHang;
import java.sql.*;
import java.util.ArrayList;

public class KhachHangDAO {
    
    public ArrayList<KhachHang> selectAll() {
        ArrayList<KhachHang> list = new ArrayList<>();
        String sql = "SELECT c.ID, c.HoTen, c.SDT, c.DiaChi, k.TrangThai " +
                     "FROM ConNguoi c JOIN KhachHang k ON c.ID = k.ID";
        try (Connection conn = DatabaseHelper.getConnection();
             Statement st = conn.createStatement();
             ResultSet rs = st.executeQuery(sql)) {
            while (rs.next()) {
                list.add(new KhachHang(
                        rs.getString("ID"),
                        rs.getString("HoTen"),
                        rs.getString("SDT"),
                        rs.getString("DiaChi"),
                        rs.getBoolean("TrangThai")
                ));
            }
        } catch (Exception e) { e.printStackTrace(); }
        return list;
    }

    public int insert(KhachHang kh) {
        // Insert vào ConNguoi trước
        new ConNguoiDAO().insert(kh);
        String sql = "INSERT INTO KhachHang(ID, TrangThai) VALUES(?,?)";
        try (Connection conn = DatabaseHelper.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, kh.getId());
            ps.setBoolean(2, kh.isTrangThai());
            return ps.executeUpdate();
        } catch (Exception e) { e.printStackTrace(); }
        return 0;
    }

    public int update(KhachHang kh) {
        // Update bảng ConNguoi trước
        new ConNguoiDAO().update(kh);
        String sql = "UPDATE KhachHang SET TrangThai=? WHERE ID=?";
        try (Connection conn = DatabaseHelper.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setBoolean(1, kh.isTrangThai());
            ps.setString(2, kh.getId());
            return ps.executeUpdate();
        } catch (Exception e) { e.printStackTrace(); }
        return 0;
    }

    public int delete(String id) {
        String sql = "DELETE FROM KhachHang WHERE ID=?";
        try (Connection conn = DatabaseHelper.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, id);
            int rows = ps.executeUpdate();
            // Xóa luôn bên ConNguoi
            new ConNguoiDAO().delete(id);
            return rows;
        } catch (Exception e) { e.printStackTrace(); }
        return 0;
    }
        public String generateMa() {
        String sql = "SELECT ID FROM KhachHang ORDER BY CAST(SUBSTRING(ID, 4) AS UNSIGNED) DESC LIMIT 1";
        try (Connection conn = DatabaseHelper.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            if(rs.next()) {
                String lastMa = rs.getString("ID");
                int num = Integer.parseInt(lastMa.substring(3));
                return String.format("KH%03d", num + 1);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "KH00";
    }
}