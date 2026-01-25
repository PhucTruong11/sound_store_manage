package Backend.DTO;

import java.time.LocalDate;
import java.util.Objects;

public class DoiTra {
    private String maDoiTra;
    private String maPhieuXuat;
    private String maKH;
    private String maPhienBan;
    private LocalDate ngayDoiTra;
    private int soLuong;
    private String lyDo;
    private String tinhTrang;

    public DoiTra() {
    }

    public DoiTra(String maDoiTra, String maPhieuXuat, String maKH, String maPhienBan, LocalDate ngayDoiTra, int soLuong, String lyDo, String tinhTrang) {
        this.maDoiTra = maDoiTra;
        this.maPhieuXuat = maPhieuXuat;
        this.maKH = maKH;
        this.maPhienBan = maPhienBan;
        this.ngayDoiTra = ngayDoiTra;
        this.soLuong = soLuong;
        this.lyDo = lyDo;
        this.tinhTrang = tinhTrang;
    }

    public String getMaDoiTra() { return maDoiTra; }
    public void setMaDoiTra(String maDoiTra) { this.maDoiTra = maDoiTra; }

    public String getMaPhieuXuat() { return maPhieuXuat; }
    public void setMaPhieuXuat(String maPhieuXuat) { this.maPhieuXuat = maPhieuXuat; }

    public String getMaKH() { return maKH; }
    public void setMaKH(String maKH) { this.maKH = maKH; }

    public String getMaPhienBan() { return maPhienBan; }
    public void setMaPhienBan(String maPhienBan) { this.maPhienBan = maPhienBan; }

    public LocalDate getNgayDoiTra() { return ngayDoiTra; }
    public void setNgayDoiTra(LocalDate ngayDoiTra) { this.ngayDoiTra = ngayDoiTra; }

    public int getSoLuong() { return soLuong; }
    public void setSoLuong(int soLuong) { this.soLuong = soLuong; }

    public String getLyDo() { return lyDo; }
    public void setLyDo(String lyDo) { this.lyDo = lyDo; }

    public String getTinhTrang() { return tinhTrang; }
    public void setTinhTrang(String tinhTrang) { this.tinhTrang = tinhTrang; }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        DoiTra that = (DoiTra) obj;
        return Objects.equals(maDoiTra, that.maDoiTra);
    }

    @Override
    public String toString() {
        return "DoiTra{" +
                "maDoiTra='" + maDoiTra + '\'' +
                ", maPhieuXuat='" + maPhieuXuat + '\'' +
                ", maKH='" + maKH + '\'' +
                ", maPhienBan='" + maPhienBan + '\'' +
                ", ngayDoiTra=" + ngayDoiTra +
                ", soLuong=" + soLuong +
                ", lyDo='" + lyDo + '\'' +
                ", tinhTrang='" + tinhTrang + '\'' +
                '}';
    }
}
