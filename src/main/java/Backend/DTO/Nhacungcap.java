package Backend.DTO;

public class Nhacungcap {
    private String MaNCC;
    private String TenNCC;
    private String DiaChi;
    private String SDT;
    public Nhacungcap(String maNCC, String tenNCC, String diaChi, String sDT) {
        this.MaNCC = maNCC;
        this.TenNCC = tenNCC;
        this.DiaChi = diaChi;
        this.SDT = sDT;
    }
    public String getMaNCC() {
        return MaNCC;
    }
    public void setMaNCC(String maNCC) {
        MaNCC = maNCC;
    }
    public String getTenNCC() {
        return TenNCC;
    }
    public void setTenNCC(String tenNCC) {
        TenNCC = tenNCC;
    }
    public String getDiaChi() {
        return DiaChi;
    }
    public void setDiaChi(String diaChi) {
        DiaChi = diaChi;
    }
    public String getSDT() {
        return SDT;
    }
    public void setSDT(String sDT) {
        SDT = sDT;
    }

    
}
