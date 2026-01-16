package Frontend.GUI.PhieuNhap;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.DefaultTableCellRenderer;
import java.awt.*;
import net.miginfocom.swing.MigLayout;
import Frontend.Compoent.Table;
import Frontend.Compoent.Theme;

public class ChiTietHoaDonNhapDialog extends JDialog {
    private String maHD;
    private JTable tblDetails;
    private DefaultTableModel model;
    private JLabel lblImage;
    private JLabel lblInfo;

    public ChiTietHoaDonNhapDialog(JFrame parent, String maHD) {
        super(parent, "Chi tiết hóa đơn: " + maHD, true);
        this.maHD = maHD;
        setSize(1000, 600);
        setLocationRelativeTo(getOwner());
        setLayout(new BorderLayout(10, 10));
        getContentPane().setBackground(Color.WHITE);
        getRootPane().putClientProperty("FlatLaf.style", "arc: " + Theme.ROUNDING_ARC);

        initComponents();
        loadData();
    }

    private void initComponents() {
        JPanel pnlHeader = new JPanel(new FlowLayout(FlowLayout.CENTER));
        pnlHeader.setBackground(Theme.PRIMARY_COLOR);
        JLabel lblTitle = new JLabel("THÔNG TIN CHI TIẾT HÓA ĐƠN");
        lblTitle.setForeground(Color.WHITE);
        lblTitle.setFont(new Font("Segoe UI", Font.BOLD, 18));
        pnlHeader.add(lblTitle);
        add(pnlHeader, BorderLayout.NORTH);

        JPanel pnlMain = new JPanel(new MigLayout("fill, insets 10", "[grow]10[300!]", "[grow]"));
        pnlMain.setBackground(Color.WHITE);

        String[] columns = { "STT", "Mã SP", "Tên Sản Phẩm", "Số lượng", "Đơn giá", "Thành tiền" };
        model = new DefaultTableModel(columns, 0);
        tblDetails = new Table();
        tblDetails.setModel(model);

        JScrollPane scrollPane = new JScrollPane(tblDetails);
        pnlMain.add(scrollPane, "grow");

        JPanel pnlPreview = new JPanel(new MigLayout("wrap 1, fillx, insets 15", "[center]", "[]10[grow]10[]"));
        pnlPreview.setBackground(new Color(248, 249, 250));
        pnlPreview.setBorder(BorderFactory.createLineBorder(new Color(230, 230, 230)));

        lblInfo = new JLabel("Chọn sản phẩm để xem ảnh");
        lblInfo.setFont(new Font("Segoe UI", Font.BOLD, 14));

        lblImage = new JLabel();
        lblImage.setPreferredSize(new Dimension(250, 250));
        lblImage.setBorder(BorderFactory.createLineBorder(new Color(220, 220, 220)));
        lblImage.setHorizontalAlignment(JLabel.CENTER);

        JLabel lblImeiTitle = new JLabel("Mã IMEI/Serial bảo hành:");
        JTextField txtImei = new JTextField("IMEI-123456789");
        txtImei.setEditable(false);
        txtImei.setHorizontalAlignment(JTextField.CENTER);

        pnlPreview.add(lblInfo);
        pnlPreview.add(lblImage, "w 250!, h 250!");
        pnlPreview.add(lblImeiTitle, "left, gaptop 10");
        pnlPreview.add(txtImei, "growx, h 35!");

        pnlMain.add(pnlPreview, "growy");
        add(pnlMain, BorderLayout.CENTER);

        // --- Sự kiện click bảng để đổi ảnh ---
        tblDetails.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                updatePreview();
            }
        });
    }

    private void updatePreview() {
        int row = tblDetails.getSelectedRow();
        if (row != -1) {
            String tenSP = model.getValueAt(row, 2).toString();
            lblInfo.setText(tenSP);
            lblImage.setIcon(new ImageIcon("path/to/image.jpg"));
        }
    }

    private void loadData() {
        model.addRow(new Object[] { "1", "TAI001", "Tai nghe Sony WH-1000XM5", "1", "8,500,000", "8,500,000" });
        model.addRow(new Object[] { "2", "LOA005", "Loa Marshall Stanmore III", "1", "10,200,000", "10,200,000" });
    }
}