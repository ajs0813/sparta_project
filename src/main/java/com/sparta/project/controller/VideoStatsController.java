package com.sparta.project.controller;

import com.sparta.project.entity.VideoStats;
import com.sparta.project.service.VideoStatsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/api/stats/videos")
public class VideoStatsController {

    private final VideoStatsService videoStatsService;

    @Autowired
    public VideoStatsController(VideoStatsService videoStatsService) {
        this.videoStatsService = videoStatsService;
    }

    @PostMapping("/generate")
    public void generateStats(@RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") Date date) {
        videoStatsService.generateStats(date);
    }

    @GetMapping("/top-views")
    public List<VideoStats> getTopVideosByViews(@RequestParam String period) {
        Date now = new Date();
        Date startDate;
        Date endDate;

        switch (period) {
            case "day":
                startDate = videoStatsService.getStartOfDay(now);
                endDate = videoStatsService.getEndOfDay(now);
                break;

            case "week":
                startDate = videoStatsService.getStartOfWeek(now);
                endDate = videoStatsService.getEndOfWeek(now);
                break;

            case "month":
                startDate = videoStatsService.getStartOfMonth(now);
                endDate = videoStatsService.getEndOfMonth(now);
                break;

            default:
                throw new IllegalArgumentException("Invalid period: " + period);
        }

        return videoStatsService.getTop5VideosByViews(startDate, endDate);
    }

    @GetMapping("/top-times")
    public List<VideoStats> getTopVideosByTimes(@RequestParam String period) {
        Date now = new Date();
        Date startDate;
        Date endDate;

        switch (period) {
            case "day":
                startDate = videoStatsService.getStartOfDay(now);
                endDate = videoStatsService.getEndOfDay(now);
                break;

            case "week":
                startDate = videoStatsService.getStartOfWeek(now);
                endDate = videoStatsService.getEndOfWeek(now);
                break;

            case "month":
                startDate = videoStatsService.getStartOfMonth(now);
                endDate = videoStatsService.getEndOfMonth(now);
                break;

            default:
                throw new IllegalArgumentException("Invalid period: " + period);
        }

        return videoStatsService.getTop5VideosByTimes(startDate, endDate);
    }
}
