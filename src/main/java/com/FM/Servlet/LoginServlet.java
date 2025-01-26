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
import com.FM.Entities.Order;
import com.FM.Entities.Product;
import com.FM.Entities.Register;


@WebServlet("/login")
public class LoginServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;
    private RegisterDAO registerDAO;
    private ProductDAO productDAO = new ProductDAO();
    private InventoryDAO inventoryDAO = new InventoryDAO();
    private OrderDAO orderDAO = new OrderDAO();
    private AdminLogDAO adminLogDAO = new AdminLogDAO();
    private ReloadServlet reloadServlet;
    @Override
    public void init() throws ServletException {
        // Initialize the RegisterDAO
        registerDAO = new RegisterDAO();
        reloadServlet = new ReloadServlet();
        
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // Retrieve form parameters
        String email = request.getParameter("email");
        String password = request.getParameter("password");

        // Authenticate user
        boolean isEmailPresent = registerDAO.authenticateEmail(email);
        
        Register user = registerDAO.authenticate(email, password);
        reloadServlet.reloadProductList(productDAO, request);
        reloadServlet.reloadInventoryList(inventoryDAO, request);
        reloadServlet.reloadOrderList(orderDAO, request);
        reloadServlet.reloadAdminLogList(adminLogDAO, request);
        
        if (user != null) {
            // User is authenticated, create a session
        	HttpSession session = request.getSession();
            session.setAttribute("user", user);
            
        	if(user.getAccountType().equals("admin"))
        		response.sendRedirect("views/productA.jsp");
        	else if(user.getAccountType().equals("customer"))
        		response.sendRedirect("views/productList.jsp");
        }else if(isEmailPresent){
        	response.sendRedirect("views/login.jsp?error=true&message=Invalid password.");

        }
        
        else {
        	// Authentication failed, redirect to login page with error
            response.sendRedirect("views/login.jsp?error=true&message=Invalid email or password.");
        }
        

    }
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
    	
        if(request.getParameter("requestType")!=null && !request.getParameter("requestType").isEmpty()) {
        	if(request.getParameter("requestType").equals("logout")){
        		HttpSession session = request.getSession();
                session.setAttribute("user", null);
                response.sendRedirect("views/login.jsp");
        	}
        }
    
    }
}
