package Backend.DAO;

import java.util.ArrayList;

public interface ChiTietInterface<T> {
    // Chèn nguyên một danh sách chi tiết của một hóa đơn
    public int insert(ArrayList<T> t); 
    // Xóa toàn bộ chi tiết thuộc về một mã cha
    public int delete(String id);
    // Cập nhật danh sách chi tiết dựa trên mã khóa ngoại của bảng cha
    public int update(ArrayList<T> t, String pk);
    // Lấy tất cả chi tiết thuộc về một mã cha cụ thể
    public ArrayList<T> selectAll(String id);
}
