package com.CareVibe.view;

import com.CareVibe.model.UserSession;
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

import java.util.HashMap;
import java.util.Map;

public class LoginDialog {

    private static Map<String, String> registeredUsers = new HashMap<>();
    private static Map<String, String> userNames = new HashMap<>();
    private MainWindow mainWindow;
    private Stage dialogStage;
    private String prefilledEmail = "";
    private String prefilledPassword = "";

    static {
        registeredUsers.put("user@example.com", "password123");
        userNames.put("user@example.com", "User Example");
        registeredUsers.put("test@vibecare.com", "test123");
        userNames.put("test@vibecare.com", "Test User");
    }

    public LoginDialog(MainWindow mainWindow) {
        this(mainWindow, "", "");
    }

    public LoginDialog(MainWindow mainWindow, String email, String password) {
        this.mainWindow = mainWindow;
        this.prefilledEmail = email;
        this.prefilledPassword = password;
        showLoginDialog();
    }

    private void showLoginDialog() {
        dialogStage = new Stage();
        dialogStage.initModality(Modality.APPLICATION_MODAL);
        dialogStage.initStyle(StageStyle.UNDECORATED);
        dialogStage.setTitle("Masuk ke VibeCare");

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

        VBox content = new VBox(20);
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

        Label title = new Label("Masuk ke Akun");
        title.setFont(Font.font("System", FontWeight.BOLD, 20));
        title.setStyle("-fx-text-fill: #1F2937;");

        // Email
        VBox emailBox = new VBox(5);
        Label emailLabel = new Label("Email");
        emailLabel.setStyle("-fx-text-fill: #4B5563; -fx-font-size: 13px; -fx-font-weight: bold;");
        TextField emailField = new TextField();
        emailField.setPromptText("email@contoh.com");
        emailField.setStyle("-fx-background-color: white; -fx-background-radius: 12; -fx-border-color: #E5E7EB; -fx-border-radius: 12; -fx-padding: 12 15 12 15; -fx-font-size: 14px;");
        if (!prefilledEmail.isEmpty()) emailField.setText(prefilledEmail);
        emailBox.getChildren().addAll(emailLabel, emailField);

        // Password
        VBox passBox = new VBox(5);
        Label passLabel = new Label("Password");
        passLabel.setStyle("-fx-text-fill: #4B5563; -fx-font-size: 13px; -fx-font-weight: bold;");
        PasswordField passField = new PasswordField();
        passField.setPromptText("••••••••");
        passField.setStyle("-fx-background-color: white; -fx-background-radius: 12; -fx-border-color: #E5E7EB; -fx-border-radius: 12; -fx-padding: 12 15 12 15; -fx-font-size: 14px;");
        if (!prefilledPassword.isEmpty()) passField.setText(prefilledPassword);
        passBox.getChildren().addAll(passLabel, passField);

        Button loginBtn = new Button("Masuk");
        loginBtn.setStyle("-fx-background-color: #10B981; -fx-text-fill: white; -fx-font-weight: bold; -fx-font-size: 14px; -fx-padding: 12 20 12 20; -fx-background-radius: 30; -fx-cursor: hand;");
        loginBtn.setMaxWidth(Double.MAX_VALUE);
        loginBtn.setOnMouseEntered(e -> loginBtn.setStyle("-fx-background-color: #059669; -fx-text-fill: white; -fx-font-weight: bold; -fx-font-size: 14px; -fx-padding: 12 20 12 20; -fx-background-radius: 30; -fx-cursor: hand;"));
        loginBtn.setOnMouseExited(e -> loginBtn.setStyle("-fx-background-color: #10B981; -fx-text-fill: white; -fx-font-weight: bold; -fx-font-size: 14px; -fx-padding: 12 20 12 20; -fx-background-radius: 30; -fx-cursor: hand;"));

        HBox registerLink = new HBox(5);
        registerLink.setAlignment(Pos.CENTER);
        Label noAccountLabel = new Label("Belum punya akun?");
        noAccountLabel.setStyle("-fx-text-fill: #6B7280; -fx-font-size: 13px;");
        Hyperlink registerHyperlink = new Hyperlink("Daftar di sini");
        registerHyperlink.setStyle("-fx-text-fill: #10B981; -fx-font-size: 13px; -fx-underline: true; -fx-cursor: hand;");
        registerHyperlink.setOnAction(e -> { dialogStage.close(); new RegisterDialog(mainWindow); });
        registerLink.getChildren().addAll(noAccountLabel, registerHyperlink);

        Label errorLabel = new Label();
        errorLabel.setStyle("-fx-text-fill: #EF4444; -fx-font-size: 12px;");
        errorLabel.setVisible(false);

        loginBtn.setOnAction(e -> {
            String email = emailField.getText().trim();
            String password = passField.getText();
            if (email.isEmpty() || password.isEmpty()) {
                errorLabel.setText("Email dan password harus diisi!");
                errorLabel.setVisible(true);
                return;
            }
            if (registeredUsers.containsKey(email) && registeredUsers.get(email).equals(password)) {
                String userName = userNames.getOrDefault(email, email.split("@")[0]);
                UserSession.login(email, userName);
                UserSession.addPoints(10);
                dialogStage.close();
                mainWindow.updateLoginStatus();
                mainWindow.refreshPoints();
                showSuccessDialog(userName, email);
            } else {
                errorLabel.setText("Email atau password salah!");
                errorLabel.setVisible(true);
            }
        });

        content.getChildren().addAll(logoBox, title, emailBox, passBox, loginBtn, registerLink, errorLabel);
        scrollPane.setContent(content);
        root.getChildren().addAll(headerBar, scrollPane);

        Scene scene = new Scene(root, 450, 580);
        dialogStage.setScene(scene);
        dialogStage.showAndWait();
    }

    private void showSuccessDialog(String name, String email) {
        Dialog<ButtonType> dialog = new Dialog<>();
        dialog.setTitle("Login Berhasil");
        dialog.initModality(Modality.APPLICATION_MODAL);
        dialog.initStyle(StageStyle.UNDECORATED);

        VBox content = new VBox(20);
        content.setAlignment(Pos.CENTER);
        content.setPadding(new Insets(30, 35, 35, 35));
        content.setStyle("-fx-background-color: #F0FDF4;");

        StackPane iconContainer = new StackPane();
        Circle circleBg = new Circle(50);
        circleBg.setFill(Color.web("#10B98120"));
        Circle circleInner = new Circle(38);
        circleInner.setFill(Color.web("#10B981"));
        Label checkIcon = new Label("✓");
        checkIcon.setFont(Font.font("System", FontWeight.BOLD, 38));
        checkIcon.setStyle("-fx-text-fill: white;");
        iconContainer.getChildren().addAll(circleBg, circleInner, checkIcon);

        Label titleLabel = new Label("Login Berhasil!");
        titleLabel.setFont(Font.font("System", FontWeight.BOLD, 22));
        titleLabel.setStyle("-fx-text-fill: #065F46;");
        Label subtitleLabel = new Label("Selamat datang kembali,");
        subtitleLabel.setStyle("-fx-text-fill: #6B7280; -fx-font-size: 14px;");
        Label nameLabel = new Label(name + "!");
        nameLabel.setFont(Font.font("System", FontWeight.BOLD, 18));
        nameLabel.setStyle("-fx-text-fill: #1F2937;");

        Separator sep = new Separator();
        sep.setStyle("-fx-background-color: #E5E7EB;");
        sep.setMaxWidth(300);

        VBox detailCard = new VBox(12);
        detailCard.setStyle("-fx-background-color: white; -fx-background-radius: 16; -fx-effect: dropshadow(gaussian, rgba(0,0,0,0.05), 5, 0, 0, 2);");
        detailCard.setPadding(new Insets(16));
        detailCard.setMaxWidth(350);
        detailCard.getChildren().add(createDetailRow("📧 Email", email));
        Separator detailSep = new Separator();
        detailSep.setStyle("-fx-background-color: #F3F4F6;");
        detailCard.getChildren().add(detailSep);
        HBox bonusBox = new HBox(8);
        bonusBox.setAlignment(Pos.CENTER_LEFT);
        Label bonusIcon = new Label("✨");
        bonusIcon.setStyle("-fx-font-size: 16px;");
        Label bonusText = new Label("+10 Poin telah ditambahkan ke akun Anda!");
        bonusText.setStyle("-fx-text-fill: #F59E0B; -fx-font-size: 13px; -fx-font-weight: bold;");
        bonusBox.getChildren().addAll(bonusIcon, bonusText);
        detailCard.getChildren().add(bonusBox);

        Label welcomeLabel = new Label("Nikmati layanan kesehatan mental terbaik bersama VibeCare!");
        welcomeLabel.setStyle("-fx-text-fill: #6B7280; -fx-font-size: 13px;");
        welcomeLabel.setWrapText(true);
        welcomeLabel.setAlignment(Pos.CENTER);

        Button okButton = new Button("Mulai Sekarang");
        okButton.setStyle("-fx-background-color: #10B981; -fx-text-fill: white; -fx-font-weight: bold; -fx-font-size: 14px; -fx-padding: 12 30 12 30; -fx-background-radius: 30; -fx-cursor: hand;");
        okButton.setMaxWidth(250);
        okButton.setOnMouseEntered(e -> okButton.setStyle("-fx-background-color: #059669; -fx-text-fill: white; -fx-font-weight: bold; -fx-font-size: 14px; -fx-padding: 12 30 12 30; -fx-background-radius: 30; -fx-cursor: hand;"));
        okButton.setOnMouseExited(e -> okButton.setStyle("-fx-background-color: #10B981; -fx-text-fill: white; -fx-font-weight: bold; -fx-font-size: 14px; -fx-padding: 12 30 12 30; -fx-background-radius: 30; -fx-cursor: hand;"));
        okButton.setOnAction(e -> dialog.close());

        content.getChildren().addAll(iconContainer, titleLabel, subtitleLabel, nameLabel, sep, detailCard, welcomeLabel, okButton);
        dialog.getDialogPane().setContent(content);
        dialog.getDialogPane().setPrefWidth(450);
        dialog.getDialogPane().setStyle("-fx-background-color: #F0FDF4;");
        dialog.getDialogPane().getButtonTypes().add(ButtonType.CLOSE);
        Button closeButton = (Button) dialog.getDialogPane().lookupButton(ButtonType.CLOSE);
        closeButton.setVisible(false);
        dialog.showAndWait();
    }

    private HBox createDetailRow(String label, String value) {
        HBox row = new HBox(10);
        row.setAlignment(Pos.CENTER_LEFT);
        Label labelLbl = new Label(label);
        labelLbl.setStyle("-fx-text-fill: #6B7280; -fx-font-size: 13px;");
        Region spacer = new Region();
        HBox.setHgrow(spacer, Priority.ALWAYS);
        Label valueLbl = new Label(value);
        valueLbl.setStyle("-fx-text-fill: #1F2937; -fx-font-size: 13px; -fx-font-weight: bold;");
        row.getChildren().addAll(labelLbl, spacer, valueLbl);
        return row;
    }

    public static boolean registerUser(String name, String email, String password) {
        if (registeredUsers.containsKey(email)) return false;
        registeredUsers.put(email, password);
        userNames.put(email, name);
        return true;
    }
}