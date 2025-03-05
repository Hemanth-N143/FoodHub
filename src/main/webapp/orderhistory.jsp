<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="com.tap.model.User, com.tap.model.Order, com.tap.model.Orderitem, java.util.List, java.util.Map" %>

<%
    User loggedUser = (User) session.getAttribute("user");

    if (loggedUser == null) {
        response.sendRedirect("login.jsp");
        return;
    }

    List<Order> orderList = (List<Order>) request.getAttribute("orderList");
    Map<Integer, List<Orderitem>> orderItemsMap = (Map<Integer, List<Orderitem>>) request.getAttribute("orderItemsMap");
%>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Order History - FoodHub</title>
    <link rel="stylesheet" href="home.css">
    <link rel="stylesheet" href="orderhistory.css">
    <link href="https://fonts.googleapis.com/css2?family=Inter:wght@400;500;600;700&display=swap" rel="stylesheet">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0/css/all.min.css">
    <script type="module" src="https://unpkg.com/ionicons@7.1.0/dist/ionicons/ionicons.esm.js"></script>
    <script nomodule src="https://unpkg.com/ionicons@7.1.0/dist/ionicons/ionicons.js"></script>
</head>
<body>
    <header class="header">
        <nav class="nav">
            <div class="logo">FoodHub</div>
            <ul class="nav-links">
                <li><a href="/FoodHub/home">Home</a></li>
                <li><a href="/FoodHub/cart">Cart</a></li>
                <li><a href="/FoodHub/orderhistory" class="active">Orders</a></li>
                <li><a href="dashboard">
    <ion-icon name="person-circle" class="profile-icon"></ion-icon>
    <span class="nav-text"> 
        <%
             loggedUser = (User) session.getAttribute("user");
            if (loggedUser != null) {
                System.out.println("✅ JSP: User Found in Session - " + loggedUser.getName());
                out.print(loggedUser.getName().split(" ")[0]); // Display first name
            } else {
                System.out.println("❌ JSP: User Not Found in Session");
                out.print("Login");
            }
        %>
    </span>
</a></li>
            </ul>
        </nav>
    </header>

    <main class="history-container">
        <h1>Your Order History</h1>

        <div class="order-list">
            <% if (orderList != null && !orderList.isEmpty()) { %>
                <% for (Order order : orderList) { %>
                    <div class="order-item">
                        <div class="order-header">
                            <div class="order-info">
                                <h3>Order #<%= order.getOrderId() %></h3>
                                <span class="order-date">
                                    <i class="far fa-calendar-alt"></i> <%= order.getOrderDate() %>
                                </span>
                            </div>
                            <span class="order-status 
                                <%= order.getStatus().equals("Delivered") ? "delivered" : "processing" %>">
                                <i class="<%= order.getStatus().equals("Delivered") ? "fas fa-check-circle" : "fas fa-spinner fa-spin" %>"></i> 
                                <%= order.getStatus() %>
                            </span>
                        </div>
                        <div class="order-details">
                            <div class="order-items">
							    <% if (orderItemsMap.containsKey(order.getOrderId())) { %>
							        <% for (Orderitem item : orderItemsMap.get(order.getOrderId())) { %>
							            <p><i class="fas fa-utensils"></i> <%= item.getQuantity() %> x <%= item.getName() %></p>
							        <% } %>
							    <% } else { %>
							        <p>No items found for this order.</p>
							    <% } %>
							</div>

                            <div class="order-total">
                                <span>Total:</span>
                                <span>₹<%= order.getTotalAmount() %></span>
                            </div>
                        </div>
                        <button class="reorder-btn"><i class="fas fa-redo"></i> Reorder</button>
                    </div>
                <% } %>
            <% } else { %>
                <p>No orders found.</p>
            <% } %>
        </div>
    </main>

    <script>
document.addEventListener('DOMContentLoaded', () => {
    const orderItems = document.querySelectorAll('.order-item');
    orderItems.forEach((item, index) => {
        item.style.animationDelay = `${index * 0.2}s`;
    });

    const reorderButtons = document.querySelectorAll('.reorder-btn');
    reorderButtons.forEach(button => {
        button.addEventListener('click', () => {
            button.classList.add('animate-pulse');
            setTimeout(() => {
                button.classList.remove('animate-pulse');
            }, 500);
        });
    });

    const orderStatuses = document.querySelectorAll('.order-status');
    orderStatuses.forEach(status => {
        status.addEventListener('mouseenter', () => {
            status.style.transform = 'scale(1.1)';
        });
        status.addEventListener('mouseleave', () => {
            status.style.transform = 'scale(1)';
        });
    });

    // ✅ Auto-change "Pending" to "Delivered" after 1 minute
    setTimeout(() => {
        document.querySelectorAll('.order-status').forEach(status => {
            if (status.innerText.trim() === 'Pending') {
                status.innerHTML = '<i class="fas fa-check-circle"></i> Delivered';
                status.classList.remove('processing');
                status.classList.add('delivered');
            }
        });
        console.log("✅ Order status changed to Delivered.");
    }, 30000); // 1 minute (60,000 milliseconds)
});
</script>

</body>
</html>
