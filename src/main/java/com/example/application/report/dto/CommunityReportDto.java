package com.example.application.report.dto;

import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public class CommunityReportDto {
    private Long communityId;
    private Long accountId;

    public void reportValid() {
       if (this.communityId != null && this.accountId != null) {
           throw new RuntimeException(this.communityId + "신고를 중복으로 눌렀습니다." + this.accountId);
       }
    }
}
