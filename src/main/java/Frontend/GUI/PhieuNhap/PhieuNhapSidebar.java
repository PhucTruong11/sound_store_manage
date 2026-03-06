package Frontend.GUI.PhieuNhap;

import Frontend.Compoent.Theme;
import Frontend.GUI.Nhaphang.NhapHangTable;

import com.toedter.calendar.JDateChooser;

import Backend.BUS.NhaCungCapBUS;
import Backend.DTO.NhaCungCap;
import net.miginfocom.swing.MigLayout;
import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.Date;

public class PhieuNhapSidebar extends JPanel{
    private JDateChooser dateFrom;
    private JDateChooser dateTo;
    private JTextField txtMinPrice, txtMaxPrice;
    private JComboBox<String> cbxNhanVien;
    private JComboBox<NhaCungCap> cbxNhaCungCap;

    private PhieuNhapTable table;

    public PhieuNhapSidebar(PhieuNhapTable table) {
        this.table = table;
        setLayout(new MigLayout("wrap 1, fillx, insets 20", "[fill]"));
        setPreferredSize(new Dimension(280, 0));
        setBackground(Color.WHITE);
        putClientProperty("FlatLaf.style", "arc: " + Theme.ROUNDING_ARC);

        // initNhanVienNhap();
        // initNhaCungCap();
        initDate();
        initPrice();
    }

    // private void initNhanVienNhap() {
    //     add(new JLabel("Nhân viên nhập"), "gaptop 10");
    //     cbxNhanVien = new JComboBox<>(new String[] { "Tất cả", "Phuc truong", "Van Nam" });
    //     add(cbxNhanVien, "h 35!");
    // }

    // private void initNhaCungCap() {
    //     add(new JLabel("Nhà cung cấp"), "gaptop 10");
    //     cbxNhaCungCap = new JComboBox<>();

    //     NhaCungCapBUS nccBUS = new NhaCungCapBUS();
    //     ArrayList<NhaCungCap> list = nccBUS.getAllNhaCungCap();

    //     DefaultComboBoxModel<NhaCungCap> model = new DefaultComboBoxModel<>();
    //     model.addElement(new NhaCungCap("All", "Tất cả", " ", ""));

    //     for(NhaCungCap ncc : list) {
    //         model.addElement(ncc);
    //     }
    //     cbxNhaCungCap.setModel(model);

    //     cbxNhaCungCap.addActionListener(e -> { onFilterChange(); });

    //     add(cbxNhaCungCap, "h 35!");
    // }

    private void onFilterChange() {
        // NhaCungCap ncc = (NhaCungCap) cbxNhaCungCap.getSelectedItem();
        // String maNCC = (ncc != null) ? ncc.getMaNCC() : "All";
        table.filter(/*maNCC,*/ dateFrom.getDate(), dateTo.getDate(), txtMinPrice.getText(), txtMaxPrice.getText());
    }

    private void initDate() {
        add(new JLabel("Từ ngày:"), "gaptop 10");
        dateFrom = new JDateChooser();
        dateFrom.setBorder(null);
        dateFrom.setBackground(Color.WHITE);
        dateFrom.setDateFormatString("dd/MM/yyyy");
        dateFrom.setDate(new Date(100, 0, 1));
        add(dateFrom, "h 35!");

        add(new JLabel("Đến ngày:"), "gaptop 10");
        dateTo = new JDateChooser();
        dateTo.setBorder(null);
        dateTo.setBackground(Color.WHITE);
        dateTo.setDateFormatString("dd/MM/yyyy");
        dateTo.setDate(new Date());
        add(dateTo, "h 35!");

        dateFrom.addPropertyChangeListener(e -> {
            if("date".equals(e.getPropertyName())) onFilterChange();
        });
        dateTo.addPropertyChangeListener(e -> {
            if("date".equals(e.getPropertyName())) onFilterChange();
        });
    }

    private void initPrice() {
        add(new JLabel("Số tiền từ (VNĐ):"), "gaptop 10");
        txtMinPrice = new JTextField();
        add(txtMinPrice, "h 35!");
        add(new JLabel("Đến số tiền (VNĐ):"), "gaptop 10");
        txtMaxPrice = new JTextField();
        add(txtMaxPrice, "h 35!");

        javax.swing.event.DocumentListener priceListener = new javax.swing.event.DocumentListener() {
        @Override
        public void insertUpdate(javax.swing.event.DocumentEvent e) { onFilterChange(); }
        @Override
        public void removeUpdate(javax.swing.event.DocumentEvent e) { onFilterChange(); }
        @Override
        public void changedUpdate(javax.swing.event.DocumentEvent e) { onFilterChange(); }
        };

        txtMinPrice.getDocument().addDocumentListener(priceListener);
        txtMaxPrice.getDocument().addDocumentListener(priceListener);
    }
}
