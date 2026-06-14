package com.CareVibe.view;

import com.CareVibe.model.UserSession;
import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class LoginDialog extends Stage {

    private MainWindow mainWindow;
    private String prefilledEmail = "";
    private String prefilledPassword = "";

    private static final String BASE_URL = "http://localhost:8080/api/auth";
    private static final HttpClient httpClient = HttpClient.newHttpClient();

    public LoginDialog(MainWindow mainWindow) {
        this.mainWindow = mainWindow;
        initModality(Modality.APPLICATION_MODAL);
        setTitle("Masuk - VibeCare");
        setResizable(false);
        buildUI();
        show();
    }

    public LoginDialog(MainWindow mainWindow, String email, String password) {
        this.mainWindow = mainWindow;
        this.prefilledEmail = email;
        this.prefilledPassword = password;
        initModality(Modality.APPLICATION_MODAL);
        setTitle("Masuk - VibeCare");
        setResizable(false);
        buildUI();
        showAndWait();
    }

    private void buildUI() {
        VBox root = new VBox(20);
        root.setPadding(new Insets(40));
        root.setAlignment(Pos.CENTER);
        root.setStyle("-fx-background-color: #F0FDF4;");
        root.setPrefWidth(400);

        Label title = new Label("Selamat Datang");
        title.setFont(Font.font("System", FontWeight.BOLD, 26));
        title.setStyle("-fx-text-fill: #064E3B;");

        Label subtitle = new Label("Masuk ke akun VibeCare Anda");
        subtitle.setStyle("-fx-text-fill: #6B7280; -fx-font-size: 14px;");

        VBox headerBox = new VBox(4, title, subtitle);
        headerBox.setAlignment(Pos.CENTER);

        VBox formBox = new VBox(12);
        formBox.setMaxWidth(320);

        Label emailLabel = new Label("Email");
        emailLabel.setStyle("-fx-font-weight: bold; -fx-font-size: 13px; -fx-text-fill: #1F2937;");
        TextField emailField = new TextField();
        emailField.setPromptText("contoh@email.com");
        emailField.setStyle(
                "-fx-background-color: white; -fx-border-color: #D1FAE5;" +
                        "-fx-border-radius: 10; -fx-background-radius: 10;" +
                        "-fx-padding: 10 14 10 14; -fx-font-size: 14px;"
        );
        if (!prefilledEmail.isEmpty()) emailField.setText(prefilledEmail);

        Label passLabel = new Label("Password");
        passLabel.setStyle("-fx-font-weight: bold; -fx-font-size: 13px; -fx-text-fill: #1F2937;");
        PasswordField passField = new PasswordField();
        passField.setPromptText("Masukkan password");
        passField.setStyle(
                "-fx-background-color: white; -fx-border-color: #D1FAE5;" +
                        "-fx-border-radius: 10; -fx-background-radius: 10;" +
                        "-fx-padding: 10 14 10 14; -fx-font-size: 14px;"
        );
        if (!prefilledPassword.isEmpty()) passField.setText(prefilledPassword);

        Label errorLabel = new Label("");
        errorLabel.setStyle("-fx-text-fill: #EF4444; -fx-font-size: 12px;");
        errorLabel.setWrapText(true);
        errorLabel.setVisible(false);

        formBox.getChildren().addAll(emailLabel, emailField, passLabel, passField, errorLabel);

        Button btnLogin = new Button("Masuk");
        btnLogin.setMaxWidth(Double.MAX_VALUE);
        btnLogin.setMinHeight(44);
        btnLogin.setStyle(
                "-fx-background-color: #10B981; -fx-text-fill: white;" +
                        "-fx-font-weight: bold; -fx-font-size: 15px;" +
                        "-fx-padding: 12 0 12 0; -fx-background-radius: 30; -fx-cursor: hand;"
        );
        btnLogin.setOnMouseEntered(e -> btnLogin.setStyle(
                "-fx-background-color: #059669; -fx-text-fill: white;" +
                        "-fx-font-weight: bold; -fx-font-size: 15px;" +
                        "-fx-padding: 12 0 12 0; -fx-background-radius: 30; -fx-cursor: hand;"
        ));
        btnLogin.setOnMouseExited(e -> btnLogin.setStyle(
                "-fx-background-color: #10B981; -fx-text-fill: white;" +
                        "-fx-font-weight: bold; -fx-font-size: 15px;" +
                        "-fx-padding: 12 0 12 0; -fx-background-radius: 30; -fx-cursor: hand;"
        ));

        // ✅ FIX: logika login ada di dalam setOnAction
        btnLogin.setOnAction(e -> {
            String email = emailField.getText().trim();
            String password = passField.getText();

            if (email.isEmpty() || password.isEmpty()) {
                errorLabel.setText("Email dan password tidak boleh kosong!");
                errorLabel.setVisible(true);
                return;
            }

            btnLogin.setDisable(true);
            btnLogin.setText("Memproses...");
            errorLabel.setVisible(false);

            // HTTP call di background thread
            new Thread(() -> {
                try {
                    String json = "{\"email\":\"" + email + "\",\"password\":\"" + password + "\"}";
                    HttpRequest request = HttpRequest.newBuilder()
                            .uri(URI.create(BASE_URL + "/login"))
                            .header("Content-Type", "application/json")
                            .POST(HttpRequest.BodyPublishers.ofString(json))
                            .build();

                    HttpResponse<String> response = httpClient.send(request,
                            HttpResponse.BodyHandlers.ofString());

                    Platform.runLater(() -> {
                        if (response.statusCode() == 200) {
                            // Parse response JSON manual
                            String body = response.body();
                            String name = extractJson(body, "name");
                            String roleStr = extractJson(body, "role");

                            // Set session
                            UserSession.login(email, name);

                            // Set role
                            if ("ROLE_ADMIN".equals(roleStr)) {
                                UserSession.setLoggedInRole(UserSession.Role.ADMIN);
                            } else if ("ROLE_PSIKOLOG".equals(roleStr)) {
                                UserSession.setLoggedInRole(UserSession.Role.PSIKOLOG);
                            } else {
                                UserSession.setLoggedInRole(UserSession.Role.USER);
                            }

                            close();
                            Stage stage = (Stage) mainWindow.getScene().getWindow();
                            if (UserSession.getLoggedInRole() == UserSession.Role.ADMIN) {
                                mainWindow.updateLoginStatus();
                                stage.setScene(new Scene(new AdminDashboardView(mainWindow), 1200, 700));
                            } else if (UserSession.getLoggedInRole() == UserSession.Role.PSIKOLOG) {
                                mainWindow.updateLoginStatus();
                                stage.setScene(new Scene(new PsikologDashboardView(), 1200, 700));
                            } else {
                                mainWindow.updateLoginStatus();
                                mainWindow.switchPage("Beranda");
                            }
                        } else {
                            String body = response.body();
                            String errMsg = extractJson(body, "error");
                            errorLabel.setText(errMsg != null ? errMsg : "Login gagal!");
                            errorLabel.setVisible(true);
                            btnLogin.setDisable(false);
                            btnLogin.setText("Masuk");
                        }
                    });
                } catch (Exception ex) {
                    Platform.runLater(() -> {
                        errorLabel.setText("Tidak bisa terhubung ke server. Pastikan backend berjalan.");
                        errorLabel.setVisible(true);
                        btnLogin.setDisable(false);
                        btnLogin.setText("Masuk");
                    });
                }
            }).start();
        });

        passField.setOnAction(e -> btnLogin.fire());
        emailField.setOnAction(e -> passField.requestFocus());

        Label registerHint = new Label("Belum punya akun? ");
        registerHint.setStyle("-fx-text-fill: #6B7280; -fx-font-size: 13px;");
        Hyperlink registerLink = new Hyperlink("Daftar sekarang");
        registerLink.setStyle("-fx-text-fill: #10B981; -fx-font-weight: bold; -fx-font-size: 13px;");
        registerLink.setOnAction(e -> {
            close();
            RegisterDialog registerDialog = new RegisterDialog(mainWindow);
            registerDialog.showAndWait();
        });
        HBox registerBox = new HBox(2, registerHint, registerLink);
        registerBox.setAlignment(Pos.CENTER);

        root.getChildren().addAll(headerBox, formBox, btnLogin, registerBox);

        Scene scene = new Scene(root);
        setScene(scene);
    }

    // Helper parse JSON string sederhana
    private String extractJson(String json, String key) {
        String search = "\"" + key + "\":\"";
        int start = json.indexOf(search);
        if (start == -1) return null;
        start += search.length();
        int end = json.indexOf("\"", start);
        if (end == -1) return null;
        return json.substring(start, end);
    }
}
