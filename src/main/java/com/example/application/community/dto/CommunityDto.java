package com.example.application.community.dto;

import com.example.application.util.exception.CommunityIdValidationException;
import lombok.*;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

public class CommunityDto  {


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
    @Getter
    public static class CommunityNewDto {

        private String title;
        private String content;
        private Long accountId;
        private Integer communityId;

        public static CommunityNewDto createNewCommunity(CommunityNewReqDto communityNewReqDto, Long accountId) {
            return CommunityNewDto.builder()
                    .title(communityNewReqDto.getTitle())
                    .content(communityNewReqDto.getContent())
                    .accountId(accountId)
                    .build();
        }
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
       private String deleted;
       private boolean imageEnabled;

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
    public static class ArticleModificationDto {
        private Long communityId;
        private String title;
        private String content;
        private LocalDateTime modifiedAt;
        private Long accountId;

        public static ArticleModificationDto updateArticleModificationDto(ArticleModificationFormDto articleModificationFormDto) {
            return ArticleModificationDto.builder()
                    .communityId(articleModificationFormDto.getCommunityId())
                    .title(articleModificationFormDto.getTitle())
                    .content(articleModificationFormDto.getContent())
                    .modifiedAt(LocalDateTime.now())
                    .accountId(articleModificationFormDto.getAccountId())
                    .build();
        }
    }
}
