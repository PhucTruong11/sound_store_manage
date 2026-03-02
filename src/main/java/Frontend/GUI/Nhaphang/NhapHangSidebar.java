package Frontend.GUI.Nhaphang;

import Frontend.Compoent.Theme;
import Frontend.Compoent.CustomButton;
import net.miginfocom.swing.MigLayout;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import Backend.BUS.NhaCungCapBUS;
import Backend.BUS.TaiKhoanBUS;
import Backend.DTO.NhaCungCap;
import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class NhapHangSidebar extends JPanel{
    private JLabel lblTenSP, lblMaSP, lblGia, lblTonKho;
    private JSpinner spnSoLuongNhap;
    private JTable tblNhap;
    private DefaultTableModel modelNhap;
    private JComboBox<String> cbxNhanVien;
    private JComboBox<NhaCungCap> cbxNhaCungCap;

    private String currenMa = "";
    private String currenTen = "";
    private String currenGia = "";

    private NhapHangTable table;
    private TaiKhoanBUS tkBUS;

    public NhapHangSidebar(NhapHangTable table) {
        this.table = table;
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
        cbxNhaCungCap = new JComboBox<>();
        
        // Load dữ liệu NCC vào ComboBox
        NhaCungCapBUS nccBUS = new NhaCungCapBUS();
        ArrayList<NhaCungCap> list = nccBUS.getAllNhaCungCap();

        DefaultComboBoxModel<NhaCungCap> model = new DefaultComboBoxModel<>();
        model.addElement(new NhaCungCap("All", "Tất cả", "", ""));

        for(NhaCungCap ncc : list) {
            model.addElement(ncc);
        }
        cbxNhaCungCap.setModel(model);

        // Sự kiện: Khi chọn NCC khác, bảng chính sẽ load lại
        cbxNhaCungCap.addActionListener(e -> {
            NhaCungCap selected = (NhaCungCap) cbxNhaCungCap.getSelectedItem();
            if(selected != null) {
                table.loadDataByNCC(selected.getMaNCC());
            }
        });

        add(cbxNhaCungCap, "h 30!");
        add(new JSeparator(), "gaptop 5, gapbottom 5");
    }

    private void initProductSelection() {
        lblTenSP = new JLabel("Chọn sản phẩm");
        lblTenSP.setFont(new Font("Segoe UI", Font.BOLD, 14));
        lblTenSP.setForeground(Theme.PRIMARY_COLOR);

        lblTonKho = new JLabel("Tồn kho hiện tại: 0");

        add(lblTenSP);
        add(lblTonKho);
        add(new JSeparator(), "growx, gaptop 5, gapbottom 5");

        add(new JLabel("Số lượng nhập:"), "gaptop 5");
        spnSoLuongNhap = new JSpinner(new SpinnerNumberModel(1, 1, 1000, 1));
        add(spnSoLuongNhap, "split 2, w 120!, h 30!");

        CustomButton btnThem = new CustomButton("THÊM", new Color(52, 152, 219));
        btnThem.addActionListener(e -> addProductToTable());
        add(btnThem, "growx, h 30!");
    }

    private void initMiniTable() {
        add(new JLabel("Danh sách chờ nhập:"), "gaptop 15");
        String[] cols = {"Mã SP", "Tên SP", "SL", "Đơn giá"};
        modelNhap = new DefaultTableModel(cols, 0);
        tblNhap = new JTable(modelNhap);
        tblNhap.setRowHeight(25);

        // ẨN CỘT Tên SP (index 1) và Đơn giá (index 3)
        tblNhap.getColumnModel().getColumn(1).setMinWidth(0);
        tblNhap.getColumnModel().getColumn(1).setMaxWidth(0);
        tblNhap.getColumnModel().getColumn(1).setPreferredWidth(0);

        tblNhap.getColumnModel().getColumn(3).setMinWidth(0);
        tblNhap.getColumnModel().getColumn(3).setMaxWidth(0);
        tblNhap.getColumnModel().getColumn(3).setPreferredWidth(0);

        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(JLabel.CENTER);
        tblNhap.setDefaultRenderer(Object.class, centerRenderer);

        JScrollPane scroll = new JScrollPane(tblNhap);
        add(scroll, "h 140!, gaptop 5");
    }

    private void initConfirmButton() {
        CustomButton btnXacNhan = new CustomButton("XÁC NHẬN NHẬP", Theme.ACCENT_COLOR);
        btnXacNhan.addActionListener(e -> {
        if (modelNhap.getRowCount() == 0) {
            JOptionPane.showMessageDialog(this, "Danh sách chờ nhập đang trống!");
            return;
        }
        
        // Mở Dialog xác nhận
        JFrame parent = (JFrame) SwingUtilities.getWindowAncestor(this);
        XacNhanNhapHangDialog dialog = new XacNhanNhapHangDialog(parent, modelNhap);
        dialog.setVisible(true);
    });
        add(btnXacNhan, "gaptop 5, growx, h 40!, , pushy, aligny bottom");
    }

    private void addProductToTable() {
        if(currenMa.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn sản phẩm từ bảng bên phải!");
            return;
        }

        int sl = (int) spnSoLuongNhap.getValue();

        modelNhap.addRow(new Object[]{currenMa, currenTen, sl, currenGia});

        resetSelection(); // Reset phần chọn sau khi thêm
    }

    private void resetSelection() {
        currenMa = "";
        currenTen = "";
        currenGia = "";
        lblTenSP.setText("Chưa chọn sản phẩm");
        lblTonKho.setText("Tồn kho hiện tại: 0");
        spnSoLuongNhap.setValue(1);
    }

    public void updateInfo(String ma, String ten, String gia, String ton) {
        this.currenMa = ma;
        this.currenTen = ten;
        this.currenGia = gia;
        lblTenSP.setText(ten);
        lblTonKho.setText("Tồn kho hiện tại: " + ton);
    }
    
}
