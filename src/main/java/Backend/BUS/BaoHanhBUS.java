package Backend.BUS;

import Backend.DAO.BaoHanhDAO;
import Backend.DTO.BaoHanh;
import java.util.ArrayList;

public class BaoHanhBUS {
    private final BaoHanhDAO bhDAO = new BaoHanhDAO();

    public ArrayList<BaoHanh> getAllBaoHanh(){
        return bhDAO.getAllBaoHanh();
    }
}
