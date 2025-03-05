package com.tap.dao;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import com.tap.model.Order;
import com.tap.model.Orderitem;

public interface OrderDAO {
	
	int addOrder(Order order);
	Order getOrder(int orderId);
	void updateOrder(Order order);
	void deleteOrder(int orderId);
	List<Order>getAllOrders(int orderId);
	List<Order> getRecentOrdersByUserId(int userId);
	void insertOrderItems(List<Orderitem> orderItems, int orderId, Connection connection) throws SQLException;
	void addOrderItems(List<Orderitem> items, int orderId);
	List<Orderitem> getOrderItemsByOrderId(int orderId);
	List<String> getOrderItems(int orderId);
	List<Order> getAllOrders();
}
