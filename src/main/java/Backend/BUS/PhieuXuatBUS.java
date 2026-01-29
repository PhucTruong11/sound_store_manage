package Backend.BUS;

import Backend.DAO.PhieuXuatDAO;
import Backend.DTO.PhieuXuat;
import java.util.ArrayList;

public class PhieuXuatBUS {
    private final PhieuXuatDAO pxDAO = new PhieuXuatDAO();

    public ArrayList<PhieuXuat> getAllPhieuXuat() {
        return pxDAO.selectAll();
    }

    public ArrayList<PhieuXuat> search(String keyword) {
        if (keyword == null || keyword.isEmpty()) {
            return pxDAO.selectAll();
        }
        return pxDAO.search(keyword); 
    }
}