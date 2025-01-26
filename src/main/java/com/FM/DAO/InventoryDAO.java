package com.FM.DAO;

import java.time.LocalDateTime;
import java.util.List;
import org.hibernate.*;
import org.hibernate.query.Query;

import com.FM.HibernateUtil;
import com.FM.Entities.AdminLog;
import com.FM.Entities.Inventory;
import com.FM.Entities.Product;
import java.util.concurrent.TimeUnit;
public class InventoryDAO {

    public void saveInventory(Inventory inventory) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction transaction = session.beginTransaction();
            session.save(inventory);
            transaction.commit();
            session.close();
    }

    public Inventory getInventoryById(Long id) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return session.get(Inventory.class, id);
        }
    }
    
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


    public List<Inventory> getAllInventories() {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return session.createQuery("from Inventory", Inventory.class).list();
        }
    }

    public void updateInventory(Inventory inventory) {
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            session.update(inventory);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) transaction.rollback();
            e.printStackTrace();
        }
    }

    public void deleteInventory(Long id) {
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            Inventory inventory = session.get(Inventory.class, id);
            if (inventory != null) {
                session.delete(inventory);
                transaction.commit();
            }
        } catch (Exception e) {
            if (transaction != null) transaction.rollback();
            e.printStackTrace();
        }
    }
    
    public int checkAndUpdateInventory(int pid, int quantity) {
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();

         // Retrieve  the inventory record for the given product
            ProductDAO productDAO = new ProductDAO();
            Product product = productDAO.getProductById(pid);
            Inventory inventory = product.getInventory();
            if (inventory == null) {

                // Handle the case where the product doesn't have inventory
            	System.out.println("Product not found.|InventoryDAO");
                throw new RuntimeException("Product not found.|InventoryDAO");
            }

            
            // Update the inventory quantity

            int availableAfterOrder = inventory.getQuantityAvailable() - quantity;

            if(availableAfterOrder>=0) {
            inventory.setQuantityAvailable(availableAfterOrder);
            product.setQty(availableAfterOrder);
            session.update(product);
            session.update(inventory);
            transaction.commit();
            
            }
            return availableAfterOrder; // Inventory check passed
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
            return 0; // Inventory check failed
        }
    }

    
    public int updateProductQuantity(int productId, int quantityChange) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction transaction = null;
        int updatedQuantity = -1;

        try {
            // Start a transaction
            transaction = session.beginTransaction();



            // Fetch the current inventory
            Product product = session.get(Product.class, productId);
            Inventory inventory = product.getInventory();
            if (inventory == null) {
            	System.out.println("Product not found.");
                throw new IllegalArgumentException("Product not found.");
            }

            // Get the current quantity
            int currentQuantity = inventory.getQuantityAvailable();

            // Update the inventory quantity
            inventory.setQuantityAvailable(currentQuantity + quantityChange);
            inventory.getProduct().setQty(currentQuantity + quantityChange);


            // Commit the transaction
            session.update(inventory);
            transaction.commit();

            // Return the updated quantity
            updatedQuantity = inventory.getQuantityAvailable();

        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            System.err.println("Error: " + e.getMessage());
        } finally {
            session.close();
        }

        return updatedQuantity;
    }
    
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
    
    public static void truncateInventoryTable() {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction transaction = session.beginTransaction();
        session.createNativeQuery("SET FOREIGN_KEY_CHECKS = 0").executeUpdate();
            // Execute the TRUNCATE TABLE SQL query
            String sql = "TRUNCATE TABLE inventory";
            session.createNativeQuery(sql).executeUpdate();
            
            session.createNativeQuery("SET FOREIGN_KEY_CHECKS = 1").executeUpdate();
            transaction.commit();

            session.close();
        }
    
}