package com.CareVibe.view;

import com.CareVibe.model.UserSession;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Duration;

public class MeditationView extends VBox {

    public MeditationView() {
        this.setStyle("-fx-background-color: #F0FDF4; -fx-padding: 30;");
        this.setSpacing(25);

        ScrollPane scrollPane = new ScrollPane();
        scrollPane.setFitToWidth(true);
        scrollPane.setStyle("-fx-background-color: transparent; -fx-background: transparent;");
        scrollPane.setVbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED);

        VBox content = new VBox(25);
        content.setPadding(new Insets(0, 0, 20, 0));
        content.setAlignment(Pos.TOP_LEFT);

        // Hapus createControllerSection() dari sini
        content.getChildren().addAll(createHeaderSection(), createMeditasiSection(), createMusikSection());
        scrollPane.setContent(content);
        this.getChildren().add(scrollPane);
    }

    // ==================== ORIGINAL VIEW METHODS ====================

    private VBox createHeaderSection() {
        VBox header = new VBox(8);
        header.setAlignment(Pos.TOP_LEFT);

        Label title = new Label("Meditasi & Relaksasi");
        title.setFont(Font.font("System", FontWeight.BOLD, 28));
        title.setStyle("-fx-text-fill: #065F46;");

        Label subtitle = new Label("Temukan ketenangan dengan panduan meditasi dan musik relaksasi.");
        subtitle.setWrapText(true);
        subtitle.setStyle("-fx-text-fill: #6B7280; -fx-font-size: 14px;");

        header.getChildren().addAll(title, subtitle);
        return header;
    }

    private VBox createMeditasiSection() {
        VBox section = new VBox(15);
        section.setAlignment(Pos.TOP_LEFT);

        Label sectionTitle = new Label("Panduan Meditasi");
        sectionTitle.setFont(Font.font("System", FontWeight.BOLD, 20));
        sectionTitle.setStyle("-fx-text-fill: #065F46;");

        FlowPane cardContainer = new FlowPane();
        cardContainer.setHgap(20);
        cardContainer.setVgap(20);
        cardContainer.setPadding(new Insets(10, 0, 10, 0));
        cardContainer.setAlignment(Pos.TOP_LEFT);

        Object[][] meditasiList = {
                {
                        "Meditasi Pernapasan",
                        "Panduan meditasi 10 menit untuk menenangkan pikiran.",
                        10, 20, "#10B981",
                        new String[]{
                                "Duduk tegak & nyaman\nTutup mata, rilekskan bahu. Letakkan tangan di lutut.",
                                "Tarik napas dalam\nHirup perlahan melalui hidung selama 4 detik. Rasakan perut mengembang.",
                                "Tahan napas\nTahan selama 4 detik. Jaga tubuh tetap santai.",
                                "Hembuskan perlahan\nKeluarkan napas melalui mulut selama 6 detik.",
                                "Ulangi & fokus\nUlangi siklus pernapasan. Arahkan pikiran kembali bila melayang.",
                                "Nikmati ketenangan\nBiarkan pikiran mengalir tanpa menghakimi. Fokus pada napas.",
                                "Selesai\nPerlahan buka mata. Regangkan tubuh. Bawa ketenangan ini ke aktivitasmu."
                        }
                },
                {
                        "Tidur Nyenyak",
                        "Meditasi panduan untuk meningkatkan kualitas tidur.",
                        15, 20, "#8B5CF6",
                        new String[]{
                                "Berbaring nyaman\nTidur telentang. Kaki sedikit terbuka. Tangan di samping badan.",
                                "Lepaskan ketegangan\nHirup dalam dan keluarkan semua ketegangan hari ini.",
                                "Rilekskan kaki\nFokus pada kaki. Rasakan kaki menjadi berat dan hangat.",
                                "Rilekskan tubuh ke atas\nPindah perhatian ke betis, paha, perut, dada, tangan, bahu.",
                                "Rilekskan wajah\nLemaskan rahang, dahi, mata. Biarkan semua otot wajah kendur.",
                                "Bayangkan tempat damai\nVisualisasikan pantai tenang, hutan sunyi, atau tempat favoritmu.",
                                "Biarkan dirimu tertidur\nJangan paksa tidur. Nikmati rasa rileks. Biarkan tidur datang sendiri.",
                                "Selesai\nBangun dengan perlahan. Tidur berkualitas membuat harimu lebih baik."
                        }
                },
                {
                        "Redakan Kecemasan",
                        "Teknik relaksasi untuk meredakan serangan kecemasan.",
                        12, 20, "#F59E0B",
                        new String[]{
                                "Kenali kecemasanmu\nAkui bahwa kamu merasa cemas. Ini normal dan bisa diatasi.",
                                "Berhenti sejenak\nHentikan aktivitas. Duduk atau berdiri di tempat yang aman.",
                                "Napas 4-7-8\nHirup 4 detik → tahan 7 detik → hembuskan 8 detik. Ulangi 3x.",
                                "Teknik 5 Indera\nSebutkan: 5 hal yang kamu lihat, 4 yang dirasakan, 3 didengar, 2 dicium, 1 dirasa.",
                                "Tegangkan & Lepaskan\nKepalkan tangan kuat 5 detik, lalu lepaskan. Rasakan perbedaannya.",
                                "Tantang pikiran negatif\nTanya: Apakah pikiran ini fakta? Apa buktinya? Apa yang paling mungkin terjadi?",
                                "Afirmasi positif\nUcapkan: 'Aku aman. Aku kuat. Ini akan berlalu. Aku bisa menghadapinya.'",
                                "Selesai\nKecemasan sudah berkurang. Berikan apresiasi untuk dirimu sendiri!"
                        }
                },
                {
                        "Mindfulness Harian",
                        "Latihan mindfulness untuk meningkatkan kesadaran.",
                        8, 20, "#EF4444",
                        new String[]{
                                "Hadir sepenuhnya\nLetakkan semua gadget. Duduk nyaman. Sadari di mana kamu berada sekarang.",
                                "Scan tubuh\nPerhatikan tubuhmu dari ujung kepala hingga kaki. Catat sensasi tanpa menghakimi.",
                                "Amati pikiran\nBiarkan pikiran muncul seperti awan. Amati tanpa mengikutinya.",
                                "Perhatikan emosi\nApa yang kamu rasakan sekarang? Beri nama emosinya tanpa menghakimi.",
                                "Kembali ke napas\nSetiap kali pikiran melayang, kembali fokus pada napas sebagai jangkar.",
                                "Rasa syukur\nPikirkan 3 hal yang kamu syukuri hari ini, sekecil apapun itu.",
                                "Selesai\nMindfulness bukan tentang pikiran kosong, tapi tentang sadar sepenuhnya. Bagus sekali!"
                        }
                }
        };

        for (Object[] m : meditasiList) {
            cardContainer.getChildren().add(createMeditationCard(
                    (String) m[0], (String) m[1], (int) m[2], (int) m[3], (String) m[4], (String[]) m[5]
            ));
        }

        section.getChildren().addAll(sectionTitle, cardContainer);
        return section;
    }

    private VBox createMeditationCard(String title, String description, int durationMinutes, int points, String color, String[] steps) {
        VBox card = new VBox(10);
        card.setStyle(
                "-fx-background-color: white;" +
                        "-fx-background-radius: 16;" +
                        "-fx-border-color: #E5E7EB;" +
                        "-fx-border-radius: 16;" +
                        "-fx-border-width: 1;"
        );
        card.setPadding(new Insets(18));
        card.setPrefWidth(280);
        card.setMinWidth(260);
        card.setMaxWidth(300);

        HBox badgeRow = new HBox();
        Label lblPoints = new Label("+" + points + " Poin");
        lblPoints.setStyle(
                "-fx-background-color: " + color + "18;" +
                        "-fx-text-fill: " + color + ";" +
                        "-fx-font-size: 11px;" +
                        "-fx-font-weight: bold;" +
                        "-fx-padding: 3 10 3 10;" +
                        "-fx-background-radius: 20;"
        );
        badgeRow.getChildren().add(lblPoints);

        Label lblTitle = new Label(title);
        lblTitle.setFont(Font.font("System", FontWeight.BOLD, 15));
        lblTitle.setStyle("-fx-text-fill: #111827;");

        Label lblDesc = new Label(description);
        lblDesc.setWrapText(true);
        lblDesc.setStyle("-fx-text-fill: #6B7280; -fx-font-size: 12px;");

        Region separator = new Region();
        separator.setStyle("-fx-background-color: #F3F4F6;");
        separator.setPrefHeight(1);
        separator.setMaxWidth(Double.MAX_VALUE);

        HBox footer = new HBox(10);
        footer.setAlignment(Pos.CENTER_LEFT);

        Label lblDuration = new Label("⏱  " + durationMinutes + " menit");
        lblDuration.setStyle("-fx-text-fill: #9CA3AF; -fx-font-size: 11px;");

        Region spacer = new Region();
        HBox.setHgrow(spacer, Priority.ALWAYS);

        Button btnStart = new Button("Mulai");
        btnStart.setStyle(
                "-fx-background-color: " + color + ";" +
                        "-fx-text-fill: white;" +
                        "-fx-font-size: 12px;" +
                        "-fx-font-weight: bold;" +
                        "-fx-padding: 6 18 6 18;" +
                        "-fx-background-radius: 20;" +
                        "-fx-cursor: hand;"
        );

        btnStart.setOnMouseEntered(e -> btnStart.setStyle(
                "-fx-background-color: " + color + "CC;" +
                        "-fx-text-fill: white;" +
                        "-fx-font-size: 12px;" +
                        "-fx-font-weight: bold;" +
                        "-fx-padding: 6 18 6 18;" +
                        "-fx-background-radius: 20;" +
                        "-fx-cursor: hand;"
        ));
        btnStart.setOnMouseExited(e -> btnStart.setStyle(
                "-fx-background-color: " + color + ";" +
                        "-fx-text-fill: white;" +
                        "-fx-font-size: 12px;" +
                        "-fx-font-weight: bold;" +
                        "-fx-padding: 6 18 6 18;" +
                        "-fx-background-radius: 20;" +
                        "-fx-cursor: hand;"
        ));

        btnStart.setOnAction(e -> showMeditationSession(title, durationMinutes, points, color, steps));

        footer.getChildren().addAll(lblDuration, spacer, btnStart);
        card.getChildren().addAll(badgeRow, lblTitle, lblDesc, separator, footer);
        return card;
    }

    private void showMeditationSession(String meditationTitle, int totalMinutes, int points, String color, String[] steps) {
        Stage dialogStage = new Stage();
        dialogStage.initModality(Modality.APPLICATION_MODAL);
        dialogStage.initStyle(StageStyle.UNDECORATED);
        dialogStage.setTitle("Sesi Meditasi");

        VBox root = new VBox(0);
        root.setStyle("-fx-background-color: #F9FAFB;");

        VBox banner = new VBox(8);
        banner.setStyle("-fx-background-color: " + color + "; -fx-padding: 24 20 20 20;");
        banner.setAlignment(Pos.CENTER);

        Label lblTitle = new Label(meditationTitle);
        lblTitle.setFont(Font.font("System", FontWeight.BOLD, 18));
        lblTitle.setStyle("-fx-text-fill: white;");

        int totalSeconds = totalMinutes * 60;
        Label lblTimer = new Label(formatTime(totalSeconds));
        lblTimer.setFont(Font.font("System", FontWeight.BOLD, 52));
        lblTimer.setStyle("-fx-text-fill: white;");

        ProgressBar progressBar = new ProgressBar(0);
        progressBar.setPrefWidth(400);
        progressBar.setPrefHeight(6);
        progressBar.setStyle(
                "-fx-accent: white;" +
                        "-fx-control-inner-background: rgba(255,255,255,0.3);" +
                        "-fx-background-radius: 4;"
        );

        banner.getChildren().addAll(lblTitle, lblTimer, progressBar);

        VBox body = new VBox(16);
        body.setPadding(new Insets(20, 24, 20, 24));
        body.setAlignment(Pos.TOP_CENTER);

        int stepCount = steps.length;
        int secondsPerStep = totalSeconds / stepCount;

        Label lblStepNum = new Label("Langkah 1 dari " + stepCount);
        lblStepNum.setStyle("-fx-text-fill: #6B7280; -fx-font-size: 12px;");

        VBox stepCard = new VBox(10);
        stepCard.setStyle(
                "-fx-background-color: white;" +
                        "-fx-background-radius: 14;" +
                        "-fx-border-color: #E5E7EB;" +
                        "-fx-border-radius: 14;" +
                        "-fx-border-width: 1;" +
                        "-fx-padding: 18 18 18 18;"
        );
        stepCard.setMinHeight(120);

        Label lblStepTitle = new Label();
        lblStepTitle.setFont(Font.font("System", FontWeight.BOLD, 15));
        lblStepTitle.setStyle("-fx-text-fill: #111827;");
        lblStepTitle.setWrapText(true);

        Label lblStepBody = new Label();
        lblStepBody.setStyle("-fx-text-fill: #4B5563; -fx-font-size: 13px; -fx-line-spacing: 2;");
        lblStepBody.setWrapText(true);
        stepCard.getChildren().addAll(lblStepTitle, lblStepBody);

        HBox stepNav = new HBox(10);
        stepNav.setAlignment(Pos.CENTER);

        Button btnPrev = new Button("◀ Sebelumnya");
        Button btnNext = new Button("Berikutnya ▶");

        String outlineStyle = "-fx-background-color: white; -fx-text-fill: " + color + "; -fx-border-color: " + color + "; -fx-border-width: 1.5; -fx-font-size: 12px; -fx-font-weight: bold; -fx-padding: 8 18 8 18; -fx-background-radius: 20; -fx-border-radius: 20; -fx-cursor: hand;";
        btnPrev.setStyle(outlineStyle);
        btnNext.setStyle(outlineStyle);

        stepNav.getChildren().addAll(btnPrev, btnNext);

        HBox controlBox = new HBox(15);
        controlBox.setAlignment(Pos.CENTER);
        controlBox.setPadding(new Insets(10, 0, 10, 0));

        Button btnStartTimer = new Button("▶ Mulai Sesi");
        Button btnPause = new Button("⏸ Jeda");
        Button btnStop = new Button("⏹ Stop");

        String filledStyle = "-fx-background-color: " + color + "; -fx-text-fill: white; -fx-font-size: 13px; -fx-font-weight: bold; -fx-padding: 10 24 10 24; -fx-background-radius: 25; -fx-cursor: hand;";
        btnStartTimer.setStyle(filledStyle);
        btnPause.setStyle("-fx-background-color: #6B7280; -fx-text-fill: white; -fx-font-size: 13px; -fx-font-weight: bold; -fx-padding: 10 24 10 24; -fx-background-radius: 25; -fx-cursor: hand;");
        btnStop.setStyle("-fx-background-color: #EF4444; -fx-text-fill: white; -fx-font-size: 13px; -fx-font-weight: bold; -fx-padding: 10 24 10 24; -fx-background-radius: 25; -fx-cursor: hand;");

        btnPause.setDisable(true);
        btnStop.setDisable(true);

        controlBox.getChildren().addAll(btnStartTimer, btnPause, btnStop);

        Label lblCoin = new Label("+" + points + " Poin berhasil diperoleh!");
        lblCoin.setStyle(
                "-fx-background-color: #D1FAE5;" +
                        "-fx-text-fill: #065F46;" +
                        "-fx-font-weight: bold;" +
                        "-fx-font-size: 13px;" +
                        "-fx-padding: 10 22 10 22;" +
                        "-fx-background-radius: 24;"
        );
        lblCoin.setVisible(false);

        body.getChildren().addAll(lblStepNum, stepCard, stepNav, controlBox, lblCoin);
        root.getChildren().addAll(banner, body);

        final int[] secondsLeft = {totalSeconds};
        final boolean[] started = {false};
        final boolean[] paused = {false};
        final boolean[] finished = {false};
        final int[] currentStep = {0};

        Runnable updateStep = () -> {
            String[] parts = steps[currentStep[0]].split("\n", 2);
            lblStepTitle.setText(parts[0]);
            lblStepBody.setText(parts.length > 1 ? parts[1] : "");
            lblStepNum.setText("Langkah " + (currentStep[0] + 1) + " dari " + stepCount);
            btnPrev.setDisable(currentStep[0] == 0);
            btnNext.setDisable(currentStep[0] == stepCount - 1);
        };
        updateStep.run();

        btnPrev.setOnAction(e -> {
            if (currentStep[0] > 0) {
                currentStep[0]--;
                updateStep.run();
            }
        });

        btnNext.setOnAction(e -> {
            if (currentStep[0] < stepCount - 1) {
                currentStep[0]++;
                updateStep.run();
            }
        });

        Timeline timeline = new Timeline(new KeyFrame(Duration.seconds(1), e -> {
            if (paused[0] || finished[0]) return;
            secondsLeft[0]--;
            if (secondsLeft[0] < 0) secondsLeft[0] = 0;

            lblTimer.setText(formatTime(secondsLeft[0]));
            double progress = 1.0 - (double) secondsLeft[0] / totalSeconds;
            progressBar.setProgress(progress);

            int elapsed = totalSeconds - secondsLeft[0];
            int autoStep = Math.min(elapsed / secondsPerStep, stepCount - 1);
            if (autoStep != currentStep[0]) {
                currentStep[0] = autoStep;
                updateStep.run();
            }

            if (secondsLeft[0] == 0 && !finished[0]) {
                finished[0] = true;
                UserSession.addPoints(points);
                lblTimer.setText("Selesai!");
                lblTimer.setFont(Font.font("System", FontWeight.BOLD, 34));
                progressBar.setProgress(1.0);
                lblCoin.setVisible(true);
                btnPause.setDisable(true);
                btnStop.setDisable(true);
                btnStartTimer.setDisable(true);
                currentStep[0] = stepCount - 1;
                updateStep.run();
            }
        }));
        timeline.setCycleCount(totalSeconds + 1);

        btnStartTimer.setOnAction(e -> {
            started[0] = true;
            paused[0] = false;
            timeline.play();
            btnStartTimer.setDisable(true);
            btnPause.setDisable(false);
            btnStop.setDisable(false);
        });

        btnPause.setOnAction(e -> {
            paused[0] = !paused[0];
            btnPause.setText(paused[0] ? "▶ Lanjutkan" : "⏸ Jeda");
        });

        btnStop.setOnAction(e -> {
            timeline.stop();
            dialogStage.close();
        });

        Button closeButton = new Button("Tutup");
        closeButton.setStyle(
                "-fx-background-color: #9CA3AF;" +
                        "-fx-text-fill: white;" +
                        "-fx-font-weight: bold;" +
                        "-fx-font-size: 13px;" +
                        "-fx-padding: 10 30 10 30;" +
                        "-fx-background-radius: 25;" +
                        "-fx-cursor: hand;"
        );
        closeButton.setOnAction(e -> {
            timeline.stop();
            dialogStage.close();
        });

        HBox closeBox = new HBox(closeButton);
        closeBox.setAlignment(Pos.CENTER);
        closeBox.setPadding(new Insets(0, 0, 20, 0));
        root.getChildren().add(closeBox);

        Scene scene = new Scene(root, 500, 650);
        dialogStage.setScene(scene);
        dialogStage.showAndWait();
    }

    private VBox createMusikSection() {
        VBox section = new VBox(15);
        section.setAlignment(Pos.TOP_LEFT);

        Label sectionTitle = new Label("Musik Relaksasi");
        sectionTitle.setFont(Font.font("System", FontWeight.BOLD, 20));
        sectionTitle.setStyle("-fx-text-fill: #065F46;");

        Label sectionDesc = new Label("Dengarkan koleksi musik relaksasi yang dirancang khusus untuk menenangkan pikiran dan mengurangi stres.");
        sectionDesc.setWrapText(true);
        sectionDesc.setStyle("-fx-text-fill: #6B7280; -fx-font-size: 13px;");

        Label pilihanLabel = new Label("Pilihan Musik:");
        pilihanLabel.setStyle("-fx-font-weight: bold; -fx-font-size: 14px; -fx-text-fill: #065F46;");

        FlowPane musikContainer = new FlowPane();
        musikContainer.setHgap(20);
        musikContainer.setVgap(20);
        musikContainer.setPadding(new Insets(10, 0, 10, 0));
        musikContainer.setAlignment(Pos.TOP_LEFT);

        String[][] musikList = {
                {"🌧️", "Suara Alam", "Hujan & Burung", "#3B82F6", "Tenang", "Alam"},
                {"🌊", "Suara Laut", "Ombak & Angin Laut", "#06B6D4", "Tenang", "Meditasi"},
                {"🔥", "Api Unggun", "Kretek Api & Kayu", "#F97316", "Hangat", "Fokus"},
                {"🌿", "Forest Calm", "Daun & Angin Hutan", "#22C55E", "Segar", "Tidur"}
        };

        for (String[] m : musikList) {
            musikContainer.getChildren().add(createMusicCard(m[0], m[1], m[2], m[3], m[4], m[5]));
        }

        section.getChildren().addAll(sectionTitle, sectionDesc, pilihanLabel, musikContainer);
        return section;
    }

    private VBox createMusicCard(String icon, String title, String description, String color, String mood, String category) {
        VBox card = new VBox(10);
        card.setStyle(
                "-fx-background-color: white;" +
                        "-fx-background-radius: 16;" +
                        "-fx-border-color: #E5E7EB;" +
                        "-fx-border-radius: 16;" +
                        "-fx-border-width: 1;"
        );
        card.setPadding(new Insets(16));
        card.setPrefWidth(265);
        card.setMinWidth(240);
        card.setMaxWidth(280);

        HBox iconRow = new HBox(12);
        iconRow.setAlignment(Pos.CENTER_LEFT);

        Label lblIcon = new Label(icon);
        lblIcon.setStyle(
                "-fx-font-size: 28px;" +
                        "-fx-background-color: " + color + "18;" +
                        "-fx-background-radius: 50;" +
                        "-fx-padding: 10 12 10 12;"
        );

        VBox titleBox = new VBox(2);
        Label lblTitle = new Label(title);
        lblTitle.setFont(Font.font("System", FontWeight.BOLD, 14));
        lblTitle.setStyle("-fx-text-fill: #111827;");
        Label lblDesc = new Label(description);
        lblDesc.setStyle("-fx-text-fill: #9CA3AF; -fx-font-size: 11px;");
        titleBox.getChildren().addAll(lblTitle, lblDesc);
        iconRow.getChildren().addAll(lblIcon, titleBox);

        HBox tagRow = new HBox(6);
        Label tagMood = new Label(mood);
        tagMood.setStyle(
                "-fx-background-color: " + color + "18;" +
                        "-fx-text-fill: " + color + ";" +
                        "-fx-font-size: 10px;" +
                        "-fx-padding: 3 8 3 8;" +
                        "-fx-background-radius: 10;"
        );
        Label tagCat = new Label(category);
        tagCat.setStyle(
                "-fx-background-color: #F3F4F6;" +
                        "-fx-text-fill: #6B7280;" +
                        "-fx-font-size: 10px;" +
                        "-fx-padding: 3 8 3 8;" +
                        "-fx-background-radius: 10;"
        );
        tagRow.getChildren().addAll(tagMood, tagCat);

        Region sep = new Region();
        sep.setStyle("-fx-background-color: #F3F4F6;");
        sep.setPrefHeight(1);
        sep.setMaxWidth(Double.MAX_VALUE);

        Button btnPlay = new Button("▶ Putar Musik");
        btnPlay.setStyle(
                "-fx-background-color: " + color + ";" +
                        "-fx-text-fill: white;" +
                        "-fx-font-size: 12px;" +
                        "-fx-font-weight: bold;" +
                        "-fx-padding: 8 0 8 0;" +
                        "-fx-background-radius: 20;" +
                        "-fx-cursor: hand;"
        );
        btnPlay.setMaxWidth(Double.MAX_VALUE);

        btnPlay.setOnAction(e -> showMusicInfoDialog(title, description, color));

        card.getChildren().addAll(iconRow, tagRow, sep, btnPlay);
        return card;
    }

    private void showMusicInfoDialog(String title, String description, String color) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Musik Relaksasi");
        alert.setHeaderText("🎵 " + title);
        alert.setContentText(
                description + "\n\n" +
                        "+10 Poin akan ditambahkan setelah mendengarkan selama 1 menit.\n\n" +
                        "Tips: Gunakan headphone untuk pengalaman terbaik!"
        );
        alert.showAndWait();
        UserSession.addPoints(10);
    }

    private String formatTime(int totalSeconds) {
        return String.format("%02d:%02d", totalSeconds / 60, totalSeconds % 60);
    }
}
