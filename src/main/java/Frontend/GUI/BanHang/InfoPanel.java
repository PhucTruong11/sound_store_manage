package Frontend.GUI.BanHang;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import net.miginfocom.swing.MigLayout;

class InfoPanel extends JPanel {
    // Thêm các biến lưu trữ thông tin sản phẩm
    private String maSP, tenSP, gia, imgPath;

    public InfoPanel(String maSP, String tenSP, String gia, String imgPath, BanHangSidebar sidebar) {
        this.maSP = maSP;
        this.tenSP = tenSP;
        this.gia = gia;
        this.imgPath = imgPath;

        setLayout(new MigLayout("wrap 1, align center", "[center]"));
        setBackground(Color.WHITE);
        setCursor(new Cursor(Cursor.HAND_CURSOR));

        JLabel lblImg = new JLabel();
        try {
            // Chỉ dùng thư mục cha và biến imgPath truyền vào
            java.net.URL imgURL = getClass().getClassLoader().getResource("images/product/marshall.jpg" + imgPath);

            if (imgURL != null) {
                ImageIcon icon = new ImageIcon(imgURL);
                Image scaled = icon.getImage().getScaledInstance(250, 250, Image.SCALE_SMOOTH);
                lblImg.setIcon(new ImageIcon(scaled));
                lblImg.setText(""); // Xóa text thông báo lỗi nếu tìm thấy ảnh
            } else {
                // In ra console để kiểm tra đường dẫn thực tế đang bị sai ở đâu
                System.err.println("Không tìm thấy: " + imgPath);
                lblImg.setText("Không tìm thấy file");
            }
        } catch (Exception e) {
            lblImg.setText("Lỗi nạp ảnh");
        }

        lblImg.setBorder(BorderFactory.createLineBorder(new Color(240, 240, 240)));

        JLabel lblName = new JLabel(tenSP);
        lblName.setFont(new Font("Segoe UI", Font.BOLD, 22));

        JLabel lblPrice = new JLabel(gia + " VNĐ");
        lblPrice.setFont(new Font("Segoe UI", Font.BOLD, 18));
        lblPrice.setForeground(new Color(231, 76, 60));

        add(lblImg, "w 250!, h 250!");
        add(lblName, "gaptop 15");
        add(lblPrice, "gaptop 5");

        this.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                // 1. Click đơn: Cập nhật Sidebar
                if (sidebar != null) {
                    sidebar.updateInfo(maSP, tenSP, gia);
                }
                // 2. Double Click: Mở Dialog chi tiết
                if(e.getClickCount() == 2) {
                    JFrame parent = (JFrame) SwingUtilities.getWindowAncestor(InfoPanel.this);
                    ChiTietSanPhamDialog dialog = new ChiTietSanPhamDialog(parent, maSP, tenSP, gia, imgPath);
                    dialog.setVisible(true);
                }
            }

            @Override
            public void mouseEntered(MouseEvent e) {
                setBackground(new Color(245, 245, 245));
            }

            @Override
            public void mouseExited(MouseEvent e) {
                setBackground(Color.WHITE);
            }
        });
    }
}