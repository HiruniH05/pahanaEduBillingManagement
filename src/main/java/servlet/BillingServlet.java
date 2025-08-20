package servlet;



import javax.servlet.*;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;

import dao.BillDAO;
import dao.CustomerDAO;
import dao.ItemDAO;
import dao.PaymentDAO;
import model.BillItem;
import model.Customer;
import model.Item;
import model.Payment;

import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@WebServlet(name = "BillingServlet", urlPatterns = {"/billing"})
public class BillingServlet extends HttpServlet {

    private final CustomerDAO customerDAO = new CustomerDAO();
    private final ItemDAO itemDAO = new ItemDAO();
    private final BillDAO billDAO = new BillDAO();
    private final PaymentDAO paymentDAO = new PaymentDAO();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        List<Customer> customers = new ArrayList<>();
        List<Item> items = new ArrayList<>();
        try {
            customers = customerDAO.findAll();
        } catch (Exception e) {
            e.printStackTrace();
        }
        try {
            items = itemDAO.getAllItems();
        } catch (Exception e) {
            e.printStackTrace();
        }
        req.setAttribute("customers", customers);
        req.setAttribute("items", items);
        req.getRequestDispatcher("billing.jsp").forward(req, resp);
    }


    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // Expect arrays: item_id[], quantity[]
        int customerId = Integer.parseInt(req.getParameter("customer_id"));
        // If you store user_id in session upon login:
        Integer userId = (Integer) req.getSession().getAttribute("user_id");
        if (userId == null) userId = 1; // fallback if not set

        String[] itemIds = req.getParameterValues("item_id");
        String[] quantities = req.getParameterValues("quantity");
        String paymentMethod = req.getParameter("payment_method");

        if (itemIds == null || quantities == null || itemIds.length == 0) {
            req.setAttribute("error", "Please add at least one item.");
            doGet(req, resp);
            return;
        }

        try {
            // Calculate totals
            double grandTotal = 0;
            List<BillItem> billItems = new ArrayList<>();
            for (int i = 0; i < itemIds.length; i++) {
                int itemId = Integer.parseInt(itemIds[i]);
                int qty = Integer.parseInt(quantities[i]);
                if (qty <= 0) continue;

                Item item = itemDAO.findById(itemId);
                double sub = item.getPrice() * qty;

                BillItem bi = new BillItem();
                bi.setItemId(itemId);
                bi.setQuantity(qty);
                bi.setSubtotal(sub);

                billItems.add(bi);
                grandTotal += sub;
            }

            // Create bill header
            int billId = billDAO.createBillHeader(customerId, userId, grandTotal);
            // Insert bill items
            billDAO.addBillItems(billId, billItems);
            // Update total just to be safe (already set)
            billDAO.updateBillTotal(billId, grandTotal);

            // Record payment
            Payment p = new Payment();
            p.setBillId(billId);
            p.setAmountPaid(grandTotal);
            p.setPaymentMethod(paymentMethod);
            paymentDAO.addPayment(p);

            resp.sendRedirect("bill_success.jsp?billId=" + billId);

        } catch (Exception e) {
            throw new ServletException("Billing failed", e);
        }
    }
}
