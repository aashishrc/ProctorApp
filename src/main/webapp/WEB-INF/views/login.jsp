<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>User Login</title>
    <style>
        body {
            font-family: Arial, sans-serif;
            background-color: #f4f4f4;
            margin: 0;
            padding: 0;
            display: flex;
            justify-content: center;
            align-items: center;
            height: 100vh;
        }

        .registration-form {
            background-color: #fff;
            border-radius: 5px;
            box-shadow: 0 2px 5px rgba(0, 0, 0, 0.1);
            padding: 20px;
            width: 400px;
        }

        .registration-form h2 {
            margin-bottom: 20px;
            text-align: center;
        }

        .registration-form label {
            font-weight: bold;
        }

        .registration-form input[type="text"],
        .registration-form input[type="password"] {
            width: calc(100% - 20px);
            padding: 10px;
            margin: 5px 0 15px 0;
            border: 1px solid #ccc;
            border-radius: 4px;
        }

        .registration-form input[type="radio"] {
            margin-right: 5px;
        }

        .registration-form button {
            background-color: #007bff;
            color: #fff;
            padding: 10px 20px;
            border: none;
            border-radius: 4px;
            cursor: pointer;
            width: 100%;
            display: block;
            margin-top: 20px;
        }

        .registration-form button:hover {
            background-color: #0056b3;
        }
    </style>
</head>
<body>
<c:set var="error" value="${sessionScope.error}" />
<div class="registration-form">
    <p>${error}</p>
    <h2>User Login</h2>
    <form action="/login" method="post">
        <div>
            <label for="username">Username/Email:</label>
            <input type="text" id="username" name="username" required>
        </div>
        <div>
            <label for="password">Password:</label>
            <input type="password" id="password" name="password" required>
        </div>

        <button type="submit">Login</button>

        <div>Don't have an account? <a href="/register">Register</a> </div>
    </form>
</div>
</body>
</html>
