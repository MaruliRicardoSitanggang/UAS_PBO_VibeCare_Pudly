package com.CareVibe.view;

import com.CareVibe.model.DailyMission;
import com.CareVibe.model.UserSession;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.*;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

import java.util.ArrayList;
import java.util.List;

public class DailyMissionView extends VBox {

    private List<DailyMission> missions;
    private VBox missionContainer;

    public DailyMissionView() {
        this.setStyle("-fx-background-color: #F0FDF4; -fx-padding: 30;");
        this.setSpacing(25);

        ScrollPane scrollPane = new ScrollPane();
        scrollPane.setFitToWidth(true);
        scrollPane.setStyle("-fx-background-color: transparent; -fx-background: transparent;");
        scrollPane.setVbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED);

        VBox content = new VBox(20);
        content.setPadding(new Insets(0, 0, 20, 0));
        content.setAlignment(Pos.TOP_LEFT);

        content.getChildren().addAll(createHeaderSection(), createPoinCard(), createMissionSection());
        scrollPane.setContent(content);
        this.getChildren().add(scrollPane);
    }

    private VBox createHeaderSection() {
        VBox header = new VBox(10);
        header.setAlignment(Pos.TOP_LEFT);

        Label title = new Label("Misi Harian & Poin");
        title.setFont(Font.font("System", FontWeight.BOLD, 28));
        title.setStyle("-fx-text-fill: #065F46;");

        Label subtitle = new Label("Selesaikan misi harian dan login setiap hari untuk mendapatkan poin yang dapat ditukarkan dengan konsultasi gratis.");
        subtitle.setWrapText(true);
        subtitle.setStyle("-fx-text-fill: #4B5563; -fx-font-size: 14px;");

        header.getChildren().addAll(title, subtitle);
        return header;
    }

    private VBox createPoinCard() {
        VBox card = new VBox(10);
        card.setStyle("-fx-background-color: linear-gradient(to right, #10B981, #059669); -fx-background-radius: 16;");
        card.setPadding(new Insets(20));
        card.setMaxWidth(Double.MAX_VALUE);

        Label lblTitle = new Label("Total Poin Anda");
        lblTitle.setStyle("-fx-text-fill: white; -fx-font-size: 14px;");

        Label lblPoints = new Label(String.valueOf(UserSession.getPoints()));
        lblPoints.setFont(Font.font("System", FontWeight.BOLD, 36));
        lblPoints.setStyle("-fx-text-fill: white;");

        Label lblInfo = new Label("Terus kumpulkan poin untuk konsultasi gratis!");
        lblInfo.setStyle("-fx-text-fill: #D1FAE5; -fx-font-size: 12px;");

        UserSession.setOnPointsChanged(() -> {
            lblPoints.setText(String.valueOf(UserSession.getPoints()));
            refreshMissions();
        });

        card.getChildren().addAll(lblTitle, lblPoints, lblInfo);
        return card;
    }

    private VBox createMissionSection() {
        VBox section = new VBox(15);
        section.setAlignment(Pos.TOP_LEFT);

        Label sectionTitle = new Label("Misi Hari Ini");
        sectionTitle.setFont(Font.font("System", FontWeight.BOLD, 20));
        sectionTitle.setStyle("-fx-text-fill: #065F46;");

        missionContainer = new VBox(12);
        missionContainer.setAlignment(Pos.TOP_LEFT);

        initializeMissions();
        refreshMissions();

        section.getChildren().addAll(sectionTitle, missionContainer);
        return section;
    }

    private void initializeMissions() {
        missions = new ArrayList<>();

        // Data Misi
        missions.add(new DailyMission("Login Harian", "Login ke aplikasi setiap hari", 10));
        missions.add(new DailyMission("Tes Kesehatan Mental", "Selesaikan salah satu tes kesehatan mental", 25));
        missions.add(new DailyMission("Berbagi Cerita", "Bagikan cerita atau pengalaman di komunitas", 15));
        missions.add(new DailyMission("Meditasi 10 Menit", "Lakukan meditasi selama minimal 10 menit", 20));

        // Cek status otomatis untuk Login Harian (index 0)
        if (UserSession.isLoggedIn() && !UserSession.hasCompletedMission("mission_0") && !missions.isEmpty()) {
            missions.get(0).setCompleted(true);
            UserSession.setMissionCompleted("mission_0", true);
            UserSession.addPoints(missions.get(0).getRewardPoints());
        }

        loadMissionStatus();
    }

    private void loadMissionStatus() {
        for (int i = 0; i < missions.size(); i++) {
            if (UserSession.hasCompletedMission("mission_" + i)) {
                missions.get(i).setCompleted(true);
            }
        }
    }

    private void saveMissionStatus(int index) {
        UserSession.setMissionCompleted("mission_" + index, true);
    }

    private void refreshMissions() {
        if (missionContainer == null) return;
        missionContainer.getChildren().clear();
        for (int i = 0; i < missions.size(); i++) {
            missionContainer.getChildren().add(createMissionCard(missions.get(i), i));
        }
    }

    private VBox createMissionCard(DailyMission mission, int index) {
        VBox card = new VBox(10);
        card.setStyle("-fx-background-color: white; -fx-background-radius: 16; " +
                "-fx-effect: dropshadow(gaussian, rgba(0,0,0,0.08), 8, 0, 0, 2);");
        card.setPadding(new Insets(18));
        card.setMaxWidth(Double.MAX_VALUE);

        // Header
        HBox headerBox = new HBox(10);
        headerBox.setAlignment(Pos.CENTER_LEFT);

        Label lblMissionName = new Label(mission.getMissionName());
        lblMissionName.setFont(Font.font("System", FontWeight.BOLD, 16));
        lblMissionName.setStyle("-fx-text-fill: #1F2937;");

        Region spacer1 = new Region();
        HBox.setHgrow(spacer1, Priority.ALWAYS);

        Label lblPoints = new Label("+" + mission.getRewardPoints() + " Poin");
        lblPoints.setStyle(
                "-fx-background-color: #FEF3C7; -fx-text-fill: #D97706; -fx-font-size: 12px;" +
                        "-fx-font-weight: bold; -fx-padding: 4 10 4 10; -fx-background-radius: 20;"
        );
        headerBox.getChildren().addAll(lblMissionName, spacer1, lblPoints);

        // Deskripsi
        Label lblDescription = new Label(mission.getDescription());
        lblDescription.setStyle("-fx-text-fill: #6B7280; -fx-font-size: 13px;");

        // Footer
        HBox footerBox = new HBox(15);
        footerBox.setAlignment(Pos.CENTER_LEFT);
        footerBox.setPadding(new Insets(10, 0, 0, 0));

        Label lblStatus = new Label(mission.isCompleted() ? "✓ Selesai" : "○ Belum Selesai");
        lblStatus.setStyle(mission.isCompleted()
                ? "-fx-text-fill: #10B981; -fx-font-size: 13px; -fx-font-weight: bold;"
                : "-fx-text-fill: #EF4444; -fx-font-size: 13px;");

        Region spacer2 = new Region();
        HBox.setHgrow(spacer2, Priority.ALWAYS);

        // index 0 = Login Harian   → otomatis, tombol non-aktif
        // index 1 = Tes Kesehatan  → harus dari halaman Tes, tombol "Kunjungi"
        // index 2 = Berbagi Cerita → harus dari Komunitas, tombol "Kunjungi"
        // index 3 = Meditasi       → bisa tandai manual
        boolean canManualComplete = (index == 3);
        boolean isAutoMission     = (index == 0);

        String btnLabel;
        if (mission.isCompleted())      btnLabel = "Selesai";
        else if (isAutoMission)         btnLabel = "Otomatis";
        else if (canManualComplete)     btnLabel = "Tandai Selesai";
        else                            btnLabel = "Kunjungi";

        Button btnAction = new Button(btnLabel);
        btnAction.setStyle(mission.isCompleted() || isAutoMission
                ? "-fx-background-color: #D1FAE5; -fx-text-fill: #059669; -fx-font-size: 12px; -fx-font-weight: bold; -fx-padding: 8 20 8 20; -fx-background-radius: 30; -fx-cursor: hand;"
                : "-fx-background-color: #10B981; -fx-text-fill: white; -fx-font-size: 12px; -fx-font-weight: bold; -fx-padding: 8 20 8 20; -fx-background-radius: 30; -fx-cursor: hand;"
        );

        if (!mission.isCompleted() && !isAutoMission) {
            btnAction.setOnMouseEntered(e ->
                    btnAction.setStyle("-fx-background-color: #059669; -fx-text-fill: white; -fx-font-size: 12px; -fx-font-weight: bold; -fx-padding: 8 20 8 20; -fx-background-radius: 30; -fx-cursor: hand;")
            );
            btnAction.setOnMouseExited(e ->
                    btnAction.setStyle("-fx-background-color: #10B981; -fx-text-fill: white; -fx-font-size: 12px; -fx-font-weight: bold; -fx-padding: 8 20 8 20; -fx-background-radius: 30; -fx-cursor: hand;")
            );

            btnAction.setOnAction(e -> {
                if (canManualComplete) {
                    // Meditasi: tandai selesai langsung
                    mission.setCompleted(true);
                    UserSession.addPoints(mission.getRewardPoints());
                    saveMissionStatus(index);
                    refreshMissions();
                    showMissionCompleteDialog(mission.getMissionName(), mission.getRewardPoints());
                } else {
                    // Tes / Komunitas: tampilkan petunjuk
                    showNavigationHint(index);
                }
            });
        }

        footerBox.getChildren().addAll(lblStatus, spacer2, btnAction);
        card.getChildren().addAll(headerBox, lblDescription, footerBox);
        return card;
    }

    // Dipanggil dari luar (MentalTestView, CommunityView) untuk selesaikan misi otomatis
    public static void completeMissionExternally(int index) {
        String key = "mission_" + index;
        if (!UserSession.hasCompletedMission(key)) {
            UserSession.setMissionCompleted(key, true);
            // Poin ditambah sesuai reward masing-masing misi
            int reward = switch (index) {
                case 1 -> 25; // Tes Kesehatan Mental
                case 2 -> 15; // Berbagi Cerita
                default -> 0;
            };
            if (reward > 0) UserSession.addPoints(reward);
        }
    }

    private void showNavigationHint(int index) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Cara Menyelesaikan Misi");
        switch (index) {
            case 1 -> {
                alert.setHeaderText("Tes Kesehatan Mental");
                alert.setContentText("Pergi ke menu \"Tes Kesehatan\" dan selesaikan salah satu tes untuk mendapatkan +25 poin.");
            }
            case 2 -> {
                alert.setHeaderText("Berbagi Cerita");
                alert.setContentText("Pergi ke menu \"Komunitas\" dan buat postingan untuk mendapatkan +15 poin.");
            }
            default -> {
                alert.setHeaderText("Informasi");
                alert.setContentText("Selesaikan misi untuk mendapatkan poin!");
            }
        }
        alert.showAndWait();
    }

    private void showMissionCompleteDialog(String missionName, int points) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Misi Selesai!");
        alert.setHeaderText("Selamat! Anda menyelesaikan misi:");
        alert.setContentText(missionName + "\n\n+" + points + " Poin telah ditambahkan!\n\nTotal poin Anda sekarang: " + UserSession.getPoints());
        alert.showAndWait();
    }
}