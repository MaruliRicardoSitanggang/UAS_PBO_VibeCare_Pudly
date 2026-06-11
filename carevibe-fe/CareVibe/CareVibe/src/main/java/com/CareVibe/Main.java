package com.CareVibe;

import com.CareVibe.view.MainWindow;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {
    @Override
    public void start(Stage primaryStage) {
        MainWindow mainLayout = new MainWindow();
        Scene scene = new Scene(mainLayout, 1200, 680);

        // Memuat berkas CSS dari folder resources secara aman
        try {
            String cssPath = getClass().getResource("/style.css").toExternalForm();
            scene.getStylesheets().add(cssPath);
        } catch (Exception e) {
            System.out.println("Berkas style.css belum dimuat di folder resources.");
        }

        primaryStage.setTitle("VibeCare - Aplikasi Kesehatan Mental");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}