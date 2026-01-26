package Backend.BUS;

import Backend.DAO.BaohanhDAO;
import Backend.DAO.HoadonbanhangDAO;
import Backend.DTO.BaoHanh;
import Backend.DTO.HoaDonBanHang;
import java.util.ArrayList;

public class HoadonbanhangBUS {
    private final HoadonbanhangDAO hdbhDAO = new HoadonbanhangDAO();

    public ArrayList<HoaDonBanHang> getAllHoaDonBanHang() {
        return hdbhDAO.getAllHoaDonBanHang();
    }
}
