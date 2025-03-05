package com.tap.daoimplementation;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.tap.dao.MenuDAO;
import com.tap.model.Menu;
import com.tap.utility.DBConnection;

public class MenuDAOImpl implements MenuDAO{
	
	private static final String INSERT_MENU_QUERY="INSERT INTO `menu` (`restaurantId`,`itemName`,`description`,`price`,`ratings`,`isAvailable`,`imagePath`) VALUES(?,?,?,?,?,?,?)";
	private static final String GET_MENU_QUERY="SELECT * FROM `menu` WHERE `menuId`=?";
	private static final String UPDATE_MENU_QUERY = "UPDATE `menu` SET `restaurantId`=?, `itemName`=?, `description`=?, `price`=?, `ratings`=?, `isAvailable`=?, `imagePath`=? WHERE `menuId`=?";
	private static final String DELETE_MENU_QUERY="DELETE FROM `menu` WHERE `menuId`=?";
	private static final String GET_ALL_MENU_QUERY="SELECT * FROM menu WHERE `restaurantId`=?";
	
	
	@Override
	public void addMenu(Menu menu) {
		
		try(Connection connection=DBConnection.getConnect();
			PreparedStatement prepareStatement=connection.prepareStatement(INSERT_MENU_QUERY);)
		{
			prepareStatement.setInt(1, menu.getRestaurantId());
			prepareStatement.setString(2, menu.getItemName());
			prepareStatement.setString(3,menu.getDescription());
			prepareStatement.setDouble(4, menu.getPrice());
			prepareStatement.setDouble(5, menu.getRatings());
			prepareStatement.setString(6, menu.getIsAvailable());
			prepareStatement.setString(7, menu.getImagePath());
			
			prepareStatement.executeUpdate();
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	
	
	@Override
	public Menu getMenu(int menuId) {
	    Menu menu = null;
	    try (Connection connection = DBConnection.getConnect();
	         PreparedStatement prepareStatement = connection.prepareStatement(GET_MENU_QUERY)) {
	        
	        prepareStatement.setInt(1, menuId);
	        ResultSet res = prepareStatement.executeQuery();
	        
	        if (res.next()) { // Ensure there is a result
	            int restaurantId = res.getInt("restaurantId");
	            String itemName = res.getString("itemName");
	            String description = res.getString("description");
	            double price = res.getDouble("price");
	            double ratings = res.getDouble("ratings");
	            String isAvailable = res.getString("isAvailable");
	            String imagePath = res.getString("imagePath");
	            
	            menu = new Menu(menuId, restaurantId, itemName, description, price, ratings, isAvailable, imagePath);
	        }
	    } catch (SQLException e) {
	        e.printStackTrace();
	    }
	    return menu;
	}


	
	
	@Override
	public void updateMenu(Menu menu) {
		
		Connection connection;
		PreparedStatement prepareStatement;
		try {
			connection=DBConnection.getConnect();
			prepareStatement=connection.prepareStatement(UPDATE_MENU_QUERY);
			
			prepareStatement.setInt(1, menu.getRestaurantId());
			prepareStatement.setString(2, menu.getItemName());
			prepareStatement.setString(3,menu.getDescription());
			prepareStatement.setDouble(4, menu.getPrice());
			prepareStatement.setDouble(5, menu.getRatings());
			prepareStatement.setString(6, menu.getIsAvailable());
			prepareStatement.setString(7, menu.getImagePath());
			
			prepareStatement.executeUpdate();
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void deleteMenu(int menuId) {
		
		Connection connection;
		PreparedStatement prepareStatement;
		
		try {
			connection=DBConnection.getConnect();
			prepareStatement=connection.prepareStatement(DELETE_MENU_QUERY);
			
			prepareStatement.setInt(1, menuId);
			
			prepareStatement.executeUpdate();
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
	}

	
	
	@Override
	public List<Menu> getAllMenu(int restaurantId) {
	    List<Menu> menuList = new ArrayList<>();
	    try (Connection connection = DBConnection.getConnect();
	         PreparedStatement preparedStatement = connection.prepareStatement(GET_ALL_MENU_QUERY)) {
	        
	        preparedStatement.setInt(1, restaurantId);
	        System.out.println("Executing query for restaurantId: " + restaurantId);

	        try (ResultSet res = preparedStatement.executeQuery()) {
	        	
	            while (res.next()) {
	                int menuId = res.getInt("menuId");
	                String itemName = res.getString("itemName");
	                String description = res.getString("description");
	                double price = res.getDouble("price");
	                double ratings = res.getDouble("ratings");
	                String isAvailable = res.getString("isAvailable");
	                String imagePath = res.getString("imagePath");

	                Menu menu = new Menu(menuId, restaurantId, itemName, description, price, ratings, isAvailable, imagePath);
	                menuList.add(menu);
	                System.out.println("Fetched menu item: " + itemName);
	            }
	        }
	    } catch (SQLException e) {
	        System.err.println("Error fetching menu: " + e.getMessage());
	    }
	    System.out.println("Total menu items fetched: " + menuList.size());
	    return menuList;
	}



}
