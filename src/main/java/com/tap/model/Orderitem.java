package com.tap.model;

public class Orderitem {
    
    private int orderitemId;
    private int orderId;
    private int menuId;
    private int quantity;
    private double totalPrice;
    private String name;

    public Orderitem() {}

    public Orderitem(int orderitemId, int orderId, int menuId, int quantity, double totalPrice,String name) {
        this.orderitemId = orderitemId;
        this.orderId = orderId;
        this.menuId = menuId;
        this.quantity = quantity;
        this.totalPrice = totalPrice;
        this.name=name;
    }

    
    
    
    public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getOrderitemId() {
        return orderitemId;
    }

    public void setOrderitemId(int orderitemId) {
        this.orderitemId = orderitemId;
    }

    public int getOrderId() {
        return orderId;
    }

    public void setOrderId(int orderId) {
        this.orderId = orderId;
    }

    public int getMenuId() {
        return menuId;
    }

    public void setMenuId(int menuId) {
        this.menuId = menuId;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public double getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(double totalPrice) {
        this.totalPrice = totalPrice;
    }
}
