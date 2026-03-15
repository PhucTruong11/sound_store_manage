package Backend.DTO;

import java.time.LocalDate;

public class DoiTra {

    private String maDoiTra;
    private String maPhieuXuat;
    private String maKH;
    private String tenKH;
    private String maPhienBan;
    private String tenSP;
    private LocalDate ngayDoiTra;
    private int soLuong;
    private String lyDo;
    private String tinhTrang;

    public DoiTra() {}

    public DoiTra(String maDoiTra, String maPhieuXuat, String maKH,
                  String maPhienBan, LocalDate ngayDoiTra,
                  int soLuong, String lyDo, String tinhTrang) {

        this.maDoiTra = maDoiTra;
        this.maPhieuXuat = maPhieuXuat;
        this.maKH = maKH;
        this.maPhienBan = maPhienBan;
        this.ngayDoiTra = ngayDoiTra;
        this.soLuong = soLuong;
        this.lyDo = lyDo;
        this.tinhTrang = tinhTrang;
    }

    public LocalDate getNgayHetHan() {
        if (ngayDoiTra == null) return null;
        return ngayDoiTra.plusDays(30);
    }
    public String getTrangThaiThoiHan() {
        if (getNgayHetHan() == null) return "Không xác định";
        if (LocalDate.now().isAfter(getNgayHetHan())) {
            return "Đã hết hạn đổi trả";
        }
        return "Còn thời hạn";
    }

    public String getMaDoiTra() { return maDoiTra; }
    public void setMaDoiTra(String maDoiTra) { this.maDoiTra = maDoiTra; }

    public String getMaPhieuXuat() { return maPhieuXuat; }
    public void setMaPhieuXuat(String maPhieuXuat) { this.maPhieuXuat = maPhieuXuat; }

    public String getMaKH() { return maKH; }
    public void setMaKH(String maKH) { this.maKH = maKH; }

    public String getTenKH() { return tenKH; }
    public void setTenKH(String tenKH) { this.tenKH = tenKH; }

    public String getMaPhienBan() { return maPhienBan; }
    public void setMaPhienBan(String maPhienBan) { this.maPhienBan = maPhienBan; }

    public String getTenSP() { return tenSP; }
    public void setTenSP(String tenSP) { this.tenSP = tenSP; }

    public LocalDate getNgayDoiTra() { return ngayDoiTra; }
    public void setNgayDoiTra(LocalDate ngayDoiTra) { this.ngayDoiTra = ngayDoiTra; }

    public int getSoLuong() { return soLuong; }
    public void setSoLuong(int soLuong) { this.soLuong = soLuong; }

    public String getLyDo() { return lyDo; }
    public void setLyDo(String lyDo) { this.lyDo = lyDo; }

    public String getTinhTrang() { return tinhTrang; }
    public void setTinhTrang(String tinhTrang) { this.tinhTrang = tinhTrang; }
}