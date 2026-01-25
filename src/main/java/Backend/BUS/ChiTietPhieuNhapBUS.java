package Backend.BUS;

import Backend.DAO.ChiTietPhieuNhapDAO;
import Backend.DTO.ChiTietPhieuNhap;
import java.util.ArrayList;

public class ChiTietPhieuNhapBUS {
    private final ChiTietPhieuNhapDAO ctDAO = new ChiTietPhieuNhapDAO();

    public ArrayList<ChiTietPhieuNhap> getByMaPhieu(String maPN) {
        return ctDAO.getByMaPhieu(maPN);
    }
}
