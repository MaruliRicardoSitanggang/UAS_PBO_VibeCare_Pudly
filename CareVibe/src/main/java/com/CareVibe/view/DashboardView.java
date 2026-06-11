package com.CareVibe.view;

import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.*;

public class DashboardView extends GridPane {
    public DashboardView(MainWindow mainWindow) {
        this.setStyle("-fx-background-color: #F0FDF4; -fx-background-radius: 20 20 0 0; -fx-padding: 40;");
        this.setHgap(40);
        this.setAlignment(Pos.CENTER);

        VBox leftHero = new VBox(20);
        leftHero.setPrefWidth(550);
        leftHero.setAlignment(Pos.CENTER_LEFT);

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
        leftHero.getChildren().addAll(titleBox, description, actionButtons);

        VBox rightCard = new VBox(15);
        rightCard.getStyleClass().add("card-item");
        rightCard.setPrefWidth(350);

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

        this.add(leftHero, 0, 0);
        this.add(rightCard, 1, 0);
    }
}