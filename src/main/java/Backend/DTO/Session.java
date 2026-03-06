package Backend.DTO;

public class Session {
    // 1. Lưu đối tượng tài khoản (Chứa username, password, maNV, maNhomQuyen)
    public static TaiKhoan currentAccount;
    
    // 2. Lưu đối tượng nhân viên (Chứa đầy đủ HoTen, SDT, Email...)
    // Thay vì chỉ lưu String tên, ta lưu cả đối tượng để dễ lấy MaNV hoặc các thông tin khác
    public static NhanVien currentNhanVien;

    // 3. Hàm xóa phiên làm việc khi đăng xuất
    public static void clear() {
        currentAccount = null;
        currentNhanVien = null;
    }
}
