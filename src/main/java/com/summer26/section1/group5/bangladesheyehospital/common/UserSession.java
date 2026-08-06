package com.summer26.section1.group5.bangladesheyehospital.common;

public class UserSession {

    private static int userId;
    private static String userName;
    private static String role;

    public static void setLoggedInUser(
            int id,
            String name,
            String userRole
    ) {
        userId = id;
        userName = name;
        role = userRole;
    }

    public static int getUserId() {
        return userId;
    }

    public static String getUserName() {
        return userName;
    }

    public static String getRole() {
        return role;
    }

    public static boolean isLoggedIn() {
        return userId != 0;
    }

    public static void clearSession() {
        userId = 0;
        userName = null;
        role = null;
    }
}