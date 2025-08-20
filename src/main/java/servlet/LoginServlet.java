package servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import dao.UserDAO;

@WebServlet("/login")
public class LoginServlet extends HttpServlet {
	private UserDAO userDAO;

    @Override
    public void init() throws ServletException {
        try {
            userDAO = new UserDAO();
        } catch (Exception e) {
            throw new ServletException(e); 
        }
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String username = request.getParameter("username");
        String password = request.getParameter("password");

        String role = userDAO.loginUser(username, password);

        if (role != null) {
            // Store session
            HttpSession session = request.getSession();
            session.setAttribute("username", username);
            session.setAttribute("role", role);

            // Redirect based on role
            switch (role) {
                case "ADMIN":
                    response.sendRedirect("index.html");
                    break;
                case "CASHIER":
                    response.sendRedirect("index.html");
                    break;
                case "CUSTOMER":
                    response.sendRedirect("index.html");
                    break;
            }
            
            
        } else {
            response.sendRedirect("login.html?error=true");
        }
    }
    
    
    
}
