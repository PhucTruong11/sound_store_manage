package Backend.DTO;

import java.util.Objects;

public class ChiTietPhieuXuat {
    private String maPhieuXuat;
    private String maPhienBan;
    private int soLuong;
    private double donGia;
    private double thanhTien;

    public ChiTietPhieuXuat() {
    }

    public ChiTietPhieuXuat(String maPhieuXuat, String maPhienBan, int soLuong, double donGia, double thanhTien) {
        this.maPhieuXuat = maPhieuXuat;
        this.maPhienBan = maPhienBan;
        this.soLuong = soLuong;
        this.donGia = donGia;
        this.thanhTien = thanhTien;
    }

    public String getMaPhieuXuat() { return maPhieuXuat; }
    public void setMaPhieuXuat(String maPhieuXuat) { this.maPhieuXuat = maPhieuXuat; }

    public String getMaPhienBan() { return maPhienBan; }
    public void setMaPhienBan(String maPhienBan) { this.maPhienBan = maPhienBan; }

    public int getSoLuong() { return soLuong; }
    public void setSoLuong(int soLuong) { this.soLuong = soLuong; }

    public double getDonGia() { return donGia; }
    public void setDonGia(double donGia) { this.donGia = donGia; }

    public double getThanhTien() { return thanhTien; }
    public void setThanhTien(double thanhTien) { this.thanhTien = thanhTien; }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        ChiTietPhieuXuat that = (ChiTietPhieuXuat) obj;
        return Objects.equals(maPhieuXuat, that.maPhieuXuat) &&
               Objects.equals(maPhienBan, that.maPhienBan);
    }

    @Override
    public String toString() {
        return "ChiTietPhieuXuat{" +
                "maPhieuXuat='" + maPhieuXuat + '\'' +
                ", maPhienBan='" + maPhienBan + '\'' +
                ", soLuong=" + soLuong +
                ", donGia=" + donGia +
                ", thanhTien=" + thanhTien +
                '}';
    }
}
