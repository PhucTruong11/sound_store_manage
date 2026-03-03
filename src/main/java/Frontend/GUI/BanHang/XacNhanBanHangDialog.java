package Frontend.GUI.BanHang;

import Frontend.Compoent.Theme;
import Frontend.GUI.BaoHanh.BaoHanhTable;
import Frontend.GUI.PhieuXuat.PhieuXuatTable;
import Frontend.Compoent.CustomButton;
import Frontend.Compoent.Table;
import net.miginfocom.swing.MigLayout;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import Backend.DAO.PhieuXuatDAO;
import Backend.BUS.PhieuXuatBUS;
import Backend.BUS.ChiTietPhieuXuatBUS;
import Backend.DTO.PhieuXuat;
import Backend.DTO.ChiTietPhieuXuat;
import Backend.DTO.KhuyenMai;

import javax.swing.table.DefaultTableCellRenderer;
import java.awt.*;
import java.util.ArrayList;
import java.sql.Timestamp;

public class XacNhanBanHangDialog extends JDialog {
    private JTable tblReview;
    private DefaultTableModel modelReview;
    private JLabel lblTongSL, lblTongTien;
    private String maKH;
    private PhieuXuatTable phieuXuatTable;
    private BaoHanhTable baoHanhTable;
    private BanHangSidebar sidebar;
    private String maKMSelected = null;
    private double phanTramGiam = 0;
    private JLabel lblGiamGia;

    public XacNhanBanHangDialog(JFrame parent, DefaultTableModel sourceModel, String maKH,
            PhieuXuatTable phieuXuatTable, BaoHanhTable baoHanhTable, BanHangSidebar sidebar) {
        super(parent, "Xác nhận hóa đơn bán hàng", true);
        this.maKH = maKH;
        this.phieuXuatTable = phieuXuatTable;
        this.baoHanhTable = baoHanhTable;
        this.sidebar = sidebar;
        setLayout(new MigLayout("fill, insets 20", "[grow]", "[][grow][]"));
        setSize(800, 500);
        setLocationRelativeTo(parent);
        getContentPane().setBackground(Color.WHITE);

        initComponents(sourceModel);
        calculateTotal();
    }

    private void initComponents(DefaultTableModel sourceModel) {
        JLabel lblTitle = new JLabel("KIỂM TRA THÔNG TIN HÓA ĐƠN");
        lblTitle.setFont(new Font("Segoe UI", Font.BOLD, 18));
        lblTitle.setForeground(Theme.PRIMARY_COLOR);
        add(lblTitle, "center, wrap, gapbottom 15");

        String[] cols = { "STT", "Mã SP", "Tên Sản Phẩm", "SL", "Đơn Giá", "Thành Tiền" };
        modelReview = new DefaultTableModel(cols, 0);

        Table tblReview = new Table();
        tblReview.setModel(modelReview);

        tblReview.getColumnModel().getColumn(0).setPreferredWidth(40);
        tblReview.getColumnModel().getColumn(0).setMaxWidth(50);

        tblReview.getColumnModel().getColumn(1).setPreferredWidth(80);
        tblReview.getColumnModel().getColumn(1).setMaxWidth(100);

        tblReview.getColumnModel().getColumn(3).setPreferredWidth(50);
        tblReview.getColumnModel().getColumn(3).setMaxWidth(60);

        tblReview.getColumnModel().getColumn(2).setPreferredWidth(250);

        tblReview.getColumnModel().getColumn(4).setPreferredWidth(100);
        tblReview.getColumnModel().getColumn(5).setPreferredWidth(120);

        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(JLabel.CENTER);
        tblReview.setDefaultRenderer(Object.class, centerRenderer);

        for (int i = 0; i < sourceModel.getRowCount(); i++) {
            String ma = sourceModel.getValueAt(i, 0).toString();
            String ten = sourceModel.getValueAt(i, 1).toString();
            int sl = Integer.parseInt(sourceModel.getValueAt(i, 2).toString());
            String giaStr = sourceModel.getValueAt(i, 3).toString().replaceAll("[^0-9]", "");
            double gia = Double.parseDouble(giaStr);

            double thanhTien = sl * gia;

            modelReview.addRow(new Object[] {
                    (i + 1),
                    ma,
                    ten,
                    sl,
                    String.format("%,.0f", gia),
                    String.format("%,.0f", thanhTien)
            });
        }

        add(new JScrollPane(tblReview), "grow, wrap");

        JPanel pnlTotal = new JPanel(new MigLayout("fillx", "[grow][right]"));
        pnlTotal.setBackground(new Color(245, 245, 245));
        pnlTotal.putClientProperty("FlatLaf.style", "arc: 15");

        lblTongSL = new JLabel("Tổng số lượng: 0");
        lblTongSL.setFont(new Font("Segoe UI", Font.BOLD, 16));
        lblTongSL.setForeground(Color.RED);

        lblTongTien = new JLabel("Tổng tiền thanh toán: 0 VNĐ");
        lblTongTien.setFont(new Font("Segoe UI", Font.BOLD, 16));
        lblTongTien.setForeground(Color.RED);

        pnlTotal.add(lblTongSL);
        pnlTotal.add(lblTongTien);
        add(pnlTotal, "growx, wrap, gaptop 10");

        JPanel pnlButtons = new JPanel(new MigLayout("fillx, insets 5 0 0 0", "[left][grow][right]"));
        pnlButtons.setBackground(Color.WHITE);

        CustomButton btnKhuyenMai = new CustomButton("CHỌN KHUYẾN MÃI", Theme.ACCENT_COLOR);
        btnKhuyenMai.addActionListener(e -> {
            JFrame parentFrame = (JFrame) SwingUtilities.getWindowAncestor(this);
            ChonKhuyenMaiDialog dialog = new ChonKhuyenMaiDialog(parentFrame);
            dialog.setVisible(true);

            if (dialog.getSelectedKM() != null) {
                KhuyenMai km = dialog.getSelectedKM();

                this.maKMSelected = km.getMaKM();
                this.phanTramGiam = km.getPhanTramGiam();

                btnKhuyenMai.setText("KM: " + km.getTenKM() + " (-" + phanTramGiam + "%)");
                calculateTotal();
            }
        });

        CustomButton btnHuy = new CustomButton("HỦY BỎ", new Color(149, 165, 166));
        btnHuy.addActionListener(e -> dispose());

        CustomButton btnXacNhan = new CustomButton("XÁC NHẬN & XUẤT HÓA ĐƠN", Theme.ACCENT_COLOR);
        btnXacNhan.addActionListener(e -> {
            try {
                PhieuXuatBUS pxBUS = new PhieuXuatBUS();
                String maPX = pxBUS.getNewMaPhieu();
                Timestamp currentTime = new Timestamp(System.currentTimeMillis());

                PhieuXuat phieuXuat = new PhieuXuat(
                        maPX, currentTime, "NV01", maKH, maKMSelected, finalTotalValue, true);

                ArrayList<ChiTietPhieuXuat> dsChiTiet = new ArrayList<>();
                for (int i = 0; i < modelReview.getRowCount(); i++) {
                    String maPB = modelReview.getValueAt(i, 1).toString();
                    int sl = Integer.parseInt(modelReview.getValueAt(i, 3).toString());
                    String giaStr = modelReview.getValueAt(i, 4).toString().replaceAll("[^0-9]", "");
                    double gia = Double.parseDouble(giaStr);

                    dsChiTiet.add(new ChiTietPhieuXuat(maPX, maPB, sl, gia, 0.0));
                }

                if (pxBUS.thanhToan(phieuXuat, dsChiTiet)) {
                    JOptionPane.showMessageDialog(this,
                            "Bán hàng thành công!\n" +
                                    "Mã hóa đơn: " + maPX + "\n" +
                                    "Hệ thống đã tự động kích hoạt bảo hành cho các thiết bị.");

                    if (this.phieuXuatTable != null) {
                        this.phieuXuatTable.loadData("");
                    }

                    if (this.baoHanhTable != null) {
                        this.baoHanhTable.loadData();
                    }

                    if (this.sidebar != null) {
                        this.sidebar.clearCart();
                    }
                    dispose();
                } else {
                    JOptionPane.showMessageDialog(this,
                            "Thanh toán thất bại! Vui lòng kiểm tra lại số lượng IMEI trong kho.",
                            "Lỗi xử lý", JOptionPane.ERROR_MESSAGE);
                }
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        });

        pnlButtons.add(btnKhuyenMai);
        pnlButtons.add(new JLabel(), "growx");
        pnlButtons.add(btnHuy);
        pnlButtons.add(btnXacNhan);
        add(pnlButtons, "right");
    }

    // Thêm biến này vào đầu class XacNhanBanHangDialog
    private double finalTotalValue = 0;

    private void calculateTotal() {
        int totalSL = 0;
        double subTotal = 0;

        for (int i = 0; i < modelReview.getRowCount(); i++) {
            totalSL += Integer.parseInt(modelReview.getValueAt(i, 3).toString());
            // Lấy giá trị tiền gốc từ chính logic tính toán, không nên đọc từ Table String
            int sl = Integer.parseInt(modelReview.getValueAt(i, 3).toString());
            String giaStr = modelReview.getValueAt(i, 4).toString().replaceAll("[^0-9]", "");
            subTotal += sl * Double.parseDouble(giaStr);
        }

        // Tính tiền sau giảm giá
        double moneyDiscount = subTotal * (this.phanTramGiam / 100.0);
        this.finalTotalValue = subTotal - moneyDiscount;

        lblTongSL.setText("Tổng số lượng: " + totalSL);
        lblTongTien.setText("TỔNG TIỀN THANH TOÁN: " + String.format("%,.0f", finalTotalValue) + " VNĐ");
    }
}
