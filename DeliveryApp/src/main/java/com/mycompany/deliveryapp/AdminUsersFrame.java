package com.mycompany.deliveryapp;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.*;

public class AdminUsersFrame extends JFrame {

    public AdminUsersFrame() {
        setTitle("All Users");
        setSize(600, 400);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout());

        JLabel title = new JLabel("User Accounts", SwingConstants.CENTER);
        title.setFont(new Font("Arial", Font.BOLD, 24));
        add(title, BorderLayout.NORTH);

        String[] columns = {"ID", "Name", "Email", "Role"};
        DefaultTableModel model = new DefaultTableModel(columns, 0);
        JTable table = new JTable(model);
        add(new JScrollPane(table), BorderLayout.CENTER);

        try {
            Connection conn = DatabaseConnection.connect();
            if (conn != null) {
                String sql = "SELECT id, name, email, role FROM users ORDER BY id";
                Statement stmt = conn.createStatement();
                ResultSet rs = stmt.executeQuery(sql);

                while (rs.next()) {
                    int id = rs.getInt("id");
                    String name = rs.getString("name");
                    String email = rs.getString("email");
                    String role = rs.getString("role");
                    model.addRow(new Object[]{id, name, email, role});
                }

                conn.close();
            } else {
                JOptionPane.showMessageDialog(this, "Database connection failed.");
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Error: " + e.getMessage());
        }

        JButton closeButton = new JButton("Close");
        closeButton.addActionListener(e -> this.dispose());
        JPanel panel = new JPanel();
        panel.add(closeButton);
        add(panel, BorderLayout.SOUTH);
    }
}