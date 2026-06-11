package com.CareVibe.view;

import com.CareVibe.model.UserSession;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.util.Duration;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class CommunityView extends VBox {

    private VBox feedBox;
    private List<Post> posts = new ArrayList<>();
    private String activeFilter = "Semua";
    private String searchQuery = "";
    private TextField searchField;
    private FlowPane tagContainer;

    private static final String COLOR_BG       = "#F0FDF4";
    private static final String COLOR_PRIMARY   = "#10B981";
    private static final String COLOR_PRIMARY_D = "#059669";
    private static final String COLOR_PRIMARY_L = "#D1FAE5";
    private static final String COLOR_DARK      = "#064E3B";
    private static final String COLOR_TEXT      = "#1F2937";
    private static final String COLOR_SUBTEXT   = "#6B7280";
    private static final String COLOR_BORDER    = "#E5E7EB";
    private static final String COLOR_WHITE     = "white";
    private static final String COLOR_RED       = "#EF4444";
    private static final String COLOR_RED_L     = "#FEE2E2";

    private static final String[] TAGS = {"Semua", "Pencapaian", "Meditasi", "Dukungan", "Curhat", "Motivasi"};
    private static final String[] TAG_COLORS = {
            COLOR_PRIMARY, "#3B82F6", "#8B5CF6", "#F59E0B", "#EC4899", "#F97316"
    };

    private static class Post {
        String username, content, timeAgo, tag;
        int likes, comments;
        boolean likedByCurrentUser;
        List<String> commentList = new ArrayList<>();

        Post(String username, String content, String timeAgo, int likes, int comments, String tag) {
            this.username = username;
            this.content = content;
            this.timeAgo = timeAgo;
            this.likes = likes;
            this.comments = comments;
            this.tag = tag;
        }
    }

    public CommunityView() {
        this.setStyle("-fx-background-color: " + COLOR_BG + ";");
        this.setSpacing(0);

        ScrollPane mainScroll = new ScrollPane();
        mainScroll.setFitToWidth(true);
        mainScroll.setStyle("-fx-background-color: transparent; -fx-background: transparent;");
        mainScroll.setVbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED);
        mainScroll.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);

        VBox contentWrapper = new VBox(0);
        contentWrapper.setStyle("-fx-background-color: " + COLOR_BG + ";");

        feedBox = new VBox(16);
        feedBox.setPadding(new Insets(0, 30, 20, 30));

        loadSamplePosts();
        refreshFeed();

        Button btnShare = createShareStoryButton();
        HBox shareBtnBox = new HBox(btnShare);
        shareBtnBox.setPadding(new Insets(4, 30, 30, 30));
        shareBtnBox.setAlignment(Pos.CENTER);

        contentWrapper.getChildren().addAll(
                createSearchSection(),
                createFilterSection(),
                feedBox,
                shareBtnBox
        );

        mainScroll.setContent(contentWrapper);
        this.getChildren().add(mainScroll);
        VBox.setVgrow(mainScroll, Priority.ALWAYS);
    }

    // ── Reset ────────────────────────────────────────────────────────────────────

    private void resetToMainCommunity() {
        searchQuery = "";
        searchField.setText("");
        activeFilter = "Semua";
        refreshAllTagButtons();
        refreshFeed();
        showToast("✅ Kembali ke halaman utama komunitas");
    }

    private void refreshAllTagButtons() {
        if (tagContainer == null) return;
        for (int i = 0; i < tagContainer.getChildren().size(); i++) {
            Button btn = (Button) tagContainer.getChildren().get(i);
            String tag = TAGS[i];
            String color = TAG_COLORS[i];
            applyTagStyle(btn, tag.equals(activeFilter), color);
        }
    }

    // ── Search section ───────────────────────────────────────────────────────────

    private VBox createSearchSection() {
        VBox wrapper = new VBox();
        wrapper.setPadding(new Insets(20, 30, 8, 30));

        HBox topContainer = new HBox(12);
        topContainer.setAlignment(Pos.CENTER_LEFT);

        // ── Back button - HANYA ICON, lebih gendut ────────────────────────────────
        Button backButton = new Button("←");
        // Font bold dan size lebih besar untuk icon yang lebih gendut
        backButton.setStyle(
                "-fx-background-color: " + COLOR_PRIMARY + ";" +
                        "-fx-text-fill: white; -fx-font-weight: bold; -fx-font-size: 24px;" +
                        "-fx-padding: 8 16 8 16; -fx-background-radius: 30; -fx-cursor: hand;" +
                        "-fx-effect: dropshadow(gaussian, rgba(0,0,0,0.1), 4, 0, 0, 2);"
        );

        // Hover effect
        backButton.setOnMouseEntered(e -> backButton.setStyle(
                "-fx-background-color: " + COLOR_PRIMARY_D + ";" +
                        "-fx-text-fill: white; -fx-font-weight: bold; -fx-font-size: 24px;" +
                        "-fx-padding: 8 16 8 16; -fx-background-radius: 30; -fx-cursor: hand;" +
                        "-fx-effect: dropshadow(gaussian, rgba(0,0,0,0.15), 4, 0, 0, 2);"
        ));
        backButton.setOnMouseExited(e -> backButton.setStyle(
                "-fx-background-color: " + COLOR_PRIMARY + ";" +
                        "-fx-text-fill: white; -fx-font-weight: bold; -fx-font-size: 24px;" +
                        "-fx-padding: 8 16 8 16; -fx-background-radius: 30; -fx-cursor: hand;" +
                        "-fx-effect: dropshadow(gaussian, rgba(0,0,0,0.1), 4, 0, 0, 2);"
        ));

        Tooltip backTooltip = new Tooltip("Kembali ke halaman utama");
        backTooltip.setStyle("-fx-font-size: 11px;");
        backButton.setTooltip(backTooltip);
        backButton.setOnAction(e -> resetToMainCommunity());

        // ── Search box ───────────────────────────────────────────────────────────
        HBox searchBox = new HBox(10);
        searchBox.setAlignment(Pos.CENTER_LEFT);
        searchBox.setPadding(new Insets(10, 16, 10, 16));
        searchBox.setStyle(
                "-fx-background-color: white; -fx-background-radius: 12;" +
                        "-fx-border-color: " + COLOR_BORDER + "; -fx-border-radius: 12;"
        );
        HBox.setHgrow(searchBox, Priority.ALWAYS);

        Label icon = new Label("🔍");
        icon.setStyle("-fx-font-size: 14px;");

        searchField = new TextField();
        searchField.setPromptText("Cari cerita atau kata kunci...");
        searchField.setStyle(
                "-fx-background-color: transparent; -fx-border-color: transparent;" +
                        "-fx-font-size: 14px; -fx-text-fill: " + COLOR_TEXT + ";"
        );
        HBox.setHgrow(searchField, Priority.ALWAYS);

        Button searchButton = new Button("Cari");
        String searchBase =
                "-fx-background-color: " + COLOR_PRIMARY + "; -fx-text-fill: white;" +
                        "-fx-font-weight: bold; -fx-font-size: 12px;" +
                        "-fx-padding: 6 16 6 16; -fx-background-radius: 20; -fx-cursor: hand;";
        String searchHover =
                "-fx-background-color: " + COLOR_PRIMARY_D + "; -fx-text-fill: white;" +
                        "-fx-font-weight: bold; -fx-font-size: 12px;" +
                        "-fx-padding: 6 16 6 16; -fx-background-radius: 20; -fx-cursor: hand;";
        searchButton.setStyle(searchBase);
        searchButton.setOnMouseEntered(e -> searchButton.setStyle(searchHover));
        searchButton.setOnMouseExited(e -> searchButton.setStyle(searchBase));

        Runnable doSearch = () -> {
            searchQuery = searchField.getText().trim().toLowerCase();
            refreshFeed();
        };
        searchButton.setOnAction(e -> doSearch.run());
        searchField.setOnAction(e -> doSearch.run());

        searchBox.getChildren().addAll(icon, searchField, searchButton);
        topContainer.getChildren().addAll(backButton, searchBox);
        wrapper.getChildren().add(topContainer);
        return wrapper;
    }

    // ── Filter section ───────────────────────────────────────────────────────────

    private VBox createFilterSection() {
        VBox wrapper = new VBox(8);
        wrapper.setPadding(new Insets(8, 30, 16, 30));

        Label lbl = new Label("Filter Topik");
        lbl.setStyle("-fx-text-fill: " + COLOR_SUBTEXT + "; -fx-font-size: 12px; -fx-font-weight: bold;");

        tagContainer = new FlowPane(8, 8);
        tagContainer.setAlignment(Pos.CENTER_LEFT);

        for (int i = 0; i < TAGS.length; i++) {
            String tag = TAGS[i];
            String color = TAG_COLORS[i];
            Button btn = new Button(tag);
            applyTagStyle(btn, tag.equals(activeFilter), color);
            btn.setOnAction(e -> {
                activeFilter = tag;
                refreshAllTagButtons();
                refreshFeed();
            });
            tagContainer.getChildren().add(btn);
        }

        wrapper.getChildren().addAll(lbl, tagContainer);
        return wrapper;
    }

    private void applyTagStyle(Button btn, boolean active, String color) {
        btn.setStyle(active
                ? "-fx-background-color: " + color + "; -fx-text-fill: white; -fx-background-radius: 20;" +
                "-fx-font-size: 12px; -fx-font-weight: bold; -fx-padding: 6 14 6 14; -fx-cursor: hand;" +
                "-fx-effect: dropshadow(gaussian, rgba(0,0,0,0.15), 4, 0, 0, 2);"
                : "-fx-background-color: white; -fx-text-fill: " + COLOR_SUBTEXT + "; -fx-background-radius: 20;" +
                "-fx-border-color: " + COLOR_BORDER + "; -fx-border-radius: 20;" +
                "-fx-font-size: 12px; -fx-padding: 6 14 6 14; -fx-cursor: hand;"
        );
    }

    // ── Sample data ──────────────────────────────────────────────────────────────

    private void loadSamplePosts() {
        posts.clear();
        posts.add(new Post("🌱 Pengguna Anonim",
                "Hari ini aku berhasil bangun dari tempat tidur meski berat sekali. Rasanya seperti kemenangan kecil yang luar biasa. Terima kasih untuk semua yang selalu menyemangati di sini 🌱",
                "2 jam yang lalu", 24, 8, "Pencapaian"));
        posts.add(new Post("🧘 Pengguna Anonim",
                "Baru saja menyelesaikan sesi meditasi pertama setelah sekian lama. Rasanya lega sekali, seperti napas panjang setelah lama menahan. Highly recommended buat yang belum coba!",
                "5 jam yang lalu", 42, 12, "Meditasi"));
        posts.add(new Post("💚 Pengguna Anonim",
                "Terima kasih untuk semua yang telah memberikan dukungan di sini. Membaca cerita kalian membuatku merasa tidak sendirian dalam perjalanan ini 💚",
                "1 hari yang lalu", 67, 15, "Dukungan"));
        posts.add(new Post("🌟 Pengguna Anonim",
                "Kadang aku merasa overwhelmed dengan semua tuntutan hidup. Tapi aku ingat, aku tidak harus sempurna. Cukup melangkah satu langkah kecil setiap hari.",
                "2 hari yang lalu", 89, 21, "Motivasi"));
        posts.add(new Post("💬 Pengguna Anonim",
                "Mau curhat, minggu ini lumayan berat. Tapi aku berhasil minta bantuan ke orang terdekat untuk pertama kalinya. Meski susah, ternyata jauh lebih lega.",
                "3 hari yang lalu", 55, 18, "Curhat"));
    }

    // ── Feed ─────────────────────────────────────────────────────────────────────

    private void refreshFeed() {
        feedBox.getChildren().clear();
        List<Post> filtered = posts.stream()
                .filter(p -> activeFilter.equals("Semua") || p.tag.equals(activeFilter))
                .filter(p -> searchQuery.isEmpty()
                        || p.content.toLowerCase().contains(searchQuery)
                        || p.tag.toLowerCase().contains(searchQuery))
                .collect(Collectors.toList());

        if (filtered.isEmpty()) {
            feedBox.getChildren().add(createEmptyState());
        } else {
            for (Post post : filtered) {
                feedBox.getChildren().add(createPostCard(post));
            }
        }
    }

    private VBox createEmptyState() {
        VBox box = new VBox(10);
        box.setAlignment(Pos.CENTER);
        box.setPadding(new Insets(50));
        Label emoji = new Label("🔍");
        emoji.setStyle("-fx-font-size: 40px;");
        Label msg = new Label("Tidak ada cerita ditemukan");
        msg.setStyle("-fx-font-size: 16px; -fx-font-weight: bold; -fx-text-fill: " + COLOR_SUBTEXT + ";");
        Label hint = new Label("Coba ubah filter atau kata kunci pencarian.");
        hint.setStyle("-fx-font-size: 13px; -fx-text-fill: " + COLOR_SUBTEXT + ";");
        box.getChildren().addAll(emoji, msg, hint);
        return box;
    }

    // ── Post card ─────────────────────────────────────────────────────────────────

    private VBox createPostCard(Post post) {
        VBox card = new VBox(0);
        card.setStyle(
                "-fx-background-color: white; -fx-background-radius: 20;" +
                        "-fx-effect: dropshadow(gaussian, rgba(0,0,0,0.08), 12, 0, 0, 4);"
        );
        card.setMaxWidth(Double.MAX_VALUE);

        String tagColor = getTagColor(post.tag);

        HBox accent = new HBox();
        accent.setPrefHeight(5);
        accent.setMaxWidth(Double.MAX_VALUE);
        accent.setStyle("-fx-background-color: " + tagColor + "; -fx-background-radius: 20 20 0 0;");

        VBox main = new VBox(14);
        main.setPadding(new Insets(20, 20, 18, 20));

        HBox header = new HBox(12);
        header.setAlignment(Pos.CENTER_LEFT);

        Label avatar = new Label(getAvatarEmoji(posts.indexOf(post)));
        avatar.setStyle(
                "-fx-background-color: " + COLOR_PRIMARY_L + ";" +
                        "-fx-background-radius: 50; -fx-padding: 10; -fx-font-size: 18px;"
        );

        VBox userInfo = new VBox(4);
        HBox nameRow = new HBox(8);
        nameRow.setAlignment(Pos.CENTER_LEFT);

        Label uname = new Label(post.username);
        uname.setStyle("-fx-font-weight: bold; -fx-font-size: 14px; -fx-text-fill: " + COLOR_DARK + ";");

        Label tagBadge = new Label(post.tag);
        tagBadge.setStyle(
                "-fx-background-color: " + tagColor + "22; -fx-text-fill: " + tagColor + ";" +
                        "-fx-background-radius: 20; -fx-padding: 3 10 3 10;" +
                        "-fx-font-size: 10px; -fx-font-weight: bold;"
        );
        nameRow.getChildren().addAll(uname, tagBadge);

        Label time = new Label(post.timeAgo);
        time.setStyle("-fx-text-fill: " + COLOR_SUBTEXT + "; -fx-font-size: 11px;");
        userInfo.getChildren().addAll(nameRow, time);

        Region spacer = new Region();
        HBox.setHgrow(spacer, Priority.ALWAYS);
        header.getChildren().addAll(avatar, userInfo, spacer);

        Label content = new Label(post.content);
        content.setWrapText(true);
        content.setStyle("-fx-text-fill: " + COLOR_TEXT + "; -fx-font-size: 14px; -fx-line-spacing: 5;");

        Separator sep = new Separator();

        HBox actions = new HBox(16);
        actions.setAlignment(Pos.CENTER_LEFT);
        actions.setPadding(new Insets(4, 0, 0, 0));

        Button btnLike    = buildLikeButton(post);
        Button btnComment = new Button("💬 " + post.comments);
        Button btnShare2  = new Button("🔗 Bagikan");

        String actionStyle =
                "-fx-background-color: transparent; -fx-text-fill: " + COLOR_SUBTEXT + ";" +
                        "-fx-font-size: 13px; -fx-padding: 6 12 6 12; -fx-cursor: hand; -fx-background-radius: 20;";
        btnComment.setStyle(actionStyle);
        btnShare2.setStyle(actionStyle);
        btnShare2.setOnAction(e -> showToast("🔗 Link berhasil disalin!"));

        actions.getChildren().addAll(btnLike, btnComment, btnShare2);

        VBox commentsPanel = new VBox(8);
        commentsPanel.setPadding(new Insets(8, 0, 0, 0));
        commentsPanel.setVisible(false);
        commentsPanel.setManaged(false);

        btnComment.setOnAction(e -> {
            boolean show = !commentsPanel.isVisible();
            commentsPanel.setVisible(show);
            commentsPanel.setManaged(show);
            if (show) rebuildComments(post, commentsPanel);
        });

        main.getChildren().addAll(header, content, sep, actions);
        card.getChildren().addAll(accent, main, commentsPanel);

        card.setOnMouseEntered(e -> card.setStyle(
                "-fx-background-color: white; -fx-background-radius: 20;" +
                        "-fx-effect: dropshadow(gaussian, rgba(16,185,129,0.2), 16, 0, 0, 6);"
        ));
        card.setOnMouseExited(e -> card.setStyle(
                "-fx-background-color: white; -fx-background-radius: 20;" +
                        "-fx-effect: dropshadow(gaussian, rgba(0,0,0,0.08), 12, 0, 0, 4);"
        ));
        return card;
    }

    private Button buildLikeButton(Post post) {
        Button btn = new Button(post.likedByCurrentUser ? "❤️ " + post.likes : "🤍 " + post.likes);
        btn.setStyle(
                (post.likedByCurrentUser
                        ? "-fx-background-color: " + COLOR_RED_L + "; -fx-text-fill: " + COLOR_RED + ";"
                        : "-fx-background-color: transparent; -fx-text-fill: " + COLOR_SUBTEXT + ";") +
                        "-fx-font-size: 13px; -fx-padding: 6 12 6 12; -fx-cursor: hand; -fx-background-radius: 20;"
        );
        btn.setOnAction(e -> {
            post.likedByCurrentUser = !post.likedByCurrentUser;
            post.likes += post.likedByCurrentUser ? 1 : -1;
            refreshFeed();
        });
        return btn;
    }

    // ── Inline comments ───────────────────────────────────────────────────────────

    private void rebuildComments(Post post, VBox container) {
        container.getChildren().clear();

        if (post.commentList.isEmpty()) {
            Label empty = new Label("Belum ada komentar. Jadilah yang pertama! 💬");
            empty.setStyle("-fx-text-fill: " + COLOR_SUBTEXT + "; -fx-font-size: 12px; -fx-padding: 8 0 4 0;");
            container.getChildren().add(empty);
        } else {
            for (String c : post.commentList) {
                HBox row = new HBox(10);
                row.setAlignment(Pos.TOP_LEFT);
                row.setPadding(new Insets(0, 0, 8, 0));

                Label av = new Label("👤");
                av.setStyle("-fx-font-size: 14px;");

                VBox bubble = new VBox(2);
                bubble.setStyle(
                        "-fx-background-color: " + COLOR_BG + "; -fx-background-radius: 12; -fx-padding: 10 14 10 14;"
                );
                Label nm = new Label("Pengguna Anonim");
                nm.setStyle("-fx-font-weight: bold; -fx-font-size: 11px; -fx-text-fill: " + COLOR_TEXT + ";");
                Label ct = new Label(c);
                ct.setWrapText(true);
                ct.setStyle("-fx-font-size: 13px; -fx-text-fill: " + COLOR_TEXT + ";");
                HBox.setHgrow(bubble, Priority.ALWAYS);
                bubble.getChildren().addAll(nm, ct);
                row.getChildren().addAll(av, bubble);
                container.getChildren().add(row);
            }
        }

        HBox inputRow = new HBox(8);
        inputRow.setAlignment(Pos.CENTER);
        inputRow.setPadding(new Insets(8, 0, 4, 0));

        TextField commentInput = new TextField();
        commentInput.setPromptText("Tulis komentar yang menyemangati...");
        commentInput.setStyle(
                "-fx-background-color: " + COLOR_BG + "; -fx-background-radius: 20;" +
                        "-fx-border-color: " + COLOR_BORDER + "; -fx-border-radius: 20;" +
                        "-fx-padding: 8 14 8 14; -fx-font-size: 13px;"
        );
        HBox.setHgrow(commentInput, Priority.ALWAYS);

        Button sendBtn = new Button("Kirim");
        String sendBase =
                "-fx-background-color: " + COLOR_PRIMARY + "; -fx-text-fill: white;" +
                        "-fx-background-radius: 20; -fx-padding: 8 16 8 16;" +
                        "-fx-font-size: 12px; -fx-font-weight: bold; -fx-cursor: hand;";
        String sendHover =
                "-fx-background-color: " + COLOR_PRIMARY_D + "; -fx-text-fill: white;" +
                        "-fx-background-radius: 20; -fx-padding: 8 16 8 16;" +
                        "-fx-font-size: 12px; -fx-font-weight: bold; -fx-cursor: hand;";
        sendBtn.setStyle(sendBase);
        sendBtn.setOnMouseEntered(e -> sendBtn.setStyle(sendHover));
        sendBtn.setOnMouseExited(e -> sendBtn.setStyle(sendBase));

        sendBtn.setOnAction(e -> {
            String text = commentInput.getText().trim();
            if (!text.isEmpty()) {
                post.commentList.add(text);
                post.comments++;
                commentInput.clear();
                rebuildComments(post, container);
                showToast("💚 Komentar berhasil ditambahkan!");
                refreshFeed();
            }
        });
        commentInput.setOnAction(e -> sendBtn.fire());

        inputRow.getChildren().addAll(commentInput, sendBtn);
        container.getChildren().add(inputRow);
    }

    // ── Share story button ────────────────────────────────────────────────────────

    private Button createShareStoryButton() {
        Button btn = new Button("✍️  Bagikan Cerita Anda");
        String base =
                "-fx-background-color: " + COLOR_PRIMARY + "; -fx-text-fill: white;" +
                        "-fx-font-weight: bold; -fx-font-size: 15px;" +
                        "-fx-padding: 14 40 14 40; -fx-background-radius: 40; -fx-cursor: hand;" +
                        "-fx-effect: dropshadow(gaussian, rgba(16,185,129,0.3), 12, 0, 0, 4);";
        String hover =
                "-fx-background-color: " + COLOR_PRIMARY_D + "; -fx-text-fill: white;" +
                        "-fx-font-weight: bold; -fx-font-size: 15px;" +
                        "-fx-padding: 14 40 14 40; -fx-background-radius: 40; -fx-cursor: hand;" +
                        "-fx-effect: dropshadow(gaussian, rgba(16,185,129,0.4), 14, 0, 0, 6);";
        btn.setStyle(base);
        btn.setOnMouseEntered(e -> btn.setStyle(hover));
        btn.setOnMouseExited(e -> btn.setStyle(base));
        btn.setOnAction(e -> showShareStoryDialog());
        return btn;
    }

    // ── Share story dialog ────────────────────────────────────────────────────────

    private void showShareStoryDialog() {
        Dialog<Post> dialog = new Dialog<>();
        dialog.setTitle("Bagikan Cerita");
        dialog.setHeaderText(null);
        dialog.getDialogPane().setStyle("-fx-background-color: white; -fx-background-radius: 16; -fx-padding: 0;");
        dialog.getDialogPane().setPrefWidth(480);

        ButtonType btnSubmit = new ButtonType("Bagikan", ButtonBar.ButtonData.OK_DONE);
        dialog.getDialogPane().getButtonTypes().addAll(btnSubmit, ButtonType.CANCEL);

        dialog.getDialogPane().lookupButton(btnSubmit).setStyle(
                "-fx-background-color: " + COLOR_PRIMARY + "; -fx-text-fill: white;" +
                        "-fx-font-weight: bold; -fx-font-size: 14px;" +
                        "-fx-background-radius: 10; -fx-padding: 10 24 10 24; -fx-cursor: hand;"
        );
        dialog.getDialogPane().lookupButton(ButtonType.CANCEL).setStyle(
                "-fx-background-color: " + COLOR_BG + "; -fx-text-fill: " + COLOR_SUBTEXT + ";" +
                        "-fx-font-size: 14px; -fx-background-radius: 10; -fx-padding: 10 24 10 24; -fx-cursor: hand;" +
                        "-fx-border-color: " + COLOR_BORDER + "; -fx-border-radius: 10;"
        );

        VBox content = new VBox(0);
        content.setStyle("-fx-background-color: white;");

        HBox dialogHeader = new HBox(12);
        dialogHeader.setAlignment(Pos.CENTER_LEFT);
        dialogHeader.setPadding(new Insets(20, 24, 16, 24));
        dialogHeader.setStyle(
                "-fx-background-color: linear-gradient(to right, #ECFDF5, #D1FAE5);" +
                        "-fx-border-color: " + COLOR_BORDER + "; -fx-border-width: 0 0 1 0;"
        );

        Label headerEmoji = new Label("✍️");
        headerEmoji.setStyle("-fx-font-size: 26px;");

        VBox headerText = new VBox(2);
        Label headerTitle = new Label("Bagikan Cerita Anda");
        headerTitle.setFont(Font.font("System", FontWeight.BOLD, 17));
        headerTitle.setStyle("-fx-text-fill: " + COLOR_DARK + ";");
        Label headerSub = new Label("Cerita Anda akan ditampilkan secara anonim dan aman 🔒");
        headerSub.setStyle("-fx-text-fill: " + COLOR_SUBTEXT + "; -fx-font-size: 12px;");
        headerText.getChildren().addAll(headerTitle, headerSub);
        dialogHeader.getChildren().addAll(headerEmoji, headerText);

        VBox body = new VBox(16);
        body.setPadding(new Insets(20, 24, 8, 24));

        Label tagLabel = new Label("Pilih Topik");
        tagLabel.setStyle("-fx-font-weight: bold; -fx-font-size: 13px; -fx-text-fill: " + COLOR_TEXT + ";");

        FlowPane tagRow = new FlowPane(8, 8);
        tagRow.setMaxWidth(420);
        ToggleGroup tagGroup = new ToggleGroup();
        String[] selectableTags = {"Pencapaian", "Meditasi", "Dukungan", "Curhat", "Motivasi"};

        for (int i = 0; i < selectableTags.length; i++) {
            String t = selectableTags[i];
            String c = TAG_COLORS[i + 1];
            ToggleButton tb = new ToggleButton(t);
            tb.setToggleGroup(tagGroup);
            String unsel =
                    "-fx-background-color: " + COLOR_BG + "; -fx-text-fill: " + COLOR_SUBTEXT + ";" +
                            "-fx-background-radius: 20; -fx-border-color: " + COLOR_BORDER + ";" +
                            "-fx-border-radius: 20; -fx-font-size: 12px; -fx-padding: 7 16 7 16; -fx-cursor: hand;";
            String sel =
                    "-fx-background-color: " + c + "; -fx-text-fill: white;" +
                            "-fx-background-radius: 20; -fx-font-size: 12px; -fx-font-weight: bold;" +
                            "-fx-padding: 7 16 7 16; -fx-cursor: hand;" +
                            "-fx-effect: dropshadow(gaussian, rgba(0,0,0,0.15), 4, 0, 0, 2);";
            tb.setStyle(unsel);
            tb.selectedProperty().addListener((obs, was, is) -> tb.setStyle(is ? sel : unsel));
            tagRow.getChildren().add(tb);
        }

        Label storyLabel = new Label("Ceritakan Pengalamanmu");
        storyLabel.setStyle("-fx-font-weight: bold; -fx-font-size: 13px; -fx-text-fill: " + COLOR_TEXT + ";");

        VBox textAreaWrapper = new VBox(0);
        textAreaWrapper.setStyle(
                "-fx-border-color: " + COLOR_BORDER + "; -fx-border-radius: 12;" +
                        "-fx-background-color: " + COLOR_BG + "; -fx-background-radius: 12;"
        );

        TextArea storyArea = new TextArea();
        storyArea.setPromptText(
                "Tulis cerita Anda di sini...\n\nContoh: Hari ini aku berhasil melakukan sesuatu yang kecil tapi berarti..."
        );
        storyArea.setPrefHeight(150);
        storyArea.setWrapText(true);
        storyArea.setStyle(
                "-fx-background-color: transparent; -fx-background-radius: 12;" +
                        "-fx-border-color: transparent; -fx-font-size: 14px;" +
                        "-fx-text-fill: " + COLOR_TEXT + "; -fx-padding: 10 12 10 12;"
        );

        HBox counterRow = new HBox();
        counterRow.setAlignment(Pos.CENTER_RIGHT);
        counterRow.setPadding(new Insets(4, 10, 8, 10));
        Label charCounter = new Label("0 / 500");
        charCounter.setStyle("-fx-text-fill: " + COLOR_SUBTEXT + "; -fx-font-size: 11px;");
        storyArea.textProperty().addListener((obs, oldVal, newVal) -> {
            if (newVal.length() > 500) storyArea.setText(oldVal);
            int count = storyArea.getText().length();
            charCounter.setText(count + " / 500");
            charCounter.setStyle("-fx-font-size: 11px; -fx-text-fill: " +
                    (count > 450 ? COLOR_RED : COLOR_SUBTEXT) + ";");
        });
        counterRow.getChildren().add(charCounter);
        textAreaWrapper.getChildren().addAll(storyArea, counterRow);

        HBox tipsBox = new HBox(10);
        tipsBox.setAlignment(Pos.CENTER_LEFT);
        tipsBox.setPadding(new Insets(10, 14, 10, 14));
        tipsBox.setStyle(
                "-fx-background-color: #EFF6FF; -fx-background-radius: 10;" +
                        "-fx-border-color: #BFDBFE; -fx-border-radius: 10;"
        );
        Label tipsIcon = new Label("💡");
        tipsIcon.setStyle("-fx-font-size: 14px;");
        Label tipsText = new Label("Tips: Fokus pada perasaan dan pengalaman. Tidak perlu sempurna — yang penting tulus!");
        tipsText.setWrapText(true);
        tipsText.setStyle("-fx-text-fill: #1E40AF; -fx-font-size: 12px;");
        HBox.setHgrow(tipsText, Priority.ALWAYS);
        tipsBox.getChildren().addAll(tipsIcon, tipsText);

        body.getChildren().addAll(tagLabel, tagRow, storyLabel, textAreaWrapper, tipsBox);
        content.getChildren().addAll(dialogHeader, body);
        dialog.getDialogPane().setContent(content);

        dialog.setResultConverter(db -> {
            if (db == btnSubmit && !storyArea.getText().trim().isEmpty()) {
                Toggle sel = tagGroup.getSelectedToggle();
                String chosenTag = sel != null ? ((ToggleButton) sel).getText() : "Curhat";
                return new Post(getAvatarEmoji(posts.size()) + " Pengguna Anonim",
                        storyArea.getText(), "Baru saja", 0, 0, chosenTag);
            }
            return null;
        });

        dialog.showAndWait().ifPresent(newPost -> {
            posts.add(0, newPost);
            refreshFeed();
            showToast("✨ Cerita Anda berhasil dibagikan!");
        });
    }

    // ── Toast ─────────────────────────────────────────────────────────────────────

    private void showToast(String message) {
        Alert toast = new Alert(Alert.AlertType.INFORMATION);
        toast.setTitle("VibeCare");
        toast.setHeaderText(null);
        toast.setContentText(message);
        new Timeline(new KeyFrame(Duration.millis(2000), e -> toast.close())).play();
        toast.show();
    }

    // ── Helpers ───────────────────────────────────────────────────────────────────

    private String getTagColor(String tag) {
        for (int i = 0; i < TAGS.length; i++) {
            if (TAGS[i].equals(tag)) return TAG_COLORS[i];
        }
        return COLOR_PRIMARY;
    }

    private String getAvatarEmoji(int index) {
        String[] av = {"🌱", "🧘", "💚", "🌟", "💬", "🌸", "🌿", "🍃"};
        return av[Math.abs(index) % av.length];
    }
}