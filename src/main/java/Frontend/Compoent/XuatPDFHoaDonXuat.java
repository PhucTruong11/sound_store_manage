package Frontend.Compoent;

import com.itextpdf.io.font.PdfEncodings;
import com.itextpdf.kernel.font.PdfFont;
import com.itextpdf.kernel.font.PdfFontFactory;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Table;
import com.itextpdf.layout.element.Cell;
import com.itextpdf.layout.properties.TextAlignment;
import com.itextpdf.kernel.colors.ColorConstants;
import Backend.DTO.PhieuXuat;
import Backend.BUS.PhieuXuatBUS;
import javax.swing.table.DefaultTableModel;
import java.util.Date;
import java.io.File;
import java.awt.Desktop;

public class XuatPDFHoaDonXuat {
    public static void xuatHoaDonNhap(PhieuXuat px, DefaultTableModel model) {
        try {
            if (model.getColumnCount() < 6) {
                System.out.println("LỖI: Model truyền vào PDF không đủ cột dữ liệu!");
                return;
            }
            PhieuXuatBUS pxBUS = new PhieuXuatBUS();
            double phanTramGiam = pxBUS.getPhanTramGiamCuaPhieu(px.getMaPhieuXuat());

            String fileName = "HoaDonXuat_" + px.getMaPhieuXuat() + ".pdf";
            PdfWriter writer = new PdfWriter(fileName);
            PdfDocument pdf = new PdfDocument(writer);
            Document document = new Document(pdf);

            PdfFont font = PdfFontFactory.createFont("C:/Windows/Fonts/Arial.ttf", PdfEncodings.IDENTITY_H);

            document.add(new Paragraph("PHIẾU BÁN HÀNG")
                    .setFont(font).setFontSize(18).setBold()
                    .setTextAlignment(TextAlignment.CENTER));
            document.add(new Paragraph("\n"));

            document.add(new Paragraph("Mã phiếu: " + px.getMaPhieuXuat()).setFont(font));
            document.add(new Paragraph("Nhân viên lập: " + px.getMaNV()).setFont(font));
            document.add(new Paragraph("Ngày lập: " + new Date().toString()).setFont(font));
            document.add(new Paragraph("\n"));

            float[] columnWidths = { 30f, 180f, 40f, 80f, 100f };
            Table table = new Table(columnWidths);
            table.useAllAvailableWidth();

            String[] headers = { "STT", "Tên Sản Phẩm", "SL", "Đơn Giá", "Thành Tiền" };
            for (String h : headers) {
                table.addHeaderCell(new Cell().add(new Paragraph(h).setFont(font).setBold())
                        .setTextAlignment(TextAlignment.CENTER)
                        .setBackgroundColor(ColorConstants.LIGHT_GRAY));
            }

            double totalInvoice = 0;

            for (int i = 0; i < model.getRowCount(); i++) {
                // Cột 0: STT
                table.addCell(new Cell().add(new Paragraph(model.getValueAt(i, 0).toString()).setFont(font))
                        .setTextAlignment(TextAlignment.CENTER));

                // Cột 2: Tên Sản Phẩm
                table.addCell(new Cell().add(new Paragraph(model.getValueAt(i, 2).toString()).setFont(font)));

                // Cột 3: Số lượng
                table.addCell(new Cell().add(new Paragraph(model.getValueAt(i, 3).toString()).setFont(font))
                        .setTextAlignment(TextAlignment.CENTER));

                // Cột 4: Đơn giá
                String giaStr = model.getValueAt(i, 4).toString().replaceAll("[^0-9]", "");
                double donGia = Double.parseDouble(giaStr);
                int sl = Integer.parseInt(model.getValueAt(i, 3).toString());

                double thanhTienSauGiamRow = (donGia * sl) * (1 - phanTramGiam / 100.0);
                totalInvoice += thanhTienSauGiamRow;

                table.addCell(new Cell().add(new Paragraph(String.format("%,.0f", donGia)).setFont(font))
                        .setTextAlignment(TextAlignment.RIGHT));
                // Cột 5: thành tiền
                table.addCell(new Cell().add(new Paragraph(String.format("%,.0f", thanhTienSauGiamRow)).setFont(font))
                        .setTextAlignment(TextAlignment.RIGHT));
            }

            table.addCell(new Cell(1, 4).add(new Paragraph("TỔNG TIỀN THANH TOÁN").setFont(font).setBold())
                    .setTextAlignment(TextAlignment.RIGHT));

            table.addCell(new Cell().add(new Paragraph(String.format("%,.0f VNĐ", totalInvoice)).setFont(font).setBold())
                            .setFontColor(ColorConstants.RED)
                            .setTextAlignment(TextAlignment.RIGHT));

            document.add(table);
            document.close();

            File file = new File(fileName);
            if (Desktop.isDesktopSupported()) {
                Desktop.getDesktop().open(file);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}