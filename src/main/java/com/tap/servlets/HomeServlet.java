package com.tap.servlets;

import java.io.IOException;
import java.util.List;

import com.tap.daoimplementation.RestaurantDAOImpl;
import com.tap.model.Restaurant;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/home")
public class HomeServlet  extends HttpServlet{
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
		System.out.println("HomeServlet Called");
		
		RestaurantDAOImpl resDAO=new RestaurantDAOImpl();
		
		List<Restaurant> allRes=resDAO.getAllRestaurants();
		
		req.setAttribute("restaurants", allRes);
		
//		System.out.println("Forwarding to home.jsp");
		RequestDispatcher rd = req.getRequestDispatcher("home.jsp");
		rd.forward(req, resp);
//		System.out.println("Forward completed");

		
	}

}
