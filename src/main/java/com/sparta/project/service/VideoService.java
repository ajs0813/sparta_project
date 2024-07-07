package com.sparta.project.service;

import com.sparta.project.dto.VideoUploadRequestDto;
import com.sparta.project.entity.User;
import com.sparta.project.entity.Video;
import com.sparta.project.entity.VideoPlay;
import com.sparta.project.repository.UserRepository;
import com.sparta.project.repository.VideoPlayRepository;
import com.sparta.project.repository.VideoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Duration;
import java.util.Optional;

@Service
@Transactional
public class VideoService {

    private final VideoRepository videoRepository;
    private final UserRepository userRepository;
    private final VideoPlayRepository videoPlayRepository;

    @Autowired
    public VideoService(VideoRepository videoRepository, UserRepository userRepository, VideoPlayRepository videoPlayRepository) {
        this.videoRepository = videoRepository;
        this.userRepository = userRepository;
        this.videoPlayRepository = videoPlayRepository;
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

    public void playVideo(Long userId, Long videoId, int stopTime, Duration lastPlayedDuration){
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

        // VideoPlay 엔티티 생성 및 저장

        VideoPlay videoPlay = new VideoPlay(user, video, lastPlayedDuration);
        videoPlay.setStopTime(stopTime); // 중단 시간 설정
        videoPlayRepository.save(videoPlay);

        // 조회수 증가
        int currentViews = video.getViews();
        video.setViews(currentViews + 1);
    }
}
