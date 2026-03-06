package Backend.BUS;

import Backend.DAO.BaoHanhDAO;
import Backend.DTO.BaoHanh;
import java.util.ArrayList;

public class BaoHanhBUS {
    private final BaoHanhDAO bhDAO = new BaoHanhDAO();

    public ArrayList<BaoHanh> getAllBaoHanh() {
        return bhDAO.selectAllWithDetails();
    }

    public boolean add(BaoHanh bh) {
        // Kiểm tra Imei phải tồn tại trong bảng ChiTietSP mới cho thêm
        if (!bhDAO.checkImeiExists(bh.getMaImei())) {
            System.err.println("Mã Imei không tồn tại: " + bh.getMaImei());
            return false;
        }
        return bhDAO.insert(bh) > 0;
    }

    public boolean update(BaoHanh bh) {
        return bhDAO.update(bh) > 0;
    }

    public boolean delete(String id) {
        ChiTietBaoHanhBUS ctbhBUS = new ChiTietBaoHanhBUS();

        // Bước 1: Dọn dẹp bảng con trước để gỡ bỏ ràng buộc khóa ngoại
        ctbhBUS.delete(id);

        // Bước 2: Xóa bảng cha (BaoHanh)
        return bhDAO.delete(id) > 0;
    }

    public String getNewMaBH() {
        return bhDAO.generateMaBH();
    }
}