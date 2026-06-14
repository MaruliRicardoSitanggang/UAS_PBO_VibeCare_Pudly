package com.CareVibe.view;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.*;

public class DashboardView extends GridPane {

    private Label welcomeLabel;
    private Label missionSummaryLabel;
    private Label meditationSummaryLabel;
    private VBox summaryPanel;

    public DashboardView(MainWindow mainWindow) {
        this.setStyle("-fx-background-color: #F0FDF4; -fx-background-radius: 20 20 0 0; -fx-padding: 40;");
        this.setHgap(40);
        this.setAlignment(Pos.CENTER);

        VBox leftHero = new VBox(20);
        leftHero.setPrefWidth(550);
        leftHero.setAlignment(Pos.CENTER_LEFT);

        // Welcome message (akan di-update oleh controller)
        welcomeLabel = new Label("Halo, Selamat Datang!");
        welcomeLabel.setStyle("-fx-font-size: 28px; -fx-font-weight: 800; -fx-text-fill: #1F2937;");

        Label titleTop = new Label("Kesehatan Mental Anda");
        titleTop.setStyle("-fx-font-size: 36px; -fx-font-weight: 800; -fx-text-fill: #1F2937;");
        Label titleBottom = new Label("Prioritas Kami");
        titleBottom.setStyle("-fx-font-size: 36px; -fx-font-weight: 800; -fx-text-fill: #22C55E;");
        VBox titleBox = new VBox(0, titleTop, titleBottom);

        Label description = new Label("Platform kesehatan mental terpercaya dengan psikolog profesional, " +
                "komunitas yang mendukung, dan berbagai fitur untuk menjaga kesejahteraan mental Anda.");
        description.setWrapText(true);
        description.setStyle("-fx-font-size: 15px; -fx-text-fill: #4B5563; -fx-line-spacing: 5;");

        Button btnBuatJanji = new Button("📅 Buat Janji Konsultasi");
        btnBuatJanji.getStyleClass().add("btn-primary");
        btnBuatJanji.setOnAction(e -> mainWindow.switchPage("Konsultasi"));

        Button btnCariPsikolog = new Button("👤 Cari Psikolog");
        btnCariPsikolog.getStyleClass().add("btn-outline");
        btnCariPsikolog.setOnAction(e -> mainWindow.switchPage("Konsultasi"));

        HBox actionButtons = new HBox(15, btnBuatJanji, btnCariPsikolog);
        leftHero.getChildren().addAll(welcomeLabel, titleBox, description, actionButtons);

        // Right column (tanpa Points Card)
        VBox rightColumn = new VBox(20);
        rightColumn.setPrefWidth(350);

        // Tes Kesehatan Card
        VBox rightCard = new VBox(15);
        rightCard.getStyleClass().add("card-item");
        rightCard.setStyle("-fx-background-color: white; -fx-background-radius: 16; -fx-padding: 20; -fx-effect: dropshadow(gaussian, rgba(0,0,0,0.08), 12, 0, 0, 4);");

        Label iconBrain = new Label("🧠");
        iconBrain.setStyle("-fx-font-size: 24px; -fx-background-color: #DCFCE7; -fx-background-radius: 50; -fx-padding: 5;");
        Label cardTitle = new Label("Tes Kesehatan Mental");
        cardTitle.setStyle("-fx-font-weight: bold; -fx-font-size: 16px;");
        Label cardDesc = new Label("Lakukan pengecekan kesehatan mental Anda dengan tes yang telah divalidasi oleh para ahli.");
        cardDesc.setWrapText(true);

        Button btnMulaiTes = new Button("Mulai Tes Sekarang");
        btnMulaiTes.getStyleClass().add("btn-primary");
        btnMulaiTes.setMaxWidth(Double.MAX_VALUE);
        btnMulaiTes.setOnAction(e -> mainWindow.switchPage("Tes Kesehatan"));

        rightCard.getChildren().addAll(iconBrain, cardTitle, cardDesc, btnMulaiTes);

        // Summary Panel (untuk ringkasan misi & meditasi)
        summaryPanel = new VBox(12);
        summaryPanel.setStyle("-fx-background-color: #F3F4F6; -fx-background-radius: 16; -fx-padding: 20;");
        summaryPanel.setVisible(false);

        Label summaryTitle = new Label("Ringkasan Aktivitas");
        summaryTitle.setStyle("-fx-font-weight: bold; -fx-font-size: 14px; -fx-text-fill: #1F2937;");

        missionSummaryLabel = new Label();
        missionSummaryLabel.setStyle("-fx-text-fill: #4B5563; -fx-font-size: 13px;");

        meditationSummaryLabel = new Label();
        meditationSummaryLabel.setStyle("-fx-text-fill: #4B5563; -fx-font-size: 13px;");

        summaryPanel.getChildren().addAll(summaryTitle, missionSummaryLabel, meditationSummaryLabel);

        rightColumn.getChildren().addAll(rightCard, summaryPanel);
        this.add(leftHero, 0, 0);
        this.add(rightColumn, 1, 0);
    }

    // ==================== CONTROLLER COMPATIBILITY METHODS ====================

    public void setWelcomeMessage(String message) {
        welcomeLabel.setText(message);
    }

    public void setMissionSummary(String summary) {
        missionSummaryLabel.setText("📋 " + summary);
        summaryPanel.setVisible(true);
    }

    public void setMeditationSummary(String summary) {
        meditationSummaryLabel.setText("🧘 " + summary);
        summaryPanel.setVisible(true);
    }
}