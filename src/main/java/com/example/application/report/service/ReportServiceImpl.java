package com.example.application.report.service;

import com.example.application.report.mapper.CommentReportWriteMapper;
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


   @Override
   public void reportCommunity(Long communityId, Long accountId) {
       HashMap<String, Long> reportParams = new HashMap<>();
       reportParams.put("communityId", communityId);
       reportParams.put("accountId", accountId);
       communityReportWriteMapper.insertCommunityReport(reportParams);
   }

   @Override
   public void reportComment(Long commentId, Long accountId) {
       HashMap<String, Long> reportParams = new HashMap<>();
       reportParams.put("commentId", commentId);
       reportParams.put("accountId", accountId);
       commentReportWriteMapper.insertCommentReport(reportParams);
   }

}
