package com.FM;

import com.mysql.cj.jdbc.AbandonedConnectionCleanupThread;
import org.hibernate.SessionFactory;

import java.sql.Driver;
import java.sql.DriverManager;
import java.util.Enumeration;

// Use Jakarta's ServletContextListener
import jakarta.servlet.ServletContextEvent;
import jakarta.servlet.ServletContextListener;

public class MyAppContextListener implements ServletContextListener {

    private static SessionFactory sessionFactory = HibernateUtil.getSessionFactory();

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        // Initialization logic, if needed
        System.out.println("Application started!");
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        // Step 1: Close Hibernate's SessionFactory
        if (sessionFactory != null) {
            sessionFactory.close();
            System.out.println("Hibernate SessionFactory closed.");
        }

        // Step 2: Deregister JDBC drivers
        Enumeration<Driver> drivers = DriverManager.getDrivers();
        while (drivers.hasMoreElements()) {
            Driver driver = drivers.nextElement();
            try {
                DriverManager.deregisterDriver(driver);
                System.out.println("Deregistered JDBC driver: " + driver);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        // Step 3: Stop MySQL's AbandonedConnectionCleanupThread
        try {
            AbandonedConnectionCleanupThread.checkedShutdown();
            System.out.println("AbandonedConnectionCleanupThread stopped.");
        } catch (Exception e) {
            e.printStackTrace();
        }

        System.out.println("Application shutdown complete.");
    }
}
