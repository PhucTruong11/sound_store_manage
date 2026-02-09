package Backend.BUS;

import Backend.DAO.PhienBanSanPhamDAO;
import Backend.DAO.SanPhamDAO;
import Backend.DTO.SanPham;
import java.util.ArrayList;

public class SanPhamBUS {
    private final SanPhamDAO spDAO = new SanPhamDAO();
    private final PhienBanSanPhamDAO pbDAO = new PhienBanSanPhamDAO();

    public ArrayList<SanPham> getAll() {
        return spDAO.selectAll();
    }

    public boolean update(SanPham sp){
        return spDAO.update(sp)>0;
    }

    public boolean add(SanPham sp){
        return spDAO.insert(sp)>0;
    }

    public boolean delete(String id){
        if(spDAO.delete(id)>0){
            pbDAO.deleteByMaSP(id);
            return true;
        }
        return false;
    }

    public String getNextID(){
        ArrayList<SanPham> list = spDAO.selectAll(); 
        if (list == null || list.isEmpty()) {
            return "SP01"; 
        }
        int maxId = 0;
        for (SanPham sp : list) {
            try {
            // Cắt bỏ chữ "SP", lấy phần số
                String so = sp.getMaSP().replaceAll("[^0-9]", "");
                int id = Integer.parseInt(so);
                if (id > maxId) {
                maxId = id;
            }
            } catch (Exception e) {
            }
        }
        return "SP" + String.format("%02d", maxId + 1);
    }
}

