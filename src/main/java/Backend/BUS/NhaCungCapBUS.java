package Backend.BUS;

import Backend.DAO.NhaCungCapDAO;
import Backend.DTO.NhaCungCap;
import java.util.ArrayList;

public class NhaCungCapBUS {
    private final NhaCungCapDAO nccDAO = new NhaCungCapDAO();

    public ArrayList<NhaCungCap> getAllNhaCungCap() {
        return nccDAO.getAllNhaCungCap();
    }
}
