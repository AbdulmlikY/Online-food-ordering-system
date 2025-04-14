import javax.swing.*;
import java.awt.*;

public class AdminDashboardFrame extends JFrame {

    public AdminDashboardFrame() {
        setTitle("Admin Dashboard");
        setSize(400, 300);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new GridLayout(4, 1, 10, 10));

        JLabel title = new JLabel("Admin Panel", SwingConstants.CENTER);
        title.setFont(new Font("Arial", Font.BOLD, 24));
        add(title);

        JButton usersButton = new JButton("View Users");
        JButton ordersButton = new JButton("Manage Orders");
        JButton restaurantsButton = new JButton("Manage Restaurants");
        JButton logoutButton = new JButton("Logout");

        add(usersButton);
        add(ordersButton);
        add(restaurantsButton);
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

        logoutButton.addActionListener(e -> {
            this.dispose();
            new LoginFrame().setVisible(true);
        });
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new AdminDashboardFrame().setVisible(true));
    }
}