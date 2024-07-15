package com.sparta.project.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Entity
@Table(name = "ad_plays")
@Getter
@Setter
@NoArgsConstructor
public class AdPlay {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "video_id", nullable = false)
    private Video video;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ad_id", nullable = false)
    private Ad ad;

    @Column(name = "ad_play_times", nullable = false)
    private int adPlayTimes;

    @Column(name = "created_at", nullable = false)
    @Temporal(TemporalType.DATE)
    private Date createdAt;

    public AdPlay(Video video, Ad ad, int adPlayTimes){
        this.video = video;
        this.ad = ad;
        this.adPlayTimes = adPlayTimes;
        this.createdAt = new Date();
    }
}
