package com.tap.servlets;

import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import com.tap.daoimplementation.UserDAOImpl;
import com.tap.model.User;

@WebServlet("/signin")
public class SignInServlet extends HttpServlet {
    
    private UserDAOImpl userDAO = new UserDAOImpl(); // Using DAO implementation

    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String name = req.getParameter("name");
        String username = req.getParameter("username");
        String email = req.getParameter("email");
        String password = req.getParameter("password");
        String phone = req.getParameter("phone");
        String role = req.getParameter("role");
        String address = req.getParameter("address");

        // Check if user already exists
        User existingUserByUsername = userDAO.getUserByUsername(username);
        User existingUserByEmail = userDAO.getUserByEmail(email);

        // Print user details for debugging
        System.out.println("Existing User by Username: " + existingUserByUsername);
        System.out.println("Existing User by Email: " + existingUserByEmail);

        if (existingUserByUsername != null) {
            req.setAttribute("message", "Username already taken. Please choose a different one.");
            req.setAttribute("redirect", "signup");
            req.getRequestDispatcher("error.jsp").forward(req, resp);
        } else if (existingUserByEmail != null) {
            req.setAttribute("message", "Email already in use. Please choose a different one.");
            req.setAttribute("redirect", "signup");
            req.getRequestDispatcher("error.jsp").forward(req, resp);
        } else {
            User user = new User(0, name, username, password, email, phone, address, role, null, null);
            boolean result = userDAO.addUser(user);

            if (result) {
                System.out.println("User registered successfully: " + user);
                resp.sendRedirect("login.html"); // Redirect to login page
            } else {
                req.setAttribute("message", "Registration failed. Please try again.");
                req.setAttribute("redirect", "sign");
                req.getRequestDispatcher("error.jsp").forward(req, resp);
            }
        }
    }
}
