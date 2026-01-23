package Backend.DTO;

public class Amthanh {
    private String maSP;
    private String tenSP;
    private double giaBan;
    private int soLuong;

    public Amthanh(String maSP, String tenSP, double giaBan,int soLuong) {
        this.maSP = maSP;
        this.tenSP = tenSP;
        this.giaBan = giaBan;
        this.soLuong=soLuong;
    }

    public String getMaSP() { return maSP; }
    public String getTenSP() { return tenSP; }
    public double getGiaBan() { return giaBan; }
    public int getSoLuong(){return soLuong;}

    public void setMaSP(String maSP) {
        this.maSP = maSP;
    }

    public void setTenSP(String tenSP) {
        this.tenSP = tenSP;
    }

    public void setGiaBan(double giaBan) {
        this.giaBan = giaBan;
    }
    public void setSoLuong(int soLuong){
        this.soLuong=soLuong;
    }

    
}
