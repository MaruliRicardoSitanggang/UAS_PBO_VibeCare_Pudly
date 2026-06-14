package com.CareVibe.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class UserSession {

    public enum Role {
        USER, ADMIN, PSIKOLOG
    }

    private static int points = 0;
    private static String loggedInUser = null;
    private static String loggedInName = null;
    private static Role loggedInRole = Role.USER;
    private static final List<Runnable> pointsListeners = new ArrayList<>();
    private static Map<String, Boolean> completedMissions = new HashMap<>();
    private static Map<String, Integer> userPointsMap = new HashMap<>();
    private static Map<String, String> userNamesMap = new HashMap<>();
    private static Map<String, Role> userRolesMap = new HashMap<>();

    static {
        userPointsMap.put("default", 0);
        userRolesMap.put("default", Role.USER);

        userPointsMap.put("admin@vibecare.com", 0);
        userNamesMap.put("admin@vibecare.com", "Super Admin");
        userRolesMap.put("admin@vibecare.com", Role.ADMIN);

        userPointsMap.put("psikolog@vibecare.com", 0);
        userNamesMap.put("psikolog@vibecare.com", "Dr. Psikolog");
        userRolesMap.put("psikolog@vibecare.com", Role.PSIKOLOG);
    }

    // ===== LISTENER =====
    public static void setOnPointsChanged(Runnable callback) {
        pointsListeners.add(callback);
    }

    public static void removeOnPointsChanged(Runnable callback) {
        pointsListeners.remove(callback);
    }

    private static void firePointsChanged() {
        for (Runnable r : new ArrayList<>(pointsListeners)) r.run();
    }

    // ===== POINTS =====
    public static int getPoints() {
        return points;
    }

    public static void addPoints(int amount) {
        points += amount;
        if (loggedInUser != null) userPointsMap.put(loggedInUser, points);
        firePointsChanged();
        System.out.println("+ " + amount + " points. Total: " + points);
    }

    public static void deductPoints(int amount) {
        points = Math.max(0, points - amount);
        if (loggedInUser != null) userPointsMap.put(loggedInUser, points);
        firePointsChanged();
    }

    public static void setPoints(int newPoints) {
        points = newPoints;
        if (loggedInUser != null) userPointsMap.put(loggedInUser, points);
        firePointsChanged();
    }

    // ===== USER INFO =====
    public static String getLoggedInUser() {
        return loggedInUser;
    }

    public static String getLoggedInName() {
        if (loggedInName != null && !loggedInName.isEmpty()) return loggedInName;
        if (loggedInUser != null && loggedInUser.contains("@"))
            return loggedInUser.substring(0, loggedInUser.indexOf("@"));
        return loggedInUser;
    }

    public static Role getLoggedInRole() {
        return loggedInRole;
    }

    public static boolean isAdmin() {
        return loggedInRole == Role.ADMIN;
    }

    public static boolean isPsychologist() {
        return loggedInRole == Role.PSIKOLOG;
    }

    public static boolean isUser() {
        return loggedInRole == Role.USER;
    }

    public static boolean isLoggedIn() {
        return loggedInUser != null && !loggedInUser.isEmpty();
    }

    // ===== SETTERS =====
    public static void setLoggedInUser(String user) {
        loggedInUser = user;
        if (user != null && userPointsMap.containsKey(user)) {
            points = userPointsMap.get(user);
        } else if (user != null) {
            points = 0;
            userPointsMap.put(user, 0);
        }
        if (user != null && userRolesMap.containsKey(user)) {
            loggedInRole = userRolesMap.get(user);
        } else {
            loggedInRole = Role.USER;
        }
        firePointsChanged();
    }

    public static void setLoggedInName(String name) {
        loggedInName = name;
        if (loggedInUser != null) userNamesMap.put(loggedInUser, name);
    }

    public static void setLoggedInRole(Role role) {
        loggedInRole = role;
        if (loggedInUser != null) userRolesMap.put(loggedInUser, role);
        firePointsChanged();
    }

    // ===== MISSIONS =====
    public static boolean hasCompletedMission(String key) {
        return completedMissions.getOrDefault(key, false);
    }

    public static void setMissionCompleted(String key, boolean completed) {
        completedMissions.put(key, completed);
        if (completed) addPoints(10);
    }

    // ===== LOGIN / LOGOUT =====
    public static void login(String email) {
        loggedInUser = email;
        loggedInName = userNamesMap.containsKey(email)
                ? userNamesMap.get(email)
                : (email.contains("@") ? email.substring(0, email.indexOf("@")) : email);
        points = userPointsMap.getOrDefault(email, 0);
        userPointsMap.put(email, points);
        loggedInRole = userRolesMap.getOrDefault(email, Role.USER);
        userRolesMap.put(email, loggedInRole);
        firePointsChanged();
        System.out.println("User logged in: " + email + " | role: " + loggedInRole + " | points: " + points);
    }

    public static void login(String email, String name) {
        loggedInUser = email;
        loggedInName = name;
        userNamesMap.put(email, name);
        points = userPointsMap.getOrDefault(email, 0);
        userPointsMap.put(email, points);
        loggedInRole = userRolesMap.getOrDefault(email, Role.USER);
        userRolesMap.put(email, loggedInRole);
        firePointsChanged();
        System.out.println("User logged in: " + name + " (" + email + ") | role: " + loggedInRole + " | points: " + points);
    }

    public static void logout() {
        if (loggedInUser != null) userPointsMap.put(loggedInUser, points);
        loggedInUser = null;
        loggedInName = null;
        loggedInRole = Role.USER;
        points = 0;
        firePointsChanged();
        System.out.println("User logged out");
    }

    public static void reset() {
        points = 0;
        loggedInUser = null;
        loggedInName = null;
        loggedInRole = Role.USER;
        completedMissions.clear();
        firePointsChanged();
    }

    // ===== UTILS =====
    public static int getUserPoints(String email) {
        return userPointsMap.getOrDefault(email, 0);
    }

    public static Map<String, Integer> getAllUserPoints() {
        return new HashMap<>(userPointsMap);
    }

    public static void registerAdmin(String email, String name, String password) {
        userNamesMap.put(email, name);
        userPointsMap.put(email, 0);
        userRolesMap.put(email, Role.ADMIN);
        System.out.println("Admin registered: " + email);
    }

    public static void registerPsychologist(String email, String name, String password) {
        userNamesMap.put(email, name);
        userPointsMap.put(email, 0);
        userRolesMap.put(email, Role.PSIKOLOG);
        System.out.println("Psychologist registered: " + email);
    }

    public static Role getUserRole(String email) {
        return userRolesMap.getOrDefault(email, Role.USER);
    }

    public static void updateUserRole(String email, Role role) {
        userRolesMap.put(email, role);
        if (loggedInUser != null && loggedInUser.equals(email)) {
            loggedInRole = role;
            firePointsChanged();
        }
        System.out.println("User role updated: " + email + " -> " + role);
    }
}