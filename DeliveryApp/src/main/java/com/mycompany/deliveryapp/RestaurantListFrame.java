 package com.mycompany.deliveryapp;

import javax.swing.*;
import java.awt.*;// للتصميم مثل border
import java.awt.event.*;// للازرار
import java.net.URL;// للروابط
import java.sql.*;// للداتا بيس

public class RestaurantListFrame extends JFrame {

    private JPanel container;
    private JComboBox<String> filterBox;

    public RestaurantListFrame() {
        setTitle("Restaurants");
        setSize(1000, 750);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        String[] filters = {"All", "Burgers", "Pizza", "Coffee", "Groceries", "Juice", "Desserts"};//فلتر لقائمه
        filterBox = new JComboBox<>(filters);// قائمه كومبو للفلتر
        filterBox.addActionListener(e -> renderCards((String) filterBox.getSelectedItem()));//تضيف  خصائص للزر بشكل اسرع -e
        add(filterBox, BorderLayout.NORTH);

        container = new JPanel();
        container.setLayout(new GridLayout(0, 3, 15, 15));
        container.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));//حوايف للبطاقات

        JScrollPane scrollPane = new JScrollPane(container);//اذا زدت عدد المطاعم يمديك تنزل تحت
        add(scrollPane, BorderLayout.CENTER);

        renderCards("All");// بالبدايه يظهر المطاعم كله
    }

    private void renderCards(String category) {
        container.removeAll();//تزيل كل البطاقات الي قبل للتحديث

        try {//try للداتا بيس علشان يتاكد البيانات موجوده ولالا
            Connection conn = DatabaseConnection.connect();
            String sql = "SELECT name, type, image_path, rating FROM restaurants";
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sql);

            while (rs.next()) {
                String name = rs.getString("name");
                String type = rs.getString("type");
                String image = rs.getString("image_path");
                double rating = rs.getDouble("rating");

                if (!category.equals("All") && !type.equalsIgnoreCase(category)) {//اذا ماطابق الفلتر يكمل
                    continue;
                }
                //اضافه بطاقه للمطعم
                JPanel card = new JPanel();
                card.setLayout(new BorderLayout());
                card.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY, 1));
                card.setBackground(Color.WHITE);
                card.setCursor(new Cursor(Cursor.HAND_CURSOR));
                ////
                try {// يتاكد الصوره موجوده ولالا
                    System.out.println("Trying to load: " + image);
                    URL path = getClass().getClassLoader().getResource("images/" + image);

                         System.out.println("Resolved path: " + path);
                    if (path != null) {
                        ImageIcon icon = new ImageIcon(path);
                        Image img = icon.getImage().getScaledInstance(300, 160, Image.SCALE_SMOOTH);
                        card.add(new JLabel(new ImageIcon(img)), BorderLayout.NORTH);
                    } else {
                        card.add(new JLabel("No Image"), BorderLayout.NORTH);
                    }
                } catch (Exception ex) {
                    card.add(new JLabel("Image error"), BorderLayout.NORTH);
                }
                //اعداد محتويات البطاقات
                JPanel textPanel = new JPanel();
                textPanel.setLayout(new BoxLayout(textPanel, BoxLayout.Y_AXIS));
                textPanel.setBackground(Color.WHITE);
                textPanel.setBorder(BorderFactory.createEmptyBorder(5, 10, 10, 10));
                
                JLabel nameLabel = new JLabel(name);// اسم المطعم
                nameLabel.setFont(new Font("Arial", Font.BOLD, 16));// نوع الخط
                JLabel typeLabel = new JLabel("<html><small>" + type + "</small></html>");// نوع االمطعم
                JLabel ratingLabel = new JLabel("⭐ " + rating);// التقييم

                textPanel.add(nameLabel);
                textPanel.add(typeLabel);
                textPanel.add(ratingLabel);

                card.add(textPanel, BorderLayout.CENTER);
                // اذا ضغطت على الزر يوديك لواجهه MenuFrame
                card.addMouseListener(new MouseAdapter() {
                    @Override
                    public void mouseClicked(MouseEvent e) {
                        dispose();// يقفل الواجهه الي قبل
                        new MenuFrame(name).setVisible(true); 
                    }
                });

                container.add(card);
            }

            conn.close();
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Error loading restaurants: " + e.getMessage());
        }

        container.revalidate();
        container.repaint();
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new RestaurantListFrame().setVisible(true));
    }
}
