package Frontend.GUI.Nhaphang;

import Frontend.Compoent.Theme;
import Frontend.Compoent.Button;
import net.miginfocom.swing.MigLayout;
import javax.swing.table.DefaultTableModel;
import javax.swing.*;
import java.awt.*;

public class NhapHangSidebar extends JPanel{
    private JLabel lblTenSP, lblMaSP, lblGia, lblTonKho;
    private JSpinner spnSoLuongNhap;
    private JTable tblNhap;
    private DefaultTableModel modelNhap;
    private JComboBox<String> cbxNhanVien, cbxNhaCungCap;

    public NhapHangSidebar() {
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
        add(new JLabel("Nhân viên nhập"), "gaptop 5");
        cbxNhanVien = new JComboBox<>(new String[] {"Phúc Trương"});
        cbxNhanVien.setEnabled(false); // Auto lấy tên user, không cho chỉnh
        add(cbxNhanVien, "h 30!");

        add(new JLabel("Nhà cung cấp:"));
        cbxNhaCungCap = new JComboBox<>(new String[] {"Tất cả", "Sony Electronics", "JBL Official", "Marshall VN"});
        add(cbxNhaCungCap, "h 30!");

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

        add(new JLabel("Số lượng nhập:"), "gaptop 5");
        spnSoLuongNhap = new JSpinner(new SpinnerNumberModel(1, 1, 1000, 1));
        add(spnSoLuongNhap, "split 2, w 120!, h 30!");

        Button btnThem = new Button("THÊM", new Color(52, 152, 219));
        btnThem.addActionListener(e -> addProductToTable());
        add(btnThem, "growx, h 30!");
    }

    private void initMiniTable() {
        add(new JLabel("Danh sách chờ nhập:"), "gaptop 15");
        String[] cols = {"Mã SP", "Tên SP", "SL", "Đơn Giá"};
        modelNhap = new DefaultTableModel(cols, 0);
        tblNhap = new JTable(modelNhap);

        tblNhap.setRowHeight(25);
        JScrollPane scroll = new JScrollPane(tblNhap);

        add(scroll, "h 140!, gaptop 5");
    }

    private void initConfirmButton() {
        Button btnXacNhan = new Button("XÁC NHẬN NHẬP", Theme.ACCENT_COLOR);
        btnXacNhan.addActionListener(e -> {
        if (modelNhap.getRowCount() == 0) {
            JOptionPane.showMessageDialog(this, "Danh sách chờ nhập đang trống!");
            return;
        }
        
        // Mở Dialog xác nhận
        JFrame parent = (JFrame) SwingUtilities.getWindowAncestor(this);
        XacNhanNhapHangDialog dialog = new XacNhanNhapHangDialog(parent, modelNhap);
        dialog.setVisible(true);
        
        // Nếu sau khi xác nhận mà muốn xóa sạch bảng mini ở Sidebar:
        // modelNhap.setRowCount(0); 
    });
        add(btnXacNhan, "gaptop 5, growx, h 40!, , pushy, aligny bottom");
    }

    private void addProductToTable() {
        if(lblMaSP.getText().equals("Mã: -")) {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn sản phẩm từ bảng bên phải!");
            return;
        }

        String ma = lblMaSP.getText();
        String ten = lblTenSP.getText();
        int sl = (int) spnSoLuongNhap.getValue();
        String gia = lblGia.getText().replace("Giá nhập: ", "");

        modelNhap.addRow(new Object[]{ma, ten, sl, gia});

        resetSelection(); // Reset phần chọn sau khi thêm
    }

    private void resetSelection() {
        lblMaSP.setText("Mã: -");
        lblTenSP.setText("Chưa chọn sản phẩm");
        lblGia.setText("Giá nhập: 0");
        spnSoLuongNhap.setValue(1);
    }

    // Các hàm cập nhật thông tin từ bên ngoài
    public void updateInfo(String ma, String ten, String gia) {
        lblMaSP.setText("Mã: " + ma);
        lblTenSP.setText(ten);
        lblTonKho.setText("Giá: " + gia);
    }
    
}
