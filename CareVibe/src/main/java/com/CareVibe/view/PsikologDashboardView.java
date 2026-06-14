package com.CareVibe.view;

import com.CareVibe.model.UserSession;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

public class PsikologDashboardView extends BorderPane {

    private StackPane contentArea;
    private Button activeMenuBtn = null;

    private static final String SIDEBAR_BG  = "-fx-background-color: #064E3B;";
    private static final String MENU_NORMAL = "-fx-background-color: transparent; -fx-text-fill: #A7F3D0; -fx-font-size: 14px; -fx-padding: 12 16; -fx-cursor: hand; -fx-alignment: CENTER_LEFT; -fx-background-radius: 10;";
    private static final String MENU_HOVER  = "-fx-background-color: #065F46; -fx-text-fill: white; -fx-font-size: 14px; -fx-padding: 12 16; -fx-cursor: hand; -fx-alignment: CENTER_LEFT; -fx-background-radius: 10;";
    private static final String MENU_ACTIVE = "-fx-background-color: #10B981; -fx-text-fill: white; -fx-font-size: 14px; -fx-font-weight: bold; -fx-padding: 12 16; -fx-cursor: hand; -fx-alignment: CENTER_LEFT; -fx-background-radius: 10;";

    private static final String GREEN_PRIMARY = "#10B981";
    private static final String GREEN_DARK    = "#065F46";
    private static final String GRAY_TEXT     = "#6B7280";
    private static final String DARK_TEXT     = "#1F2937";

    public PsikologDashboardView() {
        initializeUI();
    }

    private void initializeUI() {
        this.setStyle("-fx-background-color: #F0FDF4;");

        // ===== TOP BAR =====
        HBox topBar = new HBox(15);
        topBar.setPadding(new Insets(16, 30, 16, 24));
        topBar.setStyle("-fx-background-color: #065F46; -fx-effect: dropshadow(gaussian, rgba(0,0,0,0.15), 8, 0, 0, 2);");
        topBar.setAlignment(Pos.CENTER_LEFT);

        HBox logoBox = new HBox(10);
        logoBox.setAlignment(Pos.CENTER_LEFT);
        Circle logoDot = new Circle(8);
        logoDot.setFill(Color.web("#10B981"));
        Label logoLabel = new Label("VibeCare Psikolog Panel");
        logoLabel.setFont(Font.font("System", FontWeight.BOLD, 20));
        logoLabel.setStyle("-fx-text-fill: white;");
        logoBox.getChildren().addAll(logoDot, logoLabel);

        Region spacer = new Region();
        HBox.setHgrow(spacer, Priority.ALWAYS);

        String name = UserSession.getLoggedInName();
        String initials = (name != null && !name.isEmpty())
                ? String.valueOf(name.charAt(0)).toUpperCase() : "P";

        StackPane avatarStack = new StackPane();
        Circle avatarCircle = new Circle(16);
        avatarCircle.setFill(Color.web("#10B981"));
        Label avatarLbl = new Label(initials);
        avatarLbl.setStyle("-fx-text-fill: white; -fx-font-weight: bold; -fx-font-size: 12px;");
        avatarStack.getChildren().addAll(avatarCircle, avatarLbl);

        Label nameLabel = new Label(name);
        nameLabel.setStyle("-fx-text-fill: #A7F3D0; -fx-font-size: 14px;");

        Button logoutBtn = new Button("Logout");
        logoutBtn.setStyle("-fx-background-color: #EF4444; -fx-text-fill: white; " +
                "-fx-font-weight: bold; -fx-font-size: 13px; -fx-padding: 8 16; " +
                "-fx-background-radius: 20; -fx-cursor: hand;");
        logoutBtn.setOnMouseEntered(e -> logoutBtn.setStyle(
                "-fx-background-color: #DC2626; -fx-text-fill: white; " +
                        "-fx-font-weight: bold; -fx-font-size: 13px; -fx-padding: 8 16; " +
                        "-fx-background-radius: 20; -fx-cursor: hand;"));
        logoutBtn.setOnMouseExited(e -> logoutBtn.setStyle(
                "-fx-background-color: #EF4444; -fx-text-fill: white; " +
                        "-fx-font-weight: bold; -fx-font-size: 13px; -fx-padding: 8 16; " +
                        "-fx-background-radius: 20; -fx-cursor: hand;"));
        logoutBtn.setOnAction(e -> logout());

        topBar.getChildren().addAll(logoBox, spacer, avatarStack, nameLabel, logoutBtn);

        // ===== SIDEBAR =====
        VBox sidebar = new VBox(4);
        sidebar.setPadding(new Insets(20, 12, 20, 12));
        sidebar.setPrefWidth(230);
        sidebar.setStyle(SIDEBAR_BG);

        Label sidebarHeader = new Label("MENU");
        sidebarHeader.setStyle("-fx-text-fill: #6EE7B7; -fx-font-size: 11px; -fx-font-weight: bold; -fx-padding: 0 0 8 8;");
        sidebar.getChildren().add(sidebarHeader);

        String[][] menuItems = {
                {"📊", "Dashboard"},
                {"📋", "Permintaan Konsultasi"},
                {"📅", "Jadwal Saya"},
                {"👤", "Pasien Saya"},
                {"📝", "Catatan Sesi"},
                {"⭐", "Ulasan & Rating"},
                {"👤", "Profil Saya"}
        };

        for (String[] item : menuItems) {
            Button menuBtn = new Button(item[0] + "  " + item[1]);
            menuBtn.setStyle(MENU_NORMAL);
            menuBtn.setMaxWidth(Double.MAX_VALUE);

            menuBtn.setOnAction(e -> {
                setActiveMenu(menuBtn);
                switchContent(item[1]);
            });
            menuBtn.setOnMouseEntered(e -> {
                if (menuBtn != activeMenuBtn) menuBtn.setStyle(MENU_HOVER);
            });
            menuBtn.setOnMouseExited(e -> {
                if (menuBtn != activeMenuBtn) menuBtn.setStyle(MENU_NORMAL);
            });

            sidebar.getChildren().add(menuBtn);

            if (item[1].equals("Dashboard")) {
                setActiveMenu(menuBtn);
            }
        }

        Region sidebarSpacer = new Region();
        VBox.setVgrow(sidebarSpacer, Priority.ALWAYS);
        Label versionLbl = new Label("VibeCare v1.0.0");
        versionLbl.setStyle("-fx-text-fill: #6EE7B7; -fx-font-size: 11px; -fx-padding: 0 0 0 8;");
        sidebar.getChildren().addAll(sidebarSpacer, versionLbl);

        // ===== CONTENT AREA =====
        contentArea = new StackPane();
        contentArea.setPadding(new Insets(30));
        contentArea.setStyle("-fx-background-color: #F0FDF4;");
        contentArea.setAlignment(Pos.TOP_LEFT);

        showDashboardContent();

        this.setTop(topBar);
        this.setLeft(sidebar);
        this.setCenter(contentArea);
    }

    private void setActiveMenu(Button btn) {
        if (activeMenuBtn != null) activeMenuBtn.setStyle(MENU_NORMAL);
        activeMenuBtn = btn;
        btn.setStyle(MENU_ACTIVE);
    }

    private void switchContent(String menu) {
        switch (menu) {
            case "Dashboard"                -> showDashboardContent();
            case "Permintaan Konsultasi"    -> showKonsultasiRequestContent();
            case "Jadwal Saya"              -> showJadwalContent();
            case "Pasien Saya"              -> showPasienContent();
            case "Catatan Sesi"             -> showCatatanSesiContent();
            case "Ulasan & Rating"          -> showUlasanContent();
            case "Profil Saya"              -> showProfilContent();
        }
    }

    // ===== DASHBOARD =====
    private void showDashboardContent() {
        VBox dashboard = new VBox(24);
        dashboard.setAlignment(Pos.TOP_LEFT);
        dashboard.setMaxWidth(Double.MAX_VALUE);

        VBox headerBox = new VBox(4);
        Label title = new Label("Dashboard Psikolog");
        title.setFont(Font.font("System", FontWeight.BOLD, 26));
        title.setStyle("-fx-text-fill: #064E3B;");
        Label subtitle = new Label("Selamat datang, " + UserSession.getLoggedInName() + "!");
        subtitle.setStyle("-fx-text-fill: " + GRAY_TEXT + "; -fx-font-size: 14px;");
        headerBox.getChildren().addAll(title, subtitle);

        // Stats
        GridPane statsGrid = new GridPane();
        statsGrid.setHgap(16);
        statsGrid.setVgap(16);
        statsGrid.setMaxWidth(Double.MAX_VALUE);

        ColumnConstraints col = new ColumnConstraints();
        col.setPercentWidth(25);
        col.setFillWidth(true);
        statsGrid.getColumnConstraints().addAll(col, col, col, col);

        statsGrid.add(createStatCard("📋", "Permintaan Baru",  "5",    "#F59E0B", "#FEF3C7"), 0, 0);
        statsGrid.add(createStatCard("📅", "Jadwal Hari Ini",  "3",    "#10B981", "#D1FAE5"), 1, 0);
        statsGrid.add(createStatCard("👤", "Total Pasien",     "48",   "#3B82F6", "#DBEAFE"), 2, 0);
        statsGrid.add(createStatCard("⭐", "Rating Rata-rata", "4.9",  "#8B5CF6", "#EDE9FE"), 3, 0);

        // Permintaan konsultasi terbaru
        VBox requestSection = new VBox(12);
        Label reqTitle = new Label("Permintaan Konsultasi Terbaru");
        reqTitle.setFont(Font.font("System", FontWeight.BOLD, 16));
        reqTitle.setStyle("-fx-text-fill: #064E3B;");

        VBox requestList = new VBox(10);
        String[][] requests = {
                {"Budi Santoso",    "Kecemasan & Stres Kerja",   "Senin, 16 Jun 2025 • 09:00", "Video Call", "BARU"},
                {"Siti Rahayu",     "Depresi Ringan",             "Senin, 16 Jun 2025 • 11:00", "Chat",       "BARU"},
                {"Andi Wijaya",     "Konsultasi Keluarga",        "Selasa, 17 Jun 2025 • 14:00","Telepon",    "PENDING"},
        };

        for (String[] req : requests) {
            requestList.getChildren().add(createRequestCard(req[0], req[1], req[2], req[3], req[4]));
        }

        requestSection.getChildren().addAll(reqTitle, requestList);

        // Jadwal hari ini
        VBox scheduleSection = new VBox(12);
        Label schedTitle = new Label("Jadwal Hari Ini");
        schedTitle.setFont(Font.font("System", FontWeight.BOLD, 16));
        schedTitle.setStyle("-fx-text-fill: #064E3B;");

        VBox schedList = new VBox(10);
        String[][] schedules = {
                {"08:00", "Maya Putri",    "Video Call",  "Berlangsung"},
                {"10:00", "Rudi Hermawan", "Chat",        "Menunggu"},
                {"14:00", "Dewi Lestari",  "Telepon",     "Menunggu"},
        };

        for (String[] sched : schedules) {
            schedList.getChildren().add(createScheduleCard(sched[0], sched[1], sched[2], sched[3]));
        }

        scheduleSection.getChildren().addAll(schedTitle, schedList);

        dashboard.getChildren().addAll(headerBox, statsGrid, requestSection, scheduleSection);

        ScrollPane scroll = new ScrollPane(dashboard);
        scroll.setFitToWidth(true);
        scroll.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        scroll.setStyle("-fx-background-color: #F0FDF4; -fx-background: #F0FDF4;");
        contentArea.getChildren().setAll(scroll);
    }

    private VBox createStatCard(String icon, String title, String value, String valueColor, String bgColor) {
        VBox card = new VBox(10);
        card.setAlignment(Pos.CENTER_LEFT);
        card.setPadding(new Insets(20));
        card.setMaxWidth(Double.MAX_VALUE);
        card.setStyle("-fx-background-color: white; -fx-background-radius: 14; " +
                "-fx-effect: dropshadow(gaussian, rgba(0,0,0,0.06), 8, 0, 0, 2);");

        StackPane iconBadge = new StackPane();
        javafx.scene.shape.Rectangle iconBg = new javafx.scene.shape.Rectangle(40, 40);
        iconBg.setArcWidth(10);
        iconBg.setArcHeight(10);
        iconBg.setFill(Color.web(bgColor));
        Label iconLbl = new Label(icon);
        iconLbl.setFont(Font.font("System", 18));
        iconBadge.getChildren().addAll(iconBg, iconLbl);

        Label titleLabel = new Label(title);
        titleLabel.setStyle("-fx-text-fill: " + GRAY_TEXT + "; -fx-font-size: 12px;");

        Label valueLabel = new Label(value);
        valueLabel.setFont(Font.font("System", FontWeight.BOLD, 24));
        valueLabel.setStyle("-fx-text-fill: " + valueColor + ";");

        card.getChildren().addAll(iconBadge, titleLabel, valueLabel);
        return card;
    }

    // Card permintaan konsultasi
    private HBox createRequestCard(String patientName, String complaint,
                                   String schedule, String method, String status) {
        HBox card = new HBox(14);
        card.setAlignment(Pos.CENTER_LEFT);
        card.setPadding(new Insets(14, 16, 14, 16));
        card.setStyle("-fx-background-color: white; -fx-background-radius: 12; " +
                "-fx-effect: dropshadow(gaussian, rgba(0,0,0,0.05), 6, 0, 0, 2);");
        card.setMaxWidth(Double.MAX_VALUE);

        // Avatar inisial
        StackPane avatar = new StackPane();
        Circle avatarCircle = new Circle(22);
        avatarCircle.setFill(Color.web("#D1FAE5"));
        Label avatarLbl = new Label(String.valueOf(patientName.charAt(0)));
        avatarLbl.setStyle("-fx-text-fill: #065F46; -fx-font-weight: bold; -fx-font-size: 14px;");
        avatar.getChildren().addAll(avatarCircle, avatarLbl);

        VBox info = new VBox(3);
        HBox.setHgrow(info, Priority.ALWAYS);

        Label nameLbl = new Label(patientName);
        nameLbl.setFont(Font.font("System", FontWeight.BOLD, 14));
        nameLbl.setStyle("-fx-text-fill: " + DARK_TEXT + ";");

        Label complaintLbl = new Label(complaint);
        complaintLbl.setStyle("-fx-text-fill: " + GRAY_TEXT + "; -fx-font-size: 12px;");

        HBox meta = new HBox(10);
        Label scheduleLbl = new Label("🕐 " + schedule);
        scheduleLbl.setStyle("-fx-text-fill: " + GRAY_TEXT + "; -fx-font-size: 11px;");
        Label methodLbl = new Label("• " + method);
        methodLbl.setStyle("-fx-text-fill: " + GRAY_TEXT + "; -fx-font-size: 11px;");
        meta.getChildren().addAll(scheduleLbl, methodLbl);

        info.getChildren().addAll(nameLbl, complaintLbl, meta);

        // Status badge
        String badgeBg    = status.equals("BARU")    ? "#FEF3C7" : "#EDE9FE";
        String badgeColor = status.equals("BARU")    ? "#92400E" : "#5B21B6";
        Label statusBadge = new Label(status);
        statusBadge.setStyle("-fx-background-color: " + badgeBg + "; -fx-text-fill: " + badgeColor + "; " +
                "-fx-font-size: 11px; -fx-font-weight: bold; " +
                "-fx-background-radius: 20; -fx-padding: 4 10 4 10;");

        // Tombol ACC & Tolak
        VBox actionBox = new VBox(6);
        actionBox.setAlignment(Pos.CENTER);

        Button accBtn = new Button("✓ ACC");
        accBtn.setStyle("-fx-background-color: #10B981; -fx-text-fill: white; " +
                "-fx-font-size: 12px; -fx-font-weight: bold; -fx-padding: 6 12; " +
                "-fx-background-radius: 20; -fx-cursor: hand;");
        accBtn.setOnAction(e -> showAccDialog(patientName, schedule, method));

        Button tolakBtn = new Button("✕ Tolak");
        tolakBtn.setStyle("-fx-background-color: #FEE2E2; -fx-text-fill: #DC2626; " +
                "-fx-font-size: 12px; -fx-font-weight: bold; -fx-padding: 6 12; " +
                "-fx-background-radius: 20; -fx-cursor: hand;");
        tolakBtn.setOnAction(e -> showTolakDialog(patientName));

        actionBox.getChildren().addAll(accBtn, tolakBtn);

        card.getChildren().addAll(avatar, info, statusBadge, actionBox);
        return card;
    }

    // Card jadwal hari ini
    private HBox createScheduleCard(String time, String patient, String method, String status) {
        HBox card = new HBox(14);
        card.setAlignment(Pos.CENTER_LEFT);
        card.setPadding(new Insets(12, 16, 12, 16));
        card.setMaxWidth(Double.MAX_VALUE);

        String cardBg = status.equals("Berlangsung") ? "#ECFDF5" : "white";
        card.setStyle("-fx-background-color: " + cardBg + "; -fx-background-radius: 12; " +
                "-fx-effect: dropshadow(gaussian, rgba(0,0,0,0.05), 6, 0, 0, 2);");

        // Waktu
        VBox timeBox = new VBox(2);
        timeBox.setAlignment(Pos.CENTER);
        timeBox.setMinWidth(50);
        Label timeLbl = new Label(time);
        timeLbl.setFont(Font.font("System", FontWeight.BOLD, 15));
        timeLbl.setStyle("-fx-text-fill: " + GREEN_DARK + ";");
        timeBox.getChildren().add(timeLbl);

        // Garis vertikal
        javafx.scene.shape.Rectangle divider = new javafx.scene.shape.Rectangle(2, 40);
        divider.setFill(Color.web("#D1FAE5"));

        VBox info = new VBox(4);
        HBox.setHgrow(info, Priority.ALWAYS);

        Label patientLbl = new Label(patient);
        patientLbl.setFont(Font.font("System", FontWeight.BOLD, 14));
        patientLbl.setStyle("-fx-text-fill: " + DARK_TEXT + ";");

        Label methodLbl = new Label(method);
        methodLbl.setStyle("-fx-text-fill: " + GRAY_TEXT + "; -fx-font-size: 12px;");

        info.getChildren().addAll(patientLbl, methodLbl);

        // Status badge
        String badgeBg    = status.equals("Berlangsung") ? "#D1FAE5" : "#F3F4F6";
        String badgeColor = status.equals("Berlangsung") ? "#065F46" : GRAY_TEXT;
        Label statusLbl = new Label(status.equals("Berlangsung") ? "● " + status : status);
        statusLbl.setStyle("-fx-background-color: " + badgeBg + "; -fx-text-fill: " + badgeColor + "; " +
                "-fx-font-size: 11px; -fx-font-weight: bold; " +
                "-fx-background-radius: 20; -fx-padding: 4 10 4 10;");

        card.getChildren().addAll(timeBox, divider, info, statusLbl);
        return card;
    }

    // ===== PERMINTAAN KONSULTASI =====
    private void showKonsultasiRequestContent() {
        VBox content = new VBox(16);
        content.setMaxWidth(Double.MAX_VALUE);

        Label title = new Label("Permintaan Konsultasi");
        title.setFont(Font.font("System", FontWeight.BOLD, 24));
        title.setStyle("-fx-text-fill: #064E3B;");

        Label info = new Label("Terima atau tolak permintaan konsultasi dari pasien.");
        info.setStyle("-fx-text-fill: " + GRAY_TEXT + "; -fx-font-size: 14px;");

        // Filter bar
        HBox filterBar = new HBox(10);
        String[] filters = {"Semua", "Baru", "Pending", "Diterima", "Ditolak"};
        ToggleGroup tg = new ToggleGroup();
        for (int i = 0; i < filters.length; i++) {
            ToggleButton btn = new ToggleButton(filters[i]);
            btn.setToggleGroup(tg);
            if (i == 0) btn.setSelected(true);
            styleFilterButton(btn);
            btn.selectedProperty().addListener((obs, was, is) -> styleFilterButton(btn));
            filterBar.getChildren().add(btn);
        }

        VBox requestList = new VBox(10);
        String[][] requests = {
                {"Budi Santoso",    "Kecemasan & Stres Kerja",    "Senin, 16 Jun 2025 • 09:00",  "Video Call", "BARU"},
                {"Siti Rahayu",     "Depresi Ringan",              "Senin, 16 Jun 2025 • 11:00",  "Chat",       "BARU"},
                {"Andi Wijaya",     "Konsultasi Keluarga",         "Selasa, 17 Jun 2025 • 14:00", "Telepon",    "PENDING"},
                {"Rina Kusuma",     "Trauma & PTSD",               "Rabu, 18 Jun 2025 • 09:00",   "Video Call", "BARU"},
                {"Doni Prasetyo",   "Gangguan Tidur",              "Kamis, 19 Jun 2025 • 10:00",  "Chat",       "PENDING"},
        };

        for (String[] req : requests) {
            requestList.getChildren().add(createRequestCard(req[0], req[1], req[2], req[3], req[4]));
        }

        content.getChildren().addAll(title, info, filterBar, requestList);

        ScrollPane scroll = new ScrollPane(content);
        scroll.setFitToWidth(true);
        scroll.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        scroll.setStyle("-fx-background-color: #F0FDF4; -fx-background: #F0FDF4;");
        contentArea.getChildren().setAll(scroll);
    }

    // ===== JADWAL =====
    private void showJadwalContent() {
        VBox content = new VBox(16);
        content.setMaxWidth(Double.MAX_VALUE);

        Label title = new Label("Jadwal Saya");
        title.setFont(Font.font("System", FontWeight.BOLD, 24));
        title.setStyle("-fx-text-fill: #064E3B;");

        // Hari tersedia
        VBox availSection = new VBox(10);
        Label availTitle = new Label("Hari Praktek");
        availTitle.setFont(Font.font("System", FontWeight.BOLD, 15));
        availTitle.setStyle("-fx-text-fill: " + DARK_TEXT + ";");

        HBox dayChips = new HBox(8);
        String[] days = {"Senin", "Selasa", "Rabu", "Kamis", "Jumat"};
        for (String day : days) {
            Label chip = new Label(day);
            chip.setStyle("-fx-background-color: #D1FAE5; -fx-text-fill: #065F46; " +
                    "-fx-font-weight: bold; -fx-font-size: 13px; " +
                    "-fx-background-radius: 20; -fx-padding: 8 16 8 16;");
            dayChips.getChildren().add(chip);
        }
        availSection.getChildren().addAll(availTitle, dayChips);

        // Jadwal minggu ini
        VBox weekSection = new VBox(10);
        Label weekTitle = new Label("Jadwal Minggu Ini");
        weekTitle.setFont(Font.font("System", FontWeight.BOLD, 15));
        weekTitle.setStyle("-fx-text-fill: " + DARK_TEXT + ";");

        VBox schedList = new VBox(8);
        String[][] schedules = {
                {"Senin, 16 Jun",   "08:00", "Maya Putri",     "Video Call",  "Berlangsung"},
                {"Senin, 16 Jun",   "10:00", "Rudi Hermawan",  "Chat",        "Menunggu"},
                {"Senin, 16 Jun",   "14:00", "Dewi Lestari",   "Telepon",     "Menunggu"},
                {"Selasa, 17 Jun",  "09:00", "Andi Wijaya",    "Telepon",     "Menunggu"},
                {"Rabu, 18 Jun",    "11:00", "Rina Kusuma",    "Video Call",  "Menunggu"},
        };

        for (String[] s : schedules) {
            HBox row = new HBox(14);
            row.setAlignment(Pos.CENTER_LEFT);
            row.setPadding(new Insets(12, 16, 12, 16));
            row.setStyle("-fx-background-color: white; -fx-background-radius: 12; " +
                    "-fx-effect: dropshadow(gaussian, rgba(0,0,0,0.04), 6, 0, 0, 2);");

            Label dateLbl = new Label(s[0]);
            dateLbl.setStyle("-fx-text-fill: " + GRAY_TEXT + "; -fx-font-size: 12px;");
            dateLbl.setMinWidth(110);

            Label timeLbl = new Label(s[1]);
            timeLbl.setFont(Font.font("System", FontWeight.BOLD, 14));
            timeLbl.setStyle("-fx-text-fill: " + GREEN_DARK + ";");
            timeLbl.setMinWidth(55);

            Label patientLbl = new Label(s[2]);
            patientLbl.setFont(Font.font("System", FontWeight.BOLD, 13));
            patientLbl.setStyle("-fx-text-fill: " + DARK_TEXT + ";");
            HBox.setHgrow(patientLbl, Priority.ALWAYS);

            Label methodLbl = new Label(s[3]);
            methodLbl.setStyle("-fx-text-fill: " + GRAY_TEXT + "; -fx-font-size: 12px;");
            methodLbl.setMinWidth(80);

            String badgeBg    = s[4].equals("Berlangsung") ? "#D1FAE5" : "#F3F4F6";
            String badgeColor = s[4].equals("Berlangsung") ? "#065F46" : GRAY_TEXT;
            Label statusLbl = new Label(s[4]);
            statusLbl.setStyle("-fx-background-color: " + badgeBg + "; -fx-text-fill: " + badgeColor + "; " +
                    "-fx-font-size: 11px; -fx-font-weight: bold; " +
                    "-fx-background-radius: 20; -fx-padding: 4 10 4 10;");

            row.getChildren().addAll(dateLbl, timeLbl, patientLbl, methodLbl, statusLbl);
            schedList.getChildren().add(row);
        }

        weekSection.getChildren().addAll(weekTitle, schedList);
        content.getChildren().addAll(title, availSection, weekSection);

        ScrollPane scroll = new ScrollPane(content);
        scroll.setFitToWidth(true);
        scroll.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        scroll.setStyle("-fx-background-color: #F0FDF4; -fx-background: #F0FDF4;");
        contentArea.getChildren().setAll(scroll);
    }

    // ===== PASIEN SAYA =====
    private void showPasienContent() {
        VBox content = new VBox(16);
        content.setMaxWidth(Double.MAX_VALUE);

        Label title = new Label("Pasien Saya");
        title.setFont(Font.font("System", FontWeight.BOLD, 24));
        title.setStyle("-fx-text-fill: #064E3B;");

        Label info = new Label("Daftar pasien yang pernah atau sedang berkonsultasi dengan Anda.");
        info.setStyle("-fx-text-fill: " + GRAY_TEXT + "; -fx-font-size: 14px;");

        VBox pasienList = new VBox(10);
        String[][] patients = {
                {"Maya Putri",    "28 thn", "Kecemasan",          "3 sesi",  "Aktif"},
                {"Rudi Hermawan", "35 thn", "Stres Kerja",        "5 sesi",  "Aktif"},
                {"Dewi Lestari",  "22 thn", "Depresi Ringan",     "2 sesi",  "Aktif"},
                {"Andi Wijaya",   "42 thn", "Konsultasi Keluarga","7 sesi",  "Selesai"},
                {"Rina Kusuma",   "31 thn", "Trauma",             "4 sesi",  "Aktif"},
                {"Doni Prasetyo", "26 thn", "Gangguan Tidur",     "1 sesi",  "Baru"},
        };

        for (String[] p : patients) {
            HBox card = new HBox(14);
            card.setAlignment(Pos.CENTER_LEFT);
            card.setPadding(new Insets(14, 16, 14, 16));
            card.setStyle("-fx-background-color: white; -fx-background-radius: 12; " +
                    "-fx-effect: dropshadow(gaussian, rgba(0,0,0,0.05), 6, 0, 0, 2);");

            StackPane avatar = new StackPane();
            Circle avatarCircle = new Circle(22);
            avatarCircle.setFill(Color.web("#D1FAE5"));
            Label avatarLbl = new Label(String.valueOf(p[0].charAt(0)));
            avatarLbl.setStyle("-fx-text-fill: #065F46; -fx-font-weight: bold; -fx-font-size: 14px;");
            avatar.getChildren().addAll(avatarCircle, avatarLbl);

            VBox info2 = new VBox(3);
            HBox.setHgrow(info2, Priority.ALWAYS);

            Label nameLbl = new Label(p[0]);
            nameLbl.setFont(Font.font("System", FontWeight.BOLD, 14));
            nameLbl.setStyle("-fx-text-fill: " + DARK_TEXT + ";");

            Label detailLbl = new Label(p[1] + " • " + p[2] + " • " + p[3]);
            detailLbl.setStyle("-fx-text-fill: " + GRAY_TEXT + "; -fx-font-size: 12px;");

            info2.getChildren().addAll(nameLbl, detailLbl);

            String badgeBg    = p[4].equals("Aktif") ? "#D1FAE5"
                    : p[4].equals("Baru")  ? "#FEF3C7" : "#F3F4F6";
            String badgeColor = p[4].equals("Aktif") ? "#065F46"
                    : p[4].equals("Baru")  ? "#92400E" : GRAY_TEXT;
            Label statusLbl = new Label(p[4]);
            statusLbl.setStyle("-fx-background-color: " + badgeBg + "; -fx-text-fill: " + badgeColor + "; " +
                    "-fx-font-size: 11px; -fx-font-weight: bold; " +
                    "-fx-background-radius: 20; -fx-padding: 4 10 4 10;");

            Button lihatBtn = new Button("Lihat Detail");
            lihatBtn.setStyle("-fx-background-color: #D1FAE5; -fx-text-fill: #065F46; " +
                    "-fx-font-size: 12px; -fx-font-weight: bold; -fx-padding: 6 12; " +
                    "-fx-background-radius: 20; -fx-cursor: hand;");
            lihatBtn.setOnAction(e -> showInfoDialog("Detail Pasien",
                    "Nama: " + p[0] + "\nUsia: " + p[1] + "\nKeluhan: " + p[2] + "\nTotal Sesi: " + p[3]));

            card.getChildren().addAll(avatar, info2, statusLbl, lihatBtn);
            pasienList.getChildren().add(card);
        }

        content.getChildren().addAll(title, info, pasienList);

        ScrollPane scroll = new ScrollPane(content);
        scroll.setFitToWidth(true);
        scroll.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        scroll.setStyle("-fx-background-color: #F0FDF4; -fx-background: #F0FDF4;");
        contentArea.getChildren().setAll(scroll);
    }

    // ===== CATATAN SESI =====
    private void showCatatanSesiContent() {
        VBox content = new VBox(16);
        content.setMaxWidth(Double.MAX_VALUE);

        Label title = new Label("Catatan Sesi");
        title.setFont(Font.font("System", FontWeight.BOLD, 24));
        title.setStyle("-fx-text-fill: #064E3B;");

        Label info = new Label("Tulis dan kelola catatan setiap sesi konsultasi.");
        info.setStyle("-fx-text-fill: " + GRAY_TEXT + "; -fx-font-size: 14px;");

        // Form tulis catatan
        VBox formCard = new VBox(14);
        formCard.setPadding(new Insets(20));
        formCard.setStyle("-fx-background-color: white; -fx-background-radius: 14; " +
                "-fx-effect: dropshadow(gaussian, rgba(0,0,0,0.06), 8, 0, 0, 2);");

        Label formTitle = new Label("✏️ Tulis Catatan Sesi Baru");
        formTitle.setFont(Font.font("System", FontWeight.BOLD, 15));
        formTitle.setStyle("-fx-text-fill: " + DARK_TEXT + ";");

        ComboBox<String> patientCombo = new ComboBox<>();
        patientCombo.getItems().addAll("Maya Putri", "Rudi Hermawan", "Dewi Lestari",
                "Andi Wijaya", "Rina Kusuma", "Doni Prasetyo");
        patientCombo.setPromptText("Pilih Pasien");
        patientCombo.setMaxWidth(Double.MAX_VALUE);

        TextArea catatanArea = new TextArea();
        catatanArea.setPromptText("Tulis ringkasan sesi, perkembangan pasien, dan rencana tindak lanjut...");
        catatanArea.setPrefHeight(120);
        catatanArea.setWrapText(true);

        Button simpanBtn = new Button("💾 Simpan Catatan");
        simpanBtn.setStyle("-fx-background-color: #10B981; -fx-text-fill: white; " +
                "-fx-font-weight: bold; -fx-font-size: 13px; -fx-padding: 10 20; " +
                "-fx-background-radius: 20; -fx-cursor: hand;");
        simpanBtn.setOnAction(e -> {
            if (patientCombo.getValue() == null || catatanArea.getText().isEmpty()) {
                showInfoDialog("Peringatan", "Pilih pasien dan isi catatan terlebih dahulu.");
            } else {
                showInfoDialog("Berhasil", "Catatan sesi untuk " + patientCombo.getValue() + " berhasil disimpan!");
                catatanArea.clear();
                patientCombo.setValue(null);
            }
        });

        formCard.getChildren().addAll(formTitle,
                makeLabeledField("Pasien", patientCombo),
                makeLabeledField("Catatan", catatanArea),
                simpanBtn);

        // Catatan sebelumnya
        Label prevTitle = new Label("Catatan Sebelumnya");
        prevTitle.setFont(Font.font("System", FontWeight.BOLD, 15));
        prevTitle.setStyle("-fx-text-fill: " + DARK_TEXT + ";");

        VBox prevList = new VBox(10);
        String[][] notes = {
                {"Maya Putri",    "12 Jun 2025", "Pasien menunjukkan perkembangan positif. Kecemasan berkurang. Lanjutkan teknik pernapasan."},
                {"Rudi Hermawan", "10 Jun 2025", "Stres kerja masih tinggi. Disarankan untuk menetapkan batasan waktu kerja. Sesi berikutnya fokus pada manajemen waktu."},
                {"Dewi Lestari",  "8 Jun 2025",  "Sesi pertama. Pasien masih tertutup. Perlu pendekatan lebih lanjut untuk membangun kepercayaan."},
        };

        for (String[] note : notes) {
            VBox noteCard = new VBox(8);
            noteCard.setPadding(new Insets(14));
            noteCard.setStyle("-fx-background-color: white; -fx-background-radius: 12; " +
                    "-fx-effect: dropshadow(gaussian, rgba(0,0,0,0.04), 6, 0, 0, 2);");

            HBox noteHeader = new HBox(10);
            noteHeader.setAlignment(Pos.CENTER_LEFT);
            Label noteName = new Label(note[0]);
            noteName.setFont(Font.font("System", FontWeight.BOLD, 13));
            noteName.setStyle("-fx-text-fill: " + DARK_TEXT + ";");
            Region noteSpacer = new Region();
            HBox.setHgrow(noteSpacer, Priority.ALWAYS);
            Label noteDate = new Label(note[1]);
            noteDate.setStyle("-fx-text-fill: " + GRAY_TEXT + "; -fx-font-size: 11px;");
            noteHeader.getChildren().addAll(noteName, noteSpacer, noteDate);

            Label noteText = new Label(note[2]);
            noteText.setStyle("-fx-text-fill: " + GRAY_TEXT + "; -fx-font-size: 12px;");
            noteText.setWrapText(true);

            noteCard.getChildren().addAll(noteHeader, noteText);
            prevList.getChildren().add(noteCard);
        }

        content.getChildren().addAll(title, info, formCard, prevTitle, prevList);

        ScrollPane scroll = new ScrollPane(content);
        scroll.setFitToWidth(true);
        scroll.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        scroll.setStyle("-fx-background-color: #F0FDF4; -fx-background: #F0FDF4;");
        contentArea.getChildren().setAll(scroll);
    }

    // ===== ULASAN & RATING =====
    private void showUlasanContent() {
        VBox content = new VBox(16);
        content.setMaxWidth(Double.MAX_VALUE);

        Label title = new Label("Ulasan & Rating");
        title.setFont(Font.font("System", FontWeight.BOLD, 24));
        title.setStyle("-fx-text-fill: #064E3B;");

        // Summary card
        HBox summaryCard = new HBox(30);
        summaryCard.setPadding(new Insets(20));
        summaryCard.setAlignment(Pos.CENTER_LEFT);
        summaryCard.setStyle("-fx-background-color: white; -fx-background-radius: 14; " +
                "-fx-effect: dropshadow(gaussian, rgba(0,0,0,0.06), 8, 0, 0, 2);");

        VBox ratingMain = new VBox(4);
        ratingMain.setAlignment(Pos.CENTER);
        Label ratingNum = new Label("4.9");
        ratingNum.setFont(Font.font("System", FontWeight.BOLD, 42));
        ratingNum.setStyle("-fx-text-fill: #064E3B;");
        Label stars = new Label("⭐⭐⭐⭐⭐");
        stars.setStyle("-fx-font-size: 16px;");
        Label totalReview = new Label("dari 48 ulasan");
        totalReview.setStyle("-fx-text-fill: " + GRAY_TEXT + "; -fx-font-size: 12px;");
        ratingMain.getChildren().addAll(ratingNum, stars, totalReview);

        VBox ratingBars = new VBox(6);
        HBox.setHgrow(ratingBars, Priority.ALWAYS);
        int[] counts = {38, 7, 2, 1, 0};
        for (int i = 5; i >= 1; i--) {
            HBox barRow = new HBox(8);
            barRow.setAlignment(Pos.CENTER_LEFT);
            Label starLbl = new Label(i + "⭐");
            starLbl.setStyle("-fx-font-size: 12px;");
            starLbl.setMinWidth(30);
            ProgressBar bar = new ProgressBar((double) counts[5 - i] / 48);
            bar.setPrefWidth(150);
            bar.setStyle("-fx-accent: #10B981;");
            Label countLbl = new Label(String.valueOf(counts[5 - i]));
            countLbl.setStyle("-fx-text-fill: " + GRAY_TEXT + "; -fx-font-size: 12px;");
            barRow.getChildren().addAll(starLbl, bar, countLbl);
            ratingBars.getChildren().add(barRow);
        }

        summaryCard.getChildren().addAll(ratingMain, ratingBars);

        // Daftar ulasan
        Label reviewsTitle = new Label("Ulasan Terbaru");
        reviewsTitle.setFont(Font.font("System", FontWeight.BOLD, 15));
        reviewsTitle.setStyle("-fx-text-fill: " + DARK_TEXT + ";");

        VBox reviewList = new VBox(10);
        String[][] reviews = {
                {"Maya Putri",    "5", "Sangat membantu! Dr. sangat sabar dan penuh empati. Saya merasa jauh lebih baik setelah sesi.",          "2 hari lalu"},
                {"Rudi Hermawan", "5", "Penjelasannya mudah dipahami dan sangat relevan dengan masalah saya. Highly recommended!",                "5 hari lalu"},
                {"Andi Wijaya",   "4", "Sesi yang sangat bermanfaat. Beberapa saran langsung bisa saya terapkan dalam kehidupan sehari-hari.",   "1 minggu lalu"},
        };

        for (String[] r : reviews) {
            VBox reviewCard = new VBox(8);
            reviewCard.setPadding(new Insets(14));
            reviewCard.setStyle("-fx-background-color: white; -fx-background-radius: 12; " +
                    "-fx-effect: dropshadow(gaussian, rgba(0,0,0,0.04), 6, 0, 0, 2);");

            HBox reviewHeader = new HBox(10);
            reviewHeader.setAlignment(Pos.CENTER_LEFT);

            StackPane rAvatar = new StackPane();
            Circle rCircle = new Circle(18);
            rCircle.setFill(Color.web("#D1FAE5"));
            Label rInit = new Label(String.valueOf(r[0].charAt(0)));
            rInit.setStyle("-fx-text-fill: #065F46; -fx-font-weight: bold; -fx-font-size: 12px;");
            rAvatar.getChildren().addAll(rCircle, rInit);

            VBox rInfo = new VBox(2);
            HBox.setHgrow(rInfo, Priority.ALWAYS);
            Label rName = new Label(r[0]);
            rName.setFont(Font.font("System", FontWeight.BOLD, 13));
            rName.setStyle("-fx-text-fill: " + DARK_TEXT + ";");
            Label rStars = new Label("⭐".repeat(Integer.parseInt(r[1])));
            rStars.setStyle("-fx-font-size: 12px;");
            rInfo.getChildren().addAll(rName, rStars);

            Label rDate = new Label(r[3]);
            rDate.setStyle("-fx-text-fill: " + GRAY_TEXT + "; -fx-font-size: 11px;");

            reviewHeader.getChildren().addAll(rAvatar, rInfo, rDate);

            Label rText = new Label(r[2]);
            rText.setStyle("-fx-text-fill: " + GRAY_TEXT + "; -fx-font-size: 12px;");
            rText.setWrapText(true);

            reviewCard.getChildren().addAll(reviewHeader, rText);
            reviewList.getChildren().add(reviewCard);
        }

        content.getChildren().addAll(title, summaryCard, reviewsTitle, reviewList);

        ScrollPane scroll = new ScrollPane(content);
        scroll.setFitToWidth(true);
        scroll.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        scroll.setStyle("-fx-background-color: #F0FDF4; -fx-background: #F0FDF4;");
        contentArea.getChildren().setAll(scroll);
    }

    // ===== PROFIL PSIKIATER =====
    private void showProfilContent() {
        VBox content = new VBox(20);
        content.setMaxWidth(Double.MAX_VALUE);

        Label title = new Label("Profil Saya");
        title.setFont(Font.font("System", FontWeight.BOLD, 24));
        title.setStyle("-fx-text-fill: #064E3B;");

        VBox profileCard = new VBox(16);
        profileCard.setPadding(new Insets(24));
        profileCard.setStyle("-fx-background-color: white; -fx-background-radius: 14; " +
                "-fx-effect: dropshadow(gaussian, rgba(0,0,0,0.06), 8, 0, 0, 2);");

        String docName = UserSession.getLoggedInName();
        String initials = docName != null && !docName.isEmpty()
                ? String.valueOf(docName.charAt(0)).toUpperCase() : "P";

        StackPane avatarStack = new StackPane();
        Circle avatarBg = new Circle(45);
        avatarBg.setFill(Color.web("#10B981"));
        Label avatarLbl = new Label(initials);
        avatarLbl.setFont(Font.font("System", FontWeight.BOLD, 28));
        avatarLbl.setStyle("-fx-text-fill: white;");
        avatarStack.getChildren().addAll(avatarBg, avatarLbl);
        avatarStack.setMaxWidth(90);

        VBox nameBox = new VBox(4);
        Label nameLbl = new Label(docName);
        nameLbl.setFont(Font.font("System", FontWeight.BOLD, 20));
        nameLbl.setStyle("-fx-text-fill: " + DARK_TEXT + ";");
        Label emailLbl = new Label(UserSession.getLoggedInUser());
        emailLbl.setStyle("-fx-text-fill: " + GRAY_TEXT + "; -fx-font-size: 13px;");
        Label roleLbl = new Label("🩺 Psikolog Klinis");
        roleLbl.setStyle("-fx-background-color: #D1FAE5; -fx-text-fill: #065F46; " +
                "-fx-font-size: 12px; -fx-font-weight: bold; " +
                "-fx-background-radius: 20; -fx-padding: 4 12 4 12;");
        nameBox.getChildren().addAll(nameLbl, emailLbl, roleLbl);

        HBox profileHeader = new HBox(16);
        profileHeader.setAlignment(Pos.CENTER_LEFT);
        profileHeader.getChildren().addAll(avatarStack, nameBox);

        Separator sep = new Separator();

        GridPane fields = new GridPane();
        fields.setHgap(20);
        fields.setVgap(14);

        TextField namaField = new TextField(docName);
        TextField spesialisasiField = new TextField("Psikolog Klinis");
        TextField pengalamanField = new TextField("10 Tahun");
        TextField lisensiField = new TextField("SIPP-12345/2024");
        TextArea bioArea = new TextArea("Berpengalaman dalam menangani gangguan kecemasan, depresi, dan trauma.");
        bioArea.setPrefHeight(80);
        bioArea.setWrapText(true);

        fields.add(makeLabeledField("Nama Lengkap", namaField), 0, 0);
        fields.add(makeLabeledField("Spesialisasi", spesialisasiField), 1, 0);
        fields.add(makeLabeledField("Pengalaman", pengalamanField), 0, 1);
        fields.add(makeLabeledField("No. Lisensi (SIPP)", lisensiField), 1, 1);

        ColumnConstraints gridCol = new ColumnConstraints();
        gridCol.setPercentWidth(50);
        fields.getColumnConstraints().addAll(gridCol, gridCol);

        VBox bioBox = makeLabeledField("Bio / Deskripsi", bioArea);

        Button simpanBtn = new Button("💾 Simpan Perubahan");
        simpanBtn.setStyle("-fx-background-color: #10B981; -fx-text-fill: white; " +
                "-fx-font-weight: bold; -fx-font-size: 13px; -fx-padding: 10 20; " +
                "-fx-background-radius: 20; -fx-cursor: hand;");
        simpanBtn.setOnAction(e -> showInfoDialog("Berhasil", "Profil berhasil diperbarui!"));

        profileCard.getChildren().addAll(profileHeader, sep, fields, bioBox, simpanBtn);
        content.getChildren().addAll(title, profileCard);

        ScrollPane scroll = new ScrollPane(content);
        scroll.setFitToWidth(true);
        scroll.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        scroll.setStyle("-fx-background-color: #F0FDF4; -fx-background: #F0FDF4;");
        contentArea.getChildren().setAll(scroll);
    }

    // ===== DIALOG ACC =====
    private void showAccDialog(String patientName, String schedule, String method) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Terima Konsultasi");
        alert.setHeaderText(null);
        alert.setContentText("Terima permintaan konsultasi dari " + patientName +
                "?\n\nJadwal: " + schedule + "\nMetode: " + method);

        Button okBtn = (Button) alert.getDialogPane().lookupButton(ButtonType.OK);
        okBtn.setText("Ya, Terima");
        ((Button) alert.getDialogPane().lookupButton(ButtonType.CANCEL)).setText("Batal");

        alert.showAndWait().ifPresent(result -> {
            if (result == ButtonType.OK) {
                showInfoDialog("Berhasil",
                        "Konsultasi dengan " + patientName + " telah diterima!\n" +
                                "Pasien akan mendapat notifikasi konfirmasi.");
            }
        });
    }

    // ===== DIALOG TOLAK =====
    private void showTolakDialog(String patientName) {
        Dialog<ButtonType> dialog = new Dialog<>();
        dialog.setTitle("Tolak Konsultasi");
        dialog.setHeaderText(null);

        VBox content = new VBox(12);
        content.setPadding(new Insets(20));

        Label lbl = new Label("Alasan penolakan (akan dikirim ke pasien):");
        lbl.setStyle("-fx-text-fill: " + DARK_TEXT + "; -fx-font-weight: bold;");

        TextArea alasanArea = new TextArea();
        alasanArea.setPromptText("Contoh: Jadwal penuh, silakan pilih waktu lain...");
        alasanArea.setPrefHeight(80);
        alasanArea.setWrapText(true);

        content.getChildren().addAll(lbl, alasanArea);
        dialog.getDialogPane().setContent(content);
        dialog.getDialogPane().getButtonTypes().addAll(ButtonType.OK, ButtonType.CANCEL);

        ((Button) dialog.getDialogPane().lookupButton(ButtonType.OK)).setText("Tolak Permintaan");
        ((Button) dialog.getDialogPane().lookupButton(ButtonType.CANCEL)).setText("Batal");

        dialog.showAndWait().ifPresent(result -> {
            if (result == ButtonType.OK) {
                showInfoDialog("Ditolak",
                        "Permintaan konsultasi dari " + patientName + " telah ditolak.\n" +
                                "Pasien akan mendapat notifikasi.");
            }
        });
    }

    // ===== HELPER =====
    private void styleFilterButton(ToggleButton btn) {
        if (btn.isSelected()) {
            btn.setStyle("-fx-background-color: #10B981; -fx-text-fill: white; " +
                    "-fx-background-radius: 20; -fx-font-size: 12px; -fx-padding: 6 16; -fx-cursor: hand;");
        } else {
            btn.setStyle("-fx-background-color: white; -fx-text-fill: " + GRAY_TEXT + "; " +
                    "-fx-border-color: #E5E7EB; -fx-border-radius: 20; -fx-border-width: 1; " +
                    "-fx-background-radius: 20; -fx-font-size: 12px; -fx-padding: 6 16; -fx-cursor: hand;");
        }
    }

    private VBox makeLabeledField(String labelText, Control field) {
        VBox box = new VBox(5);
        Label lbl = new Label(labelText);
        lbl.setStyle("-fx-text-fill: " + GRAY_TEXT + "; -fx-font-size: 13px; -fx-font-weight: bold;");
        box.getChildren().addAll(lbl, field);
        return box;
    }

    private void showInfoDialog(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        ((Button) alert.getDialogPane().lookupButton(ButtonType.OK)).setText("Oke");
        alert.showAndWait();
    }

    private void logout() {
        UserSession.logout();
        javafx.application.Platform.runLater(() -> {
            javafx.stage.Stage stage = (javafx.stage.Stage) this.getScene().getWindow();
            MainWindow mw = new MainWindow();
            javafx.scene.Scene scene = new javafx.scene.Scene(mw, 1200, 700);
            stage.setScene(scene);
            stage.setTitle("VibeCare - Aplikasi Kesehatan Mental");
            stage.show();
        });
    }
}