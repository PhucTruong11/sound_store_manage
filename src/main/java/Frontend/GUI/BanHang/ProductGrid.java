package Frontend.GUI.BanHang;

import javax.swing.*;
import Backend.BUS.AmthanhBUS;
import Backend.BUS.PhienBanSanPhamBUS;
import Backend.DTO.Amthanh;
import Backend.DTO.PhienBanSanPham;

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

        loadData(sidebar);
    }

    public void loadData(BanHangSidebar sidebar) {
        mainPanel.removeAll();

        PhienBanSanPhamBUS pbBUS = new PhienBanSanPhamBUS();

        ArrayList<PhienBanSanPham> list = pbBUS.getAllPhienBanSanPham();

        for (PhienBanSanPham pb : list) {
            String ma = pb.getMaPhienBan();
            String ten = pb.getTenSP();
            String gia = String.format("%,.0f", pb.getGiaBan());
            String anh = pb.getHinhAnh();

            InfoPanel card = new InfoPanel(ma, ten, gia, anh, sidebar);
            mainPanel.add(card);
        }

        mainPanel.revalidate();
        mainPanel.repaint();
    }
}