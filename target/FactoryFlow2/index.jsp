<%@ page import="com.FM.Entities.Register" %>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    <%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
<link type="text/css" rel="stylesheet" href="views/css/bootstrap.min.css">
<script type="text/javascript" src="views/js/bootstrap.min.js"></script>

<title>Home</title>
</head>

<body>

<br>
<%
    Register user = (Register) session.getAttribute("user"); 
%>
				
<nav class="navbar navbar-expand-lg navbar-dark bg-dark">
  <div class="container-fluid">
    <a class="navbar-brand" href="index.jsp">FactoryFlow</a>
    <button class="navbar-toggler" type="button" data-bs-toggle="collapse" data-bs-target="#navbarSupportedContent" aria-controls="navbarSupportedContent" aria-expanded="false" aria-label="Toggle navigation">
      <span class="navbar-toggler-icon"></span>
    </button>
    <div class="collapse navbar-collapse" id="navbarSupportedContent">
      <ul class="navbar-nav me-auto mb-2 mb-lg-0">
        <li class="nav-item">
          <a class="nav-link active" aria-current="page" href="index.jsp">Home</a>
        </li>
        <li class="nav-item">
          <a class="nav-link" href="views/productList.jsp">Products</a>
        </li>
        <li class="nav-item">
          <a class="nav-link" href="views/register.jsp">Register</a>
        </li>
        
        
        <%		if(session.getAttribute("user")==null){		%>
        
        <li class="nav-item">
          <a class="nav-link" href="views/login.jsp">Login</a>
        </li>
        <%		}else{       %>
        
        <li class="nav-item">
          <a class="nav-link" href="login?requestType=logout">Logout</a>
        </li>
        <% 		}      %>
        
        
        
        
        
        <%
        if(session.getAttribute("user")!=null &&
        	"admin".equals(((Register) session.getAttribute("user")).getAccountType())) {
        	
        %>
        <li class="nav-item">
          <a class="nav-link" href="views/productA.jsp">Update Produts</a>
        </li>
        <%
        }       
        %>
        
      </ul>

    </div>

		<a href="views/register.jsp?type=update"
			class="btn btn-outline-light" style="width: 40px;" >
			<b>
<%= (session.getAttribute("user") != null) ? Character.toUpperCase(((Register) session.getAttribute("user")).getName().charAt(0)) : "" %>
			 </b>

 
        </a>
       
  </div>
</nav>

<div>

<img src="assets/img/track_your_order.jpg" width=100% height=85%>

</div>
<hr>




</body>
