package com.FM.DAO;

import java.util.List;
import org.hibernate.*;
import com.FM.HibernateUtil;
import com.FM.Entities.Product;

import com.FM.Entities.Order;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import java.util.List;

public class OrderDAO {

    public void saveOrder(Order order) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction transaction = session.beginTransaction();
        session.save(order);
        transaction.commit();
        session.close();
    }

    public Order getOrderById(int orderId) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return session.get(Order.class, orderId);
        }
    }

    public List<Order> getAllOrders() {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return session.createQuery("from Order", Order.class).list();
        }
    }

    public void updateOrder(Order order) {
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            session.update(order);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) transaction.rollback();
            e.printStackTrace();
        }
    }

    public void deleteOrder(Long id) {
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            Order order = session.get(Order.class, id);
            if (order != null) {
                session.delete(order);
                transaction.commit();
            }
        } catch (Exception e) {
            if (transaction != null) transaction.rollback();
            e.printStackTrace();
        }
    }
    
    public void deleteAllOrdersByProductId(int productId) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction transaction = session.beginTransaction();

        try {
            // HQL query to delete admin logs related to the product
            String hql = "DELETE FROM Order WHERE product.id = :productId";
            Query query = session.createQuery(hql);
            query.setParameter("productId", productId);
            int deletedRows = query.executeUpdate(); // Executes delete query

            transaction.commit();
            System.out.println("Deleted " + deletedRows + " orders related to productId " + productId);
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        } finally {
            session.close();
        }
    }
    
    public Order getPendingByProduct(String productName) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            // Use parameterized query to prevent SQL injection
            Query<Order> query = session.createQuery(
                    "FROM Order WHERE product.name = :productName AND status = 'pending' ORDER BY orderDate ASC", Order.class); // Assuming 'id' is a suitable column for ordering
            query.setParameter("productName", productName);
            // Limit the result to one
            query.setMaxResults(1);

            List<Order> results = query.list();
            if (results.isEmpty()) {
                return null; // Or throw an exception if appropriate
            } else {
                return results.get(0);
            }
        }
    }
    public static void truncateOrderTable() {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction transaction = session.beginTransaction();
        session.createNativeQuery("SET FOREIGN_KEY_CHECKS = 0").executeUpdate();
            // Execute the TRUNCATE TABLE SQL query
            String sql = "TRUNCATE TABLE orders";
            session.createNativeQuery(sql).executeUpdate();
            session.createNativeQuery("SET FOREIGN_KEY_CHECKS = 1").executeUpdate();
            transaction.commit();

            session.close();
        }
}