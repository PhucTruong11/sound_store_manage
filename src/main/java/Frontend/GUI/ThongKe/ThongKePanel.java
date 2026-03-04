package Frontend.GUI.ThongKe;

import javax.swing.*;
import java.awt.*;
import org.jfree.chart.*;
import org.jfree.chart.axis.*;
import org.jfree.chart.plot.*;
import org.jfree.chart.renderer.category.*;
import org.jfree.chart.renderer.xy.XYLineAndShapeRenderer;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.data.general.DefaultPieDataset;
import com.formdev.flatlaf.extras.FlatSVGIcon;
import Frontend.Compoent.Theme;
import net.miginfocom.swing.MigLayout;

public class ThongKePanel extends JPanel {

    public ThongKePanel() {
        // Layout 3 hàng: Cards -> Biểu đồ lớn -> Biểu đồ phụ
        setLayout(new MigLayout("fill, insets 20", "[grow, fill][grow, fill]", "[100!]20[grow, fill]20[300!]"));
        setBackground(Theme.BACKGROUND_COLOR);

        initComponents();
    }

    private void initComponents() {
        // --- HÀNG 1: THẺ CHỈ SỐ (KPI CARDS) ---
        JPanel pnlCards = new JPanel(new MigLayout("fill, insets 0", "[grow]15[grow]15[grow]15[grow]", "fill"));
        pnlCards.setOpaque(false);
        pnlCards.add(createKpiCard("Doanh Thu Ngày", "+15.2%", "5.400.000", "trending-up.svg", new Color(46, 204, 113)));
        pnlCards.add(createKpiCard("Đơn Hàng Mới", "+5", "24", "shopping-cart.svg", new Color(52, 152, 219)));
        pnlCards.add(createKpiCard("Vốn Nhập Kho", "-2.1%", "12.800.000", "shield-plus.svg", new Color(231, 76, 60)));
        pnlCards.add(createKpiCard("Lợi Nhuận Gộp", "+8.4%", "1.250.000", "pie-chart.svg", new Color(155, 89, 182)));
        add(pnlCards, "span 2, wrap");

        // --- HÀNG 2: BIỂU ĐỒ CHỨNG KHOÁN `(LINE CHART) ---
        JPanel pnlMainChart = new JPanel(new BorderLayout());
        pnlMainChart.setBackground(Color.WHITE);
        pnlMainChart.putClientProperty("FlatLaf.style", "arc: 20");
        pnlMainChart.add(createMainStockChart(), BorderLayout.CENTER);
        add(pnlMainChart, "span 2, wrap");
    }

    private JPanel createKpiCard(String title, String trend, String value, String iconName, Color color) {
        JPanel card = new JPanel(new MigLayout("insets 15", "[][grow]", "[]2[]"));
        card.setBackground(Color.WHITE);
        card.putClientProperty("FlatLaf.style", "arc: 20");

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

    private ChartPanel createMainStockChart() {
        DefaultCategoryDataset dataset = new DefaultCategoryDataset();
        // Giả lập dữ liệu 10 ngày gần nhất (Real-time feel)
        for(int i = 1; i <= 10; i++) {
            dataset.addValue(10 + Math.random() * 20, "Doanh Thu", "0" + i + "/03");
            dataset.addValue(5 + Math.random() * 15, "Vốn Nhập", "0" + i + "/03");
        }

        JFreeChart chart = ChartFactory.createLineChart("BIẾN ĐỘNG DOANH THU & CHI PHÍ", "", "Triệu VNĐ", dataset);
        formatChart(chart, true);
        return new ChartPanel(chart);
    }

    private void formatChart(JFreeChart chart, boolean isLine) {
        chart.setBackgroundPaint(Color.WHITE);
        chart.getTitle().setFont(new Font("Segoe UI", Font.BOLD, 15));
        
        CategoryPlot plot = chart.getCategoryPlot();
        plot.setBackgroundPaint(Color.WHITE);
        plot.setRangeGridlinePaint(new Color(236, 240, 241));
        plot.setOutlineVisible(false);

        if (isLine) {
            LineAndShapeRenderer renderer = new LineAndShapeRenderer();
            renderer.setSeriesPaint(0, new Color(46, 204, 113)); // Green
            renderer.setSeriesPaint(1, new Color(231, 76, 60));  // Red
            renderer.setSeriesStroke(0, new BasicStroke(3.5f));
            renderer.setSeriesStroke(1, new BasicStroke(3.5f));
            plot.setRenderer(renderer);
        } else {
            BarRenderer renderer = (BarRenderer) plot.getRenderer();
            renderer.setSeriesPaint(0, Theme.PRIMARY_COLOR);
            // renderer.setBarAlignmentFactor(0.5);
        }
    }
}