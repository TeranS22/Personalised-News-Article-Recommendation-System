package org.example.newsgenie2409084.Util;

public class CurrentUser {
    private static String currentUser;

    public static void setUsername(String username) {
        currentUser = username;
    }

    public static String getUsername() {
        return currentUser;
    }

    public static void clear() {
        currentUser = null;
    }
}