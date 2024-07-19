package com.sparta.project.repository;

import com.sparta.project.entity.Ad;
import com.sparta.project.entity.AdPlay;
import com.sparta.project.entity.Video;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Repository
public interface AdPlayRepository extends JpaRepository<AdPlay, Long> {
    Optional<AdPlay> findByVideoAndAd(Video video, Ad ad);

    List<AdPlay> findByCreatedAtBetween(Date startOfDay, Date endOfDay);

    @Query("SELECT a FROM Ad a JOIN AdPlay ap ON a.id = ap.ad.id WHERE ap.video = :video")
    Ad findAdForVideo(@Param("video") Video video);
}
