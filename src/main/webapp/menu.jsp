<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    
    <%@ page import="java.util.List,com.tap.model.Menu,com.tap.model.User" %>
    
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Spice Route - FoodHub</title>
    <link rel="stylesheet" href="menu.css">
    <link rel="stylesheet" href="https://fonts.googleapis.com/css2?family=Inter:wght@400;500;600;700&display=swap">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0/css/all.min.css">
    <link rel="stylesheet" href="home.css">
    <link href="https://fonts.googleapis.com/css2?family=Inter:wght@400;500;600;700&display=swap" rel="stylesheet">
    <script type="module" src="https://unpkg.com/ionicons@5.5.2/dist/ionicons/ionicons.esm.js"></script>
	<script nomodule src="https://unpkg.com/ionicons@5.5.2/dist/ionicons/ionicons.js"></script>
</head>
<body>
    <header>
        <nav>
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
    <main class="restaurant-menu">
        <div class="restaurant-info">
           <h1 class="animate-pulse"> <%= request.getAttribute("restaurantName") %></h1>
            <p><%= request.getAttribute("cuisineType") %></p>
            <div class="rating">★ <%= request.getAttribute("restaurantRating") %><span>(600+ reviews)</span></div>
        </div>
        
        <div class="menu-items">
        
       <%
    List<Menu> menuList = (List<Menu>) request.getAttribute("menus");

    if (menuList != null && !menuList.isEmpty()) {
        String restaurantId = request.getParameter("restaurantId");

        // If restaurantId is null in request, get it from session
        if (restaurantId == null || restaurantId.isEmpty()) {
            restaurantId = (String) session.getAttribute("restaurantId");
        }

        for (Menu m : menuList) {
%>

        <div class="menu-item">
            <div class="item-image-container">
                <img src="<%=m.getImagePath()%>" alt="<%= m.getItemName() %>">
                <div class="item-availability available"><%=m.getIsAvailable() %></div>
            </div>
            <div class="item-content">
                <div class="item-header">
                    <h3 class="item-name"><%= m.getItemName() %></h3>
                    <div class="item-rating">
                        <span class="stars">★</span><%= m.getRatings() %>
                    </div>
                </div>
                <p class="item-description"><%= m.getDescription() %></p>
                <div class="item-footer">
                    <span class="price">₹<%= m.getPrice() %></span>

                    <form action="cart" method="post">
                        <input type="hidden" name="restaurantId" value="<%= restaurantId %>">
                        <input type="hidden" name="itemId" value="<%= m.getMenuId() %>">
                        <input type="hidden" name="quantity" value="1"> <!-- Always add 1 -->
                        <input type="hidden" name="action" value="add">
                        <button class="add-to-cart">Add to Cart</button>
                    </form>
                </div>
            </div>
        </div>

<%
        }
    } else {
%>
    <p>No menu items available.</p>
<%
    }
%>

        </div>
    </main>
    
    <footer>
        <div class="footer-content">
            <div>
                <h3>About Us</h3>
                <ul>
                    <li><a href="#">Who We Are</a></li>
                    <li><a href="#">Blog</a></li>
                    <li><a href="#">Careers</a></li>
                </ul>
            </div>
            <div>
                <h3>For Restaurants</h3>
                <ul>
                    <li><a href="#">Partner With Us</a></li>
                    <li><a href="#">Apps For You</a></li>
                </ul>
            </div>
            <div>
                <h3>Learn More</h3>
                <ul>
                    <li><a href="#">Privacy</a></li>
                    <li><a href="#">Security</a></li>
                    <li><a href="#">Terms</a></li>
                </ul>
            </div>
            <div>
                <h3>Contact Us</h3>
                <ul>
                    <li><a href="#">Help & Support</a></li>
                    <li><a href="#">Partner With Us</a></li>
                    <li><a href="#">Ride with us</a></li>
                </ul>
            </div>
        </div>
    </footer>
    <script>
         document.addEventListener('DOMContentLoaded', (event) => {
            const menuItems = document.querySelectorAll('.menu-item');
            menuItems.forEach((item, index) => {
                item.style.animationDelay = `${index * 0.1}s`;
            });

            const addToCartButtons = document.querySelectorAll('.add-to-cart');
            addToCartButtons.forEach(button => {
                button.addEventListener('click', () => {
                    button.classList.add('animate-pulse');
                    setTimeout(() => {
                        button.classList.remove('animate-pulse');
                    }, 1000);

                    // Update cart count
                    const cartCount = document.querySelector('.cart-count');
                    cartCount.textContent = parseInt(cartCount.textContent) + 1;
                });
            });
        });
    </script>
</body>
</html>

    