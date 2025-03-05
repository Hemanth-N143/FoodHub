<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="com.tap.model.User, java.util.Random" %>

<%
    // Retrieve stored order details from session
    Integer orderId = (Integer) session.getAttribute("orderId");
    String deliveryAddress = (String) session.getAttribute("deliveryAddress");
    Double finalAmount = (Double) session.getAttribute("finalAmount");

    // Handle cases where values might be missing
    if (orderId == null) {
        orderId = 0; // Show a placeholder if missing
    }
    if (deliveryAddress == null) {
        deliveryAddress = "Not Available";
    }
    if (finalAmount == null) {
        finalAmount = 0.0;
    }

    // Retrieve logged-in user
    User loggedUser = (User) session.getAttribute("user");
%>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Order Confirmed - FoodHub</title>
    <link rel="stylesheet" href="home.css">
    <link rel="stylesheet" href="orderConfirmation.css">
    <link href="https://fonts.googleapis.com/css2?family=Poppins:wght@400;500;600;700&display=swap" rel="stylesheet">
    <script src="https://cdnjs.cloudflare.com/ajax/libs/gsap/3.9.1/gsap.min.js"></script>
    <script type="module" src="https://unpkg.com/ionicons@5.5.2/dist/ionicons/ionicons.esm.js"></script>
	<script nomodule src="https://unpkg.com/ionicons@5.5.2/dist/ionicons/ionicons.js"></script>
</head>
<body>
    <div class="confetti-container"></div>

    <header class="header">
        <nav class="nav">
            <div class="logo">FoodHub</div>
            <ul class="nav-links">
                <li><a href="/FoodHub/home">Home</a></li>
                <li><a href="/FoodHub/cart">Cart</a></li>
                <li><a href="/FoodHub/orderhistory">Orders</a></li>
                <li>
                    <a href="dashboard">
                        <ion-icon name="person-circle" class="profile-icon"></ion-icon>
                        <span class="nav-text">
                            <%= (loggedUser != null) ? loggedUser.getName().split(" ")[0] : "Login" %>
                        </span>
                    </a>
                </li>
            </ul>
        </nav>
    </header>

    <main>
        <div class="confirmation-container" id="confirmation">
            <div class="order-card">
                <div class="success-animation">
                    <svg class="checkmark" xmlns="http://www.w3.org/2000/svg" viewBox="0 0 52 52">
                        <circle class="checkmark__circle" cx="26" cy="26" r="25" fill="none"/>
                        <path class="checkmark__check" fill="none" d="M14.1 27.2l7.1 7.2 16.7-16.8"/>
                    </svg>
                </div>
                <h1>Order Confirmed!</h1>
                <p class="order-number">Order #<%= orderId %></p>
                <p class="thank-you">Thank you for your order. Your food is being prepared!</p>

                <div class="order-details">
                    <h2>Order Details</h2>
                    <div class="detail-row">
                        <span>Estimated Delivery Time:</span>
                        <span id="delivery-time">30 min</span>
                    </div>
                    <div class="detail-row">
                        <span>Delivery Address:</span>
                        <span><%= deliveryAddress %></span>
                    </div>
                    <div class="detail-row">
                        <span>Total Amount:</span>
                        <span id="total-amount">₹<%= String.format("%.2f", finalAmount) %></span>
                    </div>
                </div>

                <div class="progress-bar-container">
                    <div class="progress-bar">
                        <div class="progress"></div>
                    </div>
                    <p>Order Progress</p>
                </div>

                <div class="action-buttons">
                    <a href="#" class="track-order-btn">Track Order</a>
                    <a href="/FoodHub/home" class="continue-shopping-btn">Continue Shopping</a>
                </div>
            </div>
        </div>
    </main>

    <script>
    document.addEventListener("DOMContentLoaded", () => {
        // Animate progress bar
        setTimeout(() => {
            document.querySelector(".progress").style.width = "100%";
        }, 500);

        

        // Create confetti effect
        const confettiColors = ["#FF0000", "#FFFFFF", "#000000"];
        const confettiCount = 200;

        for (let i = 0; i < confettiCount; i++) {
            createConfetti();
        }

        function createConfetti() {
            const confetti = document.createElement("div");
            confetti.className = "confetti";
            confetti.style.left = Math.random() * 100 + "vw";
            confetti.style.animationDuration = Math.random() * 3 + 2 + "s";
            confetti.style.backgroundColor = confettiColors[Math.floor(Math.random() * confettiColors.length)];
            document.querySelector(".confetti-container").appendChild(confetti);

            confetti.addEventListener("animationend", () => {
                confetti.remove();
            });
        }
    });
    </script>

</body>
</html>
