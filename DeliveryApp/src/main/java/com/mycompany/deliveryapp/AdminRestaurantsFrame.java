package com.mycompany.deliveryapp;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.*;

public class AdminRestaurantsFrame extends JFrame {

    private JTable table;
    private DefaultTableModel model;
    private JTextField nameField, typeField;

    public AdminRestaurantsFrame() {
        setTitle("Manage Restaurants");
        setSize(700, 400);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout());

        JLabel title = new JLabel("Restaurant Management", SwingConstants.CENTER);
        title.setFont(new Font("Arial", Font.BOLD, 24));
        add(title, BorderLayout.NORTH);

        String[] columns = {"ID", "Name", "Type"};
        model = new DefaultTableModel(columns, 0);
        table = new JTable(model);
        add(new JScrollPane(table), BorderLayout.CENTER);

        JPanel formPanel = new JPanel(new GridLayout(1, 4, 10, 10));
        nameField = new JTextField();
        typeField = new JTextField();
        JButton addButton = new JButton("Add");
        JButton deleteButton = new JButton("Delete Selected");

        formPanel.add(new JLabel("Name:"));
        formPanel.add(nameField);
        formPanel.add(typeField);
        formPanel.add(addButton);
        formPanel.add(deleteButton);

        add(formPanel, BorderLayout.SOUTH);

        loadRestaurants();

        addButton.addActionListener(e -> addRestaurant());
        deleteButton.addActionListener(e -> deleteSelected());
    }

    private void loadRestaurants() {
        model.setRowCount(0);
        try {
            Connection conn = DatabaseConnection.connect();
            String sql = "SELECT id, name, type FROM restaurants ORDER BY id";
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sql);

            while (rs.next()) {
                model.addRow(new Object[]{
                        rs.getInt("id"),
                        rs.getString("name"),
                        rs.getString("type")
                });
            }

            conn.close();
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Error loading restaurants: " + e.getMessage());
        }
    }

    private void addRestaurant() {
        String name = nameField.getText().trim();
        String type = typeField.getText().trim();

        if (name.isEmpty() || type.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please fill in both fields.");
            return;
        }

        try {
            Connection conn = DatabaseConnection.connect();
            String sql = "INSERT INTO restaurants (name, type) VALUES (?, ?)";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, name);
            stmt.setString(2, type);
            stmt.executeUpdate();
            conn.close();

            nameField.setText("");
            typeField.setText("");
            loadRestaurants();
            JOptionPane.showMessageDialog(this, "Restaurant added successfully.");
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Error adding restaurant: " + e.getMessage());
        }
    }

    private void deleteSelected() {
        int selectedRow = table.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Please select a restaurant to delete.");
            return;
        }

        int id = (int) model.getValueAt(selectedRow, 0);

        try {
            Connection conn = DatabaseConnection.connect();
            String sql = "DELETE FROM restaurants WHERE id = ?";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setInt(1, id);
            stmt.executeUpdate();
            conn.close();

            loadRestaurants();
            JOptionPane.showMessageDialog(this, "Restaurant deleted.");
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Error deleting restaurant: " + e.getMessage());
        }
    }
}