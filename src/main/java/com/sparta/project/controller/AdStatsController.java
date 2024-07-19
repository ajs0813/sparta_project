package com.sparta.project.controller;

import com.sparta.project.entity.AdStats;
import com.sparta.project.service.AdStatsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/api/stats/ads")
public class AdStatsController {

    private final AdStatsService adStatsService;

    @Autowired
    public AdStatsController(AdStatsService adStatsService) {
        this.adStatsService = adStatsService;
    }

    @PostMapping("/generate")
    public void generateStats(@RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") Date date) {
        adStatsService.generateStats(date);
    }

    @GetMapping("/top-views")
    public List<AdStats> getTopAdsByViews(@RequestParam String period) {
        Date now = new Date();
        Date startDate;
        Date endDate;

        switch (period) {
            case "day":
                startDate = adStatsService.getStartOfDay(now);
                endDate = adStatsService.getEndOfDay(now);
                break;

            case "week":
                startDate = adStatsService.getStartOfWeek(now);
                endDate = adStatsService.getEndOfWeek(now);
                break;

            case "month":
                startDate = adStatsService.getStartOfMonth(now);
                endDate = adStatsService.getEndOfMonth(now);
                break;

            default:
                throw new IllegalArgumentException("Invalid period: " + period);
        }

        return adStatsService.getTop5AdsByViews(startDate, endDate);
    }
}
