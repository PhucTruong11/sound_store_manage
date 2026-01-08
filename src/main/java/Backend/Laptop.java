package Backend;

public class Laptop {
    private String maMay;
    private String tenMay;
    private double giaBan;

    public Laptop(String maMay, String tenMay, double giaBan) {
        this.maMay = maMay;
        this.tenMay = tenMay;
        this.giaBan = giaBan;
    }

    // Getter (để lấy dữ liệu ra)
    public String getMaMay() { return maMay; }
    public String getTenMay() { return tenMay; }
    public double getGiaBan() { return giaBan; }
}
