<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<html>
<head>
    <title>Login</title>
</head>
<body>
    <h1>Error Page</h1>
    <% 
        String error = request.getParameter("error");
        if (error != null) {
    %>
    	<p style="color: red;"><%=error %>></p>
        <p style="color: red;">Invalid email or password. Please try again.</p>
        
    <% 
        }
    %>
</body>
</html>
