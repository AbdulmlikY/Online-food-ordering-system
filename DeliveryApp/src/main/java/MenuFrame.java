import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;
import java.util.ArrayList;

public class MenuFrame extends JFrame {

    private DefaultListModel<String> mealModel;
    private JList<String> mealList;
    private JTextField searchField;
    private ArrayList<String> allMeals = new ArrayList<>();
    private String restaurantName;

    public MenuFrame(String restaurantName) {
        this.restaurantName = restaurantName;

        setTitle("Menu - " + restaurantName);
        setSize(500, 450);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        JLabel title = new JLabel("Menu for " + restaurantName, SwingConstants.CENTER);
        title.setFont(new Font("Arial", Font.BOLD, 20));
        add(title, BorderLayout.NORTH);

        JPanel topPanel = new JPanel(new BorderLayout());
        searchField = new JTextField();
        topPanel.add(new JLabel("Search: "), BorderLayout.WEST);
        topPanel.add(searchField, BorderLayout.CENTER);
        add(topPanel, BorderLayout.BEFORE_FIRST_LINE);

        mealModel = new DefaultListModel<>();
        loadMealsFromDatabase();
        mealList = new JList<>(mealModel);
        mealList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        add(new JScrollPane(mealList), BorderLayout.CENTER);

        JPanel buttonPanel = new JPanel(new FlowLayout());
        JButton addToCartButton = new JButton("Add to Cart");
        JButton goToCartButton = new JButton("Go to Cart");
        JButton backButton = new JButton("Back");
        buttonPanel.add(addToCartButton);
        buttonPanel.add(goToCartButton);
        buttonPanel.add(backButton);
        add(buttonPanel, BorderLayout.SOUTH);

        searchField.addKeyListener(new KeyAdapter() {
            public void keyReleased(KeyEvent e) {
                String search = searchField.getText().toLowerCase();
                mealModel.clear();
                for (String meal : allMeals) {
                    if (meal.toLowerCase().contains(search)) {
                        mealModel.addElement(meal);
                    }
                }
            }
        });

        addToCartButton.addActionListener(e -> {
            String selectedMeal = mealList.getSelectedValue();
            if (selectedMeal != null) {
                Cart.addItem(restaurantName + ": " + selectedMeal);
                JOptionPane.showMessageDialog(this, selectedMeal + " added to cart!");
            } else {
                JOptionPane.showMessageDialog(this, "Please select a meal first.");
            }
        });

        goToCartButton.addActionListener(e -> {
            this.dispose();
            new CartFrame().setVisible(true);
        });

        backButton.addActionListener(e -> {
            this.dispose();
            new RestaurantListFrame().setVisible(true);
        });
    }

    private void loadMealsFromDatabase() {
        allMeals.clear();
        try {
            Connection conn = DatabaseConnection.connect();
            String sql = "SELECT name, price FROM meals WHERE restaurant_name = ?";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, restaurantName);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                String meal = rs.getString("name") + " - " + rs.getDouble("price") + " SAR";
                allMeals.add(meal);
            }

            conn.close();
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Error loading meals: " + e.getMessage());
        }

        mealModel.clear();
        for (String meal : allMeals) {
            mealModel.addElement(meal);
        }
    }
}