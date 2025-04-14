import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;

public class LoginFrame extends JFrame {

    private JTextField emailField;
    private JPasswordField passwordField;

    public LoginFrame() {
        setTitle("Login - Delivery App");
        setSize(400, 300);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        JLabel title = new JLabel("Login", SwingConstants.CENTER);
        title.setFont(new Font("Arial", Font.BOLD, 24));
        add(title, BorderLayout.NORTH);

        JPanel form = new JPanel(new GridLayout(3, 2, 10, 10));
        form.setBorder(BorderFactory.createEmptyBorder(20, 40, 20, 40));

        form.add(new JLabel("Email:"));
        emailField = new JTextField();
        form.add(emailField);

        form.add(new JLabel("Password:"));
        passwordField = new JPasswordField();
        form.add(passwordField);

        JButton loginButton = new JButton("Login");
        JButton registerButton = new JButton("Register");

        form.add(loginButton);
        form.add(registerButton);

        add(form, BorderLayout.CENTER);

        loginButton.addActionListener(e -> {
            String email = emailField.getText();
            String password = new String(passwordField.getPassword());

            if (email.isEmpty() || password.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Please fill in all fields.");
                return;
            }

            try {
                Connection conn = DatabaseConnection.connect();
                if (conn == null) {
                    JOptionPane.showMessageDialog(this, "Database connection failed.");
                    return;
                }

                String sql = "SELECT * FROM users WHERE email = ? AND password = ?";
                PreparedStatement stmt = conn.prepareStatement(sql);
                stmt.setString(1, email);
                stmt.setString(2, password);

                ResultSet rs = stmt.executeQuery();

                if (rs.next()) {
                    int userId = rs.getInt("id");
                    String name = rs.getString("name");
                    String emailFromDb = rs.getString("email");
                    String role = rs.getString("role");

                    CurrentUser.setUser(userId, name, emailFromDb);

                    JOptionPane.showMessageDialog(this, "Welcome, " + name + "!");
                    this.dispose();

                    switch (role) {
                        case "admin":
                            new AdminDashboardFrame().setVisible(true);
                            break;
                        case "owner":
                            new OwnerDashboardFrame().setVisible(true); // تحتاج تنشئها
                            break;
                        default:
                            new RestaurantListFrame().setVisible(true);
                            break;
                    }
                } else {
                    JOptionPane.showMessageDialog(this, "Invalid email or password.");
                }

                conn.close();

            } catch (SQLException ex) {
                JOptionPane.showMessageDialog(this, "Error: " + ex.getMessage());
            }
        });

        registerButton.addActionListener(e -> {
            new RegisterFrame().setVisible(true);
            this.dispose();
        });
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new LoginFrame().setVisible(true));
    }
}