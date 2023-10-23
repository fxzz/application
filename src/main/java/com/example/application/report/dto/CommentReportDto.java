package com.example.application.report.dto;

import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public class CommentReportDto {
    private Long commentId;
    private Long accountId;

    public void reportValid() {
        if (this.commentId != null && this.accountId != null) {
            throw new RuntimeException(this.commentId + "신고를 중복으로 눌렀습니다." + this.accountId);
        }
    }
}
