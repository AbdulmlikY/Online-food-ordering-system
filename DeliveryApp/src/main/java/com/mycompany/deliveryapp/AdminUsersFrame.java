package com.mycompany.deliveryapp;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.*;

public class AdminUsersFrame extends JFrame {

    public AdminUsersFrame() {
        setTitle("All Users");
        setSize(600, 400);
        setLocationRelativeTo(null); // يحطه بنص الشاشة
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);//مايتسكر لين اضغط على علامة اكس
        setLayout(new BorderLayout());

        JLabel title = new JLabel("User Accounts", SwingConstants.CENTER);// الكونستنت يخلي الكتابة بالنص بالضبط
        title.setFont(new Font("Arial", Font.BOLD, 24));
        add(title, BorderLayout.NORTH);//يحطه فوق الشمال يعني فوق بديهي ههههه

        String[] columns = {"ID", "Name", "Email", "Role"};
        DefaultTableModel model = new DefaultTableModel(columns, 0);//سوينا جدول لكن مبدئيا ماعندنا صفوف
        JTable table = new JTable(model);// يربط المودل بالجدول
        add(new JScrollPane(table), BorderLayout.CENTER);

        try {
            Connection conn = DatabaseConnection.connect();
            if (conn != null) {
                String sql = "SELECT id, name, email, role FROM users ORDER BY id";
                Statement stmt = conn.createStatement();
                ResultSet rs = stmt.executeQuery(sql);// ينفذ الامر داخل قاعدة البيانات ويحفطهن ب ار اس

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