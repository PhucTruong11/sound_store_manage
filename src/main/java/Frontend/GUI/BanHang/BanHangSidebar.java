package Frontend.GUI.BanHang;

import Frontend.Compoent.Theme;
import Frontend.GUI.Nhaphang.XacNhanNhapHangDialog;
import Frontend.Compoent.CustomButton;
import net.miginfocom.swing.MigLayout;

import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.*;
import java.awt.*;

public class BanHangSidebar extends JPanel {

    private JLabel lblTenSP, lblMaSP, lblGia, lblTonKho;
    private JSpinner spnSoLuongBan;
    private JTable tblBan;
    private DefaultTableModel modelBan;
    private JComboBox<String> cbxNhanVien;

    public BanHangSidebar() {
        setLayout(new MigLayout("wrap 1, fillx, insets 20", "[fill]"));
        setBackground(Color.WHITE);
        setPreferredSize(new Dimension(280, 0));
        putClientProperty("FlatLaf.style", "arc: 15");

        initHeader();
        initProductSelection();
        initMiniTable();
        initConfirmButton();
    }

    private void initHeader() {
        add(new JLabel("Nhân viên bán"), "gaptop 5");
        cbxNhanVien = new JComboBox<>(new String[] { "Phúc Trương" });
        cbxNhanVien.setEnabled(false); // Auto lấy tên user, không cho chỉnh
        add(cbxNhanVien, "h 30!");

        add(new JSeparator(), "gaptop 5, gapbottom 5");
    }

    private void initProductSelection() {
        lblTenSP = new JLabel("Chọn sản phẩm");
        lblTenSP.setFont(new Font("Segoe UI", Font.BOLD, 14));
        lblTenSP.setForeground(Theme.PRIMARY_COLOR);

        lblMaSP = new JLabel("Mã: -");
        lblGia = new JLabel("Giá nhập: 0");
        lblTonKho = new JLabel("Tồn kho hiện tại: 0");

        add(lblTenSP);
        add(lblMaSP, "split 2, growx");
        add(lblGia);
        add(lblTonKho);
        add(new JSeparator(), "growx, gaptop 5, gapbottom 5");

        add(new JLabel("Số lượng bán:"), "gaptop 5");
        spnSoLuongBan = new JSpinner(new SpinnerNumberModel(1, 1, 1000, 1));
        add(spnSoLuongBan, "split 2, w 120!, h 30!");

        CustomButton btnThem = new CustomButton("THÊM", new Color(52, 152, 219));
        btnThem.addActionListener(e -> addProductToTable());
        add(btnThem, "growx, h 30!");
    }

    private void initMiniTable() {
        add(new JLabel("Danh sách chờ bán:"), "gaptop 15");
        String[] cols = { "Mã SP", "Tên SP", "SL", "Đơn giá" };
        modelBan = new DefaultTableModel(cols, 0);
        tblBan = new JTable(modelBan);
        tblBan.setRowHeight(25);

        // ẨN CỘT Tên SP (index 1) và Đơn giá (index 3)
        tblBan.getColumnModel().getColumn(1).setMinWidth(0);
        tblBan.getColumnModel().getColumn(1).setMaxWidth(0);
        tblBan.getColumnModel().getColumn(1).setPreferredWidth(0);
        
        tblBan.getColumnModel().getColumn(3).setMinWidth(0);
        tblBan.getColumnModel().getColumn(3).setMaxWidth(0);
        tblBan.getColumnModel().getColumn(3).setPreferredWidth(0);

        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(JLabel.CENTER);
        tblBan.setDefaultRenderer(Object.class, centerRenderer);

        JScrollPane scroll = new JScrollPane(tblBan);
        add(scroll, "h 180!, gaptop 5");
    }

    private void initConfirmButton() {
        CustomButton btnXacNhan = new CustomButton("XÁC NHẬN BÁN", Theme.ACCENT_COLOR);
        btnXacNhan.addActionListener(e -> {
            if (modelBan.getRowCount() == 0) {
                JOptionPane.showMessageDialog(this, "Danh sách chờ bán đang trống!");
                return;
            }

            // Mở Dialog xác nhận
            JFrame parent = (JFrame) SwingUtilities.getWindowAncestor(this);
            XacNhanNhapHangDialog dialog = new XacNhanNhapHangDialog(parent, modelBan);
            dialog.setVisible(true);
        });
        add(btnXacNhan, "gaptop 5, growx, h 40!, , pushy, aligny bottom");
    }

    private void addProductToTable() {
        if (lblMaSP.getText().equals("Mã: -")) {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn sản phẩm từ bảng bên phải!");
            return;
        }

        String ma = lblMaSP.getText();
        String ten = lblTenSP.getText();
        int sl = (int) spnSoLuongBan.getValue();
        String gia = lblGia.getText().replace("Giá bán: ", "");

        modelBan.addRow(new Object[] { ma, ten, sl, gia });

        resetSelection(); // Reset phần chọn sau khi thêm
    }

    private void resetSelection() {
        lblMaSP.setText("Mã: -");
        lblTenSP.setText("Chưa chọn sản phẩm");
        lblGia.setText("Giá nhập: 0");
        spnSoLuongBan.setValue(1);
    }

    // Các hàm cập nhật thông tin từ bên ngoài
    public void updateInfo(String ma, String ten, String gia) {
        lblMaSP.setText("Mã: " + ma);
        lblTenSP.setText(ten);
        lblGia.setText("Giá: " + gia);
    }
}