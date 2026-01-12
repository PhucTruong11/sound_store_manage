package Backend.DTO;

public class Amthanh {
    private String maMay;
    private String tenMay;
    private double giaBan;

    public Amthanh(String maMay, String tenMay, double giaBan) {
        this.maMay = maMay;
        this.tenMay = tenMay;
        this.giaBan = giaBan;
    }

    public String getMaMay() { return maMay; }
    public String getTenMay() { return tenMay; }
    public double getGiaBan() { return giaBan; }
}
