package Frontend.GUI.BanHang;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

import Backend.BUS.AmthanhBUS;
import Backend.DTO.Amthanh;
import java.awt.*;
import net.miginfocom.swing.MigLayout;
import java.util.ArrayList;

public class ProductGrid extends JPanel {
    private JPanel mainPanel;
    private DefaultTableModel model;
    private AmthanhBUS amthanhBUS = new AmthanhBUS();

    public ProductGrid() {
        setLayout(new BorderLayout());
        setBackground(Color.WHITE);

        mainPanel = new JPanel(new MigLayout("ins 10, wrap 4, fillx",
                "[fill, grow]15[fill, grow]15[fill, grow]15[fill, grow]", "[]15[]"));
        mainPanel.setBackground(Color.WHITE);

        JScrollPane scrollPane = new JScrollPane(mainPanel);
        scrollPane.setBorder(null);
        scrollPane.getVerticalScrollBar().setUnitIncrement(16);

        add(scrollPane, BorderLayout.CENTER);

        loadData();
    }

    private void loadData() {
        mainPanel.removeAll();

        for (int i = 1; i <= 12; i++) {
            String tenMau = "Loa Marshall Stanmore " + i;
            String giaMau = "10.500.000";

            String anhMau = "src/resources/images/marshall.jpg";

            SanPham card = new SanPham(tenMau, giaMau, anhMau);
            mainPanel.add(card);
        }

        mainPanel.revalidate();
        mainPanel.repaint();
    }
}