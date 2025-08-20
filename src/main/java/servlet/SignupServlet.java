package servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dao.UserDAO;
import model.User;

@WebServlet("/signup")
public class SignupServlet extends HttpServlet {
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
        String role = request.getParameter("role");

        User user = new User(username, password, role);
        boolean success = userDAO.registerUser(user);

        if (success) {
            // Redirect to main page with success flag
            response.sendRedirect("index.html?registered=true");
        } else {
            // Redirect back to signup with error flag
            response.sendRedirect("signup.html?error=true");
        }
    }
}
