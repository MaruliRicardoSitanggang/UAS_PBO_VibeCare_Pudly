package com.CareVibe.view;

import com.CareVibe.model.UserSession;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

public class AdminDashboardView extends BorderPane {

    private MainWindow mainWindow;
    private StackPane contentArea;
    private Button activeMenuBtn = null;

    private static final String SIDEBAR_BG  = "-fx-background-color: #064E3B;";
    private static final String MENU_NORMAL = "-fx-background-color: transparent; -fx-text-fill: #A7F3D0; -fx-font-size: 14px; -fx-padding: 12 16; -fx-cursor: hand; -fx-alignment: CENTER_LEFT; -fx-background-radius: 10;";
    private static final String MENU_HOVER  = "-fx-background-color: #065F46; -fx-text-fill: white; -fx-font-size: 14px; -fx-padding: 12 16; -fx-cursor: hand; -fx-alignment: CENTER_LEFT; -fx-background-radius: 10;";
    private static final String MENU_ACTIVE = "-fx-background-color: #10B981; -fx-text-fill: white; -fx-font-size: 14px; -fx-font-weight: bold; -fx-padding: 12 16; -fx-cursor: hand; -fx-alignment: CENTER_LEFT; -fx-background-radius: 10;";

    public AdminDashboardView(MainWindow mainWindow) {
        this.mainWindow = mainWindow;
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
        Label logoLabel = new Label("VibeCare Admin Panel");
        logoLabel.setFont(Font.font("System", FontWeight.BOLD, 20));
        logoLabel.setStyle("-fx-text-fill: white;");
        logoBox.getChildren().addAll(logoDot, logoLabel);

        Region spacer = new Region();
        HBox.setHgrow(spacer, Priority.ALWAYS);

        String adminName = UserSession.getLoggedInName();
        String initials  = adminName != null && !adminName.isEmpty()
                ? String.valueOf(adminName.charAt(0)).toUpperCase() : "A";

        StackPane avatarStack = new StackPane();
        Circle avatarCircle = new Circle(16);
        avatarCircle.setFill(Color.web("#10B981"));
        Label avatarLbl = new Label(initials);
        avatarLbl.setStyle("-fx-text-fill: white; -fx-font-weight: bold; -fx-font-size: 12px;");
        avatarStack.getChildren().addAll(avatarCircle, avatarLbl);

        Label adminLabel = new Label(adminName);
        adminLabel.setStyle("-fx-text-fill: #A7F3D0; -fx-font-size: 14px;");

        Button logoutBtn = new Button("Logout");
        logoutBtn.setStyle("-fx-background-color: #EF4444; -fx-text-fill: white; -fx-font-weight: bold; -fx-font-size: 13px; -fx-padding: 8 16; -fx-background-radius: 20; -fx-cursor: hand;");
        logoutBtn.setOnMouseEntered(e -> logoutBtn.setStyle("-fx-background-color: #DC2626; -fx-text-fill: white; -fx-font-weight: bold; -fx-font-size: 13px; -fx-padding: 8 16; -fx-background-radius: 20; -fx-cursor: hand;"));
        logoutBtn.setOnMouseExited(e -> logoutBtn.setStyle("-fx-background-color: #EF4444; -fx-text-fill: white; -fx-font-weight: bold; -fx-font-size: 13px; -fx-padding: 8 16; -fx-background-radius: 20; -fx-cursor: hand;"));
        logoutBtn.setOnAction(e -> logout());

        topBar.getChildren().addAll(logoBox, spacer, avatarStack, adminLabel, logoutBtn);

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
                {"🧑‍⚕️", "Kelola Psikolog"},
                {"📝", "Moderasi Postingan"}
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
                if (menuBtn != activeMenuBtn)
                    menuBtn.setStyle(MENU_HOVER);
            });
            menuBtn.setOnMouseExited(e -> {
                if (menuBtn != activeMenuBtn)
                    menuBtn.setStyle(MENU_NORMAL);
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
        if (activeMenuBtn != null) {
            activeMenuBtn.setStyle(MENU_NORMAL);
        }
        activeMenuBtn = btn;
        btn.setStyle(MENU_ACTIVE);
    }

    private void switchContent(String menu) {
        switch (menu) {
            case "Dashboard"          -> showDashboardContent();
            case "Kelola Psikolog"    -> showPsychologistManagementContent();
            case "Moderasi Postingan" -> showModerationContent();
        }
    }

    private void showDashboardContent() {
        VBox dashboard = new VBox(24);
        dashboard.setAlignment(Pos.TOP_LEFT);
        dashboard.setMaxWidth(Double.MAX_VALUE);

        VBox headerBox = new VBox(4);
        Label title = new Label("Dashboard Admin");
        title.setFont(Font.font("System", FontWeight.BOLD, 26));
        title.setStyle("-fx-text-fill: #064E3B;");
        Label subtitle = new Label("Selamat datang kembali, " + UserSession.getLoggedInName() + "!");
        subtitle.setStyle("-fx-text-fill: #6B7280; -fx-font-size: 14px;");
        headerBox.getChildren().addAll(title, subtitle);

        GridPane statsGrid = new GridPane();
        statsGrid.setHgap(16);
        statsGrid.setVgap(16);
        statsGrid.setMaxWidth(Double.MAX_VALUE);

        statsGrid.add(createStatCard("🧑‍⚕️", "Total Psikolog", "12",    "#3B82F6", "#DBEAFE"), 0, 0);
        statsGrid.add(createStatCard("📝", "Total Postingan",  "456",   "#F59E0B", "#FEF3C7"), 1, 0);
        statsGrid.add(createStatCard("✅", "Misi Selesai",     "789",   "#8B5CF6", "#EDE9FE"), 2, 0);
        statsGrid.add(createStatCard("💰", "Total Poin",       "12,345","#065F46", "#D1FAE5"), 0, 1);

        ColumnConstraints col = new ColumnConstraints();
        col.setPercentWidth(33.33);
        col.setFillWidth(true);
        statsGrid.getColumnConstraints().addAll(col, col, col);

        HBox infoBanner = new HBox(12);
        infoBanner.setAlignment(Pos.CENTER_LEFT);
        infoBanner.setPadding(new Insets(16, 20, 16, 20));
        infoBanner.setStyle("-fx-background-color: #D1FAE5; -fx-background-radius: 12;");
        Label infoIcon = new Label("ℹ️");
        infoIcon.setStyle("-fx-font-size: 16px;");
        Label infoLabel = new Label("Gunakan menu di sebelah kiri untuk mengelola psikolog dan moderasi postingan.");
        infoLabel.setStyle("-fx-text-fill: #065F46; -fx-font-size: 13px;");
        infoLabel.setWrapText(true);
        HBox.setHgrow(infoLabel, Priority.ALWAYS);
        infoBanner.getChildren().addAll(infoIcon, infoLabel);

        dashboard.getChildren().addAll(headerBox, statsGrid, infoBanner);
        contentArea.getChildren().setAll(dashboard);
    }

    private VBox createStatCard(String icon, String title, String value, String valueColor, String bgColor) {
        VBox card = new VBox(10);
        card.setAlignment(Pos.CENTER_LEFT);
        card.setPadding(new Insets(20));
        card.setMaxWidth(Double.MAX_VALUE);
        card.setStyle("-fx-background-color: white; -fx-background-radius: 14; -fx-effect: dropshadow(gaussian, rgba(0,0,0,0.06), 8, 0, 0, 2);");

        StackPane iconBadge = new StackPane();
        javafx.scene.shape.Rectangle iconBg = new javafx.scene.shape.Rectangle(40, 40);
        iconBg.setArcWidth(10);
        iconBg.setArcHeight(10);
        iconBg.setFill(Color.web(bgColor));
        Label iconLbl = new Label(icon);
        iconLbl.setFont(Font.font("System", 18));
        iconBadge.getChildren().addAll(iconBg, iconLbl);

        Label titleLabel = new Label(title);
        titleLabel.setStyle("-fx-text-fill: #6B7280; -fx-font-size: 13px;");

        Label valueLabel = new Label(value);
        valueLabel.setFont(Font.font("System", FontWeight.BOLD, 26));
        valueLabel.setStyle("-fx-text-fill: " + valueColor + ";");

        card.getChildren().addAll(iconBadge, titleLabel, valueLabel);
        return card;
    }

    private void showPlaceholderContent(String titleText, String infoText, String emoji) {
        VBox content = new VBox(16);
        content.setAlignment(Pos.TOP_LEFT);
        content.setMaxWidth(Double.MAX_VALUE);

        Label title = new Label(titleText);
        title.setFont(Font.font("System", FontWeight.BOLD, 24));
        title.setStyle("-fx-text-fill: #064E3B;");

        Label info = new Label(infoText);
        info.setStyle("-fx-text-fill: #6B7280; -fx-font-size: 14px;");

        VBox placeholder = new VBox(12);
        placeholder.setAlignment(Pos.CENTER);
        placeholder.setPadding(new Insets(60));
        placeholder.setMaxWidth(Double.MAX_VALUE);
        placeholder.setStyle("-fx-background-color: white; -fx-background-radius: 14; -fx-effect: dropshadow(gaussian, rgba(0,0,0,0.06), 8, 0, 0, 2);");

        Label placeholderIcon = new Label(emoji);
        placeholderIcon.setFont(Font.font("System", 48));

        Label placeholderText = new Label("Fitur " + titleText + " akan segera hadir");
        placeholderText.setStyle("-fx-text-fill: #9CA3AF; -fx-font-size: 14px;");

        Label placeholderSub = new Label("Sedang dalam pengembangan");
        placeholderSub.setStyle("-fx-text-fill: #D1D5DB; -fx-font-size: 12px;");

        Label badge = new Label("🚧  Coming Soon");
        badge.setStyle("-fx-background-color: #D1FAE5; -fx-text-fill: #065F46; -fx-font-size: 12px; -fx-font-weight: bold; -fx-background-radius: 20; -fx-padding: 6 14 6 14;");

        placeholder.getChildren().addAll(placeholderIcon, placeholderText, placeholderSub, badge);
        content.getChildren().addAll(title, info, placeholder);
        contentArea.getChildren().setAll(content);
    }

    private void showPsychologistManagementContent() {
        showPlaceholderContent("Kelola Psikolog",
                "Halaman ini akan menampilkan daftar psikolog, fitur tambah, edit, dan hapus psikolog.",
                "🧑‍⚕️");
    }

    private void showModerationContent() {
        showPlaceholderContent("Moderasi Postingan",
                "Halaman ini akan menampilkan daftar postingan yang perlu dimoderasi.",
                "📝");
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