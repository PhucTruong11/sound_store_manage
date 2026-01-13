package Frontend.GUI.Nhaphang;

import javax.swing.*;
import java.awt.*;
import net.miginfocom.swing.MigLayout;
import Frontend.Compoent.Theme;

public class ChiTietSanPhamDialog extends JDialog{
    public ChiTietSanPhamDialog(JFrame parent, String ma, String ten, String gia) {
        setTitle("Chi tiết sản phẩm");
        setSize(700, 500);
        setLocationRelativeTo(parent);
        setLayout(new BorderLayout());
        setModal(true); // Chặn tương tác với cửa sổ cha khi đang mở popup

        JPanel header = new JPanel();
        header.setBackground(Theme.BACKGROUND_COLOR);
        JLabel title = new JLabel(ten);
        title.setForeground(Theme.PRIMARY_COLOR);
        title.setFont(new Font("Segoe UI", Font.BOLD, 16));
        header.add(title);
        add(header, BorderLayout.NORTH);

        // Body: Chia làm 2 phần (Ảnh - Thông tin)
        JPanel body = new JPanel(new MigLayout("fill, insets 20", "[150!][grow]", "[]10[]10[]"));
        body.setBackground(Color.WHITE);

        JLabel lblImg = new JLabel();
        lblImg.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY));
        lblImg.setHorizontalAlignment(SwingConstants.CENTER);
        lblImg.setText("IMG");
        body.add(lblImg, "growy, spany, w 150!, h 150!");

        body.add(new JLabel("Mã sản phẩm:"));
        JTextField txtMa = createReadOnlyField(ma);
        body.add(txtMa, "growx, wrap");

        body.add(new JLabel("Đơn giá nhập:"));
        JTextField txtGia = createReadOnlyField(gia);
        body.add(txtGia, "growx, wrap");

        add(body, BorderLayout.CENTER);

        JPanel footer = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        footer.setBackground(Color.WHITE);
        footer.setBorder(BorderFactory.createMatteBorder(1, 0, 0, 0, new Color(230, 230, 230)));
        
        JButton btnClose = new JButton("Đóng");
        btnClose.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        btnClose.addActionListener(e -> dispose());
        footer.add(btnClose);
        
        add(footer, BorderLayout.SOUTH);
    }

    private JTextField createReadOnlyField(String text) {
        JTextField txt = new JTextField(text);
        txt.setEditable(false);
        txt.setFont(new Font("Segoe UI", Font.BOLD, 14));
        txt.setBackground(Color.WHITE);
        txt.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, Color.LIGHT_GRAY));
        return txt;
    }
}
