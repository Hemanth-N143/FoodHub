package com.tap.servlets;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.tap.daoimplementation.OrderDAOImpl;
import com.tap.daoimplementation.OrderitemDAOImpl;  // ✅ Add missing DAO import
import com.tap.model.Order;
import com.tap.model.Orderitem;
import com.tap.model.User;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@WebServlet("/dashboard")
public class DashboardServlet extends HttpServlet {
    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession(false);
        
        if (session != null) {
            User user = (User) session.getAttribute("user");

            if (user != null) {
                System.out.println("🟢 User found in session: " + user.getName() + " (ID: " + user.getUserId() + ")");
                
                // ✅ Correct userId
                int userId = user.getUserId();
                
                OrderDAOImpl orderDAO = new OrderDAOImpl();
                OrderitemDAOImpl orderitemDAO = new OrderitemDAOImpl(); // ✅ Initialize order item DAO
                
                List<Order> recentOrders = orderDAO.getRecentOrdersByUserId(userId);
                Map<Integer, List<Orderitem>> orderItemsMap = new HashMap<>();

                for (Order order : recentOrders) {
                    List<Orderitem> items = orderitemDAO.getOrderItemsByOrderId(order.getOrderId());
                    orderItemsMap.put(order.getOrderId(), items);
                }

                // ✅ Pass data to JSP
                req.setAttribute("user", user);
                req.setAttribute("recentOrders", recentOrders);
                req.setAttribute("orderItemsMap", orderItemsMap); // ✅ Missing attribute added
                
                System.out.println("🟢 Passing " + recentOrders.size() + " orders to user.jsp");
                
                req.getRequestDispatcher("user.jsp").forward(req, resp);
                return;
            } else {
                System.out.println("❌ User not found in session");
            }
        } else {
            System.out.println("❌ No active session found");
        }
        resp.sendRedirect("login.html"); 
    }
}
