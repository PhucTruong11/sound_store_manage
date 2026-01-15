package Frontend.GUI.PhieuNhap;

import Frontend.Compoent.Table;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.DefaultTableCellRenderer;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

public class PhieuNhapTable extends JScrollPane{
    private JTable tbl;
    private DefaultTableModel tblModel;
    private PhieuNhapSidebar sidebar;

    public PhieuNhapTable(PhieuNhapSidebar sidebar) {
        this.sidebar = sidebar;
        initTable();
    }
    
   private void initTable() {
        String[] columns = {"STT", "Mã HĐN", "Ngày Lập", "Nhân Viên", "Số Lượng", "Đơn Giá (VNĐ)"};
        tblModel = new DefaultTableModel(columns, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false; 
            }
        };
        
        tbl = new Table();
        tbl.setModel(tblModel);

        // Căn giữa các cột
        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(JLabel.CENTER);
        tbl.setDefaultRenderer(Object.class, centerRenderer);

        setViewportView(tbl);
        setBorder(BorderFactory.createLineBorder(new Color(230, 230, 230), 1));
   }
}
