package com.example.application.report.dto;

import com.example.application.util.exception.CustomApiException;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public class CommunityReportDto {
    private Long communityId;
    private Long accountId;

    public void reportValid() {
       if (this.communityId != null && this.accountId != null) {

           if (this.communityId < 0 || this.accountId < 0) {
               throw new CustomApiException("communityId 또는 accountId가 존재하지 않습니다.");
           }

           throw new CustomApiException(this.communityId + "중복된 신고 입니다." + this.accountId);

       }
    }
}
