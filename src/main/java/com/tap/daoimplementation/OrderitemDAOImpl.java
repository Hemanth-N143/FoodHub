package com.tap.daoimplementation;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import com.tap.dao.OrderitemDAO;
import com.tap.model.Orderitem;
import com.tap.utility.DBConnection;

public class OrderitemDAOImpl implements OrderitemDAO {
    
    
    private static final String GET_ORDERITEM_QUERY = "SELECT * FROM `orderitem` WHERE `orderitemId`=?";
    private static final String UPDATE_ORDERITEM_QUERY = "UPDATE `orderitem` SET `orderId`=?, `menuId`=?, `quantity`=?, `totalPrice`=? WHERE `orderitemId`=?";
    private static final String DELETE_ORDERITEM_QUERY = "DELETE FROM `orderitem` WHERE `orderitemId`=?";
    private static final String GET_ALL_ORDERITEMS_QUERY = "SELECT * FROM `orderitem`";
    private static final String INSERT_ORDERITEM_QUERY =  "INSERT INTO `orderitem` (orderId, menuId, quantity, totalPrice, name) VALUES (?, ?, ?, ?, ?)";
    private static final String GET_ORDERITEMS_BY_ORDERID_QUERY = "SELECT * FROM `orderitem` WHERE orderId = ?";

        @Override
        public boolean addOrderitem(Orderitem orderitem) {
            boolean isInserted = false;
            try (Connection connection = DBConnection.getConnect();
                 PreparedStatement ps = connection.prepareStatement(INSERT_ORDERITEM_QUERY)) {

                ps.setInt(1, orderitem.getOrderId());
                ps.setInt(2, orderitem.getMenuId());
                ps.setInt(3, orderitem.getQuantity());
                ps.setDouble(4, orderitem.getTotalPrice());
                ps.setString(5, orderitem.getName()); // ✅ Ensure name is stored

                int rowsAffected = ps.executeUpdate();
                isInserted = rowsAffected > 0;
                if (isInserted) {
                    System.out.println("✅ OrderItem inserted: " + orderitem.getName());
                } else {
                    System.out.println("⚠️ OrderItem insertion failed");
                }

            } catch (SQLException e) {
                e.printStackTrace();
            }
            return isInserted;
        }


        @Override
        public List<Orderitem> getOrderItemsByOrderId(int orderId) {
            List<Orderitem> orderItems = new ArrayList<>();

            try (Connection connection = DBConnection.getConnect();
                 PreparedStatement ps = connection.prepareStatement(GET_ORDERITEMS_BY_ORDERID_QUERY)) {

                ps.setInt(1, orderId);
                ResultSet rs = ps.executeQuery();

                while (rs.next()) {
                    int orderitemId = rs.getInt("orderitemId");
                    int menuId = rs.getInt("menuId");
                    int quantity = rs.getInt("quantity");
                    double totalPrice = rs.getDouble("totalPrice");
                    String name = rs.getString("name");

                    orderItems.add(new Orderitem(orderitemId, orderId, menuId, quantity, totalPrice, name));
                    System.out.println("🛒 Retrieved OrderItem: " + name + " for Order ID: " + orderId);
                }

            } catch (SQLException e) {
                e.printStackTrace();
            }

            if (orderItems.isEmpty()) {
                System.out.println("⚠️ No order items found for Order ID: " + orderId);
            }

            return orderItems;
        }


    @Override
    public Orderitem getOrderitem(int orderitemId) {
        Orderitem orderitem = null;
        
        try (Connection connection = DBConnection.getConnect();
             PreparedStatement prepareStatement = connection.prepareStatement(GET_ORDERITEM_QUERY)) {
            prepareStatement.setInt(1, orderitemId);
            ResultSet res = prepareStatement.executeQuery();
            
            if (res.next()) {
                int orderId = res.getInt("orderId");
                int menuId = res.getInt("menuId");
                int quantity = res.getInt("quantity");
                double totalPrice = res.getDouble("totalPrice");

                orderitem = new Orderitem(orderitemId, orderId, menuId, quantity, totalPrice, null);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return orderitem;
    }

    @Override
    public void updateOrderitem(Orderitem orderitem) {
        try (Connection connection = DBConnection.getConnect();
             PreparedStatement prepareStatement = connection.prepareStatement(UPDATE_ORDERITEM_QUERY)) {
            prepareStatement.setInt(1, orderitem.getOrderId());
            prepareStatement.setInt(2, orderitem.getMenuId());
            prepareStatement.setInt(3, orderitem.getQuantity());
            prepareStatement.setDouble(4, orderitem.getTotalPrice());
            prepareStatement.setInt(5, orderitem.getOrderitemId());
            
            prepareStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void deleteOrderitem(int orderitemId) {
        try (Connection connection = DBConnection.getConnect();
             PreparedStatement prepareStatement = connection.prepareStatement(DELETE_ORDERITEM_QUERY)) {
            prepareStatement.setInt(1, orderitemId);
            prepareStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<Orderitem> getAllOrderitems() {
        List<Orderitem> orderitemList = new ArrayList<>();
        
        try (Connection connection = DBConnection.getConnect();
             Statement statement = connection.createStatement()) {
            ResultSet res = statement.executeQuery(GET_ALL_ORDERITEMS_QUERY);
            
            while (res.next()) {
                int orderitemId = res.getInt("orderitemId");
                int orderId = res.getInt("orderId");
                int menuId = res.getInt("menuId");
                int quantity = res.getInt("quantity");
                double totalPrice = res.getDouble("totalPrice");

                Orderitem orderitem = new Orderitem(orderitemId, orderId, menuId, quantity, totalPrice, null);
                orderitemList.add(orderitem);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        
        return orderitemList;
    }

    

}
