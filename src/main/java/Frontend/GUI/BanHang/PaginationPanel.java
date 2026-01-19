package Frontend.GUI.BanHang;

import javax.swing.*;
import java.awt.*;
import java.util.function.Consumer;

public class PaginationPanel extends JPanel {
    private int currentPage = 1;
    private int totalPages;
    private JLabel lblPageInfo;
    private Consumer<Integer> onPageChange; // Callback để báo cho Panel cha khi đổi trang

    public PaginationPanel(int totalPages, Consumer<Integer> onPageChange) {
        this.totalPages = totalPages;
        this.onPageChange = onPageChange;

        setLayout(new FlowLayout(FlowLayout.CENTER, 15, 0));
        setBackground(Color.WHITE);

        // Tăng khoảng đệm phía trên (Top: 20) để ngăn việc đè lên sản phẩm hàng cuối
        setBorder(BorderFactory.createEmptyBorder(20, 0, 10, 0));

        JButton btnPrev = new JButton("<");
        JButton btnNext = new JButton(">");
        lblPageInfo = new JLabel("Trang " + currentPage + " / " + totalPages);
        lblPageInfo.setFont(new Font("Segoe UI", Font.BOLD, 14));

        btnPrev.addActionListener(e -> {
            if (currentPage > 1) {
                currentPage--;
                refreshPage(); // Đã đổi tên hàm để hết lỗi gạch đỏ
            }
        });

        btnNext.addActionListener(e -> {
            if (currentPage < totalPages) {
                currentPage++;
                refreshPage(); // Đã đổi tên hàm để hết lỗi gạch đỏ
            }
        });

        add(btnPrev);
        add(lblPageInfo);
        add(btnNext);
    }

    // Đổi tên từ updateUI thành refreshPage để tránh trùng với hàm hệ thống của
    // JPanel
    private void refreshPage() {
        lblPageInfo.setText("Trang " + currentPage + " / " + totalPages);
        // Gọi hàm xử lý ở Panel cha
        if (onPageChange != null) {
            onPageChange.accept(currentPage);
        }
    }

    public void setTotalPages(int total) {
        this.totalPages = total;
        refreshPage();
    }
}