package Frontend.GUI.KhuyenMai;

import Backend.DTO.KhuyenMai;
import com.toedter.calendar.JDateChooser;
import net.miginfocom.swing.MigLayout;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import javax.swing.plaf.basic.BasicButtonUI;
import java.awt.*;
import java.util.Date;

public class KhuyenMaiDialog extends JDialog {
    private JTextField txtMa, txtTen, txtGiam;
    private JDateChooser dateBD, dateKT;
    private JButton btnSave, btnCancel;
    private boolean confirmed = false;

    public KhuyenMaiDialog(Frame owner, String title, KhuyenMai data) {
        super(owner, title, true);
        
        // Cấu hình kích thước nhỏ gọn hơn (giảm chiều rộng và chiều cao)
        this.setSize(500, 580); 
        this.setResizable(false);
        this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        this.setUndecorated(true);

        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBorder(new LineBorder(new Color(200, 200, 200), 1));
        mainPanel.setBackground(Color.WHITE);
        setContentPane(mainPanel);

        // 1. Header: Chiều cao thấp hơn (40px)
        JPanel pnlHeader = new JPanel(new BorderLayout());
        pnlHeader.setBackground(new Color(44, 62, 80));
        pnlHeader.setPreferredSize(new Dimension(0, 40));

        JLabel lblHeader = new JLabel(title.toUpperCase(), SwingConstants.CENTER);
        lblHeader.setFont(new Font("Segoe UI", Font.BOLD, 14));
        lblHeader.setForeground(Color.WHITE);
        pnlHeader.add(lblHeader, BorderLayout.CENTER);

        JButton btnClose = new JButton("X");
        btnClose.setFont(new Font("Arial", Font.BOLD, 14));
        btnClose.setForeground(Color.WHITE);
        btnClose.setFocusPainted(false);
        btnClose.setBorderPainted(false);
        btnClose.setContentAreaFilled(false);
        btnClose.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnClose.addActionListener(e -> dispose());
        pnlHeader.add(btnClose, BorderLayout.EAST);

        mainPanel.add(pnlHeader, BorderLayout.NORTH);

        // 2. Content: Thu hẹp insets và gap
        // [right, 130!] (nhãn hẹp hơn) 15 (khoảng cách hẹp hơn) [grow, fill]
        JPanel pnlContent = new JPanel(new MigLayout("fillx, insets 25 40 20 40", "[right, 130!]15[grow, fill]"));
        pnlContent.setBackground(Color.WHITE);
        mainPanel.add(pnlContent, BorderLayout.CENTER);

        Font labelFont = new Font("Segoe UI", Font.PLAIN, 13);
        String fieldStyle = "h 32!, wrap 20"; // Chiều cao input chỉ 32px (giống chuẩn web/app)

        txtMa = createStyledTextField();
        txtTen = createStyledTextField();
        txtGiam = createStyledTextField();
        dateBD = createStyledDateChooser();
        dateKT = createStyledDateChooser();

        pnlContent.add(new JLabel("Mã khuyến mãi:") {{ setFont(labelFont); }});
        pnlContent.add(txtMa, fieldStyle);

        pnlContent.add(new JLabel("Tên chương trình:") {{ setFont(labelFont); }});
        pnlContent.add(txtTen, fieldStyle);

        pnlContent.add(new JLabel("% Giảm giá:") {{ setFont(labelFont); }});
        pnlContent.add(txtGiam, fieldStyle);

        pnlContent.add(new JLabel("Ngày bắt đầu:") {{ setFont(labelFont); }});
        pnlContent.add(dateBD, fieldStyle);

        pnlContent.add(new JLabel("Ngày kết thúc:") {{ setFont(labelFont); }});
        pnlContent.add(dateKT, fieldStyle);

        // 3. Footer: Nền xám nhạt, nút bấm nhỏ gọn
        JPanel pnlFooter = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 10));
        pnlFooter.setBackground(new Color(245, 245, 245));

        btnCancel = new JButton("HỦY");
        styleButton(btnCancel, new Color(240, 94, 94), 90); // Chiều rộng chỉ 90px

        btnSave = new JButton("LƯU THAY ĐỔI");
        styleButton(btnSave, new Color(26, 188, 156), 140); // Chiều rộng 140px

        pnlFooter.add(btnCancel);
        pnlFooter.add(btnSave);
        mainPanel.add(pnlFooter, BorderLayout.SOUTH);

        if (data != null) {
            txtMa.setText(data.getMaKM());
            txtMa.setEditable(false);
            txtTen.setText(data.getTenKM());
            txtGiam.setText(String.valueOf(data.getPhanTramGiam()));
            dateBD.setDate(data.getNgayBD());
            dateKT.setDate(data.getNgayKT());
        }

        btnSave.addActionListener(e -> { confirmed = true; dispose(); });
        btnCancel.addActionListener(e -> dispose());

        this.setLocationRelativeTo(owner);
    }

    private JTextField createStyledTextField() {
        JTextField tf = new JTextField();
        tf.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        tf.setBorder(BorderFactory.createCompoundBorder(
            new LineBorder(new Color(220, 220, 220), 1, true),
            new EmptyBorder(0, 8, 0, 8)
        ));
        return tf;
    }

    private JDateChooser createStyledDateChooser() {
        JDateChooser dc = new JDateChooser();
        dc.setDateFormatString("dd/MM/yyyy");
        dc.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        dc.getComponent(1).setCursor(new Cursor(Cursor.HAND_CURSOR)); // Icon lịch
        dc.setBorder(new LineBorder(new Color(220, 220, 220), 1, true));
        dc.setBackground(Color.WHITE);
        return dc;
    }

    private void styleButton(JButton btn, Color color, int width) {
        btn.setBackground(color);
        btn.setForeground(Color.WHITE);
        btn.setFont(new Font("Segoe UI", Font.BOLD, 12));
        btn.setPreferredSize(new Dimension(width, 32)); // Nút thanh mảnh hơn (cao 32px)
        btn.setFocusPainted(false);
        btn.setBorderPainted(false);
        btn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btn.setContentAreaFilled(false);
        
        btn.setUI(new BasicButtonUI() {
            @Override
            public void paint(Graphics g, JComponent c) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(c.getBackground());
                g2.fillRoundRect(0, 0, c.getWidth(), c.getHeight(), 6, 6); // Bo góc ít hơn cho tinh tế
                super.paint(g2, c);
                g2.dispose();
            }
        });
    }

    public KhuyenMai getData() {
        if (!confirmed) return null;
        try {
            return new KhuyenMai(txtMa.getText().trim(), txtTen.getText().trim(), 
                                 Double.parseDouble(txtGiam.getText()), dateBD.getDate(), dateKT.getDate(), 1);
        } catch (Exception e) { return null; }
    }
}
