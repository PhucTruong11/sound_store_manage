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

    // ĐƯA HÀM NÀY VÀO TRONG LỚP NHƯ THẾ NÀY
    public NhanVien getById(String id) {
        if (id == null) return null;
        ArrayList<NhanVien> list = getAllNhanVien();
        for (NhanVien nv : list) {
            if (nv.getId().equalsIgnoreCase(id)) {
                return nv;
            }
        }
        return null;
    }
}
