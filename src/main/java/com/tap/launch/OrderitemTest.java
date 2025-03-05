package com.tap.launch;

import com.tap.daoimplementation.OrderitemDAOImpl;
import com.tap.model.Orderitem;

public class OrderitemTest {
    public static void main(String[] args) {
        OrderitemDAOImpl orderitemDAO = new OrderitemDAOImpl();
        
        // Creating an OrderItem
        Orderitem orderItem = new Orderitem();
        orderItem.setOrderId(1);  // Ensure orderId exists
        orderItem.setMenuId(2);
        orderItem.setQuantity(3);
        orderItem.setTotalPrice(150.0);
        
        boolean success = orderitemDAO.addOrderitem(orderItem);
        
        if (success) {
            System.out.println("Orderitem added successfully!");
        } else {
            System.out.println("Failed to add Orderitem.");
        }
    }
}
