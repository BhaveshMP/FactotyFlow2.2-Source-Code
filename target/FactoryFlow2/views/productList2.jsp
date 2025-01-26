<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
  <%@ page import="com.FM.Entities.Product" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="java.util.*, java.text.SimpleDateFormat" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="com.FM.Entities.Register" %>
  <%--   
    
    
    
    
    
    NOT USING 
    
    NOT USING 
    
    NOT USING
    
    
    
    
    
    
     --%>
<html>
<head>
<title>Product List</title>
<link type="text/javascript" src="js/myScript.js">
<link type="text/css" rel="stylesheet" href="css/bootstrap.min.css">
<script type="text/javascript" src="js/bootstrap.min.js"></script>

</head>
<body>
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
      </ul>
      <form class="d-flex">
        <input class="form-control me-2" type="search" placeholder="Search" aria-label="Search">
        <button class="btn btn-outline-light" type="submit">Search</button>
      </form>
    </div>
    		<a href="views/register.jsp?type=update"
			class="btn btn-outline-light" style="width: 40px;" >
			<b>
<%= (session.getAttribute("user") != null) ? Character.toUpperCase(((Register) session.getAttribute("user")).getName().charAt(0)) : "" %>
			 </b>

 
        </a>
  </div>
</nav>






    <h2>Product Catalog</h2>

    
     <section class="my-lg-14 my-8">
    <div class="container">
               <!-- row -->
               <div class="row">
                  <div class="col-12">
                     <div class="mb-8">
                        <!-- heading -->
                        <h3 class="mb-0"></h3>
                        <br>
                     </div>
                  </div>
               </div>
               <!-- row -->
               <div class="row row-cols-2 row-cols-md-3 row-cols-lg-3 row-cols-xxl-6 g-5">
 
                 <!-- col -->
                <c:forEach var="product" items="${ProductList}">
                


                  <div class="col" data-bs-toggle="modal" data-bs-target="#jake${product.id}">
                        <!-- card -->
                        <div class="card card-product">
                           <div class="card-body text-center py-8">
                              <!-- img -->
                              <img src="${request.getContextPath()}/FactoryFlow2/uploads/${product.fileName}" width="150" height="150" alt="${product.name}" class="mb-3">
                              <!-- text -->
                              <div class="text-truncate">${product.name}</div>
                           </div>
                        </div>
                  </div>
                
                
                  
                  <!-- Modal -->
<%-- 				<div class="modal fade" id=${product.name} tabindex="-1" aria-labelledby="exampleModalLabel" aria-hidden="true">
				  <div class="modal-dialog modal-dialog-centered">
				    <div class="modal-content">
				      <div class="modal-header">
				        <h1 class="modal-title fs-5" id="exampleModalLabel">Modal title</h1>
				        <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
				      </div>
				      <div class="modal-body">
				        <b>${product.name}</b>
				      </div>
				      <div class="modal-footer">
				        <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Close</button>
				        <button type="button" class="btn btn-primary">Order</button>
				      </div>
				    </div>
				  </div>
				</div> --%>
                </c:forEach>
               </div>
            </div>
            
            <c:forEach var="product" items="${ProductList}">
           <div class="container" style="padding: 20px;">
    <div class="modal fade" id="jake${product.id}" tabindex="-1" aria-labelledby="exampleModalLabel" aria-hidden="true">
        <div class="modal-dialog modal-dialog-centered modal-lg">
            <div class="modal-content">
                <div class="row">

                    <!-- Gallery -->
                    <div class="col-md-6 pb-4 pb-md-0 mb-3 mb-sm-3 mb-md-0">
                        <div class="container-fluid d-flex align-items-center" style="min-height: 200px;">
                            <img src="${request.getContextPath()}/FactoryFlow2/uploads/${product.fileName}" style="margin:auto;" height="300" width="200" alt="Image">
                        </div>
                    </div>

                    <!-- Product details and options -->
                    <div class="col-md-6">
                        <div class="ps-md-4 ps-xl-5">

                            <!-- Title -->
                            <h1 class="h3">${product.name}</h1>

                            <!-- Description -->
                            <p class="fs-sm mb-0">${product.description}</p>

                            <!-- Price -->
                            <div class="h4 d-flex align-items-center my-4">
                                ${product.price}
                            </div>

                           
                            <form method="post" action="../order" class="d-flex gap-2 pb-3 pb-lg-6 mb-3">
                                <input type="number" name="qtyOrdered" class="form-control form-control w-30" min="1" value="1">
                                <input type="hidden" name="productId" value="${product.id}">
								<%
								int userId=0;
								if(session.getAttribute("user")!=null && userId!=0){
								  Register user= (Register)session.getAttribute("user");
								userId = user.getId();
								}
								%>

                                <input type="hidden" name="registerId" value="<%=userId %>">
                                <input type="submit"  class="btn btn-lg btn-dark w-30" name="userId" value="Order">
                                </form>
                           

                            <!-- Info list -->
                            <ul class="list-unstyled gap-3 pb-3 pb-lg-4 mb-3">
                                <li class="d-flex flex-wrap fs-sm">
                                    <span class="d-flex align-items-center fw-medium text-dark-emphasis me-2">
                                        <i class="ci-clock fs-base me-2"></i>
                                        <%
                                            Calendar cal = Calendar.getInstance();
                                            int days = 10 + new Random().nextInt(11);
                                            cal.add(Calendar.DAY_OF_MONTH, days);
                                            SimpleDateFormat sdf = new SimpleDateFormat("dd-MMM");
                                        %>
                                        <p>Estimated delivery: <%= sdf.format(cal.getTime()) %></p>
                                    </span>
                                </li>
                            </ul>

                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>
           
            
       </c:forEach>
      
      
            </section>
 <%if(request.getParameter("message")!=null){
 	String s = request.getParameter("message");
 	out.println("<script> alert('"+s+"');</script>");
 }
 request.getParameter("message");
 
	 %>


</body>
</html>
