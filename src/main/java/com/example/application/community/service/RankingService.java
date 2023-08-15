package com.example.application.community.service;

import com.example.application.community.dto.RankIngLikesDto;
import com.example.application.community.mapper.CommunityReadMapper;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ZSetOperations;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;

@Service
@RequiredArgsConstructor
@Slf4j
public class RankingService {

    @Setter
    @Getter
    private List<RankIngLikesDto> rankIngLikesDtoList;

    private final CommunityReadMapper communityReadMapper;
    private final StringRedisTemplate redisTemplate;

    private static final String RANKING_KEY = "likes_ranking";
    private static final long TIMEOUT_IN_SECONDS = 3600; //3600 1시간

    // 캐시 데이터가 있는 경우 레디스에서 조회하여 반환
    public List<RankIngLikesDto> getTopLikesRank(int limit) {
        boolean cacheExists = redisTemplate.hasKey(RANKING_KEY);
        if (cacheExists) {
            return getLikesRankFromRedis(limit);
        } else {
            // 캐시 데이터가 없는 경우 업데이트 메서드 실행
            updateLikesRanking();
            return getLikesRankFromRedis(limit);
        }
    }

    // 레디스에서 상위 Likes 랭킹을 가져오는 메서드
    private List<RankIngLikesDto> getLikesRankFromRedis(int limit) {
        Set<ZSetOperations.TypedTuple<String>> rangeWithScores = redisTemplate.opsForZSet().reverseRangeWithScores(RANKING_KEY, 0, limit - 1);
        List<RankIngLikesDto> rankIngLikesDtoList = new ArrayList<>();

        for (ZSetOperations.TypedTuple<String> tuple : rangeWithScores) {
            String value = tuple.getValue();
            double score = tuple.getScore();
            String[] splitValues = value.split("_");

            if (splitValues.length == 2) {
                Long communityId = Long.parseLong(splitValues[0]);
                String title = splitValues[1];

                RankIngLikesDto rankIngLikesDto = new RankIngLikesDto();
                rankIngLikesDto.setCommunityId(communityId);
                rankIngLikesDto.setLikes((long) score);
                rankIngLikesDto.setTitle(title);

                rankIngLikesDtoList.add(rankIngLikesDto);
            }
        }

        return rankIngLikesDtoList;
    }

    // 업데이트 메서드
    public void updateLikesRanking() {
        List<RankIngLikesDto> rankIngLikesDtoList = communityReadMapper.selectCommunityLikesRanking();

        for (RankIngLikesDto rankIngLikesDto : rankIngLikesDtoList) {
            Long communityId = rankIngLikesDto.getCommunityId();
            Long likes = rankIngLikesDto.getLikes();
            String title = rankIngLikesDto.getTitle();

            String value = communityId + "_" + title;
            log.info("rankIngLikesDto {}", rankIngLikesDto);
            log.info("likes {}", likes);

            redisTemplate.opsForZSet().add(RANKING_KEY, value, likes); // 중복을 포함한 값으로 추가

            // 캐시 만료 시간 설정
            redisTemplate.expire(RANKING_KEY, TIMEOUT_IN_SECONDS, TimeUnit.SECONDS);
        }
    }
}