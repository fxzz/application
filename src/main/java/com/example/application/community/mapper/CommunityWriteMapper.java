package com.example.application.community.mapper;

import com.example.application.community.dto.CommunityDto.*;
import org.apache.ibatis.annotations.Mapper;


import java.util.Map;

@Mapper
public interface CommunityWriteMapper {


   void insertCommunity(CommunityNewDto communityNewRespDto);
   void insertCommunityTag(Map map);
   void deleteArticle(Map map);
   void deleteCommunityTagsByCommunityId(Long communityId);
   void updateArticleView(Long communityId);
   void updateArticle(ArticleModificationDto articleModificationDto);

}
