<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<html>
<head>
    <title>Login</title>
</head>
<body>
    <% 
        String text = request.getParameter("text");
        if (text != null) {
    %>
        <p style="color: green;"><%= text%>></p>
    <% 
        }
    %>
</body>
</html>
