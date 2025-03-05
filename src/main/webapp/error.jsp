<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<!DOCTYPE html>
<html lang="en">
	<head>
		<meta charset="UTF-8" />
		<meta name="viewport" content="width=device-width, initial-scale=1.0" />
		<link
			rel="shortcut icon"
			href="images/logo/fevicon.svg"
			type="image/x-icon"
		/>
		<title>Oops! Something went wrong</title>
		<style>
			* {
				margin: 0;
				padding: 0;
				box-sizing: border-box;
				font-family: "Arial", sans-serif;
			}

			body {
				background-color: #f9f9f9;
				color: #333;
				display: flex;
				justify-content: center;
				align-items: center;
				height: 100vh;
				text-align: center;
			}

			.error-container {
				max-width: 500px;
				padding: 20px;
			}

			.error-container img {
				width: 100%;
				max-width: 300px;
				margin: 20px auto;
			}

			h1 {
				font-size: 2.5rem;
				color: #ff6347;
				margin-bottom: 20px;
			}

			p {
				font-size: 1.2rem;
				margin-bottom: 20px;
				color: #666;
			}

			.btn {
				display: inline-block;
				padding: 10px 20px;
				font-size: 1rem;
				color: #fff;
				background-color: #ff6347;
				text-decoration: none;
				border-radius: 5px;
				transition: background-color 0.3s;
			}

			.btn:hover {
				background-color: #e5533c;
			}
		</style>
	</head>
	<body>
		<div class="error-container">
			<img src="images/logo/logo.svg" alt="Error" />
			<h1>Oops! <%= request.getAttribute("message") %></h1>
			<p>It seems you've taken a wrong turn.</p>
			<p>Let’s get you back on track!</p>
			<a href="login.html" class="btn">Go to LogIn Page</a>
		</div>
	</body>
</html>