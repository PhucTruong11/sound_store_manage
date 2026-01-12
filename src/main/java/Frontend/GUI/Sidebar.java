package Frontend.GUI;

import javax.swing.*;
import java.awt.*;
import net.miginfocom.swing.MigLayout;
import Frontend.Compoent.Theme;

public class Sidebar extends JPanel{
    private MainFrame parent;

    public Sidebar(MainFrame parent) {
        this.parent = parent;
        setBackground(Theme.SECONDARY_COLOR);
        setLayout(new MigLayout("wrap 1, fillx, insets 20", "[fill]", "[]20[]"));

        String[] menuItems = {"Sản phẩm", "Bán hàng", "Nhập hàng", "Hóa đơn", "Phiếu nhập", "Khuyến mãi","Nhà cung cấp", "Nhân viên", "Khách hàng"};
        for(String item : menuItems)
            add(createMenubtn(item));

        JButton btnLogout = createMenubtn("Đăng xuất");
        btnLogout.setBackground(Theme.DANGER_COLOR);
        add(btnLogout, "pushy, aligny bottom");
    }

    private JButton createMenubtn(String text) {
        JButton btn = new JButton(text);
        btn.setFont(new Font("Sagoe UI", Font.PLAIN, 15));
        btn.setForeground(Color.WHITE);
        btn.setBackground(Theme.SECONDARY_COLOR);
        btn.setBorderPainted(false);
        btn.setFocusPainted(false);
        btn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btn.setHorizontalAlignment(SwingConstants.CENTER);

        btn.addActionListener(e -> {
            if(text.equals("Sản phẩm"))
                parent.setPage(new QuanlyamthanhPanel());
            else
                JOptionPane.showMessageDialog(parent, "Chức năng " + text + " đang phát triển!");
        });
        return btn;
    }
}
