package Backend.DTO;

public class TaiKhoan {

    private int id;
    private String username;
    private String passwordHash;
    private String role;
    private int status; // 0 means unavailable, 1 means active
    private String conNguoiId;

    public TaiKhoan() {
    }

    public TaiKhoan(int id, String username, String passwordHash,
                    String role, int status, String conNguoiId) {
        this.id = id;
        this.username = username;
        this.passwordHash = passwordHash;
        this.role = role;
        this.status = status;
        this.conNguoiId = conNguoiId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPasswordHash() {
        return passwordHash;
    }

    public void setPasswordHash(String passwordHash) {
        this.passwordHash = passwordHash;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getConNguoiId() {
        return conNguoiId;
    }

    public void setConNguoiId(String conNguoiId) {
        this.conNguoiId = conNguoiId;
    }
}

