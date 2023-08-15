package com.example.application.admin.dto;

import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public class WeeklyAccountCountsDto {
    private Integer monday_count;
    private Integer tuesday_count;
    private Integer wednesday_count;
    private Integer thursday_count;
    private Integer friday_count;
    private Integer saturday_count;
    private Integer sunday_count;
}
