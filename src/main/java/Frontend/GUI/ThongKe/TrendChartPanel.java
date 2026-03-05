package Frontend.GUI.ThongKe;

import javax.swing.*;
import java.awt.*;
import org.jfree.chart.*;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.renderer.category.LineAndShapeRenderer;
import org.jfree.data.category.DefaultCategoryDataset;
import net.miginfocom.swing.MigLayout;

public class TrendChartPanel extends JPanel {

    public TrendChartPanel() {
        setLayout(new BorderLayout());
        setBackground(Color.WHITE);
        putClientProperty("FlatLaf.style", "arc: 20");
        initComponents();
    }

    private void initComponents() {
        DefaultCategoryDataset dataset = new DefaultCategoryDataset();
        for(int i = 1; i <= 15; i++) {
            dataset.addValue(20 + Math.random() * 50, "Khách hàng", i + "/03");
            dataset.addValue(10 + Math.random() * 40, "Đơn hàng", i + "/03");
        }

        JFreeChart chart = ChartFactory.createLineChart("XU HƯỚNG BÁN HÀNG TRONG THÁNG", "", "Số lượng", dataset);
        chart.setBackgroundPaint(Color.WHITE);
        
        CategoryPlot plot = chart.getCategoryPlot();
        plot.setBackgroundPaint(Color.WHITE);
        
        LineAndShapeRenderer renderer = new LineAndShapeRenderer();
        renderer.setSeriesPaint(0, new Color(52, 152, 219)); // Blue
        renderer.setSeriesStroke(0, new BasicStroke(3.0f));
        plot.setRenderer(renderer);

        add(new ChartPanel(chart), BorderLayout.CENTER);
    }
}