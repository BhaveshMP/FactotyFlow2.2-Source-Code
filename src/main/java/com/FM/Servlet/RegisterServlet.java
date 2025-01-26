package com.FM.Servlet;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.util.List;

import com.FM.DAO.RegisterDAO;
import com.FM.Entities.Product;
import com.FM.Entities.Register;

@WebServlet("/register")
public class RegisterServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	private RegisterDAO registerDAO;
	
    public RegisterServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

    @Override
    public void init() throws ServletException {
        // Initialize the RegisterDAO
        registerDAO = new RegisterDAO();
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // Retrieve form parameters
        String name = request.getParameter("name");
        String password = request.getParameter("password");
        String email = request.getParameter("email");
        String address = request.getParameter("address");
        String contact = request.getParameter("contact");
        String accountType = request.getParameter("accountType");

        Register register = new Register();
        register.setName(name);
        register.setPassword(password);
        register.setEmail(email);
        register.setAddress(address);
        register.setContact(contact);
        register.setAccountType(accountType);
        
        if(request.getParameter("id")!=null && !request.getParameter("id").isEmpty()) {
        	
        	register.setId(Integer.parseInt(request.getParameter("id")));
        	registerDAO.update(register);
            response.sendRedirect("views/login.jsp");
        }else {
        // Create a new Register entity   
//        if(request.getParameter("id")!=null){
//        	int id = Integer.parseInt(request.getParameter("id"));
//        	registerDAO.update(register);
//        	response.sendRedirect("index.jsp");
//        }
        // Save the Register entity using RegisterDAO
        	if(registerDAO.authenticateEmail(email)) {
        		response.sendRedirect("views/register.jsp?error=true&message=Email already exist.");
        	}else {	
        	registerDAO.save(register);
        // Redirect to a success page or display a success message
        response.sendRedirect("views/login.jsp");
//        response.sendRedirect("views/success.jsp?text=Registered succefully !!!");
        	}
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
    	
    	String idParam = request.getParameter("id");
        // Retrieve user by ID (for demonstration)
       if (idParam != null && !idParam.isEmpty()) {
            Long id = Long.parseLong(idParam);
            Register register = registerDAO.getById(id);

            // Forward to a JSP page to display user details
            request.setAttribute("register", register);
            request.getRequestDispatcher("userDetails.jsp").forward(request, response);
        } else {
            // Handle case where ID is not provided
            response.sendRedirect("views/error.jsp?error=Login Error");
        }
    }
}
