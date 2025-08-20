package servlet;

import javax.servlet.*;
import javax.servlet.http.*;

import model.Item;

import java.io.IOException;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dao.ItemDAO;

@WebServlet("/ListItemsServlet")

public class ListItemsServlet extends HttpServlet {
    private ItemDAO itemDAO = new ItemDAO();

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            List<Item> items = itemDAO.getAllItems();
            request.setAttribute("items", items);
            RequestDispatcher dispatcher = request.getRequestDispatcher("items.jsp");
            dispatcher.forward(request, response);
        } catch (Exception e) {
            throw new ServletException(e);
        }
    }
}
