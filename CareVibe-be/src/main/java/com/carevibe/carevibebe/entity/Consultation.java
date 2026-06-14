package com.carevibe.carevibebe.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.JoinColumn;
import jakarta.validation.constraints.NotBlank;
import java.time.LocalDateTime;

@Entity
@Table(name = "consultations")
public class Consultation extends BaseEntity {

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user; // Pasien yang melakukan booking (ROLE_USER)

    @ManyToOne
    @JoinColumn(name = "psychologist_id", nullable = false)
    private User psychologist; // Psikolog yang dipilih (ROLE_PSIKOLOG)

    private LocalDateTime appointmentTime;

    @NotBlank(message = "Status konsultasi wajib diisi")
    private String status; // e.g., "SCHEDULED", "COMPLETED"

    // ==========================================
    // GETTER & SETTER MANUAL (ENCAPSULATION)
    // ==========================================

    public User getUser() { return user; }
    public void setUser(User user) { this.user = user; }

    public User getPsychologist() { return psychologist; }
    public void setPsychologist(User psychologist) { this.psychologist = psychologist; }

    public LocalDateTime getAppointmentTime() { return appointmentTime; }
    public void setAppointmentTime(LocalDateTime appointmentTime) { this.appointmentTime = appointmentTime; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
}