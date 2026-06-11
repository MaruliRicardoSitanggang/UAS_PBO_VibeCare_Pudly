package com.CareVibe.view;

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

public class RegisterDialog {

    private final MainWindow mainWindow;
    private Stage dialogStage;

    public RegisterDialog(MainWindow mainWindow) {
        this.mainWindow = mainWindow;
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

        // Nama Lengkap
        VBox nameBox = new VBox(5);
        Label nameLabel = new Label("Nama Lengkap");
        nameLabel.setStyle("-fx-text-fill: #4B5563; -fx-font-size: 13px; -fx-font-weight: bold;");
        TextField nameField = new TextField();
        nameField.setPromptText("Nama lengkap");
        nameField.setStyle("-fx-background-color: white; -fx-background-radius: 12; -fx-border-color: #E5E7EB; -fx-border-radius: 12; -fx-padding: 12 15 12 15; -fx-font-size: 14px;");
        nameBox.getChildren().addAll(nameLabel, nameField);

        // Email
        VBox emailBox = new VBox(5);
        Label emailLabel = new Label("Email");
        emailLabel.setStyle("-fx-text-fill: #4B5563; -fx-font-size: 13px; -fx-font-weight: bold;");
        TextField emailField = new TextField();
        emailField.setPromptText("email@contoh.com");
        emailField.setStyle("-fx-background-color: white; -fx-background-radius: 12; -fx-border-color: #E5E7EB; -fx-border-radius: 12; -fx-padding: 12 15 12 15; -fx-font-size: 14px;");
        emailBox.getChildren().addAll(emailLabel, emailField);

        // Password
        VBox passBox = new VBox(5);
        Label passLabel = new Label("Password");
        passLabel.setStyle("-fx-text-fill: #4B5563; -fx-font-size: 13px; -fx-font-weight: bold;");
        PasswordField passField = new PasswordField();
        passField.setPromptText("Password (minimal 5 karakter)");
        passField.setStyle("-fx-background-color: white; -fx-background-radius: 12; -fx-border-color: #E5E7EB; -fx-border-radius: 12; -fx-padding: 12 15 12 15; -fx-font-size: 14px;");
        passBox.getChildren().addAll(passLabel, passField);

        // Konfirmasi Password
        VBox confirmPassBox = new VBox(5);
        Label confirmPassLabel = new Label("Konfirmasi Password");
        confirmPassLabel.setStyle("-fx-text-fill: #4B5563; -fx-font-size: 13px; -fx-font-weight: bold;");
        PasswordField confirmPassField = new PasswordField();
        confirmPassField.setPromptText("Ulangi password");
        confirmPassField.setStyle("-fx-background-color: white; -fx-background-radius: 12; -fx-border-color: #E5E7EB; -fx-border-radius: 12; -fx-padding: 12 15 12 15; -fx-font-size: 14px;");
        confirmPassBox.getChildren().addAll(confirmPassLabel, confirmPassField);

        Button registerBtn = new Button("Daftar");
        registerBtn.setStyle("-fx-background-color: #10B981; -fx-text-fill: white; -fx-font-weight: bold; -fx-font-size: 14px; -fx-padding: 12 20 12 20; -fx-background-radius: 30; -fx-cursor: hand;");
        registerBtn.setMaxWidth(Double.MAX_VALUE);
        registerBtn.setOnMouseEntered(e -> registerBtn.setStyle("-fx-background-color: #059669; -fx-text-fill: white; -fx-font-weight: bold; -fx-font-size: 14px; -fx-padding: 12 20 12 20; -fx-background-radius: 30; -fx-cursor: hand;"));
        registerBtn.setOnMouseExited(e -> registerBtn.setStyle("-fx-background-color: #10B981; -fx-text-fill: white; -fx-font-weight: bold; -fx-font-size: 14px; -fx-padding: 12 20 12 20; -fx-background-radius: 30; -fx-cursor: hand;"));

        HBox loginLink = new HBox(5);
        loginLink.setAlignment(Pos.CENTER);
        Label haveAccountLabel = new Label("Sudah punya akun?");
        haveAccountLabel.setStyle("-fx-text-fill: #6B7280; -fx-font-size: 13px;");
        Hyperlink loginHyperlink = new Hyperlink("Masuk di sini");
        loginHyperlink.setStyle("-fx-text-fill: #10B981; -fx-font-size: 13px; -fx-underline: true; -fx-cursor: hand;");
        loginHyperlink.setOnAction(e -> { dialogStage.close(); new LoginDialog(mainWindow); });
        loginLink.getChildren().addAll(haveAccountLabel, loginHyperlink);

        Label errorLabel = new Label();
        errorLabel.setStyle("-fx-text-fill: #EF4444; -fx-font-size: 12px;");
        errorLabel.setVisible(false);
        errorLabel.setWrapText(true);

        registerBtn.setOnAction(e -> {
            String name = nameField.getText().trim();
            String email = emailField.getText().trim();
            String password = passField.getText();
            String confirmPassword = confirmPassField.getText();

            if (name.isEmpty()) { errorLabel.setText("Nama lengkap harus diisi!"); errorLabel.setVisible(true); return; }
            if (email.isEmpty()) { errorLabel.setText("Email harus diisi!"); errorLabel.setVisible(true); return; }
            if (!email.contains("@") || !email.contains(".")) { errorLabel.setText("Email tidak valid!"); errorLabel.setVisible(true); return; }
            if (password.isEmpty()) { errorLabel.setText("Password harus diisi!"); errorLabel.setVisible(true); return; }
            if (password.length() < 5) { errorLabel.setText("Password minimal 5 karakter!"); errorLabel.setVisible(true); return; }
            if (!password.equals(confirmPassword)) { errorLabel.setText("Konfirmasi password tidak cocok!"); errorLabel.setVisible(true); return; }

            if (LoginDialog.registerUser(name, email, password)) {
                dialogStage.close();
                showSuccessDialog(name, email, password);
            } else {
                errorLabel.setText("Email sudah terdaftar! Gunakan email lain.");
                errorLabel.setVisible(true);
            }
        });

        content.getChildren().addAll(logoBox, title, nameBox, emailBox, passBox, confirmPassBox, registerBtn, loginLink, errorLabel);
        scrollPane.setContent(content);
        root.getChildren().addAll(headerBar, scrollPane);

        Scene scene = new Scene(root, 480, 620);
        dialogStage.setScene(scene);
        dialogStage.showAndWait();
    }

    private void showSuccessDialog(String name, String email, String password) {
        Dialog<ButtonType> dialog = new Dialog<>();
        dialog.setTitle("Registrasi Berhasil");
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

        Label titleLabel = new Label("Registrasi Berhasil!");
        titleLabel.setFont(Font.font("System", FontWeight.BOLD, 22));
        titleLabel.setStyle("-fx-text-fill: #065F46;");
        Label subtitleLabel = new Label("Selamat, " + name + "!");
        subtitleLabel.setFont(Font.font("System", FontWeight.BOLD, 16));
        subtitleLabel.setStyle("-fx-text-fill: #1F2937;");

        Separator sep = new Separator();
        sep.setStyle("-fx-background-color: #E5E7EB;");
        sep.setMaxWidth(300);

        VBox detailCard = new VBox(12);
        detailCard.setStyle("-fx-background-color: white; -fx-background-radius: 16; -fx-effect: dropshadow(gaussian, rgba(0,0,0,0.05), 5, 0, 0, 2);");
        detailCard.setPadding(new Insets(16));
        detailCard.setMaxWidth(350);
        detailCard.getChildren().add(createDetailRow("Email", email));
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

        Label instructionLabel = new Label("Silakan login untuk menikmati layanan VibeCare.");
        instructionLabel.setStyle("-fx-text-fill: #6B7280; -fx-font-size: 13px;");
        instructionLabel.setWrapText(true);
        instructionLabel.setAlignment(Pos.CENTER);

        HBox buttonContainer = new HBox(15);
        buttonContainer.setAlignment(Pos.CENTER);
        buttonContainer.setPadding(new Insets(10, 0, 0, 0));

        Button loginNowBtn = new Button("Login Sekarang");
        loginNowBtn.setStyle("-fx-background-color: #10B981; -fx-text-fill: white; -fx-font-weight: bold; -fx-font-size: 14px; -fx-padding: 12 25 12 25; -fx-background-radius: 30; -fx-cursor: hand;");
        loginNowBtn.setOnMouseEntered(e -> loginNowBtn.setStyle("-fx-background-color: #059669; -fx-text-fill: white; -fx-font-weight: bold; -fx-font-size: 14px; -fx-padding: 12 25 12 25; -fx-background-radius: 30; -fx-cursor: hand;"));
        loginNowBtn.setOnMouseExited(e -> loginNowBtn.setStyle("-fx-background-color: #10B981; -fx-text-fill: white; -fx-font-weight: bold; -fx-font-size: 14px; -fx-padding: 12 25 12 25; -fx-background-radius: 30; -fx-cursor: hand;"));
        loginNowBtn.setOnAction(e -> { dialog.close(); new LoginDialog(mainWindow, email, password); });

        Button laterBtn = new Button("Nanti Saja");
        laterBtn.setStyle("-fx-background-color: transparent; -fx-text-fill: #6B7280; -fx-font-weight: bold; -fx-font-size: 14px; -fx-padding: 12 25 12 25; -fx-background-radius: 30; -fx-border-color: #D1D5DB; -fx-border-width: 1; -fx-cursor: hand;");
        laterBtn.setOnMouseEntered(e -> laterBtn.setStyle("-fx-background-color: #F3F4F6; -fx-text-fill: #4B5563; -fx-font-weight: bold; -fx-font-size: 14px; -fx-padding: 12 25 12 25; -fx-background-radius: 30; -fx-border-color: #D1D5DB; -fx-border-width: 1; -fx-cursor: hand;"));
        laterBtn.setOnMouseExited(e -> laterBtn.setStyle("-fx-background-color: transparent; -fx-text-fill: #6B7280; -fx-font-weight: bold; -fx-font-size: 14px; -fx-padding: 12 25 12 25; -fx-background-radius: 30; -fx-border-color: #D1D5DB; -fx-border-width: 1; -fx-cursor: hand;"));
        laterBtn.setOnAction(e -> dialog.close());

        buttonContainer.getChildren().addAll(loginNowBtn, laterBtn);
        content.getChildren().addAll(iconContainer, titleLabel, subtitleLabel, sep, detailCard, instructionLabel, buttonContainer);

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
}