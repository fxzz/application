package com.example.application.admin.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SearchCondition {

    private Integer page = 1;
    private Integer pageSize = 10;
    private String keyword = "";


    public Integer getOffset() {
        return (page-1) * pageSize;
    }
}
