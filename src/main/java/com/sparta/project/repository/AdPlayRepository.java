package com.sparta.project.repository;

import com.sparta.project.entity.Ad;
import com.sparta.project.entity.AdPlay;
import com.sparta.project.entity.Video;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AdPlayRepository extends JpaRepository<AdPlay, Long> {
    Optional<AdPlay> findByVideoAndAd(Video video, Ad ad);
}
