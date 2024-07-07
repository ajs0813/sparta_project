package com.sparta.project.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class VideoUploadRequestDto {
    private Long userId;
    private String title;
    private String description;
    private String status;
    private int videoTimes;
}
