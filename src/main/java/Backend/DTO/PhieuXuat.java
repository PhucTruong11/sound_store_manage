package Backend.DTO;

import java.sql.Timestamp;
import java.util.Objects;

public class PhieuXuat {
    private String maHDBH;
    private String maNV;
    private String maKH;
    private String maKM;
    private Timestamp ngayBan;
    private double tongTien;
    private String trangThai;

    public PhieuXuat() {
    }

    public PhieuXuat(String maHDBH, String maNV, String maKH, String maKM, Timestamp ngayBan, double tongTien,
            String trangThai) {
        this.maHDBH = maHDBH;
        this.maNV = maNV;
        this.maKH = maKH;
        this.maKM = maKM;
        this.ngayBan = ngayBan;
        this.tongTien = tongTien;
        this.trangThai = trangThai;
    }

    public String getMaHDBH() {
        return maHDBH;
    }

    public void setMaHDBH(String maHDBH) {
        this.maHDBH = maHDBH;
    }

    public String getMaNV() {
        return maNV;
    }

    public void setMaNV(String maNV) {
        this.maNV = maNV;
    }

    public String getMaKH() {
        return maKH;
    }

    public void setMaKH(String maKH) {
        this.maKH = maKH;
    }

    public String getMaKM() {
        return maKM;
    }

    public void setMaKM(String maKM) {
        this.maKM = maKM;
    }

    public Timestamp getNgayBan() {
        return ngayBan;
    }

    public void setNgayBan(Timestamp ngayBan) {
        this.ngayBan = ngayBan;
    }

    public double getTongTien() {
        return tongTien;
    }

    public void setTongTien(double tongTien) {
        this.tongTien = tongTien;
    }

    public String getTrangThai() {
        return trangThai;
    }

    public void setTrangThai(String trangThai) {
        this.trangThai = trangThai;
    }

    @Override
    public int hashCode() {
        return Objects.hash(maHDBH);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null || getClass() != obj.getClass())
            return false;

        final PhieuXuat other = (PhieuXuat) obj;

        return Objects.equals(this.maHDBH, other.maHDBH);
    }

    @Override
    public String toString() {
        return "PhieuXuat{" + "maHDBH=" + maHDBH + ", tongTien=" + tongTien + ", trangThai=" + trangThai + '}';
    }
}