package servlet;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.WebServlet;
import java.io.IOException;

import dao.ItemDAO;

@WebServlet("/DeleteItemServlet")
public class DeleteItemServlet extends HttpServlet {
    private ItemDAO itemDAO = new ItemDAO();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        String idParam = request.getParameter("id");

        if (idParam != null) {
            try {
                int id = Integer.parseInt(idParam);
                itemDAO.deleteItem(id);
            } catch (Exception e) {
                throw new ServletException(e);
            }
        }

        // Redirect back to item list after deletion
        response.sendRedirect("ListItemsServlet");
    }
}
