package com.FM.Servlet;

import jakarta.servlet.*;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.io.IOException;
import java.util.List;

import com.FM.DAO.AdminLogDAO;
import com.FM.DAO.InventoryDAO;
import com.FM.DAO.OrderDAO;
import com.FM.DAO.ProductDAO;
import com.FM.DAO.RegisterDAO;
import com.FM.Entities.AdminLog;
import com.FM.Entities.Inventory;
import com.FM.Entities.Order;
import com.FM.Entities.Register;


@WebServlet("/inventory")
public class InventoryServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;
    private RegisterDAO registerDAO;

    private ReloadServlet reloadServlet;

    @Override
    public void init() throws ServletException {
        // Initialize the RegisterDAO
        registerDAO = new RegisterDAO();
        reloadServlet = new ReloadServlet();
    }

//    @Override
//    protected void doPost(HttpServletRequest request, HttpServletResponse response)
//            throws ServletException, IOException {
//        // Retrieve form parameters
//        String email = request.getParameter("email");
//        String password = request.getParameter("password");
//
//        // Authenticate user
//        Register user = registerDAO.authenticate(email, password);
//
//        if (user != null) {
//            // User is authenticated, create a session
//        	HttpSession session = request.getSession();
//            session.setAttribute("user", user);
//            
//        	if(user.getAccountType().equals("admin"))
//        		response.sendRedirect("views/product.jsp");
//        	else if(user.getAccountType().equals("customer"))
//        		response.sendRedirect("ProductServlet");
//        } else {
//        	// Authentication failed, redirect to login page with error
//            response.sendRedirect("views/login.jsp?error=true");
//        }
//        
//        if(request.getParameter("requestType")!=null && !request.getParameter("requestType").isEmpty()) {
//        	if(request.getParameter("requestType").equals("logout")){
//        		HttpSession session = request.getSession();
//                session.setAttribute("user", null);
//        	}
//        }
//    }
    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
    	
    	if(request.getParameter("type")!=null && !request.getParameter("type").isEmpty()) {
    		
    		String redirect = request.getParameter("location");
    		InventoryDAO invDao = new InventoryDAO();
    		OrderDAO orderDao = new OrderDAO();
    		AdminLogDAO adminLogDao = new AdminLogDAO();
    	    ProductDAO productDAO = new ProductDAO();
    		
		
    		if(request.getParameter("type").equals("inventoryUpdate")) {
    			
    			int p_id = Integer.parseInt(request.getParameter("productId"));
    			int q_change = Integer.parseInt(request.getParameter("quantityInput"));
    			String message = "";
                // Validate quantity change
                if (q_change < 0) {
                	System.out.println("Quantity change cannot be negative. | InvetoryDAO");
                	response.sendRedirect("views/inventories.jsp?message= Quantity cannot be negative.");
                	return;
//                    throw new IllegalArgumentException("Quantity change cannot be negative. | InvetoryDAO");
                    
                }else {
    			int i = invDao.updateProductQuantity(p_id, q_change);
    			System.out.println("Updated qty: "+i);
    			
    			Order order = orderDao.getPendingByProduct(productDAO.getProductById(p_id).getName());
    			
    			while(order!=null && order.getQtyOrder()<=i) {
    					i = invDao.checkAndUpdateInventory(p_id, order.getQtyOrder());
    					message = "?message=Order("+ order.getQtyOrder() +") for "+  order.getProduct().getName() + " completed. \nQuantity deducted from inventory: "+order.getQtyOrder();
    					order.setStatus("Completed");
    					orderDao.updateOrder(order);
    					
    			}
    			
    			reloadServlet.reloadInventoryList(invDao, request);
    			String adminlogs= request.getParameter("type");
                }
    			try {
					Thread.sleep(5000);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
    			response.sendRedirect("views/"+redirect+".jsp" +message);
    			
    		}
    	}
    	
    	
		/*
		 * String idParam = request.getParameter("id"); // Retrieve user by ID (for
		 * demonstration) if (idParam != null && !idParam.isEmpty()) { Long id =
		 * Long.parseLong(idParam); Register register = registerDAO.getById(id);
		 * 
		 * // Forward to a JSP page to display user details
		 * request.setAttribute("register", register);
		 * request.getRequestDispatcher("userDetails.jsp").forward(request, response); }
		 * else { // Handle case where ID is not provided
		 * response.sendRedirect("views/error.jsp?error=Login Error"); }
		 */
    }
    
    
    
}
