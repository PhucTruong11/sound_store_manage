package Frontend.GUI.BanHang;

import javax.swing.*;
import java.awt.*;
import net.miginfocom.swing.MigLayout;
import java.util.ArrayList;

public class ProductGrid extends JPanel {
    private JPanel mainPanel;

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
        for (int i = 0; i < 12; i++) {
            SanPham sp = new SanPham("Loa Bluetooth Marshall " + (i + 1), "10.500.000", "src/");
            mainPanel.add(sp);
        }
    }

    // khi gõ vào ô tim kiếm kỹ tự gì thì nó tự động lọc
    public void refreshGrid(ArrayList<Object[]> listSP) {
        mainPanel.removeAll();
        for (Object[] data : listSP) {
            SanPham sp = new SanPham(data[0].toString(), data[1].toString(), data[2].toString());
            mainPanel.add(sp);
        }
        mainPanel.revalidate();
        mainPanel.repaint();
    }
}