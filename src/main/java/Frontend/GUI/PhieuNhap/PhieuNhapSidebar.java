package Frontend.GUI.PhieuNhap;

import Frontend.Compoent.Theme;
import com.toedter.calendar.JDateChooser;
import net.miginfocom.swing.MigLayout;
import javax.swing.*;
import java.awt.*;
import java.util.Date;

public class PhieuNhapSidebar extends JPanel{
    private JDateChooser dateFrom;
    private JDateChooser dateTo;
    private JTextField txtMinPrice, txtMaxPrice;
    private JComboBox<String> cboNhanVien, cboNhaCungCap;

    public PhieuNhapSidebar(PhieuNhapTable table) {
        setLayout(new MigLayout("wrap 1, fillx, insets 20", "[fill]"));
        setPreferredSize(new Dimension(280, 0));
        setBackground(Color.WHITE);
        putClientProperty("FlatLaf.style", "arc: " + Theme.ROUNDING_ARC);

        initNhanVienNhap();
        initNhaCungCap();
        initDate();
        initPrice();
    }

    private void initNhanVienNhap() {
        add(new JLabel("Nhân viên nhập"), "gaptop 10");
        cboNhanVien = new JComboBox<>(new String[] { "Tất cả", "Phuc truong", "Van Nam" });
        add(cboNhanVien, "h 35!");
    }

    private void initNhaCungCap() {
        add(new JLabel("Nhà cung cấp"), "gaptop 10");
        cboNhaCungCap = new JComboBox<>(new String[] { "Tất cả", "Sony Electronics", "JBL Official", "Marshall VN" });
        add(cboNhaCungCap, "h 35!");
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
    }

    private void initPrice() {
        add(new JLabel("Số tiền từ (VNĐ):"), "gaptop 10");
        txtMinPrice = new JTextField();
        add(txtMinPrice, "h 35!");
        add(new JLabel("Đến số tiền (VNĐ):"), "gaptop 10");
        txtMaxPrice = new JTextField();
        add(txtMaxPrice, "h 35!");
    }
}
