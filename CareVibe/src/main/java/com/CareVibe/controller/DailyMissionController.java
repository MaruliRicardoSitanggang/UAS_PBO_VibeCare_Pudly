package com.carevibe.carevibebe.controller;

import com.carevibe.carevibebe.entity.DailyMission;
import com.carevibe.carevibebe.service.DailyMissionService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/api/missions")
public class DailyMissionController {

    @Autowired
    private DailyMissionService dailyMissionService;

    @GetMapping
    public List<DailyMission> getAllMissions() {
        return dailyMissionService.getAllMissions();
    }

    @PostMapping
    public DailyMission createMission(@Valid @RequestBody DailyMission mission) {
        return dailyMissionService.createMission(mission);
    }
}