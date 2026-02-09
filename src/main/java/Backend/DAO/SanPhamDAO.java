package Backend.DAO;

import Backend.DatabaseHelper;
import Backend.DTO.SanPham;
import java.sql.*;
import java.util.ArrayList;

public class SanPhamDAO implements DAOInterface<SanPham> {
    @Override
    public ArrayList<SanPham> selectAll() {
        ArrayList<SanPham> list = new ArrayList<>();
        //String sql = "SELECT * FROM SanPham WHERE TrangThai = TRUE";
        String sql = "SELECT sp.*, " +
             "(SELECT COALESCE(SUM(pb.SoLuongTon), 0) " +" FROM PhienBanSP pb " +" WHERE pb.MaSP = sp.MaSP AND pb.TrangThai = 1) AS TongSoLuong " + "FROM SanPham sp " +"WHERE sp.TrangThai = TRUE"; 
        try (Connection conn = DatabaseHelper.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql);
                ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                list.add(new SanPham(
                        rs.getString("MaSP"),
                        rs.getString("TenSP"),
                        rs.getInt("TongSoLuong"),
                        rs.getString("MaLoai"),
                        rs.getString("MaHang"),
                        rs.getString("MoTa"),
                        rs.getInt("ThoiGianBaoHanh"),
                        rs.getBoolean("TrangThai")));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    @Override
    public SanPham selectById(String maSP) {
        String sql="SELECT * FROM SanPham WHERE MaSP=? AND TrangThai=TRUE";
        try(Connection conn=DatabaseHelper.getConnection();
        PreparedStatement stmt=conn.prepareStatement(sql)){
            stmt.setString(1,maSP);
            ResultSet rs=stmt.executeQuery();
            if(rs.next()) {
                return new SanPham(
                        rs.getString("MaSP"),
                        rs.getString("TenSP"),
                        rs.getInt("TongSoLuong"),
                        rs.getString("MaLoai"),
                        rs.getString("MaHang"),
                        rs.getString("MoTa"),
                        rs.getInt("ThoiGianBaoHanh"),
                        rs.getBoolean("TrangThai"));
            }
        }catch(Exception e){
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public int insert(SanPham sp) {
        String sql="INSERT INTO SanPham (MaSP,TenSP,MaLoai,MaHang,MoTa,ThoiGianBaoHanh) Value(?,?,?,?,?,?)";
        try(Connection conn=DatabaseHelper.getConnection();
        PreparedStatement stmt=conn.prepareStatement(sql)){
            stmt.setString(1,sp.getMaSP());
            stmt.setString(2,sp.getTenSP());
            stmt.setString(3,sp.getMaLoai());
            stmt.setString(4,sp.getMaHang());
            stmt.setString(5,sp.getMoTa());
            stmt.setInt(6,sp.getThoiGianBaoHanh());

            return stmt.executeUpdate();
        }catch(Exception e){
            e.printStackTrace();
        }
        return 0;
    }

    @Override
    public int update(SanPham sp) {
        String sql="UPDATE SanPham SET MaSP=?, TenSP=?, MaLoai=?, MaHang=?, MoTa=?, ThoiGianBaoHanh=? ";
        try(Connection conn=DatabaseHelper.getConnection();
        PreparedStatement stmt=conn.prepareStatement(sql)){
            stmt.setString(1,sp.getMaSP());
            stmt.setString(2,sp.getTenSP());
            stmt.setString(3,sp.getMaLoai());
            stmt.setString(4,sp.getMaHang());
            stmt.setString(5,sp.getMoTa());
            stmt.setInt(6,sp.getThoiGianBaoHanh());

            return stmt.executeUpdate();
        }catch(Exception e){
            e.printStackTrace();
        }
        return 0;
    }

    @Override
    public int delete(String id) {
        String sql="UPDATE SANPHAM SET TrangThai=FALSE WHERE MaSP=? ";
        try(Connection conn=DatabaseHelper.getConnection();
        PreparedStatement stmt=conn.prepareStatement(sql)){
            stmt.setString(1,id);
            return stmt.executeUpdate();
        }catch(Exception e){
            e.printStackTrace();
        }
        return 0;
    }
}
