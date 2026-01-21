package Backend.BUS;

import Backend.DAO.TaiKhoanDAO;
import Backend.DTO.TaiKhoan;

public class TaiKhoanBUS {

    private TaiKhoanDAO taiKhoanDAO = new TaiKhoanDAO();

    public TaiKhoan login(String username, String password) {

        // 1. Kiem tra du lieu rong
        if (username == null || password == null ||
                username.trim().isEmpty() || password.trim().isEmpty()) {
            return null;
        }

        // 2. Ma hoa mat khau
        String passwordHash = HashUtil.sha256(password);

        // 3. Goi DAO
        return taiKhoanDAO.login(username, passwordHash);
    }
}
