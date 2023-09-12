package com.example.application.community.service;

import com.example.application.community.dto.RankIngLikesDto;

import java.util.List;

public interface RankingService {

    List<RankIngLikesDto> getTopLikesRank(int limit);
    List<RankIngLikesDto> getRankIngLikesDtoList();
    void setRankIngLikesDtoList(List<RankIngLikesDto> rankIngLikesDtoList);
}
