package com.sparta.project.repository;

import com.sparta.project.entity.AdStats;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Date;
import java.util.List;

public interface AdStatsRepository extends JpaRepository<AdStats, Long> {
    List<AdStats> findByCreatedAtBetween(Date startDate, Date endDate);
}
