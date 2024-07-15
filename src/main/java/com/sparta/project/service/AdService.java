package com.sparta.project.service;

import com.sparta.project.dto.AdRequestDto;
import com.sparta.project.entity.Ad;
import com.sparta.project.entity.Video;
import com.sparta.project.repository.AdRepository;
import com.sparta.project.repository.VideoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.Optional;

@Service
@Transactional
public class AdService {

    private final AdRepository adRepository;
    private final VideoRepository videoRepository;

    @Autowired
    public AdService(AdRepository adRepository, VideoRepository videoRepository) {
        this.adRepository = adRepository;
        this.videoRepository = videoRepository;
    }

    public void uploadAd(AdRequestDto requestDto){
        // 비디오 정보 조회
        Optional<Video> optionalVideo = videoRepository.findById(requestDto.getVideoId());
        if(optionalVideo.isEmpty()){
            throw new IllegalArgumentException("해당 비디오를 찾을 수 없습니다.");
        }
        Video video = optionalVideo.get();

        // 광고 생성 및 저장
        Ad ad = new Ad();
        ad.setVideo(video);
        ad.setTitle(requestDto.getTitle());
        ad.setStart(requestDto.getStart());
        ad.setAdTimes(requestDto.getAdTimes());
        ad.setCreatedAt(new Date());

        adRepository.save(ad);
    }

}
