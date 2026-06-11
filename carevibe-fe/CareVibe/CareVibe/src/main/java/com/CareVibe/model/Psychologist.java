package com.CareVibe.model;

public class Psychologist {
    private String name;
    private String specialization;
    private String availability;

    public Psychologist(String name, String specialization, String availability) {
        this.name = name;
        this.specialization = specialization;
        this.availability = availability;
    }

    public String getName() { return name; }
    public String getSpecialization() { return specialization; }
    public String getAvailability() { return availability; }
}