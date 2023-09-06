package com.example.application.community.mapper;

import com.example.application.community.dto.CursorDto;
import com.example.application.community.dto.RankIngLikesDto;
import com.example.application.community.dto.SearchCondition;
import org.apache.ibatis.annotations.Mapper;
import com.example.application.community.dto.CommunityDto.*;

import java.util.List;
import java.util.Map;

@Mapper
public interface CommunityReadMapper {
    ArticleModificationFormDto selectArticleUpdateByCommunityId(Long communityId);
    int searchResultCnt(SearchCondition searchCondition);
    ArticleDto selectCommunityByCommunityId(Long communityId);
    List<CommunityTagResultDto> selectAllCommunityTag(SearchCondition searchCondition);
    List<RankIngLikesDto> selectCommunityLikesRanking();
    Map<String, Object> selectAccountTitleByCommunityId(Long communityId);

    List<CursorDto> selectFirstPage(Map<String, Object> map);
    List<CursorDto> selectNextPage(Map<String, Object> map);


}
