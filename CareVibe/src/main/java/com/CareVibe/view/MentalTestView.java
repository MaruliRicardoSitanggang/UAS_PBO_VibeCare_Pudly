package com.CareVibe.view;

import com.CareVibe.model.UserSession;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

public class MentalTestView extends VBox {

    private MainWindow mainWindow;

    public MentalTestView() {
        this(null);
    }

    public MentalTestView(MainWindow mainWindow) {
        this.mainWindow = mainWindow;
        initializeUI();
    }

    private void initializeUI() {
        this.setStyle("-fx-background-color: #F0FDF4; -fx-padding: 30;");
        this.setSpacing(25);

        ScrollPane scrollPane = new ScrollPane();
        scrollPane.setFitToWidth(true);
        scrollPane.setStyle("-fx-background-color: transparent; -fx-background: transparent;");
        scrollPane.setVbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED);

        VBox content = new VBox(25);
        content.setPadding(new Insets(0, 0, 20, 0));
        content.setAlignment(Pos.TOP_LEFT);

        VBox headerBox = createHeaderSection();
        GridPane gridTes = createTestGrid();
        HBox footerBox = createFooterSection();

        content.getChildren().addAll(headerBox, gridTes, footerBox);
        scrollPane.setContent(content);

        this.getChildren().add(scrollPane);
    }

    private VBox createHeaderSection() {
        VBox header = new VBox(10);
        header.setAlignment(Pos.TOP_LEFT);

        Label title = new Label("Tes Kesehatan Mental");
        title.setFont(Font.font("System", FontWeight.BOLD, 28));
        title.setStyle("-fx-text-fill: #065F46;");

        Label subtitle = new Label("Lakukan pengecekan kesehatan mental Anda dengan tes yang telah divalidasi oleh para ahli.");
        subtitle.setWrapText(true);
        subtitle.setStyle("-fx-text-fill: #4B5563; -fx-font-size: 14px;");

        header.getChildren().addAll(title, subtitle);
        return header;
    }

    private GridPane createTestGrid() {
        GridPane grid = new GridPane();
        grid.setHgap(25);
        grid.setVgap(25);
        grid.setAlignment(Pos.CENTER_LEFT);
        grid.setPadding(new Insets(10, 0, 20, 0));

        VBox cardDepresi = createTestCard(
                "Tes Depresi (PHQ-9)",
                "Tes untuk mengevaluasi gejala depresi dalam 2 minggu terakhir.",
                "9 pertanyaan",
                "Depresi",
                "#EF4444",
                "depresi"
        );

        VBox cardKecemasan = createTestCard(
                "Tes Kecemasan (GAD-7)",
                "Tes untuk mengevaluasi tingkat kecemasan yang Anda alami.",
                "7 pertanyaan",
                "Kecemasan",
                "#F59E0B",
                "kecemasan"
        );

        VBox cardStres = createTestCard(
                "Tes Tingkat Stres",
                "Ketahui seberapa berat beban stres yang Anda rasakan.",
                "10 pertanyaan",
                "Stres",
                "#8B5CF6",
                "stres"
        );

        grid.add(cardDepresi, 0, 0);
        grid.add(cardKecemasan, 1, 0);
        grid.add(cardStres, 2, 0);

        return grid;
    }

    private VBox createTestCard(String title, String description, String duration, String badgeText, String badgeColor, String testType) {
        VBox card = new VBox(12);
        card.setStyle(
                "-fx-background-color: white;" +
                        "-fx-background-radius: 20;" +
                        "-fx-effect: dropshadow(gaussian, rgba(0,0,0,0.08), 16, 0, 0, 6);"
        );
        card.setPadding(new Insets(22));
        card.setPrefWidth(300);

        Label badge = new Label(badgeText);
        badge.setStyle(
                "-fx-background-color: " + badgeColor + "18;" +
                        "-fx-text-fill: " + badgeColor + ";" +
                        "-fx-font-size: 12px;" +
                        "-fx-font-weight: bold;" +
                        "-fx-padding: 5 14 5 14;" +
                        "-fx-background-radius: 20;"
        );

        Label lblTitle = new Label(title);
        lblTitle.setFont(Font.font("System", FontWeight.BOLD, 18));
        lblTitle.setStyle("-fx-text-fill: #1F2937;");
        lblTitle.setWrapText(true);

        Label lblDesc = new Label(description);
        lblDesc.setWrapText(true);
        lblDesc.setStyle("-fx-text-fill: #6B7280; -fx-font-size: 13px;");

        HBox durationBox = new HBox(6);
        durationBox.setAlignment(Pos.CENTER_LEFT);
        Label lblDuration = new Label(duration);
        lblDuration.setStyle("-fx-text-fill: #9CA3AF; -fx-font-size: 12px;");
        durationBox.getChildren().add(lblDuration);

        Region spacer = new Region();
        VBox.setVgrow(spacer, Priority.ALWAYS);

        Button btnStart = new Button("Mulai Tes");
        btnStart.setStyle(
                "-fx-background-color: linear-gradient(to right, #10B981, #059669);" +
                        "-fx-text-fill: white;" +
                        "-fx-font-weight: bold;" +
                        "-fx-font-size: 13px;" +
                        "-fx-padding: 11 22 11 22;" +
                        "-fx-background-radius: 30;" +
                        "-fx-cursor: hand;"
        );
        btnStart.setMaxWidth(Double.MAX_VALUE);

        btnStart.setOnMouseEntered(e ->
                btnStart.setStyle(
                        "-fx-background-color: linear-gradient(to right, #059669, #047857);" +
                                "-fx-text-fill: white;" +
                                "-fx-font-weight: bold;" +
                                "-fx-font-size: 13px;" +
                                "-fx-padding: 11 22 11 22;" +
                                "-fx-background-radius: 30;" +
                                "-fx-cursor: hand;" +
                                "-fx-effect: dropshadow(gaussian, rgba(16,185,129,0.4), 10, 0, 0, 4);"
                )
        );
        btnStart.setOnMouseExited(e ->
                btnStart.setStyle(
                        "-fx-background-color: linear-gradient(to right, #10B981, #059669);" +
                                "-fx-text-fill: white;" +
                                "-fx-font-weight: bold;" +
                                "-fx-font-size: 13px;" +
                                "-fx-padding: 11 22 11 22;" +
                                "-fx-background-radius: 30;" +
                                "-fx-cursor: hand;"
                )
        );

        btnStart.setOnAction(e -> showTestWizard(testType, title));

        card.getChildren().addAll(badge, lblTitle, lblDesc, durationBox, spacer, btnStart);
        return card;
    }

    private void showTestWizard(String testType, String testName) {
        List<Question> questions = getQuestionsForTest(testType);
        List<Integer> answers = new ArrayList<>();
        for (int i = 0; i < questions.size(); i++) answers.add(-1);

        Stage testStage = new Stage();
        testStage.setTitle(testName);
        testStage.initModality(Modality.APPLICATION_MODAL);
        testStage.setResizable(false);

        VBox mainContainer = new VBox(20);
        mainContainer.setPadding(new Insets(28));
        mainContainer.setStyle("-fx-background-color: #F8FFFE;");
        mainContainer.setPrefWidth(600);

        // ─── Header row ───────────────────────────────────────────────
        HBox headerRow = new HBox();
        headerRow.setAlignment(Pos.CENTER_LEFT);

        Label testTitle = new Label(testName);
        testTitle.setFont(Font.font("System", FontWeight.BOLD, 17));
        testTitle.setStyle("-fx-text-fill: #065F46;");

        Region hSpacer = new Region();
        HBox.setHgrow(hSpacer, Priority.ALWAYS);

        Label pointsBadge = new Label("+25 Poin");
        pointsBadge.setStyle(
                "-fx-background-color: #FEF3C7;" +
                        "-fx-text-fill: #D97706;" +
                        "-fx-font-weight: bold;" +
                        "-fx-font-size: 12px;" +
                        "-fx-padding: 5 12 5 12;" +
                        "-fx-background-radius: 20;"
        );

        headerRow.getChildren().addAll(testTitle, hSpacer, pointsBadge);

        // ─── Progress bar + label ──────────────────────────────────────
        int totalQuestions = questions.size();

        Label progressLabel = new Label("Pertanyaan 1/" + totalQuestions);
        progressLabel.setStyle("-fx-text-fill: #6B7280; -fx-font-size: 12px;");
        progressLabel.setAlignment(Pos.CENTER_RIGHT);
        progressLabel.setMaxWidth(Double.MAX_VALUE);

        ProgressBar progressBar = new ProgressBar(1.0 / totalQuestions);
        progressBar.setMaxWidth(Double.MAX_VALUE);
        progressBar.setPrefHeight(8);
        progressBar.setStyle(
                "-fx-accent: #10B981;" +
                        "-fx-background-color: #D1FAE5;" +
                        "-fx-background-radius: 10;" +
                        "-fx-pref-height: 8;"
        );

        // ─── Question card ─────────────────────────────────────────────
        VBox questionCard = new VBox(18);
        questionCard.setStyle(
                "-fx-background-color: white;" +
                        "-fx-background-radius: 20;" +
                        "-fx-effect: dropshadow(gaussian, rgba(0,0,0,0.07), 12, 0, 0, 4);"
        );
        questionCard.setPadding(new Insets(28));

        Label questionLabel = new Label();
        questionLabel.setFont(Font.font("System", FontWeight.BOLD, 16));
        questionLabel.setWrapText(true);
        questionLabel.setStyle("-fx-text-fill: #1F2937; -fx-line-spacing: 3;");

        VBox optionsBox = new VBox(10);

        ToggleGroup optionGroup = new ToggleGroup();
        List<RadioButton> optionButtons = new ArrayList<>();
        String[] options = {"Tidak sama sekali", "Beberapa hari", "Lebih dari setengah hari", "Hampir setiap hari"};

        for (String opt : options) {
            RadioButton rb = new RadioButton(opt);
            rb.setToggleGroup(optionGroup);
            rb.setStyle(
                    "-fx-font-size: 14px;" +
                            "-fx-padding: 12 16 12 16;" +
                            "-fx-background-color: #F9FAFB;" +
                            "-fx-background-radius: 12;" +
                            "-fx-cursor: hand;"
            );
            rb.setMaxWidth(Double.MAX_VALUE);

            rb.selectedProperty().addListener((obs, wasSelected, isSelected) -> {
                if (isSelected) {
                    rb.setStyle(
                            "-fx-font-size: 14px;" +
                                    "-fx-padding: 12 16 12 16;" +
                                    "-fx-background-color: #D1FAE5;" +
                                    "-fx-background-radius: 12;" +
                                    "-fx-text-fill: #065F46;" +
                                    "-fx-cursor: hand;"
                    );
                } else {
                    rb.setStyle(
                            "-fx-font-size: 14px;" +
                                    "-fx-padding: 12 16 12 16;" +
                                    "-fx-background-color: #F9FAFB;" +
                                    "-fx-background-radius: 12;" +
                                    "-fx-cursor: hand;"
                    );
                }
            });

            optionButtons.add(rb);
            optionsBox.getChildren().add(rb);
        }

        // ─── Navigation buttons ────────────────────────────────────────
        HBox navButtons = new HBox(12);
        navButtons.setAlignment(Pos.CENTER_RIGHT);
        navButtons.setPadding(new Insets(10, 0, 0, 0));

        Button btnPrev = new Button("← Sebelumnya");
        Button btnNext = new Button("Selanjutnya →");

        btnPrev.setStyle(
                "-fx-background-color: #F3F4F6;" +
                        "-fx-text-fill: #6B7280;" +
                        "-fx-font-weight: bold;" +
                        "-fx-font-size: 13px;" +
                        "-fx-padding: 11 20 11 20;" +
                        "-fx-background-radius: 30;" +
                        "-fx-cursor: hand;"
        );
        btnNext.setStyle(
                "-fx-background-color: linear-gradient(to right, #10B981, #059669);" +
                        "-fx-text-fill: white;" +
                        "-fx-font-weight: bold;" +
                        "-fx-font-size: 13px;" +
                        "-fx-padding: 11 24 11 24;" +
                        "-fx-background-radius: 30;" +
                        "-fx-cursor: hand;"
        );

        navButtons.getChildren().addAll(btnPrev, btnNext);

        questionCard.getChildren().addAll(questionLabel, optionsBox, navButtons);
        mainContainer.getChildren().addAll(headerRow, progressBar, progressLabel, questionCard);

        // ─── State logic ───────────────────────────────────────────────
        AtomicInteger currentIndex = new AtomicInteger(0);

        Runnable updateDisplay = () -> {
            int idx = currentIndex.get();
            Question q = questions.get(idx);
            questionLabel.setText(q.getText());

            optionGroup.selectToggle(null);
            int savedAnswer = answers.get(idx);
            if (savedAnswer >= 0 && savedAnswer < optionButtons.size()) {
                optionGroup.selectToggle(optionButtons.get(savedAnswer));
            }

            progressLabel.setText("Pertanyaan " + (idx + 1) + "/" + totalQuestions);
            progressBar.setProgress((double)(idx + 1) / totalQuestions);

            btnPrev.setVisible(idx > 0);
            btnNext.setText(idx == totalQuestions - 1 ? "Selesai ✓" : "Selanjutnya →");
        };

        Runnable saveCurrentAnswer = () -> {
            int idx = currentIndex.get();
            RadioButton selected = (RadioButton) optionGroup.getSelectedToggle();
            if (selected != null) {
                answers.set(idx, optionButtons.indexOf(selected));
            }
        };

        updateDisplay.run();

        btnPrev.setOnAction(e -> {
            saveCurrentAnswer.run();
            if (currentIndex.get() > 0) {
                currentIndex.set(currentIndex.get() - 1);
                updateDisplay.run();
            }
        });

        btnNext.setOnAction(e -> {
            if (optionGroup.getSelectedToggle() == null) {
                showWarning("Pilih jawaban terlebih dahulu sebelum melanjutkan.");
                return;
            }

            saveCurrentAnswer.run();

            if (currentIndex.get() == totalQuestions - 1) {
                if (answers.stream().anyMatch(a -> a < 0)) {
                    showWarning("Mohon jawab semua pertanyaan terlebih dahulu!");
                    return;
                }
                TestResult result = calculateResult(testType, testName, answers, totalQuestions);
                testStage.close();
                showResultDialog(result);
                UserSession.addPoints(25);
            } else {
                currentIndex.set(currentIndex.get() + 1);
                updateDisplay.run();
            }
        });

        Scene scene = new Scene(mainContainer);
        testStage.setScene(scene);
        testStage.showAndWait();
    }

    private void showWarning(String message) {
        Alert warning = new Alert(Alert.AlertType.WARNING);
        warning.setTitle("Peringatan");
        warning.setHeaderText(null);
        warning.setContentText(message);
        warning.showAndWait();
    }

    private void showResultDialog(TestResult result) {
        Stage resultStage = new Stage();
        resultStage.setTitle("Hasil Tes");
        resultStage.initModality(Modality.APPLICATION_MODAL);
        resultStage.setResizable(false);

        VBox root = new VBox(0);
        root.setAlignment(Pos.CENTER);
        root.setStyle("-fx-background-color: #F0FDF4;");
        root.setPadding(new Insets(30));

        VBox card = new VBox(16);
        card.setStyle(
                "-fx-background-color: white;" +
                        "-fx-background-radius: 24;" +
                        "-fx-effect: dropshadow(gaussian, rgba(0,0,0,0.10), 20, 0, 0, 6);"
        );
        card.setPadding(new Insets(36));
        card.setAlignment(Pos.CENTER);
        card.setMaxWidth(420);

        Label titleLabel = new Label("Hasil Tes Terakhir");
        titleLabel.setFont(Font.font("System", FontWeight.BOLD, 22));
        titleLabel.setStyle("-fx-text-fill: #1F2937;");

        // ─── Circular progress canvas ──────────────────────────────────
        int size = 160;
        Canvas canvas = new Canvas(size, size);
        drawCircularProgress(canvas, result.percentage);

        // ─── Status row ────────────────────────────────────────────────
        String statusColor = getStatusColor(result.percentage);

        Label statusTagLabel = new Label("Skor Kesehatan Mental Anda");
        statusTagLabel.setStyle("-fx-text-fill: #6B7280; -fx-font-size: 14px;");

        Label statusLabel = new Label(result.status);
        statusLabel.setFont(Font.font("System", FontWeight.BOLD, 18));
        statusLabel.setStyle("-fx-text-fill: " + statusColor + ";");

        Label scoreLabel = new Label("Skor Anda: " + result.score + " dari " + result.maxScore);
        scoreLabel.setStyle("-fx-text-fill: #4B5563; -fx-font-size: 14px;");

        Label categoryLabel = new Label(result.category);
        categoryLabel.setStyle("-fx-text-fill: #9CA3AF; -fx-font-size: 13px;");

        // ─── Repeat button ─────────────────────────────────────────────
        Button btnClose = new Button("Tutup");
        btnClose.setStyle(
                "-fx-background-color: linear-gradient(to right, #10B981, #059669);" +
                        "-fx-text-fill: white;" +
                        "-fx-font-weight: bold;" +
                        "-fx-font-size: 14px;" +
                        "-fx-padding: 13 0 13 0;" +
                        "-fx-background-radius: 30;" +
                        "-fx-cursor: hand;"
        );
        btnClose.setMaxWidth(Double.MAX_VALUE);
        btnClose.setPrefWidth(340);

        btnClose.setOnMouseEntered(e ->
                btnClose.setStyle(
                        "-fx-background-color: linear-gradient(to right, #059669, #047857);" +
                                "-fx-text-fill: white;" +
                                "-fx-font-weight: bold;" +
                                "-fx-font-size: 14px;" +
                                "-fx-padding: 13 0 13 0;" +
                                "-fx-background-radius: 30;" +
                                "-fx-cursor: hand;" +
                                "-fx-effect: dropshadow(gaussian, rgba(16,185,129,0.4), 12, 0, 0, 4);"
                )
        );
        btnClose.setOnMouseExited(e ->
                btnClose.setStyle(
                        "-fx-background-color: linear-gradient(to right, #10B981, #059669);" +
                                "-fx-text-fill: white;" +
                                "-fx-font-weight: bold;" +
                                "-fx-font-size: 14px;" +
                                "-fx-padding: 13 0 13 0;" +
                                "-fx-background-radius: 30;" +
                                "-fx-cursor: hand;"
                )
        );

        btnClose.setOnAction(e -> resultStage.close());

        card.getChildren().addAll(titleLabel, canvas, statusTagLabel, statusLabel, scoreLabel, categoryLabel, btnClose);
        root.getChildren().add(card);

        Scene scene = new Scene(root, 480, 540);
        resultStage.setScene(scene);
        resultStage.showAndWait();
    }

    private void drawCircularProgress(Canvas canvas, int percentage) {
        GraphicsContext gc = canvas.getGraphicsContext2D();
        double size = canvas.getWidth();
        double cx = size / 2;
        double cy = size / 2;
        double radius = size / 2 - 14;
        double strokeWidth = 13;

        // Background arc
        gc.setStroke(Color.web("#E5E7EB"));
        gc.setLineWidth(strokeWidth);
        gc.setLineCap(javafx.scene.shape.StrokeLineCap.ROUND);
        gc.strokeArc(cx - radius, cy - radius, radius * 2, radius * 2, -90, -360, javafx.scene.shape.ArcType.OPEN);

        // Foreground arc
        String colorHex = getStatusColor(percentage);
        gc.setStroke(Color.web(colorHex));
        double sweep = -(percentage / 100.0) * 360;
        gc.strokeArc(cx - radius, cy - radius, radius * 2, radius * 2, 90, sweep, javafx.scene.shape.ArcType.OPEN);

        // Center text
        gc.setFill(Color.web("#1F2937"));
        gc.setFont(Font.font("System", FontWeight.BOLD, 26));
        String text = percentage + "%";
        javafx.scene.text.Text tempText = new javafx.scene.text.Text(text);
        tempText.setFont(gc.getFont());
        double textWidth = tempText.getBoundsInLocal().getWidth();
        double textHeight = tempText.getBoundsInLocal().getHeight();
        gc.fillText(text, cx - textWidth / 2, cy + textHeight / 4);
    }

    private String getStatusColor(int percentage) {
        if (percentage <= 35) return "#10B981";
        if (percentage <= 60) return "#F59E0B";
        return "#EF4444";
    }

    private List<Question> getQuestionsForTest(String testType) {
        List<Question> questions = new ArrayList<>();

        if (testType.equals("depresi")) {
            String[] q = {
                    "Seberapa sering Anda merasa sedikit minat atau kesenangan dalam melakukan sesuatu?",
                    "Seberapa sering Anda merasa sedih, putus asa, atau tertekan?",
                    "Seberapa sering Anda sulit tidur, sering terbangun, atau tidur terlalu banyak?",
                    "Seberapa sering Anda merasa lelah atau kurang memiliki energi?",
                    "Seberapa sering nafsu makan Anda berkurang atau makan berlebihan?",
                    "Seberapa sering Anda merasa tidak berharga atau merasa bersalah berlebihan?",
                    "Seberapa sering Anda sulit berkonsentrasi pada sesuatu?",
                    "Seberapa sering Anda bergerak atau bicara sangat lambat, atau sebaliknya sangat gelisah?",
                    "Seberapa sering Anda memiliki pikiran untuk menyakiti diri sendiri?"
            };
            for (String s : q) questions.add(new Question(s));
        } else if (testType.equals("kecemasan")) {
            String[] q = {
                    "Seberapa sering Anda merasa gugup, cemas, atau sangat gelisah?",
                    "Seberapa sering Anda tidak mampu menghentikan atau mengendalikan kekhawatiran?",
                    "Seberapa sering Anda terlalu khawatir tentang berbagai hal?",
                    "Seberapa sering Anda merasa sulit untuk bersantai?",
                    "Seberapa sering Anda sangat gelisah hingga sulit untuk diam?",
                    "Seberapa sering Anda mudah kesal atau terganggu?",
                    "Seberapa sering Anda merasa takut seolah akan terjadi sesuatu yang buruk?"
            };
            for (String s : q) questions.add(new Question(s));
        } else {
            String[] q = {
                    "Seberapa sering Anda merasa kewalahan dengan tanggung jawab Anda?",
                    "Seberapa sering Anda sulit mengatur waktu dengan baik?",
                    "Seberapa sering Anda mudah marah atau merasa frustrasi?",
                    "Seberapa sering Anda merasakan tekanan dari lingkungan sekitar?",
                    "Seberapa sering Anda sulit tidur karena pikiran yang tidak bisa berhenti?",
                    "Seberapa sering Anda merasa tidak mampu mengendalikan hal-hal penting dalam hidup?",
                    "Seberapa sering Anda mengalami sakit kepala atau ketegangan otot akibat stres?",
                    "Seberapa sering Anda menarik diri dari pergaulan sosial?",
                    "Seberapa sering Anda mengalami perubahan suasana hati yang drastis?",
                    "Seberapa sering Anda merasa kelelahan secara emosional?"
            };
            for (String s : q) questions.add(new Question(s));
        }

        return questions;
    }

    private TestResult calculateResult(String testType, String testName, List<Integer> answers, int totalQuestions) {
        int totalScore = 0;
        for (int a : answers) totalScore += a;

        int maxScore = totalQuestions * 3;
        int percentage;
        String status, category;

        if (testType.equals("depresi")) {
            if (totalScore <= 4) { status = "Tidak Ada Depresi"; category = "Skor kesehatan mental Anda baik"; percentage = 15; }
            else if (totalScore <= 9) { status = "Depresi Ringan"; category = "Gejala depresi ringan terdeteksi"; percentage = 35; }
            else if (totalScore <= 14) { status = "Depresi Sedang"; category = "Gejala depresi sedang"; percentage = 55; }
            else if (totalScore <= 19) { status = "Depresi Sedang-Berat"; category = "Disarankan segera konsultasi"; percentage = 75; }
            else { status = "Depresi Berat"; category = "Segera konsultasi profesional"; percentage = 90; }
        } else if (testType.equals("kecemasan")) {
            if (totalScore <= 4) { status = "Kecemasan Minimal"; category = "Skor kesehatan mental Anda baik"; percentage = 20; }
            else if (totalScore <= 9) { status = "Kecemasan Ringan"; category = "Gejala kecemasan ringan terdeteksi"; percentage = 40; }
            else if (totalScore <= 14) { status = "Kecemasan Sedang"; category = "Gejala kecemasan sedang"; percentage = 67; }
            else { status = "Kecemasan Berat"; category = "Segera konsultasi profesional"; percentage = 85; }
        } else {
            if (totalScore <= 10) { status = "Stres Ringan"; category = "Tingkat stres masih normal"; percentage = 28; }
            else if (totalScore <= 20) { status = "Stres Sedang"; category = "Perlu manajemen stres yang baik"; percentage = 55; }
            else { status = "Stres Berat"; category = "Segera konsultasi profesional"; percentage = 80; }
        }

        return new TestResult(testType, testName, totalScore, maxScore, percentage, status, category);
    }

    private HBox createFooterSection() {
        HBox footer = new HBox();
        footer.setAlignment(Pos.CENTER);
        footer.setPadding(new Insets(10, 0, 10, 0));

        Label infoLabel = new Label("Tes ini telah divalidasi oleh psikolog profesional");
        infoLabel.setStyle("-fx-text-fill: #9CA3AF; -fx-font-size: 12px;");

        footer.getChildren().add(infoLabel);
        return footer;
    }

    public void refresh() {
        this.getChildren().clear();
        initializeUI();
    }

    // ─── Inner classes ─────────────────────────────────────────────────────────

    private static class Question {
        private final String text;
        Question(String text) { this.text = text; }
        String getText() { return text; }
    }

    private static class TestResult {
        String testType, testName, status, category;
        int score, maxScore, percentage;

        TestResult(String testType, String testName, int score, int maxScore, int percentage, String status, String category) {
            this.testType = testType;
            this.testName = testName;
            this.score = score;
            this.maxScore = maxScore;
            this.percentage = percentage;
            this.status = status;
            this.category = category;
        }
    }
}
