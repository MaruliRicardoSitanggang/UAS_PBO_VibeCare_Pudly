package com.CareVibe.view;

import com.CareVibe.model.UserSession;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.text.Font;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.scene.Scene;
import javafx.scene.text.FontWeight;

public class ProfileView extends VBox {

    private final MainWindow mainWindow;

    public ProfileView(MainWindow mainWindow) {
        this.mainWindow = mainWindow;
        buildUI();
    }



    private void buildUI() {
        this.setStyle("-fx-background-color: #F0FDF4;");
        this.setFillWidth(true);

        // ===== SEMUA KONTEN DIBUNGKUS ScrollPane =====
        VBox innerContent = new VBox(24);
        innerContent.setStyle("-fx-background-color: #F0FDF4;");
        innerContent.setPadding(new Insets(40, 60, 40, 60));
        innerContent.setAlignment(Pos.TOP_CENTER);

        // ===== HEADER =====
        Label pageTitle = new Label("Profil Saya");
        pageTitle.setFont(Font.font("System", FontWeight.BOLD, 26));
        pageTitle.setStyle("-fx-text-fill: #065F46;");
        pageTitle.setAlignment(Pos.CENTER_LEFT);
        pageTitle.setMaxWidth(Double.MAX_VALUE);

        // ===== AVATAR + INFO CARD =====
        VBox profileCard = new VBox(16);
        profileCard.setStyle("-fx-background-color: white; -fx-background-radius: 20; " +
                "-fx-effect: dropshadow(gaussian, rgba(0,0,0,0.08), 12, 0, 0, 4);");
        profileCard.setPadding(new Insets(30));
        profileCard.setAlignment(Pos.CENTER);
        profileCard.setMaxWidth(700);

        String name = UserSession.getLoggedInName();
        String email = UserSession.getLoggedInUser();
        String initials = getInitials(name);

        StackPane avatarStack = new StackPane();
        Circle avatarCircle = new Circle(50);
        avatarCircle.setFill(Color.web("#10B981"));
        Label initialsLabel = new Label(initials);
        initialsLabel.setFont(Font.font("System", FontWeight.BOLD, 28));
        initialsLabel.setStyle("-fx-text-fill: white;");
        avatarStack.getChildren().addAll(avatarCircle, initialsLabel);

        Label nameLabel = new Label(name);
        nameLabel.setFont(Font.font("System", FontWeight.BOLD, 22));
        nameLabel.setStyle("-fx-text-fill: #1F2937;");

        Label emailLabel = new Label(email);
        emailLabel.setStyle("-fx-text-fill: #6B7280; -fx-font-size: 14px;");

        HBox badgeBox = new HBox(8);
        badgeBox.setAlignment(Pos.CENTER);
        Label pointBadge = new Label("⭐ " + UserSession.getPoints() + " Poin");
        pointBadge.setStyle("-fx-background-color: #FEF3C7; -fx-text-fill: #92400E; " +
                "-fx-font-weight: bold; -fx-font-size: 13px; " +
                "-fx-background-radius: 20; -fx-padding: 6 16 6 16;");
        badgeBox.getChildren().add(pointBadge);

        profileCard.getChildren().addAll(avatarStack, nameLabel, emailLabel, badgeBox);

        // ===== MENU FITUR =====
        VBox menuCard = new VBox(0);
        menuCard.setStyle("-fx-background-color: white; -fx-background-radius: 20; " +
                "-fx-effect: dropshadow(gaussian, rgba(0,0,0,0.08), 12, 0, 0, 4);");
        menuCard.setMaxWidth(700);

        menuCard.getChildren().addAll(
                createMenuItem("📊", "Riwayat Tes", "Lihat hasil tes kesehatan mental Anda", () -> {
                    showInfoDialog("Riwayat Tes", "Fitur riwayat tes akan segera tersedia.");
                }),
                createDivider(),
                createMenuItem("🎯", "Misi Saya", "Cek progres misi harian Anda", () -> {
                    mainWindow.switchPage("Misi Harian");
                }),
                createDivider(),
                createMenuItem("💬", "Konsultasi Saya", "Lihat jadwal dan histori konsultasi", () -> {
                    showRiwayatKonsultasi();
                }),
                createDivider(),
                createMenuItem("🔒", "Ubah Password", "Perbarui password akun Anda", () -> {
                    showChangePasswordDialog();
                }),
                createDivider(),
                createMenuItem("ℹ️", "Tentang Aplikasi", "Versi 1.0.0 - VibeCare", () -> {
                    showAboutPage();
                })
        );

        // ===== TOMBOL LOGOUT =====
        Button logoutBtn = new Button("🚪  Keluar dari Akun");
        logoutBtn.setStyle("-fx-background-color: #FEE2E2; -fx-text-fill: #DC2626; " +
                "-fx-font-weight: bold; -fx-font-size: 14px; " +
                "-fx-padding: 14 30 14 30; -fx-background-radius: 30; -fx-cursor: hand;");
        logoutBtn.setMaxWidth(700);
        logoutBtn.setOnMouseEntered(e -> logoutBtn.setStyle(
                "-fx-background-color: #FCA5A5; -fx-text-fill: #DC2626; " +
                        "-fx-font-weight: bold; -fx-font-size: 14px; " +
                        "-fx-padding: 14 30 14 30; -fx-background-radius: 30; -fx-cursor: hand;"));
        logoutBtn.setOnMouseExited(e -> logoutBtn.setStyle(
                "-fx-background-color: #FEE2E2; -fx-text-fill: #DC2626; " +
                        "-fx-font-weight: bold; -fx-font-size: 14px; " +
                        "-fx-padding: 14 30 14 30; -fx-background-radius: 30; -fx-cursor: hand;"));
        logoutBtn.setOnAction(e -> handleLogout());

        innerContent.getChildren().addAll(pageTitle, profileCard, menuCard, logoutBtn);

        // ===== SCROLL PANE membungkus semua konten =====
        ScrollPane scrollPane = new ScrollPane(innerContent);
        scrollPane.setFitToWidth(true);       // konten ikut lebar window
        scrollPane.setFitToHeight(false);     // tinggi bebas agar bisa scroll
        scrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);   // no horizontal scroll
        scrollPane.setVbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED); // scroll vertikal muncul kalau perlu
        scrollPane.setStyle("-fx-background-color: #F0FDF4; -fx-background: #F0FDF4;");

        VBox.setVgrow(scrollPane, Priority.ALWAYS); // ScrollPane mengisi sisa ruang
        this.getChildren().add(scrollPane);
    }

    // ===== HELPER: buat menu item =====
    private HBox createMenuItem(String icon, String title, String subtitle, Runnable action) {
        HBox item = new HBox(16);
        item.setAlignment(Pos.CENTER_LEFT);
        item.setPadding(new Insets(16, 20, 16, 20));
        item.setStyle("-fx-cursor: hand;");
        item.setOnMouseEntered(e -> item.setStyle(
                "-fx-background-color: #F9FAFB; -fx-cursor: hand;"));
        item.setOnMouseExited(e -> item.setStyle("-fx-cursor: hand;"));
        item.setOnMouseClicked(e -> action.run());

        Label iconLabel = new Label(icon);
        iconLabel.setFont(Font.font("System", 22));
        iconLabel.setMinWidth(36);

        VBox textBox = new VBox(3);
        Label titleLabel = new Label(title);
        titleLabel.setFont(Font.font("System", FontWeight.BOLD, 14));
        titleLabel.setStyle("-fx-text-fill: #1F2937;");
        Label subtitleLabel = new Label(subtitle);
        subtitleLabel.setStyle("-fx-text-fill: #9CA3AF; -fx-font-size: 12px;");
        textBox.getChildren().addAll(titleLabel, subtitleLabel);

        Region spacer = new Region();
        HBox.setHgrow(spacer, Priority.ALWAYS);

        Label arrow = new Label("›");
        arrow.setStyle("-fx-text-fill: #D1D5DB; -fx-font-size: 20px;");

        item.getChildren().addAll(iconLabel, textBox, spacer, arrow);
        return item;
    }

    private Separator createDivider() {
        Separator sep = new Separator();
        sep.setStyle("-fx-background-color: #F3F4F6;");
        sep.setPadding(new Insets(0, 20, 0, 20));
        return sep;
    }

    // ===== LOGOUT =====
    private void handleLogout() {
        Alert confirm = new Alert(Alert.AlertType.CONFIRMATION);
        confirm.setTitle("Konfirmasi Logout");
        confirm.setHeaderText(null);
        confirm.setContentText("Apakah Anda yakin ingin keluar dari akun?");

        // Style tombol
        Button okBtn = (Button) confirm.getDialogPane().lookupButton(ButtonType.OK);
        okBtn.setText("Ya, Keluar");
        Button cancelBtn = (Button) confirm.getDialogPane().lookupButton(ButtonType.CANCEL);
        cancelBtn.setText("Batal");

        confirm.showAndWait().ifPresent(result -> {
            if (result == ButtonType.OK) {
                UserSession.logout();
                mainWindow.updateLoginStatus();   // reset navbar: tampilkan Masuk & Daftar
                mainWindow.refreshPoints();
                mainWindow.switchPage("Beranda"); // kembali ke beranda
            }
        });
    }

    private void showRiwayatKonsultasi() {
        Stage stage = new Stage();
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.initStyle(StageStyle.UNDECORATED);
        stage.setTitle("Riwayat Konsultasi");

        VBox root = new VBox(0);
        root.setStyle("-fx-background-color: #F0FDF4;");

        HBox headerBar = new HBox();
        headerBar.setAlignment(Pos.CENTER_LEFT);
        headerBar.setPadding(new Insets(16, 16, 0, 20));
        Label headerTitle = new Label("💬 Riwayat Konsultasi");
        headerTitle.setFont(Font.font("System", FontWeight.BOLD, 18));
        headerTitle.setStyle("-fx-text-fill: #065F46;");
        Region headerSpacer = new Region();
        HBox.setHgrow(headerSpacer, Priority.ALWAYS);
        Button closeBtn = new Button("✕");
        closeBtn.setStyle("-fx-background-color: transparent; -fx-text-fill: #9CA3AF; -fx-font-size: 18px; -fx-cursor: hand;");
        closeBtn.setOnAction(e -> stage.close());
        headerBar.getChildren().addAll(headerTitle, headerSpacer, closeBtn);

        VBox content = new VBox(12);
        content.setPadding(new Insets(16, 20, 30, 20));

        // Loading label
        Label loadingLabel = new Label("🔄 Memuat riwayat konsultasi...");
        loadingLabel.setStyle("-fx-text-fill: #6B7280; -fx-font-size: 14px;");
        content.getChildren().add(loadingLabel);

        ScrollPane scrollPane = new ScrollPane(content);
        scrollPane.setFitToWidth(true);
        scrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        scrollPane.setStyle("-fx-background-color: #F0FDF4; -fx-background: #F0FDF4;");

        root.getChildren().addAll(headerBar, scrollPane);
        VBox.setVgrow(scrollPane, Priority.ALWAYS);

        Scene scene = new Scene(root, 520, 500);
        stage.setScene(scene);
        stage.show();

        // Fetch data dari backend
        String userEmail = UserSession.getLoggedInUser();
        String url = "http://localhost:8080/api/consultations/user-email?email="
                + java.net.URLEncoder.encode(userEmail, java.nio.charset.StandardCharsets.UTF_8);

        new Thread(() -> {
            try {
                java.net.http.HttpRequest request = java.net.http.HttpRequest.newBuilder()
                        .uri(java.net.URI.create(url))
                        .header("Accept", "application/json")
                        .GET()
                        .build();

                java.net.http.HttpResponse<String> response =
                        java.net.http.HttpClient.newHttpClient().send(
                                request, java.net.http.HttpResponse.BodyHandlers.ofString());

                javafx.application.Platform.runLater(() -> {
                    content.getChildren().clear();
                    if (response.statusCode() == 200 && !response.body().equals("[]")) {
                        try {
                            com.fasterxml.jackson.databind.ObjectMapper mapper =
                                    new com.fasterxml.jackson.databind.ObjectMapper();
                            com.fasterxml.jackson.databind.JsonNode arr = mapper.readTree(response.body());

                            for (com.fasterxml.jackson.databind.JsonNode node : arr) {
                                String psikologName = node.path("psychologist").path("username").asText("Psikolog");
                                String status = node.path("status").asText("SCHEDULED");
                                String time = node.path("appointmentTime").asText("-");

                                // Format tanggal
                                try {
                                    java.time.LocalDateTime ldt = java.time.LocalDateTime.parse(
                                            time.substring(0, 19));
                                    time = ldt.format(java.time.format.DateTimeFormatter
                                            .ofPattern("dd MMM yyyy, HH:mm"));
                                } catch (Exception ignored) {}

                                VBox card = new VBox(8);
                                card.setPadding(new Insets(14));
                                card.setStyle(
                                        "-fx-background-color: white;" +
                                                "-fx-background-radius: 14;" +
                                                "-fx-effect: dropshadow(gaussian, rgba(0,0,0,0.06), 8, 0, 0, 2);"
                                );

                                Label psikologLbl = new Label("👨‍⚕️ " + psikologName);
                                psikologLbl.setFont(Font.font("System", FontWeight.BOLD, 14));
                                psikologLbl.setStyle("-fx-text-fill: #1F2937;");

                                Label timeLbl = new Label("🕐 " + time);
                                timeLbl.setStyle("-fx-text-fill: #6B7280; -fx-font-size: 13px;");

                                String statusColor = status.equals("SCHEDULED") ? "#D1FAE5" : "#FEF3C7";
                                String statusTextColor = status.equals("SCHEDULED") ? "#065F46" : "#92400E";
                                String statusText = status.equals("SCHEDULED") ? "Terjadwal" : status;
                                Label statusLbl = new Label(statusText);
                                statusLbl.setStyle(
                                        "-fx-background-color: " + statusColor + ";" +
                                                "-fx-text-fill: " + statusTextColor + ";" +
                                                "-fx-font-size: 12px; -fx-font-weight: bold;" +
                                                "-fx-background-radius: 10; -fx-padding: 3 10 3 10;"
                                );

                                card.getChildren().addAll(psikologLbl, timeLbl, statusLbl);
                                content.getChildren().add(card);
                            }
                        } catch (Exception ex) {
                            Label errLbl = new Label("Gagal memuat data: " + ex.getMessage());
                            errLbl.setStyle("-fx-text-fill: #EF4444;");
                            content.getChildren().add(errLbl);
                        }
                    } else {
                        Label emptyLbl = new Label("📭 Belum ada riwayat konsultasi.");
                        emptyLbl.setStyle("-fx-text-fill: #6B7280; -fx-font-size: 14px;");
                        content.getChildren().add(emptyLbl);
                    }
                });
            } catch (Exception ex) {
                javafx.application.Platform.runLater(() -> {
                    content.getChildren().clear();
                    Label errLbl = new Label("❌ Tidak bisa terhubung ke server.");
                    errLbl.setStyle("-fx-text-fill: #EF4444; -fx-font-size: 13px;");
                    content.getChildren().add(errLbl);
                });
            }
        }).start();
    }

    //UBAH PASSWORD
    private void showChangePasswordDialog() {
        Dialog<ButtonType> dialog = new Dialog<>();
        dialog.setTitle("Ubah Password");
        dialog.setHeaderText(null);

        VBox content = new VBox(14);
        content.setPadding(new Insets(20));
        content.setStyle("-fx-background-color: #F0FDF4;");
        content.setPrefWidth(380);

        Label title = new Label("🔒 Ubah Password");
        title.setFont(Font.font("System", FontWeight.BOLD, 18));
        title.setStyle("-fx-text-fill: #065F46;");

        PasswordField oldPass = new PasswordField();
        oldPass.setPromptText("Password lama");
        oldPass.setStyle("-fx-background-color: white; -fx-background-radius: 10; " +
                "-fx-border-color: #E5E7EB; -fx-border-radius: 10; -fx-padding: 10 14; -fx-font-size: 13px;");

        PasswordField newPass = new PasswordField();
        newPass.setPromptText("Password baru (minimal 6 karakter)");
        newPass.setStyle("-fx-background-color: white; -fx-background-radius: 10; " +
                "-fx-border-color: #E5E7EB; -fx-border-radius: 10; -fx-padding: 10 14; -fx-font-size: 13px;");

        PasswordField confirmPass = new PasswordField();
        confirmPass.setPromptText("Konfirmasi password baru");
        confirmPass.setStyle("-fx-background-color: white; -fx-background-radius: 10; " +
                "-fx-border-color: #E5E7EB; -fx-border-radius: 10; -fx-padding: 10 14; -fx-font-size: 13px;");

        Label errorLbl = new Label();
        errorLbl.setStyle("-fx-text-fill: #EF4444; -fx-font-size: 12px;");
        errorLbl.setVisible(false);
        errorLbl.setWrapText(true);

        content.getChildren().addAll(title,
                makeLabeledField("Password Lama", oldPass),
                makeLabeledField("Password Baru", newPass),
                makeLabeledField("Konfirmasi Password Baru", confirmPass),
                errorLbl);

        dialog.getDialogPane().setContent(content);
        dialog.getDialogPane().setStyle("-fx-background-color: #F0FDF4;");
        dialog.getDialogPane().getButtonTypes().addAll(ButtonType.OK, ButtonType.CANCEL);

        Button okBtn = (Button) dialog.getDialogPane().lookupButton(ButtonType.OK);
        okBtn.setText("Simpan");
        okBtn.setStyle("-fx-background-color: #10B981; -fx-text-fill: white; -fx-font-weight: bold; -fx-background-radius: 20;");
        ((Button) dialog.getDialogPane().lookupButton(ButtonType.CANCEL)).setText("Batal");

        okBtn.addEventFilter(javafx.event.ActionEvent.ACTION, event -> {
            // Validasi frontend dulu
            if (oldPass.getText().isEmpty()) {
                errorLbl.setText("Password lama harus diisi!");
                errorLbl.setVisible(true);
                event.consume();
                return;
            }
            if (newPass.getText().length() < 6) {
                errorLbl.setText("Password baru minimal 6 karakter!");
                errorLbl.setVisible(true);
                event.consume();
                return;
            }
            if (!newPass.getText().equals(confirmPass.getText())) {
                errorLbl.setText("Konfirmasi password tidak cocok!");
                errorLbl.setVisible(true);
                event.consume();
                return;
            }

            // Consume dulu, proses HTTP di background
            event.consume();

            okBtn.setDisable(true);
            okBtn.setText("Menyimpan...");
            errorLbl.setVisible(false);

            String email = UserSession.getLoggedInUser();
            String oldPassword = oldPass.getText();
            String newPassword = newPass.getText();

            new Thread(() -> {
                try {
                    String json = "{"
                            + "\"email\":\"" + email.replace("\"", "\\\"") + "\","
                            + "\"oldPassword\":\"" + oldPassword.replace("\"", "\\\"") + "\","
                            + "\"newPassword\":\"" + newPassword.replace("\"", "\\\"") + "\""
                            + "}";

                    java.net.http.HttpRequest request = java.net.http.HttpRequest.newBuilder()
                            .uri(java.net.URI.create("http://localhost:8080/api/auth/change-password"))
                            .header("Content-Type", "application/json")
                            .POST(java.net.http.HttpRequest.BodyPublishers.ofString(json))
                            .build();

                    java.net.http.HttpResponse<String> response =
                            java.net.http.HttpClient.newHttpClient().send(
                                    request, java.net.http.HttpResponse.BodyHandlers.ofString());

                    javafx.application.Platform.runLater(() -> {
                        if (response.statusCode() == 200) {
                            dialog.close();
                            showInfoDialog("Berhasil", "Password berhasil diubah!");
                        } else {
                            errorLbl.setText(response.body() != null
                                    ? response.body() : "Gagal mengubah password!");
                            errorLbl.setVisible(true);
                            okBtn.setDisable(false);
                            okBtn.setText("Simpan");
                        }
                    });
                } catch (Exception ex) {
                    javafx.application.Platform.runLater(() -> {
                        errorLbl.setText("Tidak bisa terhubung ke server!");
                        errorLbl.setVisible(true);
                        okBtn.setDisable(false);
                        okBtn.setText("Simpan");
                    });
                }
            }).start();
        });

        dialog.showAndWait();
    }

    private void showAboutPage() {
        Stage aboutStage = new Stage();
        aboutStage.initModality(Modality.APPLICATION_MODAL);
        aboutStage.initStyle(StageStyle.UNDECORATED);
        aboutStage.setTitle("Tentang VibeCare");

        VBox root = new VBox(0);
        root.setStyle("-fx-background-color: #F0FDF4;");

        // ===== HEADER BAR =====
        HBox headerBar = new HBox();
        headerBar.setAlignment(Pos.CENTER_LEFT);
        headerBar.setPadding(new Insets(16, 16, 0, 16));
        Button closeBtn = new Button("✕");
        closeBtn.setStyle("-fx-background-color: transparent; -fx-text-fill: #9CA3AF; -fx-font-size: 18px; -fx-cursor: hand;");
        closeBtn.setOnAction(e -> aboutStage.close());
        Region headerSpacer = new Region();
        HBox.setHgrow(headerSpacer, Priority.ALWAYS);
        headerBar.getChildren().addAll(headerSpacer, closeBtn);

        // ===== SCROLL CONTENT =====
        VBox content = new VBox(24);
        content.setPadding(new Insets(10, 40, 40, 40));
        content.setAlignment(Pos.TOP_CENTER);

        // -- Logo & Versi --
        VBox heroBox = new VBox(10);
        heroBox.setAlignment(Pos.CENTER);
        heroBox.setPadding(new Insets(10, 0, 10, 0));

        Label appNameLbl = new Label("VibeCare");
        appNameLbl.setFont(Font.font("System", FontWeight.BOLD, 30));
        appNameLbl.setStyle("-fx-text-fill: #065F46;");

        Label versionLbl = new Label("Versi 1.0.0");
        versionLbl.setStyle("-fx-text-fill: #9CA3AF; -fx-font-size: 13px;");

        Label taglineLbl = new Label("Platform Kesehatan Mental Terpercaya");
        taglineLbl.setStyle("-fx-text-fill: #6B7280; -fx-font-size: 14px;");

        Separator heroDivider = new Separator();
        heroDivider.setMaxWidth(300);
        heroDivider.setStyle("-fx-background-color: #D1FAE5;");

        heroBox.getChildren().addAll(appNameLbl, versionLbl, taglineLbl, heroDivider);

        // -- Deskripsi singkat --
        VBox descCard = createSectionCard("📋 Tentang Aplikasi",
                "VibeCare adalah aplikasi kesehatan mental yang dirancang untuk membantu Anda " +
                        "memantau, memahami, dan menjaga kesejahteraan mental sehari-hari. " +
                        "Didukung oleh psikolog profesional dan metode berbasis bukti ilmiah.");

        // -- Fitur-fitur --
        Label featuresTitle = new Label("✨ Fitur Utama");
        featuresTitle.setFont(Font.font("System", FontWeight.BOLD, 16));
        featuresTitle.setStyle("-fx-text-fill: #065F46;");
        featuresTitle.setMaxWidth(Double.MAX_VALUE);

        VBox featuresGrid = new VBox(12);
        featuresGrid.setMaxWidth(Double.MAX_VALUE);
        featuresGrid.getChildren().addAll(
                createFeatureCard("🧠", "Tes Kesehatan Mental",
                        "Tersedia 3 tes tervalidasi secara klinis:\n" +
                                "• Tes Depresi (PHQ-9) — 9 pertanyaan\n" +
                                "• Tes Kecemasan (GAD-7) — 7 pertanyaan\n" +
                                "• Tes Tingkat Stres — 10 pertanyaan\n" +
                                "Dapatkan hasil dan rekomendasi personal setelah tes."),
                createFeatureCard("👥", "Komunitas",
                        "Bergabung dengan komunitas pengguna VibeCare. " +
                                "Berbagi cerita, pengalaman, dan saling mendukung satu sama lain " +
                                "dalam perjalanan kesehatan mental Anda."),
                createFeatureCard("🩺", "Konsultasi dengan Psikolog",
                        "Terhubung langsung dengan psikolog profesional berlisensi. " +
                                "Buat janji konsultasi online maupun tatap muka sesuai kebutuhan Anda."),
                createFeatureCard("🧘", "Meditasi & Relaksasi",
                        "Koleksi sesi meditasi terpandu untuk membantu mengurangi stres, " +
                                "meningkatkan fokus, dan mencapai ketenangan pikiran kapan saja."),
                createFeatureCard("🎯", "Misi Harian",
                        "Tantangan harian yang dirancang untuk membangun kebiasaan positif. " +
                                "Selesaikan misi, kumpulkan poin, dan pantau progres kesehatan mental Anda " +
                                "dari hari ke hari."),
                createFeatureCard("⭐", "Sistem Poin & Reward",
                        "Dapatkan poin setiap kali login, menyelesaikan tes, dan misi harian. " +
                                "Poin dapat ditukarkan dengan berbagai reward eksklusif di dalam aplikasi.")
        );

        // -- Tim & Kontak --
        VBox contactCard = new VBox(14);
        contactCard.setStyle("-fx-background-color: white; -fx-background-radius: 16; " +
                "-fx-effect: dropshadow(gaussian, rgba(0,0,0,0.06), 10, 0, 0, 3);");
        contactCard.setPadding(new Insets(20));
        contactCard.setMaxWidth(Double.MAX_VALUE);

        Label contactTitle = new Label("📬 Hubungi Kami");
        contactTitle.setFont(Font.font("System", FontWeight.BOLD, 15));
        contactTitle.setStyle("-fx-text-fill: #065F46;");

        contactCard.getChildren().addAll(
                contactTitle,
                createContactRow("✉️", "Email", "support@vibecare.com"),
                createContactRow("🌐", "Website", "www.vibecare.com"),
                createContactRow("📱", "Instagram", "@vibecare.id")
        );

        // -- Footer --
        Label footerLbl = new Label("© 2024 VibeCare. All rights reserved.\nDibuat dengan ❤️ untuk kesehatan mental Indonesia.");
        footerLbl.setStyle("-fx-text-fill: #9CA3AF; -fx-font-size: 12px;");
        footerLbl.setAlignment(Pos.CENTER);
        footerLbl.setTextAlignment(javafx.scene.text.TextAlignment.CENTER);
        footerLbl.setWrapText(true);

        Button closeBottomBtn = new Button("Tutup");
        closeBottomBtn.setStyle("-fx-background-color: #10B981; -fx-text-fill: white; " +
                "-fx-font-weight: bold; -fx-font-size: 14px; " +
                "-fx-padding: 12 40 12 40; -fx-background-radius: 30; -fx-cursor: hand;");
        closeBottomBtn.setOnMouseEntered(e -> closeBottomBtn.setStyle(
                "-fx-background-color: #059669; -fx-text-fill: white; " +
                        "-fx-font-weight: bold; -fx-font-size: 14px; " +
                        "-fx-padding: 12 40 12 40; -fx-background-radius: 30; -fx-cursor: hand;"));
        closeBottomBtn.setOnMouseExited(e -> closeBottomBtn.setStyle(
                "-fx-background-color: #10B981; -fx-text-fill: white; " +
                        "-fx-font-weight: bold; -fx-font-size: 14px; " +
                        "-fx-padding: 12 40 12 40; -fx-background-radius: 30; -fx-cursor: hand;"));
        closeBottomBtn.setOnAction(e -> aboutStage.close());

        content.getChildren().addAll(
                heroBox, descCard, featuresTitle, featuresGrid, contactCard, footerLbl, closeBottomBtn
        );

        ScrollPane scrollPane = new ScrollPane(content);
        scrollPane.setFitToWidth(true);
        scrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        scrollPane.setVbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED);
        scrollPane.setStyle("-fx-background-color: #F0FDF4; -fx-background: #F0FDF4;");

        root.getChildren().addAll(headerBar, scrollPane);
        VBox.setVgrow(scrollPane, Priority.ALWAYS);

        Scene scene = new Scene(root, 520, 680);
        aboutStage.setScene(scene);
        aboutStage.showAndWait();
    }

    // Card deskripsi section
    private VBox createSectionCard(String title, String body) {
        VBox card = new VBox(10);
        card.setStyle("-fx-background-color: white; -fx-background-radius: 16; " +
                "-fx-effect: dropshadow(gaussian, rgba(0,0,0,0.06), 10, 0, 0, 3);");
        card.setPadding(new Insets(20));
        card.setMaxWidth(Double.MAX_VALUE);

        Label titleLbl = new Label(title);
        titleLbl.setFont(Font.font("System", FontWeight.BOLD, 15));
        titleLbl.setStyle("-fx-text-fill: #065F46;");

        Label bodyLbl = new Label(body);
        bodyLbl.setStyle("-fx-text-fill: #4B5563; -fx-font-size: 13px; -fx-line-spacing: 3;");
        bodyLbl.setWrapText(true);

        card.getChildren().addAll(titleLbl, bodyLbl);
        return card;
    }

    // Card per fitur
    private HBox createFeatureCard(String emoji, String title, String desc) {
        HBox card = new HBox(14);
        card.setStyle("-fx-background-color: white; -fx-background-radius: 14; " +
                "-fx-effect: dropshadow(gaussian, rgba(0,0,0,0.06), 8, 0, 0, 2);");
        card.setPadding(new Insets(16));
        card.setAlignment(Pos.TOP_LEFT);
        card.setMaxWidth(Double.MAX_VALUE);

        StackPane iconBg = new StackPane();
        Circle iconCircle = new Circle(24);
        iconCircle.setFill(Color.web("#D1FAE5"));
        Label iconLbl = new Label(emoji);
        iconLbl.setFont(Font.font("System", 20));
        iconBg.getChildren().addAll(iconCircle, iconLbl);
        iconBg.setMinSize(48, 48);
        iconBg.setMaxSize(48, 48);

        VBox textBox = new VBox(5);
        HBox.setHgrow(textBox, Priority.ALWAYS);
        Label titleLbl = new Label(title);
        titleLbl.setFont(Font.font("System", FontWeight.BOLD, 14));
        titleLbl.setStyle("-fx-text-fill: #1F2937;");
        Label descLbl = new Label(desc);
        descLbl.setStyle("-fx-text-fill: #6B7280; -fx-font-size: 12px; -fx-line-spacing: 2;");
        descLbl.setWrapText(true);
        textBox.getChildren().addAll(titleLbl, descLbl);

        card.getChildren().addAll(iconBg, textBox);
        return card;
    }

    // Row kontak
    private HBox createContactRow(String icon, String label, String value) {
        HBox row = new HBox(12);
        row.setAlignment(Pos.CENTER_LEFT);
        row.setPadding(new Insets(4, 0, 4, 0));

        Label iconLbl = new Label(icon);
        iconLbl.setStyle("-fx-font-size: 16px;");
        iconLbl.setMinWidth(24);

        Label labelLbl = new Label(label + ":");
        labelLbl.setStyle("-fx-text-fill: #6B7280; -fx-font-size: 13px;");
        labelLbl.setMinWidth(70);

        Label valueLbl = new Label(value);
        valueLbl.setStyle("-fx-text-fill: #1F2937; -fx-font-size: 13px; -fx-font-weight: bold;");

        row.getChildren().addAll(iconLbl, labelLbl, valueLbl);
        return row;
    }

    private VBox makeLabeledField(String labelText, Control field) {
        VBox box = new VBox(5);
        Label lbl = new Label(labelText);
        lbl.setStyle("-fx-text-fill: #4B5563; -fx-font-size: 13px; -fx-font-weight: bold;");
        box.getChildren().addAll(lbl, field);
        return box;
    }

    // ===== INFO DIALOG UMUM =====
    private void showInfoDialog(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        ((Button) alert.getDialogPane().lookupButton(ButtonType.OK)).setText("Oke");
        alert.showAndWait();
    }

    // ===== HELPER inisial nama =====
    private String getInitials(String name) {
        if (name == null || name.isEmpty()) return "?";
        String[] parts = name.trim().split("\\s+");
        if (parts.length == 1) return String.valueOf(parts[0].charAt(0)).toUpperCase();
        return (String.valueOf(parts[0].charAt(0)) + String.valueOf(parts[1].charAt(0))).toUpperCase();
    }
}