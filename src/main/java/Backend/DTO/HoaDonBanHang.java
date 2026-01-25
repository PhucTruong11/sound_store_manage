package Backend.DTO;

import java.time.LocalDate;
import java.util.Objects;

public class HoaDonBanHang {
    private String maHD;
    private String maNV;
    private String maKH;
    private LocalDate ngayLap;
    private double tongTien;
    private int tinhTrang; 

    public HoaDonBanHang() {
    }

    public HoaDonBanHang(String maHD, String maNV, String maKH, LocalDate ngayLap, double tongTien, int tinhTrang) {
        this.maHD = maHD;
        this.maNV = maNV;
        this.maKH = maKH;
        this.ngayLap = ngayLap;
        this.tongTien = tongTien;
        this.tinhTrang = tinhTrang;
    }

    public String getMaHD() { return maHD; }
    public void setMaHD(String maHD) { this.maHD = maHD; }

    public String getMaNV() { return maNV; }
    public void setMaNV(String maNV) { this.maNV = maNV; }

    public String getMaKH() { return maKH; }
    public void setMaKH(String maKH) { this.maKH = maKH; }

    public LocalDate getNgayLap() { return ngayLap; }
    public void setNgayLap(LocalDate ngayLap) { this.ngayLap = ngayLap; }

    public double getTongTien() { return tongTien; }
    public void setTongTien(double tongTien) { this.tongTien = tongTien; }

    public int getTinhTrang() { return tinhTrang; }
    public void setTinhTrang(int tinhTrang) { this.tinhTrang = tinhTrang; }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        HoaDonBanHang that = (HoaDonBanHang) obj;
        return Objects.equals(maHD, that.maHD);
    }

    @Override
    public String toString() {
        return "HoaDonBanHang{" +
                "maHD='" + maHD + '\'' +
                ", maNV='" + maNV + '\'' +
                ", maKH='" + maKH + '\'' +
                ", ngayLap=" + ngayLap +
                ", tongTien=" + tongTien +
                ", tinhTrang=" + tinhTrang +
                '}';
    }
}