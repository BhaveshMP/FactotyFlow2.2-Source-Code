<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page import="com.FM.Servlet.ReloadServlet" %>
<%@ page import="com.FM.DAO.InventoryDAO" %>
<%@ page import="com.FM.Entities.Register" %>

<!DOCTYPE html>
<html>
<head>
<link type="text/css" rel="stylesheet" href="css/bootstrap.min.css">
<script type="text/javascript" src="js/bootstrap.min.js"></script>
    <title>Register</title>
</head>
<body class="style="background-color: #ff1500";>

 <%if(request.getParameter("message")!=null){
 	String s = request.getParameter("message");
 	out.println("<script> alert('"+s+"');</script>");
 }
 request.getParameter("message");
 
	 %>
	 
<%
InventoryDAO invDao = new InventoryDAO();
ReloadServlet reloadServlet = new ReloadServlet();
reloadServlet.reloadInventoryList(invDao, request);

%>
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
<div action="../ProductServlet" method="post" class="container-fluid">



</div>

<hr>
<div class="container-xl">
<table class="table table-dark table-hover">
  <thead>
    <tr>
      <th scope="col">Inventory Id</th>
      <th scope="col">Product</th>
      <th scope="col">Qty Available</th>
      <th scope="col">Reorder Level</th>
      <th scope="col">type</th>
       <th scope="col">Updated Date</th>
		<th scope="col">Update Quantity</th>
		 <th scope="col">Update</th>
    </tr>
  </thead>
  <tbody>
  
  <c:forEach var="inventory" items="${InventoryList}">
      
    <form method="get" action="../inventory">
            <tr>
                <td>${inventory.inventoryId}</td>
                <td>${inventory.product.name} (${inventory.product.id})</td>
                <td>${inventory.quantityAvailable}</td>
                <td>${inventory.reorderLevel}</td>
                <td>${inventory.type}</td>
                <td>${inventory.updatedAt}</td>
                

<td>
<div class="input-group mb-3" style="max-width: 200px;">
    <button class="btn btn-outline-secondary" type="button" id="decrementButton">-</button>
    <input type="text" name=quantityInput class="form-control text-center" id="quantityInput" value="10" min="1" max="10">
    <button class="btn btn-outline-secondary" type="button" id="incrementButton">+</button>
</div>


</td>
   <td>
   <input type="hidden" name="type" value="inventoryUpdate">
   <input type="hidden" name="location" value="inventories">
   <input type="hidden" name="productId" value="${inventory.product.getId()}">
   
<%-- <a href="../inventory?type=inventoryUpdate&location=inventories&productId=${inventory.product.getId()}&q_change=document.getElementById('quantityInput').value">
    --%>
   
   <input type="submit" class="btn btn-outline-light" value="Update">
<!--    <button class="btn btn-outline-light">Update</button> -->
   </a> 

   </td>
                
    </tr>
    </form>
     </c:forEach>
     <tbody>
     </table>
    
</div>



<!-- JavaScript for increment and decrement functionality -->
<script>
    const decrementButton = document.getElementById('decrementButton');
    const incrementButton = document.getElementById('incrementButton');
    const quantityInput = document.getElementById('quantityInput');
    const updateButton = document.getElementById('updateButton');
    
    let quantityValue = parseInt(quantityInput.value);

    decrementButton.addEventListener('click', () => {
        if (quantityValue > 1) {
            quantityValue--;
            quantityInput.value = quantityValue;
        }
    });

    incrementButton.addEventListener('click', () => {
        quantityValue++;
        quantityInput.value = quantityValue;
    });

    updateButton.addEventListener('click', () => {
        const productId = '${inventory.product.getId()}'; // Fetch product ID from server-side code
        const quantity = document.getElementById('quantityInput').value; 
        console.log("Product ID:", productId);
        const href = '../inventory?type=inventoryUpdate&location=inventories&productId=${productId}&q_change=${quantity}';
        
        // Redirect to the constructed URL
        window.location.href = href;
    });
</script>

</body>
</html>