package Frontend.GUI.BanHang;

import javax.swing.*;
import java.awt.*;
import java.util.function.Consumer;
import net.miginfocom.swing.MigLayout;
import Frontend.Compoent.Theme;
import Frontend.Compoent.ButtonAdd;

class ActionPanel extends JPanel {
    public ActionPanel(Consumer<Integer> onAction) {
        setLayout(new MigLayout("fillx, insets 0", "[center]", "[]20[]"));
        setBackground(Color.WHITE);

        JPanel pnlSL = new JPanel(new FlowLayout());
        pnlSL.setBackground(Color.WHITE);
        pnlSL.add(new JLabel("Số lượng:"));
        JSpinner spin = new JSpinner(new SpinnerNumberModel(1, 1, 100, 1));
        pnlSL.add(spin);

        JPanel pnlBtns = new JPanel(new MigLayout("fillx, insets 0", "[grow][grow]", "[45!]"));
        pnlBtns.setBackground(Color.WHITE);

        JButton btnAdd = new JButton("THÊM VÀO GIỎ");
        JButton btnBuy = new JButton("MUA NGAY");

        btnBuy.setBackground(Theme.PRIMARY_COLOR);
        btnBuy.setForeground(Color.WHITE);

        btnAdd.addActionListener(e -> onAction.accept((Integer) spin.getValue()));
        btnBuy.addActionListener(e -> onAction.accept((Integer) spin.getValue()));

        pnlBtns.add(btnAdd, "grow");
        pnlBtns.add(btnBuy, "grow");

        add(pnlSL, "wrap");
        add(pnlBtns, "growx");
    }
}
