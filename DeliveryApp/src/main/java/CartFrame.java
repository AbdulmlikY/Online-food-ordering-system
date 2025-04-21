import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.Statement;
import java.sql.SQLException;
import java.util.List;

public class CartFrame extends JFrame {

    private JList<String> cartList;
    private DefaultListModel<String> cartModel;
    private JButton checkoutButton, backButton;

    public CartFrame() {
        setTitle("Your Cart");
        setSize(400, 350);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        JLabel title = new JLabel("Cart", SwingConstants.CENTER);
        title.setFont(new Font("Arial", Font.BOLD, 22));
        add(title, BorderLayout.NORTH);

        cartModel = new DefaultListModel<>();
        List<String> items = Cart.getItems();

        if (items.isEmpty()) {
            cartModel.addElement("Cart is empty.");
        } else {
            for (String item : items) {
                cartModel.addElement(item);
            }
        }

        cartList = new JList<>(cartModel);
        add(new JScrollPane(cartList), BorderLayout.CENTER);

        JPanel bottomPanel = new JPanel(new FlowLayout());

        checkoutButton = new JButton("Checkout");
        backButton = new JButton("Back");

        bottomPanel.add(checkoutButton);
        bottomPanel.add(backButton);

        add(bottomPanel, BorderLayout.SOUTH);

        checkoutButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (Cart.isEmpty()) {
                    JOptionPane.showMessageDialog(CartFrame.this, "Your cart is already empty.");
                    return;
                }

     
                PaymentFrame paymentFrame = new PaymentFrame();
                paymentFrame.setModalExclusionType(Dialog.ModalExclusionType.APPLICATION_EXCLUDE);
                paymentFrame.setVisible(true);

        
                try {
                    Connection conn = DatabaseConnection.connect();
                    if (conn == null) {
                        JOptionPane.showMessageDialog(CartFrame.this, "Database connection failed.");
                        return;
                    }

                    String orderSql = "INSERT INTO orders (user_id) VALUES (?)";
                    PreparedStatement orderStmt = conn.prepareStatement(orderSql, Statement.RETURN_GENERATED_KEYS);
                    orderStmt.setInt(1, CurrentUser.getId());
                    int affectedRows = orderStmt.executeUpdate();

                    if (affectedRows == 0) {
                        throw new SQLException("Creating order failed, no rows affected.");
                    }

                    int orderId = 0;
                    var rs = orderStmt.getGeneratedKeys();
                    if (rs.next()) {
                        orderId = rs.getInt(1);
                    }

                    String itemSql = "INSERT INTO order_items (order_id, item_name) VALUES (?, ?)";
                    PreparedStatement itemStmt = conn.prepareStatement(itemSql);

                    for (String item : Cart.getItems()) {
                        itemStmt.setInt(1, orderId);
                        itemStmt.setString(2, item);
                        itemStmt.addBatch();
                    }

                    itemStmt.executeBatch();
                    conn.close();

                    Cart.clear();
                    cartModel.clear();
                    cartModel.addElement("Cart is empty.");

                    JOptionPane.showMessageDialog(CartFrame.this, "Order placed successfully!");

                } catch (SQLException ex) {
                    JOptionPane.showMessageDialog(CartFrame.this, "Error: " + ex.getMessage());
                }
            }
        });

        backButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                dispose();
                new RestaurantListFrame().setVisible(true);
            }
        });
    }
}