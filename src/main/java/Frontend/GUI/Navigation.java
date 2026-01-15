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

        // NAME
        JLabel lblLogo = new JLabel("SoundWave Store");
        lblLogo.setFont(new Font("Segoe UI", Font.BOLD, 22));
        lblLogo.setForeground(Color.WHITE);
        add(lblLogo, "left, ay center");

        //SEARCH
        JLabel lblCart = new JLabel();
        lblCart.setIcon(new FlatSVGIcon("images/icon/shopping-cart.svg", 22, 22));
        SearchTextField txtSearch = new SearchTextField("Tìm kiếm thứ bạn cần...");
        add(txtSearch, "center, w 450, h 38!, ay center");

        JPanel rightPanel = new JPanel(new MigLayout("insets 0, gapx 20", "[] []", "center"));
        rightPanel.setOpaque(false);

        // BASKET
        JLabel lblBasket = new JLabel();
        lblBasket.setIcon(new FlatSVGIcon("images/icon/shopping-cart.svg", 22, 22));
        lblCart.setCursor(new Cursor(Cursor.HAND_CURSOR));
        rightPanel.add(lblCart, "ay top, gaptop 2");

        //USER
        JPanel userBox = new JPanel(new MigLayout("insets 0, wrap 1, gapy 0", "[center]", "[]"));
        userBox.setOpaque(false);
        userBox.setCursor(new Cursor(Cursor.HAND_CURSOR));
        
        JLabel userIconLabel = new JLabel(new FlatSVGIcon("images/icon/circle-user-round.svg", 22, 22));

        JLabel lblUser = new JLabel(userName);
        lblUser.setForeground(Color.WHITE);
        lblUser.setFont(new Font("Segoe UI", Font.BOLD, 11)); // Cỡ chữ 11 nhỏ gọn

        userBox.add(userIconLabel);
        userBox.add(lblUser);

        rightPanel.add(userBox);

        add(rightPanel, "right");
    }
}
