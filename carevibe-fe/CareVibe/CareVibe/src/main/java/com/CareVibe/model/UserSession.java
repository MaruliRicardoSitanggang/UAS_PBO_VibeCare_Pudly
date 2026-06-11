package com.CareVibe.model;

import java.util.HashMap;
import java.util.Map;

public class UserSession {
    private static int points = 0;
    private static String loggedInUser = null;
    private static String loggedInName = null;
    private static Runnable onPointsChanged;
    private static Map<String, Boolean> completedMissions = new HashMap<>();
    private static Map<String, Integer> userPointsMap = new HashMap<>();

    // Inisialisasi default points untuk testing
    static {
        userPointsMap.put("default", 0);
    }

    public static int getPoints() {
        return points;
    }

    public static void addPoints(int amount) {
        points += amount;
        // Simpan ke map berdasarkan user
        if (loggedInUser != null) {
            userPointsMap.put(loggedInUser, points);
        }
        if (onPointsChanged != null) onPointsChanged.run();
    }

    public static void deductPoints(int amount) {
        points = Math.max(0, points - amount);
        if (loggedInUser != null) {
            userPointsMap.put(loggedInUser, points);
        }
        if (onPointsChanged != null) onPointsChanged.run();
    }

    public static String getLoggedInUser() {
        return loggedInUser;
    }

    public static String getLoggedInName() {
        return loggedInName;
    }

    public static void setLoggedInUser(String user) {
        loggedInUser = user;
        // Load points dari user yang login
        if (user != null && userPointsMap.containsKey(user)) {
            points = userPointsMap.get(user);
        } else if (user != null) {
            points = 0;
            userPointsMap.put(user, 0);
        }
        if (onPointsChanged != null) onPointsChanged.run();
    }

    public static void setLoggedInName(String name) {
        loggedInName = name;
    }

    public static void setOnPointsChanged(Runnable callback) {
        onPointsChanged = callback;
    }

    public static boolean hasCompletedMission(String key) {
        return completedMissions.getOrDefault(key, false);
    }

    public static void setMissionCompleted(String key, boolean completed) {
        completedMissions.put(key, completed);
    }

    public static void login(String email) {
        loggedInUser = email;
        // Load poin user dari storage jika ada
        if (userPointsMap.containsKey(email)) {
            points = userPointsMap.get(email);
        } else {
            points = 0;
            userPointsMap.put(email, 0);
        }
        if (onPointsChanged != null) onPointsChanged.run();
    }

    public static void login(String email, String name) {
        loggedInUser = email;
        loggedInName = name;
        if (userPointsMap.containsKey(email)) {
            points = userPointsMap.get(email);
        } else {
            points = 0;
            userPointsMap.put(email, 0);
        }
        if (onPointsChanged != null) onPointsChanged.run();
    }

    public static void logout() {
        // Simpan points sebelum logout
        if (loggedInUser != null) {
            userPointsMap.put(loggedInUser, points);
        }
        loggedInUser = null;
        loggedInName = null;
        points = 0;
        // Reset completed missions untuk user baru
        completedMissions.clear();
        if (onPointsChanged != null) onPointsChanged.run();
    }

    public static boolean isLoggedIn() {
        return loggedInUser != null && !loggedInUser.isEmpty();
    }

    // Reset semua data (untuk testing)
    public static void reset() {
        points = 0;
        loggedInUser = null;
        loggedInName = null;
        completedMissions.clear();
        if (onPointsChanged != null) onPointsChanged.run();
    }

    // Method untuk mendapatkan poin user tertentu
    public static int getUserPoints(String email) {
        return userPointsMap.getOrDefault(email, 0);
    }
}