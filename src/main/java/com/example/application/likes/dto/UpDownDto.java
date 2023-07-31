package com.example.application.likes.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;


@Setter
@Getter
@AllArgsConstructor
public class UpDownDto {

    private Long communityId;
    private Long accountId;
    private Long change;



}
