// package Frontend.GUI.BanHang;

// import Backend.DTO.Amthanh;
// import javax.swing.*;
// import java.awt.*;
// import Frontend.Compoent.Theme;

// public class BanHangPanel extends JPanel {
//     private ProductListPanel productList; // Bên trái: Danh sách Loa/Tai nghe
//     private CartPanel cartPanel;           // Bên phải: Giỏ hàng

//     public BanHangPanel() {
//         setLayout(new BorderLayout(15, 15));
//         setBackground(Theme.BACKGROUND_COLOR);
//         setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));

//         productList = new ProductListPanel(this);
//         cartPanel = new CartPanel();

//         add(productList, BorderLayout.CENTER);
//         add(cartPanel, BorderLayout.EAST);
//     }

//     // Hàm để ProductListPanel gọi khi bấm nút
//     public void handleAction(SanPhamDTO sp, boolean buyNow) {
//         cartPanel.addItem(sp, buyNow);
//         if (buyNow) {
//             // Nếu mua ngay, có thể tự động cuộn đến phần thanh toán
//         }
//     }
// }