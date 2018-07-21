package com.adinstar.pangyo.service;

import com.adinstar.pangyo.admin.mapper.StarRankMapper;
import com.adinstar.pangyo.controller.exception.InvalidConditionException;
import com.adinstar.pangyo.model.FeedResponse;
import com.adinstar.pangyo.model.RankData;
import com.adinstar.pangyo.model.Star;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class RankService {

    private static final Long TOP_RANK = 1L;

    @Autowired
    private StarRankMapper starRankMapper;

    private LocalDateTime getLastTime() {
        LocalDateTime lastTime = starRankMapper.selectLastTime();
        if (lastTime == null) {
            throw InvalidConditionException.LAST_TIME;
        }
        return lastTime;
    }

    public FeedResponse<RankData<Star>> getStarRankList(Optional<Long> rankId, int size) {
        List<RankData<Star>> starList = starRankMapper.selectStarRankList(rankId.orElse(TOP_RANK), getLastTime(), size + 1);
        return new FeedResponse<>(starList, size);
    }

    public FeedResponse<RankData<Star>> getJoinedStarRankListByUserId(long userId, Optional<Long> rankId, int size) {
        List<RankData<Star>> starList = starRankMapper.selectJoinedStarRankListByUserId(userId, rankId.orElse(TOP_RANK), getLastTime(), size + 1);
        return new FeedResponse<>(starList, size);
    }

    public FeedResponse<RankData<Star>> getNotJoinedStarRankListByUserId(long userId, Optional<Long> rankId, int size) {
        List<RankData<Star>> starList = starRankMapper.selectNotJoinedStarRankListByUserId(userId, rankId.orElse(TOP_RANK), getLastTime(), size + 1);
        return new FeedResponse<>(starList, size);
    }
}
