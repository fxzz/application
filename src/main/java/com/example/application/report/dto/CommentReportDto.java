package com.example.application.report.dto;

import com.example.application.util.exception.CustomApiException;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public class CommentReportDto {
    private Long commentId;
    private Long accountId;

    public void reportValid() {
        if (this.commentId != null && this.accountId != null) {

            if (this.commentId < 0 || this.accountId < 0) {
                throw new CustomApiException("communityId 또는 accountId가 존재하지 않습니다.");
            }

            throw new CustomApiException(this.commentId + "중복된 신고 입니다." + this.accountId);
        }
    }
}
