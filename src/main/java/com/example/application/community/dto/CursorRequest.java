package com.example.application.community.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class CursorRequest {

    private Long communityId;
    private int size;
    public static final Long NONE_communityId = -1L;

    /*
    * 커서 키(PK)가 있을 때에는 인덱스 필수, 중복 존재 X
    *
    */

    public Boolean hasCommunityId() {
        return communityId != null && !communityId.equals(NONE_communityId);
    }

    public  CursorRequest next(Long communityId) {
        return new CursorRequest(communityId, size);
    }

}
