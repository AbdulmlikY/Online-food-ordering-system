import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.net.URL;
import java.util.ArrayList;

public class RestaurantListFrame extends JFrame {

    private JPanel container;
    private JComboBox<String> filterBox;
    private Object[][] restaurants;

    public RestaurantListFrame() {
        setTitle("Restaurants");
        setSize(1000, 750);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

     restaurants = new Object[][] {
    {"Dunkin", "Coffee", "images/dunkin.png", 4.0},
    {"KFC", "Burgers", "images/KFC.png", 3.7},
    {"Starbucks", "Coffee", "images/Starbucks.png", 4.2},
    {"Herfy", "Burgers", "images/herfy.png", 4.0},
    {"Baskin Robbins", "Desserts", "images/baskinrobin.png", 4.7},
    {"Al Sadhan", "Groceries", "images/ALSADHAN.jpg", 4.4},
    {"Ramli Cafe", "Juice", "images/RAMLI.jpg", 5.0},
    {"Pizza House", "Pizza", "images/PIZZAHOUSE.jpg", 4.3},
    {"Trolley", "Groceries", "images/trolley.jpg", 5.0},
};

        // Filter dropdown
        String[] filters = {"All", "Burgers", "Pizza", "Coffee", "Groceries", "Juice", "Desserts"};
        filterBox = new JComboBox<>(filters);
        filterBox.addActionListener(e -> renderCards((String) filterBox.getSelectedItem()));
        add(filterBox, BorderLayout.NORTH);

        container = new JPanel();
        container.setLayout(new GridLayout(0, 3, 15, 15));
        container.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        JScrollPane scrollPane = new JScrollPane(container);
        add(scrollPane, BorderLayout.CENTER);

        renderCards("All");
    }

    private void renderCards(String category) {
        container.removeAll();

        for (Object[] data : restaurants) {
            String name = (String) data[0];
            String type = (String) data[1];
            String imageUrl = (String) data[2];
            double rating = (double) data[3];

            if (!category.equals("All") && !type.equalsIgnoreCase(category)) {
                continue;
            }

            JPanel card = new JPanel();
            card.setLayout(new BorderLayout());
            card.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY, 1));
            card.setBackground(Color.WHITE);
            card.setCursor(new Cursor(Cursor.HAND_CURSOR));

try {
    URL imagePath = getClass().getClassLoader().getResource(imageUrl);
    if (imagePath != null) {
        ImageIcon icon = new ImageIcon(imagePath);
        Image img = icon.getImage().getScaledInstance(300, 160, Image.SCALE_SMOOTH);
        JLabel imageLabel = new JLabel(new ImageIcon(img));
        card.add(imageLabel, BorderLayout.NORTH);
    } else {
        card.add(new JLabel("Image not found"), BorderLayout.NORTH);
    }
} catch (Exception e) {
    card.add(new JLabel("Image load error"), BorderLayout.NORTH);
}





            JPanel textPanel = new JPanel();
            textPanel.setLayout(new BoxLayout(textPanel, BoxLayout.Y_AXIS));
            textPanel.setBorder(BorderFactory.createEmptyBorder(5, 10, 10, 10));
            textPanel.setBackground(Color.WHITE);

            JLabel nameLabel = new JLabel(name);
            nameLabel.setFont(new Font("Arial", Font.BOLD, 16));
            JLabel descLabel = new JLabel("<html><small>" + type + "</small></html>");
            JLabel ratingLabel = new JLabel("⭐ " + rating);

            textPanel.add(nameLabel);
            textPanel.add(descLabel);
            textPanel.add(ratingLabel);

            card.add(textPanel, BorderLayout.CENTER);

            card.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    dispose();
                    new MenuFrame(name).setVisible(true);
                }
            });

            container.add(card);
        }

        container.revalidate();
        container.repaint();
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new RestaurantListFrame().setVisible(true));
    }
}