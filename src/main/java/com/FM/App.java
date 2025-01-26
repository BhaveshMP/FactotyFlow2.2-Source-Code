package com.FM;

import org.hibernate.cfg.Configuration;

import com.FM.DAO.InventoryDAO2;
import com.FM.Entities.AdminLog;
import com.FM.Entities.Inventory;
import com.FM.Entities.Order;
import com.FM.Entities.Product;
import com.FM.Entities.Register;

import java.time.LocalDateTime;
import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

public class App{
	public static void main( String[] args ){
		Configuration conf = new Configuration().configure("hibernate.cfg.xml");
		SessionFactory sf = conf.buildSessionFactory();
		
		Session session = sf.openSession();
		Transaction tx = session.beginTransaction();
		
		
		
//	    Product p1 = new Product();
//	    p1.setName("Lamborghini");
//	    p1.setPrice(600.0);
//	    p1.setQty(10);    
//	    p1.setDescription("Orange Lamborghini Aventador LP 700-4 1:24 Scale Die-Cast Car by Maisto");
//	    session.save(p1);	   
//		   Register r1 = new Register();
//		   r1.setId(2); // Assuming you want to set a specific ID
//		   r1.setName("Admin");
//		   r1.setPassword("admin");
//		   r1.setEmail("admin@example.com");
//		   r1.setAddress("123 Main Street");
//		   r1.setContact("1234567890");
//		   r1.setAccountType("admin"); 
//		   session.save(r1);   
//	   Order o1 = new Order();
//	   o1.setStatus("pending");
//	   o1.setProduct(p1);
//	   o1.setTotalAmount(1200.0);
//	   o1.setQtyOrder(2);
//	   o1.setRegister(r1);
//	   o1.setShippingAddress("panvel");
//		session.save(o1);	
//		
//		   AdminLog a1 = new AdminLog();
//		   a1.setProduct(p1);
//		   a1.setMessage("Add quantity of product id: 1");
//		   a1.setStatus("pending");
//		   a1.setDateCreated(LocalDateTime.now());
//		   session.save(a1);	
//		tx.commit();
//			session.close();   
//	   Inventory inventory = new Inventory();
//	   System.out.print(p1.getId());
//	   inventory.setProduct(p1);
//	   inventory.setQuantityAvailable(8);
//	   inventory.setReorderLevel(10);
//	   inventory.setType("product");	   
//	   InventoryDAO inv = new InventoryDAO(); 
//	   inv.insertInventory(inventory);
//	   
	   //1. Inserting Inventory
	   
	   //3. fetch pending admin logs CURSOR
//	   List<AdminLog> lista = inv.fetchPendingAdminLogs();
//	   String s = lista.get(0).getProduct().getName();
//	   System.out.print(s);	 
	   
//	   Product product = session.get(Product.class, 1);
//	   inventory.setProduct(product); // Ensure this is set
	   
	   //4. update on availability Function 
//	   inventory = inv.getInventoryByProductId(1);
//	   int i = inv.checkAndUpdateInventory(inventory, 3);
	     

	   //5. update available Exception handling negative number
//	   int i =inv.updateProductQuantity(1, 8);
	   
//   System.out.print("change: "+i);	

	}
}
