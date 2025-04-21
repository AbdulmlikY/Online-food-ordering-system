import javax.swing.*;
import java.awt.*;

public class AdminDashboardFrame extends JFrame {

    public AdminDashboardFrame() {
        setTitle("Admin Dashboard");
        setSize(400, 500); // زودنا الارتفاع عشان نضيف زرين زيادة
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new GridLayout(6, 1, 10, 10)); // 6 أسطر عشان نضيف الزرين

        JLabel title = new JLabel("Admin Panel", SwingConstants.CENTER);
        title.setFont(new Font("Arial", Font.BOLD, 24));
        add(title);

        JButton usersButton = new JButton("View Users");
        JButton ordersButton = new JButton("Manage Orders");
        JButton restaurantsButton = new JButton("Manage Restaurants");
        JButton updateImageButton = new JButton("Update Restaurant Image");
        JButton updateRatingButton = new JButton("Update Restaurant Rating");
        JButton logoutButton = new JButton("Logout");

        add(usersButton);
        add(ordersButton);
        add(restaurantsButton);
        add(updateImageButton);
        add(updateRatingButton);
        add(logoutButton);

        usersButton.addActionListener(e -> {
            new AdminUsersFrame().setVisible(true);
        });

        ordersButton.addActionListener(e -> {
            new AdminOrdersFrame().setVisible(true);
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

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new AdminDashboardFrame().setVisible(true));
    }
}
