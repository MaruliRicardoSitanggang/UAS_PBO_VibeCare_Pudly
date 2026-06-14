package com.CareVibe.controller;

import com.CareVibe.model.Psychologist;
import com.CareVibe.view.ConsultationView;

import java.util.ArrayList;
import java.util.List;

public class ConsultationController {


    private final ConsultationView view;
    private final List<Psychologist> psychologists = new ArrayList<>();

    public ConsultationController(ConsultationView view) {
        this.view = view;
        loadPsychologists();
    }

    private void loadPsychologists() {
        // TODO: ambil dari database
        psychologists.add(new Psychologist("Dr. Rina", "Anxiety & Stress", "Senin–Jumat"));
        psychologists.add(new Psychologist("Dr. Budi", "Depresi & Trauma", "Selasa–Sabtu"));
    }
}