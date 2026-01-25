package Backend.DTO;

import java.util.Objects;

public class SanPham {
    private String maSP;
    private String tenSP;
    private String maLoai;
    private String maHang;
    private String hinhAnh;
    private String moTa;
    private int thoiGianBaoHanh;
    private boolean trangThai;

    public SanPham() {}

    public SanPham(String maSP, String tenSP, String maLoai, String maHang, String hinhAnh, String moTa, int thoiGianBaoHanh, boolean trangThai) {
        this.maSP = maSP;
        this.tenSP = tenSP;
        this.maLoai = maLoai;
        this.maHang = maHang;
        this.hinhAnh = hinhAnh;
        this.moTa = moTa;
        this.thoiGianBaoHanh = thoiGianBaoHanh;
        this.trangThai = trangThai;
    }

    public String getMaSP() {
        return maSP;
    }

    public void setMaSP(String maSP) {
        this.maSP = maSP;
    }

    public String getTenSP() {
        return tenSP;
    }

    public void setTenSP(String tenSP) {
        this.tenSP = tenSP;
    }

    public String getMaLoai() {
        return maLoai;
    }

    public void setMaLoai(String maLoai) {
        this.maLoai = maLoai;
    }

    public String getMaHang() {
        return maHang;
    }

    public void setMaHang(String maHang) {
        this.maHang = maHang;
    }

    public String getHinhAnh() {
        return hinhAnh;
    }

    public void setHinhAnh(String hinhAnh) {
        this.hinhAnh = hinhAnh;
    }

    public String getMoTa() {
        return moTa;
    }

    public void setMoTa(String moTa) {
        this.moTa = moTa;
    }

    public int getThoiGianBaoHanh() {
        return thoiGianBaoHanh;
    }

    public void setThoiGianBaoHanh(int thoiGianBaoHanh) {
        this.thoiGianBaoHanh = thoiGianBaoHanh;
    }

    public boolean isTrangThai() {
        return trangThai;
    }

    public void setTrangThai(boolean trangThai) {
        this.trangThai = trangThai;
    }

    @Override
    public boolean equals(Object obj) {
        if(this == obj) return true;
        if(obj == null || getClass() != obj.getClass()) return false;
        SanPham that = (SanPham) obj;
        return Objects.equals(maSP, that.maSP);
    }

    @Override
    public String toString() {
        return "SanPham [maSP=" + maSP + ", tenSP=" + tenSP + ", maLoai=" + maLoai + ", maHang=" + maHang + ", hinhAnh="
                + hinhAnh + ", moTa=" + moTa + ", thoiGianBaoHanh=" + thoiGianBaoHanh + ", trangThai=" + trangThai
                + "]";
    }

    
}
