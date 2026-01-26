package Backend.BUS;

import Backend.DAO.BaohanhDAO;
import Backend.DTO.BaoHanh;
import java.util.ArrayList;

public class BaohanhBUS {
    private final BaohanhDAO bhDAO = new BaohanhDAO();

    public ArrayList<BaoHanh> getAllBaoHanh(){
        return bhDAO.getAllBaoHanh();
    }
}
