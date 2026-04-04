package Frontend.GUI.PhieuXuat;

import javax.swing.*;
import com.toedter.calendar.JDateChooser;
import java.awt.*;
import java.util.Calendar;
import java.util.Date;

import Frontend.Compoent.CustomButton;
import Frontend.Compoent.Theme;
import net.miginfocom.swing.MigLayout;

public class PhieuXuatSidebar extends JPanel {
    private JDateChooser dateFrom;
    private JDateChooser dateTo;
    private JTextField txtMinPrice, txtMaxPrice;
    private JComboBox<String> cbxNhanVien;

    private PhieuXuatTable tblPhieuXuat;

    public PhieuXuatSidebar(PhieuXuatTable tblPhieuXuat) {
        this.tblPhieuXuat = tblPhieuXuat;
        setLayout(new MigLayout("wrap 1, fillx, insets 20", "[fill]"));
        setPreferredSize(new Dimension(280, 0));
        setBackground(Color.WHITE);
        putClientProperty("FlatLaf.style", "arc: " + Theme.ROUNDING_ARC);

        // initNhanVienXuat();
        initDate();
        initPrice();
        initResetButton();
    }

    // private void initNhanVienXuat() {
    // add(new JLabel("Nhân viên xuất"), "gaptop 10");
    // cbxNhanVien = new JComboBox<>(new String[] { "Tất cả", "Phúc Trương", "Văn
    // Nam" });
    // cbxNhanVien.addActionListener(e -> { onFilterChange(); });
    // add(cbxNhanVien, "h 35!");
    // }

    private void onFilterChange() {
        String minPriceClean = txtMinPrice.getText().replaceAll("[^0-9]", "");
        String maxPriceClean = txtMaxPrice.getText().replaceAll("[^0-9]", "");

        double min = 0;
        try {
            if (!minPriceClean.isEmpty())
                min = Double.parseDouble(minPriceClean);
        } catch (Exception e) {
            min = 0;
        }

        double max = Double.MAX_VALUE;
        try {
            if (!maxPriceClean.isEmpty())
                max = Double.parseDouble(maxPriceClean);
        } catch (Exception e) {
            max = Double.MAX_VALUE;
        }

        Date start = dateFrom.getDate();
        Date end = dateTo.getDate();

        String nv = "Tất cả";

        if (tblPhieuXuat != null) {
            tblPhieuXuat.filterData(start, end, nv, min, max);
        }
    }

    private void initDate() {
        add(new JLabel("Từ ngày:"), "gaptop 10");
        dateFrom = new JDateChooser();
        dateFrom.setBorder(null);
        dateFrom.setBackground(Color.WHITE);
        dateFrom.setDateFormatString("dd/MM/yyyy");
        Calendar cal = Calendar.getInstance();
        cal.set(2000, 0, 1);
        dateFrom.setDate(cal.getTime()); 
        add(dateFrom, "h 35!");

        add(new JLabel("Đến ngày:"), "gaptop 10");
        dateTo = new JDateChooser();
        dateTo.setBorder(null);
        dateTo.setBackground(Color.WHITE);
        dateTo.setDateFormatString("dd/MM/yyyy");
        dateTo.setDate(new Date()); 
        add(dateTo, "h 35!");

        dateFrom.addPropertyChangeListener(e -> {
            if ("date".equals(e.getPropertyName()))
                onFilterChange();
        });

        dateTo.addPropertyChangeListener(e -> {
            if ("date".equals(e.getPropertyName()))
                onFilterChange();
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
            public void insertUpdate(javax.swing.event.DocumentEvent e) {
                onFilterChange();
            }

            @Override
            public void removeUpdate(javax.swing.event.DocumentEvent e) {
                onFilterChange();
            }

            @Override
            public void changedUpdate(javax.swing.event.DocumentEvent e) {
                onFilterChange();
            }
        };

        txtMinPrice.getDocument().addDocumentListener(priceListener);
        txtMaxPrice.getDocument().addDocumentListener(priceListener);
    }

    private void initResetButton() {
        CustomButton btnXacNhan = new CustomButton("MẶC ĐỊNH", Theme.ACCENT_COLOR);

        btnXacNhan.addActionListener(e -> resetFilters());

        add(btnXacNhan, "gaptop 5, growx, h 40!, pushy, aligny bottom");
    }

    private void resetFilters() {
        Calendar cal = Calendar.getInstance();
        cal.set(2000, 0, 1);
        dateFrom.setDate(cal.getTime()); 
        dateTo.setDate(new Date()); 

        txtMinPrice.setText("");
        txtMaxPrice.setText("");

        onFilterChange();
    }
}