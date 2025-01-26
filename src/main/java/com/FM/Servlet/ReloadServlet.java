package com.FM.Servlet;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.util.List;

import com.FM.DAO.AdminLogDAO;
import com.FM.DAO.InventoryDAO;
import com.FM.DAO.InventoryDAO2;
import com.FM.DAO.OrderDAO;
import com.FM.DAO.ProductDAO;
import com.FM.DAO.RegisterDAO;
import com.FM.Entities.AdminLog;
import com.FM.Entities.Inventory;
import com.FM.Entities.Order;
import com.FM.Entities.Product;
import com.FM.Entities.Register;

@WebServlet("/reload")
public class ReloadServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	
    public ReloadServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

    @Override
    public void init() throws ServletException {

    }

    	
    public void reloadProductList(ProductDAO productDAO, HttpServletRequest request) {
    List<Product> products = productDAO.getAllProducts();
  	HttpSession session = request.getSession();
    session.setAttribute("ProductList", products);
    request.setAttribute("products", products);
    
    
    }
    
    public void reloadInventoryList(InventoryDAO invDao, HttpServletRequest request) {
		List<Inventory> InventoryList = invDao.getAllInventories();
      	HttpSession session = request.getSession();
        session.setAttribute("InventoryList", InventoryList);
    }

	public void reloadOrderList(OrderDAO orderDAO, HttpServletRequest request) {
		// TODO Auto-generated method stub
        List<Order> orders = orderDAO.getAllOrders();      
      	HttpSession session = request.getSession();
        session.setAttribute("OrderList", orders);
	}
	
	public void reloadAdminLogList(AdminLogDAO adminLogDAO, HttpServletRequest request) {
		List<AdminLog> AdminLogList = adminLogDAO.fetchPendingAdminLogs();
      	HttpSession session = request.getSession();
        session.setAttribute("AdminLogList", AdminLogList);
		String adminlogs= request.getParameter("type");
	}
	
	
	
}
