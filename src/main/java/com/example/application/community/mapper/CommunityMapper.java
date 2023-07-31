package com.example.application.community.mapper;

import com.example.application.community.dto.CommunityDto.*;
import com.example.application.community.dto.SearchCondition;
import org.apache.ibatis.annotations.Mapper;


import java.util.List;
import java.util.Map;

@Mapper
public interface CommunityMapper {


   void insertCommunity(CommunityNewDto communityNewRespDto);
   void insertCommunityTag(Map map);
   List<CommunityTagResultDto> selectAllCommunityTag(SearchCondition searchCondition);
   int searchResultCnt(SearchCondition searchCondition);
   ArticleDto selectCommunityByCommunityId(Long communityId);
   void deleteArticle(Map map);
   void deleteCommunityTagsByCommunityId(Long communityId);
   void updateArticleView(Long communityId);
   ArticleModificationFormDto selectArticleUpdateByCommunityId(Long communityId);
   void updateArticle(ArticleModificationDto articleModificationDto);
}
