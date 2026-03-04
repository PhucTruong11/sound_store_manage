package Frontend.GUI.Nhaphang;

import Frontend.Compoent.Theme; 
import Frontend.Compoent.CustomButton;
import net.miginfocom.swing.MigLayout;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import Backend.BUS.NhaCungCapBUS;
import Backend.BUS.TaiKhoanBUS;
import Backend.DTO.TaiKhoan;
import Backend.DTO.NhaCungCap;
import Backend.DTO.Session; // Import Session để lấy thông tin đăng nhập
import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

public class NhapHangSidebar extends JPanel {
    private JLabel lblTenSP, lblMaSP, lblGia, lblTonKho;
    private JSpinner spnSoLuongNhap;
    private JTable tblNhap;
    private DefaultTableModel modelNhap;
    private JTextField txtNhanVien; // Thay JComboBox bằng JTextField vì chỉ hiển thị 1 người
    private JComboBox<NhaCungCap> cbxNhaCungCap;

    private String currenMa = "";
    private String currenTen = "";
    private String currenGia = "";

    private NhapHangTable table;
    // private TaiKhoanBUS tkBUS;

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
        add(new JLabel("Nhân viên nhập:"), "gaptop 5");
        
        // Lấy họ tên từ Session
        String hoTenNV = "Chưa xác định";
        if (Session.currentNhanVien != null) {
            hoTenNV = Session.currentNhanVien.getHoTen();
        } else if (Session.currentAccount != null) {
            hoTenNV = Session.currentAccount.getUsername(); // Backup nếu chưa load được đối tượng NV
        }

        txtNhanVien = new JTextField(hoTenNV);
        txtNhanVien.setEditable(false); // Không cho phép sửa
        txtNhanVien.setFocusable(false);
        txtNhanVien.setBackground(new Color(245, 245, 245));
        add(txtNhanVien, "h 30!");

        add(new JLabel("Nhà cung cấp:"), "gaptop 10");
        cbxNhaCungCap = new JComboBox<>();
        
        NhaCungCapBUS nccBUS = new NhaCungCapBUS();
        ArrayList<NhaCungCap> list = nccBUS.getAllNhaCungCap();

        DefaultComboBoxModel<NhaCungCap> model = new DefaultComboBoxModel<>();
        model.addElement(new NhaCungCap("All", "Tất cả nhà cung cấp", "", ""));

        for(NhaCungCap ncc : list) {
            model.addElement(ncc);
        }
        cbxNhaCungCap.setModel(model);

        cbxNhaCungCap.addActionListener(e -> {
            NhaCungCap selected = (NhaCungCap) cbxNhaCungCap.getSelectedItem();
            if(selected != null) {
                table.loadDataByNCC(selected.getMaNCC());
            }
        });

        add(cbxNhaCungCap, "h 30!");
        add(new JSeparator(), "gaptop 10, gapbottom 5");
    }

    // ... (Các hàm initProductSelection, initMiniTable giữ nguyên như cũ) ...

    private void initProductSelection() {
        lblTenSP = new JLabel("Chọn sản phẩm");
        lblTenSP.setFont(new Font("Segoe UI", Font.BOLD, 14));
        lblTenSP.setForeground(Theme.PRIMARY_COLOR);

        lblTonKho = new JLabel("Tồn kho hiện tại: 0");

        add(lblTenSP);
        add(lblTonKho);
        add(new JSeparator(), "growx, gaptop 5, gapbottom 5");

        add(new JLabel("Số lượng nhập:"), "gaptop 5");
        spnSoLuongNhap = new JSpinner(new SpinnerNumberModel(1, 1, 10000, 1));
        add(spnSoLuongNhap, "split 2, w 120!, h 30!");

        CustomButton btnThem = new CustomButton("THÊM", new Color(52, 152, 219));
        btnThem.addActionListener(e -> addProductToTable());
        add(btnThem, "growx, h 30!");
    }

    private void initMiniTable() {
        add(new JLabel("Danh sách chờ nhập:"), "gaptop 15");
        String[] cols = {"Mã SP", "Tên SP", "SL", "Đơn giá"};
        
        modelNhap = new DefaultTableModel(cols, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        tblNhap = new JTable(modelNhap);
        tblNhap.setRowHeight(25);
        tblNhap.setRowSelectionAllowed(true);

        tblNhap.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int selected = tblNhap.getSelectedRow();
                if(e.getClickCount() == 2 && selected != -1) {
                    modelNhap.removeRow(selected);
                }
            }
        });

        // Ẩn các cột không cần thiết trong bảng phụ để giao diện gọn hơn
        tblNhap.getColumnModel().getColumn(1).setMinWidth(0);
        tblNhap.getColumnModel().getColumn(1).setMaxWidth(0);
        tblNhap.getColumnModel().getColumn(3).setMinWidth(0);
        tblNhap.getColumnModel().getColumn(3).setMaxWidth(0);

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
            
            JFrame parent = (JFrame) SwingUtilities.getWindowAncestor(this);
            XacNhanNhapHangDialog dialog = new XacNhanNhapHangDialog(parent, modelNhap, this.table, this);
            dialog.setVisible(true);
        });
        add(btnXacNhan, "gaptop 5, growx, h 40!, pushy, aligny bottom");
    }

    private void addProductToTable() {
        if(currenMa.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn sản phẩm từ bảng bên phải!");
            return;
        }

        int sl = (int) spnSoLuongNhap.getValue();
        modelNhap.addRow(new Object[]{currenMa, currenTen, sl, currenGia});
        resetSelection();
    }

    private void resetSelection() {
        currenMa = "";
        currenTen = "";
        currenGia = "";
        lblTenSP.setText("Chưa chọn sản phẩm");
        lblTonKho.setText("Tồn kho hiện tại: 0");
        spnSoLuongNhap.setValue(1);
    }

    public void clearCart() {
        modelNhap.setRowCount(0);
        resetSelection();
    }

    public void updateInfo(String ma, String ten, String gia, String ton) {
        this.currenMa = ma;
        this.currenTen = ten;
        this.currenGia = gia;
        lblTenSP.setText(ten);
        lblTonKho.setText("Tồn kho hiện tại: " + ton);
    }
}
