package Backend.DTO;
import java.util.Date;
import Backend.DTO.Nhacungcap;

public class Hoadonnhaphang {
    private String MaHDN;
    private Date NgayNhap;
    private String MaNV;
    private String MaNCC;
    private double TongTien;
    private Nhacungcap nhacungcap;
    
    public Hoadonnhaphang(String maHDN, Date ngayNhap, String maNV, String maNCC, double tongTien) {
        this.MaHDN = maHDN;
        this.NgayNhap = ngayNhap;
        this.MaNV = maNV;
        this.MaNCC = maNCC;
        this.TongTien = tongTien;
    }
    
    public Hoadonnhaphang(String maHDN, Date ngayNhap, String maNV, String maNCC, double tongTien, Nhacungcap nhacungcap) {
        this.MaHDN = maHDN;
        this.NgayNhap = ngayNhap;
        this.MaNV = maNV;
        this.MaNCC = maNCC;
        this.TongTien = tongTien;
        this.nhacungcap = nhacungcap;
    }

    public String getMaHDN() {
        return MaHDN;
    }
    public void setMaHDN(String maHDN) {
        MaHDN = maHDN;
    }
    public Date getNgayNhap() {
        return NgayNhap;
    }
    public void setNgayNhap(Date ngayNhap) {
        NgayNhap = ngayNhap;
    }
    public String getMaNV() {
        return MaNV;
    }
    public void setMaNV(String maNV) {
        MaNV = maNV;
    }
    public String getMaNCC() {
        return MaNCC;
    }
    public void setMaNCC(String maNCC) {
        MaNCC = maNCC;
    }
    public double getTongTien() {
        return TongTien;
    }
    public void setTongTien(double tongTien) {
        TongTien = tongTien;
    }
    public Nhacungcap getNhacungcap() {
        return nhacungcap;
    }
    public void setNhacungcap(Nhacungcap nhacungcap) {
        this.nhacungcap = nhacungcap;
    }
}
