package com.example.application.report.mapper;

import org.apache.ibatis.annotations.Mapper;

import java.util.Map;

@Mapper
public interface CommunityReportWriteMapper {
   void insertCommunityReport(Map map);
}
