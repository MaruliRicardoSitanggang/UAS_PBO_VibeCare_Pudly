package com.CareVibe.model;

import java.time.LocalDateTime;

public class Admin {
    private String id;
    private String username;
    private String email;
    private String password;
    private String fullName;
    private String role; // SUPER_ADMIN, MODERATOR, CONTENT_MANAGER
    private LocalDateTime lastLogin;
    private boolean isActive;

    public Admin(String id, String username, String email, String password, String fullName, String role) {
        this.id = id;
        this.username = username;
        this.email = email;
        this.password = password;
        this.fullName = fullName;
        this.role = role;
        this.isActive = true;
        this.lastLogin = LocalDateTime.now();
    }

    // Getters
    public String getId() { return id; }
    public String getUsername() { return username; }
    public String getEmail() { return email; }
    public String getPassword() { return password; }
    public String getFullName() { return fullName; }
    public String getRole() { return role; }
    public LocalDateTime getLastLogin() { return lastLogin; }
    public boolean isActive() { return isActive; }

    // Setters
    public void setLastLogin(LocalDateTime lastLogin) { this.lastLogin = lastLogin; }
    public void setActive(boolean active) { isActive = active; }
    public void setPassword(String password) { this.password = password; }
}