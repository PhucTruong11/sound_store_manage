package Frontend.GUI.Nhaphang;

import Frontend.Compoent.Theme;
import net.miginfocom.swing.MigLayout;
import javax.swing.*;

public class FromHienThi extends JPanel {
    private NhapHangToolbar toolbar;
    private NhapHangSidebar sidebar;
    private NhapHangTable table;

    public FromHienThi() {
        setLayout(new MigLayout("fill, insets 10", "[280!]15[grow, fill]", "[][grow]"));
        setBackground(Theme.BACKGROUND_COLOR);

        toolbar = new NhapHangToolbar();
        sidebar = new NhapHangSidebar();
        table = new NhapHangTable(sidebar); // Truyền sidebar vào table để chúng "nói chuyện" với nhau

        add(toolbar, "span 2, growx, wrap, gapbottom 10");
        add(sidebar, "w 280!, growy, cell 0 1");
        add(table, "grow, cell 1 1");
    }
}