import javax.swing.*;
import java.awt.*;

public class OwnerDashboardFrame extends JFrame {

    public OwnerDashboardFrame() {
        setTitle("Restaurant Owner Dashboard");
        setSize(400, 250);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        JLabel title = new JLabel("Owner Panel", SwingConstants.CENTER);
        title.setFont(new Font("Arial", Font.BOLD, 24));
        add(title, BorderLayout.NORTH);

        JButton manageMenuButton = new JButton("Manage My Menu");
        manageMenuButton.setPreferredSize(new Dimension(200, 60));
        JPanel centerPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        centerPanel.add(manageMenuButton);
        add(centerPanel, BorderLayout.CENTER);

        JButton logoutButton = new JButton("Logout");
        JPanel bottomPanel = new JPanel();
        bottomPanel.add(logoutButton);
        add(bottomPanel, BorderLayout.SOUTH);

        manageMenuButton.addActionListener(e -> {
            new OwnerMenuFrame().setVisible(true);
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
