package Frontend.GUI.Nhaphang;

import Frontend.Compoent.SearchTextField;
import Frontend.Compoent.ButtonNhapExcel;
import Frontend.Compoent.ButtonXuatExcel;
import net.miginfocom.swing.MigLayout;
import javax.swing.*;

import Backend.BUS.LoaiSPBUS;
import Backend.DAO.LoaiSPDAO;
import Backend.DTO.ThuocTinhSanPham.LoaiSP;

import java.awt.*;
import java.util.ArrayList;

public class NhapHangToolbar extends JPanel{
    private NhapHangTable table;
    private JComboBox<LoaiSP> cbFilter;

    public NhapHangToolbar(NhapHangTable table) {
        this.table = table;
        setLayout(new MigLayout("insets 10", "[grow]10[]10[]"));
        setBackground(Color.WHITE);
        putClientProperty("FlatLaf.style", "arc: 20");
        setOpaque(false);

        SearchTextField txtSearch = new SearchTextField("Tìm kiếm tên SP, mã SP...");
        add(txtSearch, "growx, h 35!");

        txtSearch.addKeyListener(new java.awt.event.KeyAdapter() {
            @Override
            public void keyReleased(java.awt.event.KeyEvent e) {
                String query = txtSearch.getText().trim();
                table.loadDataBySearch(query);
            }
        });

        cbFilter = new JComboBox<>();
        DefaultComboBoxModel<LoaiSP> model = new DefaultComboBoxModel<>();
        model.addElement(new LoaiSP("All", "Tất cả sản phẩm"));

        LoaiSPBUS loaiBUS = new LoaiSPBUS();
        ArrayList<LoaiSP> listLoai = loaiBUS.getAll();
        for(LoaiSP l : listLoai) {
            model.addElement(l);
        }
        cbFilter.setModel(model);

        cbFilter.addActionListener(e -> {
            LoaiSP selected = (LoaiSP) cbFilter.getSelectedItem();
            if(selected != null) {
                table.loadDataByLoai(selected.getMaLoai());
            }
        });

        cbFilter.setPreferredSize(new Dimension(200, 35));
        cbFilter.putClientProperty("FlatLaf.style", "arc: 10");
        add(cbFilter, "w 200!");
    }
}
