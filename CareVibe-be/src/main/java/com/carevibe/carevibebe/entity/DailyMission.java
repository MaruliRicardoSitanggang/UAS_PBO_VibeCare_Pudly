package com.carevibe.carevibebe.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

@Entity
@Table(name = "daily_missions")
public class DailyMission extends BaseEntity { // Penerapan INHERITANCE

    @NotBlank(message = "Judul misi tidak boleh kosong")
    private String title;

    @NotBlank(message = "Deskripsi misi tidak boleh kosong")
    private String description;

    @NotNull(message = "Poin misi harus diisi")
    @Min(value = 1, message = "Poin minimal bernilai 1")
    private Integer points;

    // ==========================================
    // GETTER & SETTER MANUAL (ENCAPSULATION)
    // ==========================================

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getPoints() {
        return points;
    }

    public void setPoints(Integer points) {
        this.points = points;
    }
}