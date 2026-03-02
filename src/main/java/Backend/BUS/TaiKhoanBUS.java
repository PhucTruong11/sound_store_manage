package Backend.BUS;

import Backend.DAO.TaiKhoanDAO;
import Backend.DTO.TaiKhoan;

public class TaiKhoanBUS {

    private TaiKhoanDAO taiKhoanDAO = new TaiKhoanDAO();

    public TaiKhoan login(String username, String password) {
        if (username == null || password == null ||
                username.trim().isEmpty() || password.trim().isEmpty()) {
            return null;
        }
        return taiKhoanDAO.login(username, password);
    }
}