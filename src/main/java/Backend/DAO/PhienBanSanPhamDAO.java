package Backend.DAO;

import Backend.DatabaseHelper;
import Backend.DTO.PhienBanSanPham;
import java.sql.*;
import java.util.ArrayList;

public class PhienBanSanPhamDAO implements DAOInterface<PhienBanSanPham> {
    @Override
    public ArrayList<PhienBanSanPham> selectAll() {
        ArrayList<PhienBanSanPham> list = new ArrayList<>();
        String sql = "SELECT pb.*, sp.TenSP FROM PhienBanSP pb " + "JOIN SanPham sp ON pb.MaSP = sp.MaSP "
                + "WHERE pb.TrangThai = TRUE";
        try (Connection conn = DatabaseHelper.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql);
                ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                PhienBanSanPham pbsp = new PhienBanSanPham();
                pbsp.setMaPhienBan(rs.getString("MaPhienBan"));
                pbsp.setMaSP(rs.getString("MaSP"));
                pbsp.setMauSac(rs.getString("MauSac"));
                pbsp.setCongSuat(rs.getString("CongSuat"));
                pbsp.setPin(rs.getString("Pin"));
                pbsp.setKetNoi(rs.getString("KetNoi"));
                pbsp.setGiaNhap(rs.getDouble("GiaNhap"));
                pbsp.setGiaBan(rs.getDouble("GiaBan"));
                pbsp.setSoLuongTon(rs.getInt("SoLuongTon"));
                pbsp.setTrangThai(rs.getBoolean("TrangThai"));
                pbsp.setHinhAnh(rs.getString("HinhAnh"));

                pbsp.setTenSP(rs.getString("TenSP"));
                list.add(pbsp);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    @Override
    public PhienBanSanPham selectById(String maPhienBan) {
        String sql = "SELECT pb.*, sp.TenSP FROM PhienBanSP pb " +
                "JOIN SanPham sp ON pb.MaSP = sp.MaSP " +
                "WHERE pb.MaPhienBan = ? AND pb.TrangThai = TRUE";
        try (Connection conn = DatabaseHelper.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, maPhienBan);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return new PhienBanSanPham(
                        rs.getString("MaPhienBan"),
                        rs.getString("MaSP"),
                        rs.getString("MauSac"),
                        rs.getString("CongSuat"),
                        rs.getString("Pin"),
                        rs.getString("KetNoi"),
                        rs.getDouble("GiaNhap"),
                        rs.getDouble("GiaBan"),
                        rs.getInt("SoLuongTon"),
                        rs.getBoolean("TrangThai"),
                        rs.getString("HinhAnh"));

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public int insert(PhienBanSanPham pbsp) {
        int result = 0;
        String sql = "INSERT INTO PhienBanSP VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        try (Connection conn = DatabaseHelper.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, pbsp.getMaPhienBan());
            stmt.setString(2, pbsp.getMaSP());
            stmt.setString(3, pbsp.getMauSac());
            stmt.setString(4, pbsp.getCongSuat());
            stmt.setString(5, pbsp.getPin());
            stmt.setString(6, pbsp.getKetNoi());
            stmt.setDouble(7, pbsp.getGiaNhap());
            stmt.setDouble(8, pbsp.getGiaBan());
            stmt.setInt(9, pbsp.getSoLuongTon());
            stmt.setBoolean(10, true);
            stmt.setString(11, pbsp.getHinhAnh());

            result = stmt.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    @Override
    public int update(PhienBanSanPham pbsp) {
        String sql = "UPDATE PhienBanSP SET MauSac=?, CongSuat=?, Pin=?, KetNoi=?, GiaNhap=?, GiaBan=?, SoLuongTon=?, HinhAnh=? WHERE MaPhienBan=?";
        try (Connection conn = DatabaseHelper.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, pbsp.getMauSac());
            stmt.setString(2, pbsp.getCongSuat());
            stmt.setString(3, pbsp.getPin());
            stmt.setString(4, pbsp.getKetNoi());
            stmt.setDouble(5, pbsp.getGiaNhap());
            stmt.setDouble(6, pbsp.getGiaBan());
            stmt.setInt(7, pbsp.getSoLuongTon());
            stmt.setString(8, pbsp.getHinhAnh());
            stmt.setString(9, pbsp.getMaPhienBan());

            return stmt.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }

    @Override
    public int delete(String id) {
        String sql = "UPDATE PhienBanSP SET TrangThai=FALSE WHERE MaPhienBan=? ";
        try (Connection conn = DatabaseHelper.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, id);
            return stmt.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }

    public int deleteByMaSP(String maSP) {
        String sql = "UPDATE PhienBanSP SET TrangThai = FALSE WHERE MaSP = ?";
        try (Connection conn = DatabaseHelper.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, maSP);
            return stmt.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }

    // Chọn theo nhà cung cấp
    public ArrayList<PhienBanSanPham> selectByNCC(String maNCC) {
        ArrayList<PhienBanSanPham> list = new ArrayList<>();
        String sql = "SELECT pb.*, sp.TenSP FROM PhienBanSP pb " +
                "JOIN SanPham sp ON pb.MaSP = sp.MaSP " +
                "JOIN NCC_SanPham ncc_sp ON sp.MaSP = ncc_sp.MaSP " +
                "WHERE ncc_sp.MaNCC = ?";

        try (Connection conn = DatabaseHelper.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, maNCC);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                PhienBanSanPham pb = new PhienBanSanPham(
                        rs.getString("MaPhienBan"),
                        rs.getString("MaSP"),
                        rs.getString("MauSac"),
                        rs.getString("CongSuat"),
                        rs.getString("Pin"),
                        rs.getString("KetNoi"),
                        rs.getDouble("GiaNhap"),
                        rs.getDouble("GiaBan"),
                        rs.getInt("SoLuongTon"),
                        rs.getBoolean("TrangThai"),
                        rs.getString("HinhAnh"));
                pb.setTenSP(rs.getString("TenSP"));
                list.add(pb);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    // Lấy Phiên Bản từ mã sản phẩm
    public ArrayList<PhienBanSanPham> selectByMaSP(String maSP) {
        ArrayList<PhienBanSanPham> list = new ArrayList<>();
        String sql = "SELECT * FROM PhienBanSP WHERE MaSP = ? AND TrangThai=TRUE";

        try (Connection conn = DatabaseHelper.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, maSP);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                PhienBanSanPham pb = new PhienBanSanPham(
                        rs.getString("MaPhienBan"),
                        rs.getString("MaSP"),
                        rs.getString("MauSac"),
                        rs.getString("CongSuat"),
                        rs.getString("Pin"),
                        rs.getString("KetNoi"),
                        rs.getDouble("GiaNhap"),
                        rs.getDouble("GiaBan"),
                        rs.getInt("SoLuongTon"),
                        rs.getBoolean("TrangThai"),
                        rs.getString("HinhAnh"));
                list.add(pb);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    public ArrayList<PhienBanSanPham> search(String text) {
        ArrayList<PhienBanSanPham> list = new ArrayList<>();
        String sql = "SELECT pb.*, sp.TenSP FROM PhienBanSP pb " +
                "JOIN SanPham sp ON pb.MaSP = sp.MaSP " +
                "WHERE pb.TrangThai = TRUE AND (pb.MaPhienBan LIKE ? OR sp.TenSP LIKE ? OR pb.MauSac LIKE ?)";

        try (Connection conn = DatabaseHelper.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql)) {

            String query = "%" + text + "%";
            stmt.setString(1, query);
            stmt.setString(2, query);
            stmt.setString(3, query);

            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                PhienBanSanPham pbsp = new PhienBanSanPham();
                pbsp.setMaPhienBan(rs.getString("MaPhienBan"));
                pbsp.setMaSP(rs.getString("MaSP"));
                pbsp.setMauSac(rs.getString("MauSac"));
                pbsp.setCongSuat(rs.getString("CongSuat"));
                pbsp.setPin(rs.getString("Pin"));
                pbsp.setKetNoi(rs.getString("KetNoi"));
                pbsp.setGiaNhap(rs.getDouble("GiaNhap"));
                pbsp.setGiaBan(rs.getDouble("GiaBan"));
                pbsp.setSoLuongTon(rs.getInt("SoLuongTon"));
                pbsp.setTrangThai(rs.getBoolean("TrangThai"));
                pbsp.setHinhAnh(rs.getString("HinhAnh"));
                pbsp.setTenSP(rs.getString("TenSP"));

                list.add(pbsp);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    public ArrayList<PhienBanSanPham> selectByLoai(String tenLoai) {
        ArrayList<PhienBanSanPham> list = new ArrayList<>();
        String sql = "SELECT pb.*, sp.TenSP FROM PhienBanSP pb " +
                "JOIN SanPham sp ON pb.MaSP = sp.MaSP " +
                "JOIN LoaiSP l ON sp.MaLoai = l.MaLoai " +
                "WHERE l.TenLoai = ? AND pb.TrangThai = TRUE";
        try (Connection conn = DatabaseHelper.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, tenLoai);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                PhienBanSanPham pb = new PhienBanSanPham();
                pb.setTenSP(rs.getString("TenSP"));
                list.add(pb);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }
}
