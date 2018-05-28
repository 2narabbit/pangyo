package com.adinstar.pangyo.service;

import com.adinstar.pangyo.admin.mapper.StarRankMapper;
import com.adinstar.pangyo.controller.exception.InvalidConditionException;
import com.adinstar.pangyo.mapper.StarMapper;
import com.adinstar.pangyo.model.FeedResponse;
import com.adinstar.pangyo.model.RankData;
import com.adinstar.pangyo.model.Star;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class StarService {

    private static final long TOP_RANK = 1L;

    @Autowired
    private StarMapper starMapper;

    @Autowired
    private StarRankMapper starRankMapper;

    public Star getById(long id) {
        return starMapper.selectById(id);
    }

    public void add(Star star) {
        starMapper.insert(star);
    }

    public void modify(Star star) {
        starMapper.update(star);
    }

    private LocalDateTime getLastTime() {
        LocalDateTime lastTime = starRankMapper.selectLastTime();
        if (lastTime == null) {
            throw InvalidConditionException.LAST_TIME;
        }
        return lastTime;
    }

    public FeedResponse<RankData<Star>> getStarRankList(Optional<Long> rankId, int size) {
        List<RankData<Star>> starList = starMapper.selectStarRankList(rankId.orElse(TOP_RANK), getLastTime(), size + 1);
        return new FeedResponse<>(starList, size);
    }

    public FeedResponse<RankData<Star>> getJoinedStarRankListByUserId(long userId, Optional<Long> rankId, int size) {
        List<RankData<Star>> starList = starMapper.selectJoinedStarRankListByUserId(userId, rankId.orElse(TOP_RANK), getLastTime(), size + 1);
        return new FeedResponse<>(starList, size);
    }

    public FeedResponse<RankData<Star>> getNotJoinedStarRankListByUserId(long userId, Optional<Long> rankId, int size) {
        List<RankData<Star>> starList = starMapper.selectNotJoinedStarRankListByUserId(userId, rankId.orElse(TOP_RANK), getLastTime(), size + 1);
        return new FeedResponse<>(starList, size);
    }

    @Transactional
    public void joinedStar(long starId, long userId) {
        starMapper.insertJoin(starId, userId);
        updateFanCount(starId, 1);
    }

    @Transactional
    public void secededStar(long starId, long userId) {
        starMapper.deleteJoin(starId, userId);
        updateFanCount(starId, -1);
    }

    private void updateFanCount(long starId, int delta){
        starMapper.updateFanCount(starId, delta);
    }

    public List<Long> getStarIdListOrderByFanCount(long offset, int size) {
        return starMapper.selectStarIdListOrderByFanCount(offset, size);
    }
}
