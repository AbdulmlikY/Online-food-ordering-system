import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.*;

public class OwnerMenuFrame extends JFrame {

    private JTable table;
    private DefaultTableModel model;
    private JTextField nameField, priceField;

    public OwnerMenuFrame() {
        setTitle("Manage My Menu");
        setSize(600, 400);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        JLabel title = new JLabel("My Menu", SwingConstants.CENTER);
        title.setFont(new Font("Arial", Font.BOLD, 22));
        add(title, BorderLayout.NORTH);

        String[] columns = {"ID", "Meal Name", "Price"};
        model = new DefaultTableModel(columns, 0);
        table = new JTable(model);
        add(new JScrollPane(table), BorderLayout.CENTER);

        JPanel formPanel = new JPanel(new GridLayout(1, 4, 10, 10));
        nameField = new JTextField();
        priceField = new JTextField();
        JButton addBtn = new JButton("Add");
        JButton deleteBtn = new JButton("Delete Selected");

        formPanel.add(new JLabel("Name:"));
        formPanel.add(nameField);
        formPanel.add(priceField);
        formPanel.add(addBtn);
        formPanel.add(deleteBtn);

        add(formPanel, BorderLayout.SOUTH);

        loadMeals();

        addBtn.addActionListener(e -> addMeal());
        deleteBtn.addActionListener(e -> deleteMeal());
    }

    private void loadMeals() {
        model.setRowCount(0);
        try {
            Connection conn = DatabaseConnection.connect();
            String sql = "SELECT id, name, price FROM meals WHERE restaurant_name = ?";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, CurrentUser.getRestaurantName());
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                int id = rs.getInt("id");
                String name = rs.getString("name");
                double price = rs.getDouble("price");
                model.addRow(new Object[]{id, name, price + " SAR"});
            }

            conn.close();
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Error loading meals: " + e.getMessage());
        }
    }

    private void addMeal() {
        String name = nameField.getText().trim();
        String priceText = priceField.getText().trim();

        if (name.isEmpty() || priceText.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please enter meal name and price.");
            return;
        }

        try {
            double price = Double.parseDouble(priceText);
            Connection conn = DatabaseConnection.connect();
            String sql = "INSERT INTO meals (restaurant_name, name, price) VALUES (?, ?, ?)";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, CurrentUser.getRestaurantName());
            stmt.setString(2, name);
            stmt.setDouble(3, price);
            stmt.executeUpdate();
            conn.close();

            nameField.setText("");
            priceField.setText("");
            loadMeals();
            JOptionPane.showMessageDialog(this, "Meal added!");
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Invalid price format.");
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Database error: " + e.getMessage());
        }
    }

    private void deleteMeal() {
        int selectedRow = table.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Select a meal to delete.");
            return;
        }

        int id = (int) model.getValueAt(selectedRow, 0);
        try {
            Connection conn = DatabaseConnection.connect();
            String sql = "DELETE FROM meals WHERE id = ? AND restaurant_name = ?";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setInt(1, id);
            stmt.setString(2, CurrentUser.getRestaurantName());
            stmt.executeUpdate();
            conn.close();

            loadMeals();
            JOptionPane.showMessageDialog(this, "Meal deleted.");
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Database error: " + e.getMessage());
        }
    }
}
