package com.example.application.tag.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.Pattern;


@Getter
@Setter
public class TagFormDto {


    private String tagTitle;

    public static final String TAG_TITLE_INVALID = "특수문자 금지";
}
