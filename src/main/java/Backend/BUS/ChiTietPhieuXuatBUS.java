package Backend.BUS;

import Backend.DAO.ChiTietPhieuXuatDAO;
import Backend.DTO.ChiTietPhieuXuat;
import java.util.ArrayList;

public class ChiTietPhieuXuatBUS {
    private final ChiTietPhieuXuatDAO ctpxDAO = new ChiTietPhieuXuatDAO();

    public ArrayList<ChiTietPhieuXuat> getAllChiTietPhieuXuat(String maPX) {
        return ctpxDAO.getAllChiTietPhieuXuat(maPX);
    }
}
