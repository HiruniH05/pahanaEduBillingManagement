package servlet;


import javax.servlet.*;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;

import dao.ReportDAO;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

@WebServlet(name = "ReportsServlet", urlPatterns = {"/reports"})
public class ReportsServlet extends HttpServlet {

    private final ReportDAO reportDAO = new ReportDAO();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        List<Map<String, Object>> daily = null;
		try {
			daily = reportDAO.dailySales();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		List<Map<String, Object>> topItems = null;
		try {
			topItems = reportDAO.topItems();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		req.setAttribute("dailySales", daily);
		req.setAttribute("topItems", topItems);
		req.getRequestDispatcher("reports.jsp").forward(req, resp);
    }
}
