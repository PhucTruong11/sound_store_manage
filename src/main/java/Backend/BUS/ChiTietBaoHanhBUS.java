package Backend.BUS;

import Backend.DAO.ChiTietBaoHanhDAO;
import Backend.DTO.ChiTietBaoHanh;
import java.util.ArrayList;

public class ChiTietBaoHanhBUS {
    private final ChiTietBaoHanhDAO ctbhDAO = new ChiTietBaoHanhDAO();

    public ArrayList<ChiTietBaoHanh> getAllChiTietBaoHanh(String maBH) {
        return ctbhDAO.getAllChiTietBaoHanh(maBH);
    }
}
