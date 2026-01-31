package Backend.BUS;

import Backend.DAO.NhaCungCapDAO;
import Backend.DTO.NhaCungCap;
import java.util.ArrayList;

public class NhaCungCapBUS {
    private final NhaCungCapDAO nccDAO = new NhaCungCapDAO();

    public ArrayList<NhaCungCap> getAllNhaCungCap() {
        return nccDAO.selectAll();
    }

    public String getNewMaNCC() {
        return nccDAO.generateMaNCC();
    }
 
    public boolean add(NhaCungCap ncc) {
        // Thêm kiểm tra mã NCC đã tồn tại hay chưa
        return nccDAO.insert(ncc) > 0;
    }

    public boolean update(NhaCungCap ncc) {
        return nccDAO.update(ncc) > 0;
    }

    public boolean delete(String id) {
        return nccDAO.delete(id) > 0;
    }
}
