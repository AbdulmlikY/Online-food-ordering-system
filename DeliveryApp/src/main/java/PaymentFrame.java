import javax.swing.*;
import java.awt.*;

public class PaymentFrame extends JFrame {

    public PaymentFrame() {
        setTitle("Payment");
        setSize(400, 300);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout());

        JLabel title = new JLabel("Payment Simulation", SwingConstants.CENTER);
        title.setFont(new Font("Arial", Font.BOLD, 20));
        add(title, BorderLayout.NORTH);

        JPanel form = new JPanel(new GridLayout(3, 2, 10, 10));
        form.setBorder(BorderFactory.createEmptyBorder(20, 40, 10, 40));

        String[] methods = {"Credit Card", "Mada", "Cash on Delivery"};
        JComboBox<String> methodBox = new JComboBox<>(methods);

        JTextField cardNumber = new JTextField();
        JTextField cvv = new JTextField();

        form.add(new JLabel("Payment Method:"));
        form.add(methodBox);

        form.add(new JLabel("Card Number:"));
        form.add(cardNumber);

        form.add(new JLabel("CVV:"));
        form.add(cvv);

        add(form, BorderLayout.CENTER);

        JButton payBtn = new JButton("Pay");
        add(payBtn, BorderLayout.SOUTH);

        payBtn.addActionListener(e -> {
            String method = (String) methodBox.getSelectedItem();

            if (method.equals("Cash on Delivery")) {
                JOptionPane.showMessageDialog(this, "Order confirmed! Please pay upon delivery.");
                this.dispose();
            } else {
                if (cardNumber.getText().isEmpty() || cvv.getText().isEmpty()) {
                    JOptionPane.showMessageDialog(this, "Please enter card details.");
                } else {
                    JOptionPane.showMessageDialog(this, "Payment successful with " + method + "!");
                    this.dispose();
                }
            }
        });
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new PaymentFrame().setVisible(true));
    }
}