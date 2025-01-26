package com.FM.Servlet;

import jakarta.servlet.*;
import jakarta.servlet.http.*;
import java.io.IOException;
import java.util.List;

import com.FM.DAO.ProductDAO;
import com.FM.Entities.Product;


/**
 * Servlet implementation class RegisterServlet
 */
public class MaterialServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

    public MaterialServlet() {
        super();
        // TODO Auto-generated constructor stub
    }


	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}


	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
