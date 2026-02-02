package Backend.BUS;

import Backend.DTO.KhuyenMai;
import java.util.ArrayList;

public class KhuyenMaiBUS {
    // Giả sử bạn đã có hàm này
    public ArrayList<KhuyenMai> getAllKhuyenMai() {
        // Logic lấy dữ liệu
        return new ArrayList<>(); 
    }

    // THÊM HÀM NÀY VÀO ĐỂ HẾT LỖI
    public void delete(String maKM) {
        // Gọi xuống DAO để xóa trong database
        // Ví dụ: kmDAO.delete(maKM);
        System.out.println("Đã gọi hàm xóa KM mã: " + maKM);
    }
    
    // Nếu bạn muốn giống hệt NCC, hãy thêm hàm lấy mã mới (nếu cần)
    public String getNewMaKM() {
        return "KM" + (getAllKhuyenMai().size() + 1);
    }
}
