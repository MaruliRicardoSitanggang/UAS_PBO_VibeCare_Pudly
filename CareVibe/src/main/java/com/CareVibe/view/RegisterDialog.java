package com.CareVibe.view;

import javafx.application.Platform;
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

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class RegisterDialog {

    private final MainWindow mainWindow;
    private Stage dialogStage;

    private static final String BASE_URL = "http://localhost:8080/api/auth";
    private static final HttpClient httpClient = HttpClient.newHttpClient();

    public RegisterDialog(MainWindow mainWindow) {
        this.mainWindow = mainWindow;
    }

    public void showAndWait() {
        showRegisterDialog();
    }

    public void show() {
        showRegisterDialog();
    }

    private void showRegisterDialog() {
        dialogStage = new Stage();
        dialogStage.initModality(Modality.APPLICATION_MODAL);
        dialogStage.initStyle(StageStyle.UNDECORATED);
        dialogStage.setTitle("Daftar ke VibeCare");

        VBox root = new VBox(0);
        root.setStyle("-fx-background-color: #F0FDF4;");

        HBox headerBar = new HBox();
        headerBar.setAlignment(Pos.CENTER_RIGHT);
        headerBar.setPadding(new Insets(15, 15, 0, 15));
        Button closeBtn = new Button("✕");
        closeBtn.setStyle("-fx-background-color: transparent; -fx-text-fill: #9CA3AF; -fx-font-size: 18px; -fx-cursor: hand;");
        closeBtn.setOnAction(e -> dialogStage.close());
        headerBar.getChildren().add(closeBtn);

        ScrollPane scrollPane = new ScrollPane();
        scrollPane.setFitToWidth(true);
        scrollPane.setStyle("-fx-background-color: transparent;");
        scrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);

        VBox content = new VBox(16);
        content.setAlignment(Pos.TOP_CENTER);
        content.setPadding(new Insets(0, 40, 40, 40));

        // Logo
        VBox logoBox = new VBox(5);
        logoBox.setAlignment(Pos.CENTER);
        Circle logoCircle = new Circle(40);
        logoCircle.setFill(Color.web("#10B981"));
        Label logoIcon = new Label("🧘");
        logoIcon.setFont(Font.font("System", 40));
        StackPane logoStack = new StackPane();
        logoStack.getChildren().addAll(logoCircle, logoIcon);
        Label appName = new Label("VibeCare");
        appName.setFont(Font.font("System", FontWeight.BOLD, 28));
        appName.setStyle("-fx-text-fill: #065F46;");
        Label tagline = new Label("Aplikasi Kesehatan Mental");
        tagline.setStyle("-fx-text-fill: #6B7280; -fx-font-size: 12px;");
        logoBox.getChildren().addAll(logoStack, appName, tagline);

        Label title = new Label("Daftar Akun Baru");
        title.setFont(Font.font("System", FontWeight.BOLD, 20));
        title.setStyle("-fx-text-fill: #1F2937;");

        // Form fields
        VBox nameBox = new VBox(5);
        Label nameLabel = new Label("Nama Lengkap");
        nameLabel.setStyle("-fx-text-fill: #4B5563; -fx-font-size: 13px; -fx-font-weight: bold;");
        TextField nameField = new TextField();
        nameField.setPromptText("Nama lengkap");
        nameField.setStyle("-fx-background-color: white; -fx-background-radius: 12; -fx-border-color: #E5E7EB; -fx-border-radius: 12; -fx-padding: 12 15 12 15; -fx-font-size: 14px;");
        nameBox.getChildren().addAll(nameLabel, nameField);

        VBox emailBox = new VBox(5);
        Label emailLabel = new Label("Email");
        emailLabel.setStyle("-fx-text-fill: #4B5563; -fx-font-size: 13px; -fx-font-weight: bold;");
        TextField emailField = new TextField();
        emailField.setPromptText("email@contoh.com");
        emailField.setStyle("-fx-background-color: white; -fx-background-radius: 12; -fx-border-color: #E5E7EB; -fx-border-radius: 12; -fx-padding: 12 15 12 15; -fx-font-size: 14px;");
        emailBox.getChildren().addAll(emailLabel, emailField);

        VBox passBox = new VBox(5);
        Label passLabel = new Label("Password");
        passLabel.setStyle("-fx-text-fill: #4B5563; -fx-font-size: 13px; -fx-font-weight: bold;");
        PasswordField passField = new PasswordField();
        passField.setPromptText("Password (minimal 6 karakter)");
        passField.setStyle("-fx-background-color: white; -fx-background-radius: 12; -fx-border-color: #E5E7EB; -fx-border-radius: 12; -fx-padding: 12 15 12 15; -fx-font-size: 14px;");
        passBox.getChildren().addAll(passLabel, passField);

        VBox confirmPassBox = new VBox(5);
        Label confirmPassLabel = new Label("Konfirmasi Password");
        confirmPassLabel.setStyle("-fx-text-fill: #4B5563; -fx-font-size: 13px; -fx-font-weight: bold;");
        PasswordField confirmPassField = new PasswordField();
        confirmPassField.setPromptText("Ulangi password");
        confirmPassField.setStyle("-fx-background-color: white; -fx-background-radius: 12; -fx-border-color: #E5E7EB; -fx-border-radius: 12; -fx-padding: 12 15 12 15; -fx-font-size: 14px;");
        confirmPassBox.getChildren().addAll(confirmPassLabel, confirmPassField);

        Label errorLabel = new Label();
        errorLabel.setStyle("-fx-text-fill: #EF4444; -fx-font-size: 12px;");
        errorLabel.setVisible(false);
        errorLabel.setWrapText(true);

        Button registerBtn = new Button("Daftar");
        registerBtn.setStyle("-fx-background-color: #10B981; -fx-text-fill: white; -fx-font-weight: bold; -fx-font-size: 14px; -fx-padding: 12 20 12 20; -fx-background-radius: 30; -fx-cursor: hand;");
        registerBtn.setMaxWidth(Double.MAX_VALUE);
        registerBtn.setOnMouseEntered(e -> registerBtn.setStyle("-fx-background-color: #059669; -fx-text-fill: white; -fx-font-weight: bold; -fx-font-size: 14px; -fx-padding: 12 20 12 20; -fx-background-radius: 30; -fx-cursor: hand;"));
        registerBtn.setOnMouseExited(e -> registerBtn.setStyle("-fx-background-color: #10B981; -fx-text-fill: white; -fx-font-weight: bold; -fx-font-size: 14px; -fx-padding: 12 20 12 20; -fx-background-radius: 30; -fx-cursor: hand;"));

        registerBtn.setOnAction(e -> {
            String name = nameField.getText().trim();
            String email = emailField.getText().trim();
            String password = passField.getText();
            String confirmPassword = confirmPassField.getText();

            // Validasi frontend
            if (name.isEmpty()) { showError(errorLabel, "Nama lengkap harus diisi!"); return; }
            if (email.isEmpty()) { showError(errorLabel, "Email harus diisi!"); return; }
            if (!email.contains("@") || !email.contains(".")) { showError(errorLabel, "Email tidak valid!"); return; }
            if (password.isEmpty()) { showError(errorLabel, "Password harus diisi!"); return; }
            if (password.length() < 6) { showError(errorLabel, "Password minimal 6 karakter!"); return; }
            if (!password.equals(confirmPassword)) { showError(errorLabel, "Konfirmasi password tidak cocok!"); return; }

            registerBtn.setDisable(true);
            registerBtn.setText("Mendaftar...");
            errorLabel.setVisible(false);

            // ✅ HTTP POST ke backend
            new Thread(() -> {
                try {
                    // Escape karakter khusus untuk JSON
                    String safeName = name.replace("\"", "\\\"");
                    String safeEmail = email.replace("\"", "\\\"");
                    String safePassword = password.replace("\"", "\\\"");

                    String json = "{"
                            + "\"username\":\"" + safeName + "\","
                            + "\"email\":\"" + safeEmail + "\","
                            + "\"password\":\"" + safePassword + "\""
                            + "}";

                    HttpRequest request = HttpRequest.newBuilder()
                            .uri(URI.create(BASE_URL + "/register"))
                            .header("Content-Type", "application/json")
                            .POST(HttpRequest.BodyPublishers.ofString(json))
                            .build();

                    HttpResponse<String> response = httpClient.send(request,
                            HttpResponse.BodyHandlers.ofString());

                    Platform.runLater(() -> {
                        if (response.statusCode() == 200) {
                            // Sukses — langsung buka LoginDialog dengan email prefilled
                            dialogStage.close();
                            new LoginDialog(mainWindow, email, password);
                        } else {
                            String msg = response.body();
                            showError(errorLabel, msg != null && !msg.isEmpty()
                                    ? msg : "Registrasi gagal. Coba lagi.");
                            registerBtn.setDisable(false);
                            registerBtn.setText("Daftar");
                        }
                    });
                } catch (Exception ex) {
                    Platform.runLater(() -> {
                        showError(errorLabel, "Tidak bisa terhubung ke server. Pastikan backend berjalan.");
                        registerBtn.setDisable(false);
                        registerBtn.setText("Daftar");
                    });
                }
            }).start();
        });

        HBox loginLink = new HBox(5);
        loginLink.setAlignment(Pos.CENTER);
        Label haveAccountLabel = new Label("Sudah punya akun?");
        haveAccountLabel.setStyle("-fx-text-fill: #6B7280; -fx-font-size: 13px;");
        Hyperlink loginHyperlink = new Hyperlink("Masuk di sini");
        loginHyperlink.setStyle("-fx-text-fill: #10B981; -fx-font-size: 13px; -fx-underline: true; -fx-cursor: hand;");
        loginHyperlink.setOnAction(e -> {
            dialogStage.close();
            new LoginDialog(mainWindow).showAndWait();
        });
        loginLink.getChildren().addAll(haveAccountLabel, loginHyperlink);

        content.getChildren().addAll(logoBox, title, nameBox, emailBox, passBox,
                confirmPassBox, registerBtn, loginLink, errorLabel);
        scrollPane.setContent(content);
        root.getChildren().addAll(headerBar, scrollPane);

        Scene scene = new Scene(root, 480, 620);
        dialogStage.setScene(scene);
        dialogStage.showAndWait();
    }

    private void showError(Label label, String message) {
        label.setText(message);
        label.setVisible(true);
    }
}
