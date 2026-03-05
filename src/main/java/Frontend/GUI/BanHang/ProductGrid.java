package Frontend.GUI.BanHang;

import javax.swing.*;

import Backend.BUS.PhienBanSanPhamBUS;
import Backend.BUS.SanPhamBUS;
import Backend.DTO.PhienBanSanPham;
import Backend.DTO.SanPham;
import java.awt.*;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import net.miginfocom.swing.MigLayout;

public class ProductGrid extends JPanel {
    private JPanel mainPanel;
    private ArrayList<PhienBanSanPham> fullList = new ArrayList<>();
    private PaginationPanel pagination;
    private BanHangSidebar sidebar;

    private String currentSearchQuery = "";
    private String currentMaLoai = "";
    private PhienBanSanPhamBUS phienbansanphamBUS;


    public ProductGrid(BanHangSidebar sidebar, PaginationPanel pagination) {
        phienbansanphamBUS = new PhienBanSanPhamBUS();
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

    // public void loadData() {
    //     PhienBanSanPhamBUS pbBUS = new PhienBanSanPhamBUS();
    //     this.fullList = pbBUS.getAllPhienBanSanPham();
    //     displayAll();
    // }

    // public void loadSearchData(String text) {
    //     PhienBanSanPhamBUS pbBUS = new PhienBanSanPhamBUS();
    //     this.fullList = pbBUS.search(text);
    //     displayAll();
    // }

    // public void loadByCategoryData(String tenLoai) {
    //     PhienBanSanPhamBUS pbBUS = new PhienBanSanPhamBUS();
    //     if (tenLoai.equals("Tất cả sản phẩm")) {
    //         loadData();
    //     } else {
    //         this.fullList = pbBUS.getByLoai(tenLoai);
    //         displayAll();
    //     }
    // }

    // public void displayAll() {
    //     mainPanel.removeAll();

    //     for (PhienBanSanPham pb : fullList) {
    //         InfoPanel card = new InfoPanel(
    //                 pb.getMaPhienBan(),
    //                 pb.getTenSP(),
    //                 String.format("%,.0f", pb.getGiaBan()),
    //                 pb.getHinhAnh(),
    //                 sidebar);
    //         mainPanel.add(card);
    //     }

    //     mainPanel.revalidate();
    //     mainPanel.repaint();
    // }

    // public void loadDataByLoai(String maLoai) {
    //     PhienBanSanPhamBUS pbBUS = new PhienBanSanPhamBUS();
    //     if (maLoai.equals("All")) {
    //         loadData();
    //     } else {
    //         this.fullList = pbBUS.getByLoai(maLoai);
    //         displayAll();
    //     }
    // }

    public void loadData() {
        this.currentMaLoai = "All";
        this.currentSearchQuery = "";
        applyFiltersBanHang();
    }

    public void applyFiltersBanHang() {
        this.fullList = phienbansanphamBUS.getFilteredListBanHang(currentMaLoai, currentSearchQuery);
        displayAll();
    }

    public void displayAll() {
        mainPanel.removeAll();

        if (fullList == null || fullList.isEmpty()) {
            mainPanel.add(new JLabel("Không tìm thấy sản phẩm phù hợp"));
        } else {
            for (PhienBanSanPham pb : fullList) {
                InfoPanel card = new InfoPanel(
                        pb.getMaPhienBan(),
                        pb.getTenSP(),
                        String.format("%,.0f", pb.getGiaBan()),
                        pb.getHinhAnh(),
                        sidebar);
                mainPanel.add(card);
            }
        }

        mainPanel.revalidate();
        mainPanel.repaint();
    }

    public void loadDataSearch(String query) {
        this.currentSearchQuery = query;
        applyFiltersBanHang();
    }

    public void loadDataByLoai(String maLoai) {
        this.currentMaLoai = maLoai;
        applyFiltersBanHang();
    }
}