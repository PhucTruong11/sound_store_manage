package Frontend.Compoent;

import javax.swing.*;
import java.awt.*;

public class SearchTextField extends JTextField{
    public SearchTextField(String placeholder) {
        setBackground(new Color(240, 242, 245));
        putClientProperty("JTextField.placeholderText", placeholder);
        putClientProperty("JTextField.showClearButton", true);
        putClientProperty("FlatLaf.style", "arc: 20");
        setFont(new Font("Segoe UI", Font.PLAIN, 13));
    }
    
}
