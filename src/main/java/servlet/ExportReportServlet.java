package servlet;

import java.io.IOException;
import java.sql.*;
import javax.servlet.*;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.*;

import com.pahanaEdu.util.DBConnection;

import java.util.Date;


@WebServlet("/ExportReportServlet")
public class ExportReportServlet extends HttpServlet {
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
        throws ServletException, IOException {

        response.setContentType("application/pdf");
        response.setHeader("Content-Disposition", "attachment; filename=SalesReport.pdf");

        try {
            Document document = new Document();
            PdfWriter.getInstance(document, response.getOutputStream());
            document.open();

            // Title
            Font titleFont = new Font(Font.FontFamily.HELVETICA, 18, Font.BOLD);
            Paragraph title = new Paragraph("Pahana Edu Bookshop - Sales Report\n\n", titleFont);
            title.setAlignment(Element.ALIGN_CENTER);
            document.add(title);

            // Date
            Paragraph date = new Paragraph("Generated on: " + new Date().toString() + "\n\n");
            document.add(date);

            Connection con = DBConnection.getConnection();
            String sql = "SELECT b.bill_id, c.name as customer_name, i.item_name, bi.quantity, i.price, bi.subtotal, b.total_amount, b.bill_date, u.username " +
                         "FROM bills b " +
                         "JOIN customers c ON b.customer_id = c.customer_id " +
                         "JOIN bill_items bi ON b.bill_id = bi.bill_id " +
                         "JOIN items i ON bi.item_id = i.item_id " +
                         "JOIN users u ON b.user_id = u.user_id " +
                         "ORDER BY b.bill_id DESC";

            PreparedStatement ps = con.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();

            PdfPTable table = new PdfPTable(7); // columns
            table.setWidthPercentage(100);
            table.setSpacingBefore(10);

            // Headers
            String[] headers = {"Bill ID", "Customer", "Item", "Qty", "Unit Price", "Subtotal", "Cashier"};
            for (String h : headers) {
                PdfPCell cell = new PdfPCell(new Phrase(h, new Font(Font.FontFamily.HELVETICA, 12, Font.BOLD)));
                cell.setBackgroundColor(BaseColor.LIGHT_GRAY);
                table.addCell(cell);
            }

            // Data
            while (rs.next()) {
                table.addCell(rs.getString("bill_id"));
                table.addCell(rs.getString("customer_name"));
                table.addCell(rs.getString("item_name"));
                table.addCell(rs.getString("quantity"));
                table.addCell("LKR " + rs.getBigDecimal("price"));
                table.addCell("LKR " + rs.getBigDecimal("subtotal"));
                table.addCell(rs.getString("username"));
            }

            document.add(table);

            document.close();
            con.close();
        } catch (Exception e) {
            throw new ServletException(e);
        }
    }
}
