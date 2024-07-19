package com.sparta.project.repository;

import com.sparta.project.entity.VideoStats;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Date;
import java.util.List;

public interface VideoStatsRepository extends JpaRepository<VideoStats, Long> {
    List<VideoStats> findByCreatedAtBetween(Date startDate, Date endDate);
}
