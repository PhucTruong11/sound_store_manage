// package Frontend.GUI.BanHang;

// import Backend.DTO.Amthanh;
// import javax.swing.*;
// import javax.swing.table.DefaultTableModel;
// import java.awt.*;
// import net.miginfocom.swing.MigLayout;

// public class CartPanel extends JPanel {
//     private JTable table;
//     private DefaultTableModel model;
//     private JLabel lblTotal;

//     public CartPanel() {
//         setLayout(new MigLayout("fillx, insets 10", "[fill]", "[][grow][]"));
//         setPreferredSize(new Dimension(400, 0));
//         setBackground(Color.WHITE);

//         add(new JLabel("GIỎ HÀNG"), "center, gapbottom 10");

//         // Bảng giỏ hàng: có thêm cột Checkbox (Boolean)
//         String[] columns = { "Chọn", "Tên SP", "SL", "Thành tiền" };
//         model = new DefaultTableModel(columns, 0) {
//             @Override
//             public Class<?> getColumnClass(int columnIndex) {
//                 return columnIndex == 0 ? Boolean.class : super.getColumnClass(columnIndex);
//             }
//         };
//         table = new JTable(model);
//         add(new JScrollPane(table), "grow, wrap");

//         lblTotal = new JLabel("Tổng: 0 VNĐ");
//         add(lblTotal, "right, wrap");

//         JButton btnPay = new JButton("XÁC NHẬN THANH TOÁN");
//         btnPay.addActionListener(e -> openCheckoutDialog());
//         add(btnPay, "h 45!");
//     }

//     public void addItem(Amthanh amthanh, boolean isSelected) {
//         // Logic: Nếu SP đã có thì tăng SL, chưa có thì thêm dòng mới
//         // isSelected = true nếu bấm "Mua ngay"
//         model.addRow(new Object[] { isSelected, amthanh.getTenMay(), 1, amthanh.getGiaBan() });
//     }

//     private void openCheckoutDialog() {
//         // Mở Dialog để nhập thông tin khách và trừ kho
//     }
// }