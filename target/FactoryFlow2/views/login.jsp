<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="com.FM.Entities.Register" %>
<html>
<head>
<br>
    <title>Login</title>
    <link type="text/css" rel="stylesheet" href="css/bootstrap.min.css">
<link type="text/css" rel="stylesheet" href="css/styles.css">
<script type="text/javascript" src="js/bootstrap.min.js"></script>


</head>
<body>
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

        <%		if(session.getAttribute("user")==null){		%>
        
        <li class="nav-item">
          <a class="nav-link" href="login.jsp">Login</a>
        </li>
        <%		}else{       %>
        
        <li class="nav-item">
          <a class="nav-link" href="../login?requestType=logout">Logout</a>
        </li>
        <% 		}      %>
        
       
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

<h2>Login</h2>
<div class="col d-flex align-items-center justify-content-center bg-dark py-5 px-4 px-md-5">
    <div class="card shadow-sm p-3 mb-5 bg-white rounded">
        <form action="../login" method="post">
            <div class="form-group">
                <label for="email">Email:</label>
                <input type="text" id="email" name="email" class="form-control" required>
            </div>   

            <div class="form-group">
                <label for="password">Password:</label>
                <input type="password" id="password" name="password" class="form-control" required>
            </div>
            <button type="submit" class="btn btn-primary mt-4">Login</button>
            <a href="register.jsp" class="btn btn-primary mt-4">Register</a>
            
    <% 
        String error = request.getParameter("error");
        if (error != null && error.equals("true")) {
        	String message = request.getParameter("message")!=null?request.getParameter("message"):"Error";
    %>
        <p style="color: red;"><%= message %></p>
    <% 
        }
    %>
        </form>
    </div>
</div>   



</body>
</html>
