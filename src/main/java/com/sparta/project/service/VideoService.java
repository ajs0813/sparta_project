package com.sparta.project.service;

import com.sparta.project.dto.VideoUploadRequestDto;
import com.sparta.project.entity.*;
import com.sparta.project.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class VideoService {

    private final VideoRepository videoRepository;
    private final UserRepository userRepository;
    private final VideoPlayRepository videoPlayRepository;
    private final AdRepository adRepository;
    private final AdPlayRepository adPlayRepository;

    @Autowired
    public VideoService(VideoRepository videoRepository, UserRepository userRepository, VideoPlayRepository videoPlayRepository, AdRepository adRepository, AdPlayRepository adPlayRepository) {
        this.videoRepository = videoRepository;
        this.userRepository = userRepository;
        this.videoPlayRepository = videoPlayRepository;
        this.adRepository  = adRepository;
        this.adPlayRepository = adPlayRepository;
    }

    public void uploadVideo(VideoUploadRequestDto videoUploadRequestDto) {
        // 사용자 정보 조회
        Optional<User> optionalUser = userRepository.findById(videoUploadRequestDto.getUserId());
        if (optionalUser.isEmpty()) {
            throw new IllegalArgumentException("해당 사용자를 찾을 수 업습니다.");
        }
        User uploader = optionalUser.get();

        // 비디오 생성 및 저장
        Video video = new Video(videoUploadRequestDto.getTitle(), videoUploadRequestDto.getDescription(), uploader, videoUploadRequestDto.getStatus(), videoUploadRequestDto.getVideoTimes());
        videoRepository.save(video);

        // 사용자의 비디오 목록에 추가
        uploader.getVideos().add(video);
        userRepository.save(uploader);
    }

    public void playVideo(Long userId, Long videoId, int stopTime, int lastPlayedTime){
        //사용자 정보 조회
        Optional<User> optionalUser = userRepository.findById(userId);
        if(optionalUser.isEmpty()){
            throw new IllegalArgumentException("해당 사용자를 찾을 수 없습니다.");
        }

        User user = optionalUser.get();

        // 비디오 정보 조회
        Optional<Video> optionalVideo = videoRepository.findById(videoId);
        if(optionalVideo.isEmpty()){
            throw new IllegalArgumentException("해당 비디오를 찾을 수 없습니다.");
        }

        Video video = optionalVideo.get();

        // 기존 VideoPlay 엔티티 조회
        Optional<VideoPlay> optionalVideoPlay = videoPlayRepository.findByUserAndVideo(user, video);

        if(optionalVideoPlay.isPresent()){
            // 기존 기록이 있는 경우
            VideoPlay videoPlay = optionalVideoPlay.get();
            videoPlay.setStopTime(stopTime); // 중단 시간 업데이트
            videoPlay.setLastPlayedTime(videoPlay.getLastPlayedTime() + lastPlayedTime); // 누적 시간 업데이트
            videoPlayRepository.save(videoPlay);
        } else {
            VideoPlay videoPlay = new VideoPlay(user, video, lastPlayedTime);
            videoPlay.setStopTime(stopTime); // 중단 시간 설정
            videoPlayRepository.save(videoPlay);
        }

        // 광고 재생 여부 확인 및 처리
        List<Ad> ads = adRepository.findByVideo(video);
        for(Ad ad : ads){
            int adStart = ad.getStart();
            int adTimes = ad.getAdTimes();

            if(lastPlayedTime >= adStart){
                // 광고 시작 시간 이후 재생된 경우
                int adPlayCount = (lastPlayedTime - adStart) / adTimes + 1; // 광고 재생 횟수 계산

                // 기존 AdPlay 엔티티 조회
                Optional<AdPlay> optionalAdPlay = adPlayRepository.findByVideoAndAd(video, ad);

                if(optionalAdPlay.isPresent()){
                    // 기존 기록이 있는 경우
                    AdPlay adPlay = optionalAdPlay.get();
                    adPlay.setAdPlayTimes(adPlay.getAdPlayTimes() + adPlayCount); // 총 재생 시간 업데이트
                    adPlayRepository.save(adPlay);
                } else {
                    AdPlay adPlay = new AdPlay(video, ad, adPlayCount);
                    adPlayRepository.save(adPlay);
                }

                // 광고 조회수 증가
                ad.setAdViews(ad.getAdViews() + 1);
                adRepository.save(ad);
            }
        }



        // 조회수 증가
        int currentViews = video.getViews();
        video.setViews(currentViews + 1);
    }
}
