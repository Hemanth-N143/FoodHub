package com.tap.daoimplementation;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import com.tap.dao.RestaurantDAO;
import com.tap.model.Restaurant;
import com.tap.utility.DBConnection;

public class RestaurantDAOImpl implements RestaurantDAO {
	
	private static final String INSERT_RESTAURANT_QUERY = 
	    "INSERT INTO `restaurant`(`name`,`address`,`phone`,`rating`,`cusineType`,`isActive`,`eta`,`imagePath`) VALUES(?,?,?,?,?,?,?,?)";
	
	private static final String GET_RESTAURANT_QUERY = 
	    "SELECT * FROM `restaurant` WHERE `restaurantId`=?";
	
	private static final String UPDATE_RESTAURANT_QUERY = 
	    "UPDATE `restaurant` SET `name`=?, `address`=?, `phone`=?, `rating`=?, `cusineType`=?, `isActive`=?, `eta`=?, `imagePath`=? WHERE `restaurantId`=?";
	
	private static final String DELETE_RESTAURANT_QUERY = 
	    "DELETE FROM `restaurant` WHERE `restaurantId`=?";
	
	private static final String GET_ALL_RESTAURANTS_QUERY = 
	    "SELECT * FROM restaurant";

	@Override
	public void addRestaurant(Restaurant restaurant) {
		try (Connection connection = DBConnection.getConnect();
		     PreparedStatement prepareStatement = connection.prepareStatement(INSERT_RESTAURANT_QUERY)) {
			
			prepareStatement.setString(1, restaurant.getName());
			prepareStatement.setString(2, restaurant.getAddress());
			prepareStatement.setString(3, restaurant.getPhone());
			prepareStatement.setDouble(4, restaurant.getRating());
			prepareStatement.setString(5, restaurant.getCusineType());
			prepareStatement.setString(6, restaurant.getIsActive());
			prepareStatement.setString(7, restaurant.getEta());
			prepareStatement.setString(8, restaurant.getImagePath());
			
			prepareStatement.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	@Override
	public Restaurant getRestaurant(int restaurantId) {
		Restaurant restaurant = null;

		try (Connection connection = DBConnection.getConnect();
		     PreparedStatement prepareStatement = connection.prepareStatement(GET_RESTAURANT_QUERY)) {
			
			prepareStatement.setInt(1, restaurantId);
			try (ResultSet res = prepareStatement.executeQuery()) {
				if (res.next()) {  // Fix: Check if the result exists before accessing
					String name = res.getString("name");
					String address = res.getString("address");
					String phone = res.getString("phone");
					Double rating = res.getDouble("rating");
					String cusineType = res.getString("cusineType");
					String isActive = res.getString("isActive");
					String eta = res.getString("eta");
					String imagePath = res.getString("imagePath");

					restaurant = new Restaurant(restaurantId, name, address, phone, rating, cusineType, isActive, eta, 0, imagePath);
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return restaurant;
	}

	@Override
	public void updateRestaurant(Restaurant restaurant) {
		try (Connection connection = DBConnection.getConnect();
		     PreparedStatement prepareStatement = connection.prepareStatement(UPDATE_RESTAURANT_QUERY)) {
			
			prepareStatement.setString(1, restaurant.getName());
			prepareStatement.setString(2, restaurant.getAddress());
			prepareStatement.setString(3, restaurant.getPhone());
			prepareStatement.setDouble(4, restaurant.getRating());
			prepareStatement.setString(5, restaurant.getCusineType());
			prepareStatement.setString(6, restaurant.getIsActive());
			prepareStatement.setString(7, restaurant.getEta());
			prepareStatement.setString(8, restaurant.getImagePath());
			prepareStatement.setInt(9, restaurant.getRestaurantId()); // Fix: Add WHERE clause

			prepareStatement.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void deleteRestaurant(int restaurantId) {
		try (Connection connection = DBConnection.getConnect();
		     PreparedStatement preparedStatement = connection.prepareStatement(DELETE_RESTAURANT_QUERY)) {
			
			preparedStatement.setInt(1, restaurantId);
			preparedStatement.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	@Override
	public List<Restaurant> getAllRestaurants() {
		List<Restaurant> restaurantList = new ArrayList<>();

		try (Connection connection = DBConnection.getConnect();
		     Statement statement = connection.createStatement();
		     ResultSet res = statement.executeQuery(GET_ALL_RESTAURANTS_QUERY)) {
			
			while (res.next()) {
				int restaurantId = res.getInt("restaurantId");
				String name = res.getString("name");
				String address = res.getString("address");
				String phone = res.getString("phone");
				Double rating = res.getDouble("rating");
				String cusineType = res.getString("cusineType");
				String isActive = res.getString("isActive");
				String eta = res.getString("eta");
				int adminUserId = res.getInt("adminUserId");
				String imagePath = res.getString("imagePath");

				Restaurant restaurant = new Restaurant(restaurantId, name, address, phone, rating, cusineType, isActive, eta, adminUserId, imagePath);
				restaurantList.add(restaurant);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return restaurantList;
	}
}
