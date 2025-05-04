package com.mycompany.deliveryapp;

import javax.swing.*;
import java.awt.*;
import java.sql.*;

public class AdminUpdateRatingFrame extends JFrame {

    private JTextField restaurantNameField;
    private JTextField ratingField;

    public AdminUpdateRatingFrame() {
        setTitle("Update Restaurant Rating");
        setSize(400, 250);
        setLocationRelativeTo(null);
        setLayout(new GridLayout(5, 1, 10, 10));
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);

        restaurantNameField = new JTextField();
        ratingField = new JTextField();
        JButton updateButton = new JButton("Update Rating");

        add(new JLabel("Restaurant Name:", SwingConstants.CENTER));
        add(restaurantNameField);
        add(new JLabel("New Rating (e.g., 4.5):", SwingConstants.CENTER));
        add(ratingField);
        add(updateButton);

        updateButton.addActionListener(e -> {
            String restaurant = restaurantNameField.getText().trim();
            String ratingStr = ratingField.getText().trim();

            if (restaurant.isEmpty() || ratingStr.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Please fill in all fields.");
                return;
            }

            try {
                double rating = Double.parseDouble(ratingStr);
                if (rating < 0 || rating > 5) {
                    JOptionPane.showMessageDialog(this, "Rating must be between 0 and 5.");
                    return;
                }

                Connection conn = DatabaseConnection.connect();
                PreparedStatement stmt = conn.prepareStatement("UPDATE restaurants SET rating = ? WHERE name = ?");
                stmt.setDouble(1, rating);
                stmt.setString(2, restaurant);
                int updated = stmt.executeUpdate();
                conn.close();

                if (updated > 0) {
                    JOptionPane.showMessageDialog(this, "Rating updated.");
                    this.dispose();
                    new RestaurantListFrame().setVisible(true); 
                } else {
                    JOptionPane.showMessageDialog(this, "Restaurant not found.");
                }
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Please enter a valid number for rating.");
            } catch (SQLException ex) {
                JOptionPane.showMessageDialog(this, "Database error: " + ex.getMessage());
            }
        });

        setVisible(true);
    }
}
