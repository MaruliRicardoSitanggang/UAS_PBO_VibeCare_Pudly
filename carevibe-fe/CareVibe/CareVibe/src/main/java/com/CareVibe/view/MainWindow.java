package com.CareVibe.view;

import com.CareVibe.model.UserSession;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.*;
import java.util.HashMap;
import java.util.Map;

public class MainWindow extends BorderPane {
    private Label lblPoin;
    private Button btnMasuk;
    private Map<String, Button> navButtons = new HashMap<>();

    public MainWindow() {
        this.setStyle("-fx-background-color: #E2F5E9; -fx-font-family: 'Segoe UI', sans-serif;");

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
        // PERBAIKAN: Hapus .showAndWait() karena LoginDialog bukan Dialog
        btnMasuk.setOnAction(e -> new LoginDialog(this));

        Button btnDaftar = new Button("Daftar");
        btnDaftar.getStyleClass().add("btn-outline");
        btnDaftar.setOnAction(e -> new RegisterDialog(this));

        HBox authBox = new HBox(15, lblPoin, btnMasuk, btnDaftar);
        authBox.setAlignment(Pos.CENTER_RIGHT);

        navbar.getChildren().addAll(logo, s1, menuBox, s2, authBox);
        this.setTop(navbar);

        UserSession.setOnPointsChanged(() -> lblPoin.setText(UserSession.getPoints() + " Poin"));

        // Update tampilan jika user sudah login
        updateLoginStatus();

        switchPage("Beranda");
    }

    public void switchPage(String pageName) {
        navButtons.values().forEach(b -> b.getStyleClass().remove("nav-link-active"));
        if (navButtons.containsKey(pageName)) {
            navButtons.get(pageName).getStyleClass().add("nav-link-active");
        }

        switch (pageName) {
            case "Beranda" -> this.setCenter(new DashboardView(this));
            case "Tes Kesehatan" -> this.setCenter(new MentalTestView(this));
            case "Komunitas" -> this.setCenter(new CommunityView());
            case "Konsultasi" -> this.setCenter(new ConsultationView());
            case "Meditasi" -> this.setCenter(new MeditationView());
            case "Misi Harian" -> this.setCenter(new DailyMissionView());
        }
    }

    public void updateLoginStatus() {
        if (UserSession.getLoggedInUser() != null) {
            String displayName = UserSession.getLoggedInName();
            if (displayName == null || displayName.isEmpty()) {
                displayName = UserSession.getLoggedInUser();
            }
            btnMasuk.setText("👤 " + displayName);
            btnMasuk.setDisable(true);
        } else {
            btnMasuk.setText("Masuk");
            btnMasuk.setDisable(false);
        }
    }

    public void showMentalTestDetail(String testId, String testTitle) {
        System.out.println("Navigasi ke: " + testTitle + " (ID: " + testId + ")");
    }

    // Method untuk refresh poin di navbar
    public void refreshPoints() {
        lblPoin.setText(UserSession.getPoints() + " Poin");
    }
}