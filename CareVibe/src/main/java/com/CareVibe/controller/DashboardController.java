package com.CareVibe.controller;

import com.CareVibe.model.UserSession;
import com.CareVibe.view.DashboardView;

public class DashboardController {

    private final DashboardView view;

    public DashboardController(DashboardView view) {
        this.view = view;
        initialize();
    }

    private void initialize() {
        String username = UserSession.getLoggedInName();
        if (username == null || username.isEmpty()) {
            username = UserSession.getLoggedInUser();
        }
        if (username == null || username.isEmpty()) {
            username = "Pengunjung";
        }
        view.setWelcomeMessage("Halo, " + username + "! Bagaimana perasaanmu hari ini?");

        // Hapus baris ini karena setUserPoints sudah tidak ada di DashboardView
        // view.setUserPoints(UserSession.getPoints());

        loadSummary();
    }

    private void loadSummary() {
        view.setMissionSummary("Kamu sudah menyelesaikan 2 dari 3 misi hari ini.");
        view.setMeditationSummary("Sesi meditasi terakhir: kemarin.");
    }
}