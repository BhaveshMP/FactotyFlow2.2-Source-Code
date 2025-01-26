package com.FM.Servlet;

import jakarta.servlet.*;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.io.IOException;
import java.util.List;

import com.FM.DAO.InventoryDAO2;
import com.FM.DAO.RegisterDAO;
import com.FM.Entities.AdminLog;
import com.FM.Entities.Inventory;
import com.FM.Entities.Register;


@WebServlet("/adminLog")
public class AdminLogServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;
    private RegisterDAO registerDAO;

    @Override
    public void init() throws ServletException {
        // Initialize the RegisterDAO
        registerDAO = new RegisterDAO();
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
    		InventoryDAO2 invDao = new InventoryDAO2();
    		
    		if(request.getParameter("type").equals("adminlogs")) {
    			
    			try {
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
    			response.sendRedirect("views/adminLogs.jsp");
    			
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
