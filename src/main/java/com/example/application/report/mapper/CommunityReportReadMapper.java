package com.example.application.report.mapper;

import com.example.application.report.dto.CommunityReportDto;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface CommunityReportReadMapper {
    CommunityReportDto selectUserExists(Long communityId, Long accountId);
}
