package org.example.newsgenie2409084.Util;

public class SessionManager {
    private static final ThreadLocal<String> currentUser = new ThreadLocal<>();

    public static void setUsername(String username) {
        currentUser.set(username);
    }

    public static String getUsername() {
        return currentUser.get();
    }

    public static void clear() {
        currentUser.remove();
    }
}