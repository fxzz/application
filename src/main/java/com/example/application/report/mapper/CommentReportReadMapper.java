package com.example.application.report.mapper;


import com.example.application.report.dto.CommentReportDto;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface CommentReportReadMapper {
    CommentReportDto selectUserExists(Long commentId, Long accountId);
}
