<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="java.util.List,com.tap.model.Restaurant,com.tap.model.User" %>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>FoodHub - Order from the best restaurants</title>
    <link rel="stylesheet" href="home.css">
    <link href="https://fonts.googleapis.com/css2?family=Inter:wght@400;500;600;700&display=swap" rel="stylesheet">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0/css/all.min.css">
    <script type="module" src="https://unpkg.com/ionicons@5.5.2/dist/ionicons/ionicons.esm.js"></script>
	<script nomodule src="https://unpkg.com/ionicons@5.5.2/dist/ionicons/ionicons.js"></script>
    
</head>
<body>
    <header class="header">
    <nav class="nav">
        <div class="logo">FoodHub</div>
        <ul class="nav-links">
            <li><a href="#restaurants">Restaurants</a></li>
            <li><a href="#about">About</a></li>
            <li><a href="#contact">Contact</a></li>
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


    <section class="hero">
        <div class="hero-content">
            <h1>Discover the best food & drinks</h1>
            <p>Order from the finest restaurants in your city</p>
            <div class="search-bar">
                <input type="text" placeholder="Search for restaurants or cuisines...">
                <button>Search</button>
            </div>
        </div>
    </section>

     <!-- Categories -->
     <section class="categories scroll-reveal">
        <h2>What's on your mind?</h2>
        <div class="category-grid">
            <div class="category-item">
                <img src="https://images.unsplash.com/photo-1513104890138-7c749659a591" alt="Pizza" class="category-image">
                <p class="category-name">Pizza</p>
            </div>
            <div class="category-item">
                <img src="https://images.unsplash.com/photo-1568901346375-23c9450c58cd" alt="Burgers" class="category-image">
                <p class="category-name">Burgers</p>
            </div>
            <div class="category-item">
                <img src="https://images.unsplash.com/photo-1579871494447-9811cf80d66c" alt="Sushi" class="category-image">
                <p class="category-name">Sushi</p>
            </div>
            <div class="category-item">
                <img src="https://images.unsplash.com/photo-1585937421612-70a008356fbe" alt="Indian" class="category-image">
                <p class="category-name">Indian</p>
            </div>
            <div class="category-item">
                <img src="https://images.unsplash.com/photo-1565299624946-b28f40a0ae38" alt="Chinese" class="category-image">
                <p class="category-name">Chinese</p>
            </div>
            <div class="category-item">
                <img src="https://images.unsplash.com/photo-1565958011703-44f9829ba187" alt="Desserts" class="category-image">
                <p class="category-name">Desserts</p>
            </div>
        </div>
    </section>

     <!-- Top Brands -->
     <section id="brands" class="brands">
        <div class="container">
            <h2>Premium Partners</h2>
            <div class="brands-grid">
                <div class="brand-card animate-fadeIn delay-100">
                    <img src="https://images.unsplash.com/photo-1552566626-52f8b828add9" alt="KFC" class="brand-logo">
                    <h3 class="brand-name">KFC</h3>
                    <p class="brand-info">American • Chicken • Fast Food</p>
                    <div class="brand-stats">
                        <div class="stat">
                            <span class="stat-value">4.8</span>
                            <span class="stat-label">Rating</span>
                        </div>
                        <div class="stat">
                            <span class="stat-value">25min</span>
                            <span class="stat-label">Delivery</span>
                        </div>
                    </div>
                </div>
                
                <div class="brand-card animate-fadeIn delay-200">
                    <img src="https://images.unsplash.com/photo-1513104890138-7c749659a591" alt="Domino's" class="brand-logo">
                    <h3 class="brand-name">Domino's</h3>
                    <p class="brand-info">Pizza • Italian • Fast Food</p>
                    <div class="brand-stats">
                        <div class="stat">
                            <span class="stat-value">4.6</span>
                            <span class="stat-label">Rating</span>
                        </div>
                        <div class="stat">
                            <span class="stat-value">30min</span>
                            <span class="stat-label">Delivery</span>
                        </div>
                    </div>
                </div>

                <div class="brand-card animate-fadeIn delay-300">
                    <img src="https://images.unsplash.com/photo-1568901346375-23c9450c58cd" alt="McDonald's" class="brand-logo">
                    <h3 class="brand-name">McDonald's</h3>
                    <p class="brand-info">Burgers • Fast Food • Drinks</p>
                    <div class="brand-stats">
                        <div class="stat">
                            <span class="stat-value">4.5</span>
                            <span class="stat-label">Rating</span>
                        </div>
                        <div class="stat">
                            <span class="stat-value">20min</span>
                            <span class="stat-label">Delivery</span>
                        </div>
                    </div>
                </div>

                <div class="brand-card animate-fadeIn delay-400">
                    <img src="https://images.unsplash.com/photo-1579871494447-9811cf80d66c" alt="Subway" class="brand-logo">
                    <h3 class="brand-name">Subway</h3>
                    <p class="brand-info">Sandwiches • Healthy • Salads</p>
                    <div class="brand-stats">
                        <div class="stat">
                            <span class="stat-value">4.7</span>
                            <span class="stat-label">Rating</span>
                        </div>
                        <div class="stat">
                            <span class="stat-value">22min</span>
                            <span class="stat-label">Delivery</span>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </section>

    <section class="restaurants scroll-reveal" id="restaurants">
        <h2>Featured Restaurants</h2>
        <div class="restaurants-grid">
        
        <%
        
        List<Restaurant> restaurants = (List<Restaurant>) request.getAttribute("restaurants");

        if (restaurants != null) {
            for (Restaurant r : restaurants) {
        		
        %>
           	<div class="restaurant-card">
            	<a href="menu?restaurantId=<%= r.getRestaurantId() %>" style="text-decoration: none; color: inherit;">
                	<img src=<%= r.getImagePath() %> alt="Indian Restaurant">
                	<div class="restaurant-info">
                    	<h3><%= r.getName() %></h3>
                    	<div class="rating">★ <%= r.getRating() %><span class="reviews">(600+ reviews)</span></div>
                    	<div class="cuisine"><%= r.getCusineType() %> • Curry • Tandoor</div>
                    	<div class="delivery-info">
                        	<i class="fas fa-clock"></i><%= r.getEta() %>
                        	<i class="fas fa-tag"></i> ₹200 for One
                    	</div>
                	</div>
               	</a>
            </div>
         <%
           	 }
        		} else {
         %>
         
         <p>No restaurants available.</p>
         
         <%
    		}
		 %>
            
        </div>
    </section>

    <section class="features scroll-reveal" id="about">
        <h2>Why Choose FoodHub?</h2>
        <div class="features-grid">
            <div class="feature-card">
                <div class="feature-icon">
                    <i class="fas fa-truck"></i>
                </div>
                <h3>Fast Delivery</h3>
                <p>Lightning-fast delivery to your doorstep</p>
            </div>
            <div class="feature-card">
                <div class="feature-icon">
                    <i class="fas fa-utensils"></i>
                </div>
                <h3>Live Tracking</h3>
                <p>Real-time tracking of your order</p>
            </div>
            <div class="feature-card">
                <div class="feature-icon">
                    <i class="fas fa-shield-alt"></i>
                </div>
                <h3>Safe & Secure</h3>
                <p>Contactless delivery for your safety</p>
            </div>
        </div>
    </section>

    <footer class="footer" id="contact">
        <div class="footer-content">
            <div>
                <h4>About Us</h4>
                <ul>
                    <li><a href="#about">Who We Are</a></li>
                    <li><a href="#blog">Blog</a></li>
                    <li><a href="#careers">Careers</a></li>
                </ul>
            </div>
            <div>
                <h4>For Restaurants</h4>
                <ul>
                    <li><a href="#partner">Partner With Us</a></li>
                    <li><a href="#apps">Apps For You</a></li>
                </ul>
            </div>
            <div>
                <h4>Learn More</h4>
                <ul>
                    <li><a href="#privacy">Privacy</a></li>
                    <li><a href="#security">Security</a></li>
                    <li><a href="#terms">Terms</a></li>
                </ul>
            </div>
            <div>
                <h4>Contact Us</h4>
                <ul>
                    <li><a href="#help">Help & Support</a></li>
                    <li><a href="#partner">Partner With Us</a></li>
                    <li><a href="#ride">Ride with us</a></li>
                </ul>
            </div>
        </div>
    </footer>

    <script>
        // Scroll reveal animation
        const scrollReveal = () => {
            const elements = document.querySelectorAll('.scroll-reveal');
            elements.forEach(el => {
                const elementTop = el.getBoundingClientRect().top;
                const windowHeight = window.innerHeight;
                if (elementTop < windowHeight * 0.75) {
                    el.classList.add('revealed');
                }
            });
        };

        window.addEventListener('scroll', scrollReveal);
        window.addEventListener('load', scrollReveal);
    </script>

</body>
</html>