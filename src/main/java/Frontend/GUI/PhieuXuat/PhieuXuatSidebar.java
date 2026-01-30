package Frontend.GUI.PhieuXuat;

import javax.swing.*;
import com.toedter.calendar.JDateChooser;
import java.awt.*;
import Frontend.Compoent.CustomButton;
import Frontend.Compoent.Theme;
import net.miginfocom.swing.MigLayout;
import java.util.Date;

public class PhieuXuatSidebar extends JPanel {
    private JDateChooser dateFrom;
    private JDateChooser dateTo;
    private JTextField txtMinPrice, txtMaxPrice;
    private JComboBox<String> cbNhanVien, cbNhaCungCap;

    public PhieuXuatSidebar(PhieuXuatTable table) {
        setLayout(new MigLayout("wrap 1, fillx, insets 20", "[fill]"));
        setPreferredSize(new Dimension(280, 0));
        setBackground(Color.WHITE);
        putClientProperty("FlatLaf.style", "arc: " + Theme.ROUNDING_ARC);

        initNhanVienNhap();
        initNhaCungCap();
        initDate();
        initPrice();
        initFilterButton(table);
    }

    private void initNhanVienNhap() {
        add(new JLabel("Nhân viên xuất"), "gaptop 10");
        cbNhanVien = new JComboBox<>(new String[] { "Tất cả", "Phuc truong", "Van Nam" });
        add(cbNhanVien, "h 35!");
    }

    private void initNhaCungCap() {
        add(new JLabel("Nhà cung cấp"), "gaptop 10");
        cbNhaCungCap = new JComboBox<>(new String[] { "Tất cả", "Sony Electronics", "JBL Official", "Marshall VN" });
        add(cbNhaCungCap, "h 35!");
    }

    private void initDate() {
        add(new JLabel("Từ ngày:"), "gaptop 10");
        dateFrom = new JDateChooser();
        dateFrom.setDateFormatString("dd/MM/yyyy");
        dateFrom.setDate(new Date(125, 0, 1));
        add(dateFrom, "h 35!");

        add(new JLabel("Đến ngày:"), "gaptop 10");
        dateTo = new JDateChooser();
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

    private void initFilterButton(PhieuXuatTable table) {
        CustomButton btnFilter = new CustomButton("LỌC DỮ LIỆU", Theme.ACCENT_COLOR);
        btnFilter.addActionListener(e -> thucHienLoc(table));
        add(btnFilter, "gaptop 20, growx, h 40!");
    }

    public void thucHienLoc(PhieuXuatTable table) {
        Date start = dateFrom.getDate();
        Date end = dateTo.getDate();

        if (start == null || end == null) {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn đầy đủ khoảng ngày!");
            return;
        }

        double min = 0;
        double max = Double.MAX_VALUE;
        try {
            String minStr = txtMinPrice.getText().trim();
            String maxStr = txtMaxPrice.getText().trim();
            if (!minStr.isEmpty())
                min = Double.parseDouble(minStr);
            if (!maxStr.isEmpty())
                max = Double.parseDouble(maxStr);
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Số tiền phải là con số!");
            return;
        }

        // Lấy giá trị combo box
        String nv = cbNhanVien.getSelectedItem().toString();
        String ncc = cbNhaCungCap.getSelectedItem().toString();

        table.filterData(start, end, nv, ncc, min, max);
    }
}