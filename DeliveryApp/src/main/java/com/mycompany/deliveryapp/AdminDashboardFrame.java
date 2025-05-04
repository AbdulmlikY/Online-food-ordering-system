package com.mycompany.deliveryapp;

import javax.swing.*;
import java.awt.*;

public class AdminDashboardFrame extends JFrame {

    public AdminDashboardFrame() {
        setTitle("Admin Dashboard");
        setSize(400, 500); 
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new GridLayout(5, 1, 10, 10)); 

        JLabel title = new JLabel("Admin Panel", SwingConstants.CENTER);
        title.setFont(new Font("Arial", Font.BOLD, 24));
        add(title);

        JButton usersButton = new JButton("View Users");
        JButton restaurantsButton = new JButton("Manage Restaurants");
        JButton updateImageButton = new JButton("Update Restaurant Image");
        JButton updateRatingButton = new JButton("Update Restaurant Rating");
        JButton logoutButton = new JButton("Logout");

        add(usersButton);
        add(restaurantsButton);
        add(updateImageButton);
        add(updateRatingButton);
        add(logoutButton);

        usersButton.addActionListener(e -> {
            new AdminUsersFrame().setVisible(true);
        });

        restaurantsButton.addActionListener(e -> {
            new AdminRestaurantsFrame().setVisible(true);
        });

        updateImageButton.addActionListener(e -> {
            new AdminUpdateImageFrame().setVisible(true);
        });

        updateRatingButton.addActionListener(e -> {
            new AdminUpdateRatingFrame().setVisible(true);
        });

        logoutButton.addActionListener(e -> {
            this.dispose();
            new LoginFrame().setVisible(true);
        });
    }

}
