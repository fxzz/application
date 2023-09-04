package com.example.application.image.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
public class ImageDto {
    private Long id;
    private String newFilename;
    private String filename;
    private LocalDateTime createdAt;
    private Long communityId;

    private ImageDto(String newFilename, String filename, LocalDateTime createdAt, Long communityId) {
        this.newFilename = newFilename;
        this.filename = filename;
        this.createdAt = createdAt;
        this.communityId = communityId;
    }

    public static ImageDto of(String newFilename, String filename, Long communityId) {
//        if (communityId == null) {
//            throw new IllegalArgumentException("null");
//        }
        LocalDateTime createdAt = LocalDateTime.now();
        return new ImageDto(newFilename, filename, createdAt, communityId);
    }
}
