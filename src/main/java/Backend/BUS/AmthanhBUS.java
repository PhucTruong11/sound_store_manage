package Backend.BUS;

import java.util.ArrayList;

import Backend.DAO.AmthanhDAO;
import Backend.DTO.Amthanh;

public class AmthanhBUS {
    private AmthanhDAO laptopDAO = new AmthanhDAO();

    public ArrayList<Amthanh> getAllLaptops() {
        // Sau này có thể thêm logic ở đây (ví dụ: chỉ lấy máy giá > 0)
        return laptopDAO.getListLaptop();
    }
}
