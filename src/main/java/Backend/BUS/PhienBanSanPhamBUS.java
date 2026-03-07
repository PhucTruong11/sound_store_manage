package Backend.BUS;

import Backend.DAO.PhienBanSanPhamDAO;
import Backend.DAO.ChiTietSPDAO;
import Backend.DTO.PhienBanSanPham;
import java.util.ArrayList;

public class PhienBanSanPhamBUS {
    private final PhienBanSanPhamDAO pbspDAO = new PhienBanSanPhamDAO();
    private final ChiTietSPDAO ctDAO=new ChiTietSPDAO();

    public ArrayList<PhienBanSanPham> getAllPhienBanSanPham() {
        return pbspDAO.selectAll();
    }

    public ArrayList<PhienBanSanPham> getByNCC(String ma) {
        return pbspDAO.selectByNCC(ma);
    }

    public ArrayList<PhienBanSanPham> getByMaSP(String ma){
        return pbspDAO.selectByMaSP(ma);
    }

    public boolean update(PhienBanSanPham pbsp){
         return pbspDAO.update(pbsp)>0;
    }

    public boolean add(PhienBanSanPham pbsp){
        return pbspDAO.insert(pbsp)>0;
    }

    public boolean delete(String id){
        if(pbspDAO.delete(id)>0){
            ctDAO.deleteByMaPB(id);
            return true;
        }
        return false;
    }

    public String getNextID() {
        return pbspDAO.getNextID();
    }


    

}
