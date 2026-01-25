package Frontend.GUI.PhieuNhap;

import Backend.BUS.ChiTietPhieuNhapBUS;
import Backend.DTO.ChiTietPhieuNhap;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.text.DecimalFormat;
import java.util.ArrayList;
import net.miginfocom.swing.MigLayout;
import Frontend.Compoent.Table;
import Frontend.Compoent.Theme;

public class ChiTietHoaDonNhapDialog extends JDialog {
    private String maHD;
    private JTable tblDetails;
    private DefaultTableModel model;
    private JLabel lblImage;
    private JTextArea txtImeiList;
    private ChiTietPhieuNhapBUS ctBUS = new ChiTietPhieuNhapBUS();

    public ChiTietHoaDonNhapDialog(JFrame parent, String maHD) {
        super(parent, "Chi tiết hóa đơn: " + maHD, true);
        this.maHD = maHD;
        setSize(1000, 600);
        setLocationRelativeTo(parent);
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

        String[] columns = { "STT", "Mã Phiên Bản", "Tên Sản Phẩm", "Số lượng", "Đơn giá", "Thành tiền" };
        model = new DefaultTableModel(columns, 0) {
            @Override public boolean isCellEditable(int r, int c) { return false; }
        };

        tblDetails = new Table();
        tblDetails.setModel(model);

        JScrollPane scrollPane = new JScrollPane(tblDetails);
        pnlMain.add(scrollPane, "grow");

        JPanel pnlPreview = new JPanel(new MigLayout("wrap 1, fillx, insets 15", "[center]", "[]10[grow]10[]"));
        pnlPreview.setBackground(new Color(248, 249, 250));
        pnlPreview.setBorder(BorderFactory.createLineBorder(new Color(230, 230, 230)));

        lblImage = new JLabel();
        lblImage.setBorder(BorderFactory.createLineBorder(new Color(220, 220, 220)));
        lblImage.setHorizontalAlignment(JLabel.CENTER);

        JLabel lblImeiTitle = new JLabel("Danh sách mã IMEI nhập về:");
        lblImeiTitle.setFont(new Font("Segoe UI", Font.BOLD, 14));

        txtImeiList = new JTextArea(8, 20);
        txtImeiList.setEditable(false);
        txtImeiList.setLineWrap(true);
        txtImeiList.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        JScrollPane scrollImei = new JScrollPane(txtImeiList);

        pnlPreview.add(lblImage, "w 250!, h 200!");
        pnlPreview.add(lblImeiTitle, "left, gaptop 20");
        pnlPreview.add(scrollImei, "growx, h 180!");

        pnlMain.add(pnlPreview, "growy");
        add(pnlMain, BorderLayout.CENTER);

        // --- Sự kiện click bảng để đổi ảnh ---
        tblDetails.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                updatePreview();
            }
        });
    }

    private void loadData() {
        model.setRowCount(0);
        DecimalFormat df = new DecimalFormat("#, ###");
        ArrayList<ChiTietPhieuNhap> list = ctBUS.getByMaPhieu(maHD);

        int stt = 1;
        for(ChiTietPhieuNhap ct : list) {
            model.addRow(new Object[] {
                stt++, 
                ct.getMaPhienBan(), 
                // ct.getTenSP(), // Tên lấy từ lệnh JOIN ở DAO
                ct.getSoLuong(), 
                df.format(ct.getDonGia()), 
                df.format(ct.getThanhTien())
            });
        }
    }

    private void updatePreview() {
        int row = tblDetails.getSelectedRow();
        if (row != -1) {
            txtImeiList.setText("Hệ thống sẽ liệt kê các mã IMEI vừa tạo tự động tại đây...");
            
            // lblImage.setIcon(...); // Load ảnh từ folder dựa vào dữ liệu SanPham
        }
    }

}