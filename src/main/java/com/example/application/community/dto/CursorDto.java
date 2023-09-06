package com.example.application.community.dto;

import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class CursorDto {
   private Long communityId;
   private String title;
   private LocalDateTime createdAt;

}
