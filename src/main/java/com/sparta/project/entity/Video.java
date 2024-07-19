package com.sparta.project.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Entity
@Table(name = "video")
@Getter
@Setter
@NoArgsConstructor
public class Video {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "title", nullable = false, length = 255)
    private String title;

    @Column(name = "description", columnDefinition = "TEXT")
    private String description;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "uploaderId", nullable = false)
    private User uploaderId;

    @Column(name = "videoViews", nullable = false)
    private int views = 0;

    @Column(name = "videoTimes", nullable = false)
    private int videoTimes = 0;

    @Column(name = "status", nullable = false)
    private String status;

    @Column(name = "createdAt", nullable = false)
    @Temporal(TemporalType.DATE)
    private Date createdAt;

    // 필요한 매개변수를 받는 생성자 추가
    public Video(String title, String description, User uploaderId, String status, int videoTimes){
        this.title = title;
        this.description = description;
        this.uploaderId = uploaderId;
        this.status = status;
        this.videoTimes = videoTimes;
        this.createdAt = new Date();
    }

    public Video(Long videoId) {
    }
}
