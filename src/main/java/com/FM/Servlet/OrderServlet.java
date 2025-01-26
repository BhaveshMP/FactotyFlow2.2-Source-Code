package com.FM.Servlet;

import jakarta.servlet.*;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;

import com.FM.DAO.InventoryDAO2;
import com.FM.DAO.AdminLogDAO;
import com.FM.DAO.InventoryDAO;
import com.FM.DAO.OrderDAO;
import com.FM.DAO.ProductDAO;
import com.FM.Entities.AdminLog;
import com.FM.Entities.Inventory;
import com.FM.Entities.Order;
import com.FM.Entities.Product;
import com.FM.Entities.Register;

@WebServlet("/order")
public class OrderServlet extends HttpServlet {

    private OrderDAO orderDAO;
    private ProductDAO productDAO;
    private AdminLogDAO adminLogDao;
    ReloadServlet reloadServlet;

    @Override
    public void init() throws ServletException {
        super.init();
        orderDAO = new OrderDAO();  // Initialize DAO here
        productDAO = new ProductDAO();
        adminLogDao = new AdminLogDAO();
        reloadServlet = new ReloadServlet();
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
        	HttpSession session = req.getSession();
if(session.getAttribute("user")==null) {
	resp.sendRedirect("views/login.jsp?error=true&message=login first");
	}else {
		

            // Retrieve parameters from request
            int registerId = Integer.parseInt(req.getParameter("registerId"));
            int qtyOrdered = Integer.parseInt(req.getParameter("qtyOrdered"));

            // Validate input (optional, but recommended)
            if (qtyOrdered <= 0) {
                throw new IllegalArgumentException("Quantity ordered must be positive.");
            }
	
            // Get product details
            int productId = Integer.parseInt(req.getParameter("productId"));
            Product product = productDAO.getProductById(productId);
            if (product == null) {
                throw new IllegalArgumentException("Product not found.");
            }

            // Create and populate Order object
            Order order = new Order();

            // Adding user details
            
            Register registerUser = (Register) session.getAttribute("user");
            order.setRegister(registerUser);
            order.setShippingAddress(registerUser.getAddress());

            // Adding product and total amount
            order.setProduct(product);
            Double totalAmount = product.getPrice() * qtyOrdered;
            order.setTotalAmount(totalAmount);

            // Order status and quantity
            order.setStatus("pending");
            order.setQtyOrder(qtyOrdered);

            // Checking inventory (assuming inventoryCheck throws an exception if insufficient stock)
//            InventoryDAO inventoryDAO = new InventoryDAO();
//            int pid = product.getId();
//            inventoryDAO.inventoryCheck(pid, qtyOrdered);
           
            // Save the order
            orderDAO.saveOrder(order);
            InventoryDAO invDao = new InventoryDAO();
            System.out.println(1.1);
            Inventory inventory = product.getInventory(); 
            int i = invDao.checkAndUpdateInventory(product.getId(), qtyOrdered);
            int I = 0;
            System.out.println(1.2);
            if(i>=0) {
            	order.setStatus("completed");
            	orderDAO.updateOrder(order);
            	if(i==0){
            		AdminLog log = new AdminLog(product, "Product finished", "Out of Stock", LocalDateTime.now(), "not available");
            		adminLogDao.saveAdminLog(log);
            	}else if(i<inventory.getReorderLevel()){
            		AdminLog log = new AdminLog(product, "Product hit reorder level", "Reorder", LocalDateTime.now(), "available");
            		adminLogDao.saveAdminLog(log);
            	}
            	reloadServlet.reloadProductList(productDAO, req);
            	resp.sendRedirect("views/productList.jsp?message=Order completed");
            	
            }else {
            	I = Math.abs(i);
            	AdminLog log = new AdminLog();
            	log.setDateCreated(LocalDateTime.now());
            	log.setMessage("Manufacture " + I + " more products to get this order.");
            	log.setOrder(order);
            	log.setType("order");
            	log.setProduct(product);
            	log.setStatus("cancelled");

            	adminLogDao.saveAdminLog(log);
  	
            	resp.sendRedirect("views/productList.jsp?message=Insufficient inventory product will be replenished soon.");
            }
            // Redirect to success page (replace with your actual success page)
            
//            resp.sendRedirect("orderSuccess.jsp");
	}
        } catch (NumberFormatException e) {
            // Handle invalid number format (e.g., non-numeric input)
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid quantity or register ID format.");
        } catch (IllegalArgumentException e) {
            // Handle specific validation errors
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST, e.getMessage());
        } catch (Exception e) {
            // Handle other unexpected exceptions
            resp.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "An error occurred while placing your order."+e);
        }
        
        
        
    }
    
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            // Retrieve the order ID from the request parameter
            int orderId = Integer.parseInt(req.getParameter("orderId"));

            List<Order> orders = orderDAO.getAllOrders();
            
          	HttpSession session = req.getSession();
            session.setAttribute("OrdertList", orders);
            
            // Retrieve order details from the database
            Order order = orderDAO.getOrderById(orderId);
            if (order == null) {
                throw new IllegalArgumentException("Order not found.");
            }

            // Set order details as request attributes for the JSP
            req.setAttribute("order", order);

            // Forward to a JSP page to display order details (replace with your actual JSP page)
            RequestDispatcher dispatcher = req.getRequestDispatcher("orderDetails.jsp");
            dispatcher.forward(req, resp);
            

        } catch (NumberFormatException e) {
            // Handle invalid number format (e.g., non-numeric input)
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid order ID format.");
        } catch (IllegalArgumentException e) {
            // Handle specific validation errors
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST, e.getMessage());
        } catch (Exception e) {
            // Handle other unexpected exceptions
            resp.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "An error occurred while retrieving the order details." + e);
        }
    }
    
    // Implementations for doGet, doPut, doDelete (same structure as doPost)
}