package com.CareVibe.view;

import com.CareVibe.controller.AdminAuthController;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class AdminLoginView {

    private Stage stage;
    private AdminAuthController controller;
    private MainWindow mainWindow;

    public AdminLoginView(MainWindow mainWindow) {
        this.mainWindow = mainWindow;
        this.controller = new AdminAuthController(this, mainWindow);
        showLogin();
    }

    public void showLogin() {
        stage = new Stage();
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.initStyle(StageStyle.UNDECORATED);
        stage.setTitle("Admin Login - VibeCare");

        VBox root = new VBox(0);
        root.setStyle("-fx-background-color: #F0FDF4;");

        HBox headerBar = new HBox();
        headerBar.setAlignment(Pos.CENTER_RIGHT);
        headerBar.setPadding(new Insets(15, 15, 0, 15));

        Button closeBtn = new Button("✕");
        closeBtn.setStyle("-fx-background-color: transparent; -fx-text-fill: #9CA3AF; -fx-font-size: 18px; -fx-cursor: hand;");
        closeBtn.setOnAction(e -> stage.close());
        headerBar.getChildren().add(closeBtn);

        VBox content = new VBox(20);
        content.setAlignment(Pos.CENTER);
        content.setPadding(new Insets(0, 40, 40, 40));

        // Logo Admin
        VBox logoBox = new VBox(5);
        logoBox.setAlignment(Pos.CENTER);
        Circle logoCircle = new Circle(45);
        logoCircle.setFill(Color.web("#1E3A5F"));
        Label logoIcon = new Label("👑");
        logoIcon.setFont(Font.font("System", 42));
        StackPane logoStack = new StackPane();
        logoStack.getChildren().addAll(logoCircle, logoIcon);
        Label appName = new Label("Admin Panel");
        appName.setFont(Font.font("System", FontWeight.BOLD, 24));
        appName.setStyle("-fx-text-fill: #1E3A5F;");
        Label tagline = new Label("VibeCare Administrator");
        tagline.setStyle("-fx-text-fill: #6B7280; -fx-font-size: 12px;");
        logoBox.getChildren().addAll(logoStack, appName, tagline);

        Label title = new Label("Login Admin");
        title.setFont(Font.font("System", FontWeight.BOLD, 20));
        title.setStyle("-fx-text-fill: #1F2937;");

        // Email
        VBox emailBox = new VBox(5);
        Label emailLabel = new Label("Email Admin");
        emailLabel.setStyle("-fx-text-fill: #4B5563; -fx-font-size: 13px; -fx-font-weight: bold;");
        TextField emailField = new TextField();
        emailField.setPromptText("admin@vibecare.com");
        emailField.setStyle("-fx-background-color: white; -fx-background-radius: 12; -fx-border-color: #E5E7EB; -fx-border-radius: 12; -fx-padding: 12 15 12 15; -fx-font-size: 14px;");
        emailBox.getChildren().addAll(emailLabel, emailField);

        // Password
        VBox passBox = new VBox(5);
        Label passLabel = new Label("Password");
        passLabel.setStyle("-fx-text-fill: #4B5563; -fx-font-size: 13px; -fx-font-weight: bold;");
        PasswordField passField = new PasswordField();
        passField.setPromptText("••••••••");
        passField.setStyle("-fx-background-color: white; -fx-background-radius: 12; -fx-border-color: #E5E7EB; -fx-border-radius: 12; -fx-padding: 12 15 12 15; -fx-font-size: 14px;");
        passBox.getChildren().addAll(passLabel, passField);

        Button loginBtn = new Button("Login sebagai Admin");
        loginBtn.setStyle("-fx-background-color: #1E3A5F; -fx-text-fill: white; -fx-font-weight: bold; -fx-font-size: 14px; -fx-padding: 12 20 12 20; -fx-background-radius: 30; -fx-cursor: hand;");
        loginBtn.setMaxWidth(Double.MAX_VALUE);

        Label errorLabel = new Label();
        errorLabel.setStyle("-fx-text-fill: #EF4444; -fx-font-size: 12px;");
        errorLabel.setVisible(false);

        loginBtn.setOnAction(e -> {
            String email = emailField.getText().trim();
            String password = passField.getText();
            controller.handleLogin(email, password, errorLabel);
        });

        Button backBtn = new Button("← Kembali ke User");
        backBtn.setStyle("-fx-background-color: transparent; -fx-text-fill: #6B7280; -fx-font-size: 12px; -fx-cursor: hand; -fx-underline: true;");
        backBtn.setOnAction(e -> stage.close());

        content.getChildren().addAll(logoBox, title, emailBox, passBox, loginBtn, errorLabel, backBtn);
        root.getChildren().addAll(headerBar, content);

        Scene scene = new Scene(root, 420, 550);
        stage.setScene(scene);
        stage.showAndWait();
    }

    public void close() {
        if (stage != null) stage.close();
    }

    public void showError(String message, Label errorLabel) {
        errorLabel.setText(message);
        errorLabel.setVisible(true);
    }
}