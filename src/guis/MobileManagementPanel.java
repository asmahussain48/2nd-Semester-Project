package guis;

import javax.swing.*;
import java.awt.*;
import java.util.*;
import java.sql.*;
import java.util.List;

import entities.Product;
import entities.Session;

import javax.swing.border.Border;
import javax.swing.border.MatteBorder;
import javax.swing.SwingConstants;

import static entities.Session.getCurrentUserId;

public class MobileManagementPanel extends JPanel {

    private JTextArea outputArea;
    private List<Product> products;
    private JPanel gridPanel;
    private Map<Integer, Integer> cart;
    private JButton backToMainMenuButton;
    //southPanel.setBackground(new Color(0xF5F1EC));
    private final Color lightBlue = new Color(255,255,255);
    private final Color darkBlue = new Color(0, 0, 139);
    private final Color black = Color.BLACK;
    private final Color lightGray = Color.LIGHT_GRAY;
    private final Color white = Color.WHITE;
    // registerButton.setBackground(new Color(220, 20, 60));
    private final Color darkRed = new Color(220, 20, 60);
    ImageIcon buttonIcon=new ImageIcon("C:\\Users\\Spider Computer\\IdeaProjects\\HackSquadEcommerce2025\\src\\cart2.png");

    public MobileManagementPanel() {
        setBackground(lightBlue);
        setLayout(new BorderLayout());

        outputArea = new JTextArea(10, 80);
        outputArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(outputArea);
        add(scrollPane, BorderLayout.SOUTH);

        products = new ArrayList<>();
        cart = new HashMap<>();
        fetchMobileDetailsFromDB();
        displayMobileDetails();
    }

    private void fetchMobileDetailsFromDB() {
        try {
            try (Connection con = utils.DatabaseConnection.getConnection()) {
                String query = "SELECT * FROM products ";
                try (PreparedStatement preparedStatement = con.prepareStatement(query)) {
                    try (ResultSet rs = preparedStatement.executeQuery()) {
                        while (rs.next()) {
                            Product product = new Product();
                            product.setId(rs.getInt("id"));
                            product.setName(rs.getString("name"));
                            product.setBrand(rs.getString("brand"));
                            product.setModel(rs.getString("model"));
                            product.setDescription(rs.getString("product_description"));
                            product.setPrice(rs.getDouble("price"));
                            product.setQuantity(rs.getInt("quantity"));
                            product.setAvailable(rs.getBoolean("is_available"));
                            products.add(product);
                        }
                    }
                }
            }
        } catch (SQLException e) {
            outputArea.append("Error connecting to the database or executing query.\n");
            e.printStackTrace();
        }
    }

    private void displayMobileDetails() {
        if (gridPanel != null) {
            remove(gridPanel);
        }

        gridPanel = new JPanel(new GridBagLayout());
        gridPanel.setBackground(lightBlue);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 5, 10, 5);
        gbc.anchor = GridBagConstraints.WEST;
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 9;
        gbc.weightx = 1.0;
        gbc.fill = GridBagConstraints.HORIZONTAL;

        JLabel titleLabel = new JLabel("Mobilink", SwingConstants.CENTER);
        titleLabel.setFont(new Font("SansSerif", Font.BOLD, 36));
        // Add logo icon
        JLabel logoLabel;
        try {
            ImageIcon logoIcon = new ImageIcon("src/login.png"); // Replace with your logo path or resource
            logoLabel = new JLabel(logoIcon);
        } catch (Exception e) {
            logoLabel = new JLabel(); // fallback if image not found
        }
        gridPanel.add(logoLabel);
        titleLabel.setForeground(black);
        gridPanel.add(titleLabel, gbc);

        gbc.gridy = 1;
        gbc.fill = GridBagConstraints.NONE;
        gbc.anchor = GridBagConstraints.EAST;
        gbc.weightx = 0.0;

        backToMainMenuButton = new JButton("View Cart");
        backToMainMenuButton.setIcon(buttonIcon);
        backToMainMenuButton.setFont(new Font("Dialog", Font.BOLD, 12));
        backToMainMenuButton.setBackground(darkRed);
        backToMainMenuButton.setForeground(white);
        Border rowBorderForButton = new MatteBorder(0, 0, 1, 0, lightGray);
        backToMainMenuButton.setBorder(rowBorderForButton);
        backToMainMenuButton.setPreferredSize(new Dimension(120, 40));
        gridPanel.add(backToMainMenuButton, gbc);
        backToMainMenuButton.addActionListener(e -> {
            // OpenCartFrame window`
            CartFrame.showCart();
        });

        gbc.gridy = 2;
        gbc.gridx = 0;
        gbc.gridwidth = 1;
        gbc.anchor = GridBagConstraints.WEST;
        gbc.fill = GridBagConstraints.HORIZONTAL;

        String[] headers = {"ID", "Name", "Brand", "Model", "Description", "Price", "Quantity", "Available", "Add to Cart"};
        Border headerBorder = new MatteBorder(0, 0, 1, 0, darkBlue);

        for (int i = 0; i < headers.length; i++) {
            gbc.gridx = i;
            JLabel headerLabel = new JLabel(headers[i]);
            headerLabel.setForeground(darkBlue);
            headerLabel.setBorder(headerBorder);
            gridPanel.add(headerLabel, gbc);
        }

        for (int i = 0; i < products.size(); i++) {
            Product product = products.get(i);
            gbc.gridy = i + 3;
            gbc.fill = GridBagConstraints.HORIZONTAL;
            gbc.weightx = 1.0;
            Border rowBorder = new MatteBorder(0, 0, 1, 0, lightGray);

            JLabel idLabel = new JLabel(String.valueOf(product.getId()));
            idLabel.setForeground(black);
            idLabel.setBorder(rowBorder);
            gbc.gridx = 0;
            gridPanel.add(idLabel, gbc);

            JLabel nameLabel = new JLabel(product.getName());
            nameLabel.setForeground(black);
            nameLabel.setBorder(rowBorder);
            gbc.gridx = 1;
            gridPanel.add(nameLabel, gbc);

            JLabel brandLabel = new JLabel(product.getBrand());
            brandLabel.setForeground(black);
            brandLabel.setBorder(rowBorder);
            gbc.gridx = 2;
            gridPanel.add(brandLabel, gbc);

            JLabel modelLabel = new JLabel(product.getModel());
            modelLabel.setForeground(black);
            modelLabel.setBorder(rowBorder);
            gbc.gridx = 3;
            gridPanel.add(modelLabel, gbc);

            JLabel descriptionLabel = new JLabel(product.getDescription());
            descriptionLabel.setForeground(black);
            descriptionLabel.setBorder(rowBorder);
            gbc.gridx = 4;
            gridPanel.add(descriptionLabel, gbc);

            JLabel priceLabel = new JLabel(String.format("%.2f", product.getPrice()));
            priceLabel.setForeground(black);
            priceLabel.setBorder(rowBorder);
            gbc.gridx = 5;
            gridPanel.add(priceLabel, gbc);

            JLabel quantityLabel = new JLabel(String.valueOf(product.getQuantity()));
            quantityLabel.setForeground(black);
            quantityLabel.setBorder(rowBorder);
            gbc.gridx = 6;
            gridPanel.add(quantityLabel, gbc);

            JLabel availableLabel = new JLabel(product.isAvailable() ? "Yes" : "No");
            availableLabel.setForeground(black);
            availableLabel.setBorder(rowBorder);
            gbc.gridx = 7;
            gridPanel.add(availableLabel, gbc);
            JButton addToCartButton = new JButton("Add to Cart");
            addToCartButton.setForeground(white);
            addToCartButton.setIcon(buttonIcon);
            addToCartButton.setBackground(darkRed);
            addToCartButton.addActionListener(e -> {
                int quantity = 1;
                if (product.getQuantity() >= quantity) {
                    int currentQuantity = cart.getOrDefault(product.getId(), 0);
                    cart.put(product.getId(), currentQuantity + quantity);
                    try {
                        try (Connection con = utils.DatabaseConnection.getConnection()) {
                            // Update product stock
                            String updateQuery = "UPDATE products SET quantity = quantity - ? WHERE id = ?";
                            try (PreparedStatement updateStatement = con.prepareStatement(updateQuery)) {
                                updateStatement.setInt(1, quantity);
                                updateStatement.setInt(2, product.getId());
                                int rowsUpdated = updateStatement.executeUpdate();
                                if (rowsUpdated > 0) {
                                    outputArea.append(quantity + " " + product.getName() + "(s) added to cart.\n");

                                    // Add to cart table for current user
                                    int userId = Session.getCurrentUserId(); // You need to implement this to get logged-in user's ID
                                    entities.Cart.addToCart(userId, product.getId(), quantity);

                                    products.clear();
                                    fetchMobileDetailsFromDB();
                                    displayMobileDetails();
                                } else {
                                    outputArea.append("Failed to update quantity in database.\n");
                                }
                            }
                        }
                    } catch (SQLException ex) {
                        outputArea.append("Error updating database: " + ex.getMessage() + "\n");
                        ex.printStackTrace();
                    }
                } else {
                    outputArea.append("Insufficient stock for " + product.getName() + ".\n");
                }
            });


            addToCartButton.setBorder(rowBorder);
            gbc.gridx = 8;
            gbc.fill = GridBagConstraints.NONE;
            gbc.anchor = GridBagConstraints.CENTER;
            gridPanel.add(addToCartButton, gbc);
        }

        JScrollPane scrollPane = new JScrollPane(gridPanel);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        add(scrollPane, BorderLayout.CENTER);
        revalidate();
        repaint();
    }

    private String getFormattedValue(Product product, int columnIndex) {
        switch (columnIndex) {
            case 0:
                return String.valueOf(product.getId());
            case 1:
                return product.getName();
            case 2:
                return product.getBrand();
            case 3:
                return product.getModel();
            case 4:
                return product.getDescription();
            case 5:
                return String.format("%.2f", product.getPrice());
            case 6:
                return String.valueOf(product.getQuantity());
            case 7:
                return product.isAvailable() ? "Yes" : "No";
            default:
                return "";
        }
    }
    public JButton getBackToMainMenuButton() {
        return backToMainMenuButton;
    }
}