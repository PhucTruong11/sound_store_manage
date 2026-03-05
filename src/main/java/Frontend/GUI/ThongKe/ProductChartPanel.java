package Frontend.GUI.ThongKe;

import javax.swing.*;
import java.awt.*;
import org.jfree.chart.*;
import org.jfree.chart.plot.*;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.data.general.DefaultPieDataset;
import Frontend.Compoent.Theme;
import net.miginfocom.swing.MigLayout;

public class ProductChartPanel extends JPanel {
    private JComboBox<String> cbFilter;

    public ProductChartPanel() {
        setLayout(new MigLayout("fill, insets 0", "[60%]15[40%]", "[grow]"));
        setOpaque(false);
        initComponents();
    }

    private void initComponents() {
        // Cột trái: Sản phẩm bán chạy (Bar Chart)
        JPanel pnlBar = createChartWrapper("Sản Phẩm Bán Chạy");
        pnlBar.add(createBarChart(), BorderLayout.CENTER);
        
        // Cột phải: Phân loại sản phẩm (Pie Chart)
        JPanel pnlPie = createChartWrapper("Tỷ Lệ Phân Loại");
        pnlPie.add(createPieChart(), BorderLayout.CENTER);

        add(pnlBar, "grow");
        add(pnlPie, "grow");
    }

    private JPanel createChartWrapper(String title) {
        JPanel pnl = new JPanel(new BorderLayout());
        pnl.setBackground(Color.WHITE);
        pnl.putClientProperty("FlatLaf.style", "arc: 20");
        
        JPanel pnlHeader = new JPanel(new BorderLayout());
        pnlHeader.setOpaque(false);
        pnlHeader.setBorder(BorderFactory.createEmptyBorder(10, 15, 0, 15));
        
        JLabel lblTitle = new JLabel(title);
        lblTitle.setFont(new Font("Segoe UI", Font.BOLD, 14));
        
        cbFilter = new JComboBox<>(new String[]{"Ngày", "Tháng", "Năm"});
        
        pnlHeader.add(lblTitle, BorderLayout.WEST);
        pnlHeader.add(cbFilter, BorderLayout.EAST);
        
        pnl.add(pnlHeader, BorderLayout.NORTH);
        return pnl;
    }

    private ChartPanel createBarChart() {
        DefaultCategoryDataset dataset = new DefaultCategoryDataset();
        dataset.addValue(50, "Đã bán", "Loa Marshall");
        dataset.addValue(42, "Đã bán", "Tai nghe Sony");
        dataset.addValue(35, "Đã bán", "Loa JBL");
        
        JFreeChart chart = ChartFactory.createBarChart("", "", "Số lượng", dataset);
        formatBasicChart(chart);
        return new ChartPanel(chart);
    }

    private ChartPanel createPieChart() {
        DefaultPieDataset dataset = new DefaultPieDataset();
        dataset.setValue("Loa Bluetooth", 45);
        dataset.setValue("Tai nghe", 30);
        dataset.setValue("Phụ kiện", 25);
        
        JFreeChart chart = ChartFactory.createPieChart("", dataset, true, true, false);
        formatBasicChart(chart);
        return new ChartPanel(chart);
    }

    private void formatBasicChart(JFreeChart chart) {
        chart.setBackgroundPaint(Color.WHITE);
        chart.getPlot().setBackgroundPaint(Color.WHITE);
        chart.getPlot().setOutlineVisible(false);
    }
}