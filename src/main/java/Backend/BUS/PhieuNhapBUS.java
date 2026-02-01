package Backend.BUS;

import Backend.DAO.ChiTietPhieuNhapDAO;
import Backend.DAO.PhieuNhapDAO;
import Backend.DTO.ChiTietPhieuNhap;
import Backend.DTO.PhieuNhap;
import java.util.ArrayList;

public class PhieuNhapBUS {
    private final PhieuNhapDAO pnDAO = new PhieuNhapDAO();
    private final ChiTietPhieuNhapDAO ctDAO = new ChiTietPhieuNhapDAO();

    public ArrayList<PhieuNhap> getAllPhieuNhap() {
        return pnDAO.selectAll();
    }

    public String getNewMaPhieu() {
        return pnDAO.generateMaPhieuNhap();
    }

    public boolean thanhToan(PhieuNhap pn, ArrayList<ChiTietPhieuNhap> dsCT) {
        // Insert phiếu nhập
        if(pnDAO.insert(pn) > 0) {
            // Insert danh sách chi tiết
            // Các Trigger trg_UpdateStock, trg_CalcThanhTien, trg_UpdateTongTien sẽ chạy
            return ctDAO.insert(dsCT) > 0;
        }
        return false;
    }
}
