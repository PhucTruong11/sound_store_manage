package Backend.BUS;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import Backend.DAO.DoiTraDAO;
import Backend.DTO.DoiTra;

public class DoiTraBUS {
    private final DoiTraDAO dtDAO = new DoiTraDAO();

    public ArrayList<DoiTra> getAll() {
        return dtDAO.selectAll();
    }

    public String generateMaDoiTra() {
        ArrayList<DoiTra> list = dtDAO.selectAll();
        int max = 0;
        for (DoiTra dt : list) {
            try {
                int num = Integer.parseInt(dt.getMaDoiTra().substring(2));
                if (num > max) max = num;
            } catch (Exception e) {
            }
        }
        return String.format("DT%03d", max + 1);
    }

    public String add(DoiTra dt) {
        String validateMsg = validate(dt);
        if (!validateMsg.equals("OK")) {
            return validateMsg;
        }
        int result = dtDAO.insert(dt);
        if (result > 0) {
            boolean isUpdatedStock = dtDAO.updateImeiReturn(dt.getMaImei());
            if (isUpdatedStock) {
                return "OK";
            } else {
                return "Phiếu đã tạo nhưng không thể cập nhật trạng thái kho!";
            }
        }
        return "Lỗi hệ thống: Không thể thêm phiếu đổi trả.";
    }
    public boolean delete(String maDT) {
        return dtDAO.softDelete(maDT) > 0;
    }

    public String update(DoiTra dt) {
        String validateMsg = validate(dt);
        if (!validateMsg.equals("OK")) {
            return validateMsg;
        }
        
        return dtDAO.update(dt) > 0 ? "OK" : "Cập nhật thất bại!";
    }

    public String validate(DoiTra dt) {
        if (dt.getMaPhieuXuat() == null || dt.getMaPhieuXuat().isEmpty()) 
            return "Chưa chọn phiếu xuất!";
        if (dt.getMaImei() == null || dt.getMaImei().isEmpty()) 
            return "Chưa chọn mã IMEI sản phẩm!";
        if (dt.getLyDo() == null || dt.getLyDo().trim().isEmpty()) 
            return "Lý do đổi trả không được để trống!";

        LocalDate ngayMua = dtDAO.getNgayMuaByMaPX(dt.getMaPhieuXuat());
        if (ngayMua == null) return "Không tìm thấy thông tin ngày mua của phiếu xuất này!";

        long soNgay = ChronoUnit.DAYS.between(ngayMua, dt.getNgayDoiTra());

        if (soNgay < 0) {
            return "Lỗi: Ngày đổi trả không được trước ngày mua hàng!";
        }
        if (soNgay > 30) {
            return "Lỗi: Đã quá hạn đổi trả (Quá 30 ngày kể từ ngày mua).";
        }

        return "OK";
    }

    public ArrayList<DoiTra> search(String keyword) {
        ArrayList<DoiTra> all = getAll();
        if (keyword == null || keyword.trim().isEmpty()) {
            return all;
        }

        String[] keywords = keyword.toLowerCase().trim().split("\\s+");
        ArrayList<DoiTra> result = new ArrayList<>();

        for (DoiTra dt : all) {
            String searchTarget = (dt.getMaDoiTra() + " " + 
                                dt.getMaPhieuXuat() + " " + 
                                dt.getMaImei() + " " + 
                                dt.getTenKH() + " " + 
                                dt.getLyDo()).toLowerCase();

            boolean matchesAll = true;
            for (String k : keywords) {
                if (!searchTarget.contains(k)) {
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