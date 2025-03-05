package com.tap.dao;

import java.util.List;
import com.tap.model.Orderitem;

public interface OrderitemDAO {
    boolean addOrderitem(Orderitem orderitem);
    Orderitem getOrderitem(int orderitemId);
    void updateOrderitem(Orderitem orderitem);
    void deleteOrderitem(int orderitemId);
    List<Orderitem> getAllOrderitems();
    List<Orderitem> getOrderItemsByOrderId(int orderId);
}
