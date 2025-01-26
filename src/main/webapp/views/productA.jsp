   <%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ page import="com.FM.Servlet.ReloadServlet" %>
<%@ page import="com.FM.DAO.ProductDAO" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page import="com.FM.Entities.Register" %>

    
<!DOCTYPE html>
<html>
<head>
<link type="text/css" rel="stylesheet" href="css/bootstrap.min.css">

<script type="text/javascript" src="js/bootstrap.min.js"></script>
<meta charset="ISO-8859-1">
<title>Insert title here</title>
<style>
.hover-box {
    position: relative;
    display: inline-block;
}

.image-modal {
    display: none;
    position: absolute;
    top: 100%;
    left: 50%;
    transform: translateX(-50%);
}

.hover-box:hover .image-modal {
    display: block;
}

.image-modal img {
    border-radius: 5px; 
    border: 2px solid black;
    max-width: 200px;
    max-height: 200px;
}
</style>

</head>
<body >
<%
ProductDAO productDAO = new ProductDAO();
ReloadServlet reloadServlet = new ReloadServlet();
reloadServlet.reloadProductList(productDAO, request);

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



<hr>

<div class="container-fluid">

<form action="../ProductServlet" method="post" class="row g-9" enctype="multipart/form-data">
  <div class="col-sm-1" id="pid" style="display: none;"> 
  <input type="hidden" id="id" name="id" value="${product.id}" class="form-control" required>
   </div>
  <div class="col-sm-6">
  <input type="text" name="name" id="name"  class="form-control" aria-label="Product name" placeholder="Product name" required >   
  </div>
  <div class="col-sm">
   <input type="number" name="price" id="price" step="1"  min="1"  aria-label="Price" class="form-control" placeholder="Price" required>
  </div>
    <div class="col-sm">
   <input type="number" name="qty" id="quantity" step="1"  min="1"  aria-label="Quantity" class="form-control" placeholder="Quantity" required>
  </div>
    <div class="col-md-6">
     <input type="text" name="description" id="description" aria-label="Descriptiony" class="form-control" placeholder="Description" required>
  </div>
  

<input type="hidden" name="fileName" id="fileName" aria-label="File name" class="form-control">
  
      <div class="col-sm-3 hover-box">
        <input type="file" id="productImage" name="image" class="form-control">
            <div class="image-modal">

            
<img src="" id="imagePreview" alt="Preview" />



    </div>
        
    </div>
    



      <div class="col-sm-3">
     <button type="submit" id="submitButton" style="width: 300px" class="btn btn-primary">Insert</button>
  </div>
  
  </form>
</div>

<hr>
<div class="container-xl">
<table class="table table-dark table-hover">
  <thead>
    <tr>
      <th scope="col">Id</th>
      <th scope="col">Name</th>
      <th scope="col">Description</th>
      <th scope="col">Price</th>
       <th scope="col">Quantity</th>
		<th scope="col">Update</th>
		 <th scope="col">Delete</th>
    </tr>
  </thead>
  <tbody>
  
  <c:forEach var="product" items="${ProductList}">
            <tr>
                <td>${product.id}</td>
                <td>${product.name}</td>
                <td>${product.description}</td>
                <td>${product.price}</td>
                <td>${product.qty}</td>
                <td hidden>${product.fileName}</td>


                <td>
                <button class="btn btn-outline-light" onclick="populateForm(${product.id}, 
                '${product.name}', '${product.description}',
                '${product.price}', '${product.qty}', '${product.fileName}',
                 '../ProductServlet?id=${product.id}');">
                Update</button>
               </td>
                
                <td><a href="../ProductServlet?action=delete&productId=${product.id}"><button class="btn btn-outline-light">Delete</button></a></td>
                
    </tr>
     </c:forEach>
     <tbody>
     </table>
    
</div>

<script>
    function populateForm(id, name, description, price, qty, fileName, filePath) {
        console.log(name, id, description, price, qty, fileName, filePath);
        
        document.getElementById("id").value = id;
        document.getElementById('pid').style.display = 'block';
        var pid = document.getElementById("id");
        if (pid) {
            pid.readOnly = true;
            pid.type = "text";
        }
        document.getElementById("submitButton").innerText = "Update";
        document.getElementById("name").value = name;
        document.getElementById("description").value = description;
        document.getElementById("price").value = price;
        document.getElementById("quantity").value = qty;
        document.getElementById("quantity").readOnly = true;
        document.getElementById("fileName").value = fileName;
        document.getElementById("imagePreview").src = filePath;


    }
</script>
</body>
</html>