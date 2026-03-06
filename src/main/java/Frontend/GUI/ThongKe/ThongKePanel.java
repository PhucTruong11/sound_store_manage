package Frontend.GUI.ThongKe;

import javax.swing.*;
import Frontend.Compoent.Theme;
import net.miginfocom.swing.MigLayout;

public class ThongKePanel extends JPanel {

    public ThongKePanel() {
        // Layout: Cards -> Product Charts -> Trend Chart [cite: 2025-12-25]
        setLayout(new MigLayout("fill, insets 20", "[grow, fill]", "[]20[350!]20[grow]"));
        setBackground(Theme.BACKGROUND_COLOR); // Sử dụng màu nền dự án [cite: 2025-12-25]

        add(new KpiCardsPanel(), "wrap");
        add(new ProductChartPanel(), "wrap");
        add(new TrendChartPanel(), "grow");
    }
}