import javax.swing.*;
import java.awt.*;

public class OwnerDashboardFrame extends JFrame {

    public OwnerDashboardFrame() {
        setTitle("Restaurant Owner Dashboard");
        setSize(400, 300);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new GridLayout(3, 1, 10, 10));

        JLabel title = new JLabel("Owner Panel", SwingConstants.CENTER);
        title.setFont(new Font("Arial", Font.BOLD, 24));
        add(title);

        JButton manageMenuButton = new JButton("Manage My Menu");
        JButton viewOrdersButton = new JButton("View Orders");
        JButton logoutButton = new JButton("Logout");

        add(manageMenuButton);
        add(viewOrdersButton);
        add(logoutButton);

        manageMenuButton.addActionListener(e -> {
            JOptionPane.showMessageDialog(this, "Coming soon: Menu Management");
        });

        viewOrdersButton.addActionListener(e -> {
            JOptionPane.showMessageDialog(this, "Coming soon: Order Viewer for Your Restaurant");
        });

        logoutButton.addActionListener(e -> {
            this.dispose();
            new LoginFrame().setVisible(true);
        });
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new OwnerDashboardFrame().setVisible(true));
    }
}