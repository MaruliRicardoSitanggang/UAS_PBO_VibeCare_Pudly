package com.CareVibe.controller;

import com.CareVibe.model.UserSession;
import com.CareVibe.view.LoginDialog;
import com.CareVibe.view.MainWindow;
import com.CareVibe.view.RegisterDialog;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class AuthController {

    private final Stage stage;
    private LoginDialog loginDialog;
    private RegisterDialog registerDialog;
    private MainWindow mainWindow;

    public AuthController(Stage stage) {
        this.stage = stage;
        showLogin();
    }

    public void showLogin() {
        if (loginDialog == null) {
            loginDialog = new LoginDialog(null);
        }
        loginDialog.showAndWait();
        if (UserSession.isLoggedIn()) {
            openMainWindow();
        }
    }

    public void showRegister() {
        new RegisterDialog(null);
        if (UserSession.isLoggedIn()) {
            openMainWindow();
        }
    }

    private void openMainWindow() {
        Platform.runLater(() -> {
            if (mainWindow == null) {
                mainWindow = new MainWindow();
            }
            Scene scene = new Scene(mainWindow, 1200, 700);
            stage.setScene(scene);
            stage.setTitle("VibeCare - Aplikasi Kesehatan Mental");
            stage.show();
        });
    }
}