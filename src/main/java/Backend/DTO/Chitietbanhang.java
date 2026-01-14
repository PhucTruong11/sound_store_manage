package Backend.DTO;

public class Chitietbanhang {
    private String maHDBH;
    private String maSP;
    private int soLuong;
    private double donGia;
    private double thanhTien;

    public Chitietbanhang() {
    }

    public Chitietbanhang(String maHDBH, String maSP, int soLuong, double donGia, double thanhTien) {
        this.maHDBH = maHDBH;
        this.maSP = maSP;
        this.soLuong = soLuong;
        this.donGia = donGia;
        this.thanhTien = thanhTien;
    }

    public String getMaHDBH(){ return maHDBH;}
    public void setMaHDBH(String maHDBH){ this.maHDBH = maHDBH; }
    
    public String getMaSP(){ return maSP;}
    public void setMaSP(String maSP){ this.maSP = maSP; }
    
    public int getSoLuong(){ return soLuong;}
    public void setSoLuong(int soLuong){ this.soLuong = soLuong; }
    
    public double getDonGia(){ return donGia;}
    public void setDonGia(double donGia){ this.donGia = donGia; }
    
    public double getThanhTien(){ return thanhTien;}
    public void setThanhTien(double thanhTien){ this.thanhTien = thanhTien; }


}
