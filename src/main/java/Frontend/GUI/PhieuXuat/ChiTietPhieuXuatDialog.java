package Frontend.GUI.PhieuXuat;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.text.DecimalFormat;
import java.util.ArrayList;
import net.miginfocom.swing.MigLayout;
import Frontend.Compoent.Table;
import Frontend.Compoent.Theme;
import Backend.DTO.ChiTietPhieuXuat;
import Backend.BUS.ChiTietPhieuXuatBUS;

public class ChiTietPhieuXuatDialog extends JDialog {
    private String maPX;
    private JTable tblDetails;
    private DefaultTableModel model;
    private JLabel lblImage;
    private JTextArea txtImeiList;
    private ChiTietPhieuXuatBUS chiTietPhieuXuatBUS;
    private ArrayList<ChiTietPhieuXuat> listChiTietPhieuXuat;

    public ChiTietPhieuXuatDialog(JFrame parent, String maPX) {
        super(parent, "Chi tiết phiếu xuất: " + maPX, true);
        this.maPX = maPX;
        this.chiTietPhieuXuatBUS = new ChiTietPhieuXuatBUS();
        setSize(1000, 600);
        setLocationRelativeTo(parent);
        setLayout(new BorderLayout(10, 10));
        getContentPane().setBackground(Color.WHITE);

        initComponents();
        addEvents();
        loadData();
    }

    private void initComponents() {
        JPanel pnlHeader = new JPanel(new FlowLayout(FlowLayout.CENTER));
        pnlHeader.setBackground(Theme.PRIMARY_COLOR);
        JLabel lblTitle = new JLabel("THÔNG TIN CHI TIẾT PHIẾU XUẤT");
        lblTitle.setForeground(Color.WHITE);
        lblTitle.setFont(new Font("Segoe UI", Font.BOLD, 18));
        pnlHeader.add(lblTitle);
        add(pnlHeader, BorderLayout.NORTH);

        JPanel pnlMain = new JPanel(new MigLayout("fill, insets 15", "[grow]15[350!]", "[grow]"));
        pnlMain.setBackground(Color.WHITE);

        String[] columns = { "STT", "Mã Phiên Bản", "Tên Sản Phẩm", "Số lượng", "Đơn giá", "Thành tiền" };
        model = new DefaultTableModel(columns, 0) {
            @Override
            public boolean isCellEditable(int r, int c) {
                return false;
            }
        };
        tblDetails = new Table();
        tblDetails.setModel(model);
        pnlMain.add(new JScrollPane(tblDetails), "grow");

        JPanel pnlPreview = new JPanel(new MigLayout("wrap 1, fillx, insets 15", "[center]", "[]10[grow]10[]"));
        pnlPreview.setBackground(new Color(248, 249, 250));
        pnlPreview.setBorder(BorderFactory.createLineBorder(new Color(230, 230, 230)));

        lblImage = new JLabel();
        lblImage.setBorder(BorderFactory.createLineBorder(new Color(220, 220, 220)));
        lblImage.setHorizontalAlignment(JLabel.CENTER);

        JLabel lblImeiTitle = new JLabel("Danh sách mã IMEI đã xuất:");
        lblImeiTitle.setFont(new Font("Segoe UI", Font.BOLD, 14));

        txtImeiList = new JTextArea(8, 20);
        txtImeiList.setEditable(false);
        txtImeiList.setLineWrap(true);
        txtImeiList.setFont(new Font("Segoe UI", Font.PLAIN, 13));

        pnlPreview.add(lblImage, "w 280!, h 250!");
        pnlPreview.add(lblImeiTitle, "left, gaptop 20");
        pnlPreview.add(new JScrollPane(txtImeiList), "growx, h 180!");

        pnlMain.add(pnlPreview, "growy");
        add(pnlMain, BorderLayout.CENTER);
    }

    private void addEvents() {
        tblDetails.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                int row = tblDetails.getSelectedRow();
                if (row != -1) {
                    // Lấy đối tượng sản phẩm tại dòng được chọn
                    ChiTietPhieuXuat ct = listChiTietPhieuXuat.get(row);

                    // Hiển thị ảnh dựa trên tên file lưu trong DTO
                    if (ct.getHinhAnh() != null && !ct.getHinhAnh().isEmpty()) {
                        loadProductImage(ct.getHinhAnh());
                    } else {
                        lblImage.setIcon(null);
                        lblImage.setText("Không có ảnh");
                    }

                    // (Tùy chọn) Hiển thị thông tin IMEI kèm theo
                    txtImeiList.setText("Danh sách IMEI của sản phẩm: " + ct.getTenSP());
                }
            }
        });
    }

    private void loadData() {
        model.setRowCount(0);
        DecimalFormat df = new DecimalFormat("#,### VNĐ");
        listChiTietPhieuXuat = chiTietPhieuXuatBUS.getAllChiTietPhieuXuat(maPX);

        int STT = 1;
        for (ChiTietPhieuXuat ctpx : listChiTietPhieuXuat) {
            Object[] row = {
                    STT++,
                    ctpx.getMaPhienBan(), // Đưa Mã Phiên Bản vào đây (đúng với tiêu đề cột 2)
                    ctpx.getTenSP(),
                    ctpx.getSoLuong(),
                    df.format(ctpx.getDonGia()),
                    df.format(ctpx.getThanhTien()),
            };
            model.addRow(row);
        }
    }

    private void loadProductImage(String imgName) {
        try {
            java.net.URL imgURL = getClass().getClassLoader().getResource("images/product/" + imgName);
            if (imgURL != null) {
                ImageIcon icon = new ImageIcon(imgURL);
                Image scaled = icon.getImage().getScaledInstance(280, 250, Image.SCALE_SMOOTH);
                lblImage.setIcon(new ImageIcon(scaled));
            }
        } catch (Exception e) {
            lblImage.setText("Lỗi ảnh");
        }
    }
}