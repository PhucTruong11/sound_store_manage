package Frontend.GUI.BanHang;

import javax.swing.*;
import Backend.BUS.AmthanhBUS;
import Backend.DTO.Amthanh;
import java.awt.*;
import net.miginfocom.swing.MigLayout;
import java.util.ArrayList;

public class ProductGrid extends JPanel {
    private JPanel mainPanel;
    private AmthanhBUS amthanhBUS = new AmthanhBUS();

    public ProductGrid(BanHangSidebar sidebar) {
        setLayout(new BorderLayout());
        setBackground(Color.WHITE);

        mainPanel = new JPanel(new MigLayout("ins 10, wrap 3, fillx",
                "[fill, grow]15[fill, grow]15[fill, grow]", "[]15[]"));
        mainPanel.setBackground(Color.WHITE);

        JScrollPane scrollPane = new JScrollPane(mainPanel);
        scrollPane.setBorder(null);
        scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        scrollPane.getVerticalScrollBar().setUnitIncrement(16);

        add(scrollPane, BorderLayout.CENTER);

        loadData(1, sidebar);
    }

    public void loadData(int page, BanHangSidebar sidebar) {
        mainPanel.removeAll();
        for (int i = 1; i <= 9; i++) {
            String ma = "SP" + page + i;
            String ten = "Sản phẩm trang " + page + " số " + i;
            String gia = "10.500.000";
            String anh = "images/product/marshall.jpg";

            InfoPanel card = new InfoPanel(ma, ten, gia, anh, sidebar);
            mainPanel.add(card);
        }

        mainPanel.revalidate();
        mainPanel.repaint();
    }
}