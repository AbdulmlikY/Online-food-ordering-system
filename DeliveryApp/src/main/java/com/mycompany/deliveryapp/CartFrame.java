package com.mycompany.deliveryapp;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class CartFrame extends JFrame {

    private JList<String> cartList;//قائمه 
    private DefaultListModel<String> cartModel;//يحتفظ بالعناصر
    private JButton checkoutButton, backButton;

    public CartFrame() {
        setTitle("Your Cart");
        setSize(400, 350);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        JLabel title = new JLabel("Cart", SwingConstants.CENTER);//Cart
        title.setFont(new Font("Arial", Font.BOLD, 22));
        add(title, BorderLayout.NORTH);

        cartModel = new DefaultListModel<>();
        List<String> items = Cart.getItems();//تجيب الاشياء الموجوده بالسله

        if (items.isEmpty()) {
            cartModel.addElement("Cart is empty.");
        } else {
            for (String item : items) {
                cartModel.addElement(item);
            }
        }
        //عرض العناصر
        cartList = new JList<>(cartModel);// تضيف ليست وتحط العناصر الموجوده في cartmodel
        add(new JScrollPane(cartList), BorderLayout.CENTER);//تضيف scroll علشان تقدر تنزل
        //هاذي الpanel علشان تكون خلف الازرار الي تحت 
        JPanel bottomPanel = new JPanel(new FlowLayout());

        checkoutButton = new JButton("Checkout");
        backButton = new JButton("Back");

        bottomPanel.add(checkoutButton);
        bottomPanel.add(backButton);

        add(bottomPanel, BorderLayout.SOUTH);
        //خاصيه للزر اذا صار فاضي يطلع خطا واذا صار صحيح يكمل ويفتح واجهه الدفع
        checkoutButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (Cart.isEmpty()) {
                    JOptionPane.showMessageDialog(CartFrame.this, "Your cart is already empty.");
                    return;
                }
                
                new PaymentFrame(CartFrame.this, cartModel).setVisible(true);
            }
        });

        backButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                dispose();
                new RestaurantListFrame().setVisible(true);
            }
        });
    }
}