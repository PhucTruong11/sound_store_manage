package Frontend.GUI.BanHang;

import javax.swing.*;

import Backend.BUS.PhienBanSanPhamBUS;
import Backend.BUS.SanPhamBUS;
import Backend.DTO.PhienBanSanPham;
import Backend.DTO.SanPham;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import net.miginfocom.swing.MigLayout;
import Backend.BUS.PhienBanSanPhamBUS;
import Backend.DTO.PhienBanSanPham;

public class ProductGrid extends JPanel {
    private JPanel mainPanel;
    private ArrayList<PhienBanSanPham> fullList = new ArrayList<>();
    private PaginationPanel pagination;
    private BanHangSidebar sidebar;

    public ProductGrid(BanHangSidebar sidebar, PaginationPanel pagination) {
        this.sidebar = sidebar;

        setLayout(new BorderLayout());
        setBackground(Color.WHITE);

        mainPanel = new JPanel(new MigLayout("ins 15, wrap 3, fillx, gap 15",
                "[grow, fill][grow, fill][grow, fill]", ""));
        mainPanel.setBackground(new Color(245, 245, 245));

        JScrollPane scrollPane = new JScrollPane(mainPanel);
        scrollPane.setBorder(null);
        scrollPane.getVerticalScrollBar().setUnitIncrement(16);

        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);

        add(scrollPane, BorderLayout.CENTER);

        loadData();
    }

    public void loadData() {
        PhienBanSanPhamBUS pbBUS = new PhienBanSanPhamBUS();
        this.fullList = pbBUS.getAllPhienBanSanPham();
        displayAll();
    }

    public void loadSearchData(String text) {
        PhienBanSanPhamBUS pbBUS = new PhienBanSanPhamBUS();
        this.fullList = pbBUS.search(text);
        displayAll();
    }

    public void loadByCategoryData(String tenLoai) {
        PhienBanSanPhamBUS pbBUS = new PhienBanSanPhamBUS();
        if (tenLoai.equals("Tất cả sản phẩm")) {
            loadData();
        } else {
            this.fullList = pbBUS.getByLoai(tenLoai);
            displayAll();
        }
    }

    public void displayAll() {
        mainPanel.removeAll();

        // Duyệt toàn bộ danh sách thay vì dùng subList
        for (PhienBanSanPham pb : fullList) {
            InfoPanel card = new InfoPanel(
                    pb.getMaPhienBan(),
                    pb.getTenSP(),
                    String.format("%,.0f", pb.getGiaBan()),
                    pb.getHinhAnh(),
                    sidebar);
            mainPanel.add(card);
        }

        mainPanel.revalidate();
        mainPanel.repaint();
    }
}