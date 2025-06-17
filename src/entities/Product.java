package entities;

import utils.DatabaseConnection;

import java.sql.*;

public class Product extends category {
    private int id;
    private String name;
    private String brand;
    private String model;
    private String productDescription;
    private double price;
    private int quantity;
    private boolean isAvailable;

    public Product() {
        super();
    }

    public Product(int id, String name, String brand, String model, String productDescription, double price, int quantity, boolean isAvailable) {
        super();
        this.id = id;
        this.name = name;
        this.brand = brand;
        this.model = model;
        this.productDescription = productDescription;
        this.price = price;
        this.quantity = quantity;
        this.isAvailable = isAvailable;
    }

    public Product(String name, String brand, String model, String productDescription, double price, int quantity, boolean isAvailable) {
        super();
        this.name = name;
        this.brand = brand;
        this.model = model;
        this.productDescription = productDescription;
        this.price = price;
        this.quantity = quantity;
        this.isAvailable = isAvailable;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String getProductDescription() {
        return productDescription;
    }

    public void setProductDescription(String productDescription) {
        this.productDescription = productDescription;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        if (price < 0) {
            throw new IllegalArgumentException("Price must be a positive value.");
        }
        this.price = price;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public boolean isAvailable() {
        return isAvailable;
    }

    public void setAvailable(boolean available) {
        isAvailable = available;
    }
    public void display() {
        System.out.printf("%-10s %-20s %-15s %-15s %-30s %-10s %-10s %-12s\n",
                "ID", "Name", "Brand", "Model", "Description", "Price", "Quantity", "Available");
        System.out.println("----------------------------------------------------------------------------------------");
        System.out.printf("%-10d %-20s %-15s %-15s %-30s $%-10.2f %-10d %-12s\n",
                id, name, brand, model, productDescription, price, quantity, (isAvailable ? "Yes" : "No"));
        System.out.println("----------------------------------------------------------------------------------------");
    }

}
