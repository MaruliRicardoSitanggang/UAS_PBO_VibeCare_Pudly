package com.CareVibe.controller;

import com.CareVibe.model.UserSession;
import com.CareVibe.view.AdminLoginView;
import com.CareVibe.view.AdminDashboardView;
import com.CareVibe.view.MainWindow;
import javafx.scene.control.Label;

public class AdminAuthController {

    private final AdminLoginView view;
    private final MainWindow mainWindow;

    // Admin credentials (untuk demo, nanti bisa pindah ke database)
    private static final String ADMIN_EMAIL = "admin@vibecare.com";
    private static final String ADMIN_PASSWORD = "admin123";
    private static final String ADMIN_NAME = "Super Admin";

    public AdminAuthController(AdminLoginView view, MainWindow mainWindow) {
        this.view = view;
        this.mainWindow = mainWindow;
    }

    public void handleLogin(String email, String password, Label errorLabel) {
        if (email.isEmpty() || password.isEmpty()) {
            view.showError("Email dan password harus diisi!", errorLabel);
            return;
        }

        // Validasi admin credentials
        if (email.equals(ADMIN_EMAIL) && password.equals(ADMIN_PASSWORD)) {
            // Login sebagai admin
            UserSession.login(ADMIN_EMAIL, ADMIN_NAME);
            UserSession.setLoggedInRole(UserSession.Role.ADMIN);
            view.close();
            openAdminDashboard();
        } else {
            view.showError("Email atau password admin salah!", errorLabel);
        }
    }

    private void openAdminDashboard() {
        AdminDashboardView adminDashboard = new AdminDashboardView(mainWindow);
        // Tampilkan admin dashboard di stage baru atau replace main window
        javafx.application.Platform.runLater(() -> {
            javafx.stage.Stage stage = (javafx.stage.Stage) mainWindow.getScene().getWindow();
            javafx.scene.Scene scene = new javafx.scene.Scene(adminDashboard, 1200, 700);
            stage.setScene(scene);
            stage.setTitle("VibeCare - Admin Panel");
            stage.show();
        });
    }
}