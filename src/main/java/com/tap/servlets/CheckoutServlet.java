package com.tap.servlets;

import com.tap.dao.OrderDAO;
import com.tap.daoimplementation.OrderDAOImpl;
import com.tap.model.Cart;
import com.tap.model.CartItem;
import com.tap.model.Order;
import com.tap.model.Orderitem;
import com.tap.model.User;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@WebServlet("/checkout")
public class CheckoutServlet extends HttpServlet {

    private OrderDAO orderDAO = new OrderDAOImpl();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        System.out.println("---- CheckoutServlet: doGet() started ----");

        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("user");

        if (user == null) {
            System.out.println("User not found in session. Redirecting to login.");
            response.sendRedirect("login.html");
            return;
        }

        Cart cart = (Cart) session.getAttribute("cart");

        if (cart == null || cart.getItems().isEmpty()) {
            System.out.println("Cart is empty. Redirecting to cart page.");
            response.sendRedirect("cart.jsp");
            return;
        }

        // ✅ Calculate total amount
        double totalAmount = calculateTotalAmount(cart.getItems());
        double deliveryFee = 50.00;
        double finalAmount = totalAmount + deliveryFee;

        // ✅ Store values in session
        session.setAttribute("totalAmount", totalAmount);
        session.setAttribute("deliveryFee", deliveryFee);
        session.setAttribute("finalAmount", finalAmount);

        System.out.println("Total Amount: " + totalAmount);
        System.out.println("Delivery Fee: " + deliveryFee);
        System.out.println("Final Amount: " + finalAmount);

        // ✅ Forward to checkout page
        request.getRequestDispatcher("checkout.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        System.out.println("---- CheckoutServlet: doPost() started ----");

        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("user");

        if (user == null) {
            System.out.println("User not found in session. Redirecting to login.");
            response.sendRedirect("login.html");
            return;
        }

        Cart cart = (Cart) session.getAttribute("cart");
        if (cart == null || cart.getItems().isEmpty()) {
            System.out.println("Cart is empty. Redirecting to cart page.");
            response.sendRedirect("cart.jsp");
            return;
        }

        // 🟢 Fetching user inputs
        String fullName = request.getParameter("fullName");
        String phoneNumber = request.getParameter("phoneNumber");
        String streetAddress = request.getParameter("streetAddress");
        String paymentMode = request.getParameter("payment");
        
        session.setAttribute("deliveryAddress", streetAddress);

        if (fullName == null || phoneNumber == null || streetAddress == null || paymentMode == null) {
            System.out.println("⚠️ Missing fields in checkout form!");
            response.sendRedirect("checkout.jsp?error=missingFields");
            return;
        }

        fullName = fullName.trim();
        phoneNumber = phoneNumber.trim();
        streetAddress = streetAddress.trim();
        paymentMode = paymentMode.trim();

        // 🟢 Fetching amounts from session
        Double totalAmount = (Double) session.getAttribute("totalAmount");
        Double deliveryFee = (Double) session.getAttribute("deliveryFee");
        Double finalAmount = (Double) session.getAttribute("finalAmount");

        // 🛠️ If any value is missing, recalculate
        if (totalAmount == null || deliveryFee == null || finalAmount == null) {
            System.out.println("⚠️ Order amount is missing in session. Recalculating...");

            totalAmount = calculateTotalAmount(cart.getItems());
            deliveryFee = 50.00;
            finalAmount = totalAmount + deliveryFee;

            // Store in session
            session.setAttribute("totalAmount", totalAmount);
            session.setAttribute("deliveryFee", deliveryFee);
            session.setAttribute("finalAmount", finalAmount);
        }

        System.out.println("✅ Order Amounts:");
        System.out.println("Total Amount: " + totalAmount);
        System.out.println("Delivery Fee: " + deliveryFee);
        System.out.println("Final Amount: " + finalAmount);

        // 🟢 Creating Order
        Timestamp orderDate = new Timestamp(System.currentTimeMillis());
        Order order = new Order(0, user.getUserId(), 1, orderDate, finalAmount, "Pending", paymentMode);

        // ✅ Save Order in DB
        int orderId = -1;
        try {
            orderId = orderDAO.addOrder(order);
            if (orderId > 0) {
                System.out.println("✅ Order placed successfully! Order ID: " + orderId);

                session.setAttribute("orderId", orderId);

                // 🟢 Ensure Order Items are Stored in DB
                List<Orderitem> orderItems = new ArrayList<>();

                for (CartItem cartItem : cart.getItems().values()) {
                    Orderitem orderItem = new Orderitem();
                    orderItem.setOrderId(orderId);
                    orderItem.setMenuId(cartItem.getId()); // ✅ Set correct Menu ID
                    orderItem.setQuantity(cartItem.getQuantity());
                    orderItem.setTotalPrice(cartItem.getPrice() * cartItem.getQuantity());
                    orderItem.setName(cartItem.getName()); // ✅ Set item name
                    
                    orderItems.add(orderItem);
                }

                if (!orderItems.isEmpty()) {
                    orderDAO.addOrderItems(orderItems, orderId); // ✅ Now calling addOrderItems()
                    System.out.println("✅ Order Items Added Successfully!");
                } else {
                    System.out.println("⚠️ No items found to insert in orderitem table!");
                }

                session.removeAttribute("cart");
                response.sendRedirect("orderConfirmation.jsp");
            } else {
                System.out.println("❌ Order placement failed.");
                response.sendRedirect("checkout.jsp?error=OrderFailed");
            }
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("❌ Database Error: " + e.getMessage());
            response.sendRedirect("error.jsp");
        }

    }

    private double calculateTotalAmount(Map<Integer, CartItem> map) {
        return map.values().stream().mapToDouble(item -> item.getPrice() * item.getQuantity()).sum();
    }

    
}





