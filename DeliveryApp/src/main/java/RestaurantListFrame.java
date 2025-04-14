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
            {"Dunkin", "Coffee", "https://logos-world.net/wp-content/uploads/2020/11/Dunkin-Logo.png", 4.0},
            {"KFC", "Burgers", "https://1000logos.net/wp-content/uploads/2021/04/KFC-logo.png", 3.7},
            {"Starbucks", "Coffee", "https://upload.wikimedia.org/wikipedia/sco/thumb/a/a3/Starbucks_Corporation_Logo_2011.svg/800px-Starbucks_Corporation_Logo_2011.svg.png", 4.2},
            {"Herfy", "Burgers", "https://upload.wikimedia.org/wikipedia/en/f/f5/Herfy_logo.png", 4.0},
            {"Baskin Robbins", "Desserts", "https://upload.wikimedia.org/wikipedia/commons/thumb/7/7c/Baskin-Robbins_logo.svg/512px-Baskin-Robbins_logo.svg.png", 4.7},
            {"Al Sadhan", "Groceries", "https://sadhan.com/images/sadhan.png", 4.4},
            {"Ramli Cafe", "Juice", "https://img.freepik.com/free-photo/front-view-coffee-cup-with-coffee-beans-dark-background_140725-105774.jpg", 5.0},
            {"Pizza House", "Pizza", "https://cdn-icons-png.flaticon.com/512/3132/3132693.png", 4.3},
            {"Trolley", "Groceries", "https://www.pngplay.com/wp-content/uploads/13/Grocery-Cart-Transparent-PNG.png", 5.0},
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

//            try {
//                URL url = new URL(imageUrl);
//                Image img = Toolkit.getDefaultToolkit().createImage(url);
//                Image scaled = img.getScaledInstance(300, 160, Image.SCALE_SMOOTH);
//                JLabel imageLabel = new JLabel(new ImageIcon(scaled));
//                card.add(imageLabel, BorderLayout.NORTH);
//            } catch (Exception e) {
//                card.add(new JLabel("Image not loaded"), BorderLayout.NORTH);
//            }
                card.add(new JLabel("Restaurant Image"), BorderLayout.NORTH);


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