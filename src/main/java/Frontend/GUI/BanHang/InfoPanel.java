package Frontend.GUI.BanHang;

import javax.swing.*;
import java.awt.*;
import net.miginfocom.swing.MigLayout;

class InfoPanel extends JPanel {
    public InfoPanel(String ten, String gia, String path) {
        setLayout(new MigLayout("wrap 1, align center", "[center]"));
        setBackground(Color.WHITE);

        JLabel lblImg = new JLabel();
        try {
            java.net.URL imgURL = getClass().getClassLoader().getResource("images/" + path);

            if (imgURL != null) {
                ImageIcon icon = new ImageIcon(imgURL);
                Image scaled = icon.getImage().getScaledInstance(250, 250, Image.SCALE_SMOOTH);
                lblImg.setIcon(new ImageIcon(scaled));
            } else {
                System.err.println("Lỗi: Không tìm thấy ảnh tại: images/" + path);
                lblImg.setText("Không tìm thấy file");
            }
        } catch (Exception e) {
            lblImg.setText("Lỗi nạp ảnh");
        }
        lblImg.setBorder(BorderFactory.createLineBorder(new Color(240, 240, 240)));

        JLabel lblName = new JLabel(ten);
        lblName.setFont(new Font("Segoe UI", Font.BOLD, 22));

        JLabel lblPrice = new JLabel(gia + " VNĐ");
        lblPrice.setFont(new Font("Segoe UI", Font.BOLD, 18));
        lblPrice.setForeground(new Color(231, 76, 60));

        add(lblImg, "w 250!, h 250!");
        add(lblName, "gaptop 15");
        add(lblPrice, "gaptop 5");
    }
}
