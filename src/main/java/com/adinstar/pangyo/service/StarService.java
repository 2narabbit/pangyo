package com.adinstar.pangyo.service;

import com.adinstar.pangyo.constant.PangyoEnum;
import com.adinstar.pangyo.controller.exception.InvalidConditionException;
import com.adinstar.pangyo.mapper.ExecutionRuleMapper;
import com.adinstar.pangyo.mapper.StarMapper;
import com.adinstar.pangyo.model.ExecutionRule;
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

    private static final long TOP_RANK = 0L;

    @Autowired
    private StarMapper starMapper;

    @Autowired
    private ExecutionRuleMapper executionRuleMapper;

    public Star getById(long id) {
        return starMapper.selectById(id);
    }

    public void add(Star star) {
        starMapper.insert(star);
    }

    public void modify(Star star) {
        starMapper.update(star);
    }

    private LocalDateTime getLastUpdateTime() {
        ExecutionRule executionRule = executionRuleMapper.selectByTypeAndStatus(PangyoEnum.ExecutionRuleType.STAR_SNAPSHOT, PangyoEnum.ExecutionRuleStatus.RUNNING);
        if (executionRule == null) {
            throw InvalidConditionException.EXECUTION_RULE;
        }
        return executionRule.getStartDttm();
    }

    public FeedResponse<RankData<Star>> getStarRankList(Optional rankId, int size) {
        List<RankData<Star>> starList = starMapper.selectStarRankList((long) rankId.orElse(TOP_RANK), getLastUpdateTime(), size + 1);
        return new FeedResponse<>(starList, size);
    }

    public FeedResponse<RankData<Star>> getJoinedStarRankListByUserId(long userId, Optional rankId, int size) {
        List<RankData<Star>> starList = starMapper.selectJoinedStarRankListByUserId(userId, (long) rankId.orElse(TOP_RANK), getLastUpdateTime(), size + 1);
        return new FeedResponse<>(starList, size);
    }

    public FeedResponse<RankData<Star>> getNotJoinedStarRankListByUserId(long userId, Optional rankId, int size) {
        List<RankData<Star>> starList = starMapper.selectNotJoinedStarRankListByUserId(userId, (long) rankId.orElse(TOP_RANK), getLastUpdateTime(), size + 1);
        return new FeedResponse<>(starList, size);
    }

    @Transactional
    public void joinedStar(long starId, long userId) {
        starMapper.insertJoin(starId, userId);
        increaseFanCount(starId, 1);
    }

    @Transactional
    public void secededStar(long starId, long userId) {
        starMapper.deleteJoin(starId, userId);
        increaseFanCount(starId, -1);
    }

    private void increaseFanCount(long starId, int delta){
        starMapper.updateFanCount(starId, delta);
    }
}
