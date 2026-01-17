package Frontend.GUI.BanHang;

import javax.swing.*;
import java.awt.*;
import net.miginfocom.swing.MigLayout;
import Frontend.Compoent.Theme;

public class ChiTietSanPhamDialog extends JDialog {
    private InfoPanel pnlInfo;
    private ActionPanel pnlAction;

    public ChiTietSanPhamDialog(JFrame parent, String ten, String gia, String path) {
        super(parent, "Thông tin sản phẩm", true);
        initStyle();

        pnlInfo = new InfoPanel(ten, gia, path);
        pnlAction = new ActionPanel(qty -> {
            this.dispose();
        });

        add(pnlInfo, "wrap");
        add(pnlAction, "growx");
    }

    private void initStyle() {
        setLayout(new MigLayout("fillx, insets 20", "[center]", "[]20[]"));
        setSize(450, 600);
        setLocationRelativeTo(getOwner());
        getContentPane().setBackground(Color.WHITE);
        getRootPane().putClientProperty("FlatLaf.style", "arc:" + Theme.ROUNDING_ARC);
    }
}