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

    // public String add(DoiTra dt) {
    //     String validateMsg = validate(dt);
    //     if (!validateMsg.equals("OK")) {
    //         return validateMsg;
    //     }
    //     int result = dtDAO.insert(dt);
    //     if (result > 0) {
    //         boolean isUpdatedStock = dtDAO.updateImeiReturn(dt.getMaImei());
    //         if (isUpdatedStock) {
    //             return "OK";
    //         } else {
    //             return "Phiếu đã tạo nhưng không thể cập nhật trạng thái kho!";
    //         }
    //     }
    //     return "Lỗi hệ thống: Không thể thêm phiếu đổi trả.";
    // }

    public String add(DoiTra dt, String imeiMoi) {
        String validateMsg = validate(dt);
        if (!validateMsg.equals("OK")) return validateMsg;

        // Validate IMEI cũ (máy trả) có thuộc phiếu xuất này không
        if (!dtDAO.checkImeiInvoice(dt.getMaImei(), dt.getMaPhieuXuat())) {
            return "Lỗi: IMEI máy trả không thuộc phiếu xuất đã chọn!";
        }

        // Nếu có IMEI mới, kiểm tra nó có trong kho không
        if (imeiMoi != null && !imeiMoi.trim().isEmpty()) {
            if (!dtDAO.checkImeiInStock(imeiMoi)) {
                return "Lỗi: IMEI máy thay thế không có sẵn trong kho!";
            }
        }

        int result = dtDAO.insert(dt); 
        
        if (result > 0) {
            // Thu hồi máy cũ (Cập nhật trạng thái 'Lỗi' và gỡ khỏi phiếu xuất)
            boolean okOld = dtDAO.updateOldImeiStatus(dt.getMaImei(), "Máy lỗi đổi trả");
            
            if (okOld) {
                // Nếu có IMEI mới, giao cho khách
                if (imeiMoi != null && !imeiMoi.trim().isEmpty()) {
                    boolean okNew = dtDAO.assignNewImeiToInvoice(imeiMoi, dt.getMaPhieuXuat());
                    if (okNew) {
                        return "OK";
                    } else {
                        return "Phiếu đã tạo nhưng gặp lỗi khi gán IMEI máy mới!";
                    }
                } else {
                    // Không có IMEI mới - chỉ thu hồi máy lỗi
                    return "OK";
                }
            } else {
                return "Phiếu đã tạo nhưng không thể cập nhật trạng thái máy cũ!";
            }
        }
        return "Lỗi hệ thống: Không thể thêm phiếu đổi trả.";
    }

    public boolean delete(String maDT) {
        // Get IMEI before delete to restore it
        String imei = dtDAO.getImeiByMaDoiTra(maDT);
        
        boolean isDeleted = dtDAO.softDelete(maDT) > 0;
        
        if (isDeleted && imei != null) {
            // Restore IMEI status from "Máy lỗi đổi trả" back to "Đã bán"
            dtDAO.restoreImeiStatus(imei);
        }
        
        return isDeleted;
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

        // Check IMEI belongs to invoice
        if (!dtDAO.checkImeiInvoice(dt.getMaImei(), dt.getMaPhieuXuat())) {
            return "Lỗi: IMEI không thuộc phiếu xuất đã chọn!";
        }

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