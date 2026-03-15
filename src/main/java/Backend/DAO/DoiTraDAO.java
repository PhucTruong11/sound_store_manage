package Backend.DAO;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

import Backend.DTO.DoiTra;
import Backend.DatabaseHelper;

public class DoiTraDAO {

    public ArrayList<DoiTra> selectAll() {

        ArrayList<DoiTra> list = new ArrayList<>();

        String sql = """
            SELECT dt.*, cn.HoTen AS TenKhachHang, sp.TenSP 
            FROM DoiTra dt
            LEFT JOIN KhachHang kh ON dt.MaKH = kh.ID
            LEFT JOIN ConNguoi cn ON kh.ID = cn.ID
            LEFT JOIN PhienBanSP pb ON dt.MaPhienBan = pb.MaPhienBan
            LEFT JOIN SanPham sp ON pb.MaSP = sp.MaSP
        """;

        try (Connection conn = DatabaseHelper.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {

                DoiTra dt = new DoiTra(
                        rs.getString("MaDoiTra"),
                        rs.getString("MaPhieuXuat"),
                        rs.getString("MaKH"),
                        rs.getString("MaPhienBan"),
                        rs.getDate("NgayDoiTra").toLocalDate(),
                        rs.getInt("SoLuong"),
                        rs.getString("LyDo"),
                        rs.getString("TinhTrang")
                );
            dt.setTenKH(rs.getString("TenKhachHang") != null ? rs.getString("TenKhachHang") : "N/A");
            dt.setTenSP(rs.getString("TenSP") != null ? rs.getString("TenSP") : "N/A");

            list.add(dt);
            }

        } catch (Exception e) {
            System.err.println("Lỗi SQL tại DoiTraDAO: " + e.getMessage());
            e.printStackTrace();
        }

        return list;
    }

    public int insert(DoiTra dt) {

        String sql = "INSERT INTO DoiTra VALUES (?,?,?,?,?,?,?,?)";

        try (Connection conn = DatabaseHelper.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, dt.getMaDoiTra());
            ps.setString(2, dt.getMaPhieuXuat());
            ps.setString(3, dt.getMaKH());
            ps.setString(4, dt.getMaPhienBan());
            ps.setDate(5, Date.valueOf(dt.getNgayDoiTra()));
            ps.setInt(6, dt.getSoLuong());
            ps.setString(7, dt.getLyDo());
            ps.setString(8, dt.getTinhTrang());

            return ps.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();
        }

        return 0;
    }

    public int update(DoiTra dt) {

        String sql = """
            UPDATE DoiTra
            SET MaPhieuXuat=?,
                MaKH=?,
                MaPhienBan=?,
                NgayDoiTra=?,
                SoLuong=?,
                LyDo=?,
                TinhTrang=?
            WHERE MaDoiTra=?
        """;

        try (Connection conn = DatabaseHelper.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, dt.getMaPhieuXuat());
            ps.setString(2, dt.getMaKH());
            ps.setString(3, dt.getMaPhienBan());
            ps.setDate(4, Date.valueOf(dt.getNgayDoiTra()));
            ps.setInt(5, dt.getSoLuong());
            ps.setString(6, dt.getLyDo());
            ps.setString(7, dt.getTinhTrang());
            ps.setString(8, dt.getMaDoiTra());

            return ps.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();
        }

        return 0;
    }

    public int delete(String ma) {

        String sql = "DELETE FROM DoiTra WHERE MaDoiTra=?";

        try (Connection conn = DatabaseHelper.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, ma);

            return ps.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();
        }

        return 0;
    }
}