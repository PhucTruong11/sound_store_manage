package Backend;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

public class LaptopDAO {

    // Hàm lấy danh sách Laptop từ Database
    public ArrayList<Laptop> getListLaptop() {
        ArrayList<Laptop> list = new ArrayList<>();
        
        // CÂU LỆNH SQL CỦA BẠN (Kiểm tra kỹ tên bảng trong database nhé)
        // Ví dụ bảng của bạn tên là 'sanpham'
        String sql = "SELECT * FROM sanpham"; 

        // Sử dụng Try-with-resources của Java (tự động đóng kết nối, cô giáo rất thích cái này)
        try (Connection conn = DatabaseHelper.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            // Duyệt từng dòng dữ liệu trả về
            while (rs.next()) {
                // Lấy dữ liệu theo tên cột trong SQL
                String ma = rs.getString("MaSP");    // Tên cột phải khớp 100% trong SQL
                String ten = rs.getString("TenSP");
                double gia = rs.getDouble("DonGia"); 

                // Tạo đối tượng và thêm vào list
                Laptop lap = new Laptop(ma, ten, gia);
                list.add(lap);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        return list;
    }
}