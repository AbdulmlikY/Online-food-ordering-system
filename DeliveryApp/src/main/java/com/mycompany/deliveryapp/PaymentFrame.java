package com.mycompany.deliveryapp;

import javax.swing.*;
import java.awt.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;

public class PaymentFrame extends JFrame {

    private JFrame parentFrame; 

    public PaymentFrame(JFrame parent, DefaultListModel<String> cartModel) {
        this.parentFrame = parent; 
        setTitle("Payment");
        setSize(400, 300);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout());

        JLabel title = new JLabel("Payment Simulation", SwingConstants.CENTER);
        title.setFont(new Font("Arial", Font.BOLD, 20));
        add(title, BorderLayout.NORTH);

        JPanel form = new JPanel(new GridLayout(3, 2, 10, 10));
        form.setBorder(BorderFactory.createEmptyBorder(20, 40, 10, 40));

        String[] methods = {"Credit Card", "Mada", "Cash on Delivery"};
        JComboBox<String> methodBox = new JComboBox<>(methods);

        JTextField cardNumber = new JTextField();
        JTextField cvv = new JTextField();

        form.add(new JLabel("Payment Method:"));
        form.add(methodBox);
        form.add(new JLabel("Card Number:"));
        form.add(cardNumber);
        form.add(new JLabel("CVV:"));
        form.add(cvv);

        add(form, BorderLayout.CENTER);

        JButton payBtn = new JButton("Pay");
        add(payBtn, BorderLayout.SOUTH);

        payBtn.addActionListener(e -> processPayment(methodBox, cardNumber, cvv, cartModel));
    }

    private void processPayment(JComboBox<String> methodBox, JTextField cardNumber, JTextField cvv, DefaultListModel<String> cartModel) {
        String method = (String) methodBox.getSelectedItem();
        String card = cardNumber.getText().trim();
        String cvvCode = cvv.getText().trim();

       
        if (!validatePayment(method, card, cvvCode)) {
            return;
        }

       
        try {
            Connection conn = DatabaseConnection.connect();
            if (conn == null) {
                JOptionPane.showMessageDialog(this, "Database connection failed.");
                return;
            }

            int orderId = createOrder(conn);
            if (orderId == 0) {
                JOptionPane.showMessageDialog(this, "Order creation failed.");
                return;
            }

            saveOrderItems(conn, orderId);
            conn.close();

            Cart.clear();
            cartModel.clear();
            cartModel.addElement("Cart is empty.");

            JOptionPane.showMessageDialog(this, "Order placed successfully!");

        
            parentFrame.dispose(); 
            this.dispose(); 
            new RestaurantListFrame().setVisible(true); 

        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, "Error: " + ex.getMessage());
        }
    }

    private boolean validatePayment(String method, String card, String cvvCode) {
        if (method.equals("Cash on Delivery")) {
            return true; 
        }

        if (card.isEmpty() || cvvCode.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please enter card details.");
            return false;
        }

        if (!card.matches("\\d{12}")) {
            JOptionPane.showMessageDialog(this, "Card number must be exactly 12 digits.");
            return false;
        }

        if (!cvvCode.matches("\\d{3}")) {
            JOptionPane.showMessageDialog(this, "CVV must be exactly 3 digits.");
            return false;
        }

        return true;
    }

    private int createOrder(Connection conn) throws SQLException {
        String orderSql = "INSERT INTO orders (user_id) VALUES (?)";
        PreparedStatement orderStmt = conn.prepareStatement(orderSql, Statement.RETURN_GENERATED_KEYS);
        orderStmt.setInt(1, CurrentUser.getId());
        int affectedRows = orderStmt.executeUpdate();

        if (affectedRows == 0) {
            throw new SQLException("Creating order failed, no rows affected.");
        }

        java.sql.ResultSet rs = orderStmt.getGeneratedKeys();
        if (rs.next()) {
            return rs.getInt(1);
        }
        return 0;
    }

    private void saveOrderItems(Connection conn, int orderId) throws SQLException {
        String itemSql = "INSERT INTO order_items (order_id, item_name) VALUES (?, ?)";
        PreparedStatement itemStmt = conn.prepareStatement(itemSql);

        for (String item : Cart.getItems()) {
            itemStmt.setInt(1, orderId);
            itemStmt.setString(2, item);
            itemStmt.addBatch();
        }

        itemStmt.executeBatch();
    }
}
