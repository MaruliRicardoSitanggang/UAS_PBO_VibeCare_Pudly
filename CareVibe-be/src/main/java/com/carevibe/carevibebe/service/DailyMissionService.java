package com.carevibe.carevibebe.service;

import com.carevibe.carevibebe.entity.DailyMission;
import com.carevibe.carevibebe.repository.DailyMissionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class DailyMissionService {

    @Autowired
    private DailyMissionRepository dailyMissionRepository;

    public List<DailyMission> getAllMissions() {
        return dailyMissionRepository.findAll();
    }

    public DailyMission createMission(DailyMission mission) {
        return dailyMissionRepository.save(mission);
    }
}