package Backend.BUS;

import java.util.ArrayList;

import Backend.DAO.DoiTraDAO;
import Backend.DTO.DoiTra;

public class DoiTraBUS {

    private final DoiTraDAO dtDAO = new DoiTraDAO();

    public ArrayList<DoiTra> getAll() {
        return dtDAO.selectAll();
    }

    public boolean add(DoiTra dt) {
        return dtDAO.insert(dt) > 0;
    }

    public boolean update(DoiTra dt) {
        return dtDAO.update(dt) > 0;
    }

    public boolean delete(String ma) {
        return dtDAO.delete(ma) > 0;
    }

    public String generateMaDoiTra() {

        ArrayList<DoiTra> list = dtDAO.selectAll();

        int max = 0;

        for (DoiTra dt : list) {

            int num = Integer.parseInt(dt.getMaDoiTra().substring(2));

            if (num > max) max = num;
        }

        return String.format("DT%03d", max + 1);
    }

    public String validate(DoiTra dt, boolean isAdd) {

        if (dt.getMaPhieuXuat().isEmpty())
            return "Mã phiếu xuất không được rỗng";

        if (dt.getMaKH().isEmpty())
            return "Mã khách hàng không được rỗng";

        if (dt.getSoLuong() <= 0)
            return "Số lượng phải lớn hơn 0";

        if (dt.getLyDo() == null || dt.getLyDo().trim().isEmpty())
            return "Lý do đổi trả không được rỗng";

        return "OK";
    }
    public ArrayList<DoiTra> search(String keyword) {
        ArrayList<DoiTra> result = new ArrayList<>();
        if (keyword == null || keyword.trim().isEmpty()) {
            return getAll();
        }

        String[] words = keyword.toLowerCase().split("\\s+");

        for (DoiTra dt : getAll()) {
            String fullInfo = (dt.getMaDoiTra() + " " + 
                            dt.getTenKH() + " " + 
                            dt.getTenSP() + " " + 
                            dt.getMaPhieuXuat() + " " +
                            dt.getLyDo()).toLowerCase();
            boolean matchesAll = true;
            for (String word : words) {
                if (!fullInfo.contains(word)) {
                    matchesAll = false;
                    break;
                }
            }

            if (matchesAll) {
                result.add(dt);
            }
        }
        return result;
    }
}