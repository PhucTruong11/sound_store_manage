package Frontend.GUI.ThongKe;

import javax.swing.*;
import java.awt.*;
import com.formdev.flatlaf.extras.FlatSVGIcon;
import Frontend.Compoent.Theme;
import net.miginfocom.swing.MigLayout;

public class KpiCardsPanel extends JPanel {

    public KpiCardsPanel() {
        // 4 cột đều nhau cho 4 thẻ [cite: 2025-12-25]
        setLayout(new MigLayout("fill, insets 0", "[grow]15[grow]15[grow]15[grow]", "fill"));
        setOpaque(false);
        initComponents();
    }

    private void initComponents() {
        add(createKpiCard("Doanh Thu Ngày", "+15.2%", "5.400.000", "trending-up.svg", new Color(46, 204, 113)));
        add(createKpiCard("Đơn Hàng Mới", "+5", "24", "shopping-cart.svg", new Color(52, 152, 219)));
        add(createKpiCard("Vốn Nhập Kho", "-2.1%", "12.800.000", "shield-plus.svg", new Color(231, 76, 60)));
        add(createKpiCard("Lợi Nhuận Gộp", "+8.4%", "1.250.000", "pie-chart.svg", new Color(155, 89, 182)));
    }

    private JPanel createKpiCard(String title, String trend, String value, String iconName, Color color) {
        JPanel card = new JPanel(new MigLayout("insets 15", "[][grow]", "[]2[]"));
        card.setBackground(Color.WHITE);
        card.putClientProperty("FlatLaf.style", "arc: 20"); // Bo góc đồng bộ dự án [cite: 2025-12-25]

        FlatSVGIcon icon = new FlatSVGIcon("images/icon/" + iconName, 30, 30);
        icon.setColorFilter(new FlatSVGIcon.ColorFilter(c -> color));
        
        JLabel lblTitle = new JLabel(title);
        lblTitle.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        lblTitle.setForeground(new Color(149, 165, 166));

        JLabel lblTrend = new JLabel(trend);
        lblTrend.setFont(new Font("Segoe UI", Font.BOLD, 12));
        lblTrend.setForeground(color);

        JLabel lblValue = new JLabel(value);
        lblValue.setFont(new Font("Segoe UI", Font.BOLD, 22));

        card.add(new JLabel(icon), "spany 2, gapright 10");
        card.add(lblTitle, "split 2");
        card.add(lblTrend, "wrap");
        card.add(lblValue);

        return card;
    }
}