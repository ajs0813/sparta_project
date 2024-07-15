package com.sparta.project.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Entity
@Table(name = "ads")
@Getter
@Setter
@NoArgsConstructor
public class Ad {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "video_id", nullable = false)
    private Video video;

    @Column(name = "title", nullable = false)
    private String title;

    @Column(name = "start", nullable = false)
    private int start;

    @Column(name = "ad_views", nullable = false)
    private int adViews = 0;

    @Column(name = "ad_times")
    private int adTimes;

    @Column(name = "created_at", nullable = false)
    @Temporal(TemporalType.DATE)
    private Date createdAt;
}
