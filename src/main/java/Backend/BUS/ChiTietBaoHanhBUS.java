package Backend.BUS;

import Backend.DAO.ChiTietBaoHanhDAO;
import Backend.DTO.ChiTietBaoHanh;
import java.util.ArrayList;

public class ChiTietBaoHanhBUS {
    private final ChiTietBaoHanhDAO ctbhDAO = new ChiTietBaoHanhDAO();

    public ArrayList<ChiTietBaoHanh> getAllChiTietBaoHanh(String maBH) {
        return ctbhDAO.selectByMaBH(maBH);
    }

    public boolean add(ChiTietBaoHanh ctbh) {
        return ctbhDAO.insert(ctbh) > 0;
    }

    public boolean update(ChiTietBaoHanh ctbh) {
        return ctbhDAO.update(ctbh) > 0;
    }

    public boolean delete(String maCTBH) {
        return ctbhDAO.delete(maCTBH) > 0;
    }

    public boolean deleteAllByMaBH(String maBH) {
        return ctbhDAO.deleteAllByMaBH(maBH) >= 0;
    }
}