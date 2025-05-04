package com.mycompany.deliveryapp;

public class CurrentUser {
    private static int id;
    private static String name;
    private static String email;
    private static String restaurantName;

   
    public static void setUser(int userId, String username, String userEmail, String rName) {
        id = userId;
        name = username;
        email = userEmail;
        restaurantName = rName;
    }

  
    public static void setUser(int userId, String username, String userEmail) {
        id = userId;
        name = username;
        email = userEmail;
    }
public static void set(String userName, String rName) {
    name = userName;
    restaurantName = rName;
}

    public static String getName() {
        return name;
    }

    public static String getRestaurantName() {
        return restaurantName;
    }

    public static int getId() {
        return id;
    }

    public static String getEmail() {
        return email;
    }
}
