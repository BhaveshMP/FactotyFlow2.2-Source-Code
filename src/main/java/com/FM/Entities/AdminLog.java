package com.FM.Entities;

import jakarta.persistence.*;
import jakarta.persistence.TemporalType;

import java.time.LocalDateTime;
import java.util.Date;


@Entity
@Table(name = "admin_log")
public class AdminLog {
	

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "log_id")
    private Long logId;

    @ManyToOne
    @JoinColumn(name = "product_id", referencedColumnName = "id")
    private Product product;

    @ManyToOne
    @JoinColumn(name = "order_id", referencedColumnName = "order_id")
    private Order order;

    @Column(name = "message")
    private String message;

    @Column(name = "type")
    private String type;

    @Column(name = "date_created", updatable = false, columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
    private LocalDateTime dateCreated;

    @Column(name = "status")
    private String status;

    // Constructors
    public AdminLog() {
    }

    public AdminLog(Product product, Order order, String message, String type, LocalDateTime dateCreated, String status) {
        this.product = product;
        this.order = order;
        this.message = message;
        this.type = type;
        this.dateCreated = dateCreated;
        this.status = status;
    }
    
    public AdminLog(Product product, String message, String type, LocalDateTime dateCreated, String status) {
        this.product = product;
        this.message = message;
        this.type = type;
        this.dateCreated = dateCreated;
        this.status = status;
    }

    // Getters and Setters
    public Long getLogId() {
        return logId;
    }

    public void setLogId(Long logId) {
        this.logId = logId;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public Order getOrder() {
        return order;
    }

    public void setOrder(Order order) {
        this.order = order;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public LocalDateTime getDateCreated() {
        return dateCreated;
    }

    public void setDateCreated(LocalDateTime dateCreated) {
        this.dateCreated = dateCreated;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
