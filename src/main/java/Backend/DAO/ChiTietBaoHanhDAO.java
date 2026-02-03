package Backend.DAO;

import Backend.DatabaseHelper;
import Backend.DTO.ChiTietBaoHanh;
import Backend.DTO.ChiTietPhieuNhap;

import java.util.ArrayList;
import java.sql.*;

public class ChiTietBaoHanhDAO implements ChiTietInterface<ChiTietBaoHanh> {

    @Override
    public int insert(ArrayList<ChiTietBaoHanh> list) {
        int count = 0;
        String sql = "INSERT INTO ChiTietBaoHanh VALUES (?, ?, ?, ?)";
        try (Connection conn = DatabaseHelper.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql)) {
            for (ChiTietBaoHanh ctbh : list) {
                stmt.setString(1, ctbh.getMaCTBH());
                stmt.setString(2, ctbh.getMaBH());
                stmt.setString(3, ctbh.getNoiDung());
                stmt.setString(4, ctbh.getTinhTrang());
                count += stmt.executeUpdate();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return count;
    }

    @Override
    public ArrayList<ChiTietBaoHanh> selectAll(String maBH) {
        ArrayList<ChiTietBaoHanh> list = new ArrayList<>();
        String sql = "SELECT c.*, sp.TenSP " +
                "FROM ChiTietBaoHanh c " +
                "JOIN BaoHanh bh ON c.MaBH = bh.MaBH " +
                "LEFT JOIN ChiTietSP ctsp ON bh.MaImei = ctsp.MaImei " +
                "LEFT JOIN PhienBanSP pb ON ctsp.MaPhienBan = pb.MaPhienBan " +
                "LEFT JOIN SanPham sp ON pb.MaSP = sp.MaSP " +
                "WHERE c.MaBH = ? " +
                "ORDER BY c.MaCTBH";

        try (Connection conn = DatabaseHelper.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, maBH);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                ChiTietBaoHanh ctbh = new ChiTietBaoHanh();
                ctbh.setMaCTBH(rs.getString("MaCTBH"));
                ctbh.setMaBH(rs.getString("MaBH"));
                ctbh.setTenSP(rs.getString("TenSP"));
                ctbh.setNoiDung(rs.getString("NoiDung"));
                ctbh.setTinhTrang(rs.getString("TinhTrang"));
                list.add(ctbh);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    public int update(ChiTietBaoHanh ctbh) {
        String sql = "UPDATE ChiTietBaoHanh SET NoiDung=?, TinhTrang=? WHERE MaCTBH=?";
        try (Connection conn = DatabaseHelper.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, ctbh.getNoiDung());
            stmt.setString(2, ctbh.getTinhTrang());
            stmt.setString(3, ctbh.getMaCTBH());
            return stmt.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }

    @Override
    public int delete(String id) {
        return 0;
    }

    @Override
    public int update(ArrayList<ChiTietBaoHanh> t, String pk) {
        return 0;
    }
}