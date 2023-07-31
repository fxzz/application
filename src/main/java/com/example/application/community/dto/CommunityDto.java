package com.example.application.community.dto;

import lombok.*;

import javax.validation.constraints.NotBlank;
import java.time.LocalDateTime;
import java.util.List;

public class CommunityDto {


    @ToString
    @Setter
    @Getter
    public static class CommunityNewReqDto {

        @NotBlank(message = "제목을 입력하세요.")
        private String title;

        private String tag;

        @NotBlank(message = "내용을 입력하세요.")
        private String content;

    }

    @Builder
    @Setter
    @Getter
    public static class CommunityNewDto {

        private String title;
        private String content;
        private Long accountId;
        private Integer communityId;
    }


    @ToString
    @Getter
    @Setter
    public static class CommunityTagResultDto {
       private Long communityId;
       private String title;
       private String nickname;
       private Long views;
       private Long commentCount;
       private LocalDateTime createdAt;
       private Long likes;
       private List<String> tagTitle;
       private LocalDateTime modifiedAt;

    }

    @ToString
    @Getter
    @Setter
    public static class ArticleDto {
        private Long communityId;
        private String title;
        private String content;
        private Long accountId;
        private String nickname;
        private Long views;
        private LocalDateTime createdAt;
        private Long likes;
        private List<String> tagTitle;
        private LocalDateTime modifiedAt;
    }

    @ToString
    @Getter
    @Setter
    public static class ArticleModificationFormDto {
        private Long communityId;

        @NotBlank(message = "제목을 입력하세요.")
        private String title;

        @NotBlank(message = "내용을 입력하세요.")
        private String content;

        private Long accountId;

        private String tag;
    }


    @Builder
    @Getter
    @Setter
    public static class ArticleModificationDto {
        private Long communityId;
        private String title;
        private String content;
        private LocalDateTime modifiedAt;
    }
}
