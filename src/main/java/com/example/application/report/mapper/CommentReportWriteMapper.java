package com.example.application.report.mapper;

import org.apache.ibatis.annotations.Mapper;

import java.util.Map;

@Mapper
public interface CommentReportWriteMapper {
    void insertCommentReport(Map map);
}
