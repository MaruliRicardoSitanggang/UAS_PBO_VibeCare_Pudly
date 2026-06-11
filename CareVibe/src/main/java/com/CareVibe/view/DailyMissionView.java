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

        // Header Section
        VBox headerBox = createHeaderSection();

        // Total Poin Card
        VBox poinCard = createPoinCard();

        // Mission Section
        VBox missionSection = createMissionSection();

        content.getChildren().addAll(headerBox, poinCard, missionSection);
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

        // Update poin secara real-time
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

        // Inisialisasi misi
        initializeMissions();
        refreshMissions();

        section.getChildren().addAll(sectionTitle, missionContainer);
        return section;
    }

    private void initializeMissions() {
        missions = new ArrayList<>();
        missions.add(new DailyMission("Login Harian", "Login ke aplikasi setiap hari", 10));
        missions.add(new DailyMission("Tes Kesehatan Mental", "Selesaikan salah satu tes kesehatan mental", 25));
        missions.add(new DailyMission("Berbagi Cerita", "Bagikan cerita atau pengalaman di komunitas", 15));
        missions.add(new DailyMission("Meditasi 10 Menit", "Lakukan meditasi selama minimal 10 menit", 20));

        // Load saved completion status dari UserSession (jika ada)
        loadMissionStatus();
    }

    private void loadMissionStatus() {
        // Cek status misi dari session (bisa disimpan di UserSession)
        for (int i = 0; i < missions.size(); i++) {
            String key = "mission_" + i;
            if (UserSession.hasCompletedMission(key)) {
                missions.get(i).setCompleted(true);
            }
        }
    }

    private void saveMissionStatus(int index) {
        String key = "mission_" + index;
        UserSession.setMissionCompleted(key, true);
    }

    private void refreshMissions() {
        if (missionContainer == null) return;
        missionContainer.getChildren().clear();

        for (int i = 0; i < missions.size(); i++) {
            DailyMission mission = missions.get(i);
            VBox missionCard = createMissionCard(mission, i);
            missionContainer.getChildren().add(missionCard);
        }
    }

    private VBox createMissionCard(DailyMission mission, int index) {
        VBox card = new VBox(10);
        card.setStyle("-fx-background-color: white; -fx-background-radius: 16; -fx-effect: dropshadow(gaussian, rgba(0,0,0,0.08), 8, 0, 0, 2);");
        card.setPadding(new Insets(18));
        card.setMaxWidth(Double.MAX_VALUE);

        // Header: Nama misi dan poin
        HBox headerBox = new HBox(10);
        headerBox.setAlignment(Pos.CENTER_LEFT);

        Label lblMissionName = new Label(mission.getMissionName());
        lblMissionName.setFont(Font.font("System", FontWeight.BOLD, 16));
        lblMissionName.setStyle("-fx-text-fill: #1F2937;");

        Region spacer1 = new Region();
        HBox.setHgrow(spacer1, Priority.ALWAYS);

        Label lblPoints = new Label("+" + mission.getRewardPoints() + " Poin");
        lblPoints.setStyle(
                "-fx-background-color: #FEF3C7;" +
                        "-fx-text-fill: #D97706;" +
                        "-fx-font-size: 12px;" +
                        "-fx-font-weight: bold;" +
                        "-fx-padding: 4 10 4 10;" +
                        "-fx-background-radius: 20;"
        );

        headerBox.getChildren().addAll(lblMissionName, spacer1, lblPoints);

        // Deskripsi misi
        Label lblDescription = new Label(mission.getDescription());
        lblDescription.setStyle("-fx-text-fill: #6B7280; -fx-font-size: 13px;");

        // Status dan tombol
        HBox footerBox = new HBox(15);
        footerBox.setAlignment(Pos.CENTER_LEFT);
        footerBox.setPadding(new Insets(10, 0, 0, 0));

        Label lblStatus = new Label(mission.isCompleted() ? "✓ Selesai" : "○ Belum Selesai");
        lblStatus.setStyle(
                mission.isCompleted() ?
                        "-fx-text-fill: #10B981; -fx-font-size: 13px; -fx-font-weight: bold;" :
                        "-fx-text-fill: #EF4444; -fx-font-size: 13px;"
        );

        Region spacer2 = new Region();
        HBox.setHgrow(spacer2, Priority.ALWAYS);

        Button btnAction = new Button(mission.isCompleted() ? "Selesai" : "Tandai Selesai");
        btnAction.setStyle(
                mission.isCompleted() ?
                        "-fx-background-color: #D1FAE5; -fx-text-fill: #059669; -fx-font-size: 12px; -fx-font-weight: bold; -fx-padding: 8 20 8 20; -fx-background-radius: 30; -fx-cursor: hand;" :
                        "-fx-background-color: #10B981; -fx-text-fill: white; -fx-font-size: 12px; -fx-font-weight: bold; -fx-padding: 8 20 8 20; -fx-background-radius: 30; -fx-cursor: hand;"
        );

        if (!mission.isCompleted()) {
            btnAction.setOnMouseEntered(e ->
                    btnAction.setStyle("-fx-background-color: #059669; -fx-text-fill: white; -fx-font-size: 12px; -fx-font-weight: bold; -fx-padding: 8 20 8 20; -fx-background-radius: 30; -fx-cursor: hand;")
            );
            btnAction.setOnMouseExited(e ->
                    btnAction.setStyle("-fx-background-color: #10B981; -fx-text-fill: white; -fx-font-size: 12px; -fx-font-weight: bold; -fx-padding: 8 20 8 20; -fx-background-radius: 30; -fx-cursor: hand;")
            );
        }

        btnAction.setOnAction(e -> {
            if (!mission.isCompleted()) {
                mission.setCompleted(true);
                UserSession.addPoints(mission.getRewardPoints());
                saveMissionStatus(index);
                refreshMissions();

                // Tampilkan notifikasi
                showMissionCompleteDialog(mission.getMissionName(), mission.getRewardPoints());
            }
        });

        footerBox.getChildren().addAll(lblStatus, spacer2, btnAction);

        card.getChildren().addAll(headerBox, lblDescription, footerBox);
        return card;
    }

    private void showMissionCompleteDialog(String missionName, int points) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Misi Selesai!");
        alert.setHeaderText("Selamat! Anda menyelesaikan misi:");
        alert.setContentText( missionName + "\n\n +" + points + " Poin telah ditambahkan!\n\nTotal poin Anda sekarang: " + UserSession.getPoints());
        alert.showAndWait();
    }
}