package Backend.DTO;

import java.util.Date; // Hoặc String tùy vào cách bạn lưu database

public class KhuyenMai {
    private String maKM;
    private String tenKM;
    private int phanTramGiam;
    private String ngayBD; // Nếu bạn dùng kiểu String trong DB
    private String ngayKT;

    // ... Constructor không đối số và có đối số ...

    public String getMaKM() { return maKM; }
    public String getTenKM() { return tenKM; }
    public int getPhanTramGiam() { return phanTramGiam; }
    
    // ĐẢM BẢO TÊN HÀM GIỐNG HỆT NHƯ NÀY
    public String getNgayBD() { return ngayBD; }
    public String getNgayKT() { return ngayKT; }
    
    // ... Setter tương ứng ...
}
