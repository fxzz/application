package com.example.application.likes.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDateTime;

@ToString
@Getter
public class LikesDto {
    private Long communityId;
    private Long accountId;

    public void setAccountId(Long accountId) {
        this.accountId = accountId;
    }

    public void likesValid() {
        if (this.communityId != null && this.accountId != null) {
            throw new RuntimeException(this.communityId + "likes 를 중복으로 눌렀습니다." + this.accountId);
        }
    }
}
