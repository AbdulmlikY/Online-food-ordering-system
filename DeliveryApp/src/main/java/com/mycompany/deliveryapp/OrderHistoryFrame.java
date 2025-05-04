package com.mycompany.deliveryapp;

import javax.swing.*;
import java.awt.*;
import java.sql.*;
import java.util.ArrayList;
import javax.swing.table.DefaultTableModel;

public class OrderHistoryFrame extends JFrame {

    public OrderHistoryFrame() {
        setTitle("My Orders");
        setSize(600, 400);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        JLabel title = new JLabel("My Orders", SwingConstants.CENTER);
        title.setFont(new Font("Arial", Font.BOLD, 24));
        add(title, BorderLayout.NORTH);

        String[] columns = {"Order ID", "Date", "Status"};
        DefaultTableModel tableModel = new DefaultTableModel(columns, 0);
        JTable table = new JTable(tableModel);
        add(new JScrollPane(table), BorderLayout.CENTER);

        try {
            Connection conn = DatabaseConnection.connect();
            if (conn == null) {
                JOptionPane.showMessageDialog(this, "Database connection failed.");
                return;
            }

            String sql = "SELECT id, order_date, status FROM orders WHERE user_id = ? ORDER BY order_date DESC";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setInt(1, CurrentUser.getId());
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                int orderId = rs.getInt("id");
                String date = rs.getString("order_date");
                String status = rs.getString("status");
                tableModel.addRow(new Object[]{orderId, date, status});
            }

            conn.close();
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, "Error: " + ex.getMessage());
        }

        JButton backBtn = new JButton("Back");
        backBtn.addActionListener(e -> {
            this.dispose();
            new RestaurantListFrame().setVisible(true);
        });

        JPanel bottomPanel = new JPanel();
        bottomPanel.add(backBtn);
        add(bottomPanel, BorderLayout.SOUTH);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new OrderHistoryFrame().setVisible(true));
    }
}