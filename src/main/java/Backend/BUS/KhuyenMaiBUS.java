package Backend.BUS;

import Backend.DAO.KhuyenMaiDAO;
import Backend.DTO.KhuyenMai;
import java.util.ArrayList;

public class KhuyenMaiBUS {
    private KhuyenMaiDAO kmDAO = new KhuyenMaiDAO();
    private ArrayList<KhuyenMai> dsKM = new ArrayList<>();

    public KhuyenMaiBUS() {
        refreshData();
    }

    public void refreshData() {
        dsKM = kmDAO.getAll();
    }

    public ArrayList<KhuyenMai> getAllKhuyenMai() {
        return dsKM;
    }

    // ĐÂY LÀ HÀM ĐANG THIẾU DẪN ĐẾN LỖI BUILD
    public boolean delete(String maKM) {
        if (kmDAO.updateStatus(maKM, 0)) { // Gọi xuống DAO để đổi trạng thái thành 0
            refreshData(); // Cập nhật lại danh sách trên RAM sau khi DB thay đổi
            return true;
        }
        return false;
    }

    public String addKhuyenMai(KhuyenMai km) {
        if (kmDAO.add(km)) {
            refreshData();
            return "Thêm thành công!";
        }
        return "Thêm thất bại!";
    }

    public String updateKhuyenMai(KhuyenMai km) {
        if (kmDAO.update(km)) {
            refreshData();
            return "Cập nhật thành công!";
        }
        return "Cập nhật thất bại!";
    }

    public ArrayList<KhuyenMai> search(String text) {
        text = text.toLowerCase().trim();
        ArrayList<KhuyenMai> result = new ArrayList<>();
        for (KhuyenMai km : dsKM) {
            if (km.getMaKM().toLowerCase().contains(text) || 
                km.getTenKM().toLowerCase().contains(text)) {
                result.add(km);
            }
        }
        return result;
    }
}
