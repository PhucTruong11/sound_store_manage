package Backend.DAO;

import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.data.general.DefaultPieDataset;
import Backend.DatabaseHelper;
import java.sql.*;

public class ThongKeDAO {
    public double getDoanhThuNgay() {
        String sql = "SELECT SUM(TongTien) FROM PhieuXuat WHERE DATE(NgayXuat) = CURDATE() AND TrangThai = TRUE";

        try(Connection conn = DatabaseHelper.getConnection();
            PreparedStatement stmt = conn.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery()) {
                if(rs.next()) return rs.getDouble(1);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }

    public double getVonNhapThang() {
        String sql = "SELECT SUM(TongTien) FROM PhieuNhap " +
                     "WHERE MONTH(NgayNhap) = MONTH(CURDATE()) " +
                     "AND YEAR(NgayNhap) = YEAR(CURDATE()) AND TrangThai = TRUE";
        try (Connection conn = DatabaseHelper.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            if (rs.next()) return rs.getDouble(1);
        } catch (Exception e) { e.printStackTrace(); }
        return 0;
    }

    public int getDonHangMoiNgay() {
        String sql = "SELECT COUNT(*) FROM PhieuXuat WHERE DATE(NgayXuat) = CURDATE() AND TrangThai = TRUE";
        try (Connection conn = Backend.DatabaseHelper.getConnection();
            PreparedStatement stmt = conn.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery()) {
            if (rs.next()) return rs.getInt(1);
        } catch (Exception e) { e.printStackTrace(); }
        return 0;
    }

    public double getLoiNhuanThang() {
        // Lợi nhuận = Tổng tiền xuất - (Tổng tiền nhập của các SP đã bán)
        // Để đơn giản nhất, ta tính hiệu số thu chi trong tháng
        String sql = "SELECT (SELECT SUM(TongTien) FROM PhieuXuat WHERE MONTH(NgayXuat) = MONTH(CURDATE()) AND TrangThai = TRUE) - " +
                    "(SELECT SUM(TongTien) FROM PhieuNhap WHERE MONTH(NgayNhap) = MONTH(CURDATE()) AND TrangThai = TRUE)";
        try (Connection conn = Backend.DatabaseHelper.getConnection();
            PreparedStatement stmt = conn.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery()) {
            if (rs.next()) return rs.getDouble(1);
        } catch (Exception e) { e.printStackTrace(); }
        return 0;
    }

    public void getTop5SanPham(DefaultCategoryDataset dataset, String type) {
        dataset.clear(); // Xóa dữ liệu cũ trước khi nạp mới
    
        // Xác định điều kiện lọc thời gian
        String timeCondition = "";
        if (type.equals("Ngày")) timeCondition = "DATE(px.NgayXuat) = CURDATE()";
        else if (type.equals("Tháng")) timeCondition = "MONTH(px.NgayXuat) = MONTH(CURDATE()) AND YEAR(px.NgayXuat) = YEAR(CURDATE())";
        else if (type.equals("Năm")) timeCondition = "YEAR(px.NgayXuat) = YEAR(CURDATE())";

        String sql = "SELECT s.TenSP, SUM(ctx.SoLuong) as TongSL " +
                    "FROM ChiTietPhieuXuat ctx " +
                    "JOIN PhienBanSP pb ON ctx.MaPhienBan = pb.MaPhienBan " +
                    "JOIN SanPham s ON pb.MaSP = s.MaSP " +
                    "JOIN PhieuXuat px ON ctx.MaPhieuXuat = px.MaPhieuXuat " +
                    "WHERE " + timeCondition + " AND px.TrangThai = TRUE " +
                    "GROUP BY s.TenSP ORDER BY TongSL DESC LIMIT 5";
        
        try (Connection conn = Backend.DatabaseHelper.getConnection();
            PreparedStatement stmt = conn.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                dataset.addValue(rs.getInt("TongSL"), "Đã bán", rs.getString("TenSP"));
            }
        } catch (Exception e) { e.printStackTrace(); }
    }

    public void getTiTrongDoanhThuLoai(DefaultPieDataset dataset, String type) {
        dataset.clear(); // Xóa dữ liệu cũ trước khi nạp mới
    
        // Xác định điều kiện lọc thời gian
        String timeCondition = "";
        if (type.equals("Ngày")) timeCondition = "DATE(px.NgayXuat) = CURDATE()";
        else if (type.equals("Tháng")) timeCondition = "MONTH(px.NgayXuat) = MONTH(CURDATE()) AND YEAR(px.NgayXuat) = YEAR(CURDATE())";
        else if (type.equals("Năm")) timeCondition = "YEAR(px.NgayXuat) = YEAR(CURDATE())";

        String sql = "SELECT l.TenLoai, SUM(ctx.ThanhTien) as DoanhThu " +
                    "FROM ChiTietPhieuXuat ctx " +
                    "JOIN PhienBanSP pb ON ctx.MaPhienBan = pb.MaPhienBan " +
                    "JOIN SanPham s ON pb.MaSP = s.MaSP " +
                    "JOIN LoaiSP l ON s.MaLoai = l.MaLoai " +
                    "JOIN PhieuXuat px ON ctx.MaPhieuXuat = px.MaPhieuXuat " +
                    "WHERE " + timeCondition + " AND px.TrangThai = TRUE " +
                    "GROUP BY l.TenLoai";
        
        try (Connection conn = Backend.DatabaseHelper.getConnection();
            PreparedStatement stmt = conn.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                dataset.setValue(rs.getString("TenLoai"), rs.getDouble("DoanhThu"));
            }
        } catch (Exception e) { e.printStackTrace(); }
    }
}