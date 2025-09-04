package servlet;


import javax.servlet.*;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;

import com.pahanaEdu.util.DBConnection;

import java.io.IOException;
import java.sql.*;

@WebServlet("/DashboardServlet")

public class DashboardServlet extends HttpServlet {
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        int customerCount = 0;
        int itemCount = 0;
        int billCount = 0;

        try (Connection con = DBConnection.getConnection()) {
            // Total customers
            PreparedStatement ps1 = con.prepareStatement("SELECT COUNT(*) FROM customers");
            ResultSet rs1 = ps1.executeQuery();
            if (rs1.next()) customerCount = rs1.getInt(1);

            // Total items
            PreparedStatement ps2 = con.prepareStatement("SELECT COUNT(*) FROM items");
            ResultSet rs2 = ps2.executeQuery();
            if (rs2.next()) itemCount = rs2.getInt(1);

            // Recent bills (let’s say last 30 days, or all)
            PreparedStatement ps3 = con.prepareStatement("SELECT COUNT(*) FROM bills");
            ResultSet rs3 = ps3.executeQuery();
            if (rs3.next()) billCount = rs3.getInt(1);

        } catch (Exception e) {
            e.printStackTrace();
        }

        // set attributes
        request.setAttribute("customerCount", customerCount);
        request.setAttribute("itemCount", itemCount);
        request.setAttribute("billCount", billCount);

        RequestDispatcher rd = request.getRequestDispatcher("dashboard.jsp");
        rd.forward(request, response);
    }
}
