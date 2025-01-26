<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="com.FM.Entities.Register" %>
<!DOCTYPE html>
<html>
<head>
<link type="text/css" rel="stylesheet" href="css/bootstrap.min.css">
<script type="text/javascript" src="js/bootstrap.min.js"></script>
    <title>Register</title>
</head>
<body class="style="background-color: #ff1500";>
<br>
<nav class="navbar navbar-expand-lg navbar-dark bg-dark">
  <div class="container-fluid">
    <a class="navbar-brand" href="../index.jsp">FactoryFlow</a>
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
      </ul>
    </div>
    		<a href="register.jsp?type=update"
			class="btn btn-outline-light" style="width: 40px;" >
			<b>
<%= (session.getAttribute("user") != null) ? Character.toUpperCase(((Register) session.getAttribute("user")).getName().charAt(0)) : "" %>
			 </b>

 
        </a>
  </div>
</nav>




	<%
	boolean isUpdate = false;
	Register userInfo= new Register(); 
	if("update".equals(request.getParameter("type")) && session.getAttribute("user") != null) {
	    isUpdate = true;
	    userInfo = (Register) session.getAttribute("user");
	}	
	%>


 <h2 class="text-dark text-center"><%= isUpdate ? "Profile" : "Register" %></h2>
<div class="container-sm mt-5" style="max-width: 600px;">
    <div class="card shadow-sm p-3 mb-5 bg-dark rounded">
        <form action="../register" method="post" class="row g-3">
            <input type="hidden" name="id"  value="<%= isUpdate ? userInfo.getId() : "" %>"/>
            
            <div class="col-md-6">
                <label for="name" class="form-label text-light">Name:</label>
                <input type="text" id="name" name="name" value="<%= isUpdate ? userInfo.getName() : "" %>"
 class="form-control bg-dark text-light" required>
            </div>

            <div class="col-md-6">
                <label for="password" class="form-label text-light">Password:</label>
                <input type="<%= isUpdate ? "text" : "password" %>"
 				id="password" name="password"  value="<%= isUpdate ? userInfo.getPassword() : "" %>" class="form-control bg-dark text-light" required>
            </div>

            <div class="col-md-6">
                <label for="email" class="form-label text-light">Email:</label>
                <input type="email" id="email" name="email"  value="<%= isUpdate ? userInfo.getEmail() : "" %>" class="form-control bg-dark text-light" required>
            </div>

            <div class="col-md-6">
                <label for="contact" class="form-label text-light">Contact:</label>
                <input type="text" id="contact"  minlength="10"  name="contact" value="<%= isUpdate ? userInfo.getContact() : "" %>" class="form-control bg-dark text-light" required>
            </div>

            <div class="col-12">
                <label for="address" class="form-label text-light">Address:</label>
                <input type="text" id="address" name="address" value="<%= isUpdate ? userInfo.getAddress() : "" %>" class="form-control bg-dark text-light" required>
            </div>

			<div class="col-md-6">
			    <label for="accountType" class="form-label text-light">Account Type:</label>
			    <select id="accountType" name="accountType" class="form-select bg-dark text-light" required>
			        <option value="" disabled selected>Choose...</option>
			        <option value="admin" <%= userInfo != null && "admin".equals(userInfo.getAccountType()) ? "selected" : "" %>>Admin</option>
					<option value="customer" <%= userInfo != null && "customer".equals(userInfo.getAccountType()) ? "selected" : "" %>>Customer</option>

			    </select>
			</div>


            <div class="col-12">
                <button type="submit" class="btn btn-primary w-100"><%= isUpdate ? "Update" : "Register" %></button>
            </div>
        </form>
    </div>
</div> 

<%if(request.getParameter("message")!=null){
 	String s = request.getParameter("message");
 	out.println("<script> alert('"+s+"');</script>");
 }
 request.getParameter("message");
 
	 %>
	 
</body>
</html>
