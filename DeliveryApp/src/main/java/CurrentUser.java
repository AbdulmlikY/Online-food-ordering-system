public class CurrentUser {
    private static int id;
    private static String name;
    private static String email;
    private static String restaurantName;

    // النسخة الكاملة
    public static void setUser(int userId, String username, String userEmail, String rName) {
        id = userId;
        name = username;
        email = userEmail;
        restaurantName = rName;
    }

    // النسخة البسيطة (اختيارية حسب استخدامك)
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
