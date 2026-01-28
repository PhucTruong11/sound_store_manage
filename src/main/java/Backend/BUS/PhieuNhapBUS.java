package Backend.BUS;

import Backend.DAO.PhieuNhapDAO;
import Backend.DTO.PhieuNhap;
import java.util.ArrayList;

public class PhieuNhapBUS {
    private final PhieuNhapDAO phieuNhapDAO = new PhieuNhapDAO();

    public ArrayList<PhieuNhap> getAllPhieuNhap() {
        return phieuNhapDAO.selectAll();
    }
}
