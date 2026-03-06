package Frontend.GUI.ThongKe;

import javax.swing.*;
import java.awt.*;
import org.jfree.chart.*;
import org.jfree.chart.plot.*;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.data.general.DefaultPieDataset;

import Backend.BUS.ThongKeBUS;
import Frontend.Compoent.Theme;
import net.miginfocom.swing.MigLayout;

public class ProductChartPanel extends JPanel {
    private JComboBox<String> cbFilter;
    private final ThongKeBUS tkBUS = new ThongKeBUS();
    private DefaultCategoryDataset barDataset;
    private DefaultPieDataset pieDataset;

    public ProductChartPanel() {
        setLayout(new MigLayout("fill, insets 0", "[60%]15[40%]", "[grow]"));
        setOpaque(false);
        initComponents();
    }

    private void initComponents() {
        JPanel pnlBar = createChartWrapper("Sản Phẩm Bán Chạy");
        pnlBar.add(createBarChart(), BorderLayout.CENTER);
        
        JPanel pnlPie = createChartWrapper("Tỷ Lệ Phân Loại");
        pnlPie.add(createPieChart(), BorderLayout.CENTER);

        add(pnlBar, "grow");
        add(pnlPie, "grow");

        cbFilter.addActionListener(e -> {
            String selectedType = cbFilter.getSelectedItem().toString();
            refreshCharts(selectedType);
        });
    }

    private void refreshCharts(String type) {
        // Gọi BUS nạp lại dữ liệu vào Dataset hiện tại [cite: 2026-02-20]
        tkBUS.nạpDữLiệuTop5(barDataset, type);
        tkBUS.nạpDữLiệuTỉTrọng(pieDataset, type);
        
        // JFreeChart tự động vẽ lại khi Dataset thay đổi
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
        barDataset = new DefaultCategoryDataset();
        tkBUS.nạpDữLiệuTop5(barDataset, "Tháng"); // Mặc định là Tháng

        JFreeChart chart = ChartFactory.createBarChart("TOP 5 SẢN PHẨM", "", "Số lượng", barDataset);
        formatBasicChart(chart);
        return new ChartPanel(chart);
    }

    private ChartPanel createPieChart() {
        pieDataset = new DefaultPieDataset();
        tkBUS.nạpDữLiệuTỉTrọng(pieDataset, "Tháng"); // Mặc định là Tháng

        JFreeChart chart = ChartFactory.createPieChart("TỶ LỆ DOANH THU", pieDataset, true, true, false);
        formatBasicChart(chart);
        return new ChartPanel(chart);
    }

    private void formatBasicChart(JFreeChart chart) {
        chart.setBackgroundPaint(Color.WHITE);
        chart.getPlot().setBackgroundPaint(Color.WHITE);
        chart.getPlot().setOutlineVisible(false);
    }
}