package com.sparta.project.service;

import com.sparta.project.entity.Video;
import com.sparta.project.entity.VideoPlay;
import com.sparta.project.entity.VideoStats;
import com.sparta.project.repository.VideoPlayRepository;
import com.sparta.project.repository.VideoStatsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class VideoStatsService {
    private final VideoStatsRepository videoStatsRepository;
    private final VideoPlayRepository videoPlayRepository;

    @Autowired
    public VideoStatsService(
            VideoStatsRepository videoStatsRepository,
            VideoPlayRepository videoPlayRepository) {
        this.videoStatsRepository = videoStatsRepository;
        this.videoPlayRepository = videoPlayRepository;
    }

    public void generateStats(Date date) {
        Date startOfDay = getStartOfDay(date);
        Date endOfDay = getEndOfDay(date);

        // 주어진 날짜에 해당하는 VideoPlay 데이터 가져오기
        List<VideoPlay> videoPlays = videoPlayRepository.findByCreatedAtBetween(startOfDay, endOfDay);

        // 비디오 ID별로 그룹화
        Map<Video, List<VideoPlay>> groupedByVideo = videoPlays.stream()
                .collect(Collectors.groupingBy(VideoPlay::getVideo));

        for (Map.Entry<Video, List<VideoPlay>> entry : groupedByVideo.entrySet()) {
            Video video = entry.getKey();
            List<VideoPlay> playsForVideo = entry.getValue();

            // 비디오 조회수 (해당 비디오의 VideoPlay 수)
            long totalViews = playsForVideo.size();

            // 비디오 총 재생 시간
            long totalTimes = playsForVideo.stream()
                    .mapToLong(VideoPlay::getLastPlayedTime)
                    .sum();

            // VideoStats 객체 생성 및 저장
            VideoStats videoStats = new VideoStats();
            videoStats.setCreatedAt(date);
            videoStats.setVideo(video);
            videoStats.setVideoViews(totalViews);
            videoStats.setVideoTimes(totalTimes);

            videoStatsRepository.save(videoStats);
        }
    }

    public List<VideoStats> getTop5VideosByViews(Date startDate, Date endDate) {
        return videoStatsRepository.findByCreatedAtBetween(startDate, endDate);
    }

    public List<VideoStats> getTop5VideosByTimes(Date startDate, Date endDate) {
        return videoStatsRepository.findByCreatedAtBetween(startDate, endDate);
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
