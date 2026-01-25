package Backend.BUS;

import Backend.DAO.SanPhamDAO;
import Backend.DTO.SanPham;
import java.util.ArrayList;

public class SanPhamBUS {
    private final SanPhamDAO spDAO = new SanPhamDAO();

    public ArrayList<SanPham> getAll() {
        return spDAO.getAll();
    }
}
