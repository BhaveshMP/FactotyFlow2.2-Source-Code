package com.FM.Entities;

import jakarta.persistence.*;

@Entity
@Table(name = "bill_of_materials")
public class BillOfMaterial {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "bom_id")  // Primary key in snake_case
    private Long bomId;

    @ManyToOne
    @JoinColumn(name = "product_id", nullable = false)  // Reference to the product
    private Product product;

    @ManyToOne
    @JoinColumn(name = "material_id", nullable = false)  // Reference to the material
    private Material material;

    @Column(name = "quantity", nullable = false)  // Quantity of material needed
    private int quantity;

    // Constructors, getters, and setters
    public BillOfMaterial() {
    }

    public BillOfMaterial(Product product, Material material, int quantity) {
        this.product = product;
        this.material = material;
        this.quantity = quantity;
    }

    // Getters and setters
    public Long getBomId() {
        return bomId;
    }

    public void setBomId(Long bomId) {
        this.bomId = bomId;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public Material getMaterial() {
        return material;
    }

    public void setMaterial(Material material) {
        this.material = material;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
}
