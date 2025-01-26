package com.FM.Entities;

import jakarta.persistence.*;
import com.FM.Entities.Inventory;
@Entity
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")  // Column name according to the variable name
    private int id;

    @Column(name = "name", length = 100, nullable = false)  // Column name according to variable 'name'
    private String name;

    @Column(name = "description", length = 255)  // Column name according to variable 'description'
    private String description;

    @Column(name = "price", nullable = false)  // Column name according to variable 'price'
    private Double price;

    @Column(name = "qty", nullable = false)  // Column name according to variable 'qty'
    private int qty;

    
    @OneToOne(targetEntity=Inventory.class, cascade = CascadeType.ALL, orphanRemoval = true) 
    private Inventory inventory;
    
    @Lob
    @Column(name = "image", columnDefinition = "LONGBLOB")
    private byte[] image; // To store image as byte array (BLOB)

    @Column(name = "file_name", length = 255)  // Column name according to variable 'description'
    private String fileName;
    
    // Getters and setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public Inventory getInventory() {
		return inventory;
	}

	public void setInventory(Inventory inventory) {
		this.inventory = inventory;
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

    public int getQty() {
        return qty;
    }

    public void setQty(int qty) {
        this.qty = qty;
    }

	public Double getPrice() {
		return price;
	}

	public void setPrice(Double price) {
		this.price = price;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	
	public byte[] getImage() {
		return image;
	}

	public void setImage(byte[] image) {
		this.image = image;
	}

	@Override
	public String toString() {
		return "Product [id=" + id + ", name=" + name + ", description=" + description + ", price=" + price + ", qty="
				+ qty +  ", fileName=" + fileName + "]";
	}
    
    
}
