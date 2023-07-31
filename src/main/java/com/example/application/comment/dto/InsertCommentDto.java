package com.example.application.comment.dto;


import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;


@Setter
@Getter
public class InsertCommentDto {
    private Long communityId;
    private Long accountId;

    @NotBlank(message = "내용을 입력하세요.")
    private String content;
}
