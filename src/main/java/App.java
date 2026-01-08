import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import Frontend.MainFrame;
import com.formdev.flatlaf.FlatLightLaf; // Hoặc FlatDarkLaf nếu thích chế độ tối
import javax.swing.*;
import java.awt.*;

public class App {
    public static void main( String[] args ) {
        try {
            // Kích hoạt giao diện FlatLaf
            FlatLightLaf.setup();

            // Cấu hình bo tròn góc (Arc) toàn hệ thống
            UIManager.put("Button.arc", 15);         // Bo tròn cho nút bấm
            UIManager.put("Component.arc", 15);      // Bo tròn cho các ô nhập liệu, combobox
            UIManager.put("TextComponent.arc", 15);  // Bo tròn cho JTextField
            UIManager.put("CheckBox.arc", 5);        // Bo tròn nhẹ cho checkbox

            // Chỉnh màu sắc focus cho hiện đại
            UIManager.put("Component.focusWidth", 1);
            UIManager.put("Button.innerFocusWidth", 0);
            
            // Thêm hiệu ứng focus cho đẹp
            UIManager.put("Component.focusWidth", 2); 
            UIManager.put("Button.innerFocusWidth", 1);

        } catch (Exception ex) {
            System.err.println("Lỗi khởi tạo giao diện!");
        }

       SwingUtilities.invokeLater(() -> {
            MainFrame frame = new MainFrame();
            frame.setVisible(true);
        });
    }
}