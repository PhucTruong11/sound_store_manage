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
    private final int ITEMS_PER_PAGE = 9;
    private ArrayList<PhienBanSanPham> fullList = new ArrayList<>();
    private PaginationPanel pagination;
    private BanHangSidebar sidebar;

    public ProductGrid(BanHangSidebar sidebar, PaginationPanel pagination) {
        this.sidebar = sidebar;
        this.pagination = pagination;

        if (this.pagination != null) {
            this.pagination.setOnPageChange(this::displayPage);
        }

        setLayout(new BorderLayout());
        setBackground(Color.WHITE);

        mainPanel = new JPanel(new MigLayout("ins 5, wrap 3, fillx",
                "[fill, grow]10[fill, grow]10[fill, grow]", "[]10[]"));
        mainPanel.setBackground(Color.WHITE);

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
        updatePagination();
    }

    public void loadSearchData(String text) {
        PhienBanSanPhamBUS pbBUS = new PhienBanSanPhamBUS();
        this.fullList = pbBUS.search(text);
        updatePagination();
        displayPage(1);
    }

    public void loadByCategoryData(String tenLoai) {
        PhienBanSanPhamBUS pbBUS = new PhienBanSanPhamBUS();
        this.fullList = pbBUS.getByLoai(tenLoai);
        updatePagination();
        displayPage(1);
    }

    private void updatePagination() {
        if (pagination != null) {
            int totalItems = fullList.size();
            int totalPages = (int) Math.ceil((double) totalItems / ITEMS_PER_PAGE);
            pagination.setTotalPages(totalPages == 0 ? 1 : totalPages);
        }
    }

    public void displayPage(int page) {
        mainPanel.removeAll();
        int start = (page - 1) * ITEMS_PER_PAGE;
        int end = Math.min(start + ITEMS_PER_PAGE, fullList.size());

        if (start < fullList.size()) {
            List<PhienBanSanPham> pagedList = fullList.subList(start, end);
            for (PhienBanSanPham pb : pagedList) {
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
}