package com.tap.servlets;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import com.tap.dao.UserDAO;
import com.tap.dao.OrderDAO;
import com.tap.daoimplementation.UserDAOImpl;
import com.tap.daoimplementation.OrderDAOImpl;
import com.tap.model.User;
import com.tap.model.Cart;
import com.tap.model.Order;

import java.io.IOException;
import java.sql.Timestamp;
import java.util.List;

@WebServlet("/loginin")  // Ensures the servlet is correctly mapped
public class LoginInServlet extends HttpServlet {

    private final UserDAO userDAO = new UserDAOImpl();
    private final OrderDAO orderDAO = new OrderDAOImpl();

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {

        String username = req.getParameter("username");
        String password = req.getParameter("password");

        // Debugging inputs
        System.out.println("Username: " + username);
        System.out.println("Password: " + password);

        if (username == null || password == null || username.trim().isEmpty() || password.trim().isEmpty()) {
            resp.sendRedirect("login.html?error=Username or password cannot be empty");
            return;
        }

        username = username.trim();
        password = password.trim();

        // Validate user from database
        User user = userDAO.getUserByUsername(username);

        if (user != null && user.getPassword().equals(password)) {
            HttpSession session = req.getSession(true);
            session.setAttribute("username", user.getName());
            session.setAttribute("user", user);

            // Set the last login date
            Timestamp lastLoginDate = new Timestamp(System.currentTimeMillis());
            session.setAttribute("lastLoginDate", lastLoginDate);

            // Update last login date in the database
            user.setLastLoginDate(lastLoginDate);
            userDAO.updateUser(user);

            // Get user's orders
            List<Order> orders = orderDAO.getAllOrders(user.getUserId());
            session.setAttribute("ordersCount", orders.size());
            session.setAttribute("recentOrders", orders);

            // ✅ Ensure cart is initialized
            if (user.getCart() == null) {
                user.setCart(new Cart()); // Create new cart if null
                System.out.println("🛒 Cart initialized for user: " + user.getName());
            }

            // Redirect to home page
            System.out.println("✅ Login Successful. User stored in session: " + session.getAttribute("user"));
            resp.sendRedirect("home");  // Ensure home.jsp exists, or use a servlet mapped to "home"

        } else {
            // Redirect back to login page with error message
            System.out.println("❌ Login Failed: Invalid credentials");
            resp.sendRedirect("login.html?error=Invalid username or password");
        }
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.sendError(HttpServletResponse.SC_METHOD_NOT_ALLOWED, "GET method is not supported for this endpoint.");
    }
}
