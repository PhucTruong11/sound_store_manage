package Backend.DAO;
import Backend.DatabaseHelper;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

import Backend.DTO.Amthanh;

public class AmthanhDAO {

    // Hàm lấy danh sách Laptop từ Database
    public ArrayList<Amthanh> getListAmthanh() {
        ArrayList<Amthanh> list = new ArrayList<>();
        
        // Ví dụ bảng tên là 'sanpham'
        String sql = "SELECT * FROM sanpham"; 

        // Lấy kết nối và kiểm tra null
        Connection conn = DatabaseHelper.getConnection();
        if (conn == null) {
            System.err.println("LỖI: Không thể kết nối đến Database!");
            return list; // Trả về danh sách trống thay vì làm sập App
        }

        try (PreparedStatement stmt = conn.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery()) {
        while (rs.next()) {
            String ma = rs.getString("MaSP"); 
            String ten = rs.getString("TenSP");
            double gia = rs.getDouble("DonGia"); 
            list.add(new Amthanh(ma, ten, gia));
        }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try { if(conn != null) conn.close(); } catch(Exception e){}
        }
        return list;
        }
}