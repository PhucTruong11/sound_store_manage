package Backend.BUS;

import Backend.DAO.NhanVienDAO;
import Backend.DTO.NhanVien;
import java.util.ArrayList;

public class NhanVienBUS {
    private final NhanVienDAO nhanVienDAO = new NhanVienDAO();

    public ArrayList<NhanVien> getAllNhanVien() {
        return nhanVienDAO.selectAll();
    }

    public String getNewMa() {
        return nhanVienDAO.generateMaNV();
    }

    public boolean add(NhanVien nv) {
        return nhanVienDAO.insert(nv) > 0;
    }

    public boolean update(NhanVien nv) {
        return nhanVienDAO.update(nv) > 0;
    }

    public boolean delete(String id) {
        return nhanVienDAO.delete(id) > 0;
    }
}

