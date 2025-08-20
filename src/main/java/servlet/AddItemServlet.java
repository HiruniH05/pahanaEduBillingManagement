package servlet;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.WebServlet;  // <-- add this

import model.Item;
import java.io.IOException;
import dao.ItemDAO;

@WebServlet("/AddItemServlet")   // <-- mapping here
public class AddItemServlet extends HttpServlet {
    private ItemDAO itemDAO = new ItemDAO();

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Item item = new Item();
        item.setItemName(request.getParameter("itemName"));
        item.setCategory(request.getParameter("category"));
        item.setPrice(Double.parseDouble(request.getParameter("price")));
        item.setStockQuantity(Integer.parseInt(request.getParameter("stockQuantity")));

        try {
            itemDAO.addItem(item);
            response.sendRedirect("ListItemsServlet");
        } catch (Exception e) {
            throw new ServletException(e);
        }
    }
}
