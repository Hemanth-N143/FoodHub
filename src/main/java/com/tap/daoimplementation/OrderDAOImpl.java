package com.tap.daoimplementation;

import com.tap.dao.OrderDAO;
import com.tap.model.Order;
import com.tap.model.Orderitem;
import com.tap.utility.DBConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class OrderDAOImpl implements OrderDAO {

	
    private static final String GET_ORDER_QUERY = "SELECT o.*, m.itemName AS itemName, oi.quantity FROM order o JOIN orderitem oi ON o.orderId = oi.orderId JOIN menu m ON oi.menuId = m.menuId WHERE o.orderId = ?";
    private static final String UPDATE_ORDER_QUERY = "UPDATE orders SET userId=?, restaurantId=?, totalAmount=?, status=?, paymentMode=? WHERE orderId=?";
    private static final String DELETE_ORDER_QUERY = "DELETE FROM `orders` WHERE `orderId`=?";
    private static final String GET_ALL_ORDERS_QUERY = "SELECT * FROM `orders` WHERE userId=? ORDER BY orderDate DESC"; 
    private static final String GET_RECENT_ORDERS_QUERY = "SELECT * FROM orders WHERE userId = ? ORDER BY orderDate DESC LIMIT 5";
    private static final String INSERT_ORDER_QUERY =  "INSERT INTO `orders` (userId, restaurantId, orderDate, totalAmount, status, paymentMode) VALUES (?, ?, ?, ?, ?, ?)";
    private static final String INSERT_ORDERITEM_QUERY = "INSERT INTO `orderitem` (orderId, menuId, quantity, totalPrice, name) VALUES (?, ?, ?, ?, ?)";

        @Override
        public int addOrder(Order order) {
            Connection connection = DBConnection.getConnect();
            int orderId = -1;

            try (PreparedStatement ps = connection.prepareStatement(INSERT_ORDER_QUERY, Statement.RETURN_GENERATED_KEYS)) {
                ps.setInt(1, order.getUserId());
                ps.setInt(2, order.getRestaurantId());
                ps.setTimestamp(3, order.getOrderDate());
                ps.setDouble(4, order.getTotalAmount());
                ps.setString(5, order.getStatus());
                ps.setString(6, order.getPaymentMode());

                int affectedRows = ps.executeUpdate();
                if (affectedRows > 0) {
                    try (ResultSet rs = ps.getGeneratedKeys()) {
                        if (rs.next()) {
                            orderId = rs.getInt(1);
                            System.out.println("✅ Order inserted with ID: " + orderId);
                        }
                    }
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }

            // ✅ Ensure Order Items Are Inserted
            if (orderId > 0 && order.getOrderItems() != null && !order.getOrderItems().isEmpty()) {
                addOrderItems(order.getOrderItems(), orderId);
            } else {
                System.out.println("⚠️ No items found to insert for Order ID: " + orderId);
            }

            return orderId;
        }


        @Override
        public void addOrderItems(List<Orderitem> items, int orderId) {
            try (Connection connection = DBConnection.getConnect();
                 PreparedStatement ps = connection.prepareStatement(INSERT_ORDERITEM_QUERY)) {

                for (Orderitem item : items) {
                    ps.setInt(1, orderId);
                    ps.setInt(2, item.getMenuId());
                    ps.setInt(3, item.getQuantity());
                    ps.setDouble(4, item.getTotalPrice());
                    ps.setString(5, item.getName());
                    ps.addBatch();
                    System.out.println("🛒 Adding OrderItem - Order ID: " + orderId + ", Menu ID: " + item.getMenuId());
                }

                ps.executeBatch(); // Execute batch insert
                System.out.println("✅ Order items inserted successfully!");

            } catch (SQLException e) {
                e.printStackTrace();
            }
        }


    

    @Override
    public Order getOrder(int orderId) {
        Order order = null;
        try (Connection connection = DBConnection.getConnect();
             PreparedStatement ps = connection.prepareStatement(GET_ORDER_QUERY)) {
            ps.setInt(1, orderId);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                order = new Order(
                        orderId,
                        rs.getInt("userId"),
                        rs.getInt("restaurantId"),
                        rs.getTimestamp("orderDate"),
                        rs.getDouble("totalAmount"),
                        rs.getString("status"),
                        rs.getString("paymentMode")
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return order;
    }

   

    @Override
    public void updateOrder(Order order) {
        try (Connection connection = DBConnection.getConnect();
             PreparedStatement ps = connection.prepareStatement(UPDATE_ORDER_QUERY)) {
            ps.setInt(1, order.getUserId());
            ps.setInt(2, order.getRestaurantId());
            ps.setDouble(3, order.getTotalAmount());
            ps.setString(4, order.getStatus());
            ps.setString(5, order.getPaymentMode());
            ps.setInt(6, order.getOrderId());
            ps.executeUpdate();
            System.out.println("Order updated successfully for ID: " + order.getOrderId());
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    @Override
    public void deleteOrder(int orderId) {
        try (Connection connection = DBConnection.getConnect();
             PreparedStatement ps = connection.prepareStatement(DELETE_ORDER_QUERY)) {
            ps.setInt(1, orderId);
            ps.executeUpdate();
            System.out.println("Order deleted successfully for ID: " + orderId);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<Order> getAllOrders(int userId) {
        List<Order> orderList = new ArrayList<>();
        try (Connection connection = DBConnection.getConnect();
             PreparedStatement stmt = connection.prepareStatement(GET_ALL_ORDERS_QUERY)) {
            stmt.setInt(1, userId);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                orderList.add(new Order(
                        rs.getInt("orderId"),
                        userId,
                        rs.getInt("restaurantId"),
                        rs.getTimestamp("orderDate"),
                        rs.getDouble("totalAmount"),
                        rs.getString("status"),
                        rs.getString("paymentMode")
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return orderList;
    }
    
    
    @Override
    public List<Orderitem> getOrderItemsByOrderId(int orderId) {
        List<Orderitem> orderItems = new ArrayList<>();
        String query = "SELECT oi.*, m.itemName FROM orderitem oi JOIN menu m ON oi.menuId = m.menuId WHERE oi.orderId = ?";

        try (Connection connection = DBConnection.getConnect();
             PreparedStatement ps = connection.prepareStatement(query)) {
            ps.setInt(1, orderId);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                orderItems.add(new Orderitem(
                        rs.getInt("orderItemId"),
                        rs.getInt("orderId"),
                        rs.getInt("menuId"),
                        rs.getInt("quantity"),
                        rs.getDouble("totalPrice"), null
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return orderItems;
    }
    

    @Override
    public List<Order> getRecentOrdersByUserId(int userId) {
        List<Order> orderList = new ArrayList<>();
        try (Connection connection = DBConnection.getConnect();
                PreparedStatement stmt = connection.prepareStatement(GET_RECENT_ORDERS_QUERY)) {
               
               stmt.setInt(1, userId);
               ResultSet rs = stmt.executeQuery();
               
               System.out.println("🟢 Fetching recent orders for userId: " + userId);

               while (rs.next()) {
                   Order order = new Order(
                       rs.getInt("orderId"),
                       userId,
                       rs.getInt("restaurantId"),
                       rs.getTimestamp("orderDate"),
                       rs.getDouble("totalAmount"),
                       rs.getString("status"),
                       rs.getString("paymentMode")
                   );
                   orderList.add(order);
                   System.out.println("✅ Order Fetched: " + order.getOrderId() + " | Date: " + order.getOrderDate());
               }
               
               System.out.println("🟢 Total Orders Retrieved: " + orderList.size());
               
           } catch (SQLException e) {
               System.out.println("❌ SQL Error in fetching recent orders");
               e.printStackTrace();
           }
           return orderList;
    }

    @Override
    public List<String> getOrderItems(int orderId) {
        List<String> items = new ArrayList<>();
        String query = "SELECT m.itemName FROM orderitem oi JOIN menu m ON oi.menuId = m.menuId WHERE oi.orderId = ?";

        try (Connection connection = DBConnection.getConnect();
             PreparedStatement ps = connection.prepareStatement(query)) {
            ps.setInt(1, orderId);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                items.add(rs.getString("itemName"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return items;
    }

	@Override
	public void insertOrderItems(List<Orderitem> orderItems, int orderId, Connection connection) throws SQLException {
		// TODO Auto-generated method stub
		
	}



	@Override
	public List<Order> getAllOrders() {
		// TODO Auto-generated method stub
		return null;
	}
}
