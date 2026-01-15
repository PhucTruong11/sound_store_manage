package Frontend.GUI.BanHang;

import javax.swing.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import net.miginfocom.swing.MigLayout;
import Frontend.Compoent.Theme;
import java.awt.*;

public class SanPham extends JPanel {
    private String tenSP;
    private String gia;
    private String imgPath;

    public SanPham(String tenSP, String gia, String imgPath) {
        this.tenSP = tenSP;
        this.gia = gia;
        this.imgPath = imgPath;

        setLayout(new MigLayout("wrap 1, align center", "[center]"));
        setBackground(Color.WHITE);
        setBorder(BorderFactory.createLineBorder(new Color(240, 240, 240)));
        putClientProperty("FlatLaf.style", "arc: 15");

        JLabel lblImg = new JLabel();
        try {
            ImageIcon icon = new ImageIcon(imgPath);
            Image scaled = icon.getImage().getScaledInstance(140, 140, Image.SCALE_SMOOTH);
            lblImg.setIcon(new ImageIcon(scaled));
        } catch (Exception e) {
            lblImg.setText("No Image");
        }
        
        add(lblImg, "w 140!, h 140!");

        JLabel lblName = new JLabel(tenSP);
        lblName.setFont(new Font("Segoe UI", Font.BOLD, 13));
        add(lblName, "gaptop 10");

        JLabel lblPrice = new JLabel(gia + " VNĐ");
        lblPrice.setForeground(new Color(231, 76, 60));
        add(lblPrice);

        this.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                ChiTietSanPhamDialog();
            }
        });
    }

    private void ChiTietSanPhamDialog() {
        Window parent = SwingUtilities.getWindowAncestor(this);
        JOptionPane.showMessageDialog(parent, "Đang mở chi tiết: " + tenSP);
    }
}
