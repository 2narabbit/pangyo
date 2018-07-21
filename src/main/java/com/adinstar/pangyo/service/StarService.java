package com.adinstar.pangyo.service;

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
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Service
public class StarService {

    private static final long TOP_RANK = 1L;

    @Autowired
    private StarMapper starMapper;

    public Star getById(long id) {
        return starMapper.selectById(id);
    }

    public void add(Star star) {
        starMapper.insert(star);
    }

    public void modify(Star star) {
        starMapper.update(star);
    }

    public FeedResponse<RankData<Star>> getStarRankList(Optional<Long> rankId, int size) {
        List<Star> starList = starMapper.selectListOrderByFanCount(rankId.orElse(TOP_RANK) - 1, Integer.MAX_VALUE);

        LocalDateTime updateTime = LocalDateTime.now();
        List<RankData<Star>> rankDataList = IntStream.range(0, starList.size())
                                                     .mapToObj(i -> new RankData<Star>(Long.valueOf(i) + rankId.orElse(TOP_RANK), starList.get(i), updateTime))
                                                     .collect(Collectors.toList());
        return new FeedResponse<>(rankDataList, size);
    }

    private Set<Long> getJoinedStarId(long userId, Long starId) {
        return starMapper.selectListByUserIdAndStarId(userId, starId)
                         .stream()
                         .map(s -> s.getId())
                         .collect(Collectors.toSet());
    }

    public FeedResponse<RankData<Star>> getJoinedStarRankListByUserId(long userId, Optional<Long> rankId, int size) {
        Set<Long> joinedStarId = getJoinedStarId(userId, null);
        if (joinedStarId.isEmpty()) {
            return (FeedResponse<RankData<Star>>) FeedResponse.EMPTY_LIST;
        }

        List<RankData<Star>> joinedStarList = getStarRankList(rankId, Integer.MAX_VALUE)
                                              .getList()
                                              .stream()
                                              .filter(s -> joinedStarId.contains(s.getContent().getId()))
                                              .collect(Collectors.toList());
        return new FeedResponse<>(joinedStarList, size);
    }

    public FeedResponse<RankData<Star>> getNotJoinedStarRankListByUserId(long userId, Optional<Long> rankId, int size) {
        Set<Long> joinedStarId = getJoinedStarId(userId, null);
        List<RankData<Star>> notJoinedStarList = getStarRankList(rankId, Integer.MAX_VALUE)
                                                 .getList()
                                                 .stream()
                                                 .filter(s -> !joinedStarId.contains(s.getContent().getId()))
                                                 .collect(Collectors.toList());
        return new FeedResponse<>(notJoinedStarList, size);
    }

    @Transactional
    public void joinedStar(long starId, long userId) {
        int insertedRowCount = starMapper.insertJoin(starId, userId);
        if (insertedRowCount > 0) {
            updateFanCount(starId, 1);
        }
    }

    @Transactional
    public void secededStar(long starId, long userId) {
        int deletedRawCount = starMapper.deleteJoin(starId, userId);
        if (deletedRawCount > 0) {
            updateFanCount(starId, -1);
        }
    }

    private void updateFanCount(long starId, int delta) {
        starMapper.updateFanCount(starId, delta);
    }

    public List<Long> getStarIdListOrderByFanCount(long offset, int size) {
        return starMapper.selectListOrderByFanCount(offset, size)
                         .stream()
                         .map(s -> s.getId())
                         .collect(Collectors.toList());
    }

    public boolean isJoined(long starId, long userId) {
        return !getJoinedStarId(userId, starId).isEmpty();
    }
}
