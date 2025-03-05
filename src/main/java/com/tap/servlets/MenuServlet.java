package com.tap.servlets;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.tap.daoimplementation.MenuDAOImpl;
import com.tap.daoimplementation.RestaurantDAOImpl;
import com.tap.model.Menu;
import com.tap.model.Restaurant;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/menu")
public class MenuServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        System.out.println("MenuServlet Called");
        String restaurantIdParam = req.getParameter("restaurantId");
        if (restaurantIdParam == null || restaurantIdParam.isEmpty()) {
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "Restaurant ID is required.");
            return;
        }
        int rid = Integer.parseInt(restaurantIdParam);
        MenuDAOImpl menuDao = new MenuDAOImpl();
        RestaurantDAOImpl restaurantDao = new RestaurantDAOImpl();

        try {
            // Fetch restaurant object
            Restaurant restaurant = restaurantDao.getRestaurant(rid);

            if (restaurant != null) {
                req.setAttribute("restaurantName", restaurant.getName());
                req.setAttribute("cuisineType", restaurant.getCusineType());
                req.setAttribute("restaurantRating", restaurant.getRating());
            } else {
                req.setAttribute("restaurantName", "Unknown Restaurant");
                req.setAttribute("cuisineType", "N/A");
                req.setAttribute("restaurantRating", "N/A");
            }
            // Fetch menu list
            List<Menu> menuList = menuDao.getAllMenu(rid);
            req.setAttribute("menus", menuList != null ? menuList : new ArrayList<>());
        } catch (Exception e) {
            e.printStackTrace();
            req.setAttribute("menus", new ArrayList<>()); // Avoid null
        }

        RequestDispatcher rd = req.getRequestDispatcher("menu.jsp");
        rd.forward(req, resp);
    }
}
