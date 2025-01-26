package com.FM.DAO;

import java.util.List;
import org.hibernate.*;
import com.FM.HibernateUtil;
import com.FM.Entities.Register;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;


public class RegisterDAO {

    public void save(Register register) {
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            session.save(register);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) transaction.rollback();
            e.printStackTrace();
        }
    }

    public Register getById(Long id) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return session.get(Register.class, id);
        }
    }

    public List<Register> getAll() {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Query<Register> query = session.createQuery("from Register", Register.class);
            return query.list();
        }
    }

    public void update(Register register) {
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            session.update(register);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) transaction.rollback();
            e.printStackTrace();
        }
    }

    public void delete(Long id) {
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            Register register = session.get(Register.class, id);
            if (register != null) {
                session.delete(register);
            }
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) transaction.rollback();
            e.printStackTrace();
        }
    }
    
    public Register authenticate(String email, String password) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            String hql = "FROM Register WHERE email = :email AND password = :password";
            Query<Register> query = session.createQuery(hql, Register.class);
            query.setParameter("email", email);
            query.setParameter("password", password);
            return query.uniqueResult();
        }
    
    }

    public boolean authenticateEmail(String email) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            String hql = "SELECT email FROM Register WHERE email = :email";
            Query<String> query = session.createQuery(hql, String.class); // Correct return type
            query.setParameter("email", email);

            // Check if a result is returned
            return query.uniqueResult() != null;
        } catch (Exception e) {
            e.printStackTrace(); // Log exception for debugging
            return false; // In case of exception, return false
        }
    }

    public static void truncateRegisterTable() {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction transaction = session.beginTransaction();
        session.createNativeQuery("SET FOREIGN_KEY_CHECKS = 0").executeUpdate();
            // Execute the TRUNCATE TABLE SQL query
            String sql = "TRUNCATE TABLE register";
            session.createNativeQuery(sql).executeUpdate();
            session.createNativeQuery("SET FOREIGN_KEY_CHECKS = 1").executeUpdate();
            transaction.commit();

            session.close();
        }

    
}
