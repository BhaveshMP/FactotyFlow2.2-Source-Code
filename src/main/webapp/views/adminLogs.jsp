<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="com.FM.Entities.AdminLog" %>
<%@ page import="com.FM.Servlet.ReloadServlet" %>
<%@ page import="com.FM.DAO.AdminLogDAO" %>
<%@ page import="com.FM.Entities.Register" %>
<!DOCTYPE html>
<html>
<head>
<link type="text/css" rel="stylesheet" href="css/bootstrap.min.css">
<script type="text/javascript" src="js/bootstrap.min.js"></script>
    <title>Register</title>
</head>
<body class="style="background-color: #ff1500";>
<%
AdminLogDAO adminLogDAO = new AdminLogDAO();
ReloadServlet reloadServlet = new ReloadServlet();
reloadServlet.reloadAdminLogList(adminLogDAO, request);

%>
<br>
<nav class="navbar navbar-expand-lg navbar-dark bg-dark">
  <div class="container-fluid">
    <a class="navbar-brand" href="#">FactoryFlow</a>
    <button class="navbar-toggler" type="button" data-bs-toggle="collapse" data-bs-target="#navbarSupportedContent" aria-controls="navbarSupportedContent" aria-expanded="false" aria-label="Toggle navigation">
      <span class="navbar-toggler-icon"></span>
    </button>
    <div class="collapse navbar-collapse" id="navbarSupportedContent">
      <ul class="navbar-nav me-auto mb-2 mb-lg-0">
        <li class="nav-item">
          <a class="nav-link active" aria-current="page" href="../index.jsp">Home</a>
        </li>
        <li class="nav-item">
          <a class="nav-link" href="productList.jsp">Products</a>
        </li>
         <li class="nav-item">
          <a class="nav-link" href="register.jsp">Register</a>
        </li>

        <% if (session.getAttribute("user") == null) { %>
        <li class="nav-item">
          <a class="nav-link" href="login.jsp">Login</a>
        </li>
        <% } else { %>
        <li class="nav-item">
          <a class="nav-link" href="../login?requestType=logout">Logout</a>
        </li>
        <% } %>
        
                        <%
        if(session.getAttribute("user")!=null &&
        	"admin".equals(((Register) session.getAttribute("user")).getAccountType())) {
        	
        %>
        <li class="nav-item">
          <a class="nav-link" href="productA.jsp">Update Produts</a>
        </li>
        <%
        }       
        %>
        <li class="nav-item dropdown">
          <a class="nav-link dropdown-toggle" href="#" id="navbarDropdown" role="button" data-bs-toggle="dropdown" aria-expanded="false">
            Dropdown
          </a>
          <ul class="dropdown-menu" aria-labelledby="navbarDropdown">
            <li><a class="dropdown-item" href="adminLogs.jsp">Logs</a></li>
            <li><hr class="dropdown-divider"></li>
            <li><a class="dropdown-item" href="inventories.jsp">Inventory</a></li>
            
          </ul>
        </li>
        <li class="nav-item">
          <a class="nav-link" href="orders.jsp" tabindex="-1">Orders</a>
        </li>
      </ul>
<form class="d-flex">
    <input class="form-control me-2" type="search" placeholder="Search" aria-label="Search">
    <button class="btn btn-outline-light me-2" type="submit">Search</button> <!-- Added 'me-2' for spacing -->
    <a href="register.jsp?type=update"
       class="btn btn-outline-light" style="width: 40px;">
        <b>
            <%= (session.getAttribute("user") != null) ? Character.toUpperCase(((Register) session.getAttribute("user")).getName().charAt(0)) : "" %>
        </b>
    </a>
</form>

    </div>
  </div>
</nav>

<br>



<div class="container-xl">
<table class="table table-dark table-hover">
  <thead>
    <tr>
      <th scope="col">Log id</th>
      <th scope="col">Date</th>
      <th scope="col">Product</th>
      <th scope="col">Order</th>
       <th scope="col">Message</th>
		<th scope="col">Type</th>
		 <th scope="col">Allow</th>
    </tr>
  </thead>
  <tbody>
 <%-- <% if(request.getAttribute("AdminLogList")!=null){ %> --%>
  <c:forEach var="log" items="${AdminLogList}">
            <tr>
                <td>${log.logId}</td>
                <td>${log.dateCreated}</td>
                <td>${log.product.name} (${log.product.id})</td>
                <td>${log.order.orderId}</td>
                <td>${log.message}</td>
                <td>${log.type} (${log.status})</td>
                <td><a href="inventories.jsp"><button class="btn btn-outline-light">Allow</button></a>
           <%--      <button class="btn btn-outline-light" onclick="populateForm(${log.id}, 
                '${product.name}', '${product.description}',
                '${product.price}', '${product.qty}');">
                Update</button> --%>
               </td>
                
<%--                 <td><a href="../ProductServlet?action=delete&productId=${product.id}"><button class="btn btn-outline-light">Delete</button></a></td>
 --%>      
           
    </tr>
     </c:forEach>
<%--      <%} %> --%>


     <tbody>
     </table>
     
     
     
     
     
     
</body>
</html>