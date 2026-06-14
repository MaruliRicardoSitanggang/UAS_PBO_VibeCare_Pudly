package com.carevibe.carevibebe.controller;

import com.carevibe.carevibebe.dto.ConsultationRequest;
import com.carevibe.carevibebe.entity.Consultation;
import com.carevibe.carevibebe.entity.User;
import com.carevibe.carevibebe.service.ConsultationService;
import com.carevibe.carevibebe.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import java.util.Optional;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/api/consultations")
public class ConsultationController {

    @Autowired
    private ConsultationService consultationService;

    @Autowired
    private UserService userService;

    @GetMapping("/user/{userId}")
    public List<Consultation> getUserConsultations(@PathVariable Long userId) {
        return consultationService.getConsultationsByUserId(userId);
    }

    @GetMapping("/user-email")
    public ResponseEntity<?> getConsultationsByEmail(@RequestParam String email) {
        Optional<User> userOpt = userService.findByEmail(email);
        if (userOpt.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User tidak ditemukan");
        }
        List<Consultation> list = consultationService.getConsultationsByUserId(userOpt.get().getId());
        return ResponseEntity.ok(list);
    }

    @PostMapping
    public ResponseEntity<?> createBooking(@RequestBody ConsultationRequest request) {
        // Cari user berdasarkan email
        Optional<User> userOpt = userService.findByEmail(request.getUserEmail());
        if (userOpt.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("User tidak ditemukan: " + request.getUserEmail());
        }

        // Cari psikolog berdasarkan ID
        Optional<User> psikologOpt = userService.findById(request.getPsychologistId());
        if (psikologOpt.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Psikolog tidak ditemukan dengan ID: " + request.getPsychologistId());
        }

        Consultation consultation = new Consultation();
        consultation.setUser(userOpt.get());
        consultation.setPsychologist(psikologOpt.get());
        consultation.setAppointmentTime(request.getAppointmentTime());
        consultation.setStatus(request.getStatus() != null ? request.getStatus() : "SCHEDULED");

        Consultation saved = consultationService.bookSchedule(consultation);
        return ResponseEntity.ok(saved);
    }
}