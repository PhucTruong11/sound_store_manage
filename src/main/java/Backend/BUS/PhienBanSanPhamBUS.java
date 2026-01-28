package Backend.BUS;

import Backend.DAO.PhienBanSanPhamDAO;
import Backend.DTO.PhienBanSanPham;
import java.util.ArrayList;

public class PhienBanSanPhamBUS {
    private final PhienBanSanPhamDAO phienbansanphamDAO = new PhienBanSanPhamDAO();

    public ArrayList<PhienBanSanPham> getAllPhienBanSanPham() {
        return phienbansanphamDAO.selectAll();
    }
}
