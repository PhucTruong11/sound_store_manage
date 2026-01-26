package Frontend.GUI.BaoHanh;

import Frontend.Compoent.Table;
import Frontend.Compoent.Theme;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import Backend.DTO.ChiTietBaoHanh;
import Backend.BUS.ChiTietBaoHanhBUS;
import java.awt.*;
import java.util.ArrayList;

import net.miginfocom.swing.MigLayout;

public class ChiTietBaoHanhDialog extends JDialog {
    private String maBH;
    private JTable tblDetails;
    private DefaultTableModel model;
    private ChiTietBaoHanhBUS chiTietBaoHanhBUS;

    public ChiTietBaoHanhDialog(JFrame parent, String maBH) {
        super(parent, "Chi tiết bảo hành: " + maBH, true);
        this.maBH = maBH;
        this.chiTietBaoHanhBUS = new ChiTietBaoHanhBUS();
        setSize(700, 400);
        setLocationRelativeTo(parent);
        setLayout(new BorderLayout(10, 10));
        getContentPane().setBackground(Color.WHITE);

        initComponents();
        loadData();
    }

    private void initComponents() {
        JPanel pnlHeader = new JPanel(new FlowLayout(FlowLayout.CENTER));
        pnlHeader.setBackground(Theme.PRIMARY_COLOR);
        JLabel lblTitle = new JLabel("LỊCH SỬ SỬA CHỮA BẢO HÀNH");
        lblTitle.setForeground(Color.WHITE);
        lblTitle.setFont(new Font("Segoe UI", Font.BOLD, 18));
        pnlHeader.add(lblTitle);
        add(pnlHeader, BorderLayout.NORTH);

        JPanel pnlMain = new JPanel(new MigLayout("fill, insets 20", "[grow]", "[grow]"));
        pnlMain.setBackground(Color.WHITE);

        String[] columns = { "STT", "Mã CTBH", "Mã BH", "Nội Dung", "Tình Trạng" };
        model = new DefaultTableModel(columns, 0) {
            @Override
            public boolean isCellEditable(int r, int c) {
                return false;
            }
        };

        tblDetails = new Table();
        tblDetails.setModel(model);

        JScrollPane scrollPane = new JScrollPane(tblDetails);
        pnlMain.add(scrollPane, "grow");

        add(pnlMain, BorderLayout.CENTER);
    }

    private void loadData() {
        model.setRowCount(0);
        ArrayList<ChiTietBaoHanh> listChiTietBaoHanh = chiTietBaoHanhBUS.getAllChiTietBaoHanh(this.maBH);

        int STT = 1;
        for (ChiTietBaoHanh ctbh : listChiTietBaoHanh) {
            Object[] row = {
                    STT++,
                    ctbh.getMaCTBH(),
                    ctbh.getMaBH(),
                    ctbh.getNoiDung(),
                    ctbh.getTinhTrang(),
            };
            model.addRow(row);
        }
    }
}