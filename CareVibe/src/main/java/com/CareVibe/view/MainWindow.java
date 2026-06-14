package com.CareVibe.view;

import com.CareVibe.model.UserSession;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import java.util.HashMap;
import java.util.Map;

public class MainWindow extends BorderPane {
    private Label lblPoin;
    private Button btnMasuk;
    private Button btnAdmin;
    private Button btnDaftar;
    private Map<String, Button> navButtons = new HashMap<>();

    public MainWindow() {
        this.setStyle("-fx-background-color: #E2F5E9; -fx-font-family: 'Segoe UI', sans-serif;");
        try {
            String css = getClass().getResource("/style.css").toExternalForm();
            this.getStylesheets().add(css);
        } catch (Exception e) {
            System.out.println("style.css tidak ditemukan.");
        }

        HBox navbar = new HBox(15);
        navbar.setPadding(new Insets(20, 50, 20, 50));
        navbar.setAlignment(Pos.CENTER_LEFT);

        Label logo = new Label("VibeCare");
        logo.setStyle("-fx-text-fill: #22C55E; -fx-font-size: 24px; -fx-font-weight: bold;");

        Region s1 = new Region();
        HBox.setHgrow(s1, Priority.ALWAYS);

        String[] menuItems = {"Beranda", "Tes Kesehatan", "Komunitas", "Konsultasi", "Meditasi", "Misi Harian"};
        HBox menuBox = new HBox(10);
        menuBox.setAlignment(Pos.CENTER);

        for (String item : menuItems) {
            Button btn = new Button(item);
            btn.getStyleClass().add("nav-link");
            btn.setOnAction(e -> switchPage(item));
            navButtons.put(item, btn);
            menuBox.getChildren().add(btn);
        }

        Region s2 = new Region();
        HBox.setHgrow(s2, Priority.ALWAYS);

        lblPoin = new Label("0 Poin");
        lblPoin.setStyle("-fx-font-weight: bold;");

        btnMasuk = new Button("Masuk");
        btnMasuk.getStyleClass().add("btn-primary");
        btnMasuk.setOnAction(e -> new LoginDialog(this).show());

        btnDaftar = new Button("Daftar");
        btnDaftar.getStyleClass().add("btn-outline");
        btnDaftar.setOnAction(e -> {
            RegisterDialog registerDialog = new RegisterDialog(this);
            registerDialog.showAndWait();
        });

        btnAdmin = new Button("👑 Admin");
        btnAdmin.getStyleClass().add("btn-outline");
        btnAdmin.setStyle("-fx-background-color: #1E3A5F; -fx-text-fill: white; -fx-font-weight: bold; -fx-padding: 6 12; -fx-background-radius: 20; -fx-cursor: hand;");
        btnAdmin.setOnAction(e -> new AdminLoginView(this));
        btnAdmin.setVisible(false);

        HBox authBox = new HBox(15, lblPoin, btnMasuk, btnDaftar, btnAdmin);
        authBox.setAlignment(Pos.CENTER_RIGHT);

        navbar.getChildren().addAll(logo, s1, menuBox, s2, authBox);
        this.setTop(navbar);

        UserSession.setOnPointsChanged(() -> {
            lblPoin.setText(UserSession.getPoints() + " Poin");
            updateAdminButtonVisibility();
        });

        updateLoginStatus();
        switchPage("Beranda");
    }

    public void switchPage(String pageName) {
        boolean requiresLogin = pageName.equals("Misi Harian")
                || pageName.equals("Konsultasi")
                || pageName.equals("Komunitas")
                || pageName.equals("Meditasi")
                || pageName.equals("Tes Kesehatan")
                || pageName.equals("Profil");

        if (requiresLogin && !UserSession.isLoggedIn()) {
            Dialog<ButtonType> dialog = new Dialog<>();
            dialog.setTitle("Login Diperlukan");
            dialog.setHeaderText(null);

            ButtonType btnMasukType  = new ButtonType("Masuk");
            ButtonType btnDaftarType = new ButtonType("Daftar");
            ButtonType btnBatal      = new ButtonType("Batal", ButtonBar.ButtonData.CANCEL_CLOSE);
            dialog.getDialogPane().getButtonTypes().addAll(btnMasukType, btnDaftarType, btnBatal);

            // Style tombol
            Button masukBtn = (Button) dialog.getDialogPane().lookupButton(btnMasukType);
            masukBtn.setStyle("-fx-background-color: #10B981; -fx-text-fill: white; -fx-font-weight: bold; -fx-font-size: 13px; -fx-background-radius: 20; -fx-padding: 8 20 8 20; -fx-cursor: hand;");

            Button daftarBtn = (Button) dialog.getDialogPane().lookupButton(btnDaftarType);
            daftarBtn.setStyle("-fx-background-color: transparent; -fx-text-fill: #10B981; -fx-font-weight: bold; -fx-font-size: 13px; -fx-background-radius: 20; -fx-padding: 8 20 8 20; -fx-border-color: #10B981; -fx-border-radius: 20; -fx-cursor: hand;");

            Button batalBtn = (Button) dialog.getDialogPane().lookupButton(btnBatal);
            batalBtn.setStyle("-fx-background-color: transparent; -fx-text-fill: #9CA3AF; -fx-font-size: 13px; -fx-background-radius: 20; -fx-padding: 8 20 8 20; -fx-cursor: hand;");

            // Konten dialog
            VBox content = new VBox(16);
            content.setAlignment(Pos.CENTER);
            content.setPadding(new Insets(24, 32, 8, 32));
            content.setStyle("-fx-background-color: white;");

            Label iconLbl = new Label("🔒");
            iconLbl.setStyle("-fx-font-size: 40px;");

            Label titleLbl = new Label("Login Diperlukan");
            titleLbl.setStyle("-fx-font-size: 18px; -fx-font-weight: bold; -fx-text-fill: #065F46;");

            Label msgLbl = new Label("Silakan login atau daftar terlebih dahulu\nuntuk mengakses fitur ini.");
            msgLbl.setStyle("-fx-font-size: 13px; -fx-text-fill: #6B7280;");
            msgLbl.setTextAlignment(javafx.scene.text.TextAlignment.CENTER);
            msgLbl.setAlignment(Pos.CENTER);

            content.getChildren().addAll(iconLbl, titleLbl, msgLbl);

            dialog.getDialogPane().setContent(content);
            dialog.getDialogPane().setStyle("-fx-background-color: white; -fx-background-radius: 16;");
            dialog.getDialogPane().setPrefWidth(360);

            dialog.showAndWait().ifPresent(result -> {
                if (result == btnMasukType) {
                    new LoginDialog(this).show();
                } else if (result == btnDaftarType) {
                    RegisterDialog registerDialog = new RegisterDialog(this);
                    registerDialog.showAndWait();
                }
            });
            return;
        }

        navButtons.values().forEach(b -> b.getStyleClass().remove("nav-link-active"));
        if (navButtons.containsKey(pageName)) {
            navButtons.get(pageName).getStyleClass().add("nav-link-active");
        }

        switch (pageName) {
            case "Beranda"       -> this.setCenter(new DashboardView(this));
            case "Tes Kesehatan" -> this.setCenter(new MentalTestView(this));
            case "Komunitas"     -> this.setCenter(new CommunityView());
            case "Konsultasi"    -> this.setCenter(new ConsultationView());
            case "Meditasi"      -> this.setCenter(new MeditationView());
            case "Misi Harian"   -> this.setCenter(new DailyMissionView());
            case "Profil"        -> this.setCenter(new ProfileView(this));
            default              -> this.setCenter(new DashboardView(this));
        }
    }

    public void updateLoginStatus() {
        if (UserSession.isLoggedIn()) {
            String displayName = UserSession.getLoggedInName();
            if (displayName == null || displayName.isEmpty()) {
                displayName = UserSession.getLoggedInUser();
            }
            btnMasuk.setText("👤 " + displayName);
            btnMasuk.setDisable(false);
            btnMasuk.setOnAction(e -> switchPage("Profil"));
            btnDaftar.setVisible(false);
            btnDaftar.setManaged(false);
        } else {
            btnMasuk.setText("Masuk");
            btnMasuk.setDisable(false);
            btnMasuk.setOnAction(e -> new LoginDialog(this).show());
            btnDaftar.setVisible(true);
            btnDaftar.setManaged(true);
        }
        refreshPoints();
        updateAdminButtonVisibility();
    }

    private void updateAdminButtonVisibility() {
        if (UserSession.isLoggedIn() && UserSession.isAdmin()) {
            btnAdmin.setVisible(true);
            btnAdmin.setManaged(true);
        } else {
            btnAdmin.setVisible(false);
            btnAdmin.setManaged(false);
        }
    }

    public void showMentalTestDetail(String testId, String testTitle) {
        System.out.println("Navigasi ke: " + testTitle + " (ID: " + testId + ")");
    }

    public void refreshPoints() {
        lblPoin.setText(UserSession.getPoints() + " Poin");
    }
}
