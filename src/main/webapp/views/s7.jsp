  
  <%@ page import="com.FM.DAO.OrderDAO" %>
  <%@ page import="com.FM.Entities.Order" %>
   
   <%
   
   OrderDAO orderDao = new OrderDAO();
   Order order = orderDao.getPendingByProduct("headphone");
   
   
   %>
   
   <%=order.getOrderId() %>>
   <%=order.getTotalAmount() %>>