package Frontend.GUI.KhachHang;

import Frontend.Compoent.Theme;
import net.miginfocom.swing.MigLayout;
import javax.swing.*;

public class FromHienThiKhachHang extends JPanel {
    private KhachHangToolbar toolbar;
    private KhachHangSidebar sidebar;
    private KhachHangTable table;

    public FromHienThiKhachHang() {
        setLayout(new MigLayout("fill, insets 15", "[280!]15[grow, fill]", "[][grow]"));
        setBackground(Theme.BACKGROUND_COLOR);

        table = new KhachHangTable();
        toolbar = new KhachHangToolbar(table);
        sidebar = new KhachHangSidebar(table);

        add(toolbar, "span 2, growx, wrap, gapbottom 10");
        add(sidebar, "w 280!, growy, cell 0 1");
        add(table, "grow, cell 1 1");
    }
}
