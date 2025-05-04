package com.mycompany.deliveryapp;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.sql.*;

public class AdminUpdateImageFrame extends JFrame {

    private JTextField restaurantNameField;
    private JLabel imagePathLabel;

    public AdminUpdateImageFrame() {
        setTitle("Update Restaurant Image");
        setSize(400, 250);
        setLocationRelativeTo(null);
        setLayout(new GridLayout(5, 1, 10, 10));
        setDefaultCloseOperation(DISPOSE_ON_CLOSE); // إغلاق النافذة فقط

        restaurantNameField = new JTextField();
        imagePathLabel = new JLabel("No file selected", SwingConstants.CENTER);
        JButton browseButton = new JButton("Browse Image");
        JButton saveButton = new JButton("Save");

        add(new JLabel("Restaurant Name:", SwingConstants.CENTER));
        add(restaurantNameField);
        add(imagePathLabel);
        add(browseButton);
        add(saveButton);

        // فتح مستعرض الصور
        browseButton.addActionListener(e -> {
            JFileChooser fileChooser = new JFileChooser();
            if (fileChooser.showOpenDialog(this) == JFileChooser.APPROVE_OPTION) {
                File file = fileChooser.getSelectedFile();
                imagePathLabel.setText(file.getName()); // فقط اسم الملف
            }
        });

        // حفظ التحديث
        saveButton.addActionListener(e -> {
            String restaurant = restaurantNameField.getText().trim();
            String imagePath = imagePathLabel.getText().trim();

            if (restaurant.isEmpty() || imagePath.equals("No file selected")) {
                JOptionPane.showMessageDialog(this, "Please provide both restaurant name and image.");
                return;
            }

            try {
                Connection conn = DatabaseConnection.connect();
                PreparedStatement stmt = conn.prepareStatement("UPDATE restaurants SET image_path = ? WHERE name = ?");
                stmt.setString(1, imagePath);
                stmt.setString(2, restaurant);
                int updated = stmt.executeUpdate();
                conn.close();

                if (updated > 0) {
                    JOptionPane.showMessageDialog(this, "Image updated successfully.");
                    this.dispose();
                    new RestaurantListFrame().setVisible(true); // تحديث الواجهة
                } else {
                    JOptionPane.showMessageDialog(this, "Restaurant not found.");
                }
            } catch (SQLException ex) {
                JOptionPane.showMessageDialog(this, "Error: " + ex.getMessage());
            }
        });

        setVisible(true); // إظهار النافذة
    }
}
