package com.sparta.project.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class VideoPlayDto {
    private Long userId;
    private Long videoId;
    private int stopTime;
    private int lastPlayedTime;
}
