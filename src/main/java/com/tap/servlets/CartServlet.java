package com.tap.servlets;

import java.io.IOException;
import com.tap.dao.MenuDAO;
import com.tap.daoimplementation.MenuDAOImpl;
import com.tap.model.Cart;
import com.tap.model.CartItem;
import com.tap.model.Menu;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@WebServlet("/cart")
public class CartServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.sendRedirect("cart.jsp");
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession();
        Cart cart = (Cart) session.getAttribute("cart");

        // Ensure cart is initialized
        if (cart == null) {
            cart = new Cart();
            session.setAttribute("cart", cart);
            System.out.println("🛒 New cart initialized in session");
        }

        String restaurantIdParam = req.getParameter("restaurantId");
        if (restaurantIdParam == null || restaurantIdParam.isEmpty()) {
            resp.sendRedirect("cart.jsp");
            return;
        }

        int newRestaurantId = Integer.parseInt(restaurantIdParam);
        Integer currentRestaurantId = (Integer) session.getAttribute("restaurantId");

        if (currentRestaurantId == null || newRestaurantId != currentRestaurantId) {
            session.setAttribute("restaurantId", newRestaurantId);
        }

        String action = req.getParameter("action");

        try {
            if ("add".equals(action)) {
                addItemToCart(req, cart);
            } else if ("update".equals(action)) {
                updateCartItem(req, cart);
            } else if ("remove".equals(action)) {
                removeItemFromCart(req, cart);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        resp.sendRedirect("cart.jsp");
    }

    private void addItemToCart(HttpServletRequest req, Cart cart) throws ClassNotFoundException {
        try {
            int itemId = Integer.parseInt(req.getParameter("itemId"));
            int quantity = Integer.parseInt(req.getParameter("quantity"));

            MenuDAO menuDAO = new MenuDAOImpl();
            Menu menuItem = menuDAO.getMenu(itemId);

            if (menuItem != null) {
                CartItem existingItem = cart.getItems().get(itemId);
                if (existingItem != null) {
                    existingItem.setQuantity(existingItem.getQuantity() + quantity);
                } else {
                    CartItem newItem = new CartItem(
                        menuItem.getMenuId(),
                        menuItem.getRestaurantId(),
                        menuItem.getItemName(),
                        menuItem.getDescription(),
                        menuItem.getImagePath(),
                        menuItem.getPrice(),
                        quantity
                    );
                    cart.addItem(newItem);
                }
            }
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }
    }

    private void updateCartItem(HttpServletRequest req, Cart cart) {
        String itemIdParam = req.getParameter("itemId");
        String quantityParam = req.getParameter("quantity");

        if (itemIdParam == null || quantityParam == null) {
            return;
        }

        try {
            int itemId = Integer.parseInt(itemIdParam);
            int quantity = Integer.parseInt(quantityParam);

            if (quantity > 0) {
                cart.updateItem(itemId, quantity);
            }
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }
    }

    private void removeItemFromCart(HttpServletRequest req, Cart cart) {
        String itemIdParam = req.getParameter("itemId");

        if (itemIdParam == null || itemIdParam.isEmpty()) {
            return;
        }

        try {
            int itemId = Integer.parseInt(itemIdParam);
            cart.removeItem(itemId);
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }
    }
}
