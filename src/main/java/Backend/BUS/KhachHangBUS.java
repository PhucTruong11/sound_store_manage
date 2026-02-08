package Backend.BUS;

import Backend.DAO.KhachHangDAO;
import Backend.DTO.KhachHang;
import java.util.ArrayList;

public class KhachHangBUS {
    private final KhachHangDAO khDAO = new KhachHangDAO();

    public ArrayList<KhachHang> getAllKhachHang() {
        return khDAO.selectAll();
    }
    public String getNewMa() {
        return khDAO.generateMaKH();
    }

    public boolean add(KhachHang kh) {
        return khDAO.insert(kh) > 0;
    }

    public boolean update(KhachHang kh) {
        return khDAO.update(kh) > 0;
    }

    public boolean delete(String id) {
        return khDAO.delete(id) > 0;
    }
    
}
