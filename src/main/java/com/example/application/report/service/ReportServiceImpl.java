package com.example.application.report.service;

import com.example.application.report.dto.CommentReportDto;
import com.example.application.report.dto.CommunityReportDto;
import com.example.application.report.mapper.CommentReportReadMapper;
import com.example.application.report.mapper.CommentReportWriteMapper;
import com.example.application.report.mapper.CommunityReportReadMapper;
import com.example.application.report.mapper.CommunityReportWriteMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;

@Service
@RequiredArgsConstructor
@Transactional
public class ReportServiceImpl implements ReportService{
   private final CommentReportWriteMapper commentReportWriteMapper;
   private final CommunityReportWriteMapper communityReportWriteMapper;
   private final CommunityReportReadMapper communityReportReadMapper;
   private final CommentReportReadMapper commentReportReadMapper;


   @Override
   public void reportCommunity(Long communityId, Long accountId) {

       reportCommunityValid(communityId, accountId);

       saveCommunityReport(communityId, accountId);

   }



   @Override
   public void reportComment(Long commentId, Long accountId) {

        reportCommentValid(commentId, accountId);

        saveReportComment(commentId, accountId);
    }



    private void saveReportComment(Long commentId, Long accountId) {
        HashMap<String, Long> reportParams = new HashMap<>();
        reportParams.put("commentId", commentId);
        reportParams.put("accountId", accountId);
        commentReportWriteMapper.insertCommentReport(reportParams);
    }


    private void saveCommunityReport(Long communityId, Long accountId) {
        HashMap<String, Long> reportParams = new HashMap<>();
        reportParams.put("communityId", communityId);
        reportParams.put("accountId", accountId);
        communityReportWriteMapper.insertCommunityReport(reportParams);
    }


    private void reportCommunityValid(Long communityId, Long accountId) {
        CommunityReportDto communityReportDto = communityReportReadMapper.selectUserExists(communityId, accountId);
        if (communityReportDto != null) {
            communityReportDto.reportValid();
        }
    }

    private void reportCommentValid(Long commentId, Long accountId) {
        CommentReportDto commentReportDto  = commentReportReadMapper.selectUserExists(commentId, accountId);
        if (commentReportDto != null) {
            commentReportDto.reportValid();
        }
    }

}
