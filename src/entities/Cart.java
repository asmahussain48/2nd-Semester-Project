package entities;

import utils.DatabaseConnection;

import java.sql.*;

public class Cart {
    private int id;
    private int productId;
    private int quantity;
    private double totalAmount;
    // Getters and setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public int getProductId() { return productId; }
    public void setProductId(int productId) { this.productId = productId; }

    public int getQuantity() { return quantity; }
    public void setQuantity(int quantity) { this.quantity = quantity; }

    public double getTotalAmount() { return totalAmount; }
    public void setTotalAmount(double totalAmount) { this.totalAmount = totalAmount; }

    /**
     * Calculate the total amount of all products in the cart for a given user.
     */
    public static double getTotalAmount(int userId) {
        double totalAmount = 0.0;
        String query = "SELECT p.price, c.quantity FROM cart c JOIN products p ON c.product_id = p.id WHERE c.user_id = ?";

        try (Connection con = DatabaseConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(query)) {
            ps.setInt(1, userId);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    totalAmount += rs.getDouble("price") * rs.getInt("quantity");
                }
            }
        } catch (SQLException e) {
            System.err.println("Error calculating total amount: " + e.getMessage());
        }
        return totalAmount;
    }

    /**
     * Adds a product to the cart. If product exists, increase the quantity.
     */
    public static void addToCart(int userId, int productId, int quantity) {
        String sql = "INSERT INTO cart (user_id, product_id, quantity) VALUES (?, ?, ?) " +
                "ON DUPLICATE KEY UPDATE quantity = quantity + ?";

        try (Connection con = DatabaseConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, userId);
            ps.setInt(2, productId);
            ps.setInt(3, quantity);
            ps.setInt(4, quantity);

            int rows = ps.executeUpdate();
            System.out.println(rows > 0 ? "Added to cart successfully." : "Failed to add to cart.");
        } catch (SQLException e) {
            System.err.println("Error adding to cart: " + e.getMessage());
        }
    }

    /**
     * Displays all products in the user's cart.
     */
    public static void viewCart(int userId) {
        String sql = "SELECT c.id, p.name, p.brand, p.model, p.product_description, p.price, c.quantity " +
                "FROM cart c JOIN products p ON c.product_id = p.id WHERE c.user_id = ?";

        try (Connection con = DatabaseConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, userId);
            try (ResultSet rs = ps.executeQuery()) {

                System.out.printf("%-10s %-20s %-15s %-15s %-30s %-10s %-10s%n",
                        "ID", "Name", "Brand", "Model", "Description", "Price", "Quantity");
                System.out.println("----------------------------------------------------------------------------------------");

                boolean isEmpty = true;
                while (rs.next()) {
                    isEmpty = false;
                    System.out.printf("%-10d %-20s %-15s %-15s %-30s $%-10.2f %-10d%n",
                            rs.getInt("id"),
                            rs.getString("name"),
                            rs.getString("brand"),
                            rs.getString("model"),
                            rs.getString("product_description"),
                            rs.getDouble("price"),
                            rs.getInt("quantity"));
                }
                if (isEmpty) System.out.println("Your cart is empty.");
            }

        } catch (SQLException e) {
            System.err.println("Error fetching cart items: " + e.getMessage());
        }
    }

    /**
     * Remove a specific product from the cart by cart item ID.
     */
    public static void removeFromCart(int userId, int cartItemId) {
        String sql = "DELETE FROM cart WHERE user_id = ? AND id = ?";

        try (Connection con = DatabaseConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, userId);
            ps.setInt(2, cartItemId);

            int rows = ps.executeUpdate();
            System.out.println(rows > 0 ? "Product removed from cart." : "Product not found in cart.");

        } catch (SQLException e) {
            System.err.println("Error removing product from cart: " + e.getMessage());
        }
    }

    /**
     * Clears all products in the user's cart.
     */
    public static void clearCart(int userId) {
        String sql = "DELETE FROM cart WHERE user_id = ?";

        try (Connection con = DatabaseConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, userId);
            int rows = ps.executeUpdate();

            System.out.println(rows > 0 ? "Cart cleared successfully." : "No items found in cart.");

        } catch (SQLException e) {
            System.err.println("Error clearing the cart: " + e.getMessage());
        }
    }

    /**
     * Updates the quantity of a product in the cart.
     */
    public static void updateCart(int userId, int productId, int newQuantity) {
        String sql = "UPDATE cart SET quantity = ? WHERE user_id = ? AND product_id = ?";

        try (Connection con = DatabaseConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, newQuantity);
            ps.setInt(2, userId);
            ps.setInt(3, productId);

            int rows = ps.executeUpdate();
            System.out.println(rows > 0 ? "Cart updated successfully." : "Failed to update cart.");

        } catch (SQLException e) {
            System.err.println("Error updating cart: " + e.getMessage());
        }
    }

    /**
     * Review cart items (similar to viewCart, can be merged or customized).
     */
    public static void reviewCart(int userId) {
        // This method is similar to viewCart; you can reuse viewCart or customize output here.
        viewCart(userId);
    }
}
