package com.mycompany.deliveryapp;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.*;

public class AdminOrdersFrame extends JFrame {

    private JTable table;
    private DefaultTableModel model;

    public AdminOrdersFrame() {
        setTitle("Manage Orders");
        setSize(700, 400);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout());

        JLabel title = new JLabel("Order Management", SwingConstants.CENTER);
        title.setFont(new Font("Arial", Font.BOLD, 24));
        add(title, BorderLayout.NORTH);

        String[] columns = {"Order ID", "User ID", "Date", "Status"};
        model = new DefaultTableModel(columns, 0);
        table = new JTable(model);
        add(new JScrollPane(table), BorderLayout.CENTER);

        loadOrders();

        JPanel bottomPanel = new JPanel();
        JButton updateBtn = new JButton("Update Status");
        JButton refreshBtn = new JButton("Refresh");
        JButton closeBtn = new JButton("Close");

        bottomPanel.add(updateBtn);
        bottomPanel.add(refreshBtn);
        bottomPanel.add(closeBtn);
        add(bottomPanel, BorderLayout.SOUTH);

        updateBtn.addActionListener(e -> updateOrderStatus());
        refreshBtn.addActionListener(e -> loadOrders());
        closeBtn.addActionListener(e -> this.dispose());
    }

    private void loadOrders() {
        model.setRowCount(0);
        try {
            Connection conn = DatabaseConnection.connect();
            if (conn != null) {
                String sql = "SELECT id, user_id, order_date, status FROM orders ORDER BY order_date DESC";
                Statement stmt = conn.createStatement();
                ResultSet rs = stmt.executeQuery(sql);

                while (rs.next()) {
                    int id = rs.getInt("id");
                    int userId = rs.getInt("user_id");
                    String date = rs.getString("order_date");
                    String status = rs.getString("status");
                    model.addRow(new Object[]{id, userId, date, status});
                }

                conn.close();
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Error: " + e.getMessage());
        }
    }

    private void updateOrderStatus() {
        int selectedRow = table.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Please select an order first.");
            return;
        }

        int orderId = (int) model.getValueAt(selectedRow, 0);
        String[] statuses = {"Pending", "Preparing", "On the Way", "Delivered"};
        String newStatus = (String) JOptionPane.showInputDialog(
                this,
                "Select new status:",
                "Update Order Status",
                JOptionPane.QUESTION_MESSAGE,
                null,
                statuses,
                model.getValueAt(selectedRow, 3)
        );

        if (newStatus != null) {
            try {
                Connection conn = DatabaseConnection.connect();
                String sql = "UPDATE orders SET status = ? WHERE id = ?";
                PreparedStatement stmt = conn.prepareStatement(sql);
                stmt.setString(1, newStatus);
                stmt.setInt(2, orderId);
                stmt.executeUpdate();
                conn.close();

                model.setValueAt(newStatus, selectedRow, 3);
                JOptionPane.showMessageDialog(this, "Status updated successfully.");
            } catch (SQLException e) {
                JOptionPane.showMessageDialog(this, "Error: " + e.getMessage());
            }
        }
    }
}