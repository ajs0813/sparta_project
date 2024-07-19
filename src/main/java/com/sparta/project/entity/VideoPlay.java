package com.sparta.project.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Entity
@Table(name = "videoPlay")
@Getter
@Setter
@NoArgsConstructor
public class VideoPlay {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "video_id", nullable = false)
    private Video video;

    @Column(name = "stop_time")
    private int stopTime;

    @Column(name = "last_played_time", nullable = false)
    private int lastPlayedTime;

    @Column(name = "created_at", nullable = false)
    @Temporal(TemporalType.DATE)
    private Date createdAt;

    public VideoPlay(User user, Video video, int lastPlayedTime) {
        this.user = user;
        this.video = video;
        this.lastPlayedTime = lastPlayedTime;
        this.createdAt = new Date();
    }
    
}
