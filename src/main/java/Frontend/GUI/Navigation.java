package Frontend.GUI;

import javax.swing.*;
import java.awt.*;
import net.miginfocom.swing.MigLayout;
import Frontend.Compoent.SearchTextField;
import Frontend.Compoent.Theme; 
import com.formdev.flatlaf.extras.FlatSVGIcon;

public class Navigation extends JPanel{
    public Navigation(String userName) {
        setBackground(Theme.PRIMARY_COLOR);
        setLayout(new MigLayout("fill, insets 0 40 0 40","[200!][grow, center][300!]", "[65!, center]"));

        setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, new Color(230, 230, 230)));

        // LOGO
        JLabel lblLogo = new JLabel("SoundWave Store");
        lblLogo.setFont(new Font("Segoe UI", Font.BOLD, 22));
        lblLogo.setForeground(Color.WHITE);
        add(lblLogo, "left, ay center");

        SearchTextField txtSearch = new SearchTextField("Tìm kiếm thứ bạn cần...");
        add(txtSearch, "center, w 450, h 38!, ay center");

        // TÀI KHOẢN 
        JPanel rightPanel = new JPanel(new MigLayout("insets 0", "[]15[]10[]", "center"));
        rightPanel.setOpaque(false);

        JLabel lblUser = new JLabel(userName);
        lblUser.setForeground(Color.WHITE);
        lblUser.setFont(new Font("Segoe UI", Font.BOLD, 15));

        JLabel lblCart = new JLabel();
        try {
            FlatSVGIcon icon = new FlatSVGIcon("images/icon/shopping-cart.svg", 20, 20);
            lblCart.setIcon(icon);
        } catch (Exception e) {
            System.err.println("Không tìm thấy icon giỏ hàng!");
        }
        lblCart.setCursor(new Cursor(Cursor.HAND_CURSOR));

        rightPanel.add(lblUser);
        rightPanel.add(lblCart);

        add(rightPanel, "right, ay center");
    }
}
