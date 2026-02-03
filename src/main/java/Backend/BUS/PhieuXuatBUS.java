package Backend.BUS;

import Backend.DAO.ChiTietPhieuXuatDAO;
import Backend.DAO.PhieuXuatDAO;
import Backend.DAO.SanPhamDAO;
import Backend.DTO.ChiTietPhieuXuat;
import Backend.DTO.PhieuXuat;
import java.util.ArrayList;

public class PhieuXuatBUS {
    private final PhieuXuatDAO pxDAO = new PhieuXuatDAO();
    private final ChiTietPhieuXuatDAO ctpxDAO = new ChiTietPhieuXuatDAO();

    public ArrayList<PhieuXuat> getAllPhieuXuat() {
        return pxDAO.selectAll();
    }

    public ArrayList<PhieuXuat> search(String keyword) {
        if (keyword == null || keyword.isEmpty()) {
            return pxDAO.selectAll();
        }
        return pxDAO.search(keyword);
    }

    public String getNewMaPhieu() {
        return pxDAO.generateMaPhieuXuat();
    }

    public boolean thanhToan(PhieuXuat px, ArrayList<ChiTietPhieuXuat> dsCT) {
        // Insert phiếu nhập
        if (pxDAO.insert(px) > 0) {
            // Insert danh sách chi tiết
            // Các Trigger trg_UpdateStock, trg_CalcThanhTien, trg_UpdateTongTien sẽ chạy
            return ctpxDAO.insert(dsCT) > 0;
        }
        return false;
    }
}