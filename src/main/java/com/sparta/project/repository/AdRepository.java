package com.sparta.project.repository;

import com.sparta.project.entity.Ad;
import com.sparta.project.entity.Video;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AdRepository extends JpaRepository<Ad, Long> {
    // 광고를 특정 동영상과 연관지어 찾기 위한 메소드
    List<Ad> findByVideo(Video video);
}
