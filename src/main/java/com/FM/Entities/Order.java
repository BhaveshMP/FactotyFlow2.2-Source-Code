package com.FM.Entities;

import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;

import java.util.Date;

@Entity
@Table(name = "orders")
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "order_id")  // Use snake_case for consistency
    private Long orderId;

    @ManyToOne
    @JoinColumn(name = "register_id", nullable = false)  // Represent customerId as a foreign key
    private Register register;  // Assuming there's a Customer entity

    @CreationTimestamp  // Automatically sets the timestamp when the order is created
    @Temporal(TemporalType.TIMESTAMP)  // Store both date and time
    @Column(name = "order_date", nullable = false)
    private Date orderDate;

    @Column(name = "status", length = 20, nullable = false)
    private String status;

    @Column(name = "qty_order", nullable = false)
    private int qtyOrder;
    
    @Column(name = "total_amount", nullable = false)
    private Double totalAmount;

    @Column(name = "shipping_address", length = 255, nullable = false)
    private String shippingAddress;


    @ManyToOne  // One-to-One relationship with Product
    @JoinColumn(name = "product_id", nullable = false)  // Foreign key for Product entity
    private Product product;

    // Constructors, getters, and setters
    public Order() {
    }

    public Order(Register register, String status, Double totalAmount, String shippingAddress,Product product) {
        this.register = register;
        this.status = status;
        this.totalAmount = totalAmount;
        this.shippingAddress = shippingAddress;
        this.product = product;
    }

    // Getters and setters
    public Long getOrderId() {
        return orderId;
    }

    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }

    public Register getRegister() {
		return register;
	}

	public void setRegister(Register register) {
		this.register = register;
	}

	public Date getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(Date orderDate) {
        this.orderDate = orderDate;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Double getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(Double totalAmount) {
        this.totalAmount = totalAmount;
    }

    public String getShippingAddress() {
        return shippingAddress;
    }

    public void setShippingAddress(String shippingAddress) {
        this.shippingAddress = shippingAddress;
    }

	public Product getProduct() {
		return product;
	}

	public void setProduct(Product product) {
		this.product = product;
	}

	public int getQtyOrder() {
		return qtyOrder;
	}

	public void setQtyOrder(int qtyOrder) {
		this.qtyOrder = qtyOrder;
	}
	
    
}
