package com.sparta.project.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class AdRequestDto {
    private Long videoId;
    private String title;
    private int start;
    private int adTimes;
}
