package com.FM.Servlet;

import jakarta.servlet.*;
import jakarta.servlet.http.*;
import java.io.IOException;
import java.util.List;

import com.FM.DAO.ProductDAO;
import com.FM.Entities.Product;



import com.FM.DAO.InventoryDAO;
import com.FM.Entities.Inventory;
import com.FM.Entities.Product;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebServlet("/inventoryServlet")
public class InventoryServlet2 extends HttpServlet {
//NOT USING
    private InventoryDAO inventoryDAO;
    private ProductDAO productDAO;
    @Override
    public void init() {
        inventoryDAO = new InventoryDAO();  // Initialize DAO here
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // Retrieve parameters from request
        int productId = Integer.parseInt(req.getParameter("productId"));
        int quantityAvailable = Integer.parseInt(req.getParameter("quantityAvailable"));
        int reorderLevel = Integer.parseInt(req.getParameter("reorderLevel"));

        // Create and populate Inventory object
        Inventory inventory = new Inventory();
        
        //adding req product and total amount
        Product product = (Product)productDAO.getProductById(productId);  
        inventory.setProduct(product);
        inventory.setQuantityAvailable(10);
        inventory.setReorderLevel(10);

        resp.sendRedirect("oma.jsp");
        // Save the inventory
//        inventoryDAO.saveInventory(inventory);
//
//        resp.sendRedirect("inventoryList.jsp");  // Redirect to a page listing inventories
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String action = req.getParameter("action");

        if ("list".equals(action)) {
            // Handle listing all inventories
            req.setAttribute("inventories", inventoryDAO.getAllInventories());
            req.getRequestDispatcher("inventoryList.jsp").forward(req, resp);
        } else if ("view".equals(action)) {
            Long id = Long.parseLong(req.getParameter("id"));
            Inventory inventory = inventoryDAO.getInventoryById(id);
            req.setAttribute("inventory", inventory);
            req.getRequestDispatcher("inventoryDetail.jsp").forward(req, resp);
        }
    }

}
