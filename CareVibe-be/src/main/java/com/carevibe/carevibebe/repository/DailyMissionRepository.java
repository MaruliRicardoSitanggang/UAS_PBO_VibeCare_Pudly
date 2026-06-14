package com.carevibe.carevibebe.repository;

import com.carevibe.carevibebe.entity.DailyMission;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DailyMissionRepository extends JpaRepository<DailyMission, Long> {
}