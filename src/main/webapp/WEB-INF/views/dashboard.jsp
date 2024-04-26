<%@ page import="com.proctorapp.model.Users" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Dashboard</title>
    <style>
        body {
            font-family: Arial, sans-serif;
            margin: 0;
            padding: 0;
        }
        .container {
            display: flex;
            flex-direction: column;
            height: 100vh;
        }
        .top-nav {
            background-color: #333;
            color: #fff;
            padding: 10px;
            display: flex;
            justify-content: space-between;
            align-items: center;
        }
        .left-nav {
            background-color: #f4f4f4;
            padding: 10px;
            width: 200px;
            display: flex;
            flex-direction: column;
        }
        .content {
            flex-grow: 1;
            padding: 20px;
        }
        .logout-btn {
            background-color: #d9534f;
            color: #fff;
            border: none;
            padding: 8px 12px;
            border-radius: 4px;
            cursor: pointer;
        }
        .logo{
            color: white;
        }
        a{
            text-decoration: none;
            color: white;
        }
    </style>
</head>
<body>

<!-- Fetching logged-in user's information -->
<c:set var="user" value="${sessionScope.user}" />
<c:set var="id" value="${sessionScope.user.id}"/>
<%
    Users user = (Users) session.getAttribute("user");
    Long id = (Long) session.getAttribute("id");
    String userType = user.getUserType();
    String prefix;
    if(userType.equals("Professor")){
        prefix = "Prof.";
    }else{
        prefix = "";
    }

%>
<div class="container">
    <div class="top-nav">
        <div class="logo">
<%--            <form action="" method="post">--%>
<%--                <button type="submit">ProctorApp</button>--%>
<%--            </form>--%>
            <a href="">ProctorApp</a>
        </div>
        <div>

            <!-- Display logged-in user's name -->
            Logged in as: <%=prefix%>${user.firstname}
        </div>
        <div>
            <form action="/logout" method="get">
                <button class="logout-btn" type="submit">Logout</button>
            </form>
        </div>
    </div>
    <div class="left-nav">
        <form action="/profile" method="post">
            <button type="submit">Profile</button>
        </form>
        <%-- Assuming you have a variable named 'userType' set in your JSP --%>
        <% if (userType.equals("Professor")) {
            System.out.println(user.getId());
        %>

        <form action="${pageContext.request.contextPath}/students?professorId=${user.id}" method="post">
            <button type="submit">Students</button>
        </form>
        <% } else { %>
        <% } %>

        <form action="/updateProfile" method="post">
            <button type="submit">Update Profile</button>
        </form>
    </div>
    <div class="content">
        <!-- Content area -->
        <!-- This is where the content of the selected navigation item will be displayed -->
    </div>
</div>
</body>
</html>
