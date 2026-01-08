package Backend;

import java.util.ArrayList;

public class LaptopBUS {
    private LaptopDAO laptopDAO = new LaptopDAO();

    public ArrayList<Laptop> getAllLaptops() {
        // Sau này có thể thêm logic ở đây (ví dụ: chỉ lấy máy giá > 0)
        return laptopDAO.getListLaptop();
    }
}
