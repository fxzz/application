package com.example.application.community.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Getter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class CommunityImageEnabledDto {
    private Long communityId;
    private boolean isImageEnabled;
}
