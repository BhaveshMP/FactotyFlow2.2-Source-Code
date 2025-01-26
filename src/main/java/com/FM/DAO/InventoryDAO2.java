package com.FM.DAO;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import com.FM.DatabaseConnection5;
import com.FM.HibernateUtil;
import com.FM.Entities.AdminLog;
import com.FM.Entities.Inventory;
import com.FM.Entities.Order;
import com.FM.Entities.Product;


import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;


public class InventoryDAO2 {

    private SessionFactory sessionFactory;
//    private static EntityManagerFactory emf = Persistence.createEntityManagerFactory("FactoryManager");
    // done implementation in InventoryDAO
    public List<Inventory> getAllInventories() {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return session.createQuery("from Inventory", Inventory.class).list();
        }
    }
    // done implementation in InventoryDAO
    public void deleteProduct(int invId) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction transaction = session.beginTransaction();
        
        // Retrieve the product from the database
        Inventory inventory = session.get(Inventory.class, invId);
        if (inventory != null) {
            session.delete(inventory); // Delete the product
        }
        
        transaction.commit();
        session.close();
    }
    // done implementation in InventoryDAO
    public Inventory getInventoryByProductId(int productId) {
        Inventory inventory = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
        	
            
            // Create HQL query to get Inventory by product_id
            String hql = "FROM Inventory i WHERE i.product.id = :productId";
            Query<Inventory> query = session.createQuery(hql, Inventory.class);
            query.setParameter("productId", productId);
            // Execute query and get single result
            inventory = query.uniqueResult();
            if (inventory != null) {
                System.out.println("Inventory found for product ID: " + productId);
            } else {
                System.out.println("No inventory found for product ID: " + productId);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return inventory;
    }
    
    // done implementation in InventoryDAO
    public int checkAndUpdateInventory(Inventory inventory, int orderQty) {
        Connection conn = null;
        CallableStatement cstmt = null;
        int resultQty = -1; // Variable to store the result from the function

        try {
            // Get connection from utility class
            conn = DatabaseConnection5.getConnection();

            // Prepare the callable statement to execute the stored function
            String sql = "{? = CALL check_and_update_inventory(?, ?)}";
            cstmt = conn.prepareCall(sql);

            // Register the OUT parameter (the return value of the function)
            cstmt.registerOutParameter(1, Types.INTEGER);

            // Set parameters for the stored function
            cstmt.setInt(2, inventory.getProduct().getId()); // Product ID
            cstmt.setInt(3, orderQty); // Ordered quantity

            // Execute the callable statement
            cstmt.execute();

            // Retrieve the returned result from the function
            resultQty = cstmt.getInt(1);
            System.out.println("Resulting quantity after update: " + resultQty);

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            // Close resources using utility method
            DatabaseConnection5.closeConnection(conn);
        }

        return resultQty;
    }

    // done implementation in InventoryDAO
    public void insertInventory(Inventory inventory) {
        Connection conn = null;
        CallableStatement cstmt = null;

        try {
        	   System.out.println("Waiting for 5 seconds...");
                 // Sleep for 5 seconds (5000 milliseconds)
            // Get connection from utility class
            conn = DatabaseConnection5.getConnection();

            // Prepare the callable statement to execute the stored procedure
            String sql = "{CALL insert_inventory(?, ?, ?, ?)}";
            cstmt = conn.prepareCall(sql);


            // Set parameters for the stored procedure
            cstmt.setInt(1,inventory.getProduct().getId()); // Assuming there's a method to get the product ID
            cstmt.setInt(2, inventory.getQuantityAvailable());
            cstmt.setInt(3, inventory.getReorderLevel());
            cstmt.setString(4, inventory.getType());

            // Execute the stored procedure
            cstmt.execute();
            System.out.println("Inventory inserted successfully!");
            
        } catch (SQLException e) {
        	
            e.printStackTrace();
		} finally {
            // Close resources using utility method
            DatabaseConnection5.closeConnection(conn);
        }
    }
  
    // done implementation in InventoryDAO
    public List<AdminLog> fetchPendingAdminLogs() {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return session.createQuery("from AdminLog", AdminLog.class).list();
        }
    	
    }
    
//    public List<AdminLog> fetchPendingAdminLogs() {
//        List<AdminLog> adminLogs = new ArrayList<>();
//        Connection conn = null;
//        CallableStatement cstmt = null;
//        ResultSet rs = null;
//
//        try {
//            // Get connection from utility class
//            conn = DatabaseConnection.getConnection();
//
//            // Prepare the callable statement to execute the stored procedure
//            String sql = "{CALL fetch_pending_logs()}";
//            cstmt = conn.prepareCall(sql);
//
//            // Execute the stored procedure
//            cstmt.execute();
//
//            // Get the result set
//            rs = cstmt.getResultSet();
//
//            ProductDAO productDAO = new ProductDAO();
//            OrderDAO orderDAO = new OrderDAO();
//            // Process the result set
//            while (rs.next()) {
//                AdminLog log = new AdminLog();
//                log.setLogId(rs.getLong("log_id"));
//
//                
//                //handle cursor
//                int productId = rs.getInt("product_id");
//                int orderId = rs.getInt("order_id");
//                
//                Product product = productDAO.getProductById(productId);
//                Order order = orderDAO.getOrderById(orderId);
//                
//                log.setProduct(product);
//                log.setOrder(order);
//                
//                log.setMessage(rs.getString("message")!=null?rs.getString("message"):null);
//                log.setType(rs.getString("type"));
//                log.setDateCreated(rs.getTimestamp("date_created").toLocalDateTime());
//                log.setStatus(rs.getString("status"));
//
//                adminLogs.add(log);
//            }
//            
//            System.out.print(adminLogs.get(0).getStatus());
//
//        } catch (SQLException e) {
//            e.printStackTrace();
//        } finally {
//            // Close resources using utility method
//            DatabaseConnection.closeConnection(conn);
//            if (rs != null) try { rs.close(); } catch (SQLException e) { e.printStackTrace(); }
//            if (cstmt != null) try { cstmt.close(); } catch (SQLException e) { e.printStackTrace(); }
//        }
//
//        return adminLogs;
//    }
    
    public int updateProductQuantity(int productId, int quantityChange) {
        Connection conn = null;
        CallableStatement cstmt = null;
        int resultQty = -1;
        try {
            // Get connection from utility class
            conn = DatabaseConnection5.getConnection();

            // Prepare the callable statement to execute the stored procedure
            String sql = "{CALL update_available_product(?, ?, ?)}";  // Add OUT parameter
            cstmt = conn.prepareCall(sql);

            // Set IN parameters for the stored procedure
            cstmt.setInt(1, productId);       // Product ID
            cstmt.setInt(2, quantityChange);  // Quantity change

            // Register the OUT parameter for the updated quantity
            cstmt.registerOutParameter(3, java.sql.Types.INTEGER);

            // Execute the stored procedure
            cstmt.execute();
            
            // Retrieve the updated quantity from the OUT parameter
            resultQty = cstmt.getInt(3);
            
        } catch (SQLException e) {
            // Handle the exception (print error message)
            System.err.println("SQL Error: " + e.getMessage());
        } finally {
            // Close resources
            DatabaseConnection5.closeConnection(conn);
            if (cstmt != null) try { cstmt.close(); } catch (SQLException e) { e.printStackTrace(); }
        }
        return resultQty;
    }
    
    // done implementation in InventoryDAO
    public void saveAdminLog(AdminLog log) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction transaction = session.beginTransaction();
        session.save(log);
        transaction.commit();
        session.close();
    }
}
