package Frontend.GUI.BanHang;

import javax.swing.*;
import java.awt.*;
import java.util.function.Consumer;

public class PaginationPanel extends JPanel {
    private int currentPage = 1;
    private int totalPages;
    private JLabel lblPageInfo;
    private Consumer<Integer> onPageChange; 

    public PaginationPanel(int totalPages, Consumer<Integer> onPageChange) {
        this.totalPages = totalPages;
        this.onPageChange = onPageChange;

        setLayout(new FlowLayout(FlowLayout.CENTER, 15, 0));
        setBackground(Color.WHITE);
        putClientProperty("FlatLaf.style", "arc: 10");

        setBorder(BorderFactory.createEmptyBorder(5, 0, 5, 0));

        JButton btnPrev = new JButton("<");
        JButton btnNext = new JButton(">");

        btnPrev.putClientProperty("FlatLaf.style", "arc: 10");
        btnNext.putClientProperty("FlatLaf.style", "arc: 10");

        lblPageInfo = new JLabel("Trang " + currentPage + " / " + totalPages);
        lblPageInfo.setFont(new Font("Segoe UI", Font.BOLD, 14));

        btnPrev.addActionListener(e -> {
            if (currentPage > 1) {
                currentPage--;
                refreshPage(); 
            }
        });

        btnNext.addActionListener(e -> {
            if (currentPage < totalPages) {
                currentPage++;
                refreshPage(); 
            }
        });

        add(btnPrev);
        add(lblPageInfo);
        add(btnNext);
    }

    private void refreshPage() {
        lblPageInfo.setText("Trang " + currentPage + " / " + totalPages);
        if (onPageChange != null) {
            onPageChange.accept(currentPage);
        }
    }

    public void setTotalPages(int total) {
        this.totalPages = total;
        refreshPage();
    }
}