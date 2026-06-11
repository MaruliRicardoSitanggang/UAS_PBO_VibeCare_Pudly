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

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.TextStyle;
import java.util.List;
import java.util.Locale;

public class ConsultationView extends VBox {

    private static final String BG_PAGE        = "#F0FDF4";
    private static final String GREEN_PRIMARY  = "#10B981";
    private static final String GREEN_DARK     = "#065F46";
    private static final String GREEN_HOVER    = "#059669";
    private static final String GOLD_COIN      = "#F59E0B";
    private static final String WHITE          = "#FFFFFF";
    private static final String GRAY_TEXT      = "#6B7280";
    private static final String DARK_TEXT      = "#1F2937";
    private static final String BORDER_LIGHT   = "#D1FAE5";

    public ConsultationView() {
        this.setStyle("-fx-background-color: " + BG_PAGE + "; -fx-padding: 30;");
        this.setSpacing(25);

        ScrollPane scrollPane = new ScrollPane();
        scrollPane.setFitToWidth(true);
        scrollPane.setStyle("-fx-background-color: transparent; -fx-background: transparent;");
        scrollPane.setVbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED);
        scrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);

        VBox content = new VBox(24);
        content.setPadding(new Insets(0, 0, 30, 0));
        content.setAlignment(Pos.TOP_LEFT);

        content.getChildren().addAll(
                createHeaderSection(),
                createCoinBannerCard(),
                createFilterBar(),
                createPsychologistCards()
        );

        scrollPane.setContent(content);
        this.getChildren().add(scrollPane);
    }

    private VBox createHeaderSection() {
        VBox header = new VBox(6);

        Label title = new Label("Konsultasi Psikolog");
        title.setFont(Font.font("System", FontWeight.BOLD, 30));
        title.setStyle("-fx-text-fill: " + GREEN_DARK + ";");

        Label subtitle = new Label(
                "Konsultasi dengan psikolog profesional via chat, telepon, atau video call. " +
                        "Gunakan koin kamu untuk diskon konsultasi!"
        );
        subtitle.setWrapText(true);
        subtitle.setStyle("-fx-text-fill: " + GRAY_TEXT + "; -fx-font-size: 14px;");

        header.getChildren().addAll(title, subtitle);
        return header;
    }

    private HBox createCoinBannerCard() {
        HBox banner = new HBox(16);
        banner.setAlignment(Pos.CENTER_LEFT);
        banner.setPadding(new Insets(18, 20, 18, 20));
        banner.setStyle(
                "-fx-background-color: linear-gradient(to right, #ECFDF5, #D1FAE5);" +
                        "-fx-background-radius: 16;" +
                        "-fx-border-color: " + BORDER_LIGHT + ";" +
                        "-fx-border-radius: 16;" +
                        "-fx-border-width: 1.5;"
        );

        StackPane coinStack = new StackPane();
        Circle coinBg = new Circle(26);
        coinBg.setFill(Color.web("#FEF3C7"));
        Label coinIcon = new Label("🪙");
        coinIcon.setStyle("-fx-font-size: 28px;");
        coinStack.getChildren().addAll(coinBg, coinIcon);

        VBox coinInfo = new VBox(3);
        Label coinTitle = new Label("Koin Kamu");
        coinTitle.setStyle("-fx-text-fill: " + GRAY_TEXT + "; -fx-font-size: 12px;");

        int currentCoins = UserSession.getPoints();
        Label coinValue = new Label(currentCoins + " Koin");
        coinValue.setFont(Font.font("System", FontWeight.BOLD, 22));
        coinValue.setStyle("-fx-text-fill: " + GOLD_COIN + ";");
        coinInfo.getChildren().addAll(coinTitle, coinValue);

        Region spacer = new Region();
        HBox.setHgrow(spacer, Priority.ALWAYS);

        VBox convInfo = new VBox(3);
        convInfo.setAlignment(Pos.CENTER_RIGHT);
        Label convTitle = new Label("Nilai Koin");
        convTitle.setStyle("-fx-text-fill: " + GRAY_TEXT + "; -fx-font-size: 11px;");
        Label convValue = new Label("100 koin = Rp 10.000");
        convValue.setStyle("-fx-text-fill: " + GREEN_DARK + "; -fx-font-size: 13px; -fx-font-weight: bold;");
        Label convNote = new Label("Maks. 50% dari harga");
        convNote.setStyle("-fx-text-fill: " + GRAY_TEXT + "; -fx-font-size: 11px;");
        convInfo.getChildren().addAll(convTitle, convValue, convNote);

        banner.getChildren().addAll(coinStack, coinInfo, spacer, convInfo);
        return banner;
    }

    private HBox createFilterBar() {
        HBox bar = new HBox(10);
        bar.setAlignment(Pos.CENTER_LEFT);

        String[] filters = {"Semua", "Klinis", "Anak & Remaja", "Keluarga", "Trauma"};
        ToggleGroup tg = new ToggleGroup();

        for (int i = 0; i < filters.length; i++) {
            ToggleButton btn = new ToggleButton(filters[i]);
            btn.setToggleGroup(tg);
            if (i == 0) btn.setSelected(true);

            String baseStyle =
                    "-fx-background-radius: 20;" +
                            "-fx-font-size: 12px;" +
                            "-fx-padding: 6 16 6 16;" +
                            "-fx-cursor: hand;";

            btn.setStyle(baseStyle +
                    "-fx-background-color: " + WHITE + ";" +
                    "-fx-text-fill: " + GRAY_TEXT + ";" +
                    "-fx-border-color: #E5E7EB; -fx-border-radius: 20; -fx-border-width: 1;");

            btn.selectedProperty().addListener((obs, wasSelected, isSelected) -> {
                if (isSelected) {
                    btn.setStyle(baseStyle +
                            "-fx-background-color: " + GREEN_PRIMARY + ";" +
                            "-fx-text-fill: white;");
                } else {
                    btn.setStyle(baseStyle +
                            "-fx-background-color: " + WHITE + ";" +
                            "-fx-text-fill: " + GRAY_TEXT + ";" +
                            "-fx-border-color: #E5E7EB; -fx-border-radius: 20; -fx-border-width: 1;");
                }
            });

            bar.getChildren().add(btn);
        }
        return bar;
    }

    private VBox createPsychologistCards() {
        VBox container = new VBox(16);

        List<PsychologistData> list = List.of(
                new PsychologistData(
                        "Dr. Sari Indah, M.Psi.",
                        "Psikolog Klinis",
                        "Depresi, Kecemasan, dan Gangguan Mood",
                        4.9, 1200, "👩‍⚕️", "#EC4899",
                        150000, List.of("Senin", "Rabu", "Jumat"),
                        "Berpengalaman 10 tahun dalam menangani gangguan mood dan kecemasan.",
                        List.of("💬 Chat", "📞 Telepon", "📹 Video Call"),
                        true
                ),
                new PsychologistData(
                        "Dr. Andi Pratama, M.Psi.",
                        "Psikolog Klinis",
                        "Trauma, PTSD, dan Konseling Keluarga",
                        4.8, 980, "👨‍⚕️", "#3B82F6",
                        175000, List.of("Selasa", "Kamis", "Sabtu"),
                        "Spesialis trauma dengan metode EMDR bersertifikat internasional.",
                        List.of("📞 Telepon", "📹 Video Call"),
                        false
                ),
                new PsychologistData(
                        "Dr. Maya Sari, M.Psi., Psikolog",
                        "Psikolog Anak & Remaja",
                        "Masalah Perkembangan, ADHD, dan Bullying",
                        4.9, 1500, "👩‍⚕️", "#8B5CF6",
                        200000, List.of("Senin", "Selasa", "Rabu", "Kamis"),
                        "Berfokus pada perkembangan anak usia 4–18 tahun dengan pendekatan berbasis bermain.",
                        List.of("💬 Chat", "📹 Video Call"),
                        true
                ),
                new PsychologistData(
                        "Dr. Budi Santoso, M.Psi.",
                        "Psikolog Klinis & Konselor Keluarga",
                        "Konflik Pernikahan, Parenting, dan Komunikasi",
                        4.7, 760, "👨‍⚕️", "#F97316",
                        130000, List.of("Rabu", "Jumat", "Sabtu", "Minggu"),
                        "Konselor keluarga dengan pendekatan holistik dan berbasis solusi.",
                        List.of("💬 Chat", "📞 Telepon", "📹 Video Call"),
                        false
                )
        );

        for (PsychologistData data : list) {
            container.getChildren().add(createPsychologistCard(data));
        }
        return container;
    }

    private VBox createPsychologistCard(PsychologistData data) {
        VBox card = new VBox(14);
        card.setStyle(
                "-fx-background-color: " + WHITE + ";" +
                        "-fx-background-radius: 20;" +
                        "-fx-effect: dropshadow(gaussian, rgba(0,0,0,0.07), 12, 0, 0, 4);"
        );
        card.setPadding(new Insets(20));
        card.setMaxWidth(Double.MAX_VALUE);

        HBox topRow = new HBox(14);
        topRow.setAlignment(Pos.TOP_LEFT);

        StackPane avatar = new StackPane();
        avatar.setMinSize(72, 72);
        avatar.setMaxSize(72, 72);
        avatar.setStyle("-fx-background-color: " + data.color + "20; -fx-background-radius: 50;");
        Label avatarLbl = new Label(data.avatar);
        avatarLbl.setStyle("-fx-font-size: 38px;");
        avatar.getChildren().add(avatarLbl);

        VBox infoBox = new VBox(4);
        infoBox.setAlignment(Pos.TOP_LEFT);

        HBox nameRow = new HBox(8);
        nameRow.setAlignment(Pos.CENTER_LEFT);
        Label nameLbl = new Label(data.name);
        nameLbl.setFont(Font.font("System", FontWeight.BOLD, 17));
        nameLbl.setStyle("-fx-text-fill: " + DARK_TEXT + ";");
        if (data.online) {
            Label onlineBadge = new Label("● Online");
            onlineBadge.setStyle(
                    "-fx-background-color: #D1FAE5; -fx-text-fill: #065F46;" +
                            "-fx-font-size: 11px; -fx-font-weight: bold;" +
                            "-fx-background-radius: 10; -fx-padding: 2 8 2 8;"
            );
            nameRow.getChildren().addAll(nameLbl, onlineBadge);
        } else {
            nameRow.getChildren().add(nameLbl);
        }

        Label titleLbl = new Label(data.title);
        titleLbl.setStyle("-fx-text-fill: " + GRAY_TEXT + "; -fx-font-size: 13px;");

        HBox ratingRow = new HBox(8);
        ratingRow.setAlignment(Pos.CENTER_LEFT);
        Label ratingLbl = new Label("⭐ " + data.rating);
        ratingLbl.setStyle("-fx-text-fill: " + GOLD_COIN + "; -fx-font-size: 13px; -fx-font-weight: bold;");
        Label countLbl = new Label("• " + data.consultCount + " konsultasi");
        countLbl.setStyle("-fx-text-fill: #9CA3AF; -fx-font-size: 12px;");
        ratingRow.getChildren().addAll(ratingLbl, countLbl);

        infoBox.getChildren().addAll(nameRow, titleLbl, ratingRow);

        Region spacer = new Region();
        HBox.setHgrow(spacer, Priority.ALWAYS);

        VBox priceBox = new VBox(2);
        priceBox.setAlignment(Pos.TOP_RIGHT);
        Label priceLabel = new Label("mulai dari");
        priceLabel.setStyle("-fx-text-fill: " + GRAY_TEXT + "; -fx-font-size: 11px;");
        Label priceValue = new Label(formatRupiah(data.pricePerSession));
        priceValue.setFont(Font.font("System", FontWeight.BOLD, 15));
        priceValue.setStyle("-fx-text-fill: " + GREEN_DARK + ";");
        Label pricePer = new Label("/ sesi");
        pricePer.setStyle("-fx-text-fill: " + GRAY_TEXT + "; -fx-font-size: 11px;");
        priceBox.getChildren().addAll(priceLabel, priceValue, pricePer);

        topRow.getChildren().addAll(avatar, infoBox, spacer, priceBox);

        Label descLbl = new Label(data.description);
        descLbl.setWrapText(true);
        descLbl.setStyle("-fx-text-fill: " + GRAY_TEXT + "; -fx-font-size: 13px;");

        HBox specRow = new HBox(6);
        specRow.setAlignment(Pos.CENTER_LEFT);
        Label specLbl = new Label("Spesialisasi:");
        specLbl.setStyle("-fx-font-size: 12px; -fx-font-weight: bold; -fx-text-fill: " + DARK_TEXT + ";");
        Label specVal = new Label(data.specialization);
        specVal.setStyle("-fx-text-fill: " + GRAY_TEXT + "; -fx-font-size: 12px;");
        specVal.setWrapText(true);
        specRow.getChildren().addAll(specLbl, specVal);

        HBox scheduleRow = new HBox(6);
        scheduleRow.setAlignment(Pos.CENTER_LEFT);
        Label schedIcon = new Label("Jadwal:");
        schedIcon.setStyle("-fx-font-size: 12px; -fx-font-weight: bold; -fx-text-fill: " + DARK_TEXT + ";");
        HBox dayChips = new HBox(6);
        for (String day : data.availableDays) {
            Label chip = new Label(day);
            chip.setStyle(
                    "-fx-background-color: " + BORDER_LIGHT + ";" +
                            "-fx-text-fill: " + GREEN_DARK + ";" +
                            "-fx-font-size: 11px;" +
                            "-fx-background-radius: 8;" +
                            "-fx-padding: 3 8 3 8;"
            );
            dayChips.getChildren().add(chip);
        }
        scheduleRow.getChildren().addAll(schedIcon, dayChips);

        HBox methodRow = new HBox(6);
        methodRow.setAlignment(Pos.CENTER_LEFT);
        Label methIcon = new Label("Metode:");
        methIcon.setStyle("-fx-font-size: 12px; -fx-font-weight: bold; -fx-text-fill: " + DARK_TEXT + ";");
        HBox methodChips = new HBox(6);
        for (String m : data.methods) {
            Label chip = new Label(m);
            chip.setStyle(
                    "-fx-background-color: #EEF2FF;" +
                            "-fx-text-fill: #4338CA;" +
                            "-fx-font-size: 11px;" +
                            "-fx-background-radius: 8;" +
                            "-fx-padding: 3 8 3 8;"
            );
            methodChips.getChildren().add(chip);
        }
        methodRow.getChildren().addAll(methIcon, methodChips);

        Separator sep = new Separator();
        sep.setStyle("-fx-background-color: #F3F4F6;");

        HBox footer = new HBox(10);
        footer.setAlignment(Pos.CENTER_LEFT);

        int currentCoins = UserSession.getPoints();
        long coinValue = (long) currentCoins * 100L;
        long maxDiscount = data.pricePerSession / 2;
        long actualDiscount = Math.min(coinValue, maxDiscount);
        long finalPrice = data.pricePerSession - actualDiscount;

        VBox coinHint = new VBox(2);
        Label coinHintTitle = new Label("Pakai koin kamu:");
        coinHintTitle.setStyle("-fx-font-size: 11px; -fx-text-fill: " + GRAY_TEXT + ";");
        Label coinHintVal = new Label(
                currentCoins > 0
                        ? "Hemat " + formatRupiah(actualDiscount) + " → " + formatRupiah(finalPrice)
                        : "Kumpulkan koin untuk diskon!"
        );
        coinHintVal.setStyle(
                currentCoins > 0
                        ? "-fx-font-size: 12px; -fx-font-weight: bold; -fx-text-fill: " + GOLD_COIN + ";"
                        : "-fx-font-size: 12px; -fx-text-fill: " + GRAY_TEXT + ";"
        );
        coinHint.getChildren().addAll(coinHintTitle, coinHintVal);

        Region fSpacer = new Region();
        HBox.setHgrow(fSpacer, Priority.ALWAYS);

        Button btnConsult = new Button("Buat Janji  →");
        styleButtonPrimary(btnConsult);
        btnConsult.setOnAction(e -> showPaymentDialog(data));

        footer.getChildren().addAll(coinHint, fSpacer, btnConsult);

        card.getChildren().addAll(topRow, descLbl, specRow, scheduleRow, methodRow, sep, footer);
        return card;
    }

    private void showPaymentDialog(PsychologistData data) {
        Dialog<ButtonType> dialog = new Dialog<>();
        dialog.setTitle("Buat Janji & Pembayaran");
        dialog.getDialogPane().setPrefWidth(520);
        dialog.getDialogPane().setStyle(
                "-fx-background-color: " + BG_PAGE + ";" +
                        "-fx-background-radius: 16;"
        );

        ButtonType btnPay = new ButtonType("Konfirmasi & Bayar", ButtonBar.ButtonData.OK_DONE);
        dialog.getDialogPane().getButtonTypes().addAll(btnPay, ButtonType.CANCEL);

        ScrollPane sp = new ScrollPane();
        sp.setFitToWidth(true);
        sp.setPrefHeight(600);
        sp.setStyle("-fx-background-color: transparent; -fx-background: transparent;");

        VBox content = new VBox(20);
        content.setPadding(new Insets(16, 20, 16, 20));

        HBox docHeader = new HBox(12);
        docHeader.setAlignment(Pos.CENTER_LEFT);
        docHeader.setPadding(new Insets(14));
        docHeader.setStyle(
                "-fx-background-color: white;" +
                        "-fx-background-radius: 14;" +
                        "-fx-effect: dropshadow(gaussian, rgba(0,0,0,0.05), 8, 0, 0, 2);"
        );

        StackPane dAvatar = new StackPane();
        dAvatar.setMinSize(56, 56);
        dAvatar.setMaxSize(56, 56);
        dAvatar.setStyle("-fx-background-color: " + data.color + "20; -fx-background-radius: 50;");
        Label dAvLbl = new Label(data.avatar);
        dAvLbl.setStyle("-fx-font-size: 30px;");
        dAvatar.getChildren().add(dAvLbl);

        VBox dInfo = new VBox(3);
        Label dName = new Label(data.name);
        dName.setFont(Font.font("System", FontWeight.BOLD, 15));
        dName.setStyle("-fx-text-fill: " + DARK_TEXT + ";");
        Label dTitle = new Label(data.title);
        dTitle.setStyle("-fx-text-fill: " + GRAY_TEXT + "; -fx-font-size: 12px;");
        Label dRating = new Label(data.rating + "  •  " + data.consultCount + " konsultasi");
        dRating.setStyle("-fx-text-fill: " + GRAY_TEXT + "; -fx-font-size: 12px;");
        dInfo.getChildren().addAll(dName, dTitle, dRating);

        docHeader.getChildren().addAll(dAvatar, dInfo);

        VBox dateSection = createSectionBox("Pilih Tanggal");
        DatePicker datePicker = new DatePicker(LocalDate.now().plusDays(1));
        datePicker.setMaxWidth(Double.MAX_VALUE);
        datePicker.setStyle("-fx-background-radius: 10;");

        datePicker.setDayCellFactory(picker -> new DateCell() {
            @Override
            public void updateItem(LocalDate date, boolean empty) {
                super.updateItem(date, empty);
                String dayName = date.getDayOfWeek()
                        .getDisplayName(TextStyle.FULL, new Locale("id", "ID"));
                dayName = dayName.substring(0, 1).toUpperCase() + dayName.substring(1);
                boolean unavailable = !data.availableDays.contains(dayName) || date.isBefore(LocalDate.now());
                setDisable(unavailable);
                if (unavailable) {
                    setStyle("-fx-background-color: #F3F4F6; -fx-text-fill: #D1D5DB;");
                }
            }
        });
        dateSection.getChildren().add(datePicker);

        VBox timeSection = createSectionBox("Pilih Waktu");
        FlowPane timeFlow = new FlowPane(8, 8);
        ToggleGroup timeGroup = new ToggleGroup();
        String[] times = {"08:00", "09:00", "10:00", "11:00", "13:00", "14:00", "15:00", "16:00", "17:00"};
        for (String t : times) {
            ToggleButton tb = new ToggleButton(t);
            tb.setToggleGroup(timeGroup);
            tb.setStyle(
                    "-fx-background-color: white; -fx-text-fill: " + DARK_TEXT + ";" +
                            "-fx-font-size: 13px; -fx-background-radius: 10;" +
                            "-fx-border-color: #E5E7EB; -fx-border-radius: 10; -fx-border-width: 1;" +
                            "-fx-padding: 8 16 8 16; -fx-cursor: hand;"
            );
            tb.selectedProperty().addListener((obs, was, is) -> {
                if (is) {
                    tb.setStyle("-fx-background-color: " + GREEN_PRIMARY + "; -fx-text-fill: white;" +
                            "-fx-font-size: 13px; -fx-background-radius: 10; -fx-padding: 8 16 8 16; -fx-cursor: hand;");
                } else {
                    tb.setStyle("-fx-background-color: white; -fx-text-fill: " + DARK_TEXT + ";" +
                            "-fx-font-size: 13px; -fx-background-radius: 10;" +
                            "-fx-border-color: #E5E7EB; -fx-border-radius: 10; -fx-border-width: 1;" +
                            "-fx-padding: 8 16 8 16; -fx-cursor: hand;");
                }
            });
            timeFlow.getChildren().add(tb);
        }
        timeSection.getChildren().add(timeFlow);

        VBox methodSection = createSectionBox("Metode Konsultasi");
        ToggleGroup methodGroup = new ToggleGroup();
        VBox methodOptions = new VBox(8);
        for (String m : data.methods) {
            ToggleButton mb = new ToggleButton(m);
            mb.setToggleGroup(methodGroup);
            mb.setMaxWidth(Double.MAX_VALUE);
            mb.setStyle(
                    "-fx-background-color: white; -fx-text-fill: " + DARK_TEXT + ";" +
                            "-fx-font-size: 13px; -fx-background-radius: 10;" +
                            "-fx-border-color: #E5E7EB; -fx-border-radius: 10; -fx-border-width: 1;" +
                            "-fx-padding: 10 16 10 16; -fx-cursor: hand; -fx-alignment: CENTER_LEFT;"
            );
            mb.selectedProperty().addListener((obs, was, is) -> {
                if (is) {
                    mb.setStyle("-fx-background-color: #EEF2FF; -fx-text-fill: #4338CA;" +
                            "-fx-font-size: 13px; -fx-background-radius: 10;" +
                            "-fx-border-color: #818CF8; -fx-border-radius: 10; -fx-border-width: 1.5;" +
                            "-fx-padding: 10 16 10 16; -fx-cursor: hand; -fx-alignment: CENTER_LEFT;");
                } else {
                    mb.setStyle("-fx-background-color: white; -fx-text-fill: " + DARK_TEXT + ";" +
                            "-fx-font-size: 13px; -fx-background-radius: 10;" +
                            "-fx-border-color: #E5E7EB; -fx-border-radius: 10; -fx-border-width: 1;" +
                            "-fx-padding: 10 16 10 16; -fx-cursor: hand; -fx-alignment: CENTER_LEFT;");
                }
            });
            methodOptions.getChildren().add(mb);
        }
        methodSection.getChildren().add(methodOptions);

        VBox noteSection = createSectionBox(" Keluhan / Catatan (opsional)");
        TextArea noteArea = new TextArea();
        noteArea.setPromptText("Ceritakan keluhan yang ingin dikonsultasikan agar psikolog lebih siap...");
        noteArea.setPrefHeight(80);
        noteArea.setWrapText(true);
        noteArea.setStyle("-fx-background-radius: 10; -fx-border-color: #E5E7EB; -fx-border-radius: 10;");
        noteSection.getChildren().add(noteArea);

        VBox paySection = createSectionBox("Rincian Pembayaran");

        int currentCoins = UserSession.getPoints();
        long coinValue = (long) currentCoins * 100L;
        long maxDiscount = data.pricePerSession / 2;
        long actualDisc = Math.min(coinValue, maxDiscount);
        long coinsUsed = actualDisc / 100;
        long finalPrice = data.pricePerSession - actualDisc;

        VBox payCard = new VBox(10);
        payCard.setPadding(new Insets(14));
        payCard.setStyle(
                "-fx-background-color: white;" +
                        "-fx-background-radius: 14;" +
                        "-fx-border-color: " + BORDER_LIGHT + ";" +
                        "-fx-border-radius: 14; -fx-border-width: 1;"
        );

        payCard.getChildren().add(createPayRow("Harga Konsultasi", formatRupiah(data.pricePerSession), DARK_TEXT, false));

        if (currentCoins > 0) {
            payCard.getChildren().add(createPayRow(
                    "Diskon Koin (" + coinsUsed + " koin)",
                    "-" + formatRupiah(actualDisc),
                    "#059669",
                    false
            ));
        }

        Separator paySep = new Separator();
        payCard.getChildren().add(paySep);

        payCard.getChildren().add(createPayRow(
                "Total Pembayaran",
                formatRupiah(currentCoins > 0 ? finalPrice : data.pricePerSession),
                GREEN_DARK,
                true
        ));

        HBox earnRow = new HBox(6);
        earnRow.setAlignment(Pos.CENTER_LEFT);
        earnRow.setPadding(new Insets(6, 0, 0, 0));
        Label earnLbl = new Label(" Selesai konsultasi: +10 Koin bonus!");
        earnLbl.setStyle("-fx-font-size: 12px; -fx-text-fill: " + GOLD_COIN + "; -fx-font-weight: bold;");
        earnRow.getChildren().add(earnLbl);
        payCard.getChildren().add(earnRow);

        paySection.getChildren().add(payCard);

        VBox pmSection = createSectionBox("  Metode Pembayaran");
        ToggleGroup pmGroup = new ToggleGroup();
        String[][] pmOptions = {
                {"💳", "Kartu Kredit / Debit"},
                {"🏧", "Transfer Bank"},
                {"📱", "GoPay / OVO / Dana"},
                {"🏪", "Minimarket (Alfamart / Indomaret)"}
        };
        VBox pmBox = new VBox(8);
        for (String[] pm : pmOptions) {
            ToggleButton pmBtn = new ToggleButton(pm[0] + "  " + pm[1]);
            pmBtn.setToggleGroup(pmGroup);
            pmBtn.setMaxWidth(Double.MAX_VALUE);
            pmBtn.setStyle(
                    "-fx-background-color: white; -fx-text-fill: " + DARK_TEXT + ";" +
                            "-fx-font-size: 13px; -fx-background-radius: 10;" +
                            "-fx-border-color: #E5E7EB; -fx-border-radius: 10; -fx-border-width: 1;" +
                            "-fx-padding: 10 16 10 16; -fx-cursor: hand; -fx-alignment: CENTER_LEFT;"
            );
            pmBtn.selectedProperty().addListener((obs, was, is) -> {
                if (is) {
                    pmBtn.setStyle("-fx-background-color: #FEF3C7; -fx-text-fill: #92400E;" +
                            "-fx-font-size: 13px; -fx-background-radius: 10;" +
                            "-fx-border-color: " + GOLD_COIN + "; -fx-border-radius: 10; -fx-border-width: 1.5;" +
                            "-fx-padding: 10 16 10 16; -fx-cursor: hand; -fx-alignment: CENTER_LEFT;");
                } else {
                    pmBtn.setStyle("-fx-background-color: white; -fx-text-fill: " + DARK_TEXT + ";" +
                            "-fx-font-size: 13px; -fx-background-radius: 10;" +
                            "-fx-border-color: #E5E7EB; -fx-border-radius: 10; -fx-border-width: 1;" +
                            "-fx-padding: 10 16 10 16; -fx-cursor: hand; -fx-alignment: CENTER_LEFT;");
                }
            });
            pmBox.getChildren().add(pmBtn);
        }
        pmSection.getChildren().add(pmBox);

        content.getChildren().addAll(
                docHeader,
                dateSection,
                timeSection,
                methodSection,
                noteSection,
                paySection,
                pmSection
        );

        sp.setContent(content);
        dialog.getDialogPane().setContent(sp);

        dialog.getDialogPane().lookupButton(btnPay)
                .setStyle(
                        "-fx-background-color: " + GREEN_PRIMARY + ";" +
                                "-fx-text-fill: white; -fx-font-weight: bold;" +
                                "-fx-background-radius: 20; -fx-padding: 10 24 10 24;"
                );

        dialog.showAndWait().ifPresent(result -> {
            if (result == btnPay) {
                if (datePicker.getValue() == null) {
                    showError("Tanggal belum dipilih!", "Silakan pilih tanggal konsultasi.");
                    return;
                }
                if (timeGroup.getSelectedToggle() == null) {
                    showError("Waktu belum dipilih!", "Silakan pilih waktu konsultasi.");
                    return;
                }
                if (methodGroup.getSelectedToggle() == null) {
                    showError("Metode belum dipilih!", "Silakan pilih metode konsultasi.");
                    return;
                }
                if (pmGroup.getSelectedToggle() == null) {
                    showError("Metode pembayaran belum dipilih!", "Silakan pilih metode pembayaran.");
                    return;
                }

                UserSession.addPoints(10);
                if (currentCoins > 0) {
                    UserSession.deductPoints((int) coinsUsed);
                }

                String selectedDate = datePicker.getValue()
                        .format(DateTimeFormatter.ofPattern("EEEE, d MMMM yyyy", new Locale("id", "ID")));
                String selectedTime = ((ToggleButton) timeGroup.getSelectedToggle()).getText();
                String selectedMethod = ((ToggleButton) methodGroup.getSelectedToggle()).getText();
                String selectedPm = ((ToggleButton) pmGroup.getSelectedToggle()).getText();

                showSuccessDialog(data.name, selectedDate, selectedTime, selectedMethod, selectedPm,
                        data.pricePerSession, actualDisc, finalPrice, currentCoins > 0, coinsUsed);
            }
        });
    }

    private void showSuccessDialog(String docName, String date, String time, String method,
                                   String payMethod, long basePrice, long discount,
                                   long finalPrice, boolean usedCoin, long coinsUsed) {
        Dialog<ButtonType> dialog = new Dialog<>();
        dialog.setTitle("Pembayaran Berhasil");
        dialog.initModality(javafx.stage.Modality.APPLICATION_MODAL);

        VBox content = new VBox(20);
        content.setPadding(new Insets(25, 30, 25, 30));
        content.setAlignment(Pos.CENTER);
        content.setStyle("-fx-background-color: " + BG_PAGE + ";");

        // Icon Success Circle
        StackPane iconContainer = new StackPane();
        Circle circleBg = new Circle(45);
        circleBg.setFill(Color.web("#10B98120"));
        Circle circleInner = new Circle(35);
        circleInner.setFill(Color.web("#10B981"));
        Label checkIcon = new Label("✓");
        checkIcon.setFont(Font.font("System", FontWeight.BOLD, 40));
        checkIcon.setStyle("-fx-text-fill: white;");
        iconContainer.getChildren().addAll(circleBg, circleInner, checkIcon);

        // Title
        Label titleLabel = new Label("Pembayaran Berhasil!");
        titleLabel.setFont(Font.font("System", FontWeight.BOLD, 22));
        titleLabel.setStyle("-fx-text-fill: " + GREEN_DARK + ";");

        // Subtitle
        Label subtitleLabel = new Label("Janji konsultasi berhasil dibuat");
        subtitleLabel.setStyle("-fx-text-fill: " + GRAY_TEXT + "; -fx-font-size: 14px;");

        // Separator
        Separator sep = new Separator();
        sep.setStyle("-fx-background-color: #E5E7EB;");
        sep.setMaxWidth(350);

        // Detail Card
        VBox detailCard = new VBox(12);
        detailCard.setStyle(
                "-fx-background-color: white;" +
                        "-fx-background-radius: 16;" +
                        "-fx-effect: dropshadow(gaussian, rgba(0,0,0,0.05), 5, 0, 0, 2);"
        );
        detailCard.setPadding(new Insets(16));
        detailCard.setMaxWidth(380);

        // Detail rows
        detailCard.getChildren().add(createDetailRow("‍️ Psikolog", docName));
        detailCard.getChildren().add(createDetailRow(" Tanggal", date));
        detailCard.getChildren().add(createDetailRow(" Waktu", time));
        detailCard.getChildren().add(createDetailRow(" Metode", method));

        // Separator dalam detail
        Separator detailSep = new Separator();
        detailSep.setStyle("-fx-background-color: #F3F4F6;");
        detailCard.getChildren().add(detailSep);

        detailCard.getChildren().add(createDetailRow("Harga asal", formatRupiah(basePrice)));
        if (usedCoin) {
            detailCard.getChildren().add(createDetailRow("Diskon koin", "-" + formatRupiah(discount) + " (" + coinsUsed + " koin)"));
        }

        Label totalLabel = new Label("Total dibayar:");
        totalLabel.setStyle("-fx-text-fill: " + GRAY_TEXT + "; -fx-font-size: 12px;");
        Label totalValueLabel = new Label(formatRupiah(finalPrice));
        totalValueLabel.setFont(Font.font("System", FontWeight.BOLD, 18));
        totalValueLabel.setStyle("-fx-text-fill: " + GREEN_DARK + ";");

        HBox totalRow = new HBox(10);
        totalRow.setAlignment(Pos.CENTER_LEFT);
        totalRow.getChildren().addAll(totalLabel, new Region(), totalValueLabel);
        HBox.setHgrow(new Region(), Priority.ALWAYS);
        detailCard.getChildren().add(totalRow);

        detailCard.getChildren().add(createDetailRow("Dibayar via", payMethod));

        // Bonus Koin
        HBox bonusBox = new HBox(8);
        bonusBox.setAlignment(Pos.CENTER);
        bonusBox.setStyle(
                "-fx-background-color: #FEF3C7;" +
                        "-fx-background-radius: 20;" +
                        "-fx-padding: 8 16 8 16;"
        );
        Label bonusIcon = new Label("🎁");
        bonusIcon.setStyle("-fx-font-size: 14px;");
        Label bonusText = new Label("+10 Koin telah ditambahkan ke akun kamu!");
        bonusText.setStyle("-fx-text-fill: #D97706; -fx-font-size: 12px; -fx-font-weight: bold;");
        bonusBox.getChildren().addAll(bonusIcon, bonusText);

        // Footer message
        Label footerMsg = new Label("Tim CareVibe akan menghubungi kamu untuk konfirmasi.\nSemoga lekas membaik! 💚");
        footerMsg.setStyle("-fx-text-fill: " + GRAY_TEXT + "; -fx-font-size: 12px;");
        footerMsg.setAlignment(Pos.CENTER);

        // OK Button
        Button okButton = new Button("OK");
        okButton.setStyle(
                "-fx-background-color: " + GREEN_PRIMARY + ";" +
                        "-fx-text-fill: white;" +
                        "-fx-font-weight: bold;" +
                        "-fx-font-size: 14px;" +
                        "-fx-padding: 10 30 10 30;" +
                        "-fx-background-radius: 30;" +
                        "-fx-cursor: hand;"
        );
        okButton.setMaxWidth(200);
        okButton.setOnMouseEntered(e -> okButton.setStyle(
                "-fx-background-color: " + GREEN_HOVER + "; -fx-text-fill: white; -fx-font-weight: bold; -fx-font-size: 14px; -fx-padding: 10 30 10 30; -fx-background-radius: 30; -fx-cursor: hand;"
        ));
        okButton.setOnMouseExited(e -> okButton.setStyle(
                "-fx-background-color: " + GREEN_PRIMARY + "; -fx-text-fill: white; -fx-font-weight: bold; -fx-font-size: 14px; -fx-padding: 10 30 10 30; -fx-background-radius: 30; -fx-cursor: hand;"
        ));
        okButton.setOnAction(e -> dialog.close());

        content.getChildren().addAll(
                iconContainer, titleLabel, subtitleLabel, sep,
                detailCard, bonusBox, footerMsg, okButton
        );

        dialog.getDialogPane().setContent(content);
        dialog.getDialogPane().setPrefWidth(480);
        dialog.getDialogPane().setStyle("-fx-background-color: " + BG_PAGE + ";");

        // Remove default buttons
        dialog.getDialogPane().getButtonTypes().add(ButtonType.CLOSE);
        Button closeButton = (Button) dialog.getDialogPane().lookupButton(ButtonType.CLOSE);
        closeButton.setVisible(false);

        dialog.showAndWait();
    }

    private HBox createDetailRow(String label, String value) {
        HBox row = new HBox(10);
        row.setAlignment(Pos.CENTER_LEFT);

        Label labelLbl = new Label(label);
        labelLbl.setStyle("-fx-text-fill: " + GRAY_TEXT + "; -fx-font-size: 13px;");

        Region spacer = new Region();
        HBox.setHgrow(spacer, Priority.ALWAYS);

        Label valueLbl = new Label(value);
        valueLbl.setStyle("-fx-text-fill: " + DARK_TEXT + "; -fx-font-size: 13px; -fx-font-weight: bold;");

        row.getChildren().addAll(labelLbl, spacer, valueLbl);
        return row;
    }

    private VBox createSectionBox(String title) {
        VBox box = new VBox(10);
        Label lbl = new Label(title);
        lbl.setFont(Font.font("System", FontWeight.BOLD, 14));
        lbl.setStyle("-fx-text-fill: " + DARK_TEXT + ";");
        box.getChildren().add(lbl);
        return box;
    }

    private HBox createPayRow(String label, String value, String valueColor, boolean bold) {
        HBox row = new HBox();
        row.setAlignment(Pos.CENTER_LEFT);

        Label lbl = new Label(label);
        lbl.setStyle("-fx-text-fill: " + GRAY_TEXT + "; -fx-font-size: 13px;");

        Region spacer = new Region();
        HBox.setHgrow(spacer, Priority.ALWAYS);

        Label val = new Label(value);
        val.setStyle("-fx-text-fill: " + valueColor + "; -fx-font-size: " + (bold ? "15" : "13") + "px;" +
                (bold ? " -fx-font-weight: bold;" : ""));

        row.getChildren().addAll(lbl, spacer, val);
        return row;
    }

    private void showError(String header, String content) {
        Alert err = new Alert(Alert.AlertType.WARNING);
        err.setTitle("Perhatian");
        err.setHeaderText(header);
        err.setContentText(content);
        err.showAndWait();
    }

    private void styleButtonPrimary(Button btn) {
        String base = "-fx-background-color: " + GREEN_PRIMARY + "; -fx-text-fill: white;" +
                "-fx-font-weight: bold; -fx-font-size: 13px;" +
                "-fx-padding: 10 22 10 22; -fx-background-radius: 25; -fx-cursor: hand;";
        String hover = "-fx-background-color: " + GREEN_HOVER + "; -fx-text-fill: white;" +
                "-fx-font-weight: bold; -fx-font-size: 13px;" +
                "-fx-padding: 10 22 10 22; -fx-background-radius: 25; -fx-cursor: hand;";
        btn.setStyle(base);
        btn.setOnMouseEntered(e -> btn.setStyle(hover));
        btn.setOnMouseExited(e -> btn.setStyle(base));
    }

    private String formatRupiah(long amount) {
        String s = Long.toString(amount);
        StringBuilder sb = new StringBuilder();
        int count = 0;
        for (int i = s.length() - 1; i >= 0; i--) {
            if (count > 0 && count % 3 == 0) {
                sb.insert(0, '.');
            }
            sb.insert(0, s.charAt(i));
            count++;
        }
        return "Rp " + sb;
    }

    private static class PsychologistData {
        String name, title, specialization, avatar, color, description;
        double rating;
        int consultCount;
        long pricePerSession;
        List<String> availableDays;
        List<String> methods;
        boolean online;

        PsychologistData(String name, String title, String specialization,
                         double rating, int consultCount, String avatar, String color,
                         long pricePerSession, List<String> availableDays,
                         String description, List<String> methods, boolean online) {
            this.name = name;
            this.title = title;
            this.specialization = specialization;
            this.rating = rating;
            this.consultCount = consultCount;
            this.avatar = avatar;
            this.color = color;
            this.pricePerSession = pricePerSession;
            this.availableDays = availableDays;
            this.description = description;
            this.methods = methods;
            this.online = online;
        }
    }
}