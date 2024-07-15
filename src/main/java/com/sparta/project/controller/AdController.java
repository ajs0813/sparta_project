package com.sparta.project.controller;

import com.sparta.project.dto.AdRequestDto;
import com.sparta.project.service.AdService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/ads")
public class AdController {

    private final AdService adService;

    @Autowired
    public AdController(AdService adService) {
        this.adService = adService;
    }

    @PostMapping("/upload")
    public ResponseEntity<String> uploadAd(@RequestBody AdRequestDto adRequestDto){
        try{
            adService.uploadAd(adRequestDto);
            return ResponseEntity.ok("광고가 성공적으로 업로드 되었습니다.");
        } catch(Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("광고 업로드 중 오류가 발생했습니다.");
        }
    }

}