package com.sparta.project.controller;

import com.sparta.project.dto.VideoPlayDto;
import com.sparta.project.dto.VideoUploadRequestDto;
import com.sparta.project.service.VideoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/videos")
public class VideoController {
    private final VideoService videoService;

    @Autowired
    public VideoController(VideoService videoService) {
        this.videoService = videoService;
    }

    @PostMapping("/upload")
    public ResponseEntity<String> uploadVideo(@RequestBody VideoUploadRequestDto videoUploadRequestDto)
    {
        try{
            videoService.uploadVideo(videoUploadRequestDto);
            return ResponseEntity.ok("비디오가 성공적으로 업로드 되었습니다.");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("비디오 업로드 중 오류가 발생했습니다");
        }
    }

    @PostMapping("/play")
    public ResponseEntity<String> playVideo(@RequestBody VideoPlayDto videoPlayDto){
        try{
            int lastPlayedTime = videoPlayDto.getLastPlayedTime();
            videoService.playVideo(videoPlayDto.getUserId(), videoPlayDto.getVideoId(), videoPlayDto.getStopTime(), videoPlayDto.getLastPlayedTime());
            return ResponseEntity.ok("비디오가 재생되었습니다.");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("비디오 재생 중 오류가 발생했습니다.");
        }
    }
}
