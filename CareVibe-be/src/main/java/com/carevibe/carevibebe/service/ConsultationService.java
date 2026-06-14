package com.carevibe.carevibebe.service;

import com.carevibe.carevibebe.entity.Consultation;
import com.carevibe.carevibebe.repository.ConsultationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ConsultationService {

    @Autowired
    private ConsultationRepository consultationRepository;

    public List<Consultation> getConsultationsByUserId(Long userId) {
        return consultationRepository.findByUserId(userId);
    }

    public Consultation bookSchedule(Consultation consultation) {
        if (consultation.getStatus() == null || consultation.getStatus().isEmpty()) {
            consultation.setStatus("SCHEDULED");
        }
        return consultationRepository.save(consultation);
    }
}