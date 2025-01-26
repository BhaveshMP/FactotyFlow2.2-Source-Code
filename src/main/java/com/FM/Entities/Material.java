package com.FM.Entities;
import java.util.function.Supplier;

import jakarta.persistence.*;

import jakarta.persistence.*;

@Entity
@Table(name = "material")
public class Material {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "material_id")  // snake_case for the primary key
    private Long materialId;

    @Column(name = "name", length = 100, nullable = false)
    private String name;

    @Column(name = "description", length = 255)
    private String description;

    @Column(name = "stock_quantity", nullable = false)
    private int stockQuantity;

    @ManyToOne
    @JoinColumn(name = "supplier_id", nullable = false)  // Foreign key reference to supplier
    private Supplier supplier;  // Assuming there's a Supplier entity

    // Constructors, getters, and setters
    public Material() {
    }

    public Material(String name, String description, int stockQuantity, Supplier supplier) {
        this.name = name;
        this.description = description;
        this.stockQuantity = stockQuantity;
        this.supplier = supplier;
    }

    // Getters and setters
    public Long getMaterialId() {
        return materialId;
    }

    public void setMaterialId(Long materialId) {
        this.materialId = materialId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getStockQuantity() {
        return stockQuantity;
    }

    public void setStockQuantity(int stockQuantity) {
        this.stockQuantity = stockQuantity;
    }

    public Supplier getSupplier() {
        return supplier;
    }

    public void setSupplier(Supplier supplier) {
        this.supplier = supplier;
    }
}
