<%@ page import="java.sql.*, java.util.*" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <title>Bill Receipt</title>
    <link rel="stylesheet" href="CSS/styles.css">
    <style>
        .receipt {
            margin-top:5rem;
            max-width: 600px;
            margin-left: 24rem;
            padding: 20px;
            border: 2px solid #333;
            border-radius: 10px;
            background: #fff;
            font-family: monospace;
        }
        .receipt h2 {
            text-align: center;
            margin-bottom: 10px;
        }
        .receipt .store-info {
            text-align: center;
            margin-bottom: 20px;
        }
        .receipt table {
            width: 100%;
            border-collapse: collapse;
            margin-bottom: 15px;
        }
        .receipt th, .receipt td {
            padding: 6px;
            border-bottom: 1px solid #ccc;
            text-align: left;
        }
        .receipt tfoot td {
            font-weight: bold;
        }
        .receipt .total {
            text-align: right;
            font-size: 18px;
            font-weight: bold;
        }
        .buttons {
            text-align: center;
            margin-top: 3rem;
        }
        .buttons form, .buttons button {
            display: inline-block;
            margin: 5px;
        }
        .btn {
            padding: 8px 16px;
            background: #007bff;
            color: #fff;
            border: none;
            border-radius: 6px;
            cursor: pointer;
        }
        .btn:hover {
            background: #0056b3;
        }
        .btn-secondary {
            background: #28a745;
        }
        .btn-secondary:hover {
            background: #1e7e34;
        }
    </style>
</head>
<body>

<div class="receipt">
    <h2>Pahana Edu Bookshop</h2>
    <div class="store-info">
        <p>Colombo, Sri Lanka</p>
        <p>Tel: +94 11 2345678</p>
    </div>

    <%
        int billId = Integer.parseInt(request.getParameter("billId"));
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        String customerName = "";
        double totalAmount = 0.0;

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/pahanaedu_db", "root", "12345");

            // Bill + Customer Info
            ps = conn.prepareStatement("SELECT b.total_amount, b.bill_date, c.name FROM bills b JOIN customers c ON b.customer_id = c.customer_id WHERE b.bill_id = ?");
            ps.setInt(1, billId);
            rs = ps.executeQuery();
            if (rs.next()) {
                customerName = rs.getString("name");
                totalAmount = rs.getDouble("total_amount");
                out.println("<p><strong>Bill ID:</strong> " + billId + "</p>");
                out.println("<p><strong>Date:</strong> " + rs.getTimestamp("bill_date") + "</p>");
                out.println("<p><strong>Customer:</strong> " + customerName + "</p>");
            }
            rs.close();
            ps.close();

            // Bill Items
            ps = conn.prepareStatement("SELECT i.item_name, bi.quantity, i.price, bi.subtotal FROM bill_items bi JOIN items i ON bi.item_id = i.item_id WHERE bi.bill_id = ?");
            ps.setInt(1, billId);
            rs = ps.executeQuery();
    %>

    <table>
        <thead>
            <tr>
                <th>Item</th>
                <th>Qty</th>
                <th>Unit Price</th>
                <th>Subtotal</th>
            </tr>
        </thead>
        <tbody>
        <%
            while (rs.next()) {
                out.println("<tr>");
                out.println("<td>" + rs.getString("item_name") + "</td>");
                out.println("<td>" + rs.getInt("quantity") + "</td>");
                out.println("<td>LKR " + rs.getDouble("price") + "</td>");
                out.println("<td>LKR " + rs.getDouble("subtotal") + "</td>");
                out.println("</tr>");
            }
        %>
        </tbody>
        <tfoot>
            <tr>
                <td colspan="3" class="total">TOTAL</td>
                <td>LKR <%= totalAmount %></td>
            </tr>
        </tfoot>
    </table>

    <p style="text-align:center;">Thank you for shopping with us!</p>


    <%
        } catch (Exception e) {
            out.println("<p style='color:red;'>Error loading bill: " + e.getMessage() + "</p>");
        } finally {
            if (rs != null) try { rs.close(); } catch (Exception ignored) {}
            if (ps != null) try { ps.close(); } catch (Exception ignored) {}
            if (conn != null) try { conn.close(); } catch (Exception ignored) {}
        }
    %>
</div>

 <div class="buttons">
 
        <form action="DashboardServlet" method="get">
            <button type="submit" class="btn">Home</button>
        </form>
        
        <button class="btn" onclick="window.print()">🖨 Print Bill</button>
        
        <form action="reports" method="get">
            <button type="submit" class="btn btn-secondary">Get Reports</button>
        </form>
        
        <form action="billing" method="get">
            <button type="submit" class="btn">Generate Another Bill</button>
        </form>
    </div>

</body>
</html>
