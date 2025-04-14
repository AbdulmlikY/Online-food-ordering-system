public class CurrentUser {
    private static int id;
    private static String name;
    private static String email;

    public static void setUser(int userId, String userName, String userEmail) {
        id = userId;
        name = userName;
        email = userEmail;
    }

    public static int getId() {
        return id;
    }

    public static String getName() {
        return name;
    }

    public static String getEmail() {
        return email;
    }

    public static void clear() {
        id = 0;
        name = null;
        email = null;
    }

    public static boolean isLoggedIn() {
        return id != 0;
    }
}