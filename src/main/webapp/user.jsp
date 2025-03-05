<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="com.tap.model.User" %>
<%@ page import="com.tap.model.Order" %>
<%@ page import="com.tap.model.Orderitem" %>
<%@ page import="com.tap.daoimplementation.OrderitemDAOImpl" %>
<%@ page import="com.tap.daoimplementation.OrderDAOImpl" %>
<%@ page import="java.util.List" %>
<%@ page import="java.util.Map" %>


<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8" />
    <meta name="viewport" content="width=device-width, initial-scale=1.0" />
    <title>User Profile - FoodHub</title>
    <link rel="shortcut icon" href="images/logo/fevicon.svg" type="image/x-icon" />
    <link rel="stylesheet" href="user.css" />
    <link rel="stylesheet" href="home.css" />
    <link href="https://fonts.googleapis.com/css2?family=Poppins:wght@300;400;500;600&display=swap" rel="stylesheet" />
    <script type="module" src="https://unpkg.com/ionicons@7.1.0/dist/ionicons/ionicons.esm.js"></script>
    <script nomodule src="https://unpkg.com/ionicons@7.1.0/dist/ionicons/ionicons.js"></script>
</head>
<body>
    <header class="header">
        <nav class="nav">
            <div class="logo">FoodHub</div>
            <ul class="nav-links">
                <li><a href="/FoodHub/home">Home</a></li>
                <li><a href="/FoodHub/cart" class="active">Cart <span class="cart-count">2</span></a></li>
                <li><a href="/FoodHub/orderhistory">Orders</a></li>
                <li><a href="dashboard">
    <ion-icon name="person-circle" class="profile-icon"></ion-icon>
    <span class="nav-text"> 
        <%
            User loggedUser = (User) session.getAttribute("user");
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

        <% 
            // Fetch user from request or session
            User user = (User) request.getAttribute("user");
            if (user == null) {
                user = (User) session.getAttribute("loggedInUser"); // Check session if request attribute is null
            }
        %>

        <main class="max-w-7xl mx-auto px-4 py-8 animate-fade-in">
        <div class="grid">
                <!-- Profile Card -->
                <div class="profile-card animate-slide-in-left">
                <div class="bg-white rounded-lg shadow p-6">
                    <div class="flex-col items-center text-center">
                        <img data-src="https://images.unsplash.com/profile-1446404465118-3a53b909cc82?ixlib=rb-0.3.5&q=80&fm=jpg&crop=faces&cs=tinysrgb&fit=crop&h=128&w=128&s=27a346c2362207494baa7b76f5d606e5"
                            alt="Profile Picture"
                            class="profile-image"
                            style="opacity: 0; transition: opacity 0.5s ease;"
                        />
                            
                           <% if (user != null) { %>
                            <h2 class="mt-4 text-xl font-semibold text-gray-900">
                                <%= user.getName() %>
                            </h2>
                            <p class="text-gray-500 text-sm" id="lastLogin"></p>
                            <p class="text-gray-500 text-sm" id="accountCreated"></p>
                            <p class="text-gray-500 text-sm" id="member-since"></p>
                        <% } else { %>
                            <h2 class="mt-4 text-xl font-semibold text-gray-900">Guest</h2>
                            <p class="text-gray-500 text-sm">Please <a href="login.jsp">log in</a>.</p>
                        <% } %>

                            <div class="stats-container">
                            <div class="stat">
                                <p class="text-xl font-semibold text-gray-900">
                                    <%= session.getAttribute("ordersCount") != null ? session.getAttribute("ordersCount").toString() : "0" %>
                                </p>
                                <p class="text-sm text-gray-500">Orders</p>
                            </div>
                            <!-- Add more stats here if needed -->
                        </div>
                        </div>

                        <div class="quick-actions">
                            <a class="action-button" href="orderhistory">📦 My Orders</a>
                            <a class="action-button" href="cart">🛒 Cart</a>
                            <a href="logout" class="action-button danger">🚪 Sign Out</a>
                        </div>
                    </div>
                </div>
                
                 <!-- Details Section -->
                <div class="details-section animate-slide-in-right">
                <div class="bg-white rounded-lg shadow">
                    <div class="p-6 animate-fade-in">
                        <h3 class="text-lg font-semibold text-gray-900 mb-4">
                            Personal Information
                        </h3>
                        <div class="info-grid">
                                <div class="info-item">
                                    <span class="icon">👤</span>
                                    <div>
                                        <p class="text-sm text-gray-500">Full Name</p>
                                        <p class="text-gray-900"><%= user.getName() %></p>
                                    </div>
                                </div>
                                <div class="info-item">
                                    <span class="icon">📧</span>
                                    <div>
                                        <p class="text-sm text-gray-500">Email</p>
                                        <p class="text-gray-900"><%= user.getEmail()%></p>
                                    </div>
                                </div>
                                <div class="info-item">
                                    <span class="icon">📱</span>
                                    <div>
                                        <p class="text-sm text-gray-500">Phone</p>
                                        <p class="text-gray-900"><%= user.getPhone() %></p>
                                    </div>
                                </div>
                                <div class="info-item">
                                    <span class="icon">📍</span>
                                    <div>
                                        <p class="text-sm text-gray-500">Delivery Address</p>
                                        <p class="text-gray-900 user-address "><%= user.getAddress() %></p>
                                    </div>
                                </div>
                            </div>
                        </div>

                <!-- Recent Orders -->
                
                    <!-- Recent Orders -->
						<div class="border-t border-gray-200 p-6 animate-fade-in">
						    <h3 class="text-lg font-semibold text-gray-900 mb-4">Recent Orders</h3>
						    <div class="orders-list">
						        <%
						            // ✅ Fetch recent orders from request, not session
						            List<Order> recentOrders = (List<Order>) request.getAttribute("recentOrders");
						            Map<Integer, List<Orderitem>> orderItemsMap = (Map<Integer, List<Orderitem>>) request.getAttribute("orderItemsMap");
						
						            if (recentOrders != null && !recentOrders.isEmpty()) {
						                for (Order order : recentOrders) {
						                    List<Orderitem> orderItems = orderItemsMap.get(order.getOrderId()); // Get items from map
						
						        %>
						                    <div class="order-item">
						                        <div class="flex items-center space-x-4">
						                            <div class="bg-white p-2 rounded-md">
						                                <span class="icon">📦</span>
						                            </div>
						                            <div>
						                                <p class="font-medium text-gray-900">Order #<%= order.getOrderId() %></p>
						                                <p class="text-sm text-gray-500">
						                                    <%= (orderItems != null) ? orderItems.size() : 0 %> items • ₹ <%= order.getTotalAmount() %>
						                                </p>
						                            </div>
						                        </div>
						                        <div class="flex items-center">
						                            <span class="icon">🕒</span>
						                            <span class="text-sm text-gray-500 recent-order-date"><%= order.getOrderDate() %></span>
						                        </div>
						                    </div>
						        <%
						                }
						            } else { // If no recent orders exist
						        %>
						            <div class="order-item">
						                <p class="text-sm text-gray-500">No recent orders found.</p>
						            </div>
						        <%
						            }
						        %>
						    </div>
						</div>



                </div>
            </div>
        </main>

    <script src="user.js"></script>
</body>
</html>
