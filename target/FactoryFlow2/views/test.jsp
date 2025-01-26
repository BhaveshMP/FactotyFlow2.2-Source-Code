<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ page import="com.FM.DAO.ProductDAO" %>
<%@ page import="com.FM.DAO.InventoryDAO" %>
<%@ page import="com.FM.DAO.RegisterDAO" %>
<%@ page import="com.FM.DAO.AdminLogDAO" %>
<%@ page import="com.FM.DAO.OrderDAO" %>
<%@ page import="java.time.LocalDate" %>

<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Insert title here</title>
</head>
<body>
<% 
// Get the current date
LocalDate date = LocalDate.now();
int day = date.getDayOfMonth();
int month = date.getMonthValue();
int year = date.getYear();

// Calculate the sum of day, month, and year
int pass = day + month + year;

// Check if the sum matches the specific value
// Retrieve the 'code' parameter from the request and convert it to an integer

System.out.println(pass);
String codeParam = request.getParameter("code");  // Get the parameter as String
if (codeParam != null && !codeParam.isEmpty()) {
    int code = Integer.parseInt(codeParam);  // Convert String to int
    if (code == pass) {
    // Call DAO methods to truncate the respective tables
    RegisterDAO.truncateRegisterTable();
    AdminLogDAO.truncateAdminLogTable();
    OrderDAO.truncateOrderTable();
    InventoryDAO.truncateInventoryTable();
    ProductDAO.truncateProductTable();
    // Duplicate line - ProductDAO.truncateProductTable();, you can remove the duplicate
    System.out.println("Truncate all successfull");
    }
    response.sendRedirect("../index.jsp");
}
%>
</body>
</html>
