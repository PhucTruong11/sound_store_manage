package Frontend.GUI.KhachHang;

import Frontend.Compoent.Theme;
import Frontend.Compoent.CustomButton;
import net.miginfocom.swing.MigLayout;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

import java.awt.*;

public class KhachHangSidebar extends JPanel {

    private KhachHangTable table;

    private JTextField txtMa;
    private JTextField txtTen;
    private JTextField txtDiaChi;
    private JTextField txtSDT;

    public KhachHangSidebar(KhachHangTable table) {
        this.table = table;

        setLayout(new MigLayout("wrap 1, fillx, insets 20", "[fill]"));
        setPreferredSize(new Dimension(280, 0));
        setBackground(Color.WHITE);
        putClientProperty("FlatLaf.style", "arc: " + Theme.ROUNDING_ARC);

        initFilter();
        addRealtimeFilter();
    }

    private void initFilter() {
        add(new JLabel("Theo Mã"));
        txtMa = new JTextField();
        add(txtMa, "h 35!");

        add(new JLabel("Theo Tên"));
        txtTen = new JTextField();
        add(txtTen, "h 35!");

        add(new JLabel("Theo Địa chỉ"));
        txtDiaChi = new JTextField();
        add(txtDiaChi, "h 35!");

        add(new JLabel("Theo SĐT"));
        txtSDT = new JTextField();
        add(txtSDT, "h 35!");

        CustomButton btnLoc = new CustomButton("LỌC", Theme.ACCENT_COLOR);
        add(btnLoc, "pushy,aligny bottom, growx, h 40!");

        CustomButton btnReset = new CustomButton("ĐẶT LẠI BỘ LỌC", Theme.DANGER_COLOR);
        add(btnReset, "growx, h 40!, gapy 10 0");

        btnLoc.addActionListener(e -> applyFilter());
        btnReset.addActionListener(e -> resetFilter());
    }
    private void applyFilter() {
        table.loadDataByAdvancedFilter(
                txtMa.getText().trim(),
                txtTen.getText().trim(),
                txtDiaChi.getText().trim(),
                txtSDT.getText().trim()
        );
    }
    private void resetFilter() {
        txtMa.setText("");
        txtTen.setText("");
        txtDiaChi.setText("");
        txtSDT.setText("");
        table.loadData();
    }
    private void addRealtimeFilter() {
        DocumentListener listener = new DocumentListener() {
            @Override public void insertUpdate(DocumentEvent e) { applyFilter(); }
            @Override public void removeUpdate(DocumentEvent e) { applyFilter(); }
            @Override public void changedUpdate(DocumentEvent e) {}
        };

        txtMa.getDocument().addDocumentListener(listener);
        txtTen.getDocument().addDocumentListener(listener);
        txtDiaChi.getDocument().addDocumentListener(listener);
        txtSDT.getDocument().addDocumentListener(listener);
    }
}
