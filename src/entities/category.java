package entities;

import utils.DatabaseConnection;

import java.sql.*;

public class category {
    private int category_id;
    private String name;
    private String type; // should be either "new" or "old"
    private String description;

    public category() {
    }

    public int getId() {
        return category_id;
    }

    public void setId(int id) {
        this.category_id=id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name=name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description=description;
    }
    // === Display Method ===
    public void display() {
        System.out.println("Category ID: " + category_id);
        System.out.println("Name: " + name);
        System.out.println("Type: " + type);
        System.out.println("Description: " + description);
        System.out.println("-----------------------------");
    }

}