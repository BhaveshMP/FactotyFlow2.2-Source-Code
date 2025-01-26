package com.FM.DAO;

import java.util.List;
import org.hibernate.*;
import com.FM.HibernateUtil;
import com.FM.Entities.Product;
import com.FM.Entities.AdminLog;
import com.FM.Entities.Order;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import java.util.List;

public class AdminLogDAO {

   
    public List<AdminLog> fetchPendingAdminLogs() {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return session.createQuery("from AdminLog", AdminLog.class).list();
        }
    	
    }
    
    public void saveAdminLog(AdminLog log) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction transaction = session.beginTransaction();
        session.save(log);
        transaction.commit();
        session.close();
    }
    
    // Truncate table (delete all rows in a table)
    public static void truncateAdminLogTable() {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction transaction = session.beginTransaction();
        session.createNativeQuery("SET FOREIGN_KEY_CHECKS = 0").executeUpdate();
            // Execute the TRUNCATE TABLE SQL query
            String sql = "TRUNCATE TABLE admin_log";
            session.createNativeQuery(sql).executeUpdate();
            session.createNativeQuery("SET FOREIGN_KEY_CHECKS = 1").executeUpdate();
            transaction.commit();

            session.close();
        }
    
    
    public void deleteAllAdminLogsByProductId(int productId) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction transaction = session.beginTransaction();

        try {

            // HQL query to delete admin logs related to the product

            String hql = "DELETE FROM AdminLog WHERE product.id = :productId";
            Query query = session.createQuery(hql);
            query.setParameter("productId", productId);
            int deletedRows = query.executeUpdate(); // Executes delete query
  
            transaction.commit();
            System.out.println("Deleted " + deletedRows + " admin logs related to productId " + productId);

        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        } finally {
            session.close();
        }
    }

}