package Backend;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseHelper {
    // 1. Cấu hình thông tin (Cô giáo thường chấm kỹ chỗ này)
    // Nếu database của bạn tên khác thì sửa chỗ 'quanlysieuthidienthoai' nhé
    public static final String DB_URL = "jdbc:mysql://localhost:3306/quanlylaptop?useSSL=false&serverTimezone=UTC";
    public static final String USER = "root";     // Tên đăng nhập mặc định của XAMPP
    public static final String PASS = "";         // Mật khẩu mặc định là rỗng

    // 2. Hàm lấy kết nối JDBC
    public static Connection getConnection() {
        Connection conn = null;
        try {
            // Dòng này để khai báo Driver (bắt buộc với JDBC cũ, giờ có thể bỏ nhưng nên để cho chắc)
            Class.forName("com.mysql.cj.jdbc.Driver");
            
            // Mở kết nối
            conn = DriverManager.getConnection(DB_URL, USER, PASS);
            // System.out.println("Kết nối thành công!"); // Bỏ comment dòng này để test
            
        } catch (ClassNotFoundException e) {
            System.out.println("Không tìm thấy Driver JDBC!");
            e.printStackTrace();
        } catch (SQLException e) {
            System.out.println("Lỗi kết nối SQL: " + e.getMessage());
            e.printStackTrace();
        }
        return conn;
    }
}