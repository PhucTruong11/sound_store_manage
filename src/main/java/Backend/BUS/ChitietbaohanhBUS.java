package Backend.BUS;

import Backend.DAO.ChitietbaohanhDAO;
import Backend.DTO.ChiTietBaoHanh;
import java.util.ArrayList;

public class ChitietbaohanhBUS {
    private final ChitietbaohanhDAO ctbhDAO = new ChitietbaohanhDAO();

    public ArrayList<ChiTietBaoHanh> getAllChiTietBaoHanh(String maBH) {
        return ctbhDAO.getAllChiTietBaoHanh(maBH);
    }
}
