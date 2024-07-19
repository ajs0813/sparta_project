package com.sparta.project.service;

import com.sparta.project.entity.AdPlay;
import com.sparta.project.entity.AdStats;
import com.sparta.project.entity.Video;
import com.sparta.project.repository.AdPlayRepository;
import com.sparta.project.repository.AdStatsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class AdStatsService {
    private final AdStatsRepository adStatsRepository;
    private final AdPlayRepository adPlayRepository;

    @Autowired
    public AdStatsService(
            AdStatsRepository adStatsRepository,
            AdPlayRepository adPlayRepository) {
        this.adStatsRepository = adStatsRepository;
        this.adPlayRepository = adPlayRepository;
    }

    public void generateStats(Date date) {
        Date startOfDay = getStartOfDay(date);
        Date endOfDay = getEndOfDay(date);

        // 주어진 날짜에 해당하는 AdPlay 데이터 가져오기
        List<AdPlay> adPlays = adPlayRepository.findByCreatedAtBetween(startOfDay, endOfDay);

        // 비디오 ID별로 그룹화
        Map<Video, List<AdPlay>> groupedByVideo = adPlays.stream()
                .collect(Collectors.groupingBy(AdPlay::getVideo));

        for (Map.Entry<Video, List<AdPlay>> entry : groupedByVideo.entrySet()) {
            Video video = entry.getKey();
            List<AdPlay> playsForVideo = entry.getValue();

            // 광고 조회수 (해당 비디오의 AdPlay 수)
            long totalViews = playsForVideo.size();

            // AdStats 객체 생성 및 저장
            AdStats adStats = new AdStats();
            adStats.setCreatedAt(date);
            adStats.setVideo(video);
            adStats.setAd(adPlayRepository.findAdForVideo(video)); // 이 부분은 광고와 비디오의 관계를 정의하는 로직이 필요
            adStats.setAdViews(totalViews);

            adStatsRepository.save(adStats);
        }
    }

    public List<AdStats> getTop5AdsByViews(Date startDate, Date endDate) {
        return adStatsRepository.findByCreatedAtBetween(startDate, endDate);
    }

    public Date getStartOfDay(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.set(Calendar.HOUR_OF_DAY, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MILLISECOND, 0);
        return cal.getTime();
    }

    public Date getEndOfDay(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.set(Calendar.HOUR_OF_DAY, 23);
        cal.set(Calendar.MINUTE, 59);
        cal.set(Calendar.SECOND, 59);
        cal.set(Calendar.MILLISECOND, 999);
        return cal.getTime();
    }

    public Date getStartOfWeek(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
        cal.set(Calendar.HOUR_OF_DAY, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MILLISECOND, 0);
        return cal.getTime();
    }

    public Date getEndOfWeek(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.set(Calendar.DAY_OF_WEEK, Calendar.SUNDAY);
        cal.set(Calendar.HOUR_OF_DAY, 23);
        cal.set(Calendar.MINUTE, 59);
        cal.set(Calendar.SECOND, 59);
        cal.set(Calendar.MILLISECOND, 999);
        return cal.getTime();
    }

    public Date getStartOfMonth(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.set(Calendar.DAY_OF_MONTH, 1);
        cal.set(Calendar.HOUR_OF_DAY, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MILLISECOND, 0);
        return cal.getTime();
    }

    public Date getEndOfMonth(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.set(Calendar.DAY_OF_MONTH, cal.getActualMaximum(Calendar.DAY_OF_MONTH));
        cal.set(Calendar.HOUR_OF_DAY, 23);
        cal.set(Calendar.MINUTE, 59);
        cal.set(Calendar.SECOND, 59);
        cal.set(Calendar.MILLISECOND, 999);
        return cal.getTime();
    }
}
