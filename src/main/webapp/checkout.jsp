<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page import="com.tap.model.User, com.tap.model.CartItem, com.tap.model.Cart, java.util.Map" %>

<%
    User loggedUser = (User) session.getAttribute("user");
    Cart cart = (Cart) session.getAttribute("cart");
    
    if (cart == null) {
        cart = new Cart(); // Ensure cart is not null
        session.setAttribute("cart", cart);
    }
%>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Checkout - FoodHub</title>
    <link rel="stylesheet" href="home.css">
    <link rel="stylesheet" href="checkout.css">
    <link href="https://fonts.googleapis.com/css2?family=Poppins:wght@300;400;500;600;700&display=swap" rel="stylesheet">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0/css/all.min.css">
    <script type="module" src="https://unpkg.com/ionicons@5.5.2/dist/ionicons/ionicons.esm.js"></script>
	<script nomodule src="https://unpkg.com/ionicons@5.5.2/dist/ionicons/ionicons.js"></script>
</head>
<body>
<header class="header">
    <nav class="nav">
        <div class="logo">FoodHub</div>
        <ul class="nav-links">
            <li><a href="/FoodHub/home">Home</a></li>
            <li><a href="/FoodHub/cart" class="active">Cart <span class="cart-count"><%= cart.getItems().size() %></span></a></li>
            <li><a href="/FoodHub/orderhistory">Orders</a></li>
            <li><a href="dashboard">
                <ion-icon name="person-circle" class="profile-icon"></ion-icon>
                <span class="nav-text"><%= (loggedUser != null) ? loggedUser.getName().split(" ")[0] : "Login" %></span>
            </a></li>
        </ul>
    </nav>
</header>

    <main class="checkout-container">
        <div class="progress-bar">
            <div class="progress-step active">
                <i class="fas fa-map-marker-alt"></i>
                <span>Address</span>
            </div>
            <div class="progress-step">
                <i class="fas fa-credit-card"></i>
                <span>Payment</span>
            </div>
            <div class="progress-step">
                <i class="fas fa-check-circle"></i>
                <span>Confirm</span>
            </div>
        </div>

        <div class="checkout-content">
        		<div class="delivery-address">
			    <h2><i class="fas fa-map-marker-alt"></i> Delivery Address</h2>
			    <form class="address-form" action="checkout" method="post">
			        <div class="form-group">
			            <input type="text" name="fullName" value="<%= (loggedUser != null) ? loggedUser.getName() : "" %>" placeholder="Full Name" required>
			        </div>
			        <div class="form-group">
			            <input type="tel" name="phoneNumber" value="<%= (loggedUser != null) ? loggedUser.getPhone() : "" %>" placeholder="Phone Number" required>
			        </div>
			        <div class="form-group">
			            <input type="text" name="streetAddress" placeholder="Delivery Address" required>
			        </div>
			
			        <h2><i class="fas fa-credit-card"></i> Payment Method</h2>
			        <div class="payment-options">
			            <label class="payment-option">
			                <input type="radio" name="payment" value="card" id="cardPayment" checked>
			                <span class="radio-custom"></span>
			                <i class="fas fa-credit-card"></i> Credit/Debit Card
			            </label>
			            <label class="payment-option">
			                <input type="radio" name="payment" value="upi" id="upiPayment">
			                <span class="radio-custom"></span>
			                <i class="fas fa-mobile-alt"></i> UPI
			            </label>
			            <label class="payment-option">
			                <input type="radio" name="payment" value="phonepe" id="phonepePayment">
			                <span class="radio-custom"></span>
			                <i class="fas fa-wallet"></i> Online Banking
			            </label>
			            <label class="payment-option">
			                <input type="radio" name="payment" value="cash" id="cashPayment">
			                <span class="radio-custom"></span>
			                <i class="fas fa-money-bill"></i> Cash on Delivery
			            </label>
			        </div>
			
			        <div class="card-details" id="cardDetails">
			            <div class="form-group">
			                <input type="text" name="cardNumber" placeholder="Card Number">
			            </div>
			            <div class="form-row">
			                <div class="form-group">
			                    <input type="text" name="expiryDate" placeholder="MM/YY">
			                </div>
			                <div class="form-group">
			                    <input type="text" name="cvv" placeholder="CVV">
			                </div>
			            </div>
			        </div>
			
			        <!-- ✅ Order Summary Inside Form -->
			        <div class="order-summary">
			            <h2><i class="fas fa-receipt"></i> Order Summary</h2>
			            <div class="order-items">
			                <% double total = 0; %>
			                <% for (Map.Entry<Integer, CartItem> entry : cart.getItems().entrySet()) { %>
			                    <div class="order-item">
			                        <img src="<%= entry.getValue().getImagePath() %>" alt="<%= entry.getValue().getName() %>" class="item-img">
			                        <div class="item-details">
			                            <h3><%= entry.getValue().getName() %></h3>
			                            <p>Quantity: <%= entry.getValue().getQuantity() %></p>
			                        </div>
			                        <span class="item-price">₹<%= entry.getValue().getPrice() * entry.getValue().getQuantity() %></span>
			                    </div>
			                    <% total += entry.getValue().getPrice() * entry.getValue().getQuantity(); %>
			                <% } %>
			            </div>
			            <div class="summary-items">
			                <div class="summary-item">
			                    <span>Subtotal</span>
			                    <span>₹ <%= session.getAttribute("totalAmount") != null ? session.getAttribute("totalAmount") : "0" %></span>
			                </div>
			                <div class="summary-item">
			                    <span>Delivery Fee</span>
			                    <span>₹ <%= session.getAttribute("deliveryFee") != null ? session.getAttribute("deliveryFee") : "0" %></span>
			                </div>	
			                <div class="summary-item total">
			                    <span>Total</span>
			                    <span>₹ <%= session.getAttribute("finalAmount") != null ? session.getAttribute("finalAmount") : "0" %></span>
			                </div>
			            </div>
			        </div>
			
			        <!-- ✅ Submit Button Inside Form -->
			        <button type="submit" class="place-order-btn">Place Order</button>
			    </form>
			</div>
        </div>
    </main>
    
    <footer class="footer">
        <div class="footer-content">
            <div class="footer-section">
                <h3>About FoodHub</h3>
                <p>Delivering delicious meals to your doorstep since 2023.</p>
            </div>
            <div class="footer-section">
                <h3>Quick Links</h3>
                <ul>
                    <li><a href="#">FAQ</a></li>
                    <li><a href="#">Terms of Service</a></li>
                    <li><a href="#">Privacy Policy</a></li>
                </ul>
            </div>
            <div class="footer-section">
                <h3>Contact Us</h3>
                <p>Email: support@foodhub.com</p>
                <p>Phone: (123) 456-7890</p>
            </div>
        </div>
        <div class="footer-bottom">
            <p>&copy; 2023 FoodHub. All rights reserved.</p>
        </div>
    </footer>

	<script>
    document.addEventListener("DOMContentLoaded", function () {
        const cardPayment = document.getElementById("cardPayment");
        const upiPayment = document.getElementById("upiPayment");
        const phonepePayment = document.getElementById("phonepePayment");
        const cashPayment = document.getElementById("cashPayment");

        function toggleCardDetails() {
            document.getElementById("cardDetails").style.display = cardPayment.checked ? "block" : "none";
        }

        cardPayment.addEventListener("change", toggleCardDetails);
        upiPayment.addEventListener("change", toggleCardDetails);
        phonepePayment.addEventListener("change", toggleCardDetails);
        cashPayment.addEventListener("change", toggleCardDetails);
    });
</script>
</body>
</html>
