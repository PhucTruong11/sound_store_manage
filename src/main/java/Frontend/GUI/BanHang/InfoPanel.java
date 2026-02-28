package Frontend.GUI.BanHang;

import javax.swing.*;

import Backend.BUS.PhienBanSanPhamBUS;
import Backend.DTO.PhienBanSanPham;

import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

import net.miginfocom.swing.MigLayout;

class InfoPanel extends JPanel {
    private String maSP, tenSP, gia, imgPath;

    public InfoPanel(String maSP, String tenSP, String gia, String imgPath, BanHangSidebar sidebar) {
        this.maSP = maSP;
        this.tenSP = tenSP;
        this.gia = gia;
        this.imgPath = imgPath;

        setLayout(new MigLayout("wrap 1, align center", "[center]"));
        setBackground(Color.WHITE);
        setCursor(new Cursor(Cursor.HAND_CURSOR));

        this.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(230, 230, 230), 1),
                BorderFactory.createEmptyBorder(10, 10, 10, 10) // Padding 10px mỗi bên là vừa đẹp
        ));

        this.putClientProperty("FlatLaf.style", "arc: 15");

        JLabel lblImg = new JLabel();
        try {
            String fullPath = "images/product/" + imgPath;
            java.net.URL imgURL = getClass().getClassLoader().getResource(fullPath);
            if (imgURL != null) {
                ImageIcon icon = new ImageIcon(imgURL);
                Image scaled = icon.getImage().getScaledInstance(250, 250, Image.SCALE_SMOOTH);
                lblImg.setIcon(new ImageIcon(scaled));
                lblImg.setText("");
            } else {
                System.err.println("Không tìm thấy" + imgPath);
                lblImg.setText("Không tìm thấy file");
            }
        } catch (Exception e) {
            lblImg.setText("Lỗi nạp ảnh");
        }

        lblImg.setBorder(BorderFactory.createLineBorder(new Color(240, 240, 240)));

        JLabel lblName = new JLabel(tenSP);
        lblName.setFont(new Font("Segoe UI", Font.BOLD, 18));

        JLabel lblPrice = new JLabel(gia + " VNĐ");
        lblPrice.setFont(new Font("Segoe UI", Font.BOLD, 18));
        lblPrice.setForeground(new Color(231, 76, 60));

        add(lblImg, "w 250!, h 250!");
        add(lblName, "gaptop 10, wmax 250, center");
        add(lblPrice, "gaptop 5");

        this.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                PhienBanSanPhamBUS bus = new PhienBanSanPhamBUS();
                ArrayList<PhienBanSanPham> dsPhienBan = bus.getByMaSP(maSP);

                if (dsPhienBan != null && dsPhienBan.size() > 1) {
                    String[] options = dsPhienBan.stream()
                            .map(pb -> pb.getMauSac() + " - " + String.format("%,.0f", pb.getGiaBan()))
                            .toArray(String[]::new);

                    int choice = JOptionPane.showOptionDialog(null, "Chọn màu sắc sản phẩm:",
                            "Lựa chọn phiên bản", JOptionPane.DEFAULT_OPTION,
                            JOptionPane.PLAIN_MESSAGE, null, options, options[0]);

                    if (choice >= 0) {
                        PhienBanSanPham selected = dsPhienBan.get(choice);
                        sidebar.updateInfo(selected.getMaPhienBan(), selected.getTenSP(),
                                String.format("%,.0f", selected.getGiaBan()));
                    }
                } else {
                    sidebar.updateInfo(maSP, tenSP, gia);
                }
            }
        });
    }
}