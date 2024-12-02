package org.example.newsgenie2409084.Util;

public class CurrentUser {
    private static final ThreadLocal<String> currentUser = ThreadLocal.withInitial(() -> null);

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