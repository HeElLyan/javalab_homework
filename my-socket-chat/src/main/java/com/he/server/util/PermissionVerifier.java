package com.he.server.util;

public class PermissionVerifier {

    public static void allowIfAdmin(String role) {
        if (!isAdmin(role))
            throw new SecurityException("You can't have the access");
    }

    public static void allowIfUserOrAdmin(String role) {
        if (!isUser(role) && !isAdmin(role))
            throw new SecurityException("You can't have the access" + role);
    }

    private static boolean isUser(String role) {
        return role.equals("user");
    }

    @SuppressWarnings("BooleanMethodIsAlwaysInverted")
    private static boolean isAdmin(String role) {
        return role.equals("admin");
    }

}
