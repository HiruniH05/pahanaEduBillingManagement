package servlet;


import javax.servlet.*;
import javax.servlet.http.*;

import model.Customer;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dao.CustomerDAO;

@WebServlet(name = "CustomerServlet", urlPatterns = {"/customers"})
public class CustomerServlet extends HttpServlet {

    private final CustomerDAO customerDAO = new CustomerDAO();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String action = req.getParameter("action");
        String search = req.getParameter("search");

        try {
            if ("edit".equalsIgnoreCase(action)) {
                int id = Integer.parseInt(req.getParameter("id"));
                Customer c = customerDAO.findById(id);
                req.setAttribute("editCustomer", c);
                forwardList(req, resp);
            } else if ("delete".equalsIgnoreCase(action)) {
                int id = Integer.parseInt(req.getParameter("id"));
                customerDAO.delete(id);
                resp.sendRedirect(req.getContextPath() + "/customers");
            } else if (search != null && !search.isBlank()) {
                List<Customer> customers = customerDAO.search(search);
                req.setAttribute("customers", customers);
                req.getRequestDispatcher("customer.jsp").forward(req, resp);
            } else {
                forwardList(req, resp);
            }
        } catch (SQLException e) {
            throw new ServletException(e);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void forwardList(HttpServletRequest req, HttpServletResponse resp) throws SQLException, ServletException, IOException {
        List<Customer> customers = null;
		try {
			customers = customerDAO.findAll();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        req.setAttribute("customers", customers);
        req.getRequestDispatcher("customer.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // create or update based on hidden field
        String idStr = req.getParameter("customer_id");
        Customer c = new Customer();
        c.setName(req.getParameter("name"));
        c.setEmail(req.getParameter("email"));
        c.setPhone(req.getParameter("phone"));
        c.setAddress(req.getParameter("address"));

        if (idStr == null || idStr.isBlank()) {
		    try {
				customerDAO.create(c);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} else {
		    c.setCustomerId(Integer.parseInt(idStr));
		    try {
				customerDAO.update(c);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		resp.sendRedirect(req.getContextPath() + "/customers");
    }
}
