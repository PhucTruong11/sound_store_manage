package Frontend.GUI.BanHang;

import javax.swing.*;
import java.awt.*;
import net.miginfocom.swing.MigLayout;
import Frontend.Compoent.InfoField;
import Frontend.Compoent.Theme;

public class ChiTietSanPhamDialog extends JDialog {
    public ChiTietSanPhamDialog(JFrame parent, String ma, String ten, String gia, String imgPath) {
        super(parent, "Chi tiết sản phẩm", true);
        setSize(800, 550);
        setLocationRelativeTo(parent);
        setLayout(new BorderLayout());

        getContentPane().setBackground(new Color(242, 244, 246));

        JPanel pnlHeader = new JPanel(new FlowLayout(FlowLayout.CENTER));
        pnlHeader.setBackground(Theme.PRIMARY_COLOR);
        JLabel lblTitle = new JLabel("THÔNG TIN CHI TIẾT SẢN PHẨM");
        lblTitle.setForeground(Color.WHITE);
        lblTitle.setFont(new Font("Segoe UI", Font.BOLD, 18));
        pnlHeader.add(lblTitle);
        add(pnlHeader, BorderLayout.NORTH);

        JPanel pnlMain = new JPanel(new MigLayout("fill, insets 15", "[grow]15[350!]", "[grow]"));
        pnlMain.setBackground(new Color(242, 244, 246));

        JPanel info = new JPanel(new MigLayout("wrap 1, fillx, insets 20", "[fill]"));
        info.setBackground(Color.WHITE);
        info.putClientProperty("FlatLaf.style", "arc: 15"); 
        info.setBorder(BorderFactory.createLineBorder(new Color(230, 230, 230)));

        info.add(new JLabel("Mã sản phẩm:"), "gaptop 10");
        info.add(new InfoField(ma), "h 40!");
        info.add(new JLabel("Tên sản phẩm:"), "gaptop 10");
        info.add(new InfoField(ten), "h 40!");
        info.add(new JLabel("Đơn giá nhập:"), "gaptop 10");
        info.add(new InfoField(gia), "h 40!");

        JPanel pnlPreview = new JPanel(new MigLayout("wrap 1, fillx, insets 15", "[center]", "[]push[]5[]"));
        pnlPreview.setBackground(new Color(248, 249, 250));
        pnlPreview.setBorder(BorderFactory.createLineBorder(new Color(230, 230, 230)));
        pnlPreview.putClientProperty("FlatLaf.style", "arc: 15");

        JLabel lblImg = new JLabel();
        try {
            java.net.URL imgURL = getClass().getClassLoader().getResource("images/product/" + imgPath);
            if (imgURL != null) {
                ImageIcon icon = new ImageIcon(imgURL);
                Image scaled = icon.getImage().getScaledInstance(280, 280, Image.SCALE_SMOOTH);
                lblImg.setIcon(new ImageIcon(scaled));
            } else {
                lblImg.setText("Không tìm thấy ảnh");
                lblImg.setHorizontalAlignment(SwingConstants.CENTER);
            }
        } catch (Exception e) {
            lblImg.setText("Lỗi nạp ảnh");
        }

        lblImg.setOpaque(true);
        lblImg.setBackground(Color.WHITE);
        lblImg.setBorder(BorderFactory.createLineBorder(new Color(220, 220, 220)));

        JLabel lblImei = new JLabel("Mã IMEI/Serial bảo hành:");
        lblImei.setFont(new Font("Segoe UI", Font.BOLD, 13));
        InfoField txtImei = new InfoField("IMEI-123456789");
        txtImei.setEditable(false);
        txtImei.setHorizontalAlignment(JTextField.CENTER);

        pnlPreview.add(lblImg, "w 280!, h 280!");
        pnlPreview.add(lblImei, "left, gaptop 20");
        pnlPreview.add(txtImei, "growx, h 40!, gaptop 5");

        pnlMain.add(info, "grow");
        pnlMain.add(pnlPreview, "growy");
        add(pnlMain, BorderLayout.CENTER);
    }
}