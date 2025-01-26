package com.FM.Entities;

import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;

import java.util.Date;


@Entity
@Table(name = "suppliers")
public class Supplier {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "supplier_id")  // Primary key in snake_case
    private Long supplierId;

    @Column(name = "name", length = 100, nullable = false)  // Name of the supplier
    private String name;

    @Column(name = "contact_info", length = 255)  // Contact information for the supplier
    private String contactInfo;

    @Column(name = "address", length = 255)  // Address of the supplier
    private String address;

    // Constructors, getters, and setters
    public Supplier() {
    }

    public Supplier(String name, String contactInfo, String address) {
        this.name = name;
        this.contactInfo = contactInfo;
        this.address = address;
    }

    // Getters and setters
    public Long getSupplierId() {
        return supplierId;
    }

    public void setSupplierId(Long supplierId) {
        this.supplierId = supplierId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getContactInfo() {
        return contactInfo;
    }

    public void setContactInfo(String contactInfo) {
        this.contactInfo = contactInfo;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}
