package com.example.application.community.mapper;

import com.example.application.community.dto.RankIngLikesDto;
import com.example.application.community.dto.SearchCondition;
import org.apache.ibatis.annotations.Mapper;
import com.example.application.community.dto.CommunityDto.*;

import java.util.List;

@Mapper
public interface CommunityReadMapper {
    ArticleModificationFormDto selectArticleUpdateByCommunityId(Long communityId);
    int searchResultCnt(SearchCondition searchCondition);
    ArticleDto selectCommunityByCommunityId(Long communityId);
    List<CommunityTagResultDto> selectAllCommunityTag(SearchCondition searchCondition);
    List<RankIngLikesDto> selectCommunityLikesRanking();


}
