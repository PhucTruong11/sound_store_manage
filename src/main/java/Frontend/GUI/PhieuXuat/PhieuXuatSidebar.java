package Frontend.GUI.PhieuXuat;

import javax.swing.*;
import com.toedter.calendar.JDateChooser;
import java.awt.*;
import Frontend.Compoent.Theme;
import net.miginfocom.swing.MigLayout;
import java.util.Date;

public class PhieuXuatSidebar extends JPanel {
    private JDateChooser dateFrom;
    private JDateChooser dateTo;
    private JTextField txtMinPrice, txtMaxPrice;
    private JComboBox<String> cbxNhanVien, cbNhaCungCap;
    private PhieuXuatTable tblPhieuXuat;

    public PhieuXuatSidebar(PhieuXuatTable tblPhieuXuat) {
        this.tblPhieuXuat = tblPhieuXuat;
        setLayout(new MigLayout("wrap 1, fillx, insets 20", "[fill]"));
        setPreferredSize(new Dimension(280, 0));
        setBackground(Color.WHITE);
        putClientProperty("FlatLaf.style", "arc: " + Theme.ROUNDING_ARC);

        // initNhanVienNhap();
        initDate();
        initPrice();
        addEventListeners();
    }

    // private void initNhanVienNhap() {
    //     add(new JLabel("Nhân viên xuất"), "gaptop 10");
    //     cbxNhanVien = new JComboBox<>(new String[] { "Phúc Trương" });
    //     cbxNhanVien.setEnabled(false);
    //     add(cbxNhanVien, "h 35!");
    // }

    private void initDate() {
        add(new JLabel("Từ ngày:"), "gaptop 10");
        dateFrom = new JDateChooser();
        dateFrom.setBorder(null);
        dateFrom.setBackground(Color.WHITE);
        dateFrom.setDateFormatString("dd/MM/yyyy");
        dateFrom.setDate(new Date(125, 0, 1));
        add(dateFrom, "h 35!");

        add(new JLabel("Đến ngày:"), "gaptop 10");
        dateTo = new JDateChooser();
        dateTo.setBorder(null);
        dateTo.setBackground(Color.WHITE);
        dateTo.setDateFormatString("dd/MM/yyyy");
        dateTo.setDate(new Date());
        add(dateTo, "h 35!");
    }

    private void initPrice() {
        add(new JLabel("Số tiền từ (VNĐ):"), "gaptop 10");
        txtMinPrice = new JTextField();
        add(txtMinPrice, "h 35!");
        add(new JLabel("Đến số tiền (VNĐ):"), "gaptop 10");
        txtMaxPrice = new JTextField();
        add(txtMaxPrice, "h 35!");
    }

    public void addEventListeners() {
        dateFrom.addPropertyChangeListener(evt -> triggerFilter());
        dateTo.addPropertyChangeListener(evt -> triggerFilter());

        java.awt.event.KeyAdapter keyAdapter = new java.awt.event.KeyAdapter() {
            @Override
            public void keyReleased(java.awt.event.KeyEvent e) {
                triggerFilter();
            }
        };
        txtMinPrice.addKeyListener(keyAdapter);
        txtMaxPrice.addKeyListener(keyAdapter);
    }

    private void triggerFilter() {
        Date start = dateFrom.getDate();
        Date end = dateTo.getDate();

        String nv = "Tất cả";

        double min = 0;
        try {
            min = txtMinPrice.getText().isEmpty() ? 0 : Double.parseDouble(txtMinPrice.getText());
        } catch (Exception e) {
            min = 0;
        }

        double max = Double.MAX_VALUE;
        try {
            if (!txtMaxPrice.getText().isEmpty()) {
                max = Double.parseDouble(txtMaxPrice.getText());
            }
        } catch (Exception e) {
            max = Double.MAX_VALUE;
        }

        tblPhieuXuat.filterData(start, end, nv, min, max);
    }
}