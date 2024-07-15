package com.sparta.project.repository;

import com.sparta.project.entity.User;
import com.sparta.project.entity.Video;
import com.sparta.project.entity.VideoPlay;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface VideoPlayRepository extends JpaRepository<VideoPlay, Long> {

    Optional<VideoPlay> findByUserAndVideo(User user, Video video);
}
